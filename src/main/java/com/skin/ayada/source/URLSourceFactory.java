/*
 * $RCSfile: URLSourceFactory.java,v $
 * $Revision: 1.1 $
 * $Date: 2014-12-10 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.source;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import com.skin.ayada.Source;
import com.skin.ayada.SourceFactory;
import com.skin.ayada.scanner.Scanner.Visitor;
import com.skin.ayada.scanner.URLScanner;

/**
 * <p>Title: URLSourceFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class URLSourceFactory extends SourceFactory {
    private URL url;
    private SourceFactory proxy;

    /**
     * @param url
     */
    public URLSourceFactory(URL url) {
        this.url = url;

        if("file".equals(url.getProtocol())) {
            File file = new File(url.getFile());

            if(file.isDirectory()) {
                this.proxy = new DefaultSourceFactory(file.getAbsolutePath());
                return;
            }
        }
        this.proxy = new JarSourceFactory(url);
    }

    /**
     * @param visitor
     * @throws Exception
     */
    @Override
    public void accept(Visitor visitor) throws Exception {
        URLScanner.accept(this.url, visitor);
    }

    /**
     * @param path
     * @return URL
     */
    @Override
    public URL getResource(String path) {
        return this.proxy.getResource(path);
    }

    /**
     * @param path
     * @return InputStream
     */
    @Override
    public InputStream getInputStream(String path) {
        return this.proxy.getInputStream(path);
    }

    /**
     * @param path
     * @return Source
     */
    @Override
    public Source getSource(String path) {
        return this.proxy.getSource(path);
    }

    /**
     * @param path
     * @return long
     */
    @Override
    public long getLastModified(String path) {
        return this.proxy.getLastModified(path);
    }

    /**
     * @param path
     * @return boolean
     */
    @Override
    public boolean exists(String path) {
        return this.proxy.exists(path);
    }
}
