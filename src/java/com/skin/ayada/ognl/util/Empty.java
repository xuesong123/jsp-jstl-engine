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
import java.util.Collections;
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
    public Empty()
    {
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
    @SuppressWarnings("unchecked")
    public Set<Map.Entry<K, V>> entrySet()
    {
        return Collections.EMPTY_SET;
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
    @SuppressWarnings("unchecked")
    public Set<K> keySet()
    {
        return Collections.EMPTY_SET;
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
    @SuppressWarnings("unchecked")
    public Collection<V> values()
    {
        return Collections.EMPTY_LIST;
    }

    @Override
    public String toString()
    {
        return "";
    }
}
