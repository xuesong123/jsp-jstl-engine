/*
 * $RCSfile: SetLocaleTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-28 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.fmt;

import java.util.Locale;

import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TagSupport;

/**
 * javax.servlet.jsp.jstl.fmt.locale
 * <p>Title: SetLocaleTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class SetLocaleTag extends TagSupport {
    private Object value;
    private String variant;
    private String scope;

    /**
     * doStartTag
     */
    @Override
    public int doStartTag() throws Exception {
        Locale locale = null;

        if(this.value instanceof Locale) {
            locale = (Locale)this.value;
        }
        else if(this.value instanceof String) {
            locale = this.pageContext.getLocale((String)(this.value), this.variant);
        }

        this.pageContext.setLocale(locale);
        return Tag.SKIP_BODY;
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

    /**
     * @return the variant
     */
    public String getVariant() {
        return this.variant;
    }

    /**
     * @param variant the variant to set
     */
    public void setVariant(String variant) {
        this.variant = variant;
    }

    /**
     * @return the scope
     */
    public String getScope() {
        return this.scope;
    }

    /**
     * @param scope the scope to set
     */
    public void setScope(String scope) {
        this.scope = scope;
    }
}
