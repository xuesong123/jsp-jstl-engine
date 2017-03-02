/*
 * $RCSfile: ClassPathSourceFactory.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-11-04 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.source;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.skin.ayada.Source;
import com.skin.ayada.SourceFactory;
import com.skin.ayada.scanner.Scanner.Visitor;
import com.skin.ayada.scanner.URLScanner;
import com.skin.ayada.util.StringUtil;

/**
 * <p>Title: ClassPathSourceFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class ClassPathSourceFactory extends SourceFactory {
    private String home;
    private ClassLoader classLoader;

    /**
     *
     */
    public ClassPathSourceFactory() {
    }

    /**
     * @param home
     */
    public ClassPathSourceFactory(String home) {
        this(home, ClassPathSourceFactory.class.getClassLoader());
    }

    /**
     * @param classLoader
     * @param home
     */
    public ClassPathSourceFactory(String home, ClassLoader classLoader) {
        this.setHome(home);
        this.setClassLoader(classLoader);
    }

    /**
     * @param visitor
     * @throws Exception
     */
    @Override
    public void accept(Visitor visitor) throws Exception {
        URL url = this.getClassLoader().getResource(this.home);
        URLScanner.accept(url, visitor);
    }

    /**
     * @param path
     * @return Source
     */
    @Override
    public Source getSource(String path) {
        URL url = this.getResource(path);
        String realPath = url.getPath();
        long lastModified = this.getLastModified(url);
        String realHome = this.getClassLoader().getResource(this.home).getPath();
        return new Source(realHome, path, this.getSourceType(realPath), lastModified);
    }

    /**
     * @param path
     * @return URL
     */
    @Override
    public URL getResource(String path) {
        if(path == null) {
            throw new RuntimeException("path must be not null !");
        }

        String temp = path.trim().replace('\\', '/');

        if(temp.charAt(0) == '/') {
            temp = this.home + temp;
        }
        else {
            temp = this.home + "/" + temp;
        }

        temp = StringUtil.ltrim(temp, '/');

        ClassLoader classLoader = this.getClassLoader();
        URL homeUrl = classLoader.getResource(this.home);
        URL realUrl = classLoader.getResource(temp);

        if(homeUrl == null) {
            throw new RuntimeException(this.home + " not exists !");
        }

        if(realUrl == null) {
            throw new RuntimeException(temp + " not exists !");
        }

        String homePath = homeUrl.getPath();
        String realPath = realUrl.getPath();

        if(realPath.startsWith(homePath) == false) {
            throw new RuntimeException(realPath + " can't access !");
        }
        return realUrl;
    }

    /**
     * @param path
     * @return InputStream
     */
    @Override
    public InputStream getInputStream(String path) {
        try {
            URL url = this.getResource(path);

            if(url != null) {
                return url.openStream();
            }
        }
        catch(IOException e) {
        }
        return null;
    }

    /**
     * @param path
     * @return long
     */
    @Override
    public long getLastModified(String path) {
        URL url = this.getResource(path);
        return this.getLastModified(url);
    }

    /**
     * @param path
     * @return boolean
     */
    @Override
    public boolean exists(String path) {
        URL url = null;

        try {
            url = this.getResource(path);
        }
        catch(Exception e) {
        }
        return (url != null);
    }

    /**
     * @param classLoader the classLoader to set
     */
    public void setClassLoader(ClassLoader classLoader) {
        if(classLoader != null) {
            this.classLoader = classLoader;
        }
        else {
            this.classLoader = ClassPathSourceFactory.class.getClassLoader();
        }
    }

    /**
     * @return the classLoader
     */
    public ClassLoader getClassLoader() {
        if(this.classLoader != null) {
            return this.classLoader;
        }
        return ClassPathSourceFactory.class.getClassLoader();
    }

    /**
     * @param home the home to set
     */
    public void setHome(String home) {
        String path = home;

        if(path == null) {
            path = ".";
        }
        else {
            path = path.trim();
        }

        path = path.replace('.', '/');
        path = path.replace('\\', '/');
        path = StringUtil.trim(path, '/');

        ClassLoader classLoader = this.getClassLoader();
        URL url = classLoader.getResource(path);

        if(url == null) {
            throw new RuntimeException(path + " not found !");
        }
        this.home = path;
    }

    /**
     * @return String
     */
    public String getHome() {
        return this.home;
    }
}
