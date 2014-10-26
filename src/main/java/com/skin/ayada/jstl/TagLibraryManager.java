/*
 * $RCSfile: TagLibraryFactory.java,v $$
 * $Revision: 1.1 $
 * $Date: 2014-04-19 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Title: TagLibraryManager</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class TagLibraryManager
{
    public static final Map<String, Map<String, TagInfo>> cache = new HashMap<String, Map<String, TagInfo>>();
    private static final Logger logger = LoggerFactory.getLogger(TagLibraryManager.class);

    /**
     * @param prefix
     * @param uri
     * @return TagLibrary
     * @throws Exception
     */
    public synchronized static Map<String, TagInfo> getTagLibrary(String prefix, String uri) throws Exception
    {
        String resource = uri;

        if(resource.startsWith("http://"))
        {
            int k = resource.indexOf("/", 7);

            if(k > -1)
            {
                resource = resource.substring(k + 1);
            }
            else
            {
                throw new java.lang.IllegalArgumentException(uri);
            }
        }

        if(resource.startsWith("/") || resource.startsWith("\\"))
        {
            resource = resource.substring(1);
        }

        String key = prefix + ":" + resource;
        Map<String, TagInfo> library = cache.get(key);

        if(library == null)
        {
            library = TagLibraryFactory.load(prefix, resource + ".xml");
            cache.put(key, library);
        }

        return library;
    }

    public static void main(String[] args)
    {
        try
        {
            Map<String, TagInfo> library = TagLibraryManager.getTagLibrary("test", "http://www.skin-ayada.com/ayada-taglib-test");
            library = TagLibraryManager.getTagLibrary("test", "/ayada-taglib-test");
            library = TagLibraryManager.getTagLibrary("test", "\\ayada-taglib-test");
            for(Map.Entry<String, TagInfo> entry : library.entrySet())
            {
                System.out.println(entry.getKey() + " - " + entry.getValue());
            }
        }
        catch(Exception e)
        {
            logger.warn(e.getMessage(), e);
        }
    }
}
