/*
 * $RCSfile: DefaultExpressionFactory.java,v $
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

import com.skin.ayada.Encoder;
import com.skin.ayada.ExpressionContext;
import com.skin.ayada.ExpressionFactory;
import com.skin.ayada.PageContext;
import com.skin.ayada.config.PropertyResource;
import com.skin.ayada.util.ClassUtil;

/**
 * <p>Title: DefaultExpressionFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class DefaultExpressionFactory implements ExpressionFactory {
    private static final Logger logger = LoggerFactory.getLogger(DefaultExpressionFactory.class);
    private static final DefaultExpressionFactory instance = new DefaultExpressionFactory();
    private static final Map<String, Object> attributes = getAttributes("utf-8");

    /**
     * @param pageContext
     * @return ExpressionContext
     */
    public ExpressionContext create(PageContext pageContext) {
        return new DefaultExpressionContext(pageContext.getContext());
    }

    /**
     * @param pageContext
     * @return ExpressionContext
     */
    @Override
    public ExpressionContext getExpressionContext(PageContext pageContext) {
        ExpressionContext expressionContext = this.create(pageContext);

        if(attributes != null && attributes.size() > 0) {
            for(Map.Entry<String, Object> entry : attributes.entrySet()) {
                pageContext.setAttribute(entry.getKey(), entry.getValue());
            }

            Encoder encoder = (Encoder)(attributes.get("ELEncoder"));
            expressionContext.setEncoder(encoder);
        }
        return expressionContext;
    }

    /**
     * @param pageContext
     * @return ExpressionContext
     */
    public static ExpressionContext getDefaultExpressionContext(PageContext pageContext) {
        return instance.getExpressionContext(pageContext);
    }

    /**
     * @param charset
     * @return Map<String, Object>
     */
    private static Map<String, Object> getAttributes(String charset) {
        Map<String, Object> attributes = new HashMap<String, Object>();
        Map<String, String> map = PropertyResource.load("ayada-tools.properties", charset);

        if(map.size() < 1) {
            map = PropertyResource.load("ayada-tools-default.properties", charset);
        }

        if(map.size() > 0) {
            for(Map.Entry<String, String> entry : map.entrySet()) {
                Entry pair = getEntry(entry.getKey(), entry.getValue());
                Object object = pair.getValue();

                if(object != null) {
                    attributes.put(pair.getName(), pair.getValue());
                }
                else {
                    attributes.remove(pair.getName());
                }
            }

            if(logger.isDebugEnabled()) {
                for(Map.Entry<String, Object> entry : attributes.entrySet()) {
                    if(entry.getValue() instanceof String) {
                        continue;
                    }
                    logger.debug("set " + entry.getKey() + " = " + entry.getValue());
                }

                for(Map.Entry<String, Object> entry : attributes.entrySet()) {
                    if(entry.getValue() instanceof String) {
                        logger.debug("set " + entry.getKey() + " = " + entry.getValue());
                    }
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
    public static Entry getEntry(String name, String value) {
        String key = null;
        Object result = null;

        if(value.equalsIgnoreCase("null") == false) {
            if(name.startsWith("set ")) {
                key = name.substring(4);

                try {
                    result = ClassUtil.getInstance(value);
                }
                catch(Exception e) {
                    logger.warn(e.getMessage(), e);
                }
            }
            else if(name.startsWith("var ")) {
                key = name.substring(4);
                result = value;
            }
            else {
                key = name;
                result = value;
            }
        }
        return new Entry(key, result);
    }

    /**
     * @param name
     * @param value
     */
    public static void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    /**
     * @param name
     * @return Object
     */
    public static Object getAttribute(String name) {
        return attributes.get(name);
    }

    /**
     * <p>Title: Entry</p>
     * <p>Description: </p>
     * <p>Copyright: Copyright (c) 2006</p>
     * @author xuesong.net
     * @version 1.0
     */
    public static class Entry {
        private String name;
        private Object value;

        /**
         * @param name
         * @param value
         */
        public Entry(String name, Object value) {
            this.name = name;
            this.value = value;
        }

        /**
         * @return the name
         */
        public String getName() {
            return this.name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the value
         */
        public Object getValue() {
            return this.value;
        }

        /**
         * @param value the value to set
         */
        public void setValue(Object value) {
            this.value = value;
        }
    }

    /**
     * @param attributes
     */
    public static void print(Map<String, Object> attributes) {
        System.out.println("---------- objects ----------");

        for(Map.Entry<String, Object> entry : attributes.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if(value != null && (value instanceof String) == false) {
                System.out.println(key + ": " + value);
            }
        }

        System.out.println("---------- variables ----------");

        for(Map.Entry<String, Object> entry : attributes.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if(value == null || value instanceof String) {
                System.out.println(key + ": " + value);
            }
        }
    }
}
