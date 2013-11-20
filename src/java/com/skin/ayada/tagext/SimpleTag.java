/*
 * $RCSfile: SimpleTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-20 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.tagext;

/**
 * <p>Title: SimpleTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public abstract class SimpleTag implements Tag
{
    /**
     * @return int
     */
    public int doStartTag()
    {
        throw new UnsupportedOperationException("doStartTag unsupported !");
    }

    /**
     * @return int
     */
    @Override
    public int doEndTag()
    {
        throw new UnsupportedOperationException("doStartTag unsupported !");
    }

    public abstract void doTag();

    /**
     * @param jspBody
     */
    public abstract void setPageBody(JspFragment jspBody);
}
