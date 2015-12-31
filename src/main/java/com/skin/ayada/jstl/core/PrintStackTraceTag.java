/*
 * Copyright 2014 Aliyun.com All right reserved. This software is the
 * confidential and proprietary information of Aliyun.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Aliyun.com .
 */
package com.skin.ayada.jstl.core;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TagSupport;

/**
 * <p>Title: PrintStackTraceTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * @author weixian
 * @version 1.0
 */
public class PrintStackTraceTag extends TagSupport {
    private Throwable throwable;
    private boolean escapeXml = false;

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
