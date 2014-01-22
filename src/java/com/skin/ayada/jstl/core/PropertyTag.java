/*
 * $RCSfile: PropertyTag.java,v $$
 * $Revision: 1.1  $
 * $Date: 2011-12-9  $
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
import com.skin.ayada.tagext.PropertyTagSupport;
import com.skin.ayada.tagext.Tag;

/**
 * <p>Title: PropertyTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class PropertyTag extends BodyTagSupport
{
    private static final long serialVersionUID = 3337947213732345725L;
    private String name;
    private Object value;

    @Override
    public int doStartTag()
    {
        if(this.value != null)
        {
            this.setAttribute(this.name, this.value);
            return Tag.SKIP_BODY;
        }

        return BodyTag.EVAL_BODY_BUFFERED;
    }

    @Override
    public int doEndTag()
    {
        if(this.value == null)
        {
            BodyContent body = this.getBodyContent();
            this.setAttribute(this.name, (body != null ? body.getString() : null));
        }

        return Tag.EVAL_PAGE;
    }

    /**
     * @param name
     * @param value
     */
    protected void setAttribute(String name, Object value)
    {
        Tag parent = this.getParent();

        if(parent instanceof PropertyTagSupport)
        {
            PropertyTagSupport tag = (PropertyTagSupport)(parent);
            tag.setProperty(name, value);
        }
        else
        {
            throw new RuntimeException("Illegal use of parameter-style tag without servlet as its direct parent: parent tag is not a PropertySupportTag !");
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