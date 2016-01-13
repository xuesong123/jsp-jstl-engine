/*
 * $RCSfile: FileScan1.java,v $$
 * $Revision: 1.1 $
 * $Date: 2016-1-13 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.util;

import java.io.File;
import java.io.PrintWriter;

/**
 * <p>Title: FileScan1</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class FileScan1 extends FileScaner{
    /**
     * @param output
     */
    public FileScan1(File output) {
        super(output);
    }

    @Override
    public void doScan(File dir, PrintWriter out) {
        File[] files = dir.listFiles();
        out.println(" dir: " + dir.getAbsolutePath());

        for(File file : files) {
            if(file.isDirectory()) {
                doScan(file, out);
            }
            else {
                out.println("file: " + file.getAbsolutePath());
            }
        }
    }
}
