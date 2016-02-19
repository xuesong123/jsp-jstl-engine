/*
 * $RCSfile: CacheClient.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-19 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.util;

import java.util.HashMap;
import java.util.Map;

/**
 * for test
 * <p>Title: CacheClient</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class CacheClient {
    private Map<String, Object> map = new HashMap<String, Object>();

    /**
     *
     */
    public CacheClient() {
    }

    /**
     * @param key
     * @param expires
     * @param value
     */
    public void setCache(String key, int expires, Object value) {
        this.map.put(key, value);
    }

    /**
     * @param key
     * @return Object
     */
    public Object getCache(String key) {
        return this.map.get(key);
    }
}
