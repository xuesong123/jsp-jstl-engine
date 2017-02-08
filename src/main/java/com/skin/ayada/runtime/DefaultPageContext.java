/*
 * $RCSfile: DefaultPageContext.java,v $$
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

import com.skin.ayada.jstl.fmt.LocalizationContext;
import com.skin.ayada.ognl.util.Empty;
import com.skin.ayada.tagext.BodyContent;
import com.skin.ayada.template.TemplateContext;
import com.skin.ayada.util.ClassUtil;

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
    public PageContext create() {
        return this.templateContext.getPageContext(null, this.getOut());
    }

    /**
     * @param out the out to set
     */
    public void setOut(JspWriter out) {
        this.out = out;
    }

    /**
     * @return the out
     */
    public JspWriter getOut() {
        return this.out;
    }

    /**
     * @param key
     * @param value
     * @return Object
     */
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
    public Object getAttribute(String key) {
        return this.attributes.get(key);
    }

    /**
     * @param key
     * @return Object
     */
    public Object removeAttribute(String key) {
        return this.attributes.remove(key);
    }

    /**
     * @return Iterator<String>
     */
    public Iterator<String> getAttributeNames() {
        return this.attributes.keySet().iterator();
    }

    /**
     * @param name
     * @return Object
     */
    public String getString(String name) {
        Object value = this.getAttribute(name);

        if(value == null || value instanceof Empty<?, ?>) {
            return "";
        }
        return value.toString();
    }

    /**
     * @param name
     * @return Object
     */
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
     * @param context
     */
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
    public Map<String, Object> getContext() {
        Map<String, Object> context = new HashMap<String, Object>();
        context.putAll(this.attributes);
        return context;
    }

    /**
     * @param names
     * @return Map<String, Object>
     */
    public Map<String, Object> getContext(String[] names) {
        Map<String, Object> context = new HashMap<String, Object>();

        if(names != null) {
            Object value = null;

            for(String name : names) {
                value = this.getAttribute(name);

                if(value != null) {
                    context.put(name, value);
                }
            }
        }
        return context;
    }

    /**
     * @param timeZone
     */
    public void setTimeZone(TimeZone timeZone) {
        this.setAttribute(PageContext.LOCALE_KEY, timeZone);
    }

    /**
     * @return TimeZone
     */
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
    public void setLocale(Locale locale) {
        this.setAttribute(PageContext.LOCALE_KEY, locale);
    }

    /**
     * @return Locale
     */
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
    public void setBundle(LocalizationContext bundle) {
        this.setAttribute(PageContext.BUNDLE_KEY, bundle);
    }

    /**
     * @return LocalizationContext
     */
    public LocalizationContext getBundle() {
        return (LocalizationContext)(this.getAttribute(PageContext.BUNDLE_KEY));
    }

    /**
     * @param key
     * @param args
     * @return String
     */
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
     * @return the templateContext
     */
    public TemplateContext getTemplateContext() {
        return this.templateContext;
    }

    /**
     * @param templateContext the templateContext to set
     */
    public void setTemplateContext(TemplateContext templateContext) {
        this.templateContext = templateContext;
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
    public void print(BodyContent bodyContent, boolean escapeXml) throws IOException {
        String content = bodyContent.getString();

        if(escapeXml) {
            content = this.escape(content);
        }
        bodyContent.getEnclosingWriter().write(content);
    }

    /**
     * @param source
     * @return String
     */
    private String escape(String source) {
        if(source == null) {
            return "";
        }

        char c;
        StringBuilder buffer = new StringBuilder();

        for(int i = 0, size = source.length(); i < size; i++) {
            c = source.charAt(i);

            switch (c) {
                case '&': {
                    buffer.append("&amp;");
                    break;
                }
                case '"': {
                    buffer.append("&quot;");
                    break;
                }
                case '<': {
                    buffer.append("&lt;");
                    break;
                }
                case '>': {
                    buffer.append("&gt;");
                    break;
                }
                case '\'': {
                    buffer.append("&#39;");
                    break;
                }
                default : {
                    buffer.append(c);
                    break;
                }
            }
        }
        return buffer.toString();
    }

    /**
     * @param path
     * @return URL
     */
    public URL getResource(String path) {
        return this.templateContext.getResource(path);
    }

    /**
     * @param path
     * @return InputStream
     */
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
    public void release() {
        this.attributes.clear();
        this.expressionContext.release();
        this.attributes = null;
        this.templateContext = null;
        this.expressionContext = null;
        this.out = null;
    }
}
