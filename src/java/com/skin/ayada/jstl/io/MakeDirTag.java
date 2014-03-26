/*
 * $RCSfile: CopyTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2014-3-26 $
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
public class MakeDirTag extends FileTag
{
    /**
     * @return int
     */
    @Override
    public int doEndTag() throws Exception
    {
        File source = new File(this.getFile());

        if(source.exists() == false)
        {
            source.mkdirs();
        }

        return Tag.EVAL_PAGE;
    }

    @Override
    public void process(File file) throws IOException
    {
    }
}
