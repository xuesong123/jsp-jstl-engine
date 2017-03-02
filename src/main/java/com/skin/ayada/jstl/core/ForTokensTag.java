/*
 * $RCSfile: ForTokensTag.java,v $
 * $Revision: 1.1 $
 * $Date: 2014-04-11 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import java.util.Iterator;

import com.skin.ayada.tagext.LoopTagSupport;

/**
 * <p>Title: ForTokensTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class ForTokensTag extends LoopTagSupport {
    protected String items;
    protected String delims;
    private Iterator<?> iterator;

    /**
     * @throws Exception
     */
    @Override
    public void prepare() throws Exception {
        if(this.delims == null) {
            this.iterator = new ForEachTag.StringIterator(this.items, ",");
        }
        else {
            this.iterator = new ForEachTag.StringIterator(this.items, this.delims);
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
    public void setItems(String items) {
        this.items = items;
    }

    /**
     * @param delims
     */
    public void setDelims(String delims) {
        this.delims = delims;
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
}
