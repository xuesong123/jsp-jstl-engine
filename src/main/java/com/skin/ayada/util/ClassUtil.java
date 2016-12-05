/*
 * $RCSfile: ClassUtil.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-19 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.util;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * <p>Title: ClassUtil</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class ClassUtil {
    /**
     * @param className
     * @return Object
     * @throws Exception
     */
    public static Object getInstance(String className) throws Exception {
        return getClass(className).newInstance();
    }

    /**
     * @param className
     * @param parameterTypes
     * @param parameters
     * @return Object
     * @throws Exception
     */
    public static Object getInstance(String className, Class<?>[] parameterTypes, Object[] parameters) throws Exception {
        Class<?> clazz = getClass(className);
        Constructor<?> constructor = clazz.getConstructor(parameterTypes);
        return constructor.newInstance(parameters);
    }

    /**
     * @param className
     * @return Class<?>
     * @throws ClassNotFoundException
     */
    public static Class<?> getClass(String className) throws ClassNotFoundException {
        if(className.equals("boolean")) {
            return boolean.class;
        }
        else if(className.equals("byte")) {
            return byte.class;
        }
        else if(className.equals("short")) {
            return short.class;
        }
        else if(className.equals("char")) {
            return char.class;
        }
        else if(className.equals("int")) {
            return int.class;
        }
        else if(className.equals("float")) {
            return float.class;
        }
        else if(className.equals("double")) {
            return double.class;
        }
        else if(className.equals("long")) {
            return long.class;
        }

        try {
            return Thread.currentThread().getContextClassLoader().loadClass(className);
        }
        catch(Exception e) {
        }

        try {
            return ClassUtil.class.getClassLoader().loadClass(className);
        }
        catch(Exception e) {
        }
        return Class.forName(className);
    }

    /**
     * @param values
     * @return Class<?>[]
     */
    public static Class<?>[] getTypes(Object[] values) {
        Class<?>[] types = new Class<?>[values.length];

        for(int i = 0; i < values.length; i++) {
            types[i] = values[i].getClass();
        }
        return types;
    }

    /**
     * @param bean
     * @param properties
     * @throws Exception
     */
    public static void setProperties(Object bean, Map<String, Object> properties) throws Exception {
        if(properties == null || properties.size() < 1) {
            return;
        }

        for(Map.Entry<String, Object> entry : properties.entrySet()) {
            ClassUtil.setProperty(bean, entry.getKey(), entry.getValue());
        }
    }

    /**
     * @param bean
     * @param name
     * @param value
     * @throws Exception
     */
    public static void setProperty(Object bean, String name, Object value) throws Exception {
        if(bean == null) {
            return;
        }

        Class<?> type = bean.getClass();
        String methodName = "set" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
        Method method = getSetMethod(type, methodName);

        if(method != null) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            Class<?> parameterType = parameterTypes[0];
            Object arg = ClassUtil.cast(value, parameterType);

            if(arg == null && parameterType.isPrimitive()) {
                return;
            }
            method.invoke(bean, new Object[]{arg});
        }
        else {
            throw new Exception("NoSuchMethodException: " + type.getName() + "." + methodName);
        }
    }

    /**
     * @param bean
     * @param name
     * @return Object
     * @throws Exception
     */
    public static Object getProperty(Object bean, String name) throws Exception {
        if(bean == null) {
            return null;
        }

        Class<?> type = bean.getClass();
        String methodName = "get" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
        Method method = type.getMethod(methodName, new Class[0]);
        return method.invoke(bean, new Object[0]);
    }

    /**
     * @param type
     * @param methodName
     * @return Method
     */
    public static Method getSetMethod(Class<?> type, String methodName) {
        Method[] methods = type.getMethods();

        for(Method method : methods) {
            if(method.getModifiers() != Modifier.PUBLIC) {
                continue;
            }

            if(method.getName().equals(methodName)) {
                if(method.getParameterTypes().length == 1) {
                    return method;
                }
            }
        }
        return null;
    }

    /**
     * @param type
     * @param value
     * @return Object
     */
    public static Object cast(Object value, Class<?> type) {
        if(value == null || type == null) {
            return null;
        }

        Class<?> clazz = value.getClass();

        if(type.isAssignableFrom(clazz)) {
            return value;
        }

        Object result = null;

        if(type == char.class || type == Character.class) {
            return ClassUtil.getCharacter(value);
        }
        else if(type == boolean.class || type == Boolean.class) {
            return ClassUtil.getBoolean(value);
        }
        else if(type == byte.class || type == Byte.class) {
            return ClassUtil.getByte(value);
        }
        else if(type == short.class || type == Short.class) {
            return ClassUtil.getShort(value);
        }
        else if(type == int.class || type == Integer.class) {
            return ClassUtil.getInteger(value);
        }
        else if(type == float.class || type == Float.class) {
            return ClassUtil.getFloat(value);
        }
        else if(type == double.class || type == Double.class) {
            return ClassUtil.getDouble(value);
        }
        else if(type == long.class || type == Long.class) {
            return ClassUtil.getLong(value);
        }
        else if(type == String.class) {
            result = ClassUtil.getString(value);
        }
        else if(type == java.sql.Date.class) {
            Date date = ClassUtil.getDate(value);

            if(date != null) {
                result = new java.sql.Date(date.getTime());
            }
        }
        else if(type == java.sql.Time.class) {
            Date date = ClassUtil.getDate(value);

            if(date != null) {
                result = new java.sql.Time(date.getTime());
            }
        }
        else if(type == java.sql.Timestamp.class) {
            Date date = ClassUtil.getDate(value);

            if(date != null) {
                result = new java.sql.Timestamp(date.getTime());
            }
        }
        else if(type == java.util.Date.class) {
            result = ClassUtil.getDate(value);
        }
        else if(type == Object.class) {
            result = value;
        }
        return result;
    }

    /**
     * @param source
     * @return Object[]
     */
    public static Object[] getArray(Object source) {
        if(source instanceof Object[]) {
            return (Object[])source;
        }

        if(source == null) {
            return new Object[0];
        }

        if(!source.getClass().isArray()) {
            throw new IllegalArgumentException("Source is not an array: " + source);
        }

        int length = Array.getLength(source);

        if(length == 0) {
            return new Object[0];
        }

        Class<?> type = Array.get(source, 0).getClass();
        Object[] array = (Object[])(Array.newInstance(type, length));

        for(int i = 0; i < length; i++) {
            array[i] = Array.get(source, i);
        }
        return array;
    }

    /**
     * @param value
     * @return Boolean
     */
    public static Boolean getBoolean(Object value) {
        if(value instanceof Boolean) {
            return (Boolean)value;
        }

        if(value != null) {
            return value.toString().equals("true");
        }
        return Boolean.FALSE;
    }

    /**
     * @param value
     * @return Byte
     */
    public static Byte getByte(Object value) {
        Integer i = getInteger(value);

        if(i != null) {
            return i.byteValue();
        }
        return null;
    }

    /**
     * @param value
     * @return Boolean
     */
    public static Short getShort(Object value) {
        Integer i = getInteger(value);

        if(i != null) {
            return i.shortValue();
        }
        return null;
    }

    /**
     * @param value
     * @return Character
     */
    public static Character getCharacter(Object value) {
        if(value instanceof Character) {
            return (Character)value;
        }

        if(value != null) {
            String content = value.toString();

            if(content.length() > 0) {
                return content.charAt(0);
            }
        }
        return null;
    }

    /**
     * @param value
     * @return Integer
     */
    public static Integer getInteger(Object value) {
        if(value instanceof Number) {
            return ((Number)value).intValue();
        }

        if(value != null) {
            Double d = getDouble(value);

            if(d != null) {
                return d.intValue();
            }
        }
        return null;
    }

    /**
     * @param value
     * @return Float
     */
    public static Float getFloat(Object value) {
        if(value instanceof Number) {
            return ((Number)value).floatValue();
        }

        if(value != null) {
            Double d = getDouble(value);

            if(d != null) {
                return d.floatValue();
            }
        }
        return null;
    }

    /**
     * @param value
     * @return Double
     */
    public static Double getDouble(Object value) {
        if(value instanceof Number) {
            return ((Number)value).doubleValue();
        }

        if(value != null) {
            try {
                return Double.parseDouble(value.toString());
            }
            catch(NumberFormatException e) {
            }
        }
        return null;
    }

    /**
     * @param value
     * @return Long
     */
    public static Long getLong(Object value) {
        if(value instanceof Number) {
            return ((Number)value).longValue();
        }

        if(value != null) {
            Long l = null;
            String s = value.toString().trim();

            try {
                if(s.endsWith("l") || s.endsWith("L")) {
                    l = Long.parseLong(s.substring(0, s.length() - 1));
                }
                else {
                    l = Long.parseLong(s);
                }
            }
            catch(NumberFormatException e) {
            }
            return l;
        }
        return null;
    }

    /**
     * @param value
     * @return String
     */
    public static String getString(Object value) {
        if(value instanceof String) {
            return ((String)value);
        }

        if(value != null) {
            return value.toString();
        }
        return null;
    }

    /**
     * @param value
     * @return Date
     */
    public static Date getDate(Object value) {
        if(value instanceof Date) {
            return (Date)value;
        }

        if(value != null) {
            try {
                String content = value.toString();
                String pattern = getFormat(content);
                SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
                return dateFormat.parse(content);
            }
            catch(java.text.ParseException e) {
            }
        }
        return null;
    }

    /**
     * @param date
     * @return String
     */
    protected static String getFormat(String date) {
        int length = date.length();

        String f1 = "HH:mm:ss";
        String f2 = "yyyy-MM-dd";
        String f3 = "HH:mm:ss SSS";
        String f4 = "yyyy-MM-dd HH:mm:ss";
        String f5 = "yyyy-MM-dd HH:mm:ss SSS";

        if(length <= f1.length()) {
            return f1;
        }
        else if(length <= f2.length()) {
            return f2;
        }
        else if(length <= f3.length()) {
            return f3;
        }
        else if(length <= f4.length()) {
            return f4;
        }
        else if(length <= f5.length()) {
            return f5;
        }
        return f3;
    }
}
