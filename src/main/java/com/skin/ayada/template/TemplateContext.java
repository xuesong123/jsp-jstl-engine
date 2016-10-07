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

import java.io.InputStream;
import java.io.Writer;
import java.net.URL;
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
public interface TemplateContext {
    /**
     * @param path
     * @param context
     * @param writer
     * @throws Exception
     */
    public void execute(String path, Map<String, Object> context, Writer writer) throws Exception;

    /**
     * @param path
     * @param encoding
     * @param context
     * @param writer
     * @throws Exception
     */
    public void execute(String path, String encoding, Map<String, Object> context, Writer writer) throws Exception;

    /**
     * @param template
     * @param context
     * @param writer
     * @throws Exception
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
     * @param encoding
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
     * @param context
     * @param out
     * @return PageContext
     */
    public PageContext getPageContext(Map<String, Object> context, Writer out);

    /**
     * @param context
     * @param out
     * @param buffserSize
     * @param autoFlush
     * @return PageContext
     */
    public PageContext getPageContext(Map<String, Object> context, Writer out, int buffserSize, boolean autoFlush);

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
     * @param path
     * @return URL
     */
    public URL getResource(String path);

    /**
     * @param path
     * @return InputStream
     */
    public InputStream getInputStream(String path);

    /**
     * clear the current TemplateContext
     */
    public void clear();

    /**
     * destory the current TemplateContext
     */
    public abstract void destory();
}
