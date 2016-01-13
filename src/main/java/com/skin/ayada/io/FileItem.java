/*
 * $RCSfile: FileItem.java,v $$
 * $Revision: 1.1 $
 * $Date: 2014-04-12 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.io;

import java.io.File;
import java.io.IOException;

/**
 * <p>Title: FileItem</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class FileItem {
    private int flag;
    private File file;

    public FileItem(File file) {
        this.flag = 0;
        this.file = file;
    }

    /**
     * @return the flag
     */
    public int getFlag() {
        return this.flag;
    }

    /**
     * @param flag the flag to set
     */
    public void setFlag(int flag) {
        this.flag = flag;
    }

    /**
     * @return the file
     */
    public File getFile() {
        return this.file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * @return boolean
     */
    public boolean isFile() {
        return this.file.isFile();
    }

    /**
     * @return boolean
     */
    public boolean isDirectory() {
        return this.file.isDirectory();
    }

    /**
     * @return String
     */
    public String getAbsolutePath() {
        return this.file.getAbsolutePath();
    }

    public String getCanonicalPath() throws IOException {
        return this.file.getCanonicalPath();
    }
}
