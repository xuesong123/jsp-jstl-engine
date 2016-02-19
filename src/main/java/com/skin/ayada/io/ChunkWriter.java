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
public class ChunkWriter extends Writer {
    private CharBuffer buffer;

    /**
     * @param bufferSize
     */
    public ChunkWriter(int bufferSize) {
        this.buffer = new CharBuffer(bufferSize);
    }

    @Override
    public void write(char[] cbuf, int offset, int length) throws IOException {
        this.buffer.append(cbuf, offset, length);
    }

    /**
     * @return int
     */
    public int getChunkSize() {
        return this.buffer.getChunkSize();
    }

    /**
     * @return int
     */
    public int length() {
        return this.buffer.length();
    }

    /**
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
    }

    /**
     * @throws IOException
     */
    @Override
    public void flush() throws IOException {
    }

    /**
     *
     */
    public void free() {
        this.buffer.free();
    }

    @Override
    public String toString() {
        return this.buffer.toString();
    }
}
