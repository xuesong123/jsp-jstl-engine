/*
 * $RCSfile: TagFactory.java,v $$
 * $Revision: 1.1  $
 * $Date: 2013-2-19  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.runtime;

import com.skin.ayada.jstl.TagLibrary;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.util.ClassUtil;

/**
 * <p>Title: TagFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class TagFactory
{
    /**
     * @param tagName
     * @return Tag
     * @throws Exception
     */
    public static Tag create(PageContext pageContext, String tagName)
    {
        TagLibrary tagLibrary = pageContext.getTagLibrary();
        String className = tagLibrary.getTagClassName(tagName);

        try
        {
            return (Tag)(getInstance(className, Tag.class));
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param className
     * @return Class<?>
     * @throws ClassNotFoundException
     */
    private static Class<?> getClass(String className) throws ClassNotFoundException
    {
        Class<?> clazz = null;

        try
        {
            clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
        }
        catch(Exception e)
        {
        }

        if(clazz == null)
        {
            clazz = ClassUtil.class.getClassLoader().loadClass(className);
        }

        if(clazz == null)
        {
            clazz = Class.forName(className);
        }

        return clazz;
    }

    /**
     * @param className
     * @param parent
     * @return Object
     * @throws Exception
     */
    private static Object getInstance(String className, Class<?> parent) throws Exception
    {
        Class<?> clazz = getClass(className);

        if(parent == null)
        {
            parent = Object.class;
        }

        if(!parent.isAssignableFrom(clazz))
        {
            throw new ClassCastException(className + " class must be implement the " + parent.getName() + " interface.");
        }

        return clazz.newInstance();
    }
}
