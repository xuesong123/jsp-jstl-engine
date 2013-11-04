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
import com.skin.ayada.source.DefaultSourceFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(TemplateContext.class);
    private String home;
    private int expire;
    private String charset;
    private SourceFactory sourceFactory;
    private ConcurrentHashMap<String, FutureTask<Template>> cache;

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
        this.sourceFactory = new DefaultSourceFactory(this.home);
        this.cache = new ConcurrentHashMap<String, FutureTask<Template>>();
    }

    /**
     * @param path
     * @param context
     * @param writer
     */
    public void execute(String path, Map<String, Object> context, Writer writer)
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
    public void execute(Template template, Map<String, Object> context, Writer writer)
    {
        PageContext pageContext = JspFactory.getPageContext(this, writer);

        if(context != null)
        {
            for(Map.Entry<String, Object> entry : context.entrySet())
            {
                pageContext.setAttribute(entry.getKey(), entry.getValue());
            }
        }

        if(logger.isDebugEnabled())
        {
            long t1 = System.currentTimeMillis();
            DefaultExecutor.execute(template, pageContext);
            long t2 = System.currentTimeMillis();
            logger.debug(template.getFile() + " - render time: " + (t2 - t1));
        }
        else
        {
            DefaultExecutor.execute(template, pageContext);
        }

        pageContext.release();
    }

    /**
     * @param path
     * @return Template
     */
    public Template getTemplate(String path)
    {
        path = StringUtil.replace(path, "\\", "/");
        path = StringUtil.replace(path, "//", "/");

        if(this.expire == 0)
        {
            return TemplateFactory.create(this.getSourceFactory(), path, this.charset);
        }

        if(this.cache.size() > 256)
        {
            this.clear();
        }

        final String temp = path;

        while(true)
        {
            FutureTask<Template> f = this.cache.get(temp);

            if(f == null)
            {
                Callable<Template> callable = new Callable<Template>(){
                    public Template call() throws InterruptedException
                    {
                        return TemplateFactory.create(TemplateContext.this.getSourceFactory(), temp, TemplateContext.this.charset);
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
                        this.cache.remove(temp, f);
                    }
                    else
                    {
                        return template;
                    }
                }
            }
            catch(CancellationException e)
            {
                this.cache.remove(temp, f);
            }
            catch(InterruptedException e)
            {
                throw new RuntimeException(e);
            }
            catch(ExecutionException e)
            {
                throw new RuntimeException(e);
            }
            catch(Exception e)
            {
                throw new RuntimeException(e);
            }
        }
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
     * @return the sourceFactory
     */
    public SourceFactory getSourceFactory()
    {
        return this.sourceFactory;
    }

    /**
     * @param sourceFactory the sourceFactory to set
     */
    public void setSourceFactory(SourceFactory sourceFactory)
    {
        this.sourceFactory = sourceFactory;
    }

    public void destory()
    {
        this.cache.clear();
        this.cache = null;
    }
}
