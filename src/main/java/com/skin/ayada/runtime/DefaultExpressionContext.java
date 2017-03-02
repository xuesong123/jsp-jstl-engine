/*
 * $RCSfile: DefaultExpressionContext.java,v $
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
import java.util.Map;

import com.skin.ayada.Encoder;
import com.skin.ayada.ExpressionContext;
import com.skin.ayada.util.ClassUtil;
import com.skin.ayada.util.MVELUtil;

/**
 * <p>Title: DefaultExpressionContext</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class DefaultExpressionContext implements ExpressionContext {
    protected Encoder encoder;
    protected Map<String, Object> context;

    /**
     * @param context
     */
    protected DefaultExpressionContext(Map<String, Object> context) {
        this.context = context;
    }

    /**
     * @param expression
     * @return Object
     */
    @Override
    public Object getValue(String expression) {
        try {
            return this.evaluate(expression);
        }
        catch(Exception e) {
            this.error(e);
        }
        return null;
    }

    /**
     * @param expression
     * @param resultType
     * @return Object
     */
    @Override
    public Object getValue(String expression, Class<?> resultType) {
        Object value = this.getValue(expression);

        if(value == null) {
            return null;
        }

        if(resultType != null) {
            if(value instanceof String) {
                String text = (String)value;

                if(resultType == String.class) {
                    return text;
                }
                else if(resultType == char.class || resultType == Character.class) {
                    return text;
                }
                else if(resultType == boolean.class || resultType == Boolean.class) {
                    return text.equals("true");
                }
                else if(resultType == byte.class || resultType == Byte.class) {
                    return getValue(text);
                }
                else if(resultType == short.class || resultType == Short.class) {
                    return getValue(text);
                }
                else if(resultType == int.class || resultType == Integer.class) {
                    return getValue(text);
                }
                else if(resultType == float.class || resultType == Float.class) {
                    return getValue(text);
                }
                else if(resultType == double.class || resultType == Double.class) {
                    return getValue(text);
                }
                else if(resultType == long.class || resultType == Long.class) {
                    return getValue(text);
                }
            }
            return ClassUtil.cast(value, resultType);
        }
        return value;
    }

    /**
     * @param expression
     * @return Object
     * @throws Exception
     */
    @Override
    public Object evaluate(String expression) throws Exception {
        return MVELUtil.getValue(expression, this.getContext());
    }

    /**
     * @param name
     * @return Object
     */
    @Override
    public Object getAttribute(String name) {
        return this.context.get(name);
    }

    /**
     * @param name
     * @param value
     */
    @Override
    public void setAttribute(String name, Object value) {
        this.context.put(name, value);
    }

    /**
     * @return the Map<String, Object>
     */
    @Override
    public Map<String, Object> getContext() {
        return this.context;
    }

    /**
     * @param context the context to set
     */
    @Override
    public void setContext(Map<String, Object> context) {
        this.context = context;
    }

    /**
     * @param expression
     * @return boolean
     */
    @Override
    public boolean getBoolean(String expression) {
        Object value = this.getValue(expression);

        if(value == null) {
            return false;
        }

        if(value instanceof Boolean) {
            return ((Boolean)value).booleanValue();
        }
        return (value.toString().equals("true"));
    }

    /**
     * @param expression
     * @return Byte
     */
    @Override
    public Byte getByte(String expression) {
        Object value = this.getValue(expression);

        if(value == null) {
            return null;
        }

        if(value instanceof Number) {
            return ((Number)value).byteValue();
        }
        return ClassUtil.cast(value, Byte.class);
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
        return ClassUtil.cast(value, Short.class);
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
        return ClassUtil.cast(value, Integer.class);
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
        return ClassUtil.cast(value, Float.class);
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
        return ClassUtil.cast(value, Double.class);
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
        return ClassUtil.cast(value, Long.class);
    }

    /**
     * @param expression
     * @return Character
     */
    @Override
    public Character getCharacter(String expression) {
        Object value = this.getValue(expression);

        if(value == null) {
            return null;
        }

        if(value instanceof Character) {
            return (Character)value;
        }

        String content = value.toString();

        if(content.length() > 0) {
            return content.charAt(0);
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

        if(value == null) {
            return "";
        }
        return value.toString();
    }

    /**
     * @param expression
     * @return Object
     */
    @Override
    public String getEncodeString(String expression) {
        Object value = this.getValue(expression);

        if(value == null) {
            return "";
        }

        if(this.encoder == null || value instanceof Number || value instanceof Boolean) {
            return value.toString();
        }
        else {
            return this.encoder.encode(value.toString());
        }
    }

    /**
     * @param out
     * @param b
     * @throws IOException
     */
    @Override
    public void print(Writer out, boolean b) throws IOException {
        if(b) {
            out.write("true", 0, 4);
        }
        else {
            out.write("false", 0, 5);
        }
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
                out.write(content, 0, content.length());
            }
        }
    }

    /**
     * @param out
     * @param object
     * @throws IOException
     */
    @Override
    public void print(Writer out, Object object) throws IOException {
        if(object != null) {
            String content = object.toString();

            if(object instanceof Number || object instanceof Boolean) {
                out.write(content, 0, content.length());
            }
            else {
                if(this.encoder != null) {
                    content = this.encoder.encode(content);
                }
                out.write(content, 0, content.length());
            }
        }
    }

    /**
     * @param e
     */
    public void error(Exception e) {
        boolean flag = true;
        Object ignoreElException = this.getAttribute("ignoreElException");

        if(ignoreElException != null) {
            flag = "true".equals(ignoreElException.toString());
        }

        if(!flag) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return Encoder
     */
    @Override
    public Encoder getEncoder() {
        return this.encoder;
    }

    /**
     * @param encoder
     */
    @Override
    public void setEncoder(Encoder encoder) {
        this.encoder = encoder;
    }

    /**
     * release
     */
    @Override
    public void release() {
        this.context = null;
    }
}
