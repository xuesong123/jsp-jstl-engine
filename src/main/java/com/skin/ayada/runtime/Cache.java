/*
 * $RCSfile: Cache.java,v $$
 * $Revision: 1.1 $
 * $Date: 2016-2-10 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.runtime;

/**
 * <p>Title: Cache</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public interface Cache {
    /**
     * @param key
     * @param expires
     * @param value
     */
    public void setCache(String key, int expires, Object value);

    /**
     * @param key
     * @return Object
     */
    public Object getCache(String key);
}
