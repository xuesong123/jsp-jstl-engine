/*
 * $RCSfile: StringUtil.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-16 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.util;

/**
 * <p>Title: StringUtil</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class StringUtil
{
    /**
     * @param source
     * @param length
     * @param padding
     * @return String
     */
    public String substring(String source, int length, String padding)
    {
        return com.skin.ayada.util.StringUtil.substring(source, length, padding);
    }

    /**
     * @param source
     * @param search
     * @param replacement
     * @return String
     */
    public String replace(String source, String search, String replacement)
    {
        return com.skin.ayada.util.StringUtil.replace(source, search, replacement);
    }

    /**
     * @param source
     * @param limit
     * @return String[]
     */
    public String[] split(String source, String limit)
    {
        return com.skin.ayada.util.StringUtil.split(source, limit);
    }

    /**
     * @param source
     * @return String
     */
    public String escape(String source)
    {
        return com.skin.ayada.util.StringUtil.escape(source);
    }
}
