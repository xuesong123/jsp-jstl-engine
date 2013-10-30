/*
 * $RCSfile: BeanUtil.java,v $$
 * $Revision: 1.1  $
 * $Date: 2013-2-27  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

import com.skin.ayada.ognl.util.Empty;

/**
 * <p>Title: BeanUtil</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class BeanUtil
{
    /**
     * @param value
     * @return boolean
     */
    public int size(Object value)
    {
        if(value == null)
        {
            return 0;
        }

        if(value.getClass().isArray())
        {
            return ((Object[])value).length;
        }

        if(value instanceof Collection<?>)
        {
            return ((Collection<?>)value).size();
        }

        if(value instanceof Map<?, ?>)
        {
            return ((Map<?, ?>)value).size();
        }

        return 0;
    }

    /**
     * @param value
     * @return boolean
     */
    public boolean isNull(Object value)
    {
        return (value == null || value instanceof Empty<?, ?>);
    }

    /**
     * @param value
     * @return boolean
     */
    public boolean notNull(Object value)
    {
        return (this.isNull(value) == false);
    }

    /**
     * @param value
     * @return boolean
     */
    public boolean isEmpty(Object value)
    {
        if(value == null)
        {
            return true;
        }

        if(value instanceof String)
        {
            return (((String)value).trim().length() < 1);
        }
        else if(value instanceof Collection<?>)
        {
            return ((Collection<?>)value).isEmpty();
        }
        else if(value.getClass().isArray())
        {
            return (Array.getLength(value) == 0);
        }
        else if(value instanceof Map<?, ?>)
        {
            return ((Map<?, ?>)value).isEmpty();
        }
        else
        {
            return false;
        }
    }

    /**
     * @param value
     * @return boolean
     */
    public boolean notEmpty(Object value)
    {
        return (this.isEmpty(value) == false);
    }

    /**
     * @param source
     * @param target
     * @return boolean
     */
    public boolean equals(Object source, Object target)
    {
        if(source != null && target != null)
        {
            return source.equals(target);
        }

        return (source == target);
    }

    /**
     * @param value
     */
    public void print(Object value)
    {
        if(value == null)
        {
            System.out.println("null");
        }

        System.out.println();
        System.out.println(value.getClass().getName());
        System.out.println(value.toString());
    }
}
