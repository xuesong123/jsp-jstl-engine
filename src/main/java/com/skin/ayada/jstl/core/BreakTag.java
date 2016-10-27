/*
 * $RCSfile: BreakTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-27 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import com.skin.ayada.tagext.BreakTagSupport;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TagSupport;

/**
 * <p>Title: BreakTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class BreakTag extends TagSupport implements BreakTagSupport {
    /**
     * @return int
     */
    @Override
    public int doStartTag() {
        return Tag.BREAK;
    }
}
