/*
 * $RCSfile: DefaultTagFactory.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-11-06 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.factory;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skin.ayada.ExpressionContext;
import com.skin.ayada.TagFactory;
import com.skin.ayada.statement.Attribute;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.util.ClassUtil;
import com.skin.ayada.util.ELUtil;
import com.skin.ayada.util.TagUtil;

/**
 * <p>Title: DefaultTagFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class DefaultTagFactory implements TagFactory {
    private String className;
    private static final Logger logger = LoggerFactory.getLogger(DefaultTagFactory.class);

    /**
     * default
     */
    public DefaultTagFactory() {
    }

    /**
     * @param className
     */
    public DefaultTagFactory(String className) {
        this.className = className;
    }

    /**
     * @return Tag
     */
    @Override
    public Tag create() {
        try {
            return (Tag)(ClassUtil.getInstance(this.className));
        }
        catch(Exception e) {
            logger.warn(e.getMessage(), e);
        }
        return null;
    }

    /**
     * @param tag
     * @param attributes
     * @param expressionContext
     */
    @Override
    public void setAttributes(Tag tag, Map<String, Attribute> attributes, ExpressionContext expressionContext) {
        try {
            TagUtil.setAttributes(tag, attributes, expressionContext);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param expressionContext
     * @param expression
     * @param expectType
     * @return Object
     */
    protected Object getValue(ExpressionContext expressionContext, Object expression, Class<?> expectType) {
        Attribute attribute = (Attribute)expression;

        if(attribute != null) {
            return ELUtil.getValue(expressionContext, attribute, expectType);
        }
        return null;
    }
}
