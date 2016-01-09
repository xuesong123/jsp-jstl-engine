/*
 * $RCSfile: EmptyTestTemplate.java,v $$
 * $Revision: 1.1 $
 * $Date: 2016-01-09 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 *
 * home: E:/WorkSpace/ayada/webapp
 * path: /emptyTest.jsp
 * lastModified: 2013-10-30 15:57:50 000
 * options: -fastJstl true
 * JSP generated by JspCompiler-1.0.1.2 (built 2016-01-09 14:41:24 778)
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
 * <p>Title: EmptyTestTemplate</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author JspCompiler
 * @version 1.0
 */
@SuppressWarnings("unused")
public class EmptyTestTemplate extends JspTemplate {
    public static void main(String[] args){
        java.io.StringWriter writer = new java.io.StringWriter();
        PageContext pageContext = com.skin.ayada.runtime.JspFactory.getPageContext(writer);
        EmptyTestTemplate template = new EmptyTestTemplate();

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
        // TEXT: lineNumber: 1
        // out.write("<html>\r\n<head>\r\n<title>test</title>\r\n</head>\r\n<body version=\"1.0\">\r\n");
        out.write(_jsp_string_1, 0, _jsp_string_1.length);
        // EXPRESSION: lineNumber: 6
        expressionContext.print(out, expressionContext.getString("testUser.userName"));
        // TEXT: lineNumber: 6
        // out.write("\r\n");
        out.write(_jsp_string_3, 0, _jsp_string_3.length);

        // NODE START: lineNumber: 7, offset: 3, length: 2, tagClassName: com.skin.ayada.jstl.core.IfTag, tagInstanceName: _jsp_IfTag_4
        // <c:if test=\"${&#39;test&#39; == encoding}\">...</c:if>
        if(ExpressionUtil.getBoolean(expressionContext, "${\'test\' == encoding}")) {
        } // jsp.jstl.core.IfTag END
        // NODE END: lineNumber: 7, tagClassName: com.skin.ayada.jstl.core.IfTag, tagInstanceName: _jsp_IfTag_4

        // TEXT: lineNumber: 7
        // out.write("\r\n</body>\r\n</html>");
        out.write(_jsp_string_6, 0, _jsp_string_6.length);

        out.flush();
        jspWriter.flush();
    }

    public static final char[] _jsp_string_1 = "<html>\r\n<head>\r\n<title>test</title>\r\n</head>\r\n<body version=\"1.0\">\r\n".toCharArray();
    public static final char[] _jsp_string_3 = "\r\n".toCharArray();
    public static final char[] _jsp_string_6 = "\r\n</body>\r\n</html>".toCharArray();

}
