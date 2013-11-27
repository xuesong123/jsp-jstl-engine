/*
 * $RCSfile: MapTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-26 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import java.util.LinkedHashMap;
import java.util.Map;

import com.skin.ayada.tagext.AttributeSupportTag;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TagSupport;

/**
 * <p>Title: MapTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class MapTag extends TagSupport implements AttributeSupportTag
{
    private String name;
    private Map<String, Object> map;

    @Override
    public int doStartTag()
    {
        super.doStartTag();
        
        if(this.name == null)
        {
            return Tag.SKIP_BODY;
        }

        this.map = new LinkedHashMap<String, Object>();
        return TagSupport.EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag()
    {
        this.pageContext.setAttribute(this.name, this.map);
        return Tag.EVAL_PAGE;
    }

    /**
     * @param name
     * @param value
     */
    @Override
    public void setAttribute(String name, Object value)
    {
        this.map.put(name, value);
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }
}
