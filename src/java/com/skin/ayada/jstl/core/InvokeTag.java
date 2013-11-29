/*
 * $RCSfile: InvokeTag.java,v $$
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

import com.skin.ayada.tagext.AttributeTagSupport;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TagSupport;

/**
 * <p>Title: InvokeTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class InvokeTag extends TagSupport implements AttributeTagSupport
{
    private String page;
    private Map<String, Object> attributes;

    @Override
    public int doStartTag() throws Exception
    {
        super.doStartTag();

        if(this.page == null)
        {
            return Tag.SKIP_BODY;
        }

        this.attributes = new HashMap<String, Object>();
        return TagSupport.EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag() throws Exception
    {
        if(this.getPage() != null)
        {
            pageContext.include(this.getPage(), this.attributes);
        }

        return TagSupport.EVAL_PAGE;
    }

    /**
     * @param name
     * @param value
     */
    @Override
    public void setAttribute(String name, Object value)
    {
        this.attributes.put(name, value);
    }

    /**
     * @param page the page to set
     */
    public void setPage(String page)
    {
        this.page = page;
    }

    /**
     * @return the page
     */
    public String getPage()
    {
        return this.page;
    }
}
