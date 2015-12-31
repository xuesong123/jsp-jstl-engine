/*
 * $RCSfile: HtmlEncoder.java,v $$
 * $Revision: 1.1  $
 * $Date: 2014-10-6  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.util;

import com.skin.ayada.runtime.ELEncoder;
import com.skin.ayada.util.HtmlUtil;

/**
 * <p>Title: HtmlEncoder</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class HtmlEncoder implements ELEncoder {
    public HtmlEncoder() {
    }

    /**
     * @param content
     * @return String
     */
    @Override
    public String encode(String content) {
        return HtmlUtil.encode(content);
    }
}
