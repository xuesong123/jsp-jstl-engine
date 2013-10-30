/*
 * $RCSfile: PropertyResource.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-10-30 $
 *
 * Copyright (C) 2008 WanMei, Inc. All rights reserved.
 *
 * This software is the proprietary information of WanMei, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * <p>Title: PropertyResource</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class PropertyResource
{
    /**
     * @param inputStream
     * @param map
     * @return Map<String, String>
     */
    public static Map<String, String> load(InputStream inputStream, Map<String, String> map)
    {
        try
        {
            Properties properties = new Properties();
            properties.load(inputStream);
            Set<Map.Entry<Object, Object>> set = properties.entrySet();

            for(Map.Entry<Object, Object> entry : set)
            {
                map.put((String)(entry.getKey()), (String)(entry.getValue()));
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return map;
    }

    /**
     * @param write
     */
    public static void save(Map<String, String> map, java.io.Writer writer)
    {
        throw new java.lang.UnsupportedOperationException("\"save\" method is unsupported !");
    }
}
