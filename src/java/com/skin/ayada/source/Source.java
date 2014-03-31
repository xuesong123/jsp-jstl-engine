/*
 * $RCSfile: Source.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-4 $
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
    private long lastModified;
    private int type;
    public static final int STATIC = 0;
    public static final int SCRIPT = 1;

    public Source()
    {
    }

    /**
     * @param home
     * @param source
     * @param type
     */
    public Source(String home, String path, String source, int type)
    {
        this.home = home;
        this.path = path;
        this.source = source;
        this.lastModified = 0L;
        this.type = type;
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
