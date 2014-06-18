/*
 * $RCSfile: HtmlUtil.java,v $$
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
 * <p>Title: HtmlUtil</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class HtmlUtil
{
    /**
     * @param source
     * @return String
     */
    public static String encode(String source)
    {
        return com.skin.ayada.util.HtmlUtil.encode(source);
    }

    /**
     * @param source
     * @return String
     */
    public static String decode(String source)
    {
        return com.skin.ayada.util.HtmlUtil.decode(source);
    }

    /**
     * @param source
     * @return String
     */
    public static String remove(String source)
    {
        return com.skin.ayada.util.HtmlUtil.remove(source);
    }
}