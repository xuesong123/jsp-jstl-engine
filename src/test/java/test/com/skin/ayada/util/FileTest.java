/*
 * $RCSfile: FileTest.java,v $$
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
import java.util.ArrayList;
import java.util.List;

import com.skin.ayada.io.FileItem;

/**
 * <p>Title: FileTest</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class FileTest {
    /**
     * @param args
     */
    public static void main(String[] args) {
        // scan(new File("webapp"));
        // scan1(new File("src\\test\\java"));
        // scan2(new File("src\\test\\java"));
        // scan3(new File("src\\test\\java"));
        new FileScan1(new File("docs\\1.txt")).scan(new File("src\\test\\java"));
        new FileScan2(new File("docs\\2.txt")).scan(new File("src\\test\\java"));
    }

    public static void scan1(File dir) {
        List<File> stack = new ArrayList<File>();
        stack.add(dir);
        int i = 0;
        int dirCount = 0;
        int fileCount = 0;

        while(i < stack.size()) {
            File[] files = stack.get(i).listFiles();
            dirCount++;
            System.out.println("dir: " + stack.get(i).getAbsolutePath());

            for(File file : files) {
                if(file.isDirectory()) {
                    stack.add(file);
                }
                else {
                    fileCount++;
                    System.out.println("file: " + file.getAbsolutePath());
                }
            }
            i++;
        }
        System.out.println("dirCount: " + dirCount + ", fileCount: " + fileCount);
    }

    public static void scan2(File dir) {
        File[] files = dir.listFiles();
        System.out.println(" dir: " + dir.getAbsolutePath());

        for(File file : files) {
            if(file.isDirectory()) {
                scan2(file);
            }
            else {
                System.out.println("file: " + file.getAbsolutePath());
            }
        }
    }

    /**
     * @param dir
     */
    public static void scan3(File dir) {
        int index = 0;
        List<FileItem> stack = new ArrayList<FileItem>();
        stack.add(new FileItem(dir));

        while(!stack.isEmpty()) {
            index = stack.size() - 1;
            FileItem fileItem = stack.get(index);

            if(fileItem.isDirectory()) {

                if(fileItem.getFlag() == 0) {
                    System.out.println(" dir: " + fileItem.getAbsolutePath());
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
                System.out.println("file: " + fileItem.getAbsolutePath());
            }
        }
    }
}
