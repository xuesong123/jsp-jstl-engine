/*
 * $RCSfile: ExpressionContext.java,v $$
 * $Revision: 1.1  $
 * $Date: 2013-2-20  $
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
 * <p>Title: ExpressionContext</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class ExpressionContext extends OgnlContext
{
    private PageContext pageContext;
    private static final Object EMPTY = new Empty<String, Object>();

    /**
     * @param pageContext
     */
    protected ExpressionContext(PageContext pageContext)
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
    public Object getValue(String expression)
    {
        return OgnlUtil.getValue(expression, this, this);
    }

    /**
     * @param expression
     * @return Object
     */
    public boolean getBoolean(String expression)
    {
        Object value = OgnlUtil.getValue(expression, this, this);

        if(value == null)
        {
            return false;
        }

        if(value instanceof Boolean)
        {
            return Boolean.TRUE.equals(value);
        }
        else
        {
            return false;
        }
    }

    /**
     * @param expression
     * @return Object
     */
    public boolean getInteger(String expression)
    {
        Object value = OgnlUtil.getValue(expression, this, this);

        if(value == null)
        {
            return false;
        }

        if(value instanceof Integer)
        {
            return Boolean.TRUE.equals(value);
        }
        else
        {
            return false;
        }
    }

    /**
     * @param expression
     * @return Object
     */
    public String getString(String expression)
    {
        Object value = OgnlUtil.getValue(expression, this, this);

        if(value == null || value instanceof Empty<?, ?>)
        {
            return "";
        }
        else
        {
            return value.toString();
        }
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
