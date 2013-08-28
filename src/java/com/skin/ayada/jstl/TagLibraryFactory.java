/*
 * $RCSfile: TagLibraryFactory.java,v $$
 * $Revision: 1.1  $
 * $Date: 2013-2-19  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: TagLibraryFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class TagLibraryFactory
{
    private static final Map<String, String> map = load("taglib.tld", "UTF-8");

    /**
     * @return TagLibrary
     */
    public static TagLibrary getStandardTagLibrary()
    {
        TagLibrary tagLibrary = new TagLibrary();
        tagLibrary.setup(map);
        return tagLibrary;
    };

    /**
     * @param resource
     * @param charset
     * @return Map<String, String>
     */
    private static Map<String, String> load(String resource, String charset)
    {
        return load(TagLibraryFactory.class.getClassLoader().getResourceAsStream(resource), charset);
    }

    /**
     * @param inputStream
     * @param charset
     * @return Map<String, String>
     */
    private static Map<String, String> load(InputStream inputStream, String charset)
    {
        Map<String, String> map = new HashMap<String, String>();

        if(inputStream != null)
        {
            BufferedReader reader = null;
            InputStreamReader inputStreamReader = null;

            try
            {
                String line = null;
                inputStreamReader = new InputStreamReader(inputStream, charset);
                reader = new BufferedReader(inputStreamReader);

                while((line = reader.readLine()) != null)
                {
                    line = line.trim();

                    if(line.length() < 1)
                    {
                        continue;
                    }

                    if(line.startsWith("#"))
                    {
                        continue;
                    }

                    int i = line.indexOf(" ");

                    if(i > -1)
                    {
                        String name = line.substring(0, i).trim();
                        String className = line.substring(i + 1).trim();

                        if(name.length() > 0 && className.length() > 0)
                        {
                            map.put(name, className);
                        }
                    }
                }
            }
            catch(IOException e)
            {
            }
        }

        return map;
    }
}
