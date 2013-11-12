/*
 * $RCSfile: StringStream.java,v $$
 * $Revision: 1.1  $
 * $Date: 2012-7-3  $
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
public class StringStream
{
    private int length;
    private int position;
    private char[] buffer;
    private boolean closed;

    /**
     * @param length
     */
    public StringStream(int length)
    {
        this.length = 0;
        this.position = 0;
        this.buffer = new char[length];
        this.closed = false;
    }

    /**
     * @param length
     */
    public StringStream(String source)
    {
        this(source.toCharArray());
    }

    /**
     * @param cbuf
     */
    public StringStream(char[] cbuf)
    {
        this.buffer = cbuf;
        this.position = 0;
        this.length = cbuf.length;
        this.closed = false;
    }

    /**
     * @return int
     */
    public int back()
    {
        return this.back(1);
    }

    /**
     * @param length
     * @return int
     */
    public int back(int length)
    {
        if(length < 1)
        {
            return 0;
        }

        int len = (length > this.position ? this.position : length);
        this.position -= len;
        return len;
    }

    /**
     * @return int
     */
    public int peek()
    {
        if(this.position < this.length)
        {
            return buffer[this.position];
        }

        return -1;
    }

    /**
     * @param i
     * @return int
     */
    public int peek(int offset)
    {
        int index = this.position + offset;

        if(index < this.length)
        {
            return this.buffer[index];
        }

        return -1;
    }

    /**
     * @return int
     */
    public int read()
    {
        if(this.position < this.length)
        {
            return buffer[this.position++];
        }

        return -1;
    }

    /**
     * @param cbuf
     * @return int
     */
    public int read(char[] cbuf)
    {
        return this.read(cbuf, 0, cbuf.length);
    }

    /**
     * @param cbuf
     * @param offset
     * @param length
     * @return int
     */
    public int read(char[] cbuf, int offset, int length)
    {
        if(this.closed)
        {
            throw new RuntimeException("stream is closed !");
        }

        int size = Math.min(this.length - this.position, length);

        if(size > 0)
        {
            System.arraycopy(this.buffer, this.position, cbuf, offset, size);
            this.position += size;
        }
        else
        {
            size = -1;
        }

        return size;
    }

    /**
     * @param position
     */
    public void setPosition(int position)
    {
        this.position = position;
    }

    /**
     * @return position
     */
    public int getPosition()
    {
        return this.position;
    }

    /**
     * @return int
     */
    public int length()
    {
        return this.length;
    }

    /**
     */
    public void close()
    {
        this.closed = true;
    }
}
