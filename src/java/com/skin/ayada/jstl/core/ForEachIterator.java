/*
 * $RCSfile: ForEachIterator.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-12 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

/**
 * <p>Title: ForEachIterator</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public interface ForEachIterator
{
    /**
     * @return boolean
     */
    public boolean hasNext();

    /**
     * @return Object
     */
    public Object next();
}
