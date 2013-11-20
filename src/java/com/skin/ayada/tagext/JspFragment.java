/*
 * $RCSfile: JspFragment.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-20 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.tagext;

import java.io.Writer;

import com.skin.ayada.runtime.PageContext;

/**
 * <p>Title: JspFragment</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public abstract class JspFragment
{
    /**
     * @return PageContext
     */
    public abstract PageContext getPageContext();

    /**
     * @param out
     */
    public abstract void invoke(Writer out);
}
