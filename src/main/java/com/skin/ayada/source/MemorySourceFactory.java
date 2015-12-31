/*
 * $RCSfile: MemorySourceFactory.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-04 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.source;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: MemorySourceFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class MemorySourceFactory extends SourceFactory {
    private Map<String, Source> cache;

    /**
     * @param source
     */
    public MemorySourceFactory(Source source) {
        this.cache = new HashMap<String, Source>();
        this.cache.put(source.getPath(), source);
    }

    /**
     * @param path
     * @param source
     */
    public void setSource(String path, Source source) {
    	this.cache.put(path, source);
    }

    /**
     * @param path
     * @param encoding
     * @return Source
     */
    @Override
    public Source getSource(String path, String encoding) {
        return this.cache.get(path);
    }

    /**
     * @param path
     * @return long
     */
    @Override
    public long getLastModified(String path) {
        Source source = this.getSource(path, null);

        if(source == null) {
            throw new RuntimeException(path + " can't access !");
        }
        return source.getLastModified();
    }

    /**
     * @param path
     * @return boolean
     */
    @Override
    public boolean exists(String path) {
        return (this.getSource(path, null) != null);
    }
}
