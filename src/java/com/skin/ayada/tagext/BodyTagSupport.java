/*
 * $RCSfile: BodyTagSupport.java,v $$
 * $Revision: 1.1  $
 * $Date: 2013-2-19  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.tagext;

import com.skin.ayada.runtime.JspWriter;

/**
 * <p>Title: BodyTagSupport</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class BodyTagSupport extends TagSupport implements BodyTag
{
    private BodyContent bodyContent;

    public int doStartTag()
    {
        return EVAL_BODY_BUFFERED;
    }

    public int doEndTag()
    {
        return super.doEndTag();
    }

    public void setBodyContent(BodyContent bodyContent)
    {
      this.bodyContent = bodyContent;
    }

    public void doInitBody()
    {
    }

    public int doAfterBody()
    {
        return SKIP_BODY;
    }

    public void release()
    {
        this.bodyContent = null;
        super.release();
    }

    public BodyContent getBodyContent()
    {
        return this.bodyContent;
    }

    public JspWriter getPreviousOut()
    {
        return this.bodyContent.getEnclosingWriter();
    }
}
