/*
 * $RCSfile: URLScanner.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-01-13 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.scanner;

import java.io.File;
import java.net.URL;

/**
 * <p>Title: URLScanner</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class URLScanner implements Scanner {
    private URL url;

    /**
     * @param url
     */
    public URLScanner(URL url) {
        this.url = url;
    }

    /**
     * @param visitor
     * @throws Exception
     */
    @Override
    public void accept(Visitor visitor) throws Exception {
        accept(this.url, visitor);
    }

    /**
     * @param url
     * @param visitor
     * @throws Exception
     */
    public static void accept(URL url, Visitor visitor) throws Exception {
        if("file".equals(url.getProtocol())) {
            File file = new File(url.getFile());

            if(file.isDirectory()) {
                FileScanner.accept(file, visitor);
                return;
            }
        }
        JarScanner.accept(url, visitor);
    }
}
