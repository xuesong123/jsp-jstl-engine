/*
 * $RCSfile: IfTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-19 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import com.skin.ayada.tagext.ConditionalTagSupport;

/**
 * <p>Title: IfTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class IfOutTag extends ConditionalTagSupport {
    private Object value1;
    private Object value2;
    private boolean escapeXml = false;

    /**
     * @return int
     */
    @Override
    public int doStartTag() throws Exception {
        if(this.condition() == true) {
            OutTag.print(this.pageContext, this.value1, this.escapeXml);
        }
        else {
            OutTag.print(this.pageContext, this.value2, this.escapeXml);
        }
        return SKIP_BODY;
    }

    /**
     * @param b
     */
    public void setTest(boolean b) {
        this.setCondition(b);
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
