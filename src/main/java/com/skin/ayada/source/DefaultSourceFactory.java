/*
 * $RCSfile: DefaultSourceFactory.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-04 $
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
    public DefaultSourceFactory()
    {
    }

    /**
     * @param home
     */
    public DefaultSourceFactory(String home)
    {
        super();
        String path = null;

        if(home == null)
        {
            path = ".";
        }
        else
        {
            path = home;
        }

        File root = new File(path);

        try
        {
            path = root.getCanonicalPath();
            this.setHome(path);
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
        File file = this.getFile(path);

        try
        {
            String content = IO.read(file, encoding, 4096);
            Source source = new Source(this.getHome(), path, content, this.getSourceType(file.getName()));
            source.setLastModified(file.lastModified());
            return source;
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param path
     * @return long
     */
    @Override
    public long getLastModified(String path)
    {
        return this.getFile(path).lastModified();
    }

    /**
     * @param path
     * @return boolean
     */
    @Override
    public boolean exists(String path)
    {
        File file = null;

        try
        {
            file = this.getFile(path);
        }
        catch(Exception e)
        {
        }

        return (file != null);
    }

    /**
     * @param path
     * @return File
     */
    public File getFile(String path)
    {
        if(path == null)
        {
            throw new NullPointerException("path must be not null !");
        }

        File file = new File(this.getHome(), path);

        try
        {
            if(file.getCanonicalPath().startsWith(this.getHome()) == false)
            {
                throw new RuntimeException(file.getAbsolutePath() + " not exists !");
            }
        }
        catch(Exception e)
        {
            throw new RuntimeException(file.getAbsolutePath() + " not exists !");
        }

        if(file.exists() == false)
        {
            throw new RuntimeException(file.getAbsolutePath() + " not exists !");
        }

        if(file.isFile() == false)
        {
            throw new RuntimeException(file.getAbsolutePath() + " not exists !");
        }

        return file;
    }
}
