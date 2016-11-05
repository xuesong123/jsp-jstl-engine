/*
 * $RCSfile: JspStream.java,v $$
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
import java.io.OutputStream;

/**
 * <p>Title: JspStream</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class JspStream extends OutputStream {
    private OutputStream out;
    private int position;
    private byte[] buffer;
    private int bufferSize = 8192;
    private boolean autoFlush = false;
    private static final byte[] CRLF = "\r\n".getBytes();
    private static final byte[] NULL = "null".getBytes();
    private static final byte[] TRUE = "true".getBytes();
    private static final byte[] FALSE = "false".getBytes();

    /**
     * @param out
     */
    public JspStream(OutputStream out) {
        this(out, 8192, false);
    }

    /**
     * @param out
     * @param bufferSize
     */
    public JspStream(OutputStream out, int bufferSize) {
        this(out, bufferSize, false);
    }

    /**
     * @param out
     * @param bufferSize
     * @param autoFlush
     */
    public JspStream(OutputStream out, int bufferSize, boolean autoFlush) {
        this.out = out;
        this.bufferSize = bufferSize;
        this.buffer = new byte[bufferSize];
        this.autoFlush = autoFlush;
    }

    /**
     * @param b
     * @throws IOException
     */
    @Override
    public void write(int b) throws IOException {
        this.write(new byte[]{(byte)b}, 0, 1);
    }

    /**
     * @param buf
     * @param offset
     * @param length
     * @throws IOException
     */
    @Override
    public void write(byte[] buf, int offset, int length) throws IOException {
        if(this.autoFlush) {
            this.flush();
            this.out.write(buf, offset, length);
            this.out.flush();
            return;
        }

        if(length > this.buffer.length - this.position) {
            this.flush();
            this.out.write(buf, offset, length);
        }
        else {
            System.arraycopy(buf, offset, this.buffer, this.position, length);
            this.position += length;
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
        byte[] bytes = String.valueOf(c).getBytes();
        this.write(bytes, 0, bytes.length);
    }

    /**
     * @param cbuf
     * @throws IOException
     */
    public void print(char[] cbuf) throws IOException {
        byte[] bytes = new String(cbuf, 0, cbuf.length).getBytes();
        this.write(bytes, 0, bytes.length);
    }

    /**
     * @param d
     * @throws IOException
     */
    public void print(double d) throws IOException {
        byte[] bytes = String.valueOf(d).getBytes();
        this.write(bytes, 0, bytes.length);
    }

    /**
     * @param f
     * @throws IOException
     */
    public void print(float f) throws IOException {
        byte[] bytes = String.valueOf(f).getBytes();
        this.write(bytes, 0, bytes.length);
    }

    /**
     * @param i
     * @throws IOException
     */
    public void print(int i) throws IOException {
        byte[] bytes = String.valueOf(i).getBytes();
        this.write(bytes, 0, bytes.length);
    }

    /**
     * @param l
     * @throws IOException
     */
    public void print(long l) throws IOException {
        byte[] bytes = String.valueOf(l).getBytes();
        this.write(bytes, 0, bytes.length);
    }

    /**
     * @param content
     * @throws IOException
     */
    public void print(String content) throws IOException {
        if(content != null) {
            byte[] bytes = content.getBytes();
            this.write(bytes, 0, bytes.length);
        }
    }

    /**
     * @param value
     * @throws IOException
     */
    public void print(Object value) throws IOException {
        if(value != null) {
            byte[] bytes = value.toString().getBytes();
            this.write(bytes, 0, bytes.length);
        }
    }

    /**
     * @param value
     * @param nullable
     * @throws IOException
     */
    public void print(Object value, boolean nullable) throws IOException {
        if(value != null) {
            byte[] bytes = value.toString().getBytes();
            this.write(bytes, 0, bytes.length);
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
        byte[] bytes = String.valueOf(c).getBytes();
        this.write(bytes, 0, bytes.length);
        this.write(CRLF, 0, 2);
    }

    /**
     * @param cbuf
     * @throws IOException
     */
    public void println(char[] cbuf) throws IOException {
        byte[] bytes = new String(cbuf, 0, cbuf.length).getBytes();
        this.write(bytes, 0, bytes.length);
        this.write(CRLF, 0, 2);
    }

    /**
     * @param d
     * @throws IOException
     */
    public void println(double d) throws IOException {
        byte[] bytes = String.valueOf(d).getBytes();
        this.write(bytes, 0, bytes.length);
        this.write(CRLF, 0, 2);
    }

    /**
     * @param f
     * @throws IOException
     */
    public void println(float f) throws IOException {
        byte[] bytes = String.valueOf(f).getBytes();
        this.write(bytes, 0, bytes.length);
        this.write(CRLF, 0, 2);
    }

    /**
     * @param i
     * @throws IOException
     */
    public void println(int i) throws IOException {
        byte[] bytes = String.valueOf(i).getBytes();
        this.write(bytes, 0, bytes.length);
        this.write(CRLF, 0, 2);
    }

    /**
     * @param l
     * @throws IOException
     */
    public void println(long l) throws IOException {
        byte[] bytes = String.valueOf(l).getBytes();
        this.write(bytes, 0, bytes.length);
        this.write(CRLF, 0, 2);
    }

    /**
     * @param content
     * @throws IOException
     */
    public void println(String content) throws IOException {
        if(content != null) {
            byte[] bytes = content.getBytes();
            this.write(bytes, 0, bytes.length);
        }
        this.write(CRLF, 0, 2);
    }

    /**
     * @param value
     * @throws IOException
     */
    public void println(Object value) throws IOException {
        if(value != null) {
            byte[] bytes = value.toString().getBytes();
            this.write(bytes, 0, bytes.length);
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
            byte[] bytes = value.toString().getBytes();
            this.write(bytes, 0, bytes.length);
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
        this.buffer = new byte[bufferSize];
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
    public OutputStream getOut() {
        return this.out;
    }

    /**
     * @param out
     */
    public void setOut(OutputStream out) {
        this.out = out;
    }
}
