/*
 * $RCSfile: TemplateContext.java,v $$
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
import java.util.Map;

import com.skin.ayada.runtime.ExpressionContext;
import com.skin.ayada.runtime.ExpressionFactory;
import com.skin.ayada.runtime.PageContext;
import com.skin.ayada.source.SourceFactory;

/**
 * <p>Title: TemplateContext</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public abstract class TemplateContext
{
    private String home;
    private int expire;
    private String encoding;
    private SourceFactory sourceFactory;
    private TemplateFactory templateFactory;
    private ExpressionFactory expressionFactory;

    /**
     * @param home
     */
    public TemplateContext(String home, int expire)
    {
        this.home = home;
        this.expire = expire;
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
            throw new Exception(this.home + "/" + path + " not exists!");
        }

        this.execute(template, context, writer);
    }

    /**
     * @param template
     * @param context
     * @param writer
     */
    public abstract void execute(Template template, Map<String, Object> context, Writer writer) throws Exception;

    /**
     * @param path
     * @return Template
     * @throws Exception
     */
    public Template getTemplate(final String path) throws Exception
    {
        return this.getTemplate(path, this.encoding);
    }

    /**
     * @param path
     * @return Template
     * @throws Exception
     */
    public abstract Template getTemplate(final String path, final String encoding) throws Exception;

    /**
     * @param out
     * @return PageContext
     */
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
    public abstract PageContext getPageContext(Writer out, int buffserSize, boolean autoFlush);

    /**
     * @return ExpressionContext
     */
    public abstract ExpressionContext getExpressionContext(PageContext pageContext);

    /**
     * destory the current TemplateContext
     */
    public abstract void destory();

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
     * @param expire the expire to set
     */
    public void setExpire(int expire)
    {
        this.expire = expire;
    }

    /**
     * @return the expire
     */
    public int getExpire()
    {
        return this.expire;
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
