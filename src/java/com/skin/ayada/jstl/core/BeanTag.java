/*
 * $RCSfile: BeanTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-26 $
 *
 * Copyright (C) 2008 WanMei, Inc. All rights reserved.
 *
 * This software is the proprietary information of WanMei, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import java.util.HashMap;
import java.util.Map;

import com.skin.ayada.tagext.PropertySupportTag;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TagSupport;
import com.skin.ayada.util.ClassUtil;

/**
 * <p>Title: BeanTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class BeanTag extends TagSupport implements PropertySupportTag
{
    private String name;
    private String className;
    private Map<String, Object> properties;

    @Override
    public int doStartTag()
    {
        if(this.name == null)
        {
            return Tag.SKIP_BODY;
        }

        this.properties = new HashMap<String, Object>();
        return TagSupport.EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag()
    {
        try
        {
            Object bean = ClassUtil.getInstance(this.className, null);
            ClassUtil.setProperties(bean, this.properties);
            this.pageContext.setAttribute(this.name, bean);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return Tag.EVAL_PAGE;
    }

    /**
     * @param name
     * @param value
     */
    @Override
    public void setProperty(String name, Object value)
    {
        this.properties.put(name, value);
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

    /**
     * @return the className
     */
    public String getClassName()
    {
        return this.className;
    }

    /**
     * @param className the className to set
     */
    public void setClassName(String className)
    {
        this.className = className;
    }
}
