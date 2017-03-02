/*
 * $RCSfile: PrintStackTraceTag.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-11-08 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TagSupport;

/**
 * <p>Title: PrintStackTraceTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class PrintStackTraceTag extends TagSupport {
    private Throwable throwable;
    private boolean escapeXml = false;

    /**
     * @return int
     */
    @Override
    public int doStartTag() throws Exception {
        if(this.throwable != null) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter out = new PrintWriter(stringWriter);
            this.throwable.printStackTrace(out);
            out.flush();
            OutTag.print(this.pageContext, stringWriter.toString(), this.escapeXml);
        }
        return Tag.SKIP_BODY;
    }

    /**
     * @param object the throwable to set
     */
    public void setThrowable(Object object) {
        if(object instanceof Throwable) {
            this.throwable = (Throwable)(object);
        }
    }

    /**
     * @return the throwable
     */
    public Throwable getThrowable() {
        return this.throwable;
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
}
