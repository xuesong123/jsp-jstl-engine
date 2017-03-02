/*
 * $RCSfile: JspAttributeTag.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-11-20 $
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
import com.skin.ayada.tagext.DynamicAttributes;
import com.skin.ayada.tagext.Tag;

/**
 * <p>Title: JspAttributeTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class JspAttributeTag extends BodyTagSupport {
    private String name;
    private Object value = null;

    /**
     * @return int
     * @throws Exception
     */
    @Override
    public int doStartTag() throws Exception {
        if(this.value != null) {
            this.setDynamicAttribute(this.name, this.value);
            return Tag.SKIP_BODY;
        }
        return BodyTag.EVAL_BODY_BUFFERED;
    }

    /**
     * @return int
     * @throws Exception
     */
    @Override
    public int doEndTag() throws Exception {
        BodyContent bodyContent = this.getBodyContent();

        if(bodyContent != null) {
            this.setDynamicAttribute(this.name, bodyContent.getString().trim());
        }
        return EVAL_PAGE;
    }

    /**
     * @param name
     * @param value
     */
    public void setDynamicAttribute(String name, Object value) {
        Tag parent = this.getParent();

        if(parent instanceof DynamicAttributes) {
            ((DynamicAttributes)parent).setDynamicAttribute(name, value);
        }
        else {
            throw new RuntimeException("Illegal use of parameter-style tag without servlet as its direct parent");
        }
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the value
     */
    public Object getValue() {
        return this.value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Object value) {
        this.value = value;
    }
}
