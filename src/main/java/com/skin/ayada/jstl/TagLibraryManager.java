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

/**
 * <p>Title: TagLibraryManager</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class TagLibraryManager {
    public static final Map<String, Map<String, TagInfo>> cache = new HashMap<String, Map<String, TagInfo>>();

    /**
     * @param prefix
     * @param uri
     * @return TagLibrary
     * @throws Exception
     */
    public synchronized static Map<String, TagInfo> getTagLibrary(String prefix, String uri) throws Exception {
        String resource = uri;

        if(resource.startsWith("http://")) {
            int k = resource.indexOf("/", 7);

            if(k > -1) {
                resource = resource.substring(k + 1);
            }
            else {
                throw new java.lang.IllegalArgumentException(uri);
            }
        }

        if(resource.startsWith("/") || resource.startsWith("\\")) {
            resource = resource.substring(1);
        }

        if(resource.equals("ayada-taglib-standard")) {
        	resource = "ayada-taglib-default.xml";
        }

        String key = prefix + ":" + resource;
        Map<String, TagInfo> library = cache.get(key);

        if(library == null) {
            library = TagLibraryFactory.load(prefix, resource + ".xml");
            cache.put(key, library);
        }
        return library;
    }
}
