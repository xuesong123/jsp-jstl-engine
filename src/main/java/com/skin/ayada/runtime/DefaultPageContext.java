/*
 * $RCSfile: DefaultPageContext.java,v $
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
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TimeZone;

import com.skin.ayada.ExpressionContext;
import com.skin.ayada.JspWriter;
import com.skin.ayada.PageContext;
import com.skin.ayada.TemplateContext;
import com.skin.ayada.jstl.fmt.LocalizationContext;
import com.skin.ayada.tagext.BodyContent;
import com.skin.ayada.util.ClassUtil;
import com.skin.ayada.util.HtmlUtil;

/**
 * <p>Title: DefaultPageContext</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class DefaultPageContext implements PageContext {
    private JspWriter out;
    private Map<String, Object> attributes;
    private TemplateContext templateContext;
    private ExpressionContext expressionContext;

    /**
     *
     */
    public DefaultPageContext() {
        this(null);
    }

    /**
     * @param out
     */
    public DefaultPageContext(JspWriter out) {
        this.out = out;
        this.attributes = new HashMap<String, Object>();
        this.setAttribute("pageContext", this);
    }

    /**
     * @return PageContext
     */
    @Override
    public PageContext create() {
        return this.templateContext.getPageContext((Map<String, Object>)null, this.getOut());
    }

    /**
     * @param out the out to set
     */
    @Override
    public void setOut(JspWriter out) {
        this.out = out;
    }

    /**
     * @return the out
     */
    @Override
    public JspWriter getOut() {
        return this.out;
    }

    /**
     * @param key
     * @param value
     * @return Object
     */
    @Override
    public Object setAttribute(String key, Object value) {
        if(key == null) {
            return null;
        }

        if(value != null) {
            return this.attributes.put(key, value);
        }
        return this.attributes.remove(key);
    }

    /**
     * @param key
     * @return Object
     */
    @Override
    public Object getAttribute(String key) {
        return this.attributes.get(key);
    }

    /**
     * @param key
     * @return Object
     */
    @Override
    public Object removeAttribute(String key) {
        return this.attributes.remove(key);
    }

    /**
     * @return Iterator<String>
     */
    @Override
    public Iterator<String> getAttributeNames() {
        return this.attributes.keySet().iterator();
    }

    /**
     * @param context
     */
    @Override
    public void setContext(Map<String, Object> context) {
        if(context != null) {
            for(Map.Entry<String, Object> entry : context.entrySet()) {
                if(entry.getValue() != null) {
                    this.attributes.put(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    /**
     * @return Map<String, Object>
     */
    @Override
    public Map<String, Object> getContext() {
        return this.attributes;
    }

    /**
     * @param name
     * @return Object
     */
    @Override
    public String getString(String name) {
        Object value = this.getAttribute(name);

        if(value == null) {
            return "";
        }
        return value.toString();
    }

    /**
     * @param name
     * @return Object
     */
    @Override
    public boolean getBoolean(String name) {
        Object value = this.getAttribute(name);

        if(value == null) {
            return false;
        }

        if(value instanceof Boolean) {
            return Boolean.TRUE.equals(value);
        }
        return (value.toString().equals("true"));
    }

    /**
     * @param name
     * @return Byte
     */
    @Override
    public Byte getByte(String name) {
        Object value = this.getAttribute(name);

        if(value != null && value instanceof Number) {
            return ((Number)value).byteValue();
        }
        return ClassUtil.cast(value, Byte.class);
    }

    /**
     * @param name
     * @return Short
     */
    @Override
    public Short getShort(String name) {
        Object value = this.getAttribute(name);

        if(value != null && value instanceof Number) {
            return ((Number)value).shortValue();
        }
        return ClassUtil.cast(value, Short.class);
    }

    /**
     * @param name
     * @return Integer
     */
    @Override
    public Integer getInteger(String name) {
        Object value = this.getAttribute(name);

        if(value != null && value instanceof Number) {
            return ((Number)value).intValue();
        }
        return ClassUtil.cast(value, Integer.class);
    }

    /**
     * @param name
     * @return Float
     */
    @Override
    public Float getFloat(String name) {
        Object value = this.getAttribute(name);

        if(value != null && value instanceof Number) {
            return ((Number)value).floatValue();
        }
        return ClassUtil.cast(value, Float.class);
    }

    /**
     * @param name
     * @return Double
     */
    @Override
    public Double getDouble(String name) {
        Object value = this.getAttribute(name);

        if(value != null && value instanceof Number) {
            return ((Number)value).doubleValue();
        }
        return ClassUtil.cast(value, Double.class);
    }

    /**
     * @param name
     * @return Long
     */
    @Override
    public Long getLong(String name) {
        Object value = this.getAttribute(name);

        if(value != null && value instanceof Number) {
            return ((Number)value).longValue();
        }
        return ClassUtil.cast(value, Long.class);
    }

    /**
     * @param <T>
     * @param name
     * @param type
     * @return T
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getValue(String name, Class<T> type) {
        Object value = this.getAttribute(name);

        if(value != null && value.getClass().isAssignableFrom(type)) {
            return (T)value;
        }
        else {
            return null;
        }
    }

    /**
     * @param timeZone
     */
    @Override
    public void setTimeZone(TimeZone timeZone) {
        this.setAttribute(PageContext.LOCALE_KEY, timeZone);
    }

    /**
     * @return TimeZone
     */
    @Override
    public TimeZone getTimeZone() {
        TimeZone timeZone = null;
        Object value = this.getAttribute(PageContext.TIMEZONE_KEY);

        if((value instanceof TimeZone)) {
            timeZone = (TimeZone)value;
        }
        else if((value instanceof String)) {
            timeZone = TimeZone.getTimeZone((String)value);
        }

        if(timeZone == null) {
            timeZone = TimeZone.getDefault();
        }
        return timeZone;
    }

    /**
     * @param locale
     */
    @Override
    public void setLocale(Locale locale) {
        this.setAttribute(PageContext.LOCALE_KEY, locale);
    }

    /**
     * @return Locale
     */
    @Override
    public Locale getLocale() {
        Locale locale = null;
        Object value = this.getAttribute(PageContext.LOCALE_KEY);

        if((value instanceof Locale)) {
            locale = (Locale)value;
        }
        else if((value instanceof String)) {
            locale = this.getLocale((String)value, null);
        }

        if(locale == null) {
            locale = Locale.getDefault();
        }
        return locale;
    }

    /**
     * @param value
     * @param variant
     * @return Local
     */
    @Override
    public Locale getLocale(String value, String variant) {
        int i = 0;
        char ch = '\000';
        int length = value.length();
        StringBuilder buffer = new StringBuilder();

        for(; i < length; i++) {
            ch = value.charAt(i);

            if(ch != '_' && ch != '-') {
                buffer.append(ch);
            }
            else {
                break;
            }
        }

        String language = buffer.toString();
        String country = "";

        if((ch == '_') || (ch == '-')) {
            buffer.setLength(0);

            for(i++; i < length; i++) {
                ch = value.charAt(i);

                if(ch != '_' && ch != '-') {
                    buffer.append(ch);
                }
                else {
                    break;
                }
            }
            country = buffer.toString();
        }

        if(variant != null && variant.length() > 0) {
            return new Locale(language, country, variant);
        }
        return new Locale(language, country);
    }

    /**
     * @param bundle
     */
    @Override
    public void setBundle(LocalizationContext bundle) {
        this.setAttribute(PageContext.BUNDLE_KEY, bundle);
    }

    /**
     * @return LocalizationContext
     */
    @Override
    public LocalizationContext getBundle() {
        return (LocalizationContext)(this.getAttribute(PageContext.BUNDLE_KEY));
    }

    /**
     * @param key
     * @param args
     * @return String
     */
    @Override
    public String getLocalizedMessage(String key, Object[] args) {
        LocalizationContext localizationContext = this.getBundle();
        return this.getLocalizedMessage(localizationContext, key, args);
    }

    /**
     * @param localizationContext
     * @param key
     * @param args
     * @return String
     */
    @Override
    public String getLocalizedMessage(LocalizationContext localizationContext, String key, Object[] args) {
        String result = null;
        ResourceBundle bundle = localizationContext.getResourceBundle();
        Locale locale = localizationContext.getLocale();

        if(bundle != null) {
            try {
                result = bundle.getString(key);
            }
            catch(Exception e) {
            }
        }

        if(result == null) {
            return "???" + key + "???";
        }

        if((args == null) || (args.length == 0)) {
            return result;
        }

        if(locale == null) {
            locale = this.getLocale();
        }

        if(locale != null) {
            return new MessageFormat(result, locale).format(args);
        }
        return new MessageFormat(result).format(args);
    }

    /**
     * @param templateContext the templateContext to set
     */
    public void setTemplateContext(TemplateContext templateContext) {
        this.templateContext = templateContext;
    }

    /**
     * @return the templateContext
     */
    public TemplateContext getTemplateContext() {
        return this.templateContext;
    }

    /**
     * @param expressionContext
     */
    public void setExpressionContext(ExpressionContext expressionContext) {
        this.expressionContext = expressionContext;
    }

    /**
     * @return ExpressionContext
     */
    @Override
    public ExpressionContext getExpressionContext() {
        return this.expressionContext;
    }

    /**
     * @throws IOException
     */
    @Override
    public void clear() throws IOException {
        this.out.clear();
    }

    /**
     * @throws IOException
     */
    @Override
    public void flush() throws IOException {
        this.out.flush();
    }

    /**
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        this.out.close();
    }

    /**
     * @return JspWriter
     */
    @Override
    public BodyContent pushBody() {
        BodyContent bodyContent = new BodyContent(this.out);
        this.out = bodyContent;
        return bodyContent;
    }

    /**
     * @param writer
     * @return JspWriter
     */
    public JspWriter pushBody(Writer writer) {
        BodyContent bodyContent = new BodyContent(this.out);
        bodyContent.setWriter(writer);
        this.out = bodyContent;
        return bodyContent;
    }

    /**
     * @return JspWriter
     */
    @Override
    public JspWriter popBody() {
        if(this.out instanceof BodyContent) {
            BodyContent bodyContent = (BodyContent)(this.out);
            return (this.out = bodyContent.getEnclosingWriter());
        }
        else {
            return null;
        }
    }

    /**
     * @param bodyContent
     * @param escapeXml
     * @throws IOException
     */
    @Override
    public void print(BodyContent bodyContent, boolean escapeXml) throws IOException {
        String content = bodyContent.getString();

        if(escapeXml) {
            content = HtmlUtil.encode(content);
        }
        bodyContent.getEnclosingWriter().write(content);
    }

    /**
     * @param path
     * @return URL
     */
    @Override
    public URL getResource(String path) {
        return this.templateContext.getResource(path);
    }

    /**
     * @param path
     * @return InputStream
     */
    @Override
    public InputStream getInputStream(String path) {
        return this.templateContext.getInputStream(path);
    }

    /**
     * @param page
     */
    @Override
    public void include(String page) throws Exception {
        this.include(page, null);
    }

    /**
     * @param page
     * @throws Exception
     */
    @Override
    public void include(String page, Map<String, Object> context) throws Exception {
        this.include(page, context, this.getOut());
    }

    /**
     * @param page
     * @throws Exception
     */
    @Override
    public void include(String page, Map<String, Object> context, Writer out) throws Exception {
        if(this.templateContext == null) {
            throw new NullPointerException("NullPointerException: templateContext");
        }

        if(out != null) {
            this.templateContext.execute(page, context, out);
            out.flush();
        }
        else {
            this.templateContext.execute(page, context, this.getOut());
        }
    }

    /**
     * release
     */
    @Override
    public void release() {
        this.attributes.clear();
        this.expressionContext.release();
        this.attributes = null;
        this.templateContext = null;
        this.expressionContext = null;
        this.out = null;
    }
}
