/*
 * $RCSfile: EncoderFactory.java,v $
 * $Revision: 1.1  $
 * $Date: 2014-10-6  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.runtime;

import com.skin.ayada.Encoder;
import com.skin.ayada.jstl.util.EscapeEncoder;
import com.skin.ayada.jstl.util.HtmlEncoder;

/**
 * <p>Title: EncoderFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class EncoderFactory {
    private static final HtmlEncoder htmlEncoder = new HtmlEncoder();
    private static final EscapeEncoder escapeEncoder = new EscapeEncoder();

    /**
     * @param name
     * @return ELEncoder
     */
    public static Encoder getEncoder(String name) {
        if(name == null) {
            return null;
        }

        if((name.equals("xml") || name.equals("html"))) {
            return htmlEncoder;
        }
        else if((name.equals("code"))) {
            return escapeEncoder;
        }
        else {
            return null;
        }
    }
}
