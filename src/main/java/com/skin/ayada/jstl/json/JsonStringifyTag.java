/*
 * $RCSfile: JsonStringifyTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2016-2-10 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.json;

import com.skin.ayada.jstl.core.OutTag;
import com.skin.ayada.runtime.Json;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TagSupport;

/**
 * <p>Title: JsonStringifyTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class JsonStringifyTag extends TagSupport {
    private Object value = null;
    private String defaultValue = null;
    private boolean escapeXml = false;

    /**
     * @return int
     * @throws Exception
     */
    @Override
    public int doStartTag() throws Exception {
        if(this.value != null) {
            Json json = (Json)(this.pageContext.getAttribute("jsonSupport"));

            if(json != null) {
                OutTag.print(this.pageContext, json.stringify(this.value), this.escapeXml);
            }
            return Tag.SKIP_BODY;
        }

        if(this.defaultValue != null) {
            OutTag.print(this.pageContext, this.defaultValue, this.escapeXml);
            return Tag.SKIP_BODY;
        }
        return EVAL_PAGE;
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
     * @return boolean
     */
    public boolean getEscapeXml() {
        return this.escapeXml;
    }

    /**
     * @param escapeXml
     */
    public void setEscapeXml(boolean escapeXml) {
        this.escapeXml = escapeXml;
    }
}
