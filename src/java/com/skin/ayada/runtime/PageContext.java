/*
 * $RCSfile: PageContext.java,v $$
 * $Revision: 1.1  $
 * $Date: 2013-2-19  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.runtime;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import com.skin.ayada.jstl.TagLibrary;
import com.skin.ayada.tagext.BodyContent;

/**
 * <p>Title: PageContext</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public interface PageContext
{
    /**
     * @param out the out to set
     */
    public void setOut(JspWriter out);

    /**
     * @return the out
     */
    public JspWriter getOut();

    /**
     * @param key
     * @param value
     */
    public Object setAttribute(String key, Object value);

    /**
     * @param key
     * @return Object
     */
    public Object getAttribute(String key);

    /**
     * @param key
     */
    public Object removeAttribute(String key);

    /**
     * @return Iterator<String>
     */
    public Iterator<String> getAttributeNames();

    /**
     * @param expressionContext
     */
    public void setExpressionContext(ExpressionContext expressionContext);

    /**
     * @return ExpressionContext
     */
    public ExpressionContext getExpressionContext();

    /**
     * @return the tagLibrary
     */
    public TagLibrary getTagLibrary();

    /**
     * @throws IOException
     */
    public void clear() throws IOException;

    /**
     * @throws IOException
     */
    public void flush() throws IOException;

    /**
     * @throws IOException
     */
    public void close() throws IOException;

    /**
     * @return JspWriter
     */
    public JspWriter pushBody();

    /**
     * @return JspWriter
     */
    public JspWriter popBody();

    /**
     * @param bodyContent
     * @param escapeXml
     */
    public void printBodyContent(BodyContent bodyContent, boolean escapeXml) throws IOException;

    /**
     * @param page
     */
    public void include(String page) throws Exception;

    /**
     * @param page
     */
    public void include(String page, Map<String, Object> context) throws Exception;

    /**
     * release
     */
    public void release();
}
