/*
 * $RCSfile: StringStream.java,v $$
 * $Revision: 1.1 $
 * $Date: 2012-7-03 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.io;

/**
 * <p>Title: StringStream</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class StringStream {
    private int position;
    private char[] buffer;
    private boolean closed;

    /**
     * end of stream
     */
    public static final int EOF = -1;

    /**
     * @param length
     */
    public StringStream(int length) {
        this.position = 0;
        this.buffer = new char[length];
        this.closed = false;
    }

    /**
     * @param source
     */
    public StringStream(String source) {
        this(source.toCharArray());
    }

    /**
     * @param cbuf
     */
    public StringStream(char[] cbuf) {
        this.buffer = cbuf;
        this.position = 0;
        this.closed = false;
    }

    /**
     * @return int
     */
    public int back() {
        return this.back(1);
    }

    /**
     * @param length
     * @return int
     */
    public int back(int length) {
        if(length < 1) {
            return 0;
        }

        int len = (length > this.position ? this.position : length);
        this.position -= len;
        return len;
    }

    /**
     * @return int
     */
    public int peek() {
        if(this.position < this.buffer.length) {
            return this.buffer[this.position];
        }
        return -1;
    }

    /**
     * @param offset
     * @return int
     */
    public int peek(int offset) {
        int index = this.position + offset;

        if(index < this.buffer.length) {
            return this.buffer[index];
        }
        return -1;
    }

    /**
     * @return int
     */
    public int read() {
        if(this.position < this.buffer.length) {
            return this.buffer[this.position++];
        }
        return -1;
    }

    /**
     * @param cbuf
     * @return int
     */
    public int read(char[] cbuf) {
        return this.read(cbuf, 0, cbuf.length);
    }

    /**
     * @param cbuf
     * @param offset
     * @param length
     * @return int
     */
    public int read(char[] cbuf, int offset, int length) {
        if(this.closed) {
            throw new RuntimeException("stream is closed !");
        }

        int size = Math.min(this.buffer.length - this.position, length);

        if(size > 0) {
            System.arraycopy(this.buffer, this.position, cbuf, offset, size);
            this.position += size;
        }
        else {
            size = -1;
        }
        return size;
    }

    /**
     * @return String
     */
    public String readLine() {
        if(this.position >= this.buffer.length) {
            return null;
        }

        int index = this.position;
        int offset = this.position;
        int length = this.buffer.length;

        while(index < length) {
            if(this.buffer[index++] == '\n') {
                break;
            }
        }
        this.position = index;
        return new String(this.buffer, offset, index - offset);
    }

    /**
     * @param length
     * @return int
     */
    public int skip(int length) {
        if(this.position + length <= this.buffer.length) {
            this.position = this.position + length;
            return length;
        }
        int index = this.position;
        this.position = this.buffer.length;
        return (this.buffer.length - index);
    }

    /**
     * @param c
     * @return int
     */
    public int skip(char c) {
        int i = this.position;
        int length = this.buffer.length;

        while(i < length) {
            if(this.buffer[i] == c) {
                break;
            }
            i++;
        }
        return (i - this.position);
    }

    /**
     * skip crlf
     * @return int
     */
    public int skipCRLF() {
        int i = -1;
        int index = 0;

        while(this.position < this.buffer.length) {
            i = this.buffer[this.position];

            if(i == '\r') {
                this.position++;
                continue;
            }

            if(i == '\n') {
                this.position++;
                continue;
            }
            else {
                break;
            }
        }
        return (this.position - index);
    }

    /**
     * skip whitespace
     * @return int
     */
    public int skipWhitespace() {
        int index = this.position;
        while(this.position < this.buffer.length) {
            if(this.buffer[this.position] > ' ') {
                break;
            }
            this.position++;
        }
        return (this.position - index);
    }

    /**
     * @return boolean
     */
    public boolean eof() {
        return (this.position >= this.buffer.length);
    }

    /**
     * @return String
     */
    public String getRemain() {
        return new String(this.buffer, this.position, this.buffer.length - this.position);
    }

    /**
     * @param search
     * @return boolean
     */
    public boolean match(String search) {
        int i = 0;
        int j = this.position;
        int length = search.length();

        while(i < length) {
            if(j >= this.buffer.length || search.charAt(i) != this.buffer[j]) {
                break;
            }
            i++;
            j++;
        }
        return true;
    }

    /**
     * @param offset
     * @param length
     * @return String
     */
    public String getString(int offset, int length) {
        return new String(this.buffer, offset, length);
    }

    /**
     * @param position
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * @return position
     */
    public int getPosition() {
        return this.position;
    }

    /**
     * @return int
     */
    public int length() {
        return this.buffer.length;
    }

    /**
     */
    public void close() {
        this.closed = true;
    }
}
