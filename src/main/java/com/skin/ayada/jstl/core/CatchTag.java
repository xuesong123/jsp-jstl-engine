/*
 * $RCSfile: CatchTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2014-04-11 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import com.skin.ayada.tagext.TagSupport;
import com.skin.ayada.tagext.TryCatchFinally;

/**
 * <p>Title: CatchTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class CatchTag extends TagSupport implements TryCatchFinally {
    private String var;

    @Override
    public void doCatch(Throwable throwable) throws Throwable {
        if(this.var != null) {
            this.pageContext.setAttribute(this.var, throwable);
        }
    }

    @Override
    public void doFinally() {
    }

    /**
     * @param var the var to set
     */
    public void setVar(String var) {
        this.var = var;
    }

    /**
     * @return the var
     */
    public String getVar() {
        return this.var;
    }
}
