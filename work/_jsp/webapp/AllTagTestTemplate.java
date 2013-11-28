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
 * LastModified: 2013-11-28 15:43:59 613
 * JSP generated by JspCompiler-0.1.1 (built 2013-11-28 15:45:22 550)
 *
 */
package _jsp.webapp;

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
     * @throws Exception
     */
    @Override
    public void _execute(final PageContext pageContext) throws Exception
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
        pageContext.setAttribute("myString", ExpressionUtil.evaluate(expressionContext, "${StringUtil.replace(\'abc\', \'b\', \'\\n\')}"));
        /* jsp.jstl.core.SetTag END */
        /* NODE END: lineNumber: 76, tagClassName: com.skin.ayada.jstl.core.SetTag, tagInstanceName: _jsp_SetTag_52 */

        /* TEXT: lineNumber: 76 */
        out.write("\r\n<p>myString: [");

        /* NODE START: lineNumber: 77, offset: 54, length: 2, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_55 */
        /* <c:out value="${myString}"/> */
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

        /* TEXT: lineNumber: 79 */
        out.write("\r\n<p>myString: [");

        /* NODE START: lineNumber: 80, offset: 60, length: 2, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_61 */
        /* <c:out value="${myString}"/> */
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

        /* TEXT: lineNumber: 82 */
        out.write("\r\n<h1>############## ");
        /* VARIABLE: lineNumber: 83 */
        out.print(pageContext.getAttribute("myVar"), false);
        /* TEXT: lineNumber: 83 */
        out.write(" ##############</h1>\r\n\r\n");

        /* NODE START: lineNumber: 85, offset: 68, length: 2, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_69 */
        /* <c:out value="c:out: Hello, World!"/> */
        out.write("c:out: Hello, World!");
        /* jsp.jstl.core.OutTag END */
        /* NODE END: lineNumber: 85, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_69 */

        /* TEXT: lineNumber: 85 */
        out.write("\r\n<div style=\"background-color: #c0c0c0;\"></div>\r\n");

        /* NODE START: lineNumber: 87, offset: 71, length: 2, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_72 */
        /* <c:out value="&quot;&lt;div&gt;Hello World!&lt;/div&gt;&quot;" escapeXml="false"/> */
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
        pageContext.printBodyContent((BodyContent)out, true);
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

        /* TEXT: lineNumber: 91 */
        out.write("\r\n");

        /* NODE START: lineNumber: 92, offset: 85, length: 2, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_86 */
        /* <c:out value="Hello, ${myName}!" escapeXml="true"/> */
        out.write(ExpressionUtil.getHtml(expressionContext, "Hello, ${myName}!"));
        /* jsp.jstl.core.OutTag END */
        /* NODE END: lineNumber: 92, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_86 */

        /* TEXT: lineNumber: 92 */
        out.write("\r\n");

        /* NODE START: lineNumber: 93, offset: 88, length: 2, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_89 */
        /* <c:out value="Hello, ${myName}!" escapeXml="false"/> */
        out.write(ExpressionUtil.getString(expressionContext, "Hello, ${myName}!"));
        /* jsp.jstl.core.OutTag END */
        /* NODE END: lineNumber: 93, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_89 */

        /* TEXT: lineNumber: 93 */
        out.write("\r\n\r\n");

        /* NODE START: lineNumber: 95, offset: 91, length: 3, tagClassName: com.skin.ayada.jstl.core.IfTag, tagInstanceName: _jsp_IfTag_92 */
        /* <c:if test="${1 == 1}">...</c:if> */
        if(ExpressionUtil.getBoolean(expressionContext, "${1 == 1}")){
            /* TEXT: lineNumber: 95 */
            out.write("c:if test");
        } /* jsp.jstl.core.IfTag END */
        /* NODE END: lineNumber: 95, tagClassName: com.skin.ayada.jstl.core.IfTag, tagInstanceName: _jsp_IfTag_92 */

        /* TEXT: lineNumber: 95 */
        out.write("\r\n\r\n<h1>c:forEach test1</h1>\r\n");

        /* NODE START: lineNumber: 98, offset: 95, length: 3, tagClassName: com.skin.ayada.jstl.core.ForEachTag, tagInstanceName: _jsp_ForEachTag_96 */
        /* <c:each items="1,2,3,4,5" var="mynum">...</c:each> */
        Object _jsp_old_var_96 = pageContext.getAttribute("mynum");
        com.skin.ayada.jstl.core.ForEachTag _jsp_ForEachTag_96 = new com.skin.ayada.jstl.core.ForEachTag();
        _jsp_ForEachTag_96.setParent((Tag)null);
        _jsp_ForEachTag_96.setPageContext(pageContext);
        _jsp_ForEachTag_96.setVar("mynum");
        _jsp_ForEachTag_96.setItems("1,2,3,4,5");
        _jsp_ForEachTag_96.init();
        while(_jsp_ForEachTag_96.hasNext()){
            pageContext.setAttribute("mynum", _jsp_ForEachTag_96.next());
            /* VARIABLE: lineNumber: 100 */
            out.print(pageContext.getAttribute("mynum"), false);
        }
        _jsp_ForEachTag_96.release();
        pageContext.setAttribute("mynum", _jsp_old_var_96);
        /* jsp.jstl.core.ForEachTag END */
        /* NODE END: lineNumber: 98, tagClassName: com.skin.ayada.jstl.core.ForEachTag, tagInstanceName: _jsp_ForEachTag_96 */

        /* TEXT: lineNumber: 100 */
        out.write("\r\n\r\n<h1>c:forEach test2</h1>\r\n");

        /* NODE START: lineNumber: 103, offset: 99, length: 20, tagClassName: com.skin.ayada.jstl.core.ForEachTag, tagInstanceName: _jsp_ForEachTag_100 */
        /* <c:forEach items="${userList}" var="user" varStatus="status">...</c:forEach> */
        Object _jsp_old_var_100 = pageContext.getAttribute("user");
        Object _jsp_old_var_status_100 = pageContext.getAttribute("status");
        com.skin.ayada.jstl.core.ForEachTag _jsp_ForEachTag_100 = new com.skin.ayada.jstl.core.ForEachTag();
        _jsp_ForEachTag_100.setParent((Tag)null);
        _jsp_ForEachTag_100.setPageContext(pageContext);
        _jsp_ForEachTag_100.setVar("user");
        _jsp_ForEachTag_100.setItems(ExpressionUtil.evaluate(expressionContext, "${userList}"));
        _jsp_ForEachTag_100.init();
        while(_jsp_ForEachTag_100.hasNext()){
            pageContext.setAttribute("user", _jsp_ForEachTag_100.next());
            /* TEXT: lineNumber: 103 */
            out.write("\r\n    <p>user: ");
            /* EXPRESSION: lineNumber: 104 */
            out.write(expressionContext.getString("user.userName"));
            /* TEXT: lineNumber: 104 */
            out.write("</p>\r\n    ");

            /* NODE START: lineNumber: 105, offset: 103, length: 14, tagClassName: com.skin.ayada.jstl.core.ChooseTag, tagInstanceName: _jsp_ChooseTag_104 */
            /* <c:choose>...</c:choose> */
            boolean _jsp_ChooseTag_104 = true;

            /* NODE START: lineNumber: 106, offset: 104, length: 3, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _jsp_WhenTag_105 */
            /* <c:when test="${user.userName == &#39;test1&#39;}">...</c:when> */
            if(_jsp_ChooseTag_104 && ExpressionUtil.getBoolean(expressionContext, "${user.userName == \'test1\'}")){
                _jsp_ChooseTag_104 = false;
                /* TEXT: lineNumber: 106 */
                out.write("<p>test1, good man !</p>");
            } /* jsp.jstl.core.WhenTag END */
            /* NODE END: lineNumber: 106, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _jsp_WhenTag_105 */

            /* NODE START: lineNumber: 107, offset: 107, length: 3, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _jsp_WhenTag_108 */
            /* <c:when test="${user.userName == &#39;test2&#39;}">...</c:when> */
            if(_jsp_ChooseTag_104 && ExpressionUtil.getBoolean(expressionContext, "${user.userName == \'test2\'}")){
                _jsp_ChooseTag_104 = false;
                /* TEXT: lineNumber: 107 */
                out.write("<p>test2, good man !</p>");
            } /* jsp.jstl.core.WhenTag END */
            /* NODE END: lineNumber: 107, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _jsp_WhenTag_108 */

            /* NODE START: lineNumber: 108, offset: 110, length: 3, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _jsp_WhenTag_111 */
            /* <c:when test="${user.userName == &#39;test3&#39;}">...</c:when> */
            if(_jsp_ChooseTag_104 && ExpressionUtil.getBoolean(expressionContext, "${user.userName == \'test3\'}")){
                _jsp_ChooseTag_104 = false;
                /* TEXT: lineNumber: 108 */
                out.write("<p>test3, good man !</p>");
            } /* jsp.jstl.core.WhenTag END */
            /* NODE END: lineNumber: 108, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _jsp_WhenTag_111 */

            /* NODE START: lineNumber: 109, offset: 113, length: 3, tagClassName: com.skin.ayada.jstl.core.OtherwiseTag, tagInstanceName: _jsp_OtherwiseTag_114 */
            /* <c:otherwise>...</c:otherwise> */
            if(_jsp_ChooseTag_104){
                _jsp_ChooseTag_104 = false;
                /* TEXT: lineNumber: 109 */
                out.write("<p>unknown user! Do you known \'bad egg\'? You! Are!</p>");
            } /* jsp.jstl.core.OtherwiseTag END */
            /* NODE END: lineNumber: 109, tagClassName: com.skin.ayada.jstl.core.OtherwiseTag, tagInstanceName: _jsp_OtherwiseTag_114 */

            /* jsp.jstl.core.ChooseTag END */
            /* NODE END: lineNumber: 105, tagClassName: com.skin.ayada.jstl.core.ChooseTag, tagInstanceName: _jsp_ChooseTag_104 */

            /* TEXT: lineNumber: 110 */
            out.write("\r\n");
        }
        _jsp_ForEachTag_100.release();
        pageContext.setAttribute("user", _jsp_old_var_100);
        pageContext.setAttribute("status", _jsp_old_var_status_100);
        /* jsp.jstl.core.ForEachTag END */
        /* NODE END: lineNumber: 103, tagClassName: com.skin.ayada.jstl.core.ForEachTag, tagInstanceName: _jsp_ForEachTag_100 */

        /* TEXT: lineNumber: 111 */
        out.write("\r\n\r\n<h1>c:choose test1</h1>\r\n");

        /* NODE START: lineNumber: 114, offset: 120, length: 14, tagClassName: com.skin.ayada.jstl.core.ChooseTag, tagInstanceName: _jsp_ChooseTag_121 */
        /* <c:choose>...</c:choose> */
        boolean _jsp_ChooseTag_121 = true;

        /* NODE START: lineNumber: 115, offset: 121, length: 3, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _jsp_WhenTag_122 */
        /* <c:when test="${1 == 1}">...</c:when> */
        if(_jsp_ChooseTag_121 && ExpressionUtil.getBoolean(expressionContext, "${1 == 1}")){
            _jsp_ChooseTag_121 = false;
            /* TEXT: lineNumber: 115 */
            out.write("c:when test=\"{1 == 1}\"");
        } /* jsp.jstl.core.WhenTag END */
        /* NODE END: lineNumber: 115, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _jsp_WhenTag_122 */

        /* NODE START: lineNumber: 116, offset: 124, length: 3, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _jsp_WhenTag_125 */
        /* <c:when test="${2 == 2}">...</c:when> */
        if(_jsp_ChooseTag_121 && ExpressionUtil.getBoolean(expressionContext, "${2 == 2}")){
            _jsp_ChooseTag_121 = false;
            /* TEXT: lineNumber: 116 */
            out.write("c:when test=\"{2 == 2}\"");
        } /* jsp.jstl.core.WhenTag END */
        /* NODE END: lineNumber: 116, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _jsp_WhenTag_125 */

        /* NODE START: lineNumber: 117, offset: 127, length: 3, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _jsp_WhenTag_128 */
        /* <c:when test="${3 == 3}">...</c:when> */
        if(_jsp_ChooseTag_121 && ExpressionUtil.getBoolean(expressionContext, "${3 == 3}")){
            _jsp_ChooseTag_121 = false;
            /* TEXT: lineNumber: 117 */
            out.write("c:when test=\"{3 == 3}\"");
        } /* jsp.jstl.core.WhenTag END */
        /* NODE END: lineNumber: 117, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _jsp_WhenTag_128 */

        /* NODE START: lineNumber: 118, offset: 130, length: 3, tagClassName: com.skin.ayada.jstl.core.OtherwiseTag, tagInstanceName: _jsp_OtherwiseTag_131 */
        /* <c:otherwise>...</c:otherwise> */
        if(_jsp_ChooseTag_121){
            _jsp_ChooseTag_121 = false;
            /* TEXT: lineNumber: 118 */
            out.write("c:otherwise");
        } /* jsp.jstl.core.OtherwiseTag END */
        /* NODE END: lineNumber: 118, tagClassName: com.skin.ayada.jstl.core.OtherwiseTag, tagInstanceName: _jsp_OtherwiseTag_131 */

        /* jsp.jstl.core.ChooseTag END */
        /* NODE END: lineNumber: 114, tagClassName: com.skin.ayada.jstl.core.ChooseTag, tagInstanceName: _jsp_ChooseTag_121 */

        /* TEXT: lineNumber: 119 */
        out.write("\r\n\r\n<h1>app:test test1</h1>\r\n");

        /* NODE START: lineNumber: 122, offset: 135, length: 2, tagClassName: com.skin.ayada.jstl.core.TestTag, tagInstanceName: _jsp_TestTag_136 */
        /* <app:test myBoolean="true" myChar="c" myByte="1" myInt="-1.0" myFloat="1.0f" myDouble="1.0d" myLong="1L" myString="Hello"/> */
        com.skin.ayada.jstl.core.TestTag _jsp_TestTag_136 = new com.skin.ayada.jstl.core.TestTag();
        _jsp_TestTag_136.setParent((Tag)null);
        _jsp_TestTag_136.setPageContext(pageContext);
        _jsp_TestTag_136.setMyBoolean(true);
        _jsp_TestTag_136.setMyChar('c');
        _jsp_TestTag_136.setMyByte((byte)1);
        _jsp_TestTag_136.setMyInt(-1);
        _jsp_TestTag_136.setMyFloat(1.0f);
        _jsp_TestTag_136.setMyDouble(1.0d);
        _jsp_TestTag_136.setMyLong(1L);
        _jsp_TestTag_136.setMyString("Hello");
        int _jsp_start_flag_136 = _jsp_TestTag_136.doStartTag();

        if(_jsp_start_flag_136 == Tag.SKIP_PAGE){
            return;
        }
        if(_jsp_start_flag_136 != Tag.SKIP_BODY){
            _jsp_TestTag_136.doAfterBody();
        }
        _jsp_TestTag_136.doEndTag();
        _jsp_TestTag_136.release();
        /* NODE END: lineNumber: 122, tagClassName: com.skin.ayada.jstl.core.TestTag, tagInstanceName: _jsp_TestTag_136 */

        /* TEXT: lineNumber: 122 */
        out.write("\r\n");

        /* NODE START: lineNumber: 123, offset: 138, length: 2, tagClassName: com.skin.ayada.jstl.core.TestTag, tagInstanceName: _jsp_TestTag_139 */
        /* <app:test myBoolean="false" myChar="c" myByte="243" myInt="-1.0" myFloat="1.0F" myDouble="1.0D" myLong="1L" myString="Hello"/> */
        com.skin.ayada.jstl.core.TestTag _jsp_TestTag_139 = new com.skin.ayada.jstl.core.TestTag();
        _jsp_TestTag_139.setParent((Tag)null);
        _jsp_TestTag_139.setPageContext(pageContext);
        _jsp_TestTag_139.setMyBoolean(false);
        _jsp_TestTag_139.setMyChar('c');
        _jsp_TestTag_139.setMyByte((byte)243);
        _jsp_TestTag_139.setMyInt(-1);
        _jsp_TestTag_139.setMyFloat(1.0f);
        _jsp_TestTag_139.setMyDouble(1.0d);
        _jsp_TestTag_139.setMyLong(1L);
        _jsp_TestTag_139.setMyString("Hello");
        int _jsp_start_flag_139 = _jsp_TestTag_139.doStartTag();

        if(_jsp_start_flag_139 == Tag.SKIP_PAGE){
            return;
        }
        if(_jsp_start_flag_139 != Tag.SKIP_BODY){
            _jsp_TestTag_139.doAfterBody();
        }
        _jsp_TestTag_139.doEndTag();
        _jsp_TestTag_139.release();
        /* NODE END: lineNumber: 123, tagClassName: com.skin.ayada.jstl.core.TestTag, tagInstanceName: _jsp_TestTag_139 */

        /* TEXT: lineNumber: 123 */
        out.write("\r\n");

        /* NODE START: lineNumber: 124, offset: 141, length: 2, tagClassName: com.skin.ayada.jstl.core.TestTag, tagInstanceName: _jsp_TestTag_142 */
        /* <app:test myInt="-1.0" myFloat="1.0" myDouble="1e3" myLong="1e3" myString="Hello"/> */
        com.skin.ayada.jstl.core.TestTag _jsp_TestTag_142 = new com.skin.ayada.jstl.core.TestTag();
        _jsp_TestTag_142.setParent((Tag)null);
        _jsp_TestTag_142.setPageContext(pageContext);
        _jsp_TestTag_142.setMyInt(-1);
        _jsp_TestTag_142.setMyFloat(1.0f);
        _jsp_TestTag_142.setMyDouble(1000.0d);
        _jsp_TestTag_142.setMyLong(1000L);
        _jsp_TestTag_142.setMyString("Hello");
        int _jsp_start_flag_142 = _jsp_TestTag_142.doStartTag();

        if(_jsp_start_flag_142 == Tag.SKIP_PAGE){
            return;
        }
        if(_jsp_start_flag_142 != Tag.SKIP_BODY){
            _jsp_TestTag_142.doAfterBody();
        }
        _jsp_TestTag_142.doEndTag();
        _jsp_TestTag_142.release();
        /* NODE END: lineNumber: 124, tagClassName: com.skin.ayada.jstl.core.TestTag, tagInstanceName: _jsp_TestTag_142 */

        /* TEXT: lineNumber: 124 */
        out.write("\r\n");

        /* NODE START: lineNumber: 125, offset: 144, length: 2, tagClassName: com.skin.ayada.jstl.core.TestTag, tagInstanceName: _jsp_TestTag_145 */
        /* <app:test myInt="-1.0" myFloat="1.0" myDouble="1.2e3" myLong="1.2e3" myString="Hello"/> */
        com.skin.ayada.jstl.core.TestTag _jsp_TestTag_145 = new com.skin.ayada.jstl.core.TestTag();
        _jsp_TestTag_145.setParent((Tag)null);
        _jsp_TestTag_145.setPageContext(pageContext);
        _jsp_TestTag_145.setMyInt(-1);
        _jsp_TestTag_145.setMyFloat(1.0f);
        _jsp_TestTag_145.setMyDouble(1200.0d);
        _jsp_TestTag_145.setMyLong(1200L);
        _jsp_TestTag_145.setMyString("Hello");
        int _jsp_start_flag_145 = _jsp_TestTag_145.doStartTag();

        if(_jsp_start_flag_145 == Tag.SKIP_PAGE){
            return;
        }
        if(_jsp_start_flag_145 != Tag.SKIP_BODY){
            _jsp_TestTag_145.doAfterBody();
        }
        _jsp_TestTag_145.doEndTag();
        _jsp_TestTag_145.release();
        /* NODE END: lineNumber: 125, tagClassName: com.skin.ayada.jstl.core.TestTag, tagInstanceName: _jsp_TestTag_145 */

        /* TEXT: lineNumber: 125 */
        out.write("\r\n\r\n<h1>app:scrollpage test1</h1>\r\n");

        /* NODE START: lineNumber: 128, offset: 147, length: 2, tagClassName: test.com.skin.ayada.taglib.ScrollPage, tagInstanceName: _jsp_ScrollPage_148 */
        /* <app:scrollpage count="254" pageNum="2" pageSize="10"/> */
        test.com.skin.ayada.taglib.ScrollPage _jsp_ScrollPage_148 = new test.com.skin.ayada.taglib.ScrollPage();
        _jsp_ScrollPage_148.setParent((Tag)null);
        _jsp_ScrollPage_148.setPageContext(pageContext);
        _jsp_ScrollPage_148.setCount(254);
        _jsp_ScrollPage_148.setPageNum(2);
        _jsp_ScrollPage_148.setPageSize(10);
        int _jsp_start_flag_148 = _jsp_ScrollPage_148.doStartTag();

        if(_jsp_start_flag_148 == Tag.SKIP_PAGE){
            return;
        }
        if(_jsp_start_flag_148 != Tag.SKIP_BODY){
            _jsp_ScrollPage_148.doAfterBody();
        }
        _jsp_ScrollPage_148.doEndTag();
        _jsp_ScrollPage_148.release();
        /* NODE END: lineNumber: 128, tagClassName: test.com.skin.ayada.taglib.ScrollPage, tagInstanceName: _jsp_ScrollPage_148 */

        /* TEXT: lineNumber: 128 */
        out.write("\r\n\r\n");

        /* NODE START: lineNumber: 130, offset: 150, length: 2, tagClassName: test.com.skin.ayada.taglib.TestBodyTag, tagInstanceName: _jsp_TestBodyTag_151 */
        /* <app:bodytest/> */
        test.com.skin.ayada.taglib.TestBodyTag _jsp_TestBodyTag_151 = new test.com.skin.ayada.taglib.TestBodyTag();
        _jsp_TestBodyTag_151.setParent((Tag)null);
        _jsp_TestBodyTag_151.setPageContext(pageContext);
        int _jsp_start_flag_151 = _jsp_TestBodyTag_151.doStartTag();

        if(_jsp_start_flag_151 == Tag.SKIP_PAGE){
            return;
        }
        if(_jsp_start_flag_151 != Tag.SKIP_BODY){
            _jsp_TestBodyTag_151.doAfterBody();
        }
        _jsp_TestBodyTag_151.doEndTag();
        _jsp_TestBodyTag_151.release();
        /* NODE END: lineNumber: 130, tagClassName: test.com.skin.ayada.taglib.TestBodyTag, tagInstanceName: _jsp_TestBodyTag_151 */

        /* TEXT: lineNumber: 130 */
        out.write("\r\n");

        /* NODE START: lineNumber: 131, offset: 153, length: 3, tagClassName: test.com.skin.ayada.taglib.TestBodyTag, tagInstanceName: _jsp_TestBodyTag_154 */
        /* <app:bodytest>...</app:bodytest> */
        test.com.skin.ayada.taglib.TestBodyTag _jsp_TestBodyTag_154 = new test.com.skin.ayada.taglib.TestBodyTag();
        _jsp_TestBodyTag_154.setParent((Tag)null);
        _jsp_TestBodyTag_154.setPageContext(pageContext);
        int _jsp_start_flag_154 = _jsp_TestBodyTag_154.doStartTag();

        if(_jsp_start_flag_154 == Tag.SKIP_PAGE){
            return;
        }
        if(_jsp_start_flag_154 != Tag.SKIP_BODY){
            int _jsp_flag_154 = 0;
            if(_jsp_start_flag_154 == BodyTag.EVAL_BODY_BUFFERED){
                BodyContent _jsp_body_content_154 = (BodyContent)(pageContext.pushBody());
                _jsp_TestBodyTag_154.setBodyContent(_jsp_body_content_154);
                _jsp_TestBodyTag_154.doInitBody();
                out = _jsp_body_content_154;
            }

            do{
                /* TEXT: lineNumber: 131 */
                out.write("Hello World !");
                _jsp_flag_154 = _jsp_TestBodyTag_154.doAfterBody();
            }
            while(_jsp_flag_154 == IterationTag.EVAL_BODY_AGAIN);
            if(_jsp_start_flag_154 == BodyTag.EVAL_BODY_BUFFERED){
                out = pageContext.popBody();
            }
        }
        _jsp_TestBodyTag_154.doEndTag();
        _jsp_TestBodyTag_154.release();
        /* NODE END: lineNumber: 131, tagClassName: test.com.skin.ayada.taglib.TestBodyTag, tagInstanceName: _jsp_TestBodyTag_154 */

        /* TEXT: lineNumber: 131 */
        out.write("\r\n");

        /* NODE START: lineNumber: 132, offset: 157, length: 2, tagClassName: com.skin.ayada.jstl.core.TestTag, tagInstanceName: _jsp_TestTag_158 */
        /* <c:test/> */
        com.skin.ayada.jstl.core.TestTag _jsp_TestTag_158 = new com.skin.ayada.jstl.core.TestTag();
        _jsp_TestTag_158.setParent((Tag)null);
        _jsp_TestTag_158.setPageContext(pageContext);
        int _jsp_start_flag_158 = _jsp_TestTag_158.doStartTag();

        if(_jsp_start_flag_158 == Tag.SKIP_PAGE){
            return;
        }
        if(_jsp_start_flag_158 != Tag.SKIP_BODY){
            _jsp_TestTag_158.doAfterBody();
        }
        _jsp_TestTag_158.doEndTag();
        _jsp_TestTag_158.release();
        /* NODE END: lineNumber: 132, tagClassName: com.skin.ayada.jstl.core.TestTag, tagInstanceName: _jsp_TestTag_158 */

        /* TEXT: lineNumber: 132 */
        out.write("\r\n</body>\r\n</html>");
        out.flush();
        jspWriter.flush();
    }
}
