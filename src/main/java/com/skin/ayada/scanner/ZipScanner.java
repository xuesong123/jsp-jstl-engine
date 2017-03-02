/*
 * $RCSfile: ZipScanner.java,v $
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
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.skin.ayada.util.StringUtil;

/**
 * <p>Title: ZipScanner</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class ZipScanner implements Scanner {
    private String zipFile;

    /**
     * @param zipFile
     */
    public ZipScanner(String zipFile) {
        this.zipFile = zipFile;
    }

    /**
     * @param visitor
     * @throws Exception
     */
    @Override
    public void accept(Visitor visitor) throws Exception {
        accept(new File(this.zipFile), visitor, "/");
    }

    /**
     * @param file
     * @param visitor
     * @param root
     * @throws Exception
     */
    public static void accept(File file, Visitor visitor, String root) throws Exception {
        ZipFile zipFile = null;

        try {
            zipFile = new ZipFile(file);
            accept(zipFile, visitor, root);
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            if(zipFile != null) {
                try {
                    zipFile.close();
                }
                catch(IOException e) {
                }
            }
        }
    }

    /**
     * @param zipFile
     * @param visitor
     * @param root
     * @throws Exception
     */
    public static void accept(ZipFile zipFile, Visitor visitor, String root) throws Exception {
        String prefix = StringUtil.trim(root, '/');
        Enumeration<? extends ZipEntry> entries = zipFile.entries();

        if(!prefix.endsWith("/")) {
            prefix = prefix + "/";
        }

        while(entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            String name = entry.getName();

            if(name.endsWith("/")) {
                continue;
            }

            if(prefix.equals("/") || name.startsWith(prefix)) {
                visitor.visit(root, root);
            }
        }
    }
}
