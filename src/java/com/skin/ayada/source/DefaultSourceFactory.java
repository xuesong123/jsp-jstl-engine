/*
 * $RCSfile: DefaultSourceFactory.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-4 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.source;

import java.io.File;
import java.io.IOException;

import com.skin.ayada.util.IO;

/**
 * <p>Title: DefaultSourceFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class DefaultSourceFactory extends SourceFactory
{
    private String home;

    /**
     * @param home
     */
    public DefaultSourceFactory(String home)
    {
        if(home == null)
        {
            this.home = ".";
        }
        else
        {
            this.home = home;
        }

        File root = new File(this.home);

        try
        {
            this.home = root.getCanonicalPath();
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
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

        File file = new File(this.home, path);

        try
        {
            if(file.getCanonicalPath().startsWith(this.home) == false)
            {
                throw new RuntimeException("t:include error: " + file.getAbsolutePath() + " can't access !");
            }
        }
        catch(RuntimeException e)
        {
            throw e;
        }
        catch(Exception e)
        {
            throw new RuntimeException("t:include error: " + file.getAbsolutePath() + " can't access !", e);
        }

        if(file.exists() == false)
        {
            throw new RuntimeException("t:include error: " + file.getAbsolutePath() + " not exists !");
        }

        if(file.isFile() == false)
        {
            throw new RuntimeException("t:include error: " + file.getAbsolutePath() + " not file !");
        }

        try
        {
            return new Source(home, IO.read(file, encoding, 4096), this.getSourceType(file.getName()));
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
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
