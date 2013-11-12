/*
 * $RCSfile: SimpleForEachIterator.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-12 $
 *
 * Copyright (C) 2008 WanMei, Inc. All rights reserved.
 *
 * This software is the proprietary information of WanMei, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import java.util.Iterator;

/**
 * <p>Title: SimpleForEachIterator</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class SimpleForEachIterator implements ForEachIterator
{
    private Iterator<?> iterator;

    public SimpleForEachIterator(Iterator<?> iterator)
    {
        this.iterator = iterator;
    }

    public boolean hasNext()
    {
        return this.iterator.hasNext();
    }

    public Object next()
    {
        return this.iterator.next();
    }
}
