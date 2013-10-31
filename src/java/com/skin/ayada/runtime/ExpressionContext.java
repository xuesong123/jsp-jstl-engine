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

import java.util.HashMap;
import java.util.Map;
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
    private Map<String, Object> tools = new HashMap<String, Object>();
    private static final Object EMPTY = new Empty<String, Object>();

    /**
     * @param pageContext
     */
    protected ExpressionContext(PageContext pageContext)
    {
        this.pageContext = pageContext;
    }

    /**
     * @param expression
     * @return Object
     */
    public Object evaluate(String expression)
    {
        return OgnlUtil.getValue(expression, this, this);
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

    /**
     * @param name
     * @param tools
     */
    public void addTools(String name, Object tools)
    {
        this.tools.put(name, tools);
    }

    /**
     * @return the tools
     */
    public Map<String, Object> getTools()
    {
        return this.tools;
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

        if(value == null)
        {
            value = this.tools.get(key);
        }

        return (value != null ? value : EMPTY);
    }

    public void release()
    {
        this.pageContext = null;
        this.tools.clear();
    }
}
