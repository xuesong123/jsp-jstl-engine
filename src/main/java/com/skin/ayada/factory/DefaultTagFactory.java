/*
 * $RCSfile: DefaultTagFactory.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-06 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skin.ayada.runtime.TagFactory;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.util.ClassUtil;

/**
 * <p>Title: DefaultTagFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class DefaultTagFactory implements TagFactory
{
    private String tagName;
    private String className;
    private static final Logger logger = LoggerFactory.getLogger(DefaultTagFactory.class);

    /**
     * @return Tag
     */
    @Override
    public Tag create()
    {
        try
        {
            return (Tag)(ClassUtil.getInstance(this.className));
        }
        catch(Exception e)
        {
            logger.warn(e.getMessage(), e);
        }

        return null;
    }

    /**
     * @param tagName the tagName to set
     */
    @Override
    public void setTagName(String tagName)
    {
        this.tagName = tagName;
    }

    /**
     * @return the tagName
     */
    @Override
    public String getTagName()
    {
        return this.tagName;
    }

    /**
     * @param className the className to set
     */
    @Override
    public void setClassName(String className)
    {
        this.className = className;
    }

    /**
     * @return the className
     */
    @Override
    public String getClassName()
    {
        return this.className;
    }
}
