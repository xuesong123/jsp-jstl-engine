/*
 * $RCSfile: Empty.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-27 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.ognl.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * <p>Title: Empty</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 * @param <K>
 * @param <V>
 */
public class Empty<K, V> implements Map<K, V> {
    /**
     *
     */
    public Empty() {
    }

    /**
     * clear
     */
    @Override
    public void clear() {
    }

    /**
     * @param key
     * @return boolean
     */
    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    /**
     * @param value
     * @return boolean
     */
    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    /**
     * @return Set<Map.Entry<K, V>>
     */
    @Override
    @SuppressWarnings("unchecked")
    public Set<Map.Entry<K, V>> entrySet() {
        return Collections.EMPTY_SET;
    }

    /**
     * @param key
     * @return V
     */
    @Override
    public V get(Object key) {
        return null;
    }

    /**
     * @return boolean
     */
    @Override
    public boolean isEmpty() {
        return true;
    }

    /**
     * @return Set<K>
     */
    @Override
    @SuppressWarnings("unchecked")
    public Set<K> keySet() {
        return Collections.EMPTY_SET;
    }

    /**
     * @param key
     * @param value
     * @return V
     */
    @Override
    public V put(K key, V value) {
        return null;
    }

    /**
     * @param map
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
    }

    /**
     * @param key
     * @return V
     */
    @Override
    public V remove(Object key) {
        return null;
    }

    /**
     * @return int
     */
    @Override
    public int size() {
        return 0;
    }

    /**
     * @return Collection<V>
     */
    @Override
    @SuppressWarnings("unchecked")
    public Collection<V> values() {
        return Collections.EMPTY_LIST;
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        return "";
    }
}
