/*
 * $RCSfile: StringStream.java,v $
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
    private int length;
    private int position;
    private String buffer;
    private boolean closed;

    /**
     * end of stream
     */
    public static final int EOF = -1;

    /**
     * default
     */
    public StringStream() {
        this.setStream("");
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
        this.setStream(new String(cbuf, 0, cbuf.length));
    }

    /**
     * @param source
     */
    public void setStream(String source) {
        this.position = 0;
        this.buffer = source;
        this.length = source.length();
        this.closed = (this.position < this.length);
    }

    /**
     * reset position
     */
    public void reset() {
        this.position = 0;
        this.closed = (this.position < this.length);
    }

    /**
     * @return int
     */
    public int back() {
        if(this.position > 0) {
            this.position--;
        }
        return 1;
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
        if(this.position < this.length) {
            return this.buffer.charAt(this.position);
        }
        return -1;
    }

    /**
     * @param offset
     * @return int
     */
    public int peek(int offset) {
        int index = this.position + offset;

        if(index < this.length) {
            return this.buffer.charAt(index);
        }
        return -1;
    }

    /**
     * @return int
     */
    public int read() {
        if(this.position < this.length) {
            return this.buffer.charAt(this.position++);
        }
        return -1;
    }

    /**
     * @param c
     * @return boolean
     */
    public boolean next(char c) {
        if(this.position < this.length && this.buffer.charAt(this.position) == c) {
            this.position++;
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * @param c
     * @return int
     */
    public int find(char c) {
        int index = this.position;

        while(index < this.length) {
            if(this.buffer.charAt(index++) == c) {
                break;
            }
        }
        this.position = index;
        return (index < this.length ? index : -1);
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

        int size = Math.min(this.length - this.position, length);

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
     * @param test
     * @param ignoreCase
     * @return String
     */
    public String tryread(String test, boolean ignoreCase) {
        int size = test.length();

        if((this.length - this.position) >= size) {
            int i = this.position;

            if(ignoreCase == true) {
                String temp = test.toLowerCase();

                for(int j = 0; j < size; i++, j++) {
                    if(Character.toLowerCase(this.buffer.charAt(i)) != temp.charAt(j)) {
                        return "";
                    }
                }
            }
            else {
                for(int j = 0; j < size; i++, j++) {
                    if(this.buffer.charAt(i) != test.charAt(j)) {
                        return "";
                    }
                }
            }
            this.position += size;
            return test;
        }
        return "";
    }

    /**
     * @return String
     */
    public String readLine() {
        if(this.position >= this.length) {
            return null;
        }

        int index = this.position;
        int offset = this.position;
        int length = this.length;

        while(index < length) {
            if(this.buffer.charAt(index++) == '\n') {
                break;
            }
        }
        this.position = index;
        return this.buffer.substring(offset, index);
    }

    /**
     * @param length
     * @return int
     */
    public int skip(int length) {
        if(this.position + length <= this.length) {
            this.position = this.position + length;
            return length;
        }
        int index = this.position;
        this.position = this.length;
        return this.length - index;
    }

    /**
     * skip whitespace
     */
    public void skipWhitespace() {
        while(this.position < this.length) {
            if(this.buffer.charAt(this.position) > ' ') {
                break;
            }
            this.position++;
        }
    }

    /**
     * @return boolean
     */
    public boolean eof() {
        return (this.position >= this.length);
    }

    /**
     * @return String
     */
    public String getRemain() {
        return this.buffer.substring(this.position, this.length);
    }

    /**
     * @param size
     * @return String
     */
    public String getRemain(int size) {
        int count = this.length - this.position;

        if(size < count) {
            return this.buffer.substring(this.position, this.position + size);
        }
        else {
            return this.buffer.substring(this.position, this.position + count);
        }
    }

    /**
     * @param search
     * @return boolean
     */
    public boolean match(String search) {
        int j = this.position;
        int count = search.length();

        for(int i = 0; i < count; i++, j++) {
            if(j >= this.length || search.charAt(i) != this.buffer.charAt(j)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param index
     * @return char
     */
    public char charAt(int index) {
        return this.buffer.charAt(index);
    }

    /**
     * @param offset
     * @param length
     * @return String
     */
    public String getString(int offset, int length) {
        return this.buffer.substring(offset, offset + length);
    }

    /**
     * @param start
     * @param end
     * @return String
     */
    public String substring(int start, int end) {
        return this.buffer.substring(start, end);
    }

    /**
     * @param offset
     * @param length
     * @return String
     */
    public char[] getChars(int offset, int length) {
        char[] cbuf = new char[length];
        this.buffer.getChars(offset, offset + length, cbuf, 0);
        return cbuf;
    }

    /**
     * @param offset
     * @param length
     * @return int
     */
    public int hash(int offset, int length) {
        int h = 0;
        int index = offset;

        for(int i = offset; i < length; i++) {
            h = 31 * h + this.buffer.charAt(index++);
        }
        return h;
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
        return this.length;
    }

    /**
     */
    public void close() {
        this.closed = true;
    }
}
