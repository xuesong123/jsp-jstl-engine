/*
 * $RCSfile: ClassPathSourceFactory.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-4 $
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
public class ClassPathSourceFactory extends SourceFactory
{
    private String home;
    private ClassLoader classLoader;

    /**
     * @param home
     */
    public ClassPathSourceFactory(String home)
    {
        this(ClassPathSourceFactory.class.getClassLoader(), home);
    }

    /**
     * @param classLoader
     * @param home
     */
    public ClassPathSourceFactory(ClassLoader classLoader, String home)
    {
        if(home == null)
        {
            this.home = ".";
        }
        else
        {
            this.home = home.trim();
        }

        this.home = this.home.replace('\\', '/');

        if(this.home.endsWith("/"))
        {
            this.home = this.home.substring(0, this.home.length() - 1);
        }

        this.classLoader = classLoader;
        URL url = classLoader.getResource(this.home);

        if(url == null)
        {
            throw new RuntimeException(this.home + " not found !");
        }
    }

    /**
     * @param path
     * @param charset
     * @return Source
     */
    @Override
    public Source getSource(String path, String charset)
    {
        if(path == null)
        {
            throw new RuntimeException("path must be not null !");
        }

        String temp = path.trim();
        String encoding = (charset != null ? charset : "UTF-8");

        if(temp.charAt(0) == '/')
        {
            temp = this.home + temp;
        }
        else
        {
            temp = this.home + "/" + temp;
        }

        ClassLoader classLoader = ClassPathSourceFactory.class.getClassLoader();
        URL homeUrl = classLoader.getResource(this.home);
        URL tempUrl = classLoader.getResource(temp);

        if(homeUrl == null)
        {
            throw new RuntimeException(this.home + " not exists !");
        }

        if(tempUrl == null)
        {
            throw new RuntimeException(temp + " not exists !");
        }

        String homePath = homeUrl.getPath();
        String realPath = tempUrl.getPath();

        if(realPath.startsWith(homePath) == false)
        {
            throw new RuntimeException(realPath + " can't access !");
        }

        InputStream inputStream = null;

        try
        {
            inputStream = tempUrl.openStream();
            return new Source(this.home, realPath, IO.read(inputStream, encoding, 4096), this.getSourceType(realPath));
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            IO.close(inputStream);
        }
    }

    /**
     * @param path
     * @return long
     */
    @Override
    public long getLastModified(String path)
    {
        return 0L;
    }

    /**
     * @param classLoader the classLoader to set
     */
    public void setClassLoader(ClassLoader classLoader)
    {
        this.classLoader = classLoader;
    }
    
    /**
     * @return the classLoader
     */
    public ClassLoader getClassLoader()
    {
        return this.classLoader;
    }

    /**
     * @return the home
     */
    public String getHome()
    {
        return this.home;
    }

    /**
     * @param home the home to set
     */
    public void setHome(String home)
    {
        this.home = home;
    }
}
