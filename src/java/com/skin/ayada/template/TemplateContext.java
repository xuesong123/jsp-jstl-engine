/*
 * $RCSfile: TemplateContext.java,v $$
 * $Revision: 1.1  $
 * $Date: 2013-2-19  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.template;

import java.io.Writer;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skin.ayada.runtime.JspFactory;
import com.skin.ayada.runtime.PageContext;
import com.skin.ayada.source.SourceFactory;
import com.skin.ayada.util.StringUtil;

/**
 * <p>Title: TemplateContext</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class TemplateContext
{
    private String home;
    private int expire;
    private String charset;
    private SourceFactory sourceFactory;
    private TemplateFactory templateFactory;
    private ConcurrentHashMap<String, FutureTask<Template>> cache;
    private static final Logger logger = LoggerFactory.getLogger(TemplateContext.class);

    /**
     * @param home
     */
    public TemplateContext(String home)
    {
        this(home, 300);
    }

    /**
     * @param home
     */
    public TemplateContext(String home, int expire)
    {
        this.home = home;
        this.expire = expire;
        this.cache = new ConcurrentHashMap<String, FutureTask<Template>>();
    }

    /**
     * @param path
     * @param context
     * @param writer
     */
    public void execute(String path, Map<String, Object> context, Writer writer) throws Exception
    {
        Template template = this.getTemplate(path);

        if(template == null)
        {
            throw new RuntimeException(path + " not exists!");
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
        PageContext pageContext = JspFactory.getPageContext(this, writer);

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
     * @param file
     * @return Template
     */
    public Template getTemplate(String path) throws Exception
    {
        path = StringUtil.replace(path, "\\", "/");
        path = StringUtil.replace(path, "//", "/");
        final String temp = path;

        if(this.expire == 0)
        {
            return this.create(temp);
        }

        if(this.cache.size() > 256)
        {
            this.clear();
        }
        
        int count = 0;
        int tryCount = 10;

        while(true)
        {
            FutureTask<Template> f = this.cache.get(temp);

            if(f == null)
            {
                Callable<Template> callable = new Callable<Template>(){
                    public Template call() throws InterruptedException
                    {
                        return TemplateContext.this.create(temp);
                    }
                };

                FutureTask<Template> futureTask = new FutureTask<Template>(callable);
                f = this.cache.putIfAbsent(temp, futureTask);

                if(f == null)
                {
                    f = futureTask;
                    futureTask.run();
                }
            }

            try
            {
                Template template = f.get();

                if(template == null || this.expire < 0)
                {
                    return template;
                }
                else
                {
                    long timeMillis = System.currentTimeMillis();

                    if(timeMillis - template.getUpdateTime() > this.expire * 1000L)
                    {
                        if(template.getLastModified() != sourceFactory.getLastModified(template.getPath()))
                        {
                            this.cache.remove(temp, f);
                        }
                        else
                        {
                            template.setUpdateTime(System.currentTimeMillis());
                            return template;
                        }
                    }
                    else
                    {
                        return template;
                    }
                }

                if(count++ >= tryCount)
                {
                    throw new RuntimeException("get template time out...");
                }
            }
            catch(CancellationException e)
            {
                this.cache.remove(temp, f);
            }
            catch(InterruptedException e)
            {
                throw e;
            }
            catch(ExecutionException e)
            {
                throw e;
            }
            catch(Exception e)
            {
                throw e;
            }
        }
    }

    /**
     * @param path
     * @return Template
     */
    private Template create(String path)
    {
        try
        {
            return this.templateFactory.create(this.getSourceFactory(), path, this.charset);
        }
        catch(Throwable t)
        {
            t.printStackTrace();
        }

        return null;
    }

    private synchronized void clear()
    {
        long timeMillis = System.currentTimeMillis();

        for(Map.Entry<String, FutureTask<Template>> entry : this.cache.entrySet())
        {
            FutureTask<Template> f = entry.getValue();

            try
            {
                Template template = f.get();

                if(timeMillis - template.getUpdateTime() > this.expire * 1000L)
                {
                    String key = entry.getKey();

                    if(logger.isDebugEnabled())
                    {
                        logger.debug("template.remove: " + key);
                    }

                    this.cache.remove(key, f);
                }
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
            catch(ExecutionException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * @return String
     */
    public String getHome()
    {
        return home;
    }

    /**
     * @return the expire
     */
    public int getExpire()
    {
        return expire;
    }

    /**
     * @param expire the expire to set
     */
    public void setExpire(int expire)
    {
        this.expire = expire;
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
     * @return the templateFactory
     */
    public TemplateFactory getTemplateFactory()
    {
        return this.templateFactory;
    }

    /**
     * @param templateFactory the templateFactory to set
     */
    public void setTemplateFactory(TemplateFactory templateFactory)
    {
        this.templateFactory = templateFactory;
    }

    public void destory()
    {
        this.cache.clear();
        this.cache = null;
        this.sourceFactory = null;
        this.templateFactory = null;
    }
}
