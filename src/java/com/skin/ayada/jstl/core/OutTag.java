/*
 * $RCSfile: OutTag.java,v $$
 * $Revision: 1.1  $
 * $Date: 2013-2-19  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import java.io.IOException;

import com.skin.ayada.runtime.JspWriter;
import com.skin.ayada.tagext.BodyContent;
import com.skin.ayada.tagext.BodyTag;
import com.skin.ayada.tagext.BodyTagSupport;
import com.skin.ayada.tagext.Tag;

/**
 * <p>Title: OutTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class OutTag extends BodyTagSupport
{
    private Object value = null;
    private String defaultValue = null;
    private boolean escapeXml = false;

    @Override
    public int doStartTag()
    {
        JspWriter out = pageContext.getOut();

        try
        {
            if(this.value != null)
            {
                if(this.escapeXml)
                {
                    out.print(this.escape(this.value.toString()));
                }
                else
                {
                    out.print(this.value);
                }
            }
            else if(this.defaultValue != null)
            {
                if(this.escapeXml)
                {
                    out.print(this.escape(this.defaultValue));
                }
                else
                {
                    out.print(this.defaultValue);
                }
            }
            else
            {
                return BodyTag.EVAL_BODY_BUFFERED;
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return Tag.SKIP_BODY;
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

            if(this.escapeXml)
            {
                content = this.escape(content);
            }

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

    /**
     * @param source
     * @return String
     */
    private String escape(String source)
    {
        if(source == null)
        {
            return "";
        }

        char c;
        StringBuilder buffer = new StringBuilder();

        for(int i = 0, size = source.length(); i < size; i++)
        {
            c = source.charAt(i);

            switch (c)
            {
                case '&':
                {
                    buffer.append("&amp;");
                    break;
                }
                case '"':
                {
                    buffer.append("&quot;");
                    break;
                }
                case '<':
                {
                    buffer.append("&lt;");
                    break;
                }
                case '>':
                {
                    buffer.append("&gt;");
                    break;
                }
                case '\'':
                {
                    buffer.append("&#39;");
                }
                default :
                {
                    buffer.append(c);
                    break;
                }
            }
        }

        return buffer.toString();
    }

    /**
     * @return the value
     */
    public Object getValue()
    {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Object value)
    {
        this.value = value;
    }

    /**
     * @return
     */
    public boolean getEscapeXml()
    {
        return this.escapeXml;
    }

    /**
     * @param escapseXml
     */
    public void setEscapeXml(boolean escapeXml)
    {
        this.escapeXml = escapeXml;
    }
}
