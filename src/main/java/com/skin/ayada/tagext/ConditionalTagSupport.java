/*
 * $RCSfile: ConditionalTagSupport.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-19 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.tagext;

/**
 * <p>Title: ConditionalTagSupport</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public abstract class ConditionalTagSupport extends TagSupport {
    private boolean test = false;

    public ConditionalTagSupport() {
        this.test = false;
    }

    /**
     * @param test
     */
    public ConditionalTagSupport(boolean test) {
        this.test = test;
    }

    /**
     * @return boolean
     */
    public boolean condition() {
        return this.test;
    }

    /**
     * @return the test
     */
    public boolean getTest() {
        return this.test;
    }

    /**
     * @param test the test to set
     */
    public void setTest(boolean test) {
        this.test = test;
    }

    /**
     * @return boolean
     */
    public static boolean getTrue() {
        return true;
    }

    /**
     * @return boolean
     */
    public static boolean getFalse() {
        return false;
    }
}
