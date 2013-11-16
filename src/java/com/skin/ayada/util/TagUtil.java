/*
 * $RCSfile: TagUtil.java,v $$
 * $Revision: 1.1  $
 * $Date: 2013-2-19  $
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
import com.skin.ayada.tagext.Tag;

/**
 * <p>Title: TemplateExecutor</p>
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
            return (Tag)(ClassUtil.getInstance(className, Tag.class));
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
            return (Tag)(ClassUtil.getInstance(className, Tag.class));
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
    public static void setAttributes(Tag tag, Map<String, String> attributes, ExpressionContext expressionContext)
    {
        if(attributes == null || attributes.size() < 1)
        {
            return;
        }

        for(Map.Entry<String, String> entry : attributes.entrySet())
        {
            String name = entry.getKey();
            String value = entry.getValue();
            Class<?> type = tag.getClass();

            try
            {
                name = "set" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
                Method method = getSetMethod(type, name);

                if(method != null)
                {
                    Object arg = ExpressionUtil.evaluate(expressionContext, value);
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    Class<?> parameterType = parameterTypes[0];

                    if(arg != null)
                    {
                        arg = ClassUtil.cast(arg, parameterType);
                    }

                    try
                    {
                        method.invoke(tag, new Object[]{arg});
                    }
                    catch(Exception e)
                    {
                        String className = (arg != null ? arg.getClass().getName() : "null");
                        throw new RuntimeException("method: " + method + " - " + parameterType.getName() + " - " + className, e);
                    }
                }
                else
                {
                    throw new RuntimeException("NoSuchMethodException: " + tag.getClass().getName() + "." + name);
                }
            }
            catch(SecurityException e)
            {
                throw new RuntimeException(e);
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
