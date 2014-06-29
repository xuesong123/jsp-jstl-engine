/*
 * $RCSfile: JspFactory.java,v $$
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

/**
 * <p>Title: JspFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class JspFactory
{
    /**
     * @param writer
     * @return PageContext
     */
    public static PageContext getDefaultPageContext(Writer writer)
    {
        return getDefaultPageContext(writer, 8192, false);
    }

    /**
     * @param writer
     * @param buffserSize
     * @param autoFlush
     * @return PageContext
     */
    public static PageContext getDefaultPageContext(Writer writer, int buffserSize, boolean autoFlush)
    {
        JspWriter out = new JspWriter(writer, buffserSize, autoFlush);
        DefaultPageContext pageContext = new DefaultPageContext(out);
        ExpressionContext expressionContext = DefaultExpressionFactory.getDefaultExpressionContext(pageContext);
        pageContext.setTemplateContext(null);
        pageContext.setExpressionContext(expressionContext);
        return pageContext;
    }
}
