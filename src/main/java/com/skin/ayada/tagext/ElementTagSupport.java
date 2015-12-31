/*
 * $RCSfile: ElementTagSupport.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-12-13 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.tagext;

/**
 * <p>Title: ElementTagSupport</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public interface ElementTagSupport {
    /**
     * @param value
     */
    public void addElement(Object value);

    /**
     * @param index
     * @param value
     */
    public void setElement(int index, Object value);
}
