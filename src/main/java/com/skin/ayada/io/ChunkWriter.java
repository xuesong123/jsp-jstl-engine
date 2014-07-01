/*
 * $RCSfile: ChunkWriter.java,v $$
 * $Revision: 1.1  $
 * $Date: 2014-6-30  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.io;

import java.io.IOException;
import java.io.Writer;

/**
 * <p>Title: ChunkWriter</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class ChunkWriter extends Writer
{
    private int position;
    private char[] buffer;
    private int bufferSize = 8192;
    private ChunkWriter next;
    private ChunkWriter tail;

    /**
     * @param bufferSize
     */
    public ChunkWriter(int bufferSize)
    {
        this.bufferSize = bufferSize;
        this.buffer = new char[bufferSize];
        this.position = 0;
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException
    {
        int count = 0;
        int offset = off;
        int remain = len;
        ChunkWriter tail = this.tail;

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
                tail.tail = new ChunkWriter(this.bufferSize);
                tail.next = tail.tail;
                this.tail = tail.tail;
                tail = tail.tail;

                if(this.next == null)
                {
                    this.next = this.tail;
                }
            }
        }
    }

    /**
     * @return int
     */
    @SuppressWarnings("resource")
    public int getChunkSize()
    {
        int size = 0;
        ChunkWriter writer = this;

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
    @SuppressWarnings("resource")
    public int length()
    {
        int length = 0;
        ChunkWriter writer = this;

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
    public ChunkWriter getNext()
    {
        return this.next;
    }

    /**
     * @param next the next to set
     */
    public void setNext(ChunkWriter next)
    {
        this.next = next;
    }

    /**
     * @return the tail
     */
    public ChunkWriter getTail()
    {
        return this.tail;
    }

    /**
     * @param tail the tail to set
     */
    public void setTail(ChunkWriter tail)
    {
        this.tail = tail;
    }

    /**
     * @throws IOException
     */
    @Override
    public void close() throws IOException
    {
    }

    /**
     * @throws IOException
     */
    @Override
    public void flush() throws IOException
    {
    }

    @SuppressWarnings("resource")
    public void free()
    {
        ChunkWriter next = this;
        ChunkWriter temp = this;

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
    @SuppressWarnings("resource")
    public String toString()
    {
        int length = this.length();
        ChunkWriter writer = this;
        StringBuilder buffer = new StringBuilder(length);

        while(writer != null)
        {
            buffer.append(writer.buffer, 0, writer.position);
            writer = writer.next;
        }

        return buffer.toString();
    }

    @SuppressWarnings("resource")
    public static void main(String[] args)
    {
        char[] cbuf = "1234567890".toCharArray();
        ChunkWriter writer = new ChunkWriter(2);

        for(int i = 0; i < 3; i++)
        {
            try
            {
                writer.write(cbuf);
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }

        System.out.println("012345678901234567890123456789012345678901234567890123456789");
        System.out.println(writer.toString());
    }
}
