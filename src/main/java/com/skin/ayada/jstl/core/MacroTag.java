/*
 * $RCSfile: MacroTag.java,v $$
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

import com.skin.ayada.resource.PropertyResource;
import com.skin.ayada.tagext.ParameterTagSupport;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TagSupport;

/**
 * <p>Title: MacroTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class MacroTag extends TagSupport implements ParameterTagSupport
{
    private String name;
    private String page;
    private Map<String, Object> parameters;
    private static Map<String, String> config = PropertyResource.load("ayada-macro.properties", "UTF-8");

    @Override
    public int doStartTag() throws Exception
    {
        super.doStartTag();

        if(this.name == null)
        {
            throw new Exception("macro's name must be not null!");
        }

        this.page = MacroTag.config.get(this.name);

        if(this.page == null)
        {
            throw new Exception("macro '" + this.name + "' not found in 'ayada-macro.properties'!");
        }

        this.parameters = new HashMap<String, Object>();
        return Tag.EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag() throws Exception
    {
        this.pageContext.include(this.page, this.parameters);
        return Tag.EVAL_PAGE;
    }

    /**
     * @param name
     * @param value
     */
    public void setParameter(String name, Object value)
    {
        this.parameters.put(name, value);
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return this.name;
    }
}
