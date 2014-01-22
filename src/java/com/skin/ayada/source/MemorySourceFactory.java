/*
 * $RCSfile: MemorySourceFactory.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-4 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.source;

/**
 * <p>Title: MemorySourceFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class MemorySourceFactory extends SourceFactory
{
    private Source source;

    /**
     * @param home
     */
    public MemorySourceFactory(Source source)
    {
        this.source = source;
    }

    /**
     * @param path
     * @return long
     */
    @Override
    public long getLastModified(String path)
    {
        return this.source.getLastModified();
    }

    /**
     * @param path
     * @param encoding
     * @return Source
     */
    @Override
    public Source getSource(String path, String encoding)
    {
        return this.source;
    }
}
