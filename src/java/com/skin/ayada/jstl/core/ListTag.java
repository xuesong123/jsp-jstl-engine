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
import com.skin.ayada.tagext.ElementTagSupport;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TagSupport;

/**
 * <p>Title: ListTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class ListTag extends TagSupport implements ElementTagSupport
{
    private String name;
    private List<Object> list;

    @Override
    public int doStartTag() throws Exception
    {
        super.doStartTag();

        if(this.list == null)
        {
            this.list = new ArrayList<Object>();
        }

        Tag parent = this.getParent();

        if(parent instanceof AttributeTagSupport)
        {
            if(this.name != null)
            {
                AttributeTagSupport tag = (AttributeTagSupport)(parent);
                tag.setAttribute(this.name, this.list);
                return Tag.EVAL_BODY_INCLUDE;
            }
            return Tag.SKIP_BODY;
        }
        else if(parent instanceof ElementTagSupport)
        {
            ElementTagSupport tag = (ElementTagSupport)(parent);
            tag.addElement(this.list);
            return Tag.EVAL_BODY_INCLUDE;
        }

        if(this.name != null)
        {
            this.pageContext.setAttribute(this.name, this.list);
            return Tag.EVAL_BODY_INCLUDE;
        }

        return Tag.SKIP_BODY;
    }

    /**
     * @param value
     */
    @Override
    public void addElement(Object value)
    {
        this.list.add(value);
    }

    /**
     * @param index
     * @param value
     */
    @Override
    public void setElement(int index, Object value)
    {
        if(index >= 0 && index < this.list.size())
        {
            this.list.set(index, value);
        }
        else
        {
        	this.list.add(value);
        }
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
