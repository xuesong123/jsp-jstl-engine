/*
 * $RCSfile: JspWriter.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-19 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.runtime;

import java.io.IOException;
import java.io.Writer;

/**
 * <p>Title: JspWriter</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class JspWriter extends Writer {
    private Writer out;
    private int position;
    private char[] buffer;
    private int bufferSize = 8192;
    private char[] writeBuffer;
    private final int writeBufferSize = 1024;
    private boolean autoFlush = false;
    private static final char[] CRLF = "\r\n".toCharArray();
    private static final char[] NULL = "null".toCharArray();
    private static final char[] TRUE = "true".toCharArray();
    private static final char[] FALSE = "false".toCharArray();

    /**
     * @param out
     */
    public JspWriter(Writer out) {
        this(out, 8192, false);
    }

    /**
     * @param out
     * @param bufferSize
     */
    public JspWriter(Writer out, int bufferSize) {
        this(out, bufferSize, false);
    }

    /**
     * @param out
     * @param bufferSize
     * @param autoFlush
     */
    public JspWriter(Writer out, int bufferSize, boolean autoFlush) {
        this.out = out;
        this.bufferSize = bufferSize;
        this.buffer = new char[bufferSize];
        this.autoFlush = autoFlush;
    }

    /**
     * @param cbuf
     * @param offset
     * @param length
     * @throws IOException
     */
    @Override
    public void write(char[] cbuf, int offset, int length) throws IOException {
        if(this.autoFlush) {
            this.flush();
            this.out.write(cbuf, offset, length);
            this.out.flush();
            return;
        }

        if(length > this.buffer.length - this.position) {
            this.flush();
            this.out.write(cbuf, offset, length);
        }
        else {
            System.arraycopy(cbuf, offset, this.buffer, this.position, length);
            this.position += length;
        }
    }
    
    /**
     * @param str
     * @param offset
     * @param length
     * @throws IOException
     */
    @Override
    public void write(String str, int offset, int length) throws IOException {
        char cbuf[] = null;

        if(length <= this.writeBufferSize) {
            if(this.writeBuffer == null) {
                this.writeBuffer = new char[this.writeBufferSize];
            }
            cbuf = this.writeBuffer;
        }
        else {
            cbuf = new char[length];
        }
        str.getChars(offset, (offset + length), cbuf, 0);
        this.write(cbuf, 0, length);
    }

    /**
     * @param buffer
     * @throws IOException
     */
    public void write(StringBuilder buffer) throws IOException {
        this.write(buffer, 0, buffer.length());
    }

    /**
     * @param buffer
     * @param offset
     * @param length
     * @throws IOException
     */
    public void write(StringBuilder buffer, int offset, int length) throws IOException {
        char cbuf[] = null;

        if(length <= this.writeBufferSize) {
            if(this.writeBuffer == null) {
                this.writeBuffer = new char[this.writeBufferSize];
            }
            cbuf = this.writeBuffer;
        }
        else {
            cbuf = new char[length];
        }
        buffer.getChars(offset, (offset + length), cbuf, 0);
        this.write(cbuf, 0, length);
    }

    /**
     * @param object
     * @throws IOException
     */
    public void write(Object object) throws IOException {
        if(object != null) {
            String content = object.toString();
            this.write(content, 0, content.length());
        }
    }

    /**
     * @param b
     * @throws IOException
     */
    public void print(boolean b) throws IOException {
        if(b) {
            this.write(TRUE, 0, 4);
        }
        else {
            this.write(FALSE, 0, 5);
        }
    }

    /**
     * @param c
     * @throws IOException
     */
    public void print(char c) throws IOException {
        this.write(new char[]{c}, 0, 1);
    }

    /**
     * @param cbuf
     * @throws IOException
     */
    public void print(char[] cbuf) throws IOException {
        this.write(cbuf, 0, cbuf.length);
    }

    /**
     * @param d
     * @throws IOException
     */
    public void print(double d) throws IOException {
        this.write(Double.toString(d));
    }

    /**
     * @param f
     * @throws IOException
     */
    public void print(float f) throws IOException {
        this.write(Float.toString(f));
    }

    /**
     * @param i
     * @throws IOException
     */
    public void print(int i) throws IOException {
        this.write(Integer.toString(i));
    }

    /**
     * @param l
     * @throws IOException
     */
    public void print(long l) throws IOException {
        this.write(Long.toString(l));
    }

    /**
     * @param content
     * @throws IOException
     */
    public void print(String content) throws IOException {
        if(content != null) {
            this.write(content, 0, content.length());
        }
    }

    /**
     * @param value
     * @throws IOException
     */
    public void print(Object value) throws IOException {
        if(value != null) {
            String content = value.toString();
            this.write(value.toString(), 0, content.length());
        }
    }

    /**
     * @param value
     * @param nullable
     * @throws IOException
     */
    public void print(Object value, boolean nullable) throws IOException {
        if(value != null) {
            String content = value.toString();
            this.write(value.toString(), 0, content.length());
        }
        else {
            if(nullable) {
                this.write(NULL, 0, 4);
            }
        }
    }

    /**
     * @throws IOException
     */
    public void println() throws IOException {
        this.write(CRLF, 0, 2);
    }

    /**
     * @param b
     * @throws IOException
     */
    public void println(boolean b) throws IOException {
        if(b) {
            this.write(TRUE, 0, 4);
        }
        else {
            this.write(FALSE, 0, 5);
        }
        this.write(CRLF, 0, 2);
    }

    /**
     * @param c
     * @throws IOException
     */
    public void println(char c) throws IOException {
        this.write(new char[]{c}, 0, 1);
        this.write(CRLF, 0, 2);
    }

    /**
     * @param cbuf
     * @throws IOException
     */
    public void println(char[] cbuf) throws IOException {
        this.write(cbuf, 0, cbuf.length);
        this.write(CRLF, 0, 2);
    }

    /**
     * @param d
     * @throws IOException
     */
    public void println(double d) throws IOException {
        this.write(String.valueOf(d));
        this.write(CRLF, 0, 2);
    }

    /**
     * @param f
     * @throws IOException
     */
    public void println(float f) throws IOException {
        this.write(Float.toString(f));
        this.write(CRLF, 0, 2);
    }

    /**
     * @param i
     * @throws IOException
     */
    public void println(int i) throws IOException {
        this.write(Integer.toString(i));
        this.write(CRLF, 0, 2);
    }

    /**
     * @param l
     * @throws IOException
     */
    public void println(long l) throws IOException {
        this.write(Long.toString(l));
        this.write(CRLF, 0, 2);
    }

    /**
     * @param content
     * @throws IOException
     */
    public void println(String content) throws IOException {
        if(content != null) {
            this.write(content, 0, content.length());
        }
        this.write(CRLF, 0, 2);
    }

    /**
     * @param value
     * @throws IOException
     */
    public void println(Object value) throws IOException {
        if(value != null) {
            this.write(value.toString());
        }
        this.write(CRLF, 0, 2);
    }

    /**
     * @param value
     * @param nullable
     * @throws IOException
     */
    public void println(Object value, boolean nullable) throws IOException {
        if(value != null) {
            this.write(value.toString());
        }
        else if(nullable) {
            this.write(NULL, 0, 4);
        }
        this.write(CRLF, 0, 2);
    }

    /**
     * @throws IOException
     */
    public void newLine() throws IOException {
        this.write(CRLF);
    }

    /**
     * @return int
     */
    public int getBufferSize() {
        return this.bufferSize;
    }

    /**
     * @return int
     */
    public int getRemaining() {
        return this.bufferSize - this.position;
    }

    /**
     * @param bufferSize the bufferSize to set
     */
    public void setBufferSize(int bufferSize) {
        try {
            this.flush();
        }
        catch(IOException e) {
        }

        this.position = 0;
        this.bufferSize = bufferSize;
        this.buffer = new char[bufferSize];
    }

    /**
     * @param autoFlush the autoFlush to set
     */
    public void setAutoFlush(boolean autoFlush) {
        this.autoFlush = autoFlush;

        if(this.autoFlush) {
            try {
                this.flush();
            }
            catch(IOException e) {
            }
        }
    }

    /**
     * @return boolean
     */
    public boolean isAutoFlush() {
        return this.autoFlush;
    }

    /**
     * @throws IOException
     */
    public void clear() throws IOException {
        this.position = 0;
    }

    /**
     * @throws IOException
     */
    public void clearBuffer() throws IOException {
        this.clear();
    }

    /**
     * @throws IOException
     */
    @Override
    public void flush() throws IOException {
        if(this.position > 0) {
            this.out.write(this.buffer, 0, this.position);
            this.position = 0;
        }
        this.out.flush();
    }

    /**
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        this.flush();
        this.out.close();
    }

    /**
     * @return Writer
     */
    public Writer getOut() {
        return this.out;
    }

    /**
     * @param out
     */
    public void setOut(Writer out) {
        this.out = out;
    }
}
