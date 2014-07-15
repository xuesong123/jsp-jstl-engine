/*
 * $RCSfile: BufferTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2014-02-19 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import com.skin.ayada.tagext.BodyContent;
import com.skin.ayada.tagext.BodyTag;
import com.skin.ayada.tagext.BodyTagSupport;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.util.ClassUtil;

/**
 * <p>Title: BufferTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class BufferTag extends BodyTagSupport
{
    private String var;

    /**
     * @return int
     */
    @Override
    public int doStartTag() throws Exception
    {
        if(this.var != null)
        {
            return BodyTag.EVAL_BODY_BUFFERED;
        }

        return Tag.SKIP_BODY;
    }

    /**
     * @return int
     */
    @Override
    public int doEndTag() throws Exception
    {
        if(this.var != null)
        {
            BodyContent bodyContent = this.getBodyContent();

            if(bodyContent != null)
            {
                this.pageContext.setAttribute(this.var, bodyContent.getString());
            }
            else
            {
                this.pageContext.setAttribute(this.var, "");
            }
        }

        return Tag.EVAL_PAGE;
    }

    /**
     * @param name
     * @param value
     */
    public void setValue(String name, Object value)
    {
        this.pageContext.setAttribute(name, value);
    }

    /**
     * @param name
     * @param value
     * @throws Exception 
     */
    public void setProperty(Object object, String name, Object value) throws Exception
    {
        ClassUtil.setProperty(object, name, value);
    }

    /**
     * @param var the var to set
     */
    public void setVar(String var)
    {
        this.var = var;
    }

    /**
     * @return the var
     */
    public String getVar()
    {
        return this.var;
    }
}
