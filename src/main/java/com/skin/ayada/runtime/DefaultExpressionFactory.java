/*
 * $RCSfile: DefaultExpressionFactory.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-03-18 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.runtime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.skin.ayada.util.ClassUtil;

/**
 * <p>Title: DefaultExpressionFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class DefaultExpressionFactory implements ExpressionFactory
{
    private static final Map<String, Object> attributes = getAttributes("UTF-8");

    /**
     * @param pageContext
     * @return ExpressionContext
     */
    @Override
    public ExpressionContext getExpressionContext(PageContext pageContext)
    {
        return DefaultExpressionFactory.getDefaultExpressionContext(pageContext);
    }

    /**
     * @param pageContext
     * @return ExpressionContext
     */
    public static ExpressionContext getDefaultExpressionContext(PageContext pageContext)
    {
        ExpressionContext expressionContext = new DefaultExpressionContext(pageContext);

        if(attributes != null && attributes.size() > 0)
        {
            for(Map.Entry<String, Object> entry : attributes.entrySet())
            {
                pageContext.setAttribute(entry.getKey(), entry.getValue());
            }
        }

        return expressionContext;
    }

    /**
     * @param charset
     * @return Map<String, Object>
     */
    private static Map<String, Object> getAttributes(String charset)
    {
        Map<String, Object> attributes = new HashMap<String, Object>();
        Map<String, String> map1 = load("ayada-tools-default.properties", charset);
        Map<String, String> map2 = load("ayada-tools.properties", charset);
        map1.putAll(map2);

        if(map1.size() > 0)
        {
            for(Map.Entry<String, String> entry : map1.entrySet())
            {
                Entry pair = getEntry(entry.getKey(), entry.getValue());
                attributes.put(pair.getName(), pair.getValue());
            }
        }

        return attributes;
    }

    /**
     * @param name
     * @param value
     * @return Object
     */
    public static Entry getEntry(String name, String value)
    {
        String key = null;
        Object result = null;

        if(name.startsWith("set "))
        {
            key = name.substring(4);

            try
            {
                result = ClassUtil.getInstance(value);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        else if(name.startsWith("var "))
        {
            key = name.substring(4);
            result = value;
        }
        else
        {
            key = name;
            result = value;
        }

        return new Entry(key, result);
    }

    /**
     * @param name
     * @param value
     */
    public static void setAttribute(String name, Object value)
    {
        attributes.put(name, value);
    }

    /**
     * @param name
     * @return Object
     */
    public static Object getAttribute(String name)
    {
        return attributes.get(name);
    }

    /**
     * @param resource
     * @param charset
     * @return Map<String, String>
     */
    private static Map<String, String> load(String resource, String charset)
    {
        return load(ExpressionFactory.class.getClassLoader().getResourceAsStream(resource), charset);
    }

    /**
     * @param inputStream
     * @param charset
     * @return Map<String, String>
     */
    private static Map<String, String> load(InputStream inputStream, String charset)
    {
        Map<String, String> map = new HashMap<String, String>();

        if(inputStream != null)
        {
            BufferedReader reader = null;
            InputStreamReader inputStreamReader = null;

            try
            {
                String line = null;
                inputStreamReader = new InputStreamReader(inputStream, charset);
                reader = new BufferedReader(inputStreamReader);

                while((line = reader.readLine()) != null)
                {
                    line = line.trim();

                    if(line.length() < 1)
                    {
                        continue;
                    }

                    if(line.startsWith("#"))
                    {
                        continue;
                    }

                    int i = line.indexOf("=");

                    if(i > -1)
                    {
                        String name = line.substring(0, i).trim();
                        String value = line.substring(i + 1).trim();

                        if(name.length() > 0 && value.length() > 0)
                        {
                            map.put(name, value);
                        }
                    }
                }
            }
            catch(IOException e)
            {
            }
        }

        return map;
    }

    /**
     * <p>Title: Entry</p>
     * <p>Description: </p>
     * <p>Copyright: Copyright (c) 2006</p>
     * @author xuesong.net
     * @version 1.0
     */
    public static class Entry
    {
        private String name;
        private Object value;

        public Entry(String name, Object value)
        {
            this.name = name;
            this.value = value;
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
         * @return the value
         */
        public Object getValue()
        {
            return this.value;
        }

        /**
         * @param value the value to set
         */
        public void setValue(Object value)
        {
            this.value = value;
        }
    }

    public static void main(String[] args)
    {
        System.out.println("---------- objects ----------");

        for(Map.Entry<String, Object> entry : attributes.entrySet())
        {
            String key = entry.getKey();
            Object value = entry.getValue();

            if(value != null && (value instanceof String) == false)
            {
                System.out.println(key + ": " + value);
            }
        }

        System.out.println("---------- variables ----------");

        for(Map.Entry<String, Object> entry : attributes.entrySet())
        {
            String key = entry.getKey();
            Object value = entry.getValue();

            if(value == null || value instanceof String)
            {
                System.out.println(key + ": " + value);
            }
        }
    }
}
