/*
 * $RCSfile: ParseJsonTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2016-2-10 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.json;

import com.skin.ayada.runtime.Json;
import com.skin.ayada.tagext.BodyContent;
import com.skin.ayada.tagext.BodyTag;
import com.skin.ayada.tagext.BodyTagSupport;
import com.skin.ayada.tagext.Tag;

/**
 * <p>Title: ParseJsonTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class ParseJsonTag extends BodyTagSupport {
    private String var;

    @Override
    public int doStartTag() {
        if(this.var == null || this.var.trim().length() < 1) {
            return SKIP_BODY;
        }
        else {
            return BodyTag.EVAL_BODY_BUFFERED;
        }
    }

    @Override
    public int doEndTag() {
        Json json = (Json)(this.pageContext.getAttribute("jsonSupport"));
        BodyContent bodyContent = this.getBodyContent();

        if(bodyContent != null) {
            String content = bodyContent.getString();
            
            if(json != null) {
                Object value = json.parse(content);
                this.pageContext.setAttribute(this.var, value);
            }
        }
        return Tag.EVAL_PAGE;
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
}
