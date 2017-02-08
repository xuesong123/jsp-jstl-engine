/*
 * $RCSfile: ForEachTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-19 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import com.skin.ayada.tagext.LoopTagSupport;

/**
 * <p>Title: ForEachTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class ForEachTag extends LoopTagSupport {
    private Object items;
    private Iterator<?> iterator;
    private boolean hasItems = false;

    /**
     * @throws Exception
     */
    @Override
    public void prepare() throws Exception {
        if(this.hasItems) {
            this.iterator = ForEachTag.getIterator(this.items);
        }
        else {
            this.beginSpecified = false;
            this.endSpecified = false;
            this.iterator = new RangeIterator<Integer>(this.begin, this.end);
            this.begin = 0;
            this.end = -1;
        }
    }

    /**
     * @return boolean
     */
    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    /**
     * @return Object
     */
    @Override
    public Object next() {
        return this.iterator.next();
    }

    /**
     * @param items
     */
    public void setItems(Object items) {
        this.items = items;
        this.hasItems = true;
    }

    /**
     * @param begin
     */
    public void setBegin(int begin) {
        this.begin = begin;
        this.beginSpecified = true;
    }

    /**
     * @param end
     */
    public void setEnd(int end) {
        this.end = end;
        this.endSpecified = true;
    }

    /**
     * @param step
     */
    public void setStep(int step) {
        this.step = step;
        this.stepSpecified = true;
    }

    /**
     * @param object
     * @return Iterator<?>
     */
    @SuppressWarnings("unchecked")
    public static Iterator<?> getIterator(Object object) {
        if(object == null) {
            return new NullIterator<String>();
        }

        if(object instanceof Collection<?>) {
            return ((Collection<?>)object).iterator();
        }

        if(object instanceof Map<?, ?>) {
            return ((Map<?, ?>)object).entrySet().iterator();
        }

        if(object.getClass().isArray()) {
            return new ArrayIterator<Object>(object);
        }

        if(object instanceof Iterator) {
            return (Iterator<?>)(object);
        }

        if(object instanceof Enumeration<?>) {
            Enumeration<Object> enu = (Enumeration<Object>)object;
            return new EnuIterator<Object>(enu);
        }

        if(object instanceof String) {
            return new StringIterator((String)object, ",");
        }
        else {
            throw new RuntimeException("Can't cast to iterator !");
        }
    }

    /**
     * @return Object
     */
    public Object getItems() {
        return this.items;
    }

    /**
     * <p>Title: ArrayIterator</p>
     * <p>Description: </p>
     * <p>Copyright: Copyright (c) 2006</p>
     * @author xuesong.net
     * @version 1.0
     * @param <E>
     */
    public static class ArrayIterator<E> implements Iterator<Object> {
        private Object array;
        private int index;
        private int length;

        /**
         * @param array
         */
        public ArrayIterator(Object array) {
            this.index = 0;
            this.array = array;
            this.length = Array.getLength(array);
        }

        /**
         * @return boolean
         */
        @Override
        public boolean hasNext() {
            return this.index < this.length;
        }

        /**
         * @return Object
         */
        @Override
        public Object next() {
            if(this.index < this.length) {
                return Array.get(this.array, this.index++);
            }
            return null;
        }

        /**
         * 
         */
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * <p>Title: EnuIterator</p>
     * <p>Description: </p>
     * <p>Copyright: Copyright (c) 2006</p>
     * @author xuesong.net
     * @version 1.0
     * @param <E>
     */
    public static class EnuIterator<E> implements Iterator<E> {
        private Enumeration<E> enumeration;

        /**
         * @param enumeration
         */
        public EnuIterator(Enumeration<E> enumeration) {
            this.enumeration = enumeration;
        }

        /**
         * @return boolean
         */
        @Override
        public boolean hasNext() {
            return this.enumeration.hasMoreElements();
        }

        /**
         * @return Object
         */
        @Override
        public E next() {
            return this.enumeration.nextElement();
        }

        /**
         * 
         */
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * <p>Title: RangeIterator</p>
     * <p>Description: </p>
     * <p>Copyright: Copyright (c) 2006</p>
     * @author xuesong.net
     * @version 1.0
     * @param <E>
     */
    public static class RangeIterator<E> implements Iterator<Integer> {
        private int begin;
        private int end;

        RangeIterator(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }

        /**
         * @return boolean
         */
        @Override
        public boolean hasNext() {
            return this.begin <= this.end;
        }

        /**
         * @return Object
         */
        @Override
        public Integer next() {
            if(this.begin <= this.end) {
                return new Integer(this.begin++);
            }
            return null;
        }

        /**
         * 
         */
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * <p>Title: StringIterator</p>
     * <p>Description: </p>
     * <p>Copyright: Copyright (c) 2006</p>
     * @author xuesong.net
     * @version 1.0
     */
    public static class StringIterator implements Iterator<String> {
        private String value;
        private String delims;
        private int index;
        private int length;

        protected StringIterator(String value, String delims) {
            this.index = 0;
            this.value = value;
            this.delims = delims;
            this.length = value.length();
        }

        /**
         * @return boolean
         */
        @Override
        public boolean hasNext() {
            return this.index < this.length;
        }

        /**
         * @return Object
         */
        @Override
        public String next() {
            int i = this.index;
            int k = this.value.indexOf(this.delims, this.index);

            if(k > -1) {
                this.index = k + this.delims.length();
                return this.value.substring(i, k).trim();
            }

            this.index = this.length;
            return this.value.substring(i).trim();
        }

        /**
         * 
         */
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * <p>Title: NullIterator</p>
     * <p>Description: </p>
     * <p>Copyright: Copyright (c) 2006</p>
     * @author xuesong.net
     * @version 1.0
     * @param <E>
     */
    public static class NullIterator<E> implements Iterator<E> {
        /**
         * @return boolean
         */
        @Override
        public boolean hasNext() {
            return false;
        }

        /**
         * @return Object
         */
        @Override
        public E next() {
            return null;
        }

        /**
         * 
         */
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
