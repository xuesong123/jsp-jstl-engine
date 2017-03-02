/*
 * $RCSfile: NVLTag.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-11-08 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import com.skin.ayada.jstl.util.BeanUtil;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TagSupport;

/**
 * <p>Title: NVLTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class NVLTag extends TagSupport {
    private Object value1;
    private Object value2;
    private Object value3;
    private boolean escapeXml = false;
    private static final BeanUtil beanUtil = new BeanUtil();

    /**
     * @return int
     */
    @Override
    public int doStartTag() throws Exception {
        if(!beanUtil.isEmpty(this.value1)) {
            OutTag.print(this.pageContext, this.value1, this.escapeXml);
            return Tag.SKIP_BODY;
        }

        if(!beanUtil.isEmpty(this.value2)) {
            OutTag.print(this.pageContext, this.value2, this.escapeXml);
            return Tag.SKIP_BODY;
        }

        if(!beanUtil.isEmpty(this.value3)) {
            OutTag.print(this.pageContext, this.value3, this.escapeXml);
            return Tag.SKIP_BODY;
        }
        return Tag.SKIP_BODY;
    }

    /**
     * @return the value1
     */
    public Object getValue1() {
        return this.value1;
    }

    /**
     * @param value1 the value1 to set
     */
    public void setValue1(Object value1) {
        this.value1 = value1;
    }

    /**
     * @return the value2
     */
    public Object getValue2() {
        return this.value2;
    }

    /**
     * @param value2 the value2 to set
     */
    public void setValue2(Object value2) {
        this.value2 = value2;
    }

    /**
     * @return the value3
     */
    public Object getValue3() {
        return this.value3;
    }

    /**
     * @param value3 the value3 to set
     */
    public void setValue3(Object value3) {
        this.value3 = value3;
    }

    /**
     * @return boolean
     */
    public boolean getEscapeXml() {
        return this.escapeXml;
    }

    /**
     * @param escapeXml
     */
    public void setEscapeXml(boolean escapeXml) {
        this.escapeXml = escapeXml;
    }
}
