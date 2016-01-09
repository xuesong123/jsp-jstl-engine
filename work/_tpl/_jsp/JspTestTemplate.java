/*
 * $RCSfile: JspTestTemplate.java,v $$
 * $Revision: 1.1 $
 * $Date: 2016-01-09 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 *
 * home: E:/WorkSpace/ayada/webapp
 * path: /jspTest.jsp
 * lastModified: 2016-01-08 13:21:58 000
 * options: -fastJstl true
 * JSP generated by JspCompiler-1.0.1.2 (built 2016-01-09 14:41:27 059)
 */
package _tpl._jsp;

import java.io.Writer;
import com.skin.ayada.runtime.ExpressionContext;
import com.skin.ayada.runtime.JspWriter;
import com.skin.ayada.runtime.PageContext;
import com.skin.ayada.tagext.BodyContent;
import com.skin.ayada.tagext.BodyTag;
import com.skin.ayada.tagext.IterationTag;
import com.skin.ayada.tagext.JspFragment;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.template.JspTemplate;
import com.skin.ayada.util.ExpressionUtil;


/**
 * <p>Title: JspTestTemplate</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author JspCompiler
 * @version 1.0
 */
@SuppressWarnings("unused")
public class JspTestTemplate extends JspTemplate {
    public static void main(String[] args){
        java.io.StringWriter writer = new java.io.StringWriter();
        PageContext pageContext = com.skin.ayada.runtime.JspFactory.getPageContext(writer);
        JspTestTemplate template = new JspTestTemplate();

        try {
            template.execute(pageContext);
            System.out.println(writer.toString());
        }
        catch(Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * @param pageContext
     * @throws Throwable
     */
    @Override
    @SuppressWarnings("unchecked")
    public void _execute(final PageContext pageContext) throws Throwable {
        JspWriter out = pageContext.getOut();
        JspWriter jspWriter = pageContext.getOut();
        ExpressionContext expressionContext = pageContext.getExpressionContext();
        // jsp:directive.page: lineNumber: 1
        // <jsp:directive.page contentType="text/html;charset=UTF-8"/>

        // jsp:directive.taglib: lineNumber: 2
        // <jsp:directive.taglib prefix="c" taglib="" uri="http://java.sun.com/jsp/jstl/core"/>

        // jsp:directive.taglib: lineNumber: 3
        // <jsp:directive.taglib prefix="fmt" taglib="" uri="http://java.sun.com/jsp/jstl/fmt"/>

        // TEXT: lineNumber: 4
        // out.write("<h1>Hello World !</h1>");
        out.write(_jsp_string_7, 0, _jsp_string_7.length);

        out.flush();
        jspWriter.flush();
    }

    public static final char[] _jsp_string_7 = "<h1>Hello World !</h1>".toCharArray();

}
