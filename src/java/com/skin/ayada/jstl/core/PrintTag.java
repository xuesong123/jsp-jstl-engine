/*
 * $RCSfile: PrintTag.java,v $$
 * $Revision: 1.1  $
 * $Date: 2011-12-9  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Iterator;

import com.skin.ayada.jstl.util.BeanUtil;
import com.skin.ayada.runtime.PageContext;
import com.skin.ayada.tagext.TagSupport;

/**
 * <p>Title: PrintTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class PrintTag extends TagSupport
{
    private static final long serialVersionUID = 3337947213732345725L;
    private static final BeanUtil beanUtil = new BeanUtil();
    private Object out;
    private Object value;

    @Override
    public int doEndTag()
    {
        print(this.pageContext, this.out, this.value);
        return TagSupport.EVAL_PAGE;
    }

    /**
     * @param pageContext
     * @param out
     * @param value
     */
    public static void print(PageContext pageContext, Object out, Object value)
    {
        Writer writer = null;

        if(out == null)
        {
            writer = pageContext.getOut();
        }
        else if(out instanceof Writer)
        {
            writer = ((Writer)out);
        }
        else if(out instanceof OutputStream)
        {
            writer = new PrintWriter((OutputStream)out);
        }

        if(writer != null)
        {
            try
            {
                if(value instanceof PageContext)
                {
                    String name = null;
                    Object bean = null;
                    PageContext pc = ((PageContext)value);
                    Iterator<String> iterator = pc.getAttributeNames();

                    while(iterator.hasNext())
                    {
                        name = iterator.next();
                        bean = pc.getAttribute(name);

                        writer.write((name != null ? name : "null"));
                        writer.write(": ");
                        writer.write((bean != null ? bean.toString() : "null"));
                        writer.write("\r\n");
                    }
                }
                else
                {
                    writer.write(beanUtil.toString(value));
                    writer.write("\r\n");
                }

                writer.flush();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * @return the out
     */
    public Object getOut()
    {
        return this.out;
    }

    /**
     * @param out the out to set
     */
    public void setOut(Object out)
    {
        this.out = out;
    }

    /**
     * @return the value
     */
    public Object getValue()
    {
        return this.value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Object value)
    {
        this.value = value;
    }
}