/*
 * $RCSfile: SourceFactory.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-11-04 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import com.skin.ayada.config.TemplateConfig;
import com.skin.ayada.scanner.Scanner.Visitor;
import com.skin.ayada.util.Path;
import com.skin.ayada.util.StringUtil;

/**
 * <p>Title: SourceFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public abstract class SourceFactory {
    private String sourcePattern = "jsp,jspf,jspx,tpl";

    /**
     * @param visitor
     * @throws Exception
     */
    public abstract void accept(Visitor visitor) throws Exception;

    /**
     * @param path
     * @return URL
     */
    public abstract URL getResource(String path);

    /**
     * @param path
     * @return InputStream
     */
    public InputStream getInputStream(String path) {
        URL url = this.getResource(path);

        if(url != null) {
            try {
                return url.openStream();
            }
            catch(IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    /**
     * @param path
     * @param charset
     * @return Reader
     */
    public Reader getReader(String path, String charset) {
        InputStream inputStream = this.getInputStream(path);

        if(inputStream != null) {
            try {
                return new InputStreamReader(inputStream, charset);
            }
            catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    /**
     * @param path
     * @param charset
     * @return String
     * @throws IOException
     */
    public String getContent(String path, String charset) throws IOException {
        Reader reader = this.getReader(path, charset);

        if(reader == null) {
            throw new IOException(path + " not exists.");
        }

        try {
            int length = 0;
            char[] cbuf = new char[8192];
            StringBuilder buffer = new StringBuilder();

            while((length = reader.read(cbuf, 0, 8192)) > -1) {
                buffer.append(cbuf, 0, length);
            }
            return buffer.toString();
        }
        finally {
            try {
                reader.close();
            }
            catch(IOException e) {
            }
        }
    }

    /**
     * @param path
     * @return Source
     */
    public abstract Source getSource(String path);

    /**
     * @param path
     * @return long
     */
    public abstract long getLastModified(String path);

    /**
     * @param path
     * @return boolean
     */
    public abstract boolean exists(String path);

    /**
     * @param path
     * @return int
     */
    public int getSourceType(String path) {
        if(this.sourcePattern == null) {
            this.sourcePattern = TemplateConfig.getSourcePattern();
        }

        String fileType = this.getExtension(path).toLowerCase();

        if(StringUtil.contains(this.sourcePattern, fileType)) {
            return Source.SCRIPT;
        }
        return Source.STATIC;
    }

    /**
     * @param url
     * @return long
     */
    public long getLastModified(URL url) {
        String path = url.getPath();

        if(path.startsWith("file:")) {
            path = path.substring(5);
        }

        int k = path.indexOf('!');

        if(k > 0) {
            path = path.substring(0, k);
        }
        return new File(path).lastModified();
    }

    /**
     * @param path
     * @return String
     */
    public String getExtension(String path) {
        return Path.getExtension(path);
    }

    /**
     * @param path
     * @return String
     */
    public String getStrictPath(String path) {
        return Path.getStrictPath(path);
    }

    /**
     * @return the sourcePattern
     */
    public String getSourcePattern() {
        return this.sourcePattern;
    }

    /**
     * @param sourcePattern the sourcePattern to set
     */
    public void setSourcePattern(String sourcePattern) {
        this.sourcePattern = sourcePattern;
    }
}
