/*
 * $RCSfile: TagUtil.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-02-19 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.util;

import java.lang.reflect.Method;
import java.util.Map;

import com.skin.ayada.ExpressionContext;
import com.skin.ayada.jstl.TagLibrary;
import com.skin.ayada.jstl.TagLibraryFactory;
import com.skin.ayada.statement.Attribute;
import com.skin.ayada.tagext.DynamicAttributes;
import com.skin.ayada.tagext.Tag;

/**
 * <p>Title: TagUtil</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class TagUtil {
    private TagUtil() {
    }

    /**
     * @param tagName
     * @return Tag
     */
    public static Tag create(String tagName) {
        TagLibrary tagLibrary = TagLibraryFactory.getStandardTagLibrary();
        String className = tagLibrary.getTagClassName(tagName);

        try {
            return (Tag)(ClassUtil.getInstance(className));
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param tag
     * @param attributes
     * @param expressionContext
     * @throws Exception
     */
    public static void setAttributes(Tag tag, Map<String, Attribute> attributes, ExpressionContext expressionContext) throws Exception {
        if(attributes == null || attributes.size() < 1) {
            return;
        }

        if(tag instanceof DynamicAttributes) {
            DynamicAttributes dynamicAttributes = (DynamicAttributes)tag;

            for(Map.Entry<String, Attribute> entry : attributes.entrySet()) {
                String name = entry.getKey();
                Attribute attribute = entry.getValue();
                Object argument = ELUtil.getValue(expressionContext, attribute, Object.class);
                dynamicAttributes.setDynamicAttribute(name, argument);
            }
            return;
        }

        Class<?> type = tag.getClass();
        Map<String, Method> setMethodMap = Reflect.getSetMethodMap(type);

        for(Map.Entry<String, Attribute> entry : attributes.entrySet()) {
            String name = entry.getKey();
            Method method = setMethodMap.get(name);

            if(method != null) {
                Attribute attribute = entry.getValue();
                Class<?>[] parameterTypes = method.getParameterTypes();
                Class<?> parameterType = parameterTypes[0];
                Object argument = ELUtil.getValue(expressionContext, attribute, parameterType);

                if(argument == null && parameterType.isPrimitive()) {
                    continue;
                }
                method.invoke(tag, new Object[]{argument});
            }
            else {
                throw new Exception("NoSuchMethodException: " + tag.getClass().getName() + "." + name);
            }
        }
    }
}
