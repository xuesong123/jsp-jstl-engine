/*
 * $RCSfile: EscapeEncoder.java,v $$
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

/**
 * <p>Title: EscapeEncoder</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class EscapeEncoder implements ELEncoder {
    public EscapeEncoder() {
    }

    /**
     * @param content
     * @return String
     */
    @Override
    public String encode(String content) {
        return com.skin.ayada.util.StringUtil.escape(content);
    }
}
