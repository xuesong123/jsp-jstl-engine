/*
 * $RCSfile: ForEachTag.java,v $$
 * $Revision: 1.1  $
 * $Date: 2013-2-19  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.skin.ayada.tagext.LoopTagStatus;
import com.skin.ayada.tagext.LoopTagSupport;

/**
 * <p>Title: ForEachTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class ForEachTag extends LoopTagSupport implements LoopTagStatus
{
    private ForEachIterator items;
    private boolean hasItems = false;

    /**
     * @return int
     */
    public int doStartTag()
    {
        super.doStartTag();

        if(this.hasItems == false)
        {
            List<Integer> list = new ArrayList<Integer>();

            for(int i = 0, j = this.getBegin(), end = this.getEnd(); j <= end; i++, j += this.getStep())
            {
                list.add(Integer.valueOf(j));
            }

            this.items = new SimpleForEachIterator(list.iterator());
        }

        if(this.getVarStatus() != null)
        {
            this.setLoopStatus(this);
            this.pageContext.setAttribute(this.getVarStatus(), this);
        }

        if(this.hasNext())
        {
            this.setCurrent(this.next());
        }
        else
        {
            return SKIP_BODY;
        }

        return EVAL_BODY_INCLUDE;
    };

    /**
     * @param items
     */
    public void setItems(Object object)
    {
        this.items = this.getIterator(object);
        this.hasItems = true;
    }

    /**
     * @return boolean
     */
    public boolean hasNext()
    {
        return this.items.hasNext();
    }

    /**
     * @return boolean
     */
    public Object next()
    {
        this.setIndex(this.getIndex() + 1);
        return this.items.next();
    }

    /**
     * @param items
     */
    public ForEachIterator getIterator(Object object)
    {
        ForEachIterator items = null;

        if(object instanceof ForEachIterator)
        {
            items = (ForEachIterator)(object);
        }
        else if(object instanceof Object[])
        {
            items = toForEachIterator((Object[])(Object[])object);
        }
        else if(object instanceof boolean[])
        {
            items = toForEachIterator((boolean[])(boolean[])object);
        }
        else if(object instanceof byte[])
        {
            items = toForEachIterator((byte[])(byte[])object);
        }
        else if(object instanceof char[])
        {
            items = toForEachIterator((char[])(char[])object);
        }
        else if(object instanceof short[])
        {
            items = toForEachIterator((short[])(short[])object);
        }
        else  if(object instanceof int[])
        {
            items = toForEachIterator((int[])(int[])object);
        }
        else  if(object instanceof long[])
        {
            items = toForEachIterator((long[])(long[])object);
        }
        else  if(object instanceof float[])
        {
            items = toForEachIterator((float[])(float[])object);
        }
        else if(object instanceof double[])
        {
            items = toForEachIterator((double[])(double[])object);
        }
        else if(object instanceof Collection<?>)
        {
            items = toForEachIterator((Collection<?>)object);
        }
        else  if(object instanceof Iterator<?>)
        {
            items = toForEachIterator((Iterator<?>)object);
        }
        else  if(object instanceof Enumeration<?>)
        {
            items = toForEachIterator((Enumeration<?>)object);
        }
        else if(object instanceof Map<?, ?>)
        {
            items = toForEachIterator((Map<?, ?>)object);
        }
        else  if(object instanceof String)
        {
            items = toForEachIterator((String)object);
        }
        else
        {
            items = toForEachIterator(object);
        }

        return items;
    };

    /**
     * @param items
     */
    public ForEachIterator getItems()
    {
        return this.items;
    };

    protected ForEachIterator toForEachIterator(Object o)
    {
        throw new RuntimeException("Can't cast to iterator !");
    }

    protected ForEachIterator toForEachIterator(Object a[])
    {
        return new SimpleForEachIterator(Arrays.asList(a).iterator());
    }

    protected ForEachIterator toForEachIterator(boolean a[])
    {
        Boolean wrapped[] = new Boolean[a.length];
        for(int i = 0; i < a.length; i++)
            wrapped[i] = Boolean.valueOf(a[i]);

        return new SimpleForEachIterator(Arrays.asList(wrapped).iterator());
    }

    protected ForEachIterator toForEachIterator(byte a[])
    {
        Byte wrapped[] = new Byte[a.length];
        for(int i = 0; i < a.length; i++)
            wrapped[i] = Byte.valueOf(a[i]);

        return new SimpleForEachIterator(Arrays.asList(wrapped).iterator());
    }

    protected ForEachIterator toForEachIterator(char a[])
    {
        Character wrapped[] = new Character[a.length];
        for(int i = 0; i < a.length; i++)
            wrapped[i] = Character.valueOf(a[i]);

        return new SimpleForEachIterator(Arrays.asList(wrapped).iterator());
    }

    protected ForEachIterator toForEachIterator(short a[])
    {
        Short wrapped[] = new Short[a.length];
        for(int i = 0; i < a.length; i++)
            wrapped[i] = Short.valueOf(a[i]);

        return new SimpleForEachIterator(Arrays.asList(wrapped).iterator());
    }

    protected ForEachIterator toForEachIterator(int a[])
    {
        Integer wrapped[] = new Integer[a.length];
        for(int i = 0; i < a.length; i++)
            wrapped[i] = Integer.valueOf(a[i]);

        return new SimpleForEachIterator(Arrays.asList(wrapped).iterator());
    }

    protected ForEachIterator toForEachIterator(long a[])
    {
        Long wrapped[] = new Long[a.length];
        for(int i = 0; i < a.length; i++)
            wrapped[i] = Long.valueOf(a[i]);

        return new SimpleForEachIterator(Arrays.asList(wrapped).iterator());
    }

    protected ForEachIterator toForEachIterator(float a[])
    {
        Float wrapped[] = new Float[a.length];
        for(int i = 0; i < a.length; i++)
            wrapped[i] = new Float(a[i]);

        return new SimpleForEachIterator(Arrays.asList(wrapped).iterator());
    }

    protected ForEachIterator toForEachIterator(double a[])
    {
        Double wrapped[] = new Double[a.length];
        for(int i = 0; i < a.length; i++)
            wrapped[i] = new Double(a[i]);

        return new SimpleForEachIterator(Arrays.asList(wrapped).iterator());
    }

    protected ForEachIterator toForEachIterator(Collection<?> c)
    {
        return new SimpleForEachIterator(c.iterator());
    }

    protected ForEachIterator toForEachIterator(Iterator<?> i)
    {
        return new SimpleForEachIterator(i);
    }

    protected ForEachIterator toForEachIterator(Enumeration<?> e)
    {
        class EnumerationAdapter implements ForEachIterator
        {
            private Enumeration<?> enumeration;

            public boolean hasNext()
            {
                return enumeration.hasMoreElements();
            }

            public Object next()
            {
                return enumeration.nextElement();
            }

            public EnumerationAdapter(Enumeration<?> enumeration)
            {
                this.enumeration = enumeration;
            }
        }

        return new EnumerationAdapter(e);
    }

    protected ForEachIterator toForEachIterator(Map<?, ?> m)
    {
        return new SimpleForEachIterator(m.entrySet().iterator());
    }

    protected ForEachIterator toForEachIterator(String s)
    {
        String value = null;
        List<String> list = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(s, ",");

        while(st.hasMoreElements())
        {
            value = (String)(st.nextElement());
            value = value.trim();

            if(value.length() > 0)
            {
                list.add(value);
            }
        }

        return toForEachIterator(list);
    }

    protected interface ForEachIterator
    {
        public boolean hasNext();
        public Object next();
    }

    protected class SimpleForEachIterator implements ForEachIterator
    {
        private Iterator<?> iterator;

        public SimpleForEachIterator(Iterator<?> iterator)
        {
            this.iterator = iterator;
        }

        public boolean hasNext()
        {
            return this.iterator.hasNext();
        }

        public Object next()
        {
            return this.iterator.next();
        }
    }

    public static void main(String[] args)
    {
        int iBegin = -2;
        int iEnd = -4;
        int iStep = 2;
        List<Integer> list = new ArrayList<Integer>();

        for(int i = 0, j = iBegin, end = iEnd; j <= end; i++, j += iStep)
        {
            list.add(Integer.valueOf(j));
        }

        for(Integer i : list)
        {
            System.out.println(i);
        }
    }
}
