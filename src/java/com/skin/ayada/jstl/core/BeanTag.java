/*
 * $RCSfile: BeanTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-26 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.skin.ayada.tagext.ConstructorTagSupport;
import com.skin.ayada.tagext.PropertyTagSupport;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TagSupport;
import com.skin.ayada.util.ClassUtil;

/**
 * <p>Title: BeanTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class BeanTag extends TagSupport implements Tag, ConstructorTagSupport, PropertyTagSupport
{
    private String name;
    private String className;
    private List<Class<?>> parameterTypes;
    private List<Object> parameters;
    private Map<String, Object> properties;

    @Override
    public int doStartTag() throws Exception
    {
        if(this.name == null || this.className == null)
        {
            return Tag.SKIP_BODY;
        }

        this.parameterTypes = new ArrayList<Class<?>>();
        this.parameters = new ArrayList<Object>();
        this.properties = new HashMap<String, Object>();
        return Tag.EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag() throws Exception
    {
        Object bean = null;

        if(this.parameters.size() > 0)
        {
            Object[] parameters = this.getParameters();
            Class<?>[] parameterTypes = this.getParameterTypes();
            bean = ClassUtil.getInstance(this.className, parameterTypes, parameters);
        }
        else
        {
            bean = ClassUtil.getInstance(this.className);
        }

        ClassUtil.setProperties(bean, this.properties);
        this.pageContext.setAttribute(this.name, bean);
        return Tag.EVAL_PAGE;
    }

    /**
     * @return Class<?>[]
     */
    public Class<?>[] getParameterTypes()
    {
        Class<?>[] values = new Class<?>[this.parameterTypes.size()];
        this.parameterTypes.toArray(values);
        return values;
    }

    /**
     * @return Object[]
     */
    public Object[] getParameters()
    {
        Object[] values = new Object[this.parameters.size()];
        this.parameters.toArray(values);
        return values;
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
     * @param index
     * @param type
     * @param value
     */
    @Override
    public void setArgument(Class<?> type, Object value)
    {
        if(type != null)
        {
            this.parameterTypes.add(type);
        }
        else
        {
            this.parameterTypes.add(value.getClass());
        }

        this.parameters.add(value);
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
