/*
 * $RCSfile: ListTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-26 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import java.util.ArrayList;
import java.util.List;

import com.skin.ayada.tagext.AttributeTagSupport;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TagSupport;

/**
 * <p>Title: ListTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class ListTag extends TagSupport implements AttributeTagSupport
{
    private String name;
    private List<Object> list;

    @Override
    public int doStartTag() throws Exception
    {
        super.doStartTag();

        if(this.name == null)
        {
            return Tag.SKIP_BODY;
        }

        this.list = new ArrayList<Object>();
        this.pageContext.setAttribute(this.name, this.list);
        return TagSupport.EVAL_BODY_INCLUDE;
    }

    /**
     * @param name
     * @param value
     */
    @Override
    public void setAttribute(String name, Object value)
    {
        this.list.add(value);
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }
}
