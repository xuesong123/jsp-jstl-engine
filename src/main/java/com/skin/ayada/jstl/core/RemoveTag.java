/*
 * $RCSfile: RemoveTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2014-04-11 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import com.skin.ayada.tagext.BodyTagSupport;
import com.skin.ayada.tagext.Tag;

/**
 * <p>Title: RemoveTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class RemoveTag extends BodyTagSupport {
    private String var;
    private String scope;

    /**
     * @return int
     */
    @Override
    public int doStartTag() throws Exception {
        if(this.var != null) {
            this.pageContext.removeAttribute(this.var);
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
     * @param scope the scope to set
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * @return the var
     */
    public String getScope() {
        return this.scope;
    }
}
