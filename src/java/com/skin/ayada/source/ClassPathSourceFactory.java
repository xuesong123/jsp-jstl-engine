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
public class ClassPathSourceFactory implements SourceFactory
{
    private String home;

    /**
     * @param home
     */
    public ClassPathSourceFactory(String home)
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

        URL url = ClassPathSourceFactory.class.getClassLoader().getResource(this.home);

        if(url == null)
        {
            throw new RuntimeException(this.home + " not found !");
        }
    }

    /**
     * @param path
     * @param encoding
     * @return Source
     */
    @Override
    public Source getSource(String path, String encoding)
    {
        if(path == null)
        {
            throw new RuntimeException("t:include error: attribute 'file' not exists !");
        }

        if(encoding == null)
        {
            encoding = "UTF-8";
        }

        String temp = path.trim();

        if(temp.charAt(0) == '/')
        {
            temp = this.home + temp;
        }
        else
        {
            temp = this.home + "/" + temp;
        }

        URL homeUrl = ClassPathSourceFactory.class.getClassLoader().getResource(this.home);
        URL tempUrl = ClassPathSourceFactory.class.getClassLoader().getResource(temp);

        if(homeUrl == null)
        {
            throw new RuntimeException(home + " not exists !");
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
            return new Source(this.home, IO.read(inputStream, encoding, 4096), 0);
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
