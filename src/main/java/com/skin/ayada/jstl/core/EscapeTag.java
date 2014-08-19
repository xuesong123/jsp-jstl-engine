/*
 * $RCSfile: EscapeTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2014年7月28日 $
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
 * <p>Title: EscapeTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class EscapeTag extends TagSupport
{
    private boolean escapeXml = false;

    @Override
    public int doStartTag() throws Exception
    {
        this.pageContext.getExpressionContext().setEscapeXml(this.escapeXml);
        return Tag.EVAL_PAGE;
    }

    /**
     * @return the escapeXml
     */
    public boolean getEscapeXml()
    {
        return this.escapeXml;
    }

    /**
     * @param escapeXml the escapeXml to set
     */
    public void setEscapeXml(boolean escapeXml)
    {
        this.escapeXml = escapeXml;
    }
}
