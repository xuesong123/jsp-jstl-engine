/*
 * $RCSfile: DefaultExpressionContext.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-20 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.runtime;

import java.io.IOException;
import java.io.Writer;

import ognl.OgnlContext;

import com.skin.ayada.ognl.util.Empty;
import com.skin.ayada.ognl.util.OgnlUtil;
import com.skin.ayada.util.HtmlUtil;

/**
 * <p>Title: DefaultExpressionContext</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class DefaultExpressionContext extends OgnlContext implements ExpressionContext {
    private ELEncoder encoder;
    private PageContext pageContext;
    private static final Object EMPTY = new Empty<String, Object>();

    /**
     * @param pageContext
     */
    protected DefaultExpressionContext(PageContext pageContext) {
        this.pageContext = pageContext;
    }

    /**
     * @param key
     * @param value
     */
    @Override
    public Object put(Object key, Object value) {
        return this.pageContext.setAttribute(key.toString(), value);
    }

    /**
     * @param key
     */
    @Override
    public Object get(Object key) {
        if(key == null) {
            return EMPTY;
        }

        Object value = this.pageContext.getAttribute(key.toString());
        return (value != null ? value : EMPTY);
    }

    /**
     * @param expression
     * @return Object
     */
    @Override
    public Object getValue(String expression) {
        try {
            return OgnlUtil.getValue(expression, this, this);
        }
        catch(Exception e) {
            boolean flag = true;
            Object ignoreElException = this.pageContext.getAttribute("ignoreElException");

            if(ignoreElException != null) {
                flag = "true".equals(ignoreElException.toString());
            }

            if(!flag) {
                throw new RuntimeException(e);
            }
            else {
                return null;
            }
        }
    }

    /**
     * @param expression
     * @return boolean
     */
    @Override
    public boolean getBoolean(String expression) {
        Object value = this.getValue(expression);

        if(value instanceof Boolean) {
            return Boolean.TRUE.equals(value);
        }
        return false;
    }

    /**
     * @param expression
     * @return Byte
     */
    public Byte getByte(String expression) {
        Object value = this.getValue(expression);

        if(value instanceof Number) {
            return ((Number)value).byteValue();
        }
        return null;
    }

    /**
     * @param expression
     * @return Short
     */
    @Override
    public Short getShort(String expression) {
        Object value = this.getValue(expression);

        if(value instanceof Number) {
            return ((Number)value).shortValue();
        }
        return null;
    }

    /**
     * @param expression
     * @return Integer
     */
    @Override
    public Integer getInteger(String expression) {
        Object value = this.getValue(expression);

        if(value instanceof Number) {
            return ((Number)value).intValue();
        }
        return null;
    }

    /**
     * @param expression
     * @return Float
     */
    @Override
    public Float getFloat(String expression) {
        Object value = this.getValue(expression);

        if(value instanceof Number) {
            return ((Number)value).floatValue();
        }
        return null;
    }

    /**
     * @param expression
     * @return Double
     */
    @Override
    public Double getDouble(String expression) {
        Object value = this.getValue(expression);

        if(value instanceof Number) {
            return ((Number)value).doubleValue();
        }
        return null;
    }

    /**
     * @param expression
     * @return Long
     */
    @Override
    public Long getLong(String expression) {
        Object value = this.getValue(expression);

        if(value instanceof Number) {
            return ((Number)value).longValue();
        }
        return null;
    }

    /**
     * @param expression
     * @return Character
     */
    @Override
    public Character getCharacter(String expression) {
        Object value = this.getValue(expression);

        if(value instanceof Character) {
            return (Character)value;
        }

        if(value != null) {
            String content = value.toString();

            if(content.length() > 0) {
                return content.charAt(0);
            }
        }
        return null;
    }

    /**
     * @param expression
     * @return Object
     */
    @Override
    public String getString(String expression) {
        Object value = this.getValue(expression);

        if(value == null || value instanceof Empty<?, ?>) {
            return "";
        }
        return value.toString();
    }

    /**
     * @param expression
     * @return Object
     */
    @Override
    public String getEscapeString(String expression) {
        Object value = this.getValue(expression);

        if(value == null || value instanceof Empty<?, ?>) {
            return "";
        }
        return HtmlUtil.encode(value.toString());
    }

    /**
     * @param out
     * @param b
     * @throws IOException
     */
    @Override
    public void print(Writer out, boolean b) throws IOException {
        out.write((b ? "true": "false"));
    }

    /**
     * @param out
     * @param c
     * @throws IOException
     */
    @Override
    public void print(Writer out, char c) throws IOException {
        out.write(new char[]{c}, 0, 1);
    }

    /**
     * @param out
     * @param b
     * @throws IOException
     */
    @Override
    public void print(Writer out, byte b) throws IOException {
        out.write(Integer.toString(b, 10));
    }

    /**
     * @param out
     * @param s
     * @throws IOException
     */
    @Override
    public void print(Writer out, short s) throws IOException {
        out.write(Integer.toString(s, 10));
    }

    /**
     * @param out
     * @param i
     * @throws IOException
     */
    @Override
    public void print(Writer out, int i) throws IOException {
        out.write(Integer.toString(i));
    }

    /**
     * @param out
     * @param f
     * @throws IOException
     */
    @Override
    public void print(Writer out, float f) throws IOException {
        out.write(Float.toString(f));
    }

    /**
     * @param out
     * @param d
     * @throws IOException
     */
    @Override
    public void print(Writer out, double d) throws IOException {
        out.write(Double.toString(d));
    }

    /**
     * @param out
     * @param l
     * @throws IOException
     */
    @Override
    public void print(Writer out, long l) throws IOException {
        out.write(Long.toString(l));
    }

    /**
     * @param content
     * @throws IOException
     */
    @Override
    public void print(Writer out, String content) throws IOException {
        if(content != null) {
            if(this.encoder != null) {
                out.write(this.encoder.encode(content));
            }
            else {
                out.write(content);
            }
        }
    }

    /**
     * @param out
     * @param content
     * @throws IOException
     */
    @Override
    public void print(Writer out, Object content) throws IOException {
        if(content != null) {
            if(content instanceof Number || content instanceof Boolean) {
                out.write(content.toString());
            }
            else {
                if(this.encoder != null) {
                    out.write(this.encoder.encode(content.toString()));
                }
                else {
                    out.write(content.toString());
                }
            }
        }
    }

    /**
     * @return the pageContext
     */
    public PageContext getPageContext() {
        return this.pageContext;
    }

    /**
     * @param pageContext the pageContext to set
     */
    public void setPageContext(PageContext pageContext) {
        this.pageContext = pageContext;
    }

    /**
     * @return ELEncoder
     */
    public ELEncoder getEncoder() {
        return this.encoder;
    }

    /**
     * @param encoder
     */
    public void setEncoder(ELEncoder encoder) {
        this.encoder = encoder;
    }

    /**
     * release
     */
    public void release() {
        this.pageContext = null;
    }
}
