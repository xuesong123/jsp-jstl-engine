/*
 * $RCSfile: DefaultJspFragment.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-20 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.template;

import java.io.Writer;

import com.skin.ayada.runtime.JspWriter;
import com.skin.ayada.runtime.PageContext;
import com.skin.ayada.statement.Statement;
import com.skin.ayada.tagext.JspFragment;

/**
 * <p>Title: DefaultJspFragment</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class DefaultJspFragment implements JspFragment
{
    private Template template;
    private Statement[] statements;
    private PageContext pageContext;
    private int offset;
    private int length;

    public DefaultJspFragment()
    {
    }

    /**
     * @param template
     * @param pageContext
     */
    public DefaultJspFragment(Template template, Statement[] statements, PageContext pageContext)
    {
        this.template = template;
        this.statements = statements;
        this.pageContext = pageContext;
    }

    /**
     * @param out
     */
    @Override
    public void invoke(Writer out)
    {
        if(out != null)
        {
            JspWriter jspWriter = pageContext.getOut();
            pageContext.setOut(new JspWriter(out));

            try
            {
                DefaultExecutor.execute(this.template, this.statements, this.pageContext, this.offset, this.length);
            }
            catch(Throwable t)
            {
                t.printStackTrace();
            }
            finally
            {
                pageContext.setOut(jspWriter);
            }
        }
        else
        {
            try
            {
                DefaultExecutor.execute(template, pageContext, this.offset, this.length);
            }
            catch(Throwable t)
            {
                t.printStackTrace();
            }
        }
    }

    /**
     * @return the template
     */
    public Template getTemplate()
    {
        return this.template;
    }

    /**
     * @param template the template to set
     */
    public void setTemplate(Template template)
    {
        this.template = template;
    }

    /**
     * @param statements the statements to set
     */
    public void setStatements(Statement[] statements)
    {
        this.statements = statements;
    }
    
    /**
     * @return the statements
     */
    public Statement[] getStatements()
    {
        return this.statements;
    }

    /**
     * @return PageContext
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
     * @return the offset
     */
    public int getOffset()
    {
        return this.offset;
    }

    /**
     * @param offset the offset to set
     */
    public void setOffset(int offset)
    {
        this.offset = offset;
    }

    /**
     * @return the length
     */
    public int getLength()
    {
        return this.length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(int length)
    {
        this.length = length;
    }
}
