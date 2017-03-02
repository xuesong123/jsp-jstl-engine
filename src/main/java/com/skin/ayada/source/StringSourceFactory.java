/*
 * $RCSfile: MemorySourceFactory.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-11-04 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.source;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.skin.ayada.Source;
import com.skin.ayada.SourceFactory;
import com.skin.ayada.scanner.Scanner.Visitor;

/**
 * <p>Title: MemorySourceFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class StringSourceFactory extends SourceFactory {
    private Map<String, StringSource> cache;

    /**
     * default
     */
    public StringSourceFactory() {
        this.cache = new HashMap<String, StringSource>();
    }

    /**
     * @param path
     * @param content
     * @return StringSource
     */
    public static StringSource build(String path, String content) {
        StringSource source = new StringSource("/", path, Source.SCRIPT);
        source.setSource(content);
        return source;
    }

    /**
     * @param visitor
     * @throws Exception
     */
    @Override
    public void accept(Visitor visitor) throws Exception {
        for(Map.Entry<String, StringSource> entry : this.cache.entrySet()) {
            visitor.visit("/", entry.getKey());
        }
    }

    /**
     * @param source
     */
    public void setSource(StringSource source) {
        this.cache.put(source.getPath(), source);
    }

    /**
     * @param path
     * @param content
     */
    public void setSource(String path, String content) {
        StringSource source = new StringSource("/", path, Source.SCRIPT);
        source.setSource(content);
        this.cache.put(path, source);
    }

    /**
     * @param path
     * @return Source
     */
    @Override
    public StringSource getSource(String path) {
        return this.cache.get(path);
    }

    /**
     * @param path
     * @return URL
     */
    @Override
    public URL getResource(String path) {
        throw new UnsupportedOperationException("unsupported operation.");
    }

    /**
     * @param path
     * @return InputStream
     */
    @Override
    public InputStream getInputStream(String path) {
        StringSource source = this.getSource(path);

        if(source != null) {
            return new ByteArrayInputStream(source.getBytes());
        }
        return null;
    }

    /**
     * @param path
     * @return long
     */
    @Override
    public long getLastModified(String path) {
        Source source = this.getSource(path);

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
        return (this.getSource(path) != null);
    }

    /**
     * <p>Title: StringSource</p>
     * <p>Description: </p>
     * <p>Copyright: Copyright (c) 2006</p>
     * @version 1.0
     */
    public static class StringSource extends Source {
        private byte[] bytes;

        /**
         * @param home
         * @param path
         */
        public StringSource(String home, String path) {
            super(home, path, Source.SCRIPT);
        }

        /**
         * @param home
         * @param path
         * @param type
         */
        public StringSource(String home, String path, int type) {
            super(home, path, type);
        }

        /**
         * @return bytes
         */
        public byte[] getBytes() {
            return this.bytes;
        }

        /**
         * @param bytes
         */
        public void setBytes(byte[] bytes) {
            this.bytes = bytes;
        }

        /**
         * @param content
         */
        public void setSource(String content) {
            try {
                this.bytes = content.getBytes("utf-8");
            }
            catch(Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
