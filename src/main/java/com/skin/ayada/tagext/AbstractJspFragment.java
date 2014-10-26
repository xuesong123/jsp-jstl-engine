/*
 * $RCSfile: AbstractJspFragment.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-19 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.tagext;

import java.io.IOException;
import java.io.Writer;

import com.skin.ayada.runtime.JspWriter;
import com.skin.ayada.runtime.PageContext;

/**
 * <p>Title: AbstractJspFragment</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public abstract class AbstractJspFragment implements JspFragment
{
    private Tag parent;
    private PageContext pageContext;

    /**
     * @param parent the parent to set
     */
    public void setParent(Tag parent)
    {
        this.parent = parent;
    }

    /**
     * @return the parent
     */
    public Tag getParent()
    {
        return this.parent;
    }

    /**
     * @param pageContext the pageContext to set
     */
    public void setPageContext(PageContext pageContext)
    {
        this.pageContext = pageContext;
    }

    /**
     * @return PageContext
     */
    @Override
    public PageContext getPageContext()
    {
        return this.pageContext;
    }

    /**
     * @param writer
     * @throws Exception
     */
    @Override
    public void invoke(Writer writer) throws Exception
    {
        JspWriter out = null;

        if(writer == null || writer == this.pageContext.getOut())
        {
            out = this.pageContext.getOut();
        }
        else
        {
            if(writer instanceof JspWriter)
            {
                out = (JspWriter)writer;
            }
            else
            {
               out = new JspWriter(writer);
            }
        }

        try
        {
            this.execute(out);
        }
        finally
        {
            try
            {
                out.flush();
            }
            catch(IOException e)
            {
            }

            // never close
            if(this.getFalse())
            {
                try
                {
                    out.close();
                }
                catch(IOException e)
                {
                }
            }
        }
    }

    /**
     * @return
     */
    private boolean getFalse()
    {
        return false;
    }

    /**
     * @param writer
     */
    public abstract void execute(JspWriter writer) throws Exception;
}
