/*
 * $RCSfile: BeanUtil.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-27 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.util;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.skin.ayada.ognl.util.Empty;

/**
 * <p>Title: BeanUtil</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class BeanUtil {
    /**
     * @param value
     * @return boolean
     */
    public int size(Object value) {
        if(value == null) {
            return 0;
        }

        if(value.getClass().isArray()) {
            return ((Object[])value).length;
        }

        if(value instanceof Collection<?>) {
            return ((Collection<?>)value).size();
        }

        if(value instanceof Map<?, ?>) {
            return ((Map<?, ?>)value).size();
        }
        return 0;
    }

    /**
     * @param value
     * @return boolean
     */
    public boolean isNull(Object value) {
        return (value == null || value instanceof Empty<?, ?>);
    }

    /**
     * @param value
     * @return boolean
     */
    public boolean notNull(Object value) {
        return (this.isNull(value) == false);
    }

    /**
     * @param value
     * @return boolean
     */
    public boolean isEmpty(Object value) {
        if(value == null) {
            return true;
        }

        if(value instanceof String) {
            return (((String)value).trim().length() < 1);
        }
        else if(value instanceof Collection<?>) {
            return ((Collection<?>)value).isEmpty();
        }
        else if(value.getClass().isArray()) {
            return (Array.getLength(value) == 0);
        }
        else if(value instanceof Map<?, ?>) {
            return ((Map<?, ?>)value).isEmpty();
        }
        else {
            return false;
        }
    }

    /**
     * @param value
     * @return boolean
     */
    public boolean notEmpty(Object value) {
        return (this.isEmpty(value) == false);
    }

    /**
     * @param source
     * @param target
     * @return boolean
     */
    public boolean equals(Object source, Object target) {
        if(source != null && target != null) {
            return source.equals(target);
        }

        return (source == target);
    }

    /**
     * @param c
     */
    public String toString(Character c) {
        return c.toString();
    }

    /**
     * @param b
     */
    public String toString(Boolean b) {
        return (b.toString());
    }

    /**
     * @param b
     */
    public String toString(Byte b) {
        return (b.toString());
    }

    /**
     * @param s
     */
    public String toString(Short s) {
        return (s.toString());
    }

    /**
     * @param i
     */
    public String toString(Integer i) {
        return (i.toString());
    }

    /**
     * @param f
     */
    public String toString(Float f) {
        return (f.toString());
    }

    /**
     * @param d
     */
    public String toString(Double d) {
        return (d.toString());
    }

    /**
     * @param l
     */
    public String toString(Long l) {
        return (l.toString());
    }

    /**
     * @param date
     */
    public String toString(Date date) {
        if(date != null) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS".toString());
            return (dateFormat.format(date).toString());
        }
        return "null";
    }

    /**
     * @param object
     */
    public String toString(Object object) {
        if(object == null) {
            return "null";
        }

        Class<?> clazz = object.getClass();
        String className = clazz.getName();

        if(className.startsWith("java.")) {
            return object.toString();
        }

        String line = "\r\n";
        StringBuilder buffer = new StringBuilder();
        buffer.append("--------------- " + className + " info ---------------").append(line);
        Method[] methods = clazz.getMethods();

        for(int i = 0; i < methods.length; i++) {
            String name = methods[i].getName();
            Class<?>[] parameterTypes = methods[i].getParameterTypes();

            if(Modifier.isPublic(methods[i].getModifiers()) && name.startsWith("get") && parameterTypes.length == 0 && name.equals("getClass") == false) {
                try {
                    Object value = methods[i].invoke(object, new Object[0]);
                    buffer.append(name.substring(3) + ": " + value.toString()).append(line);
                }
                catch(Exception e) {
                    buffer.append(name.substring(3) + ": " + e.getMessage()).append(line);
                }
            }
        }

        buffer.append("--------------- " + className + " end ---------------");
        buffer.append(line);
        return buffer.toString();
    }

    /**
     * @param c
     */
    public String toString(Collection<?> c) {
        return toString(c, 0);
    }

    /**
     * @param c
     */
    public String toString(Collection<?> c, int size) {
        if(c == null) {
            return "collection.size: null";
        }
        else if(c.size() < 1) {
            return ("collection.size: 0");
        }
        else {
            int i = 0;
            String line = "\r\n";
            StringBuilder buffer = new StringBuilder();
            buffer.append("collection.size: " + c.size()).append(line);

            for(Object o : c) {
                buffer.append(toString(o));

                if(size > 0) {
                    i++;

                    if(i >= size) {
                        buffer.append("还有[" + (c.size() - i) + "]项未显示 ......").append(line);
                        break;
                    }
                }
            }

            return buffer.toString();
        }
    }

    /**
     * @param map
     */
    public String toString(Map<?, ?> map) {
        return toString(map, 0);
    }

    /**
     * @param map
     */
    public String toString(Map<?, ?> map, int size) {
        if(map == null) {
            return ("map.size: null");
        }
        else if(map.size() < 1) {
            return ("map.size: 0");
        }
        else {
            int i = 0;
            String line = "\r\n";
            StringBuilder buffer = new StringBuilder();
            buffer.append("map.size: " + map.size()).append(line);

            for(Map.Entry<?, ?> entry : map.entrySet()) {
                Object key = entry.getKey();
                Object value = entry.getValue();

                buffer.append("key: " + (key != null ? key.toString() : "null")).append(line);
                buffer.append("value: " + (value != null ? value.toString() : "null")).append(line);
                buffer.append("value.detail: ").append(line);
                buffer.append(toString(value));

                if(size > 0) {
                    i++;
                    if(i >= size) {
                        buffer.append("还有[" + (map.size() - i) + "]项未显示 ......").append(line);
                        break;
                    }
                }
            }
            return buffer.toString();
        }
    }

    /**
     * @param value
     */
    public void print(Object value) {
        System.out.println(toString(value));
    }
}
