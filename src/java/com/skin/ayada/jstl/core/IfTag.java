/*
 * $RCSfile: IfTag.java,v $$
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

/**
 * <p>Title: IfTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class IfTag extends ConditionalTagSupport
{
    /**
     * @return int
     */
    @Override
    public int doStartTag()
    {
        if(this.condition() == true)
        {
            return EVAL_BODY_INCLUDE;
        }
        return SKIP_BODY;
    }

    public void setTest(boolean b)
    {
        this.setCondition(b);
    }

    /**
     * @return boolean
     */
    public static boolean getTrue()
    {
        return true;
    }

    /**
     * @return boolean
     */
    public static boolean getFalse()
    {
        return false;
    }
}
