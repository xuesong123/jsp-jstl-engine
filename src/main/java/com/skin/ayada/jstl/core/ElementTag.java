/*
 * $RCSfile: AttributeTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-12-13 $
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
import com.skin.ayada.tagext.ElementTagSupport;
import com.skin.ayada.tagext.Tag;

/**
 * <p>Title: AttributeTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class ElementTag extends BodyTagSupport {
    private int index = -1;
    private Object value;

    @Override
    public int doStartTag() {
        if(this.value != null) {
            this.setElement(this.index, this.value);
            return Tag.SKIP_BODY;
        }

        return BodyTag.EVAL_BODY_BUFFERED;
    }

    @Override
    public int doEndTag() {
        if(this.value == null) {
            BodyContent body = this.getBodyContent();
            this.setElement(this.index, (body != null ? body.getString() : null));
        }

        return Tag.EVAL_PAGE;
    }

    @Override
    public void release() {
        this.index = -1;
        this.value = null;
    }

    /**
     * @param index
     * @param value
     */
    protected void setElement(int index, Object value) {
        Tag parent = this.getParent();

        if(parent instanceof ElementTagSupport) {
            ElementTagSupport tag = (ElementTagSupport)(parent);
            tag.setElement(index, value);
        }
        else {
            throw new RuntimeException("Illegal use of parameter-style tag without servlet as its direct parent");
        }
    }

    /**
     * @return the index
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(int index) {
        this.index = index;
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