/*
 * $RCSfile: FileScaner.java,v $$
 * $Revision: 1.1 $
 * $Date: 2016-1-13 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * <p>Title: FileScaner</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public abstract class FileScaner {
    private File output;

    /**
     * @param output
     */
    public FileScaner(File output) {
        this.output = output;
    }

    /**
     * @param dir
     */
    public void scan(File dir) {
        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        PrintWriter out = null;

        try {
            outputStream = new FileOutputStream(this.output);
            outputStreamWriter = new OutputStreamWriter(outputStream, "utf-8");
            out = new PrintWriter(outputStreamWriter, true);
            this.doScan(dir, out);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        finally {
            close(out);
            close(outputStreamWriter);
            close(outputStream);
        }
    }

    /**
     * @param dir
     * @param out
     */
    protected abstract void doScan(File dir, PrintWriter out);

    /**
     * @param closeable
     */
    public void close(Closeable closeable) {
        if(closeable != null) {
            try {
                closeable.close();
            }
            catch (IOException e) {
            }
        }
    }
}
