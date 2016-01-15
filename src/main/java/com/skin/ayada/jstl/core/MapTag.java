/*
 * $RCSfile: MapTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-26 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import java.util.LinkedHashMap;
import java.util.Map;

import com.skin.ayada.tagext.AttributeTagSupport;
import com.skin.ayada.tagext.DynamicAttributes;
import com.skin.ayada.tagext.ElementTagSupport;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TagSupport;

/**
 * <p>Title: MapTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class MapTag extends TagSupport implements AttributeTagSupport, DynamicAttributes {
    private String name;
    private Map<String, Object> map;

    @Override
    public int doStartTag() throws Exception {
        super.doStartTag();

        if(this.map == null) {
            this.map = new LinkedHashMap<String, Object>();
        }

        Tag parent = this.getParent();

        if(parent instanceof AttributeTagSupport) {
            if(this.name != null) {
                AttributeTagSupport tag = (AttributeTagSupport)(parent);
                tag.setAttribute(this.name, this.map);
                return Tag.EVAL_BODY_INCLUDE;
            }
            return Tag.SKIP_BODY;
        }
        else if(parent instanceof ElementTagSupport) {
            ElementTagSupport tag = (ElementTagSupport)(parent);
            tag.addElement(this.map);
            return Tag.EVAL_BODY_INCLUDE;
        }

        if(this.name != null) {
            this.pageContext.setAttribute(this.name, this.map);
            return Tag.EVAL_BODY_INCLUDE;
        }
        return Tag.SKIP_BODY;
    }

    @Override
    public void release() {
        this.map = null;
    }

    /**
     * @param name
     * @param value
     */
    @Override
    public void setAttribute(String name, Object value) {
        if(this.map == null) {
            this.map = new LinkedHashMap<String, Object>();
        }

        this.map.put(name, value);
    }

    /**
     * @param name
     * @param value
     */
    public void setDynamicAttribute(String name, Object value) {
        if(name != null) {
            if(this.map == null) {
                this.map = new LinkedHashMap<String, Object>();
            }

            if(name.equals("name")) {
                if(value != null) {
                    this.name = value.toString();
                }
            }
            else {
                this.map.put(name, value);
            }
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
}
