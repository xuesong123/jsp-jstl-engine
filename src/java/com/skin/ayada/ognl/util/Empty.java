/*
 * $RCSfile: Empty.java,v $$
 * $Revision: 1.1  $
 * $Date: 2013-2-27  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.ognl.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <p>Title: Empty</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class Empty<K, V> implements Map<K, V>
{
    private Set<Map.Entry<K, V>> entrySet;
    private Set<K> keySet;
    private Collection<V> valueList;

    public Empty()
    {
        Map<K, V> map = new HashMap<K, V>();
        this.entrySet = map.entrySet();
        this.keySet = map.keySet();
        this.valueList = map.values();
    }

    @Override
    public void clear()
    {
    }

    @Override
    public boolean containsKey(Object key)
    {
        return false;
    }

    @Override
    public boolean containsValue(Object value)
    {
        return false;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet()
    {
        return this.entrySet;
    }

    @Override
    public V get(Object key)
    {
        return null;
    }

    @Override
    public boolean isEmpty()
    {
        return true;
    }

    @Override
    public Set<K> keySet()
    {
        return this.keySet;
    }

    @Override
    public V put(K key, V value)
    {
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m)
    {
    }

    @Override
    public V remove(Object key)
    {
        return null;
    }

    @Override
    public int size()
    {
        return 0;
    }

    @Override
    public Collection<V> values()
    {
        return this.valueList;
    }

    @Override
    public String toString()
    {
        return "";
    }
}
