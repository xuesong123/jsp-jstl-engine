/*
 * $RCSfile: ELEncoderFactory.java,v $$
 * $Revision: 1.1  $
 * $Date: 2014-10-6  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.runtime;

import com.skin.ayada.jstl.util.HtmlEncoder;

/**
 * <p>Title: ELEncoderFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class ELEncoderFactory
{
    private static final HtmlEncoder htmlEncoder = new HtmlEncoder();

    /**
     * @param name
     * @return ELEncoder
     */
    public static ELEncoder getELEncoder(String name)
    {
        if(name != null)
        {
            if(name.equals("xml") || name.equals("html"))
            {
                return htmlEncoder;
            }
        }

        return null;
    }

    /**
     * @return ELEncoder
     */
    public static ELEncoder getHtmlEncoder()
    {
        return htmlEncoder;
    }
}
