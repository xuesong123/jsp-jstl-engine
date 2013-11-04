/*
 * $RCSfile: ClassUtil.java,v $$
 * $Revision: 1.1  $
 * $Date: 2013-2-19  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Title: ClassUtil</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class ClassUtil
{
    private static Logger logger = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * @param className
     * @param parent
     * @return Object
     * @throws Exception
     */
    public static Object getInstance(String className, Class<?> parent) throws Exception
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

    /**
     * @param className
     * @return Class<?>
     * @throws ClassNotFoundException
     */
    public static Class<?> getClass(String className) throws ClassNotFoundException
    {
        Class<?> clazz = null;

        try
        {
            clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
        }
        catch(Exception e)
        {}

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
     * @param type
     * @param value
     * @return Object
     */
    public static Object cast(Object value, Class<?> type)
    {
        if(value == null || type == null)
        {
            return null;
        }

        Class<?> clazz = value.getClass();

        if(type.isAssignableFrom(clazz))
        {
            return value;
        }

        return ClassUtil.cast(value.toString(), type);
    }

    /**
     * @param type
     * @param value
     * @return Object
     */
    public static Object cast(String value, Class<?> type)
    {
        if(value == null || type == null)
        {
            return null;
        }

        Object result = null;

        if(type == char.class || type == Character.class)
        {
            result = (value.length() > 0 ? value.charAt(0) : null);
        }
        else if(type == boolean.class || type == Boolean.class)
        {
            boolean b = ("1".equalsIgnoreCase(value) || "y".equalsIgnoreCase(value) || "on".equalsIgnoreCase(value) || "yes".equalsIgnoreCase(value) || "true".equalsIgnoreCase(value));
            result = Boolean.valueOf(b);
        }
        else if(type == byte.class || type == Byte.class)
        {
            try
            {
                result = Byte.parseByte(value);
            }
            catch(NumberFormatException e)
            {
            }
        }
        else if(type == short.class || type == Short.class)
        {
            try
            {
                result = Short.parseShort(value);
            }
            catch(NumberFormatException e)
            {
            }
        }
        else if(type == int.class || type == Integer.class)
        {
            try
            {
                result = Integer.parseInt(value);
            }
            catch(NumberFormatException e)
            {
            }
        }
        else if(type == float.class || type == Float.class)
        {
            try
            {
                result = Float.parseFloat(value);
            }
            catch(NumberFormatException e)
            {
            }
        }
        else if(type == double.class || type == Double.class)
        {
            try
            {
                result = Double.parseDouble(value);
            }
            catch(NumberFormatException e)
            {
            }
        }
        else if(type == long.class || type == Long.class)
        {
            try
            {
                result = Long.parseLong(value);
            }
            catch(NumberFormatException e)
            {
            }
        }
        else if(type == String.class)
        {
            result = value;
        }
        else if(type == StringBuilder.class)
        {
            result = new StringBuilder(value);
        }
        else if(type == StringBuffer.class)
        {
            result = new StringBuffer(value);
        }
        else if(type == java.io.Reader.class)
        {
            result = new java.io.StringReader(value);
        }
        else if(type == java.sql.Date.class)
        {
            if(value.length() > 0)
            {
                try
                {
                    String format = getFormat(value);
                    DateFormat dateFormat = new SimpleDateFormat(format);
                    Date date = dateFormat.parse(value);
                    result = new java.sql.Date(date.getTime());
                }
                catch(java.text.ParseException e)
                {
                }
            }
        }
        else if(type == java.sql.Time.class)
        {
            if(value.length() > 0)
            {
                try
                {
                    String format = getFormat(value);
                    SimpleDateFormat dateFormat = new SimpleDateFormat(format);
                    java.util.Date date = dateFormat.parse(value);
                    result = new java.sql.Time(date.getTime());
                }
                catch(java.text.ParseException e)
                {
                }
            }
        }
        else if(type == java.sql.Timestamp.class)
        {
            if(value.length() > 0)
            {
                try
                {
                    String format = getFormat(value);
                    SimpleDateFormat dateFormat = new SimpleDateFormat(format);
                    java.util.Date date = dateFormat.parse(value);
                    result = new java.sql.Timestamp(date.getTime());
                }
                catch(java.text.ParseException e)
                {
                }
            }
        }
        else if(type == java.util.Date.class)
        {
            if(value.length() > 0)
            {
                try
                {
                    String format = getFormat(value);
                    SimpleDateFormat dateFormat = new SimpleDateFormat(format);
                    result = dateFormat.parse(value);
                }
                catch(java.text.ParseException e)
                {
                    if(logger.isDebugEnabled())
                    {
                        logger.debug("Exception: " + e.getMessage());
                    }
                }
            }
        }
        else if(type == Object.class)
        {
            result = value;
        }

        return result;
    }

    /**
     * @param date
     * @return String
     */
    protected static String getFormat(String date)
    {
        int length = date.length();

        String f1 = "HH:mm:ss";
        String f2 = "yyyy-MM-dd";
        String f3 = "HH:mm:ss SSS";
        String f4 = "yyyy-MM-dd HH:mm:ss";
        String f5 = "yyyy-MM-dd HH:mm:ss SSS";

        if(length <= f1.length())
        {
            return f1;
        }
        else if(length <= f2.length())
        {
            return f2;
        }
        else if(length <= f3.length())
        {
            return f3;
        }
        else if(length <= f4.length())
        {
            return f4;
        }
        else if(length <= f5.length())
        {
            return f5;
        }

        return f3;
    }
}
