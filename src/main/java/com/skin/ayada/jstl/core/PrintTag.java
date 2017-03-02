/*
 * $RCSfile: PrintTag.java,v $
 * $Revision: 1.1 $
 * $Date: 2011-12-09 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import java.io.PrintWriter;
import java.util.Iterator;

import com.skin.ayada.PageContext;
import com.skin.ayada.jstl.util.BeanUtil;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TagSupport;

/**
 * <p>Title: PrintTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class PrintTag extends TagSupport {
    private Object out;
    private Object value;
    private static final BeanUtil beanUtil = new BeanUtil();

    /**
     * @return int
     * @throws Exception
     */
    @Override
    public int doEndTag() throws Exception {
        print(this.pageContext, this.out, this.value);
        return Tag.EVAL_PAGE;
    }

    /**
     * @param pageContext
     * @param out
     * @param value
     */
    public static void print(PageContext pageContext, Object out, Object value) {
        PrintWriter printWriter = TagSupport.getPrintWriter(out);

        if(printWriter == null) {
            printWriter = new PrintWriter(pageContext.getOut());
        }

        if(value instanceof PageContext) {
            String name = null;
            Object bean = null;
            PageContext pc = ((PageContext)value);
            Iterator<String> iterator = pc.getAttributeNames();

            while(iterator.hasNext()) {
                name = iterator.next();
                bean = pc.getAttribute(name);

                printWriter.write((name != null ? name : "null"));
                printWriter.write(": ");
                printWriter.write((bean != null ? bean.toString() : "null"));
                printWriter.write("\r\n");
            }
        }
        else {
            printWriter.write(beanUtil.toString(value));
            printWriter.write("\r\n");
        }
        printWriter.flush();
    }

    /**
     * @return the out
     */
    public Object getOut() {
        return this.out;
    }

    /**
     * @param out the out to set
     */
    public void setOut(Object out) {
        this.out = out;
    }

    /**
     * @return the value
     */
    public Object getValue() {
        return this.value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Object value) {
        this.value = value;
    }
}