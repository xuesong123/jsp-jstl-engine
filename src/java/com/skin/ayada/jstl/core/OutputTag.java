/*
 * $RCSfile: OutputTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-26 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import java.io.File;

import com.skin.ayada.tagext.BodyContent;
import com.skin.ayada.tagext.BodyTag;
import com.skin.ayada.tagext.BodyTagSupport;
import com.skin.ayada.util.IO;

/**
 * <p>Title: OutputTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class OutputTag extends BodyTagSupport
{
    private String file;
    private String encoding;
    private boolean escapeXml = false;
    private boolean out = false;
    private boolean trim = false;

    @Override
    public int doStartTag() throws Exception
    {
        return BodyTag.EVAL_BODY_BUFFERED;
    }

    @Override
    public int doEndTag() throws Exception
    {
        String content = null;
        BodyContent bodyContent = (BodyContent)(this.getBodyContent());

        if(bodyContent != null)
        {
            content = bodyContent.getString();

            if(this.trim)
            {
                content = content.trim();
            }

            if(this.escapeXml)
            {
                content = this.escape(content);
            }

            if(this.encoding == null)
            {
                this.encoding = "UTF-8";
            }

            if(this.file != null)
            {
                IO.write(content.getBytes(this.encoding), new File(this.file));
            }

            if(this.out)
            {
                this.pageContext.getOut().write(content);
                this.pageContext.getOut().flush();
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
     * @return the file
     */
    public String getFile()
    {
        return this.file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(String file)
    {
        this.file = file;
    }
    
    /**
     * @return the encoding
     */
    public String getEncoding()
    {
        return this.encoding;
    }

    /**
     * @param encoding the encoding to set
     */
    public void setEncoding(String encoding)
    {
        this.encoding = encoding;
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

    /**
     * @param out the out to set
     */
    public void setOut(boolean out)
    {
        this.out = out;
    }

    /**
     * @return the out
     */
    public boolean getOut()
    {
        return this.out;
    }

    /**
     * @param trim the trim to set
     */
    public void setTrim(boolean trim)
    {
        this.trim = trim;
    }

    /**
     * @return the trim
     */
    public boolean getTrim()
    {
        return this.trim;
    }
}
