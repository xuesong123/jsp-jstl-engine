/*
 * $RCSfile: ActionDispatcher.java,v $$
 * $Revision: 1.1  $
 * $Date: 2011-12-9  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.taglib;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import com.skin.ayada.component.Parameters;
import com.skin.ayada.runtime.PageContext;

/**
 * <p>Title: ActionDispatcher</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class ActionDispatcher
{
    private static final Class<?>[] PARAMETERTYPES= new Class[]{PageContext.class, Parameters.class};

    /**
     * @param pageContext
     * @param context
     * @param className
     * @param method
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> dispatch(PageContext pageContext, Parameters parameters, String className, String method) throws Exception
    {
        Class<?> type = ActionDispatcher.getClass(className);
        Object instance = type.newInstance();
        String methodName = ActionDispatcher.getMethod(className, method);
        return (Map<String, Object>)(ActionDispatcher.invoke(instance, methodName, PARAMETERTYPES, new Object[]{pageContext, parameters}, false));
    }

    /**
     * @param className
     * @return Object
     * @throws Exception
     */
    public Object getInstance(String className) throws Exception
    {
        Class<?> type = ActionDispatcher.getClass(className);

        if(type == null)
        {
            throw new ClassNotFoundException("class " + className + " not found !");
        }

        return type.newInstance();
    }

    /**
     * @param className
     * @return Class<?>
     */
    public static Class<?> getClass(String className) throws ClassNotFoundException
    {
        Class<?> clazz = null;

        try
        {
            clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
        }
        catch(ClassNotFoundException e)
        {
        }

        if(clazz == null)
        {
            try
            {
                clazz = ActionDispatcher.class.getClassLoader().loadClass(className);
            }
            catch(ClassNotFoundException e)
            {
            }
        }

        if(clazz == null)
        {
            clazz = Class.forName(className);
        }

        return clazz;
    }

    /**
     * @param className
     * @param method
     * @return String
     */
    protected static String getMethod(String className, String method)
    {
        return (method != null ? method : "execute");
    }

    /**
     * @param instance
     * @param name
     * @param types
     * @param parameters
     * @param safe
     * @return Object
     */
    protected static Object invoke(Object instance, String name, Class<?>[] types, Object[] parameters, boolean safe)
    {
        Exception exception = null;

        try
        {
            Class<?> type = instance.getClass();
            Method method = type.getMethod(name, types);
            return method.invoke(instance, parameters);
        }
        catch(SecurityException e)
        {
            exception = e;
        }
        catch(NoSuchMethodException e)
        {
            exception = e;
        }
        catch(IllegalArgumentException e)
        {
            exception = e;
        }
        catch(IllegalAccessException e)
        {
            exception = e;
        }
        catch(InvocationTargetException e)
        {
            exception = e;
        }

        if(safe == false)
        {
            throw new RuntimeException(exception);
        }

        return null;
    }
}
