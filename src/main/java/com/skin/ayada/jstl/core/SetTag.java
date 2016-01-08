/*
 * $RCSfile: SetTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-19 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import java.util.Map;

import com.skin.ayada.runtime.PageContext;
import com.skin.ayada.tagext.BodyContent;
import com.skin.ayada.tagext.BodyTag;
import com.skin.ayada.tagext.BodyTagSupport;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.util.ClassUtil;

/**
 * <p>Title: SetTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class SetTag extends BodyTagSupport {
    private String var;
    private String property;
    private Object target;
    private Object value;

    /**
     * @return int
     */
    @Override
    public int doStartTag() throws Exception {
        if(this.value == null) {
            return BodyTag.EVAL_BODY_BUFFERED;
        }

        if(this.var != null) {
            this.setValue(this.var, this.value);
        }
        else {
            if(this.target != null && this.property != null) {
                this.setProperty(this.target, this.property, this.value);
            }
        }
        return Tag.SKIP_BODY;
    }

    /**
     * @return int
     */
    @Override
    public int doEndTag() throws Exception {
        BodyContent bodyContent = this.getBodyContent();

        if(bodyContent != null) {
            String value = bodyContent.getString().trim();

            if(this.var != null) {
                this.setValue(this.var, value);
            }
            else {
                if(this.target != null && this.property != null) {
                    this.setProperty(this.target, this.property, value);
                }
            }
        }
        return Tag.EVAL_PAGE;
    }

    /**
     * @param name
     * @param value
     */
    public void setValue(String name, Object value) {
        this.pageContext.setAttribute(name, value);
    }

    /**
     * @param name
     * @param value
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void setProperty(Object object, String name, Object value) throws Exception {
        if(object instanceof Map<?, ?>) {
            Map<Object, Object> map = (Map<Object, Object>)(object);
            map.put(value, value);
        }
        else {
            ClassUtil.setProperty(object, name, value);
        }
    }

    /**
     * @param pageContext
     * @param name
     * @param scope
     * @param value
     */
    public static void setValue(PageContext pageContext, String name, String scope, Object value) {
        pageContext.setAttribute(name, value);
    }

    /**
     * @param var the var to set
     */
    public void setVar(String var) {
        this.var = var;
    }

    /**
     * @return the var
     */
    public String getVar() {
        return this.var;
    }

    /**
     * @param property the property to set
     */
    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * @return the property
     */
    public String getProperty() {
        return this.property;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(Object target) {
        this.target = target;
    }

    /**
     * @return the target
     */
    public Object getTarget() {
        return this.target;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * @return the value
     */
    public Object getValue() {
        return this.value;
    }
}
