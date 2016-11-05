/*
 * $RCSfile: CopyTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2014-03-26 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.io;

import java.io.File;
import java.io.IOException;

import com.skin.ayada.tagext.Tag;

/**
 * <p>Title: CopyTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class MakeDirTag extends FileTag {
    /**
     * @return int
     * @throws Exception
     */
    @Override
    public int doEndTag() throws Exception {
        MakeDirTag.mkdirs(this.file);
        return Tag.EVAL_PAGE;
    }

    /**
     * @param file
     * @throws IOException
     */
    @Override
    public void process(File file) throws IOException {
    }

    /**
     * @param path
     */
    public static void mkdirs(String path) {
        File file = new File(path);

        if(file.exists() == false) {
            file.mkdirs();
        }
    }
}
