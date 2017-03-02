/*
 * $RCSfile: StackTraceTag.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-02-26 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import java.io.PrintWriter;

import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TagSupport;

/**
 * <p>Title: StackTraceTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class StackTraceTag extends TagSupport {

    /**
     * @return int
     * @throws Exception
     */
    @Override
    public int doStartTag() throws Exception {
        PrintWriter out = new PrintWriter(this.pageContext.getOut());
        new Exception("StackTrace").printStackTrace(out);
        out.flush();
        return Tag.SKIP_BODY;
    }
}
