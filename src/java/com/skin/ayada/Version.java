/*
 * $RCSfile: Version.java,v $$
 * $Revision: 1.1  $
 * $Date: 2013-12-21  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada;

import java.util.Calendar;

/**
 * <p>Title: Version</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class Version
{
    public static final int majorVersion = 1;
    public static final int minorVersion = 0;
    public static final String name = "skinfourm";
    public static final String developer = "skinsoft";
    public static final String version = getVersion();

    /**
     * @return int
     */
    public static int getMajorVersion()
    {
        return majorVersion;
    }

    /**
     * @return int
     */
    public static int getMinorVersion()
    {
        return minorVersion;
    }

    /**
     * @return the name
     */
    public static String getName()
    {
        return name;
    }

    /**
     * @return the developer
     */
    public static String getDeveloper()
    {
        return developer;
    }

    /**
     * @return String
     */
    public static String getVersion()
    {
        return getVersion(majorVersion, minorVersion);
    }

    /**
     * @param majorVersion
     * @param minorVersion
     * @return String
     */
    public static String getVersion(int majorVersion, int minorVersion)
    {
        StringBuilder buffer = new StringBuilder();
        buffer.append((majorVersion + minorVersion / 100));
        buffer.append(".");
        buffer.append((minorVersion % 100) / 10);
        buffer.append(".");
        buffer.append(minorVersion % 10);
        return buffer.toString();
    }

    /**
     * @return String
     */
    public static String getCopyright()
    {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        return "(C) Copyright 1998-" + year + developer;
    }

    public static void main(String[] args)
    {
        for(int i = 0; i < 1000; i++)
        {
            System.out.println(Version.getVersion(0, i));
        }
    }
}
