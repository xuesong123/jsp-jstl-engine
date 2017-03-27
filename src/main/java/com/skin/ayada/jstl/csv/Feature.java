/*
 * $RCSfile: Feature.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-04-25 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.csv;

/**
 * <p>Title: Feature</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class Feature {
    private int quote;
    private char seperator;
    private boolean skipWhitespace;

    /**
     * default
     */
    public Feature() {
        this.quote = '"';
        this.seperator = ',';
        this.skipWhitespace = false;
    }

    /**
     * @return int
     */
    public int getQuote() {
        return this.quote;
    }

    /**
     * @param quote
     */
    public void setQuote(int quote) {
        this.quote = quote;
    }

    /**
     * @return char
     */
    public char getSeperator() {
        return this.seperator;
    }

    /**
     * @param seperator
     */
    public void setSeperator(char seperator) {
        this.seperator = seperator;
    }

    /**
     * @return boolean
     */
    public boolean getSkipWhitespace() {
        return this.skipWhitespace;
    }

    /**
     * @param skipWhitespace
     */
    public void setSkipWhitespace(boolean skipWhitespace) {
        this.skipWhitespace = skipWhitespace;
    }
}
