/*
 * $RCSfile: WebUtil.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-03-10 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Title: WebUtil</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class WebUtil {
    private static final String root = guess();
    private static final Logger logger = LoggerFactory.getLogger(WebUtil.class);

    /**
     * @return String
     */
    public static String getRoot() {
        return root;
    }

    /**
     * @param path
     * @return String
     */
    public static String getRealPath(String path) {
        if(root == null) {
            return null;
        }

        try {
            String realPath = Path.getStrictPath(new File(root, path).getCanonicalPath());

            if(realPath.startsWith(root)) {
                return realPath;
            }
        }
        catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
        return null;
    }

    /**
     * @return String
     */
    public static String getClassPath() {
        String systemClassPath = ClassPath.getSystemClassPath();

        if(root == null) {
            return systemClassPath;
        }

        String seperator = ";";
        StringBuilder buffer = new StringBuilder();
        File lib = new File(getRealPath("/WEB-INF/lib"));

        if(System.getProperty("os.name").indexOf("Windows") < 0) {
            seperator = ":";
        }

        buffer.append(systemClassPath);
        buffer.append(seperator);

        if(lib.exists()) {
            File[] files = lib.listFiles();

            if(files != null && files.length > 0) {
                for(File file : files) {
                    buffer.append(file.getAbsolutePath());
                    buffer.append(seperator);
                }
            }
        }

        File clazz = new File(getRealPath("/WEB-INF/classes"));

        if(clazz.exists() && clazz.isDirectory()) {
            buffer.append(clazz.getAbsolutePath());
            buffer.append(seperator);
        }

        if(buffer.length() > 0) {
            buffer.delete(buffer.length() - seperator.length(), buffer.length());
        }
        return buffer.toString();
    }

    /**
     * @param servletContext
     * @return String
     */
    public static String getClassPath(ServletContext servletContext) {
        String seperator = ";";
        StringBuilder buffer = new StringBuilder();
        File lib = new File(servletContext.getRealPath("/WEB-INF/lib"));

        if(System.getProperty("os.name").indexOf("Windows") < 0) {
            seperator = ":";
        }

        if(lib.exists()) {
            File[] files = lib.listFiles();

            if(files != null && files.length > 0) {
                for(File file : files) {
                    buffer.append(file.getAbsolutePath());
                    buffer.append(seperator);
                }
            }
        }

        File clazz = new File(servletContext.getRealPath("/WEB-INF/classes"));

        if(clazz.exists() && clazz.isDirectory()) {
            buffer.append(clazz.getAbsolutePath());
            buffer.append(seperator);
        }

        if(buffer.length() > 0) {
            buffer.delete(buffer.length() - seperator.length(), buffer.length());
        }
        return buffer.toString();
    }

    /**
     * @param servletContext
     * @return String
     */
    public static String getContextPath(ServletContext servletContext) {
        try {
            Method method = ServletContext.class.getMethod("getContextPath", new Class[0]);
            return (String)(method.invoke(servletContext, new Object[0]));
        }
        catch(Exception e) {
        }
        return null;
    }

    /**
     * @return String
     */
    private static String guess() {
        try {
            String path = WebUtil.class.getClassLoader().getResource("").toURI().getPath();

            if(path == null) {
                return null;
            }
            
            File file = new File(path);
            File root = getParent(file, "/WEB-INF/classes");
            
            if(root == null) {
                root = getParent(file, "/WEB-INF/lib");
            }
            
            if(root != null) {
                return Path.getStrictPath(root.getCanonicalPath());
            }
        }
        catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
        return null;
    }

    /**
     * @param file
     * @param name
     * @return String
     */
    private static File getParent(File file, String name) {
        try {
            String path = Path.getStrictPath(file.getCanonicalPath());
            int k = path.indexOf(name);
    
            if(k > -1) {
                return new File(path.substring(0, k));
            }
        }
        catch(IOException e) {
            logger.warn(e.getMessage(), e);
        }
        return null;
    }
}
