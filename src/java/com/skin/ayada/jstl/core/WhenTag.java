/*
 * $RCSfile: WhenTag.java,v $$
 * $Revision: 1.1  $
 * $Date: 2013-2-19  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import com.skin.ayada.tagext.ConditionalTagSupport;
import com.skin.ayada.tagext.Tag;

/**
 * <p>Title: WhenTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class WhenTag extends ConditionalTagSupport
{
    /**
     * @return int
     */
    public int doStartTag()
    {
        Tag parent = this.getParent();

        if(parent == null)
        {
            throw new RuntimeException("when tag must be in choose tag !");
        }

        ChooseTag chooseTag = (ChooseTag)parent;
        
        if(chooseTag.complete())
        {
            return SKIP_BODY;
        }

        if(this.condition())
        {
            chooseTag.finish();
            return EVAL_BODY_INCLUDE;
        }

        return SKIP_BODY;
    };

    /**
     * @return int
     */
    public int doEndTag()
    {
        return EVAL_PAGE;
    }

    public void setTest(boolean b)
    {
        this.setCondition(b);
    }
}
