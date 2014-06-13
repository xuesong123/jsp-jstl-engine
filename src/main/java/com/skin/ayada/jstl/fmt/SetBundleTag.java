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
package com.skin.ayada.jstl.fmt;

import com.skin.ayada.jstl.core.SetTag;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TagSupport;

/**
 * javax.servlet.jsp.jstl.fmt.localizationContext
 * <p>Title: SetBundleTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class SetBundleTag extends TagSupport
{
    private String basename;
    private String var;
    private String scope;

    @Override
    public int doStartTag()
    {
        LocalizationContext bundle = BundleTag.getBundle(this.pageContext, this.basename);

        if(this.var == null)
        {
            this.pageContext.setBundle(bundle);
        }
        else
        {
            SetTag.setValue(this.pageContext, this.var, this.scope, bundle);
        }

        return Tag.SKIP_BODY;
    }

    /**
     * @return the basename
     */
    public String getBasename()
    {
        return this.basename;
    }

    /**
     * @param basename the basename to set
     */
    public void setBasename(String basename)
    {
        this.basename = basename;
    }

    /**
     * @return the var
     */
    public String getVar()
    {
        return this.var;
    }

    /**
     * @param var the var to set
     */
    public void setVar(String var)
    {
        this.var = var;
    }

    /**
     * @return the scope
     */
    public String getScope()
    {
        return this.scope;
    }

    /**
     * @param scope the scope to set
     */
    public void setScope(String scope)
    {
        this.scope = scope;
    }
}
