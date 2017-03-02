/*
 * $RCSfile: ClassPathResource.java,v $
 * $Revision: 1.1 $
 * $Date: 2009-3-26 $
 *
 * Copyright (C) 2005 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Title: ClassPathResource</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class ClassPathResource {
    private static final ClassLoader classLoader = ClassPathResource.class.getClassLoader();
    private static final Logger logger = LoggerFactory.getLogger(ClassPathResource.class);

    /**
     * @param name
     * @return URL
     */
    public static URL getResource(String name) {
        return classLoader.getResource(name);
    }

    /**
     * @param name
     * @return URL
     */
    public static Enumeration<URL> getResources(String name) {
        try {
            return classLoader.getResources(name);
        }
        catch(IOException e) {
        }
        return null;
    }

    /**
     * @param name
     * @return InputStream
     */
    public static InputStream getResourceAsStream(String name) {
        return classLoader.getResourceAsStream(name);
    }

    /**
     * @param name
     * @return String
     */
    public static String getResourceAsString(String name) {
        InputStreamReader inputStreamReader = null;
        InputStream inputStream = getResourceAsStream(name);

        if(inputStream == null) {
            return null;
        }

        try {
            StringBuilder buffer = new StringBuilder(4096);
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");

            int length = 0;
            char[] cbuf = new char[4096];

            while((length = inputStreamReader.read(cbuf)) > 0) {
                buffer.append(cbuf, 0, length);
            }
            return buffer.toString();
        }
        catch(IOException e) {
            logger.warn(e.getMessage(), e);
        }
        finally {
            if(inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                }
                catch(IOException e) {
                }
            }
            try {
                inputStream.close();
            }
            catch(IOException e) {
            }
        }
        return null;
    }
}
