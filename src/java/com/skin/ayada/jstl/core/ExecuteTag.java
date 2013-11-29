/*
 * $RCSfile: ExecuteTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-27 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TagSupport;

/**
 * <p>Title: ExecuteTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class ExecuteTag extends TagSupport
{
    public String var;
    public Object result;

    @Override
    public int doStartTag() throws Exception
    {
        if(this.var != null)
        {
            this.pageContext.setAttribute(this.var, this.result);
        }

        return Tag.SKIP_BODY;
    }

    /**
     * @return the var
     */
    public String getVar()
    {
        return this.var;
    }

    /**
     * @param var the var to set
     */
    public void setVar(String var)
    {
        this.var = var;
    }

    /**
     * @return the result
     */
    public Object getResult()
    {
        return this.result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(Object result)
    {
        this.result = result;
    }
}
