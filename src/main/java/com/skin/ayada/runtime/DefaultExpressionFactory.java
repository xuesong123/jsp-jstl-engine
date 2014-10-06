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

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skin.ayada.resource.PropertyResource;
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
    private static final Logger logger = LoggerFactory.getLogger(DefaultExpressionFactory.class);
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

            ELEncoder encoder = (ELEncoder)(attributes.get("ELEncoder"));
            expressionContext.setEncoder(encoder);
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
        Map<String, String> map = PropertyResource.load("ayada-tools.properties", charset);

        if(map.size() < 1)
        {
            map = PropertyResource.load("ayada-tools-default.properties", charset);
        }

        if(map.size() > 0)
        {
            for(Map.Entry<String, String> entry : map.entrySet())
            {
                Entry pair = getEntry(entry.getKey(), entry.getValue());
                attributes.put(pair.getName(), pair.getValue());
            }

            if(logger.isDebugEnabled())
            {
                for(Map.Entry<String, Object> entry : attributes.entrySet())
                {
                    logger.debug("set " + entry.getKey() + " = " + entry.getValue());
                }
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
    
    public static void print(Map<String, Object> attributes)
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

    public static void main(String[] args)
    {
        Map<String, String> attributes = PropertyResource.load("1.properties", "UTF-8");

        for(Map.Entry<String, String> entry : attributes.entrySet())
        {
            String key = entry.getKey();
            Object value = entry.getValue();
            System.out.println(key + ": " + value);
        }
    }
}
