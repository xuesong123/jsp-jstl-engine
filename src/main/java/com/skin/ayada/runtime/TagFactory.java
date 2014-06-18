/*
 * $RCSfile: TagFactory.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-19 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.runtime;

import com.skin.ayada.tagext.Tag;

/**
 * <p>Title: TagFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public interface TagFactory
{
    /**
     * @return Tag
     */
    public Tag create();

    /**
     * @param tagName
     */
    public void setTagName(String tagName);

    /**
     * @return String
     */
    public String getTagName();

    /**
     * @param className
     */
    public void setClassName(String className);

    /**
     * @return String
     */
    public String getClassName();
}