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
public class BodyContent extends JspWriter {
    private Writer writer;
    private StringWriter buffer;

    /**
     * @param out
     */
    public BodyContent(JspWriter out) {
        super(out);
        this.buffer = new StringWriter();
    }

    /**
     * @return the writer
     */
    public Writer getWriter() {
        return this.writer;
    }

    /**
     * @param writer the writer to set
     */
    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    @Override
    public void write(char[] cbuf, int offset, int length) throws IOException {
        if(this.writer != null) {
            this.writer.write(cbuf, offset, length);
        }
        else {
            this.buffer.write(cbuf, offset, length);
        }
    }

    /**
     * @param out
     * @throws IOException
     */
    public void writeOut(Writer out)  throws IOException {
        out.write(this.buffer.toString());
    }

    /**
     * @param bufferSize the bufferSize to set
     */
    @Override
    public void setBufferSize(int bufferSize) {
    }

    /**
     * @return int
     */
    @Override
    public int getBufferSize() {
        if(this.writer != null) {
            return 0;
        }
        else {
            return super.getBufferSize();
        }
    }

    /**
     * @return int
     */
    @Override
    public int getRemaining() {
        if(this.writer != null) {
            return 0;
        }
        else {
            return super.getRemaining();
        }
    }

    @Override
    public void clear() throws IOException {
        if(this.writer == null) {
            this.buffer.getBuffer().setLength(0);
        }
    }

    @Override
    public void flush() throws IOException {
        if(this.writer != null) {
            this.writer.flush();
        }
    }

    @Override
    public void close() throws IOException {
        if(this.writer != null) {
            this.writer.close();
        }
    }

    /**
     * @return JspWriter
     */
    public JspWriter getEnclosingWriter() {
        return (JspWriter)(this.getOut());
    }

    /**
     * @return String
     */
    public String getString() {
        return this.buffer.toString();
    }
}
