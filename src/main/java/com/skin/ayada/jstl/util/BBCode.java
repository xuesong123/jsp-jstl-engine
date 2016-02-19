/*
 * $RCSfile: BBCode.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-17 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.util;

/**
 * <p>Title: BBCode</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class BBCode {
    /**
     * @param name
     * @param attributes
     * @param value
     * @return String
     */
    public String encode(String name, String attributes, String value) {
        return com.skin.ayada.util.BBCode.getHtml(name, attributes, value);
    }

    /**
     * @param source
     * @return String
     */
    public String decode(String source) {
        return com.skin.ayada.util.BBCode.decode(source);
    }

    /**
     * @param name
     * @param attributes
     * @param value
     * @return String
     */
    public String getHtml(String name, String attributes, String value) {
        return com.skin.ayada.util.BBCode.getHtml(name, attributes, value);
    }

    /**
     * @param source
     * @param search
     * @param replacement
     * @return String
     */
    public String replace(String source, String search, String replacement) {
        return com.skin.ayada.util.BBCode.replace(source, search, replacement);
    }

    /***
     * @param source
     * @return String
     */
    public String remove(String source) {
        return com.skin.ayada.util.BBCode.remove(source);
    }
}
