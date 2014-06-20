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
public interface TemplateContext
{
    /**
     * @param path
     * @param context
     * @param writer
     */
    public void execute(String path, Map<String, Object> context, Writer writer) throws Exception;

    /**
     * @param template
     * @param context
     * @param writer
     */
    public void execute(Template template, Map<String, Object> context, Writer writer) throws Exception;

    /**
     * @param path
     * @return Template
     * @throws Exception
     */
    public Template getTemplate(final String path) throws Exception;

    /**
     * @param path
     * @return Template
     * @throws Exception
     */
    public Template getTemplate(final String path, final String encoding) throws Exception;

    /**
     * @param out
     * @return PageContext
     */
    public PageContext getPageContext(Writer out);

    /**
     * @param out
     * @param buffserSize
     * @param autoFlush
     * @return PageContext
     */
    public PageContext getPageContext(Writer out, int buffserSize, boolean autoFlush);

    /**
     * @param home
     */
    public void setHome(String home);

    /**
     * @return String
     */
    public String getHome();

    /**
     * @param expire
     */
    public void setExpire(int expire);

    /**
     * @return int
     */
    public int getExpire();

    /**
     * @param sourceFactory the sourceFactory to set
     */
    public void setSourceFactory(SourceFactory sourceFactory);

    /**
     * @return the sourceFactory
     */
    public SourceFactory getSourceFactory();

    /**
     * @param templateFactory the templateFactory to set
     */
    public void setTemplateFactory(TemplateFactory templateFactory);

    /**
     * @return the templateFactory
     */
    public TemplateFactory getTemplateFactory();

    /**
     * @param expressionFactory the expressionFactory to set
     */
    public void setExpressionFactory(ExpressionFactory expressionFactory);

    /**
     * @return the expressionFactory
     */
    public ExpressionFactory getExpressionFactory();

    /**
     * destory the current TemplateContext
     */
    public abstract void destory();
}
