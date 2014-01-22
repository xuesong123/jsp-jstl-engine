/*
 * $RCSfile: SetBundleTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-2-28 $
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
 * <p>Title: SetBundleTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class SetBundleTag extends TagSupport
{
    private String basename;
    private String var = "javax.servlet.jsp.jstl.fmt.localizationContext";

    @Override
    public int doStartTag()
    {
        LocalizationContext bundle = this.getBundle(this.basename);
        this.pageContext.setAttribute(this.var, bundle);
        return Tag.EVAL_PAGE;
    }

    public LocalizationContext getBundle(String basename)
    {
        return null;
    }

    public void setBasename(String basename)
    {
      this.basename = basename;
    }

    public void setVar(String var)
    {
      this.var = var;
    }
}
