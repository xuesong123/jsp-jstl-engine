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

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>Title: ClassUtil</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class ClassUtil
{
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

        Object result = null;

        if(type == char.class || type == Character.class)
        {
            return ClassUtil.getCharacter(value);
        }
        else if(type == boolean.class || type == Boolean.class)
        {
            return ClassUtil.getBoolean(value);
        }
        else if(type == byte.class || type == Byte.class)
        {
            return ClassUtil.getByte(value);
        }
        else if(type == short.class || type == Short.class)
        {
            return ClassUtil.getShort(value);
        }
        else if(type == int.class || type == Integer.class)
        {
            return ClassUtil.getInteger(value);
        }
        else if(type == float.class || type == Float.class)
        {
            return ClassUtil.getFloat(value);
        }
        else if(type == double.class || type == Double.class)
        {
            return ClassUtil.getDouble(value);
        }
        else if(type == long.class || type == Long.class)
        {
            return ClassUtil.getLong(value);
        }
        else if(type == String.class)
        {
            result = ClassUtil.getString(value);
        }
        else if(type == java.sql.Date.class)
        {
            Date date = ClassUtil.getDate(value);

            if(date != null)
            {
                result = new java.sql.Date(date.getTime());
            }
        }
        else if(type == java.sql.Time.class)
        {
            Date date = ClassUtil.getDate(value);

            if(date != null)
            {
                result = new java.sql.Time(date.getTime());
            }
        }
        else if(type == java.sql.Timestamp.class)
        {
            Date date = ClassUtil.getDate(value);

            if(date != null)
            {
                result = new java.sql.Timestamp(date.getTime());
            }
        }
        else if(type == java.util.Date.class)
        {
            result = ClassUtil.getDate(value);
        }
        else if(type == Object.class)
        {
            result = value;
        }

        return result;
    }

    /**
     * @param value
     * @return Boolean
     */
    public static Boolean getBoolean(Object value)
    {
        if(value instanceof Boolean)
        {
            return (Boolean)value;
        }

        if(value != null)
        {
            return value.toString().equals("true");
        }

        return Boolean.FALSE;
    }

    /**
     * @param value
     * @return Byte
     */
    public static Byte getByte(Object value)
    {
        Integer i = getInteger(value);
        
        if(i != null)
        {
            return i.byteValue();
        }

        return null;
    }

    /**
     * @param value
     * @return Boolean
     */
    public static Short getShort(Object value)
    {
        Integer i = getInteger(value);

        if(i != null)
        {
            return i.shortValue();
        }

        return null;
    }

    /**
     * @param value
     * @return Character
     */
    public static Character getCharacter(Object value)
    {
        if(value instanceof Character)
        {
            return (Character)value;
        }

        if(value != null)
        {
            String content = value.toString();
            
            if(content.length() > 0)
            {
                return content.charAt(0);
            }
        }

        return null;
    }

    /**
     * @param value
     * @return Integer
     */
    public static Integer getInteger(Object value)
    {
        if(value instanceof Number)
        {
            return ((Number)value).intValue();
        }

        if(value != null)
        {
            Double d = getDouble(value);

            if(d != null)
            {
                return d.intValue();
            }
        }

        return null;
    }
    
    /**
     * @param value
     * @return Float
     */
    public static Float getFloat(Object value)
    {
        if(value instanceof Number)
        {
            return ((Number)value).floatValue();
        }

        if(value != null)
        {
            Double d = getDouble(value);

            if(d != null)
            {
                return d.floatValue();
            }
        }

        return null;
    }

    /**
     * @param value
     * @return Double
     */
    public static Double getDouble(Object value)
    {
        if(value instanceof Number)
        {
            return ((Number)value).doubleValue();
        }

        if(value != null)
        {
            try
            {
                return Double.parseDouble(value.toString());
            }
            catch(NumberFormatException e)
            {
            }
        }

        return null;
    }

    /**
     * @param value
     * @return Long
     */
    public static Long getLong(Object value)
    {
        if(value instanceof Number)
        {
            return ((Number)value).longValue();
        }

        if(value != null)
        {
            Double d = getDouble(value);

            if(d != null)
            {
                return d.longValue();
            }
        }

        return null;
    }

    /**
     * @param value
     * @return
     */
    public static String getString(Object value)
    {
        if(value instanceof String)
        {
            return ((String)value);
        }

        if(value != null)
        {
            return value.toString();
        }

        return null;
    }

    /**
     * @param value
     * @return Date
     */
    public static Date getDate(Object value)
    {
        if(value instanceof Date)
        {
            return (Date)value;
        }

        if(value != null)
        {
            try
            {
                String content = value.toString();
                String pattern = getFormat(content);
                SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
                return dateFormat.parse(content);
            }
            catch(java.text.ParseException e)
            {
            }
        }

        return null;
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
