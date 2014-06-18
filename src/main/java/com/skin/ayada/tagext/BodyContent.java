/*
 * $RCSfile: BodyContent.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-19 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.tagext;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import com.skin.ayada.runtime.JspWriter;

/**
 * <p>Title: BodyContent</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class BodyContent extends JspWriter
{
    private StringWriter buffer;

    /**
     * @param out
     */
    public BodyContent(JspWriter out)
    {
        super(out);
        this.buffer = new StringWriter();
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException
    {
        this.buffer.write(cbuf, off, len);
    }

    /**
     * @param out
     * @throws IOException
     */
    public void writeOut(Writer out)  throws IOException
    {
        out.write(this.buffer.toString());
    }

    @Override
    public void clear() throws IOException
    {
        this.buffer.getBuffer().setLength(0);
    }

    @Override
    public void flush() throws IOException
    {
    }

    @Override
    public void close() throws IOException
    {
    }

    /**
     * @return JspWriter
     */
    public JspWriter getEnclosingWriter()
    {
        return (JspWriter)(this.getOut());
    }

    /**
     * @return String
     */
    public String getString()
    {
        return this.buffer.toString();
    }
}
