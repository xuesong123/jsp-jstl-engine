/*
 * $RCSfile: LoadTag.java,v $
 * $Revision: 1.1 $
 * $Date: 2016-10-3 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.skin.ayada.JspWriter;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.util.IO;

/**
 * <p>Title: LoadTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class LoadTag extends FileTag {
    private String var;
    private String path;
    private String charset;

    /**
     * @return int
     * @throws Exception
     */
    @Override
    public int doEndTag() throws Exception {
        if(this.file == null && this.path == null) {
            throw new java.lang.IllegalArgumentException("file or path must be not null.");
        }

        String content = null;

        if(this.file != null) {
            File file = new File(this.getFile());

            if(file.exists() && file.isFile()) {
                content = IO.read(file, this.charset);
            }
            else {
                throw new IOException(this.getFile() + " is not a file !");
            }
        }
        else if(this.path != null) {
            InputStream inputStream = this.pageContext.getInputStream(this.path);

            if(inputStream != null) {
                content = IO.read(inputStream, this.charset, 4096);
            }
            else {
                throw new IOException(this.getFile() + " not found !");
            }
        }
        else {
            throw new java.lang.IllegalArgumentException("file or path must be not null.");
        }
        
        if(this.var != null) {
            this.pageContext.setAttribute(this.var, content);
        }
        else {
            JspWriter out = this.pageContext.getOut();
            out.write(content, 0, content.length());
        }
        return Tag.EVAL_PAGE;
    }

    /**
     * @param file
     * @throws IOException
     */
    @Override
    public void process(File file) throws IOException {
        throw new java.lang.UnsupportedOperationException("");
    }

    /**
     * @return the var
     */
    public String getVar() {
        return this.var;
    }

    /**
     * @param var the var to set
     */
    public void setVar(String var) {
        this.var = var;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return this.path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the charset
     */
    public String getCharset() {
        return this.charset;
    }

    /**
     * @param charset the charset to set
     */
    public void setCharset(String charset) {
        this.charset = charset;
    }
}
