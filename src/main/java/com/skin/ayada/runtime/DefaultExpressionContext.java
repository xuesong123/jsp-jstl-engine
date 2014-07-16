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

import ognl.OgnlContext;

import com.skin.ayada.ognl.util.Empty;
import com.skin.ayada.ognl.util.OgnlUtil;

/**
 * <p>Title: DefaultExpressionContext</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class DefaultExpressionContext extends OgnlContext implements ExpressionContext
{
    private PageContext pageContext;
    private static final Object EMPTY = new Empty<String, Object>();

    /**
     * @param pageContext
     */
    protected DefaultExpressionContext(PageContext pageContext)
    {
        this.pageContext = pageContext;
    }

    @Override
    public Object put(Object key, Object value)
    {
        return this.pageContext.setAttribute(key.toString(), value);
    }

    @Override
    public Object get(Object key)
    {
        if(key == null)
        {
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
    public Object getValue(String expression)
    {
        return OgnlUtil.getValue(expression, this, this);
    }

    /**
     * @param expression
     * @return boolean
     */
    @Override
    public boolean getBoolean(String expression)
    {
        Object value = OgnlUtil.getValue(expression, this, this);

        if(value instanceof Boolean)
        {
            return Boolean.TRUE.equals(value);
        }
        return false;
    }

    /**
     * @param expression
     * @return Byte
     */
    public Byte getByte(String expression)
    {
        Object value = OgnlUtil.getValue(expression, this, this);

        if(value instanceof Number)
        {
            return ((Number)value).byteValue();
        }

        return null;
    }

    /**
     * @param expression
     * @return Short
     */
    @Override
    public Short getShort(String expression)
    {
        Object value = OgnlUtil.getValue(expression, this, this);

        if(value instanceof Number)
        {
            return ((Number)value).shortValue();
        }

        return null;
    }

    /**
     * @param expression
     * @return Integer
     */
    @Override
    public Integer getInteger(String expression)
    {
        Object value = OgnlUtil.getValue(expression, this, this);

        if(value instanceof Number)
        {
            return ((Number)value).intValue();
        }

        return null;
    }

    /**
     * @param expression
     * @return Float
     */
    @Override
    public Float getFloat(String expression)
    {
        Object value = OgnlUtil.getValue(expression, this, this);

        if(value instanceof Number)
        {
            return ((Number)value).floatValue();
        }

        return null;
    }

    /**
     * @param expression
     * @return Double
     */
    @Override
    public Double getDouble(String expression)
    {
        Object value = OgnlUtil.getValue(expression, this, this);

        if(value instanceof Number)
        {
            return ((Number)value).doubleValue();
        }

        return null;
    }

    /**
     * @param expression
     * @return Long
     */
    @Override
    public Long getLong(String expression)
    {
        Object value = OgnlUtil.getValue(expression, this, this);

        if(value instanceof Number)
        {
            return ((Number)value).longValue();
        }

        return null;
    }

    /**
     * @param expression
     * @return Character
     */
    @Override
    public Character getCharacter(String expression)
    {
        Object value = OgnlUtil.getValue(expression, this, this);

        if(value instanceof Character)
        {
            return (Character)value;
        }

        if(value != null)
        {
            String content = value.toString();

            if(content.length() > 0)
            {
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
    public String getString(String expression)
    {
        Object value = OgnlUtil.getValue(expression, this, this);

        if(value == null || value instanceof Empty<?, ?>)
        {
            return "";
        }
        return value.toString();
    }

    /**
     * @return the pageContext
     */
    public PageContext getPageContext()
    {
        return this.pageContext;
    }

    /**
     * @param pageContext the pageContext to set
     */
    public void setPageContext(PageContext pageContext)
    {
        this.pageContext = pageContext;
    }

    public void release()
    {
        this.pageContext = null;
    }
}
