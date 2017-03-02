/*
 * $RCSfile: PropertyResource.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-10-30 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Title: PropertyResource</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class PropertyResource {
    private static final Logger logger = LoggerFactory.getLogger(PropertyResource.class);

    /**
     * @param resource
     * @param charset
     * @return Map<String, String>
     */
    public static Map<String, String> load(String resource, String charset) {
        InputStream inputStream = null;
        URL url = PropertyResource.class.getClassLoader().getResource(resource);

        if(url != null) {
            if(logger.isDebugEnabled()) {
                logger.debug("load: " + url.toString());
            }

            try {
                inputStream = url.openStream();
            }
            catch(IOException e) {
            }
        }
        try {
            return load(inputStream, charset);
        }
        finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                }
                catch(IOException e) {
                }
            }
        }
    }

    /**
     * @param inputStream
     * @param charset
     * @return Map<String, String>
     */
    public static Map<String, String> load(InputStream inputStream, String charset) {
        Map<String, String> map = new HashMap<String, String>();

        if(inputStream != null) {
            BufferedReader reader = null;
            InputStreamReader inputStreamReader = null;

            try {
                String line = null;
                inputStreamReader = new InputStreamReader(inputStream, charset);
                reader = new BufferedReader(inputStreamReader);

                while((line = reader.readLine()) != null) {
                    line = line.trim();

                    if(line.length() < 1) {
                        continue;
                    }

                    if(line.startsWith("#")) {
                        continue;
                    }

                    if(line.startsWith("@import ")) {
                        Map<String, String> sub = load(line.substring(8).trim(), charset);
                        map.putAll(sub);
                    }

                    int i = line.indexOf("=");

                    if(i > -1) {
                        String name = line.substring(0, i).trim();
                        String value = line.substring(i + 1).trim();

                        if(name.length() > 0 && value.length() > 0) {
                            map.put(name, value);
                        }
                    }
                }
            }
            catch(IOException e) {
            }
        }
        return map;
    }

    /**
     * @param map
     * @param writer
     */
    public static void save(Map<String, String> map, java.io.Writer writer) {
        throw new java.lang.UnsupportedOperationException("\"save\" method is unsupported !");
    }
}
