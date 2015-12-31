/*
 * $RCSfile: DynamicAttributes.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-20 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.tagext;

/**
 * <p>Title: DynamicAttributes</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public interface DynamicAttributes {
    /**
     * @param name
     * @param value
     */
    public void setDynamicAttribute(String name, Object value);
}
