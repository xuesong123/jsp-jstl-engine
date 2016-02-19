/*
 * $RCSfile: ClassPath.java,v $$
 * $Revision: 1.1 $
 * $Date: 2016-1-7 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.util;

import java.io.File;
import java.util.regex.Pattern;

/**
 * <p>Title: ClassPath</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class ClassPath {
    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(getSystemClassPath());
    }

    /**
     * @return String
     */
    public static String getClassPath() {
        return WebUtil.getClassPath();
    }

    /**
     * @return String
     */
    public static String getSystemClassPath() {
        String boot = System.getProperty("sun.boot.class.path");
        String classPath = System.getProperty("java.class.path");

        if(boot != null && boot.length() > 0) {
            classPath = classPath + File.pathSeparatorChar + boot;
        }

        Pattern pattern = Pattern.compile(String.valueOf(File.pathSeparatorChar));
        String[] path = pattern.split(classPath);
        StringBuilder buffer = new StringBuilder();

        for(int i = 0; i < path.length; i++) {
            File file = new File(path[i]);

            if(file.exists() || file.isDirectory()) {
                if(buffer.length() > 0) {
                    buffer.append(File.pathSeparatorChar);
                }
                buffer.append(path[i]);
            }
        }
        return buffer.toString();
    }
}
