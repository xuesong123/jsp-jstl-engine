/*
 * $RCSfile: ExitTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-26 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import com.skin.ayada.tagext.ConditionalTagSupport;

/**
 * <p>Title: ExitTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class ExitTag extends ConditionalTagSupport {
    public ExitTag() {
        super(true);
    }

    /**
     * @return int
     */
    @Override
    public int doStartTag() {
        if(this.condition() == true) {
            return SKIP_PAGE;
        }
        return EVAL_PAGE;
    }
}
