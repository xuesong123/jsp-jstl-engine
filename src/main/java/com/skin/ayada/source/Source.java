/*
 * $RCSfile: Source.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-04 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.source;

/**
 * <p>Title: Source</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class Source
{
    private String home;
    private String path;
    private String source;
    private int type;
    private long lastModified;
    public static final int STATIC = 0;
    public static final int SCRIPT = 1;

    public Source()
    {
    }

    /**
     * @param home
     * @param path
     * @param source
     * @param type
     */
    public Source(String home, String path, String source, int type)
    {
        this(home, path, source, type, 0L);
    }

    /**
     * @param home
     * @param path
     * @param source
     * @param type
     * @param lastModified
     */
    public Source(String home, String path, String source, int type, long lastModified)
    {
        this.home = home;
        this.path = path;
        this.type = type;
        this.source = source;
        this.lastModified = lastModified;
    }

    /**
     * @param type
     * @return int
     */
    public static int valueOf(String type, int defaultValue)
    {
        if(type == null)
        {
            return defaultValue;
        }
        else if(type.equalsIgnoreCase("static"))
        {
            return Source.STATIC;
        }
        else if(type.equalsIgnoreCase("script"))
        {
            return Source.SCRIPT;
        }
        else
        {
            return defaultValue;
        }
    }

    /**
     * @param home the home to set
     */
    public void setHome(String home)
    {
        this.home = home;
    }

    /**
     * @return the home
     */
    public String getHome()
    {
        return this.home;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path)
    {
        this.path = path;
    }

    /**
     * @return the path
     */
    public String getPath()
    {
        return this.path;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source)
    {
        this.source = source;
    }

    /**
     * @return the source
     */
    public String getSource()
    {
        return this.source;
    }

    /**
     * @return the lastModified
     */
    public long getLastModified()
    {
        return this.lastModified;
    }

    /**
     * @param lastModified the lastModified to set
     */
    public void setLastModified(long lastModified)
    {
        this.lastModified = lastModified;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type)
    {
        this.type = type;
    }

    /**
     * @return the type
     */
    public int getType()
    {
        return this.type;
    }
}
