/*
 * $RCSfile: ChooseTag.java,v $
 * $Revision: 1.1$
 * $Date: 2013-02-19$
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TagSupport;

/**
 * <p>Title: ChooseTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class ChooseTag extends TagSupport {
    private boolean flag = false;

    /**
     * @return int
     * @throws Exception
     */
    @Override
    public int doStartTag() throws Exception {
        this.flag = false;
        return Tag.EVAL_BODY_INCLUDE;
    }

    /**
     * @return boolean
     */
    public boolean complete() {
        return this.flag;
    }

    /**
     *
     */
    public void finish() {
        this.flag = true;
    }
}
