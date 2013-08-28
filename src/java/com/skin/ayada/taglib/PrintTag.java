/*
 * $RCSfile: PrintTag.java,v $$
 * $Revision: 1.1  $
 * $Date: 2011-12-9  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.taglib;

import com.skin.ayada.tagext.TagSupport;

/**
 * <p>Title: PrintTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class PrintTag extends TagSupport
{
    private static final long serialVersionUID = 3337947213732345725L;
    private Object value;

    @Override
    public int doEndTag()
    {
        System.out.println(this.value);
        return TagSupport.EVAL_PAGE;
    }

    /**
     * @return the value
     */
    public Object getValue()
    {
        return this.value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Object value)
    {
        this.value = value;
    }
}