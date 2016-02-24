/*
 * $RCSfile: BBCode.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-17 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.bbcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>Title: BBCode</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class BBCodeFactory {
    private static final Map<String, String> config = BBCodeFactory.load();
    private static final Map<String, String> closed = new HashMap<String, String>();

    static {
        closed.put("img",       "1");
        closed.put("emot",      "1");
        closed.put("flash",     "1");
        closed.put("audio",     "1");
        closed.put("attach",    "1");
        closed.put("attachimg", "1");
    }

    /**
     * @return BBCode
     */
    public static BBCode getInstance() {
        return new BBCode(config, closed);
    }

    /**
     * @return Map<String, String>
     */
    private static Map<String, String> load() {
        ClassLoader classLoader = BBCode.class.getClassLoader();
        Map<String, String> map1 = load(classLoader, "ayada-bbcode-default.properties");
        Map<String, String> map2 = load(classLoader, "ayada-bbcode.properties");
        map1.putAll(map2);
        return map1;
    }

    /**
     * @param classLoader
     * @param resource
     * @return Map<String, String>
     */
    private static Map<String, String> load(ClassLoader classLoader, String resource) {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        Map<String, String> map = new LinkedHashMap<String, String>();

        try {
            inputStream = classLoader.getResourceAsStream(resource);

            if(inputStream == null) {
                return map;
            }

            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);

            int k = 0;
            String key = null;
            String line = null;
            String value = null;
            while((line = bufferedReader.readLine()) != null) {
                line = line.trim();

                if(line.length() < 1) {
                    continue;
                }

                if(line.startsWith("#")) {
                    continue;
                }

                k = line.indexOf(" ");

                if(k > -1) {
                    key = line.substring(0, k).trim();
                    value = line.substring(k + 1).trim();

                    if(key.length() > 0 && value.length() > 0) {
                        map.put(key, value);
                    }
                }
            }
        }
        catch(IOException e) {
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
        return map;
    }
}
