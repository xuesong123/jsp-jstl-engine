/*
 * $RCSfile: PageContext.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-19 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.runtime;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.URL;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import com.skin.ayada.jstl.fmt.LocalizationContext;
import com.skin.ayada.tagext.BodyContent;

/**
 * <p>Title: PageContext</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public interface PageContext {
    /**
     *
     */
    public static final String LOCALE_KEY = "com.skin.ayada.locale";

    /**
     *
     */
    public static final String TIMEZONE_KEY = "com.skin.ayada.time-zone";

    /**
     *
     */
    public static final String BUNDLE_KEY = "com.skin.ayada.bundle";

    /**
     * @return PageContext
     */
    public PageContext create();

    /**
     * @param out the out to set
     */
    public void setOut(JspWriter out);

    /**
     * @return JspWriter
     */
    public JspWriter getOut();

    /**
     * @param key
     * @param value
     * @return Object
     */
    public Object setAttribute(String key, Object value);

    /**
     * @param key
     * @return Object
     */
    public Object getAttribute(String key);

    /**
     * @param key
     * @return Object
     */
    public Object removeAttribute(String key);

    /**
     * @return Iterator<String>
     */
    public Iterator<String> getAttributeNames();

    /**
     * @param name
     * @return Object
     */
    public String getString(String name);

    /**
     * @param name
     * @return Object
     */
    public boolean getBoolean(String name);

    /**
     * @param name
     * @return Byte
     */
    public Byte getByte(String name);

    /**
     * @param name
     * @return Short
     */
    public Short getShort(String name);

    /**
     * @param name
     * @return Integer
     */
    public Integer getInteger(String name);

    /**
     * @param name
     * @return Float
     */
    public Float getFloat(String name);

    /**
     * @param name
     * @return Double
     */
    public Double getDouble(String name);

    /**
     * @param name
     * @return Long
     */
    public Long getLong(String name);

    /**
     * @param name
     * @param type
     * @return T
     */
    public <T> T getValue(String name, Class<T> type);

    /**
     * @param context
     */
    public void setContext(Map<String, Object> context);

    /**
     * @return Map<String, Object>
     */
    public Map<String, Object> getContext();

    /**
     * @param names
     * @return Map<String, Object>
     */
    public Map<String, Object> getContext(String[] names);

    /**
     * @param timeZone
     */
    public void setTimeZone(TimeZone timeZone);

    /**
     * @return TimeZone
     */
    public TimeZone getTimeZone();

    /**
     * @param locale
     */
    public void setLocale(Locale locale);

    /**
     * @return Locale
     */
    public Locale getLocale();

    /**
     * @param value
     * @param variant
     * @return Locale
     */
    public Locale getLocale(String value, String variant);

    /**
     * @param bundle
     */
    public void setBundle(LocalizationContext bundle);

    /**
     * @return LocalizationContext
     */
    public LocalizationContext getBundle();

    /**
     * @param key
     * @param args
     * @return String
     */
    public String getLocalizedMessage(String key, Object[] args);

    /**
     * @param localizationContext
     * @param key
     * @param args
     * @return String
     */
    public String getLocalizedMessage(LocalizationContext localizationContext, String key, Object[] args);

    /**
     * @param expressionContext
     */
    public void setExpressionContext(ExpressionContext expressionContext);

    /**
     * @return ExpressionContext
     */
    public ExpressionContext getExpressionContext();

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
     * @return BodyContent
     */
    public BodyContent pushBody();

    /**
     * @return JspWriter
     */
    public JspWriter popBody();

    /**
     * @param bodyContent
     * @param escapeXml
     * @throws IOException
     */
    public void print(BodyContent bodyContent, boolean escapeXml) throws IOException;

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
     * @param page
     * @throws Exception
     */
    public void include(String page) throws Exception;

    /**
     * @param page
     * @param context
     * @throws Exception
     */
    public void include(String page, Map<String, Object> context) throws Exception;

    /**
     * @param page
     * @param context
     * @param out
     * @throws Exception
     */
    public void include(String page, Map<String, Object> context, Writer out) throws Exception;

    /**
     * release
     */
    public void release();
}
