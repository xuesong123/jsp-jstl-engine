/*
 * $RCSfile: IfTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-19 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import com.skin.ayada.tagext.ConditionalTagSupport;

/**
 * <p>Title: IfTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class IfTag extends ConditionalTagSupport {
    /**
     * @return int
     * @throws Exception
     */
    @Override
    public int doStartTag() throws Exception {
        if(this.condition() == true) {
            return EVAL_BODY_INCLUDE;
        }
        else {
            return SKIP_BODY;
        }
    }
}
