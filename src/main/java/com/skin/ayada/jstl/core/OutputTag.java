/*
 * $RCSfile: OutputTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-26 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import java.io.File;

import com.skin.ayada.tagext.BodyContent;
import com.skin.ayada.tagext.BodyTag;
import com.skin.ayada.tagext.BodyTagSupport;
import com.skin.ayada.util.IO;

/**
 * <p>Title: OutputTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class OutputTag extends BodyTagSupport {
    private String file;
    private String encoding;
    private boolean escapeXml = false;
    private boolean out = false;
    private boolean trim = false;

    /**
     * @return int
     */
    @Override
    public int doStartTag() throws Exception {
        return BodyTag.EVAL_BODY_BUFFERED;
    }

    /**
     * @return int
     */
    @Override
    public int doEndTag() throws Exception {
        BodyContent bodyContent = this.getBodyContent();

        if(bodyContent != null) {
            String content = bodyContent.getString();

            if(this.trim) {
                content = content.trim();
            }

            if(this.escapeXml) {
                content = OutTag.escape(content);
            }

            if(this.encoding == null) {
                this.encoding = "UTF-8";
            }

            if(this.file != null) {
                IO.write(new File(this.file), content.getBytes(this.encoding));
            }

            if(this.out) {
                this.pageContext.getOut().write(content);
                this.pageContext.getOut().flush();
            }
        }
        return EVAL_PAGE;
    }

    /**
     * @return the file
     */
    public String getFile() {
        return this.file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(String file) {
        this.file = file;
    }

    /**
     * @return the encoding
     */
    public String getEncoding() {
        return this.encoding;
    }

    /**
     * @param encoding the encoding to set
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * @return boolean
     */
    public boolean getEscapeXml() {
        return this.escapeXml;
    }

    /**
     * @param escapeXml
     */
    public void setEscapeXml(boolean escapeXml) {
        this.escapeXml = escapeXml;
    }

    /**
     * @param out the out to set
     */
    public void setOut(boolean out) {
        this.out = out;
    }

    /**
     * @return the out
     */
    public boolean getOut() {
        return this.out;
    }

    /**
     * @param trim the trim to set
     */
    public void setTrim(boolean trim) {
        this.trim = trim;
    }

    /**
     * @return the trim
     */
    public boolean getTrim() {
        return this.trim;
    }
}
