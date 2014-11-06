/*
 * $RCSfile: DefaultTemplateContext.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-19 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.template;

import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.FutureTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skin.ayada.runtime.DefaultPageContext;
import com.skin.ayada.runtime.ExpressionContext;
import com.skin.ayada.runtime.ExpressionFactory;
import com.skin.ayada.runtime.JspWriter;
import com.skin.ayada.runtime.PageContext;
import com.skin.ayada.source.Source;
import com.skin.ayada.source.SourceFactory;
import com.skin.ayada.util.StringUtil;

/**
 * <p>Title: DefaultTemplateContext</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class DefaultTemplateContext implements TemplateContext
{
    private String home;
    private String encoding;
    private SourceFactory sourceFactory;
    private TemplateFactory templateFactory;
    private ExpressionFactory expressionFactory;
    private ConcurrentHashMap<String, FutureTask<Template>> cache;
    private static final Logger logger = LoggerFactory.getLogger(DefaultTemplateContext.class);

    /**
     * @param home
     */
    public DefaultTemplateContext(String home)
    {
        this.home = home;
        this.encoding = "UTF-8";
        this.cache = new ConcurrentHashMap<String, FutureTask<Template>>();
    }

    /**
     * @param path
     * @param context
     * @param writer
     * @throws Exception
     */
    @Override
    public void execute(String path, Map<String, Object> context, Writer writer) throws Exception
    {
        Template template = this.getTemplate(path);

        if(template == null)
        {
            throw new Exception(this.home + "/" + path + " not exists!");
        }

        this.execute(template, context, writer);
    }

    /**
     * @param template
     * @param context
     * @param writer
     */
    public void execute(Template template, Map<String, Object> context, Writer writer) throws Exception
    {
        PageContext pageContext = this.getPageContext(writer);

        if(context != null)
        {
            for(Map.Entry<String, Object> entry : context.entrySet())
            {
                if(entry.getValue() != null)
                {
                    pageContext.setAttribute(entry.getKey(), entry.getValue());
                }
            }
        }

        try
        {
            template.execute(pageContext);
        }
        finally
        {
            pageContext.release();
        }
    }

    /**
     * @param path
     * @return Template
     * @throws Exception
     */
    @Override
    public Template getTemplate(String path) throws Exception
    {
        return this.getTemplate(path, this.encoding);
    }

    /**
     * @param path
     * @return Template
     * @throws Exception
     */
    public Template getTemplate(final String path, final String encoding) throws Exception
    {
        final String realPath = this.repair(path);
        final SourceFactory sourceFactory = this.getSourceFactory();
        final TemplateFactory templateFactory = this.getTemplateFactory();

        int count = 0;
        int tryCount = 10;
        Template template = null;

        while(true)
        {
            FutureTask<Template> f = this.cache.get(realPath);

            if(f == null)
            {
                Callable<Template> callable = new Callable<Template>(){
                    public Template call() throws InterruptedException
                    {
                        try
                        {
                            return templateFactory.create(sourceFactory, realPath, encoding);
                        }
                        catch(Exception e)
                        {
                            throw new RuntimeException(e);
                        }
                    }
                };

                FutureTask<Template> futureTask = new FutureTask<Template>(callable);
                f = this.cache.putIfAbsent(realPath, futureTask);

                if(f == null)
                {
                    f = futureTask;
                    futureTask.run();
                }
            }

            try
            {
                template = f.get();
            }
            catch(CancellationException e)
            {
                this.cache.remove(realPath, f);
            }
            catch(Exception e)
            {
                throw e;
            }

            if(template != null)
            {
                if(this.modified(template))
                {
                    this.cache.remove(realPath, f);
                    template = null;
                }
                else
                {
                    template.setUpdateTime(System.currentTimeMillis());
                    return template;
                }
            }

            if(count++ >= tryCount)
            {
                throw new Exception("get template time out...");
            }
        }
    }

    /**
     * @param template
     * @return boolean
     */
    public boolean modified(Template template)
    {
        List<Source> dependencies = template.getDependencies();

        if(dependencies != null)
        {
            for(Source source : dependencies)
            {
                if(source.getLastModified() != this.sourceFactory.getLastModified(source.getPath()))
                {
                    return true;
                }
            }
        }
        
        return false;
    }

    /**
     * @param out
     * @return PageContext
     */
    @Override
    public PageContext getPageContext(Writer out)
    {
        return this.getPageContext(out, 8192, false);
    }

    /**
     * @param out
     * @param buffserSize
     * @param autoFlush
     * @return PageContext
     */
    public PageContext getPageContext(Writer out, int buffserSize, boolean autoFlush)
    {
        JspWriter jspWriter = null;

        if(out instanceof JspWriter)
        {
            jspWriter = (JspWriter)out;
        }
        else
        {
            jspWriter = new JspWriter(out, buffserSize, autoFlush);
        }

        DefaultPageContext pageContext = new DefaultPageContext(jspWriter);
        ExpressionContext expressionContext = this.getExpressionContext(pageContext);
        pageContext.setTemplateContext(this);
        pageContext.setExpressionContext(expressionContext);
        return pageContext;
    }

    /**
     * @return ExpressionContext
     */
    private ExpressionContext getExpressionContext(PageContext pageContext)
    {
        return this.getExpressionFactory().getExpressionContext(pageContext);
    }

    protected synchronized void clear()
    {
        for(Map.Entry<String, FutureTask<Template>> entry : this.cache.entrySet())
        {
            FutureTask<Template> f = entry.getValue();

            try
            {
                Template template = f.get();

                if(template.getUpdateTime() != this.sourceFactory.getLastModified(template.getPath()))
                {
                    String key = entry.getKey();

                    if(logger.isDebugEnabled())
                    {
                        logger.debug("template.remove: " + key);
                    }

                    this.cache.remove(key, f);
                }
            }
            catch(Exception e)
            {
                logger.warn(e.getMessage(), e);
            }
        }
    }

    /**
     * @param path
     * @return String
     */
    private String repair(String path)
    {
        String temp = path;
        temp = StringUtil.replace(temp, "\\", "/");
        temp = StringUtil.replace(temp, "//", "/");
        return temp;
    }

    public void destory()
    {
        this.cache.clear();
        this.cache = null;
    }

    /**
     * @param home the home to set
     */
    public void setHome(String home)
    {
        this.home = home;
    }

    /**
     * @return the home
     */
    public String getHome()
    {
        return this.home;
    }

    /**
     * @param encoding the encoding to set
     */
    public void setEncoding(String encoding)
    {
        this.encoding = encoding;
    }

    /**
     * @return the encoding
     */
    public String getEncoding()
    {
        return this.encoding;
    }

    /**
     * @param sourceFactory the sourceFactory to set
     */
    public void setSourceFactory(SourceFactory sourceFactory)
    {
        this.sourceFactory = sourceFactory;
    }

    /**
     * @return the sourceFactory
     */
    public SourceFactory getSourceFactory()
    {
        return this.sourceFactory;
    }

    /**
     * @param templateFactory the templateFactory to set
     */
    public void setTemplateFactory(TemplateFactory templateFactory)
    {
        this.templateFactory = templateFactory;
    }

    /**
     * @return the templateFactory
     */
    public TemplateFactory getTemplateFactory()
    {
        return this.templateFactory;
    }

    /**
     * @param expressionFactory the expressionFactory to set
     */
    public void setExpressionFactory(ExpressionFactory expressionFactory)
    {
        this.expressionFactory = expressionFactory;
    }

    /**
     * @return the expressionFactory
     */
    public ExpressionFactory getExpressionFactory()
    {
        return this.expressionFactory;
    }
}
