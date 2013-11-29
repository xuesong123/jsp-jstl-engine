/*
 * $RCSfile: ExecuteTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-29 $
 *
 * Copyright (C) 2008 WanMei, Inc. All rights reserved.
 *
 * This software is the proprietary information of WanMei, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TagSupport;

/**
 * <p>Title: ExecuteTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class ExecuteTag extends TagSupport
{
    private String var;
    private Object value;

    /**
     * @return int
     */
    @Override
    public int doStartTag() throws Exception
    {
        if(this.var != null)
        {
            this.pageContext.setAttribute(this.var, this.value);
        }

        return Tag.SKIP_BODY;
    }
}
