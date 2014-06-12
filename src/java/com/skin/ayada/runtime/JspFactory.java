/*
 * $RCSfile: JspFactory.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-2-19 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.runtime;

import java.io.Writer;

import com.skin.ayada.jstl.TagLibrary;
import com.skin.ayada.jstl.TagLibraryFactory;
import com.skin.ayada.template.TemplateContext;

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
     * @param out
     * @return PageContext
     */
    public static PageContext getPageContext(Writer out)
    {
        return getPageContext(null, out, 8192, false);
    }

    /**
     * @param out
     * @return PageContext
     */
    public static PageContext getPageContext(TemplateContext templateContext, Writer out)
    {
        return getPageContext(templateContext, out, 8192, false);
    }

    /**
     * @param out
     * @return PageContext
     */
    public static PageContext getPageContext(TemplateContext templateContext, Writer out, int buffserSize, boolean autoFlush)
    {
        JspWriter jspWriter = new JspWriter(out, buffserSize, autoFlush);
        DefaultPageContext pageContext = new DefaultPageContext(jspWriter);
        ExpressionContext expressionContext = ExpressionFactory.getExpressionContext(pageContext);
        TagLibrary tagLibrary = TagLibraryFactory.getStandardTagLibrary();
        pageContext.setTagLibrary(tagLibrary);
        pageContext.setTemplateContext(templateContext);
        pageContext.setExpressionContext(expressionContext);
        return pageContext;
    }
}
