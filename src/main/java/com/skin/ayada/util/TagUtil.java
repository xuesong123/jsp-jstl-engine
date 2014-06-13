/*
 * $RCSfile: TagUtil.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-2-19 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

import com.skin.ayada.jstl.TagLibrary;
import com.skin.ayada.runtime.ExpressionContext;
import com.skin.ayada.runtime.PageContext;
import com.skin.ayada.tagext.DynamicAttributes;
import com.skin.ayada.tagext.Tag;

/**
 * <p>Title: TagUtil</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class TagUtil
{
    private TagUtil()
    {
    }

    /**
     * @param className
     * @return Tag
     * @throws Exception
     */
    public static Tag create(String className)
    {
        try
        {
            return (Tag)(ClassUtil.getInstance(className));
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

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
            return (Tag)(ClassUtil.getInstance(className));
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param tag
     * @param attributes
     * @param expressionContext
     */
    public static void setAttributes(Tag tag, Map<String, String> attributes, ExpressionContext expressionContext) throws Exception
    {
        if(attributes == null || attributes.size() < 1)
        {
            return;
        }

        if(tag instanceof DynamicAttributes)
        {
            DynamicAttributes dynamicAttributes = (DynamicAttributes)tag;

            for(Map.Entry<String, String> entry : attributes.entrySet())
            {
                String name = entry.getKey();
                String value = entry.getValue();
                Object argument = ExpressionUtil.evaluate(expressionContext, value, Object.class);
                dynamicAttributes.setDynamicAttribute(name, argument);
            }

            return;
        }

        Class<?> type = tag.getClass();

        for(Map.Entry<String, String> entry : attributes.entrySet())
        {
            String name = entry.getKey();
            String value = entry.getValue();

            name = "set" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
            Method method = getSetMethod(type, name);

            if(method != null)
            {
                Class<?>[] parameterTypes = method.getParameterTypes();
                Class<?> parameterType = parameterTypes[0];
                Object arg = ExpressionUtil.evaluate(expressionContext, value, parameterType);

                if(arg == null && parameterType.isPrimitive())
                {
                    continue;
                }

                method.invoke(tag, new Object[]{arg});
            }
            else
            {
                throw new Exception("NoSuchMethodException: " + tag.getClass().getName() + "." + name);
            }
        }
    }

    /**
     * @param type
     * @param methodName
     * @return Method
     */
    public static Method getSetMethod(Class<?> type, String methodName)
    {
        Method[] methods = type.getMethods();

        for(Method method : methods)
        {
            if(method.getModifiers() != Modifier.PUBLIC)
            {
                continue;
            }

            if(method.getName().equals(methodName))
            {
                if(method.getParameterTypes().length == 1)
                {
                    return method;
                }
            }
        }

        return null;
    }
}
