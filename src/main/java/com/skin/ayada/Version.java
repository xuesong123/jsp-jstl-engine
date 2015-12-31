/*
 * $RCSfile: Version.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-12-21 $
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
public class Version {
    public static final int LEVEL = 4;
    public static final int majorVersion = 1;
    public static final int minorVersion = 10;
    public static final String name = "ayada";
    public static final String developer = "skin";
    public static final String version = getVersion();

    /**
     * @return int
     */
    public static int getMajorVersion() {
        return majorVersion;
    }

    /**
     * @return int
     */
    public static int getMinorVersion() {
        return minorVersion;
    }

    /**
     * @return the name
     */
    public static String getName() {
        return name;
    }

    /**
     * @return the developer
     */
    public static String getDeveloper() {
        return developer;
    }

    /**
     * @return String
     */
    public static String getVersion() {
        return getVersion(majorVersion, minorVersion, LEVEL);
    }

    /**
     * @param majorVersion
     * @param minorVersion
     * @return String
     */
    public static String getVersion(int majorVersion, int minorVersion) {
        return getVersion(majorVersion, minorVersion, LEVEL);
    }

    /**
     * @param majorVersion
     * @param minorVersion
     * @return String
     */
    public static String getVersion(int majorVersion, int minorVersion, int level) {
        int mod = minorVersion;
        int[] temp = new int[level];
        StringBuilder buffer = new StringBuilder();

        for(int i = 0; i < level; i++) {
            temp[level - 1 - i] = mod % 10;
            mod = mod / 10;
        }

        temp[0] = majorVersion + (mod * 10) + temp[0];

        for(int i = 0; i < level; i++) {
            buffer.append(temp[i]);
            buffer.append(".");
        }

        if(buffer.length() > 0) {
            buffer.deleteCharAt(buffer.length() - 1);
        }
        return buffer.toString();
    }

    /**
     * @return String
     */
    public static String getCopyright() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        return "(C) Copyright 1998-" + year + " " + developer;
    }
}
