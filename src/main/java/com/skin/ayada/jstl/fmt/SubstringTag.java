/*
 * $RCSfile: SubstringTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-3-6 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.fmt;

import java.io.IOException;

import com.skin.ayada.tagext.BodyContent;
import com.skin.ayada.tagext.BodyTagSupport;
import com.skin.ayada.tagext.Tag;

/**
 * <p>Title: SubstringTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class SubstringTag extends BodyTagSupport
{
    private int length;
    private String value;
    private String padding;

    /**
     * @return int
     */
    @Override
    public int doEndTag()
    {
        String result = null;

        if(this.value != null)
        {
            result = this.substring(this.value, this.length, this.padding);
        }
        else
        {
            BodyContent bodyContent = this.getBodyContent();
            result = this.substring(bodyContent.getString(), this.length, this.padding);
        }

        try
        {
            this.pageContext.getOut().write(result);
        }
        catch(IOException e)
        {
        }

        return Tag.EVAL_PAGE;
    }

    /**
     * @param source
     * @param length
     * @param padding
     * @return String
     */
    private String substring(String source, int length, String padding)
    {
        if(source == null)
        {
            return "";
        }

        String s = source.trim();

        char c;
        int size = 0;
        int count = s.length();
        StringBuilder buffer = new StringBuilder();

        for(int i = 0; i < s.length(); i++)
        {
            c = s.charAt(i);

            if(c >= 0x0080)
            {
                size += 2;
                count++;
            }
            else
            {
                size++;
            }

            if(size > length)
            {
                if(c >= 0x4e00)
                {
                    size -= 2;
                }
                else
                {
                    size--;
                }

                break;
            }

            buffer.append(c);
        }

        if(size < count && padding != null)
        {
            buffer.append(padding);
        }

        return buffer.toString();
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

    /**
     * @return the value
     */
    public String getValue()
    {
        return this.value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value)
    {
        this.value = value;
    }

    /**
     * @return the padding
     */
    public String getPadding()
    {
        return this.padding;
    }

    /**
     * @param padding the padding to set
     */
    public void setPadding(String padding)
    {
        this.padding = padding;
    }
}
