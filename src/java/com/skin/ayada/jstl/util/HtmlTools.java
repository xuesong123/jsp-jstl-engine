/*
 * $RCSfile: HtmlTools.java,v $$
 * $Revision: 1.1  $
 * $Date: 2013-2-17  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.util;

import com.skin.ayada.util.HtmlUtil;

/**
 * <p>Title: HtmlTools</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class HtmlTools
{
    /**
     * @param source
     * @return String
     */
    public static String encode(String source)
    {
        return HtmlUtil.encode(source);
    }

    /**
     * @param source
     * @return String
     */
    public static String decode(String source)
    {
        return HtmlUtil.decode(source);
    }

    /**
     * @param source
     * @return String
     */
    public static String remove(String source)
    {
        return HtmlUtil.remove(source);
    }
}