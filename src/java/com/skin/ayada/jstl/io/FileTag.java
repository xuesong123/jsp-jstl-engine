/*
 * $RCSfile: FileTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2014-03-26 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TagSupport;

/**
 * <p>Title: FileTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public abstract class FileTag extends TagSupport
{
    public String file;

    @Override
    public int doStartTag() throws Exception
    {
        return Tag.EVAL_PAGE;
    }

    /**
     * @param dir
     * @throws IOException
     */
    public void execute(File dir) throws IOException
    {
        if(dir == null || dir.exists() == false)
        {
            return;
        }

        if(dir.isFile())
        {
            this.process(dir);
            return;
        }

        List<String> dirs = new ArrayList<String>();
        dirs.add(dir.getAbsolutePath());

        File f = null;
        File[] list = null;

        for(int i = 0; i < dirs.size(); i++)
        {
            list = new File(dirs.get(i)).listFiles();

            if(list != null && list.length > 0)
            {
                for(int j = 0; j < list.length; j++)
                {
                    f = list[j];

                    if(f.isDirectory())
                    {
                        dirs.add(f.getAbsolutePath());
                    }
                }
            }
        }

        for(int size = dirs.size(), i = size - 1; i > -1; i--)
        {
            f = new File(dirs.get(i));

            if(f.exists())
            {
                list = f.listFiles();

                if(list != null && list.length > 0)
                {
                    for(int j = 0; j < list.length; j++)
                    {
                        if(list[j].isFile() && list[j].exists())
                        {
                            this.process(list[j]);
                        }
                    }
                }

                this.process(f);
            }
        }
    }

    /**
     * @param file
     */
    public abstract void process(File file) throws IOException;

    /**
     * @param path
     * @return String
     */
    public String getFileName(String path)
    {
        if(path != null && path.length() > 0)
        {
            int i = path.length() - 1;

            for(; i > -1; i--)
            {
                char c = path.charAt(i);

                if(c == '/' || c == '\\' || c == ':')
                {
                    break;
                }
            }

            return path.substring(i + 1);
        }

        return "";
    }

    /**
     * @param path
     * @return String
     */
    public String getExtension(String path)
    {
        int i = path.lastIndexOf(".");

        if(i > -1)
        {
            return path.substring(i + 1);
        }

        return "";
    }

    /**
     * @return the file
     */
    public String getFile()
    {
        return this.file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(String file)
    {
        this.file = file;
    }
}
