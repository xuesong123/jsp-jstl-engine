/*
 * $RCSfile: FileScan2.java,v $$
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
import java.util.ArrayList;
import java.util.List;

import com.skin.ayada.io.FileItem;

/**
 * <p>Title: FileScan2</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class FileScan2 extends FileScaner{
    /**
     * @param output
     */
    public FileScan2(File output) {
        super(output);
    }

    @Override
    public void doScan(File dir, PrintWriter out) {
        int index = 0;
        List<FileItem> stack = new ArrayList<FileItem>();
        stack.add(new FileItem(dir));

        while(!stack.isEmpty()) {
            index = stack.size() - 1;
            FileItem fileItem = stack.get(index);

            if(fileItem.isDirectory()) {
                if(fileItem.getFlag() == 0) {
                    out.println(" dir: " + fileItem.getAbsolutePath());
                    fileItem.setFlag(1);
                    File[] files = fileItem.getFile().listFiles();

                    for(int j = files.length - 1; j > -1; j--) {
                        stack.add(new FileItem(files[j]));
                    }
                }
                else {
                    stack.remove(index);
                }
            }
            else {
                stack.remove(index);
                out.println("file: " + fileItem.getAbsolutePath());
            }
        }
    }
}
