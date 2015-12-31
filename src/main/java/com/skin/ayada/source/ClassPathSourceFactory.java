/*
 * $RCSfile: ClassPathSourceFactory.java,v $$
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

import com.skin.ayada.util.IO;

/**
 * <p>Title: ClassPathSourceFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class ClassPathSourceFactory extends SourceFactory {
    private ClassLoader classLoader;

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
     * @param path
     * @param encoding
     * @return Source
     */
    @Override
    public Source getSource(String path, String encoding) {
        URL url = this.getResource(path);
        String realPath = url.getPath();
        InputStream inputStream = null;

        try {
            inputStream = url.openStream();
            long lastModified = this.getLastModified(url);
            String content = IO.read(inputStream, encoding, 4096);
            String realHome = this.getClassLoader().getResource(this.getHome()).getPath();
            return new Source(realHome, path, content, this.getSourceType(realPath), lastModified);
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            IO.close(inputStream);
        }
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
     * @param path
     * @return URL
     */
    public URL getResource(String path) {
        if(path == null) {
            throw new RuntimeException("path must be not null !");
        }

        String temp = path.trim().replace('\\', '/');

        if(temp.charAt(0) == '/') {
            temp = this.getHome() + temp;
        }
        else {
            temp = this.getHome() + "/" + temp;
        }

        while(temp.startsWith("/")) {
            temp = temp.substring(1);
        }

        ClassLoader classLoader = this.getClassLoader();
        URL homeUrl = classLoader.getResource(this.getHome());
        URL realUrl = classLoader.getResource(temp);

        if(homeUrl == null) {
            throw new RuntimeException(this.getHome() + " not exists !");
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
    @Override
    public void setHome(String home) {
        String path = home;

        if(path == null) {
            path = ".";
        }
        else {
            path = path.trim();
        }

        path = path.replace('\\', '/');

        while(path.startsWith("/")) {
            path = path.substring(1);
        }

        if(path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }

        ClassLoader classLoader = this.getClassLoader();
        URL url = classLoader.getResource(path);

        if(url == null) {
            throw new RuntimeException(path + " not found !");
        }
        super.setHome(path);
    }
}
