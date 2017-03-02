/*
 * $RCSfile: JspFactory.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-02-19 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.runtime;

import java.io.Writer;
import java.util.Map;

import com.skin.ayada.ExpressionContext;
import com.skin.ayada.JspWriter;
import com.skin.ayada.PageContext;

/**
 * <p>Title: JspFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class JspFactory {
    /**
     * @param writer
     * @return PageContext
     */
    public static PageContext getPageContext(Writer writer) {
        return getPageContext((Map<String, Object>)null, writer, 8192, false);
    }

    /**
     * @param context
     * @param writer
     * @return PageContext
     */
    public static PageContext getPageContext(Map<String, Object> context, Writer writer) {
        return getPageContext(context, writer, 8192, false);
    }

    /**
     * @param context
     * @param writer
     * @param buffserSize
     * @param autoFlush
     * @return PageContext
     */
    public static PageContext getPageContext(Map<String, Object> context, Writer writer, int buffserSize, boolean autoFlush) {
        JspWriter out = null;

        if(writer instanceof JspWriter) {
            out = (JspWriter)writer;
        }
        else {
            out = new JspWriter(writer, buffserSize, autoFlush);
        }

        DefaultPageContext pageContext = new DefaultPageContext(out);
        ExpressionContext expressionContext = DefaultExpressionFactory.getDefaultExpressionContext(pageContext);
        pageContext.setTemplateContext(null);
        pageContext.setExpressionContext(expressionContext);
        pageContext.setContext(context);
        return pageContext;
    }
}
