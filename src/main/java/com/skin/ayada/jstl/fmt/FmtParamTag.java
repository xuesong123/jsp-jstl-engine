/*
 * $RCSfile: FmtParamTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2014-04-11 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.fmt;

import com.skin.ayada.tagext.BodyTagSupport;
import com.skin.ayada.tagext.ParamContainerTag;
import com.skin.ayada.tagext.Tag;

/**
 * <p>Title: FmtParamTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class FmtParamTag extends BodyTagSupport {
    private Object value;
    private boolean hasValue;

    @Override
    public int doEndTag() throws Exception {
        Object value = null;

        if(this.hasValue) {
            value = this.value;
        }
        else {
            value = this.bodyContent.getString().trim();
        }

        Tag parent = this.getParent();

        if(parent instanceof ParamContainerTag) {
            ParamContainerTag paramContainerTag = (ParamContainerTag)parent;
            paramContainerTag.addParam(value);
            return Tag.EVAL_PAGE;
        }
        else {
            throw new Exception("fmt:param requires fmt:message parent.");
        }
    }

    /**
     * @param value the value to set
     */
    public void setValue(Object value) {
        this.value = value;
        this.hasValue = true;
    }

    /**
     * @return the value
     */
    public Object getValue() {
        return this.value;
    }
}
