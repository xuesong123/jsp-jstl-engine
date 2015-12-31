/*
 * $RCSfile: SetTimeZoneTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-28 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.fmt;

import java.util.TimeZone;

import com.skin.ayada.jstl.core.SetTag;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TagSupport;

/**
 * <p>Title: SetTimeZoneTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class SetTimeZoneTag extends TagSupport {
    private String var;
    private Object value;
    private String scope;

    @Override
    public int doStartTag() throws Exception {
        TimeZone timeZone = null;

        if(this.value instanceof TimeZone) {
            timeZone = (TimeZone)this.value;
        }
        else if(this.value instanceof String) {
            String string = ((String)this.value).trim();

            if(string.length() < 1) {
                timeZone = TimeZone.getTimeZone("GMT");
            }
            else {
                timeZone = TimeZone.getTimeZone(string);
            }
        }
        else {
            timeZone = TimeZone.getTimeZone("GMT");
        }

        if(this.var != null) {
            SetTag.setValue(this.pageContext, this.var, this.scope, timeZone);
        }
        else {
            this.pageContext.setTimeZone(timeZone);
        }
        return Tag.SKIP_BODY;
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

    /**
     * @param scope the scope to set
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * @return the scope
     */
    public String getScope() {
        return this.scope;
    }
}
