/*
 * $RCSfile: URLUtil.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-03-29 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * <p>Title: URLUtil</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class URLUtil
{
    /**
     * @param source
     * @return String
     */
    public String encode(String source)
    {
        return this.encode(source, "UTF-8");
    }

    /**
     * @param source
     * @param encoding
     * @return String
     */
    public String encode(String source, String encoding)
    {
        try
        {
            return URLEncoder.encode(source, encoding);
        }
        catch(UnsupportedEncodingException e)
        {
        }

        return "";
    }


    /**
     * @param source
     * @return String
     */
    public String decode(String source)
    {
        return this.decode(source, "UTF-8");
    }

    /**
     * @param source
     * @param encoding
     * @return String
     */
    public String decode(String source, String encoding)
    {
        try
        {
            return URLDecoder.decode(source, encoding);
        }
        catch(UnsupportedEncodingException e)
        {
        }

        return "";
    }
}
