/*
 * $RCSfile: TestBodyTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-25 $
 *
 * Copyright (C) 2008 WanMei, Inc. All rights reserved.
 *
 * This software is the proprietary information of WanMei, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.taglib;

import java.io.IOException;

import com.skin.ayada.runtime.JspWriter;
import com.skin.ayada.tagext.BodyContent;
import com.skin.ayada.tagext.BodyTagSupport;
import com.skin.ayada.tagext.Tag;

/**
 * <p>Title: TestBodyTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class TestBodyTag extends BodyTagSupport
{
    @Override
    public int doStartTag()
    {
        JspWriter out = pageContext.getOut();

        try
        {
            out.print("test.com.skin.ayada.taglib.TestBodyTag");
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return Tag.EVAL_PAGE;
    }

    /**
     * @return int
     */
    @Override
    public int doEndTag()
    {
        String content = null;
        BodyContent bodyContent = (BodyContent)(this.getBodyContent());

        if(bodyContent != null)
        {
            content = bodyContent.getString().trim();

            try
            {
                pageContext.getOut().print(content);
            }
            catch(IOException e)
            {
            }
        }

        return EVAL_PAGE;
    }
}
