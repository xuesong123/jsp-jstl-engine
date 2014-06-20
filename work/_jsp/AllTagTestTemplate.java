/*
 * $RCSfile: AllTagTestTemplate.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-28 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 *
 * Home: D:\workspace2\ayada\webapp
 * Path: allTagTest.jsp
 * LastModified: 2013-11-28 15:43:59 613
 * JSP generated by JspCompiler-1.0.0.0 (built 2014-06-12 15:02:18 972)
 */
package _jsp;

import com.skin.ayada.runtime.ExpressionContext;
import com.skin.ayada.runtime.JspWriter;
import com.skin.ayada.runtime.PageContext;
import com.skin.ayada.tagext.BodyContent;
import com.skin.ayada.tagext.BodyTag;
import com.skin.ayada.tagext.IterationTag;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.template.JspTemplate;
import com.skin.ayada.util.ExpressionUtil;
import java.io.IOException; /* jsp:directive.import: lineNumber: 4 */

/**
 * <p>Title: AllTagTestTemplate</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author JspCompiler
 * @version 1.0
 */
public class AllTagTestTemplate extends JspTemplate
{
    public static void main(String[] args)
    {
        java.io.StringWriter writer = new java.io.StringWriter();
        PageContext pageContext = com.skin.ayada.runtime.JspFactory.getDefaultPageContext(writer);
        AllTagTestTemplate template = new AllTagTestTemplate();

        try{
            template._execute(pageContext);
            System.out.println(writer.toString());
        }
        catch(Throwable throwable)
        {
            throwable.printStackTrace();
        }
     }

    /* JSP_DECLARATION: lineNumber: 9 */
    public void hello1(JspWriter out) throws IOException{
        out.println("Hello, World !");
    }
    /* jsp:declaration END */

    /* JSP_DECLARATION: lineNumber: 14 */
    public void hello2(JspWriter out) throws IOException{
        out.println("Hello, World !");
    }
    /* jsp:declaration END */

    /* JSP_DECLARATION: lineNumber: 20 */
    public void hello3(JspWriter out) throws IOException{
        out.println("Hello, World !");
    }
    /* jsp:declaration END */

    /* JSP_DECLARATION: lineNumber: 26 */
    public void hello4(JspWriter out) throws IOException{
        out.println("Hello, World !");
    }
    /* jsp:declaration END */

    /* JSP_DECLARATION: lineNumber: 46 */
    public int myInt = 0;
    /* jsp:declaration END */

    /**
     * @param pageContext
     * @throws Throwable
     */
    @Override
    public void _execute(final PageContext pageContext) throws Throwable
    {
        JspWriter out = pageContext.getOut();
        JspWriter jspWriter = pageContext.getOut();
        ExpressionContext expressionContext = pageContext.getExpressionContext();

        /* jsp:directive.page: lineNumber: 1 */
        /* <jsp:directive.page contentType="text/html;charset=UTF-8"/> */
        /* jsp:directive.page END */

        /* jsp:directive.taglib: lineNumber: 2 */
        /* <jsp:directive.taglib taglib="" prefix="c" uri="http://java.sun.com/jsp/jstl/core"/> */
        /* jsp:directive.taglib END */

        /* jsp:directive.taglib: lineNumber: 3 */
        /* <jsp:directive.taglib taglib="" prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"/> */
        /* jsp:directive.taglib END */

        /* jsp:directive.page: lineNumber: 4 */
        /* <jsp:directive.page import="java.io.IOException"/> */
        /* jsp:directive.page END */

        /* NODE START: lineNumber: 5, offset: 8, length: 2, tagClassName: com.skin.ayada.jstl.core.ImportTag, tagInstanceName: _jsp_ImportTag_9 */
        /* <t:import name="app:scrollpage" className="test.com.skin.ayada.taglib.ScrollPage"/> */
        /* pageContext.getTagLibrary().setup("app:scrollpage", "test.com.skin.ayada.taglib.ScrollPage"); */
        /* jsp.jstl.core.ImportTag END */
        /* NODE END: lineNumber: 5, tagClassName: com.skin.ayada.jstl.core.ImportTag, tagInstanceName: _jsp_ImportTag_9 */

        /* NODE START: lineNumber: 6, offset: 10, length: 2, tagClassName: com.skin.ayada.jstl.core.ImportTag, tagInstanceName: _jsp_ImportTag_11 */
        /* <t:import name="app:test" className="com.skin.ayada.jstl.core.TestTag"/> */
        /* pageContext.getTagLibrary().setup("app:test", "com.skin.ayada.jstl.core.TestTag"); */
        /* jsp.jstl.core.ImportTag END */
        /* NODE END: lineNumber: 6, tagClassName: com.skin.ayada.jstl.core.ImportTag, tagInstanceName: _jsp_ImportTag_11 */

        /* NODE START: lineNumber: 7, offset: 12, length: 2, tagClassName: com.skin.ayada.jstl.core.ImportTag, tagInstanceName: _jsp_ImportTag_13 */
        /* <t:import name="app:bodytest" className="test.com.skin.ayada.taglib.TestBodyTag"/> */
        /* pageContext.getTagLibrary().setup("app:bodytest", "test.com.skin.ayada.taglib.TestBodyTag"); */
        /* jsp.jstl.core.ImportTag END */
        /* NODE END: lineNumber: 7, tagClassName: com.skin.ayada.jstl.core.ImportTag, tagInstanceName: _jsp_ImportTag_13 */

        /* jsp:declaration: lineNumber: 9 */
        /* <jsp:declaration>...</jsp:declaration> */
        /* jsp:declaration END */

        /* jsp:declaration: lineNumber: 14 */
        /* <jsp:declaration>...</jsp:declaration> */
        /* jsp:declaration END */

        /* jsp:declaration: lineNumber: 20 */
        /* <jsp:declaration>...</jsp:declaration> */
        /* jsp:declaration END */

        /* jsp:declaration: lineNumber: 26 */
        /* <jsp:declaration>...</jsp:declaration> */
        /* jsp:declaration END */

        /* TEXT: lineNumber: 31 */
        out.write("<html>\r\n<head>\r\n<title>test</title>\r\n</head>\r\n<body jsp=\"");

        /* jsp:expression: lineNumber: 35 */
        out.print(1 + 2);
        /* jsp:expression END */

        /* TEXT: lineNumber: 35 */
        out.write("\" version=\"1.0\">\r\n============================================\r\n");

        /* jsp:scriptlet: lineNumber: 38 */
    out.println("Hello, World !");

        /* jsp:scriptlet END */

        /* TEXT: lineNumber: 41 */
        out.write("============================================\r\n");

        /* jsp:scriptlet: lineNumber: 42 */
    out.println("Hello, World !");

        /* jsp:scriptlet END */

        /* jsp:declaration: lineNumber: 46 */
        /* <jsp:declaration>...</jsp:declaration> */
        /* jsp:declaration END */

        /* jsp:scriptlet: lineNumber: 50 */
    // out.println("Hello, World1 !");

        /* jsp:scriptlet END */

        /* jsp:scriptlet: lineNumber: 54 */
    // out.println("Hello, World2 ! </test");
    // out.println("Hello, World2 ! </jsp:scriptlet2>");

        /* jsp:scriptlet END */

        /* jsp:scriptlet: lineNumber: 60 */
    int mytest = 0;

        /* jsp:scriptlet END */

        /* TEXT: lineNumber: 63 */
        out.write("<p>\r\n    myInt: ");

        /* jsp:expression: lineNumber: 64 */
        out.print( this.myInt );
        /* jsp:expression END */

        /* TEXT: lineNumber: 64 */
        out.write("\r\n</p>\r\n\r\n<p>\r\n    mytest: ");

        /* jsp:expression: lineNumber: 68 */
        out.print(mytest);
        /* jsp:expression END */

        /* TEXT: lineNumber: 68 */
        out.write("\r\n</p>\r\n");

        /* jsp:scriptlet: lineNumber: 70 */
    out.println("Hello, World4 !");

        /* jsp:scriptlet END */

        /* jsp:expression: lineNumber: 74 */
        out.print(("Hello" + " " + "World !"));
        /* jsp:expression END */

        /* TEXT: lineNumber: 74 */
        out.write("\r\n============================================\r\n");

        /* NODE START: lineNumber: 76, offset: 51, length: 2, tagClassName: com.skin.ayada.jstl.core.SetTag, tagInstanceName: _jsp_SetTag_52 */
        /* <c:set var="myString" value="${StringUtil.replace(&#39;abc&#39;, &#39;b&#39;, &#39;\n&#39;)}"/> */
        pageContext.setAttribute("myString", ExpressionUtil.evaluate(expressionContext, "${StringUtil.replace(\'abc\', \'b\', \'\\n\')}", null));
        /* jsp.jstl.core.SetTag END */
        /* NODE END: lineNumber: 76, tagClassName: com.skin.ayada.jstl.core.SetTag, tagInstanceName: _jsp_SetTag_52 */

        /* TEXT: lineNumber: 77 */
        out.write("<p>myString: [");

        /* NODE START: lineNumber: 77, offset: 54, length: 2, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_55 */
        /* <c:out value="${myString}"/> */
        /* out.write("${myString}"); */
        out.write(ExpressionUtil.getString(expressionContext, "${myString}"));
        /* jsp.jstl.core.OutTag END */
        /* NODE END: lineNumber: 77, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_55 */

        /* TEXT: lineNumber: 77 */
        out.write("]</p>\r\n\r\n");

        /* NODE START: lineNumber: 79, offset: 57, length: 2, tagClassName: com.skin.ayada.jstl.core.SetTag, tagInstanceName: _jsp_SetTag_58 */
        /* <c:set var="myString" value="&quot;a + 1&quot;"/> */
        pageContext.setAttribute("myString", "\"a + 1\"");
        /* jsp.jstl.core.SetTag END */
        /* NODE END: lineNumber: 79, tagClassName: com.skin.ayada.jstl.core.SetTag, tagInstanceName: _jsp_SetTag_58 */

        /* TEXT: lineNumber: 80 */
        out.write("<p>myString: [");

        /* NODE START: lineNumber: 80, offset: 60, length: 2, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_61 */
        /* <c:out value="${myString}"/> */
        /* out.write("${myString}"); */
        out.write(ExpressionUtil.getString(expressionContext, "${myString}"));
        /* jsp.jstl.core.OutTag END */
        /* NODE END: lineNumber: 80, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_61 */

        /* TEXT: lineNumber: 80 */
        out.write("]</p>\r\n\r\n");

        /* NODE START: lineNumber: 82, offset: 63, length: 2, tagClassName: com.skin.ayada.jstl.core.SetTag, tagInstanceName: _jsp_SetTag_64 */
        /* <c:set var="myVar" value="Hello, World!"/> */
        pageContext.setAttribute("myVar", "Hello, World!");
        /* jsp.jstl.core.SetTag END */
        /* NODE END: lineNumber: 82, tagClassName: com.skin.ayada.jstl.core.SetTag, tagInstanceName: _jsp_SetTag_64 */

        /* TEXT: lineNumber: 83 */
        out.write("<h1>############## ");
        /* VARIABLE: lineNumber: 83 */
        out.print(pageContext.getAttribute("myVar"), false);
        /* TEXT: lineNumber: 83 */
        out.write(" ##############</h1>\r\n\r\n");

        /* NODE START: lineNumber: 85, offset: 68, length: 2, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_69 */
        /* <c:out value="c:out: Hello, World!"/> */
        /* out.write("c:out: Hello, World!"); */
        out.write("c:out: Hello, World!");
        /* jsp.jstl.core.OutTag END */
        /* NODE END: lineNumber: 85, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_69 */

        /* TEXT: lineNumber: 85 */
        out.write("\r\n<div style=\"background-color: #c0c0c0;\"></div>\r\n");

        /* NODE START: lineNumber: 87, offset: 71, length: 2, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_72 */
        /* <c:out value="&quot;&lt;div&gt;Hello World!&lt;/div&gt;&quot;" escapeXml="false"/> */
        /* out.write("\"<div>Hello World!</div>\""); */
        out.write("\"<div>Hello World!</div>\"");
        /* jsp.jstl.core.OutTag END */
        /* NODE END: lineNumber: 87, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_72 */

        /* TEXT: lineNumber: 87 */
        out.write("\r\n");

        /* NODE START: lineNumber: 88, offset: 74, length: 3, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_75 */
        /* <c:out escapeXml="true">...</c:out> */
        out = pageContext.pushBody();
        /* TEXT: lineNumber: 88 */
        out.write("<h1>Hello World!</h1>");
        pageContext.print((BodyContent)out, true);
        out = pageContext.popBody();
        /* jsp.jstl.core.OutTag END */
        /* NODE END: lineNumber: 88, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_75 */

        /* TEXT: lineNumber: 88 */
        out.write("\r\n");

        /* NODE START: lineNumber: 89, offset: 78, length: 3, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_79 */
        /* <c:out value="&lt;div&gt;Hello World!&lt;/div&gt;" escapeXml="true">...</c:out> */
        /* out.write("<div>Hello World!</div>"); */
        out.write("&lt;div&gt;Hello World!&lt;/div&gt;");
        /* jsp.jstl.core.OutTag END */
        /* NODE END: lineNumber: 89, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_79 */

        /* TEXT: lineNumber: 89 */
        out.write("\r\n\r\n");

        /* NODE START: lineNumber: 91, offset: 82, length: 2, tagClassName: com.skin.ayada.jstl.core.SetTag, tagInstanceName: _jsp_SetTag_83 */
        /* <c:set var="myName" value="xuesong.net"/> */
        pageContext.setAttribute("myName", "xuesong.net");
        /* jsp.jstl.core.SetTag END */
        /* NODE END: lineNumber: 91, tagClassName: com.skin.ayada.jstl.core.SetTag, tagInstanceName: _jsp_SetTag_83 */

        /* NODE START: lineNumber: 92, offset: 84, length: 2, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_85 */
        /* <c:out value="Hello, ${myName}!" escapeXml="true"/> */
        /* out.write("Hello, ${myName}!"); */
        out.write(ExpressionUtil.getHtml(expressionContext, "Hello, ${myName}!"));
        /* jsp.jstl.core.OutTag END */
        /* NODE END: lineNumber: 92, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_85 */

        /* TEXT: lineNumber: 92 */
        out.write("\r\n");

        /* NODE START: lineNumber: 93, offset: 87, length: 2, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_88 */
        /* <c:out value="Hello, ${myName}!" escapeXml="false"/> */
        /* out.write("Hello, ${myName}!"); */
        out.write(ExpressionUtil.getString(expressionContext, "Hello, ${myName}!"));
        /* jsp.jstl.core.OutTag END */
        /* NODE END: lineNumber: 93, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_88 */

        /* TEXT: lineNumber: 93 */
        out.write("\r\n\r\n");

        /* NODE START: lineNumber: 95, offset: 90, length: 3, tagClassName: com.skin.ayada.jstl.core.IfTag, tagInstanceName: _jsp_IfTag_91 */
        /* <c:if test="${1 == 1}">...</c:if> */
        if(ExpressionUtil.getBoolean(expressionContext, "${1 == 1}")){
            /* TEXT: lineNumber: 95 */
            out.write("c:if test");
        } /* jsp.jstl.core.IfTag END */
        /* NODE END: lineNumber: 95, tagClassName: com.skin.ayada.jstl.core.IfTag, tagInstanceName: _jsp_IfTag_91 */

        /* TEXT: lineNumber: 95 */
        out.write("\r\n\r\n<h1>c:forEach test1</h1>\r\n");

        /* NODE START: lineNumber: 98, offset: 94, length: 3, tagClassName: com.skin.ayada.jstl.core.ForEachTag, tagInstanceName: _jsp_ForEachTag_95 */
        /* <c:each items="1,2,3,4,5" var="mynum">...</c:each> */
        Object _jsp_old_var_95 = pageContext.getAttribute("mynum");
        com.skin.ayada.jstl.core.ForEachTag _jsp_ForEachTag_95 = new com.skin.ayada.jstl.core.ForEachTag();
        _jsp_ForEachTag_95.setParent((Tag)null);
        _jsp_ForEachTag_95.setPageContext(pageContext);
        _jsp_ForEachTag_95.setVar("mynum");
        _jsp_ForEachTag_95.setItems("1,2,3,4,5");
        if(_jsp_ForEachTag_95.doStartTag() != Tag.SKIP_BODY){
            while(true){
                /* VARIABLE: lineNumber: 100 */
                out.print(pageContext.getAttribute("mynum"), false);
                if(_jsp_ForEachTag_95.doAfterBody() != IterationTag.EVAL_BODY_AGAIN){
                    break;
                }
            }
        }
        _jsp_ForEachTag_95.release();
        pageContext.setAttribute("mynum", _jsp_old_var_95);
        /* jsp.jstl.core.ForEachTag END */
        /* NODE END: lineNumber: 98, tagClassName: com.skin.ayada.jstl.core.ForEachTag, tagInstanceName: _jsp_ForEachTag_95 */

        /* TEXT: lineNumber: 100 */
        out.write("\r\n\r\n<h1>c:forEach test2</h1>\r\n");

        /* NODE START: lineNumber: 103, offset: 98, length: 20, tagClassName: com.skin.ayada.jstl.core.ForEachTag, tagInstanceName: _jsp_ForEachTag_99 */
        /* <c:forEach items="${userList}" var="user" varStatus="status">...</c:forEach> */
        Object _jsp_old_var_99 = pageContext.getAttribute("user");
        Object _jsp_old_var_status_99 = pageContext.getAttribute("status");
        com.skin.ayada.jstl.core.ForEachTag _jsp_ForEachTag_99 = new com.skin.ayada.jstl.core.ForEachTag();
        _jsp_ForEachTag_99.setParent((Tag)null);
        _jsp_ForEachTag_99.setPageContext(pageContext);
        _jsp_ForEachTag_99.setVar("user");
        _jsp_ForEachTag_99.setItems(ExpressionUtil.evaluate(expressionContext, "${userList}", null));
        if(_jsp_ForEachTag_99.doStartTag() != Tag.SKIP_BODY){
            while(true){
                /* TEXT: lineNumber: 103 */
                out.write("\r\n    <p>user: ");
                /* EXPRESSION: lineNumber: 104 */
                out.write(expressionContext.getString("user.userName"));
                /* TEXT: lineNumber: 104 */
                out.write("</p>\r\n    ");

                /* NODE START: lineNumber: 105, offset: 102, length: 14, tagClassName: com.skin.ayada.jstl.core.ChooseTag, tagInstanceName: _jsp_ChooseTag_103 */
                /* <c:choose>...</c:choose> */
                boolean _jsp_ChooseTag_103 = true;

                /* NODE START: lineNumber: 106, offset: 103, length: 3, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _jsp_WhenTag_104 */
                /* <c:when test="${user.userName == &#39;test1&#39;}">...</c:when> */
                if(_jsp_ChooseTag_103 && ExpressionUtil.getBoolean(expressionContext, "${user.userName == \'test1\'}")){
                    _jsp_ChooseTag_103 = false;
                    /* TEXT: lineNumber: 106 */
                    out.write("<p>test1, good man !</p>");
                } /* jsp.jstl.core.WhenTag END */
                /* NODE END: lineNumber: 106, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _jsp_WhenTag_104 */

                /* NODE START: lineNumber: 107, offset: 106, length: 3, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _jsp_WhenTag_107 */
                /* <c:when test="${user.userName == &#39;test2&#39;}">...</c:when> */
                if(_jsp_ChooseTag_103 && ExpressionUtil.getBoolean(expressionContext, "${user.userName == \'test2\'}")){
                    _jsp_ChooseTag_103 = false;
                    /* TEXT: lineNumber: 107 */
                    out.write("<p>test2, good man !</p>");
                } /* jsp.jstl.core.WhenTag END */
                /* NODE END: lineNumber: 107, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _jsp_WhenTag_107 */

                /* NODE START: lineNumber: 108, offset: 109, length: 3, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _jsp_WhenTag_110 */
                /* <c:when test="${user.userName == &#39;test3&#39;}">...</c:when> */
                if(_jsp_ChooseTag_103 && ExpressionUtil.getBoolean(expressionContext, "${user.userName == \'test3\'}")){
                    _jsp_ChooseTag_103 = false;
                    /* TEXT: lineNumber: 108 */
                    out.write("<p>test3, good man !</p>");
                } /* jsp.jstl.core.WhenTag END */
                /* NODE END: lineNumber: 108, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _jsp_WhenTag_110 */

                /* NODE START: lineNumber: 109, offset: 112, length: 3, tagClassName: com.skin.ayada.jstl.core.OtherwiseTag, tagInstanceName: _jsp_OtherwiseTag_113 */
                /* <c:otherwise>...</c:otherwise> */
                if(_jsp_ChooseTag_103){
                    _jsp_ChooseTag_103 = false;
                    /* TEXT: lineNumber: 109 */
                    out.write("<p>unknown user! Do you known \'bad egg\'? You! Are!</p>");
                } /* jsp.jstl.core.OtherwiseTag END */
                /* NODE END: lineNumber: 109, tagClassName: com.skin.ayada.jstl.core.OtherwiseTag, tagInstanceName: _jsp_OtherwiseTag_113 */

                /* jsp.jstl.core.ChooseTag END */
                /* NODE END: lineNumber: 105, tagClassName: com.skin.ayada.jstl.core.ChooseTag, tagInstanceName: _jsp_ChooseTag_103 */

                /* TEXT: lineNumber: 110 */
                out.write("\r\n");
                if(_jsp_ForEachTag_99.doAfterBody() != IterationTag.EVAL_BODY_AGAIN){
                    break;
                }
            }
        }
        _jsp_ForEachTag_99.release();
        pageContext.setAttribute("user", _jsp_old_var_99);
        pageContext.setAttribute("status", _jsp_old_var_status_99);
        /* jsp.jstl.core.ForEachTag END */
        /* NODE END: lineNumber: 103, tagClassName: com.skin.ayada.jstl.core.ForEachTag, tagInstanceName: _jsp_ForEachTag_99 */

        /* TEXT: lineNumber: 111 */
        out.write("\r\n\r\n<h1>c:choose test1</h1>\r\n");

        /* NODE START: lineNumber: 114, offset: 119, length: 14, tagClassName: com.skin.ayada.jstl.core.ChooseTag, tagInstanceName: _jsp_ChooseTag_120 */
        /* <c:choose>...</c:choose> */
        boolean _jsp_ChooseTag_120 = true;

        /* NODE START: lineNumber: 115, offset: 120, length: 3, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _jsp_WhenTag_121 */
        /* <c:when test="${1 == 1}">...</c:when> */
        if(_jsp_ChooseTag_120 && ExpressionUtil.getBoolean(expressionContext, "${1 == 1}")){
            _jsp_ChooseTag_120 = false;
            /* TEXT: lineNumber: 115 */
            out.write("c:when test=\"{1 == 1}\"");
        } /* jsp.jstl.core.WhenTag END */
        /* NODE END: lineNumber: 115, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _jsp_WhenTag_121 */

        /* NODE START: lineNumber: 116, offset: 123, length: 3, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _jsp_WhenTag_124 */
        /* <c:when test="${2 == 2}">...</c:when> */
        if(_jsp_ChooseTag_120 && ExpressionUtil.getBoolean(expressionContext, "${2 == 2}")){
            _jsp_ChooseTag_120 = false;
            /* TEXT: lineNumber: 116 */
            out.write("c:when test=\"{2 == 2}\"");
        } /* jsp.jstl.core.WhenTag END */
        /* NODE END: lineNumber: 116, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _jsp_WhenTag_124 */

        /* NODE START: lineNumber: 117, offset: 126, length: 3, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _jsp_WhenTag_127 */
        /* <c:when test="${3 == 3}">...</c:when> */
        if(_jsp_ChooseTag_120 && ExpressionUtil.getBoolean(expressionContext, "${3 == 3}")){
            _jsp_ChooseTag_120 = false;
            /* TEXT: lineNumber: 117 */
            out.write("c:when test=\"{3 == 3}\"");
        } /* jsp.jstl.core.WhenTag END */
        /* NODE END: lineNumber: 117, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _jsp_WhenTag_127 */

        /* NODE START: lineNumber: 118, offset: 129, length: 3, tagClassName: com.skin.ayada.jstl.core.OtherwiseTag, tagInstanceName: _jsp_OtherwiseTag_130 */
        /* <c:otherwise>...</c:otherwise> */
        if(_jsp_ChooseTag_120){
            _jsp_ChooseTag_120 = false;
            /* TEXT: lineNumber: 118 */
            out.write("c:otherwise");
        } /* jsp.jstl.core.OtherwiseTag END */
        /* NODE END: lineNumber: 118, tagClassName: com.skin.ayada.jstl.core.OtherwiseTag, tagInstanceName: _jsp_OtherwiseTag_130 */

        /* jsp.jstl.core.ChooseTag END */
        /* NODE END: lineNumber: 114, tagClassName: com.skin.ayada.jstl.core.ChooseTag, tagInstanceName: _jsp_ChooseTag_120 */

        /* TEXT: lineNumber: 119 */
        out.write("\r\n\r\n<h1>app:test test1</h1>\r\n");

        /* NODE START: lineNumber: 122, offset: 134, length: 2, tagClassName: com.skin.ayada.jstl.core.TestTag, tagInstanceName: _jsp_TestTag_135 */
        /* <app:test myBoolean="true" myChar="c" myByte="1" myInt="-1.0" myFloat="1.0f" myDouble="1.0d" myLong="1L" myString="Hello"/> */
        com.skin.ayada.jstl.core.TestTag _jsp_TestTag_135 = new com.skin.ayada.jstl.core.TestTag();
        _jsp_TestTag_135.setParent((Tag)null);
        _jsp_TestTag_135.setPageContext(pageContext);
        _jsp_TestTag_135.setMyBoolean(true);
        _jsp_TestTag_135.setMyChar('c');
        _jsp_TestTag_135.setMyByte((byte)1);
        _jsp_TestTag_135.setMyInt(-1);
        _jsp_TestTag_135.setMyFloat(1.0f);
        _jsp_TestTag_135.setMyDouble(1.0d);
        _jsp_TestTag_135.setMyLong(1L);
        _jsp_TestTag_135.setMyString("Hello");
        int _jsp_start_flag_135 = _jsp_TestTag_135.doStartTag();

        if(_jsp_start_flag_135 == Tag.SKIP_PAGE){
            return;
        }
        if(_jsp_start_flag_135 != Tag.SKIP_BODY){
            _jsp_TestTag_135.doAfterBody();
        }
        _jsp_TestTag_135.doEndTag();
        _jsp_TestTag_135.release();
        /* NODE END: lineNumber: 122, tagClassName: com.skin.ayada.jstl.core.TestTag, tagInstanceName: _jsp_TestTag_135 */

        /* TEXT: lineNumber: 122 */
        out.write("\r\n");

        /* NODE START: lineNumber: 123, offset: 137, length: 2, tagClassName: com.skin.ayada.jstl.core.TestTag, tagInstanceName: _jsp_TestTag_138 */
        /* <app:test myBoolean="false" myChar="c" myByte="243" myInt="-1.0" myFloat="1.0F" myDouble="1.0D" myLong="1L" myString="Hello"/> */
        com.skin.ayada.jstl.core.TestTag _jsp_TestTag_138 = new com.skin.ayada.jstl.core.TestTag();
        _jsp_TestTag_138.setParent((Tag)null);
        _jsp_TestTag_138.setPageContext(pageContext);
        _jsp_TestTag_138.setMyBoolean(false);
        _jsp_TestTag_138.setMyChar('c');
        _jsp_TestTag_138.setMyByte((byte)243);
        _jsp_TestTag_138.setMyInt(-1);
        _jsp_TestTag_138.setMyFloat(1.0f);
        _jsp_TestTag_138.setMyDouble(1.0d);
        _jsp_TestTag_138.setMyLong(1L);
        _jsp_TestTag_138.setMyString("Hello");
        int _jsp_start_flag_138 = _jsp_TestTag_138.doStartTag();

        if(_jsp_start_flag_138 == Tag.SKIP_PAGE){
            return;
        }
        if(_jsp_start_flag_138 != Tag.SKIP_BODY){
            _jsp_TestTag_138.doAfterBody();
        }
        _jsp_TestTag_138.doEndTag();
        _jsp_TestTag_138.release();
        /* NODE END: lineNumber: 123, tagClassName: com.skin.ayada.jstl.core.TestTag, tagInstanceName: _jsp_TestTag_138 */

        /* TEXT: lineNumber: 123 */
        out.write("\r\n");

        /* NODE START: lineNumber: 124, offset: 140, length: 2, tagClassName: com.skin.ayada.jstl.core.TestTag, tagInstanceName: _jsp_TestTag_141 */
        /* <app:test myInt="-1.0" myFloat="1.0" myDouble="1e3" myLong="1e3" myString="Hello"/> */
        com.skin.ayada.jstl.core.TestTag _jsp_TestTag_141 = new com.skin.ayada.jstl.core.TestTag();
        _jsp_TestTag_141.setParent((Tag)null);
        _jsp_TestTag_141.setPageContext(pageContext);
        _jsp_TestTag_141.setMyInt(-1);
        _jsp_TestTag_141.setMyFloat(1.0f);
        _jsp_TestTag_141.setMyDouble(1000.0d);
        _jsp_TestTag_141.setMyLong(1000L);
        _jsp_TestTag_141.setMyString("Hello");
        int _jsp_start_flag_141 = _jsp_TestTag_141.doStartTag();

        if(_jsp_start_flag_141 == Tag.SKIP_PAGE){
            return;
        }
        if(_jsp_start_flag_141 != Tag.SKIP_BODY){
            _jsp_TestTag_141.doAfterBody();
        }
        _jsp_TestTag_141.doEndTag();
        _jsp_TestTag_141.release();
        /* NODE END: lineNumber: 124, tagClassName: com.skin.ayada.jstl.core.TestTag, tagInstanceName: _jsp_TestTag_141 */

        /* TEXT: lineNumber: 124 */
        out.write("\r\n");

        /* NODE START: lineNumber: 125, offset: 143, length: 2, tagClassName: com.skin.ayada.jstl.core.TestTag, tagInstanceName: _jsp_TestTag_144 */
        /* <app:test myInt="-1.0" myFloat="1.0" myDouble="1.2e3" myLong="1.2e3" myString="Hello"/> */
        com.skin.ayada.jstl.core.TestTag _jsp_TestTag_144 = new com.skin.ayada.jstl.core.TestTag();
        _jsp_TestTag_144.setParent((Tag)null);
        _jsp_TestTag_144.setPageContext(pageContext);
        _jsp_TestTag_144.setMyInt(-1);
        _jsp_TestTag_144.setMyFloat(1.0f);
        _jsp_TestTag_144.setMyDouble(1200.0d);
        _jsp_TestTag_144.setMyLong(1200L);
        _jsp_TestTag_144.setMyString("Hello");
        int _jsp_start_flag_144 = _jsp_TestTag_144.doStartTag();

        if(_jsp_start_flag_144 == Tag.SKIP_PAGE){
            return;
        }
        if(_jsp_start_flag_144 != Tag.SKIP_BODY){
            _jsp_TestTag_144.doAfterBody();
        }
        _jsp_TestTag_144.doEndTag();
        _jsp_TestTag_144.release();
        /* NODE END: lineNumber: 125, tagClassName: com.skin.ayada.jstl.core.TestTag, tagInstanceName: _jsp_TestTag_144 */

        /* TEXT: lineNumber: 125 */
        out.write("\r\n\r\n<h1>app:scrollpage test1</h1>\r\n");

        /* NODE START: lineNumber: 128, offset: 146, length: 2, tagClassName: test.com.skin.ayada.taglib.ScrollPage, tagInstanceName: _jsp_ScrollPage_147 */
        /* <app:scrollpage count="254" pageNum="2" pageSize="10"/> */
        test.com.skin.ayada.taglib.ScrollPage _jsp_ScrollPage_147 = new test.com.skin.ayada.taglib.ScrollPage();
        _jsp_ScrollPage_147.setParent((Tag)null);
        _jsp_ScrollPage_147.setPageContext(pageContext);
        _jsp_ScrollPage_147.setCount(254);
        _jsp_ScrollPage_147.setPageNum(2);
        _jsp_ScrollPage_147.setPageSize(10);
        int _jsp_start_flag_147 = _jsp_ScrollPage_147.doStartTag();

        if(_jsp_start_flag_147 == Tag.SKIP_PAGE){
            return;
        }
        if(_jsp_start_flag_147 != Tag.SKIP_BODY){
            _jsp_ScrollPage_147.doAfterBody();
        }
        _jsp_ScrollPage_147.doEndTag();
        _jsp_ScrollPage_147.release();
        /* NODE END: lineNumber: 128, tagClassName: test.com.skin.ayada.taglib.ScrollPage, tagInstanceName: _jsp_ScrollPage_147 */

        /* TEXT: lineNumber: 128 */
        out.write("\r\n\r\n");

        /* NODE START: lineNumber: 130, offset: 149, length: 2, tagClassName: test.com.skin.ayada.taglib.TestBodyTag, tagInstanceName: _jsp_TestBodyTag_150 */
        /* <app:bodytest/> */
        test.com.skin.ayada.taglib.TestBodyTag _jsp_TestBodyTag_150 = new test.com.skin.ayada.taglib.TestBodyTag();
        _jsp_TestBodyTag_150.setParent((Tag)null);
        _jsp_TestBodyTag_150.setPageContext(pageContext);
        int _jsp_start_flag_150 = _jsp_TestBodyTag_150.doStartTag();

        if(_jsp_start_flag_150 == Tag.SKIP_PAGE){
            return;
        }
        if(_jsp_start_flag_150 != Tag.SKIP_BODY){
            _jsp_TestBodyTag_150.doAfterBody();
        }
        _jsp_TestBodyTag_150.doEndTag();
        _jsp_TestBodyTag_150.release();
        /* NODE END: lineNumber: 130, tagClassName: test.com.skin.ayada.taglib.TestBodyTag, tagInstanceName: _jsp_TestBodyTag_150 */

        /* TEXT: lineNumber: 130 */
        out.write("\r\n");

        /* NODE START: lineNumber: 131, offset: 152, length: 3, tagClassName: test.com.skin.ayada.taglib.TestBodyTag, tagInstanceName: _jsp_TestBodyTag_153 */
        /* <app:bodytest>...</app:bodytest> */
        test.com.skin.ayada.taglib.TestBodyTag _jsp_TestBodyTag_153 = new test.com.skin.ayada.taglib.TestBodyTag();
        _jsp_TestBodyTag_153.setParent((Tag)null);
        _jsp_TestBodyTag_153.setPageContext(pageContext);
        int _jsp_start_flag_153 = _jsp_TestBodyTag_153.doStartTag();

        if(_jsp_start_flag_153 == Tag.SKIP_PAGE){
            return;
        }
        if(_jsp_start_flag_153 != Tag.SKIP_BODY){
            int _jsp_flag_153 = 0;
            if(_jsp_start_flag_153 == BodyTag.EVAL_BODY_BUFFERED){
                BodyContent _jsp_body_content_153 = (BodyContent)(pageContext.pushBody());
                _jsp_TestBodyTag_153.setBodyContent(_jsp_body_content_153);
                _jsp_TestBodyTag_153.doInitBody();
                out = _jsp_body_content_153;
            }

            do{
                /* TEXT: lineNumber: 131 */
                out.write("Hello World !");
                _jsp_flag_153 = _jsp_TestBodyTag_153.doAfterBody();
            }
            while(_jsp_flag_153 == IterationTag.EVAL_BODY_AGAIN);
            if(_jsp_start_flag_153 == BodyTag.EVAL_BODY_BUFFERED){
                out = pageContext.popBody();
            }
        }
        _jsp_TestBodyTag_153.doEndTag();
        _jsp_TestBodyTag_153.release();
        /* NODE END: lineNumber: 131, tagClassName: test.com.skin.ayada.taglib.TestBodyTag, tagInstanceName: _jsp_TestBodyTag_153 */

        /* TEXT: lineNumber: 131 */
        out.write("\r\n");

        /* NODE START: lineNumber: 132, offset: 156, length: 2, tagClassName: com.skin.ayada.jstl.core.TestTag, tagInstanceName: _jsp_TestTag_157 */
        /* <c:test/> */
        com.skin.ayada.jstl.core.TestTag _jsp_TestTag_157 = new com.skin.ayada.jstl.core.TestTag();
        _jsp_TestTag_157.setParent((Tag)null);
        _jsp_TestTag_157.setPageContext(pageContext);
        int _jsp_start_flag_157 = _jsp_TestTag_157.doStartTag();

        if(_jsp_start_flag_157 == Tag.SKIP_PAGE){
            return;
        }
        if(_jsp_start_flag_157 != Tag.SKIP_BODY){
            _jsp_TestTag_157.doAfterBody();
        }
        _jsp_TestTag_157.doEndTag();
        _jsp_TestTag_157.release();
        /* NODE END: lineNumber: 132, tagClassName: com.skin.ayada.jstl.core.TestTag, tagInstanceName: _jsp_TestTag_157 */

        /* TEXT: lineNumber: 132 */
        out.write("\r\n</body>\r\n</html>");
        out.flush();
        jspWriter.flush();
    }
}
