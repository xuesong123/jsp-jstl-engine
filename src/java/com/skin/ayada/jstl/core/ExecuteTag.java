/*
 * $RCSfile: ExecuteTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-27 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import java.io.IOException;

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
    public Object result;

    @Override
    public int doStartTag()
    {
        try
        {
            if(this.result != null)
            {
                this.pageContext.getOut().write(this.result.toString());
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return Tag.SKIP_BODY;
    }

    /**
     * @return the result
     */
    public Object getResult()
    {
        return this.result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(Object result)
    {
        this.result = result;
    }
}
