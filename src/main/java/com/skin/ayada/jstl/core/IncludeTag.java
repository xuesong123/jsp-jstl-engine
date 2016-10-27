/*
 * $RCSfile: IncludeTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-26 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import java.util.HashMap;
import java.util.Map;

import com.skin.ayada.tagext.ParameterTagSupport;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TagSupport;

/**
 * <p>Title: IncludeTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class IncludeTag extends TagSupport implements ParameterTagSupport {
    private String page;
    private Map<String, Object> parameters;

    /**
     * @return int
     */
    @Override
    public int doStartTag() throws Exception {
        super.doStartTag();
        this.parameters = new HashMap<String, Object>();

        if(this.page == null) {
            return Tag.SKIP_BODY;
        }
        else {
            return Tag.EVAL_BODY_INCLUDE;
        }
    }

    /**
     * @return int
     */
    @Override
    public int doEndTag() throws Exception {
        if(this.getPage() != null) {
            this.pageContext.include(this.getPage(), this.parameters);
        }
        return Tag.EVAL_PAGE;
    }

    /**
     * @param name
     * @param value
     */
    @Override
    public void setParameter(String name, Object value) {
        this.parameters.put(name, value);
    }

    /**
     * @param page the page to set
     */
    public void setPage(String page) {
        this.page = page;
    }

    /**
     * @return the page
     */
    public String getPage() {
        return this.page;
    }
}
