/*
 * $RCSfile: MessageTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-28 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.fmt;

import java.util.ArrayList;
import java.util.List;

import com.skin.ayada.tagext.BodyTagSupport;
import com.skin.ayada.tagext.ParamContainerTag;
import com.skin.ayada.tagext.Tag;

/**
 * <p>Title: MessageTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class MessageTag extends BodyTagSupport implements ParamContainerTag {
    private String key;
    private Object bundle;
    private List<Object> parameters;
    private String var;

    /**
     *
     */
    public MessageTag() {
    }

    /**
     * @param key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @param bundle
     */
    public void setBundle(Object bundle) {
        this.bundle = bundle;
    }

    /**
     * @param var
     */
    public void setVar(String var) {
        this.var = var;
    }

    @Override
    public void addParam(Object value) {
        if(this.parameters == null) {
            this.parameters = new ArrayList<Object>();
        }
        this.parameters.add(value);
    }

    @Override
    public int doEndTag() throws Exception {
        Object[] args = null;

        if(this.parameters != null) {
            args = this.parameters.toArray(new Object[this.parameters.size()]);
            this.parameters = null;
        }

        String message = null;
        LocalizationContext localizationContext = null;

        if(this.bundle != null) {
            if(this.bundle instanceof LocalizationContext) {
                localizationContext = (LocalizationContext)(this.bundle);
            }
            else if(this.bundle instanceof String) {
                localizationContext = BundleTag.getBundle(this.pageContext, (String)(this.bundle));
            }
        }
        else {
            localizationContext = this.pageContext.getBundle();
        }

        if(localizationContext == null) {
            throw new Exception("[key: " + this.key + "]localizationContext not found !");
        }

        message = this.pageContext.getLocalizedMessage(localizationContext, this.key, args);

        if(this.var != null) {
            this.pageContext.setAttribute(this.var, message);
        }
        else {
            this.pageContext.getOut().print(message);
        }
        return Tag.EVAL_PAGE;
    }

    @Override
    public void release() {
        this.key = null;
        this.var = null;
        this.bundle = null;

        if(this.parameters != null) {
            this.parameters.clear();
        }
    }
}
