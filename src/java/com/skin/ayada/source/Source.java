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
    private String source;
    private int type;
    
    public Source()
    {
    }

    /**
     * @param home
     * @param source
     */
    public Source(String home, String source)
    {
        this(home, source, 0);
    }

    /**
     * @param home
     * @param source
     * @param type
     */
    public Source(String home, String source, int type)
    {
        this.home = home;
        this.source = source;
        this.type = type;
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

    /**
     * @return the source
     */
    public String getSource()
    {
        return this.source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source)
    {
        this.source = source;
    }

    /**
     * @return the type
     */
    public int getType()
    {
        return this.type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type)
    {
        this.type = type;
    }
}
