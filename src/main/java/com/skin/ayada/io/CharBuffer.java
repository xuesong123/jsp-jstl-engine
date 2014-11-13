/*
 * $RCSfile: CharBuffer.java,v $$
 * $Revision: 1.1  $
 * $Date: 2014-6-30  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.io;

/**
 * <p>Title: CharBuffer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class CharBuffer
{
    private int position;
    private char[] buffer;
    private int bufferSize = 8192;
    private CharBuffer next;
    private CharBuffer tail;
    private char[] temp = new char[4];
    private static final char[] NULL = new char[]{'n', 'u', 'l', 'l'};
    private static final char[] TRUE = new char[]{'t', 'r', 'u', 'e'};
    private static final char[] FALSE = new char[]{'f', 'a', 'l', 's', 'e'};

    /**
     * @param bufferSize
     */
    public CharBuffer(int bufferSize)
    {
        this.bufferSize = bufferSize;
        this.buffer = new char[bufferSize];
        this.position = 0;
    }

    /**
     * @param cbuf
     */
    public CharBuffer append(char[] cbuf)
    {
        return this.append(cbuf, 0, cbuf.length);
    }

    /**
     * @param cbuf
     * @param off
     * @param len
     */
    public CharBuffer append(char[] cbuf, int off, int len)
    {
        int count = 0;
        int offset = off;
        int remain = len;
        CharBuffer tail = this.tail;

        if(tail == null)
        {
            tail = this;
        }

        while(remain > 0)
        {
            count = Math.min(tail.bufferSize - tail.position, remain);

            if(count > 0)
            {
                System.arraycopy(cbuf, offset, tail.buffer, tail.position, count);
                tail.position += count;
                remain -= count;
                offset += count;
            }

            if(remain > 0)
            {
                tail.tail = new CharBuffer(this.bufferSize);
                tail.next = tail.tail;
                this.tail = tail.tail;
                tail = tail.tail;

                if(this.next == null)
                {
                    this.next = this.tail;
                }
            }
        }

        return this;
    }

    /**
     * @param str
     * @return CharBuffer
     */
    public CharBuffer append(String str)
    {
        if(str != null)
        {
            char[] cbuf = str.toCharArray();
            return this.append(cbuf, 0, cbuf.length);
        }
        else
        {
            return this.append(NULL);
        }
    }

    /**
     * @param c
     * @return CharBuffer
     */
    public CharBuffer append(char c)
    {
        this.temp[0] = c;
        return this.append(this.temp, 0, 1);
    }

    /**
     * @param b
     * @return CharBuffer
     */
    public CharBuffer append(boolean b)
    {
        if(b)
        {
            return this.append(TRUE, 0, 4);
        }
        else
        {
            return this.append(FALSE, 0, 5);
        }
    }

    /**
     * @param b
     * @return CharBuffer
     */
    public CharBuffer append(byte b)
    {
        return this.append(Byte.toString(b));
    }

    /**
     * @param s
     * @return CharBuffer
     */
    public CharBuffer append(short s)
    {
        return this.append(Short.toString(s));
    }

    /**
     * @param i
     * @return CharBuffer
     */
    public CharBuffer append(int i)
    {
        return this.append(Integer.toString(i));
    }

    /**
     * @param f
     * @return CharBuffer
     */
    public CharBuffer append(float f)
    {
        return this.append(Float.toString(f));
    }

    /**
     * @param d
     * @return CharBuffer
     */
    public CharBuffer append(double d)
    {
        return this.append(Double.toString(d));
    }

    /**
     * @param l
     * @return CharBuffer
     */
    public CharBuffer append(long l)
    {
        return this.append(Long.toString(l));
    }

    /**
     * @param object
     * @return CharBuffer
     */
    public CharBuffer append(Object object)
    {
        if(object != null)
        {
            char[] cbuf = object.toString().toCharArray();
            return this.append(cbuf, 0, cbuf.length);
        }
        else
        {
            return this.append(NULL);
        }
    }

    /**
     * @return int
     */
    public int getChunkSize()
    {
        int size = 0;
        CharBuffer writer = this;

        while(writer != null)
        {
            size++;
            writer = writer.next;
        }

        return size;
    }

    /**
     * @return int
     */
    public int length()
    {
        int length = 0;
        CharBuffer writer = this;

        while(writer != null)
        {
            length += writer.position;
            writer = writer.next;
        }

        return length;
    }

    /**
     * @return the next
     */
    public CharBuffer getNext()
    {
        return this.next;
    }

    /**
     * @param next the next to set
     */
    public void setNext(CharBuffer next)
    {
        this.next = next;
    }

    /**
     * @return the tail
     */
    public CharBuffer getTail()
    {
        return this.tail;
    }

    /**
     * @param tail the tail to set
     */
    public void setTail(CharBuffer tail)
    {
        this.tail = tail;
    }

    public void free()
    {
        CharBuffer next = this;
        CharBuffer temp = this;

        while(next != null)
        {
            temp = next.next;
            next.next = null;
            next.tail = null;
            next.position = 0;
            next = temp;
        }
    }

    @Override
    public String toString()
    {
        int length = this.length();
        CharBuffer writer = this;
        StringBuilder buffer = new StringBuilder(length);

        while(writer != null)
        {
            buffer.append(writer.buffer, 0, writer.position);
            writer = writer.next;
        }

        return buffer.toString();
    }
}
