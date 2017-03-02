/*
 * $RCSfile: JoinTag.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-02-19 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.skin.ayada.tagext.TagSupport;
import com.skin.ayada.util.ClassUtil;

/**
 * <p>Title: JoinTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class JoinTag extends TagSupport {
    private Object items;
    private Iterator<?> iterator;
    private String property;
    private Object value;
    private int repeat;
    private String separator;
    private boolean hasItems = false;
    private boolean nullable = false;

    /**
     * @return int
     */
    @Override
    public int doStartTag() throws Exception {
        StringBuilder buffer = new StringBuilder();

        if(this.separator == null) {
            this.separator = "";
        }

        if(this.hasItems) {
            Object element = null;
            this.iterator = ForEachTag.getIterator(this.items);
            this.iterator = JoinTag.getIterator(this.iterator, this.property);

            while(this.iterator.hasNext()) {
                element = this.iterator.next();

                if(element == null) {
                    if(this.nullable) {
                        buffer.append("null");

                        if(this.iterator.hasNext()) {
                            buffer.append(this.separator);
                        }
                    }
                }
                else {
                    buffer.append(element.toString());

                    if(this.iterator.hasNext()) {
                        buffer.append(this.separator);
                    }
                }
            }
        }
        else if(this.value != null && this.repeat > 0){
            for(int i = 0, count = this.repeat - 1; i < count; i++) {
                buffer.append(this.value.toString());
                buffer.append(this.separator);
            }
            buffer.append(this.value.toString());
        }

        if(buffer.length() > 0) {
            this.getPageContext().getOut().write(buffer.toString());
        }
        return super.doStartTag();
    }

    /**
     * @return Iterator<?>
     */
    private static Iterator<?> getIterator(Iterator<?> iterator, String property) {
        if(iterator == null) {
            return new ArrayList<Object>().iterator();
        }

        if(property == null || property.trim().length() < 1) {
            return iterator;
        }

        Object element = null;
        List<Object> list = new ArrayList<Object>();

        while(iterator.hasNext()) {
            try {
                element = ClassUtil.getProperty(iterator.next(), property);
            }
            catch (Exception e) {
                element = null;
            }
            list.add(element);
        }
        return list.iterator();
    }

    /**
     * @param items the items to set
     */
    public void setItems(Object items) {
        this.items = items;
        this.hasItems = true;
    }

    /**
     * @return the items
     */
    public Object getItems() {
        return this.items;
    }

    /**
     * @param property the property to set
     */
    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * @return the property
     */
    public String getProperty() {
        return this.property;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * @return the value
     */
    public Object getValue() {
        return this.value;
    }

    /**
     * @param repeat the repeat to set
     */
    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    /**
     * @return the repeat
     */
    public int getRepeat() {
        return this.repeat;
    }

    /**
     * @return the separator
     */
    public String getSeparator() {
        return this.separator;
    }

    /**
     * @param separator the separator to set
     */
    public void setSeparator(String separator) {
        this.separator = separator;
    }

    /**
     * @return the nullable
     */
    public boolean getNullable() {
        return this.nullable;
    }

    /**
     * @param nullable the nullable to set
     */
    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }
}
