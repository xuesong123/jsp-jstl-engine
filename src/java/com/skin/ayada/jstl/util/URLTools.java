/*
 * $RCSfile: URLTools.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-3-29 $
 *
 * Copyright (C) 2008 WanMei, Inc. All rights reserved.
 *
 * This software is the proprietary information of WanMei, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * <p>Title: URLTools</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author chenyankui
 * @version 1.0
 */
public class URLTools
{
    public String encode(String source)
    {
        return this.encode(source, "UTF-8");
    }
    
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

    public String decode(String source)
    {
        return this.decode(source, "UTF-8");
    }

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
