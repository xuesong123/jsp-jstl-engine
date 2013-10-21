/*
 * $RCSfile: StringUtil.java,v $$
 * $Revision: 1.1  $
 * $Date: 2013-2-16  $
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
}
