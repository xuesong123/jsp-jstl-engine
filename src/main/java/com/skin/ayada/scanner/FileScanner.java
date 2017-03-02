/*
 * $RCSfile: FileScanner.java,v $
 * $Revision: 1.1 $
 * $Date: 2016-01-13 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.scanner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.skin.ayada.util.Path;

/**
 * <p>Title: FileScanner</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class FileScanner implements Scanner {
    String work;

    /**
     * @param work
     */
    public FileScanner(String work) {
        this.work = work;
    }

    /**
     * @param visitor
     * @throws Exception
     */
    @Override
    public void accept(Visitor visitor) throws Exception {
        accept(new File(this.work), visitor);
    }

    /**
     * @param file
     * @param visitor
     * @throws Exception
     */
    public static void accept(File file, Visitor visitor) throws Exception {
        int index = 0;
        String home = file.getAbsolutePath();
        List<FileItem> stack = new ArrayList<FileItem>();
        stack.add(new FileItem(file));

        while(!stack.isEmpty()) {
            index = stack.size() - 1;
            FileItem fileItem = stack.get(index);
            String path = Path.getRelativePath(home, fileItem.getAbsolutePath());

            if(fileItem.isDirectory()) {
                if(fileItem.getFlag() == 0) {
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
                visitor.visit(home, path);
            }
        }
    }
}
