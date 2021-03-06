/*
 * $RCSfile: CompactWriter.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-11-15 $
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
 * <p>Title: CompactWriter</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class CompactWriter extends Writer {
    private boolean flag = true;
    private Writer out;

    /**
     * @param out
     */
    public CompactWriter(Writer out) {
        this.out = out;
    }

    /**
     * none thread-safe
     * @param cbuf
     * @param offset
     * @param length
     * @throws IOException
     */
    @Override
    public void write(char[] cbuf, int offset, int length) throws IOException {
        int j = 0;
        int end = offset + length;
        char[] buffer = new char[length];

        for(int i = offset; i < end; i++) {
            if(cbuf[i] == '\n') {
                if(this.flag) {
                    buffer[j] = cbuf[i];
                    this.flag = false;
                    j++;
                }
            }
            else if(cbuf[i] == '\r') {
                continue;
            }
            else {
                buffer[j] = cbuf[i];
                this.flag = true;
                j++;
            }
        }
        this.out.write(buffer, 0, j);
    }

    /**
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        this.out.close();
    }

    /**
     * @throws IOException
     */
    @Override
    public void flush() throws IOException {
        this.out.flush();
    }
}
