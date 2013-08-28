/*
 * $RCSfile: SetTag.java,v $$
 * $Revision: 1.1  $
 * $Date: 2013-2-19  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import com.skin.ayada.tagext.TagSupport;

/**
 * <p>Title: SetTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class SetTag extends TagSupport
{
    private String var;
    private Object value;

    /**
     * @return int
     */
    @Override
    public int doEndTag()
    {
        if(this.var != null)
        {
            pageContext.setAttribute(this.var, this.value);
        }

        return EVAL_PAGE;
    }

    /**
     * @return the var
     */
    public String getVar()
    {
        return var;
    }

    /**
     * @param var the var to set
     */
    public void setVar(String var)
    {
        this.var = var;
    }

    /**
     * @return the value
     */
    public Object getValue()
    {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Object value)
    {
        this.value = value;
    }
}
