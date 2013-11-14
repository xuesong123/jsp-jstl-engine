/*
 * $RCSfile: AllTagTestTemplate.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-8 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 *
 * JSP generated by JspCompiler-0.1.1 (built Thu Nov 14 18:04:48 CST 2013)
 *
 */
package _jsp.webapp;

import com.skin.ayada.runtime.ExpressionContext;
import com.skin.ayada.runtime.JspWriter;
import com.skin.ayada.runtime.PageContext;
import com.skin.ayada.tagext.BodyContent;
import com.skin.ayada.tagext.IterationTag;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.template.JspTemplate;
import com.skin.ayada.util.ExpressionUtil;

/**
 * <p>Title: AllTagTestTemplate</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author JspCompiler
 * @version 1.0
 */
public class AllTagTestTemplate extends JspTemplate
{
    /**
     * @param pageContext
     * @throws Exception
     */
    @Override
    public void execute(final PageContext pageContext) throws Exception
    {
        JspWriter out = pageContext.getOut();
        JspWriter jspWriter = pageContext.getOut();
        ExpressionContext expressionContext = pageContext.getExpressionContext();

        /* NODE START: lineNumber: 4, offset: 0, length: 2, tagClassName: com.skin.ayada.jstl.core.ImportTag, tagInstanceName: _jsp_ImportTag_1 */
        /* <t:import name="app:scrollpage" className="test.com.skin.ayada.taglib.ScrollPage"/> */
        pageContext.getTagLibrary().setup("app:scrollpage", "test.com.skin.ayada.taglib.ScrollPage");
        /* jsp.jstl.core.ImportTag END */
        /* NODE END: lineNumber: 4, tagClassName: com.skin.ayada.jstl.core.ImportTag, tagInstanceName: _jsp_ImportTag_1 */

        /* NODE START: lineNumber: 5, offset: 2, length: 2, tagClassName: com.skin.ayada.jstl.core.ImportTag, tagInstanceName: _jsp_ImportTag_3 */
        /* <t:import name="app:test" className="test.com.skin.ayada.taglib.TestTag"/> */
        pageContext.getTagLibrary().setup("app:test", "test.com.skin.ayada.taglib.TestTag");
        /* jsp.jstl.core.ImportTag END */
        /* NODE END: lineNumber: 5, tagClassName: com.skin.ayada.jstl.core.ImportTag, tagInstanceName: _jsp_ImportTag_3 */

        /* TEXT: lineNumber: 6 */
        out.write("<html>\r\n<head>\r\n<title>test</title>\r\n</head>\r\n<body jsp=\"\" version=\"1.0\">\r\n\r\n<jsp:declaration>\r\n    public int myInt = 0;\r\n</jsp:declaration>\r\n\r\n\r\n<jsp:declaration>\r\n    public void hello2(){\r\n        System.out.println(\"Hello, Ayada !\");\r\n    }\r\n</jsp:declaration>\r\n\r\n<jsp:scriptlet>\r\n    System.out.println(\"Hello, Ayada1 !\");\r\n</jsp:scriptlet>\r\n\r\n<jsp:scriptlet>\r\n    System.out.println(\"Hello, Ayada2 !\");\r\n</jsp:scriptlet>\r\n\r\n\r\n\r\n<jsp:scriptlet>\r\nSystem.out.println(\"Hello, Ayada4 !\");\r\n</jsp:scriptlet >\r\n\r\n<jsp:expression>(\"Hello\" + \" \" + \"World !\")</jsp:expression>\r\n\r\n");

        /* NODE START: lineNumber: 49, offset: 5, length: 2, tagClassName: com.skin.ayada.jstl.core.SetTag, tagInstanceName: _jsp_SetTag_6 */
        /* <c:set var="myVar" value="Hello, Ayada!"/> */
        pageContext.setAttribute("myVar", ExpressionUtil.evaluate(expressionContext, "Hello, Ayada!"));
        /* jsp.jstl.core.SetTag END */
        /* NODE END: lineNumber: 49, tagClassName: com.skin.ayada.jstl.core.SetTag, tagInstanceName: _jsp_SetTag_6 */

        /* TEXT: lineNumber: 49 */
        out.write("\r\n<h1>");
        /* EXPRESSION: lineNumber: 50 */
        out.write(expressionContext.getString("myVar"));
        /* TEXT: lineNumber: 50 */
        out.write("</h1>\r\n\r\n");

        /* NODE START: lineNumber: 52, offset: 10, length: 2, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_11 */
        /* <c:out value="c:out: Hello, Ayada!"/> */
        out.write(ExpressionUtil.getString(expressionContext, "c:out: Hello, Ayada!"));
        /* jsp.jstl.core.OutTag END */
        /* NODE END: lineNumber: 52, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_11 */

        /* TEXT: lineNumber: 52 */
        out.write("\r\n<div style=\"background-color: #c0c0c0;\"></div>\r\n");

        /* NODE START: lineNumber: 54, offset: 13, length: 2, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_14 */
        /* <c:out escapeXml="true" value="<div>Hello Ayada!</div>"/> */
        out.write(ExpressionUtil.getHtml(expressionContext, "<div>Hello Ayada!</div>"));
        /* jsp.jstl.core.OutTag END */
        /* NODE END: lineNumber: 54, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_14 */

        /* TEXT: lineNumber: 54 */
        out.write("\r\n");

        /* NODE START: lineNumber: 55, offset: 16, length: 3, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_17 */
        /* <c:out escapeXml="true">...</c:out> */
        out = pageContext.pushBody();
        /* TEXT: lineNumber: 55 */
        out.write("<h1>Hello Ayada!</h1>");
        pageContext.printBodyContent((BodyContent)out, true);
        out = pageContext.popBody();
        /* jsp.jstl.core.OutTag END */
        /* NODE END: lineNumber: 55, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_17 */

        /* TEXT: lineNumber: 55 */
        out.write("\r\n\r\n");

        /* NODE START: lineNumber: 57, offset: 20, length: 3, tagClassName: com.skin.ayada.jstl.core.IfTag, tagInstanceName: _jsp_IfTag_21 */
        /* <c:if test="${1 == 1}">...</c:if> */
        if(ExpressionUtil.getBoolean(expressionContext, "${1 == 1}")){
        /* TEXT: lineNumber: 57 */
        out.write("c:if test");
        } /* jsp.jstl.core.IfTag END */
        /* NODE END: lineNumber: 57, tagClassName: com.skin.ayada.jstl.core.IfTag, tagInstanceName: _jsp_IfTag_21 */

        /* TEXT: lineNumber: 57 */
        out.write("\r\n\r\n");

        /* NODE START: lineNumber: 59, offset: 24, length: 3, tagClassName: com.skin.ayada.jstl.core.ForEachTag, tagInstanceName: _jsp_ForEachTag_25 */
        /* <c:forEach items="1,2,3,4,5" var="mynum">...</c:forEach> */
        Object _jsp_old_var_25 = pageContext.getAttribute("mynum");
        com.skin.ayada.jstl.core.ForEachTag _jsp_ForEachTag_25 = new com.skin.ayada.jstl.core.ForEachTag();

        _jsp_ForEachTag_25.setParent((Tag)null);
        _jsp_ForEachTag_25.setPageContext(pageContext);
        _jsp_ForEachTag_25.setVar("mynum");
        _jsp_ForEachTag_25.setItems(ExpressionUtil.evaluate(expressionContext, "1,2,3,4,5"));
        int _jsp_flag_25 = _jsp_ForEachTag_25.doStartTag();
        if(_jsp_flag_25 != Tag.SKIP_BODY){
            while(true){
        /* EXPRESSION: lineNumber: 59 */
        out.write(expressionContext.getString("mynum"));
               if(_jsp_ForEachTag_25.hasNext()){
                   pageContext.setAttribute("mynum", _jsp_ForEachTag_25.next());
               }
               else{
                   break;
               }
           }
        }
        _jsp_ForEachTag_25.release();
        pageContext.setAttribute("mynum", _jsp_old_var_25);
        /* jsp.jstl.core.ForEachTag END */
        /* NODE END: lineNumber: 59, tagClassName: com.skin.ayada.jstl.core.ForEachTag, tagInstanceName: _jsp_ForEachTag_25 */

        /* TEXT: lineNumber: 59 */
        out.write("\r\n\r\n");

        /* NODE START: lineNumber: 61, offset: 28, length: 20, tagClassName: com.skin.ayada.jstl.core.ForEachTag, tagInstanceName: _jsp_ForEachTag_29 */
        /* <c:forEach varStatus="status" items="${userList}" var="user">...</c:forEach> */
        Object _jsp_old_var_29 = pageContext.getAttribute("user");
        Object _jsp_old_var_status_29 = pageContext.getAttribute("status");
        com.skin.ayada.jstl.core.ForEachTag _jsp_ForEachTag_29 = new com.skin.ayada.jstl.core.ForEachTag();

        _jsp_ForEachTag_29.setParent((Tag)null);
        _jsp_ForEachTag_29.setPageContext(pageContext);
        _jsp_ForEachTag_29.setVar("user");
        _jsp_ForEachTag_29.setItems(ExpressionUtil.evaluate(expressionContext, "${userList}"));
        pageContext.setAttribute("status", _jsp_ForEachTag_29.getLoopStatus());
        int _jsp_flag_29 = _jsp_ForEachTag_29.doStartTag();
        if(_jsp_flag_29 != Tag.SKIP_BODY){
            while(true){
        /* TEXT: lineNumber: 61 */
        out.write("\r\n    <p>user: ");
        /* EXPRESSION: lineNumber: 62 */
        out.write(expressionContext.getString("user.userName"));
        /* TEXT: lineNumber: 62 */
        out.write("</p>\r\n    ");

        /* NODE START: lineNumber: 63, offset: 32, length: 14, tagClassName: com.skin.ayada.jstl.core.ChooseTag, tagInstanceName: _jsp_ChooseTag_33 */
        /* <c:choose>...</c:choose> */
        boolean _jsp_ChooseTag_33 = true;


        /* NODE START: lineNumber: 64, offset: 33, length: 3, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _jsp_WhenTag_34 */
        /* <c:when test="${user.userName == 'test1'}">...</c:when> */
        if(_jsp_ChooseTag_33 && ExpressionUtil.getBoolean(expressionContext, "${user.userName == \'test1\'}")){
            _jsp_ChooseTag_33 = false;
        /* TEXT: lineNumber: 64 */
        out.write("<p>test1, good man !</p>");
        } /* jsp.jstl.core.WhenTag END */
        /* NODE END: lineNumber: 64, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _jsp_WhenTag_34 */


        /* NODE START: lineNumber: 65, offset: 36, length: 3, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _jsp_WhenTag_37 */
        /* <c:when test="${user.userName == 'test2'}">...</c:when> */
        if(_jsp_ChooseTag_33 && ExpressionUtil.getBoolean(expressionContext, "${user.userName == \'test2\'}")){
            _jsp_ChooseTag_33 = false;
        /* TEXT: lineNumber: 65 */
        out.write("<p>test2, good man !</p>");
        } /* jsp.jstl.core.WhenTag END */
        /* NODE END: lineNumber: 65, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _jsp_WhenTag_37 */


        /* NODE START: lineNumber: 66, offset: 39, length: 3, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _jsp_WhenTag_40 */
        /* <c:when test="${user.userName == 'test3'}">...</c:when> */
        if(_jsp_ChooseTag_33 && ExpressionUtil.getBoolean(expressionContext, "${user.userName == \'test3\'}")){
            _jsp_ChooseTag_33 = false;
        /* TEXT: lineNumber: 66 */
        out.write("<p>test3, good man !</p>");
        } /* jsp.jstl.core.WhenTag END */
        /* NODE END: lineNumber: 66, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _jsp_WhenTag_40 */


        /* NODE START: lineNumber: 67, offset: 42, length: 3, tagClassName: com.skin.ayada.jstl.core.OtherwiseTag, tagInstanceName: _jsp_OtherwiseTag_43 */
        /* <c:otherwise>...</c:otherwise> */
        if(_jsp_ChooseTag_33){
            _jsp_ChooseTag_33 = false;
        /* TEXT: lineNumber: 67 */
        out.write("<p>unknown user! Do you known \'bad egg\'? You! Are!</p>");
        } /* jsp.jstl.core.OtherwiseTag END */
        /* NODE END: lineNumber: 67, tagClassName: com.skin.ayada.jstl.core.OtherwiseTag, tagInstanceName: _jsp_OtherwiseTag_43 */

        /* jsp.jstl.core.ChooseTag END */
        /* NODE END: lineNumber: 63, tagClassName: com.skin.ayada.jstl.core.ChooseTag, tagInstanceName: _jsp_ChooseTag_33 */

        /* TEXT: lineNumber: 68 */
        out.write("\r\n");
               if(_jsp_ForEachTag_29.hasNext()){
                   pageContext.setAttribute("user", _jsp_ForEachTag_29.next());
               }
               else{
                   break;
               }
           }
        }
        _jsp_ForEachTag_29.release();
        pageContext.setAttribute("user", _jsp_old_var_29);
        pageContext.setAttribute("status", _jsp_old_var_status_29);
        /* jsp.jstl.core.ForEachTag END */
        /* NODE END: lineNumber: 61, tagClassName: com.skin.ayada.jstl.core.ForEachTag, tagInstanceName: _jsp_ForEachTag_29 */

        /* TEXT: lineNumber: 69 */
        out.write("\r\n\r\n");

        /* NODE START: lineNumber: 71, offset: 49, length: 14, tagClassName: com.skin.ayada.jstl.core.ChooseTag, tagInstanceName: _jsp_ChooseTag_50 */
        /* <c:choose>...</c:choose> */
        boolean _jsp_ChooseTag_50 = true;


        /* NODE START: lineNumber: 72, offset: 50, length: 3, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _jsp_WhenTag_51 */
        /* <c:when test="${1 == 1}">...</c:when> */
        if(_jsp_ChooseTag_50 && ExpressionUtil.getBoolean(expressionContext, "${1 == 1}")){
            _jsp_ChooseTag_50 = false;
        /* TEXT: lineNumber: 72 */
        out.write("1");
        } /* jsp.jstl.core.WhenTag END */
        /* NODE END: lineNumber: 72, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _jsp_WhenTag_51 */


        /* NODE START: lineNumber: 73, offset: 53, length: 3, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _jsp_WhenTag_54 */
        /* <c:when test="${2 == 2}">...</c:when> */
        if(_jsp_ChooseTag_50 && ExpressionUtil.getBoolean(expressionContext, "${2 == 2}")){
            _jsp_ChooseTag_50 = false;
        /* TEXT: lineNumber: 73 */
        out.write("2");
        } /* jsp.jstl.core.WhenTag END */
        /* NODE END: lineNumber: 73, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _jsp_WhenTag_54 */


        /* NODE START: lineNumber: 74, offset: 56, length: 3, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _jsp_WhenTag_57 */
        /* <c:when test="${3 == 3}">...</c:when> */
        if(_jsp_ChooseTag_50 && ExpressionUtil.getBoolean(expressionContext, "${3 == 3}")){
            _jsp_ChooseTag_50 = false;
        /* TEXT: lineNumber: 74 */
        out.write("3");
        } /* jsp.jstl.core.WhenTag END */
        /* NODE END: lineNumber: 74, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _jsp_WhenTag_57 */


        /* NODE START: lineNumber: 75, offset: 59, length: 3, tagClassName: com.skin.ayada.jstl.core.OtherwiseTag, tagInstanceName: _jsp_OtherwiseTag_60 */
        /* <c:otherwise>...</c:otherwise> */
        if(_jsp_ChooseTag_50){
            _jsp_ChooseTag_50 = false;
        /* TEXT: lineNumber: 75 */
        out.write("otherwise");
        } /* jsp.jstl.core.OtherwiseTag END */
        /* NODE END: lineNumber: 75, tagClassName: com.skin.ayada.jstl.core.OtherwiseTag, tagInstanceName: _jsp_OtherwiseTag_60 */

        /* jsp.jstl.core.ChooseTag END */
        /* NODE END: lineNumber: 71, tagClassName: com.skin.ayada.jstl.core.ChooseTag, tagInstanceName: _jsp_ChooseTag_50 */

        /* TEXT: lineNumber: 76 */
        out.write("\r\n\r\n");

        /* NODE START: lineNumber: 78, offset: 64, length: 2, tagClassName: test.com.skin.ayada.taglib.TestTag, tagInstanceName: _jsp_TestTag_65 */
        /* <app:test myLong="1L" myInt="-1.0" myFloat="1.0f" myDouble="1.0d" myString="Hello"/> */
        test.com.skin.ayada.taglib.TestTag _jsp_TestTag_65 = new test.com.skin.ayada.taglib.TestTag();
        _jsp_TestTag_65.setParent((Tag)null);
        _jsp_TestTag_65.setPageContext(pageContext);
        _jsp_TestTag_65.setMyLong(ExpressionUtil.getLong(expressionContext, "1L"));
        _jsp_TestTag_65.setMyInt(ExpressionUtil.getInteger(expressionContext, "-1.0"));
        _jsp_TestTag_65.setMyFloat(ExpressionUtil.getFloat(expressionContext, "1.0f"));
        _jsp_TestTag_65.setMyDouble(ExpressionUtil.getDouble(expressionContext, "1.0d"));
        _jsp_TestTag_65.setMyString(ExpressionUtil.getString(expressionContext, "Hello"));
        int _jsp_flag_65 = _jsp_TestTag_65.doStartTag();

        if(_jsp_flag_65 == Tag.SKIP_PAGE){
            return;
        }

        do{
            _jsp_flag_65 = _jsp_TestTag_65.doAfterBody();
        }
        while(_jsp_flag_65 == IterationTag.EVAL_BODY_AGAIN);
        _jsp_TestTag_65.doEndTag();
        _jsp_TestTag_65.release();
        /* NODE END: lineNumber: 78, tagClassName: test.com.skin.ayada.taglib.TestTag, tagInstanceName: _jsp_TestTag_65 */

        /* TEXT: lineNumber: 78 */
        out.write("\r\n");

        /* NODE START: lineNumber: 79, offset: 67, length: 2, tagClassName: test.com.skin.ayada.taglib.TestTag, tagInstanceName: _jsp_TestTag_68 */
        /* <app:test myLong="1L" myInt="-1.0" myFloat="1.0F" myDouble="1.0D" myString="Hello"/> */
        test.com.skin.ayada.taglib.TestTag _jsp_TestTag_68 = new test.com.skin.ayada.taglib.TestTag();
        _jsp_TestTag_68.setParent((Tag)null);
        _jsp_TestTag_68.setPageContext(pageContext);
        _jsp_TestTag_68.setMyLong(ExpressionUtil.getLong(expressionContext, "1L"));
        _jsp_TestTag_68.setMyInt(ExpressionUtil.getInteger(expressionContext, "-1.0"));
        _jsp_TestTag_68.setMyFloat(ExpressionUtil.getFloat(expressionContext, "1.0F"));
        _jsp_TestTag_68.setMyDouble(ExpressionUtil.getDouble(expressionContext, "1.0D"));
        _jsp_TestTag_68.setMyString(ExpressionUtil.getString(expressionContext, "Hello"));
        int _jsp_flag_68 = _jsp_TestTag_68.doStartTag();

        if(_jsp_flag_68 == Tag.SKIP_PAGE){
            return;
        }

        do{
            _jsp_flag_68 = _jsp_TestTag_68.doAfterBody();
        }
        while(_jsp_flag_68 == IterationTag.EVAL_BODY_AGAIN);
        _jsp_TestTag_68.doEndTag();
        _jsp_TestTag_68.release();
        /* NODE END: lineNumber: 79, tagClassName: test.com.skin.ayada.taglib.TestTag, tagInstanceName: _jsp_TestTag_68 */

        /* TEXT: lineNumber: 79 */
        out.write("\r\n");

        /* NODE START: lineNumber: 80, offset: 70, length: 2, tagClassName: test.com.skin.ayada.taglib.TestTag, tagInstanceName: _jsp_TestTag_71 */
        /* <app:test myLong="1e3" myInt="-1.0" myFloat="1.0" myDouble="1e3" myString="Hello"/> */
        test.com.skin.ayada.taglib.TestTag _jsp_TestTag_71 = new test.com.skin.ayada.taglib.TestTag();
        _jsp_TestTag_71.setParent((Tag)null);
        _jsp_TestTag_71.setPageContext(pageContext);
        _jsp_TestTag_71.setMyLong(ExpressionUtil.getLong(expressionContext, "1e3"));
        _jsp_TestTag_71.setMyInt(ExpressionUtil.getInteger(expressionContext, "-1.0"));
        _jsp_TestTag_71.setMyFloat(ExpressionUtil.getFloat(expressionContext, "1.0"));
        _jsp_TestTag_71.setMyDouble(ExpressionUtil.getDouble(expressionContext, "1e3"));
        _jsp_TestTag_71.setMyString(ExpressionUtil.getString(expressionContext, "Hello"));
        int _jsp_flag_71 = _jsp_TestTag_71.doStartTag();

        if(_jsp_flag_71 == Tag.SKIP_PAGE){
            return;
        }

        do{
            _jsp_flag_71 = _jsp_TestTag_71.doAfterBody();
        }
        while(_jsp_flag_71 == IterationTag.EVAL_BODY_AGAIN);
        _jsp_TestTag_71.doEndTag();
        _jsp_TestTag_71.release();
        /* NODE END: lineNumber: 80, tagClassName: test.com.skin.ayada.taglib.TestTag, tagInstanceName: _jsp_TestTag_71 */

        /* TEXT: lineNumber: 80 */
        out.write("\r\n");

        /* NODE START: lineNumber: 81, offset: 73, length: 2, tagClassName: test.com.skin.ayada.taglib.TestTag, tagInstanceName: _jsp_TestTag_74 */
        /* <app:test myLong="1.2e3" myInt="-1.0" myFloat="1.0" myDouble="1.2e3" myString="Hello"/> */
        test.com.skin.ayada.taglib.TestTag _jsp_TestTag_74 = new test.com.skin.ayada.taglib.TestTag();
        _jsp_TestTag_74.setParent((Tag)null);
        _jsp_TestTag_74.setPageContext(pageContext);
        _jsp_TestTag_74.setMyLong(ExpressionUtil.getLong(expressionContext, "1.2e3"));
        _jsp_TestTag_74.setMyInt(ExpressionUtil.getInteger(expressionContext, "-1.0"));
        _jsp_TestTag_74.setMyFloat(ExpressionUtil.getFloat(expressionContext, "1.0"));
        _jsp_TestTag_74.setMyDouble(ExpressionUtil.getDouble(expressionContext, "1.2e3"));
        _jsp_TestTag_74.setMyString(ExpressionUtil.getString(expressionContext, "Hello"));
        int _jsp_flag_74 = _jsp_TestTag_74.doStartTag();

        if(_jsp_flag_74 == Tag.SKIP_PAGE){
            return;
        }

        do{
            _jsp_flag_74 = _jsp_TestTag_74.doAfterBody();
        }
        while(_jsp_flag_74 == IterationTag.EVAL_BODY_AGAIN);
        _jsp_TestTag_74.doEndTag();
        _jsp_TestTag_74.release();
        /* NODE END: lineNumber: 81, tagClassName: test.com.skin.ayada.taglib.TestTag, tagInstanceName: _jsp_TestTag_74 */

        /* TEXT: lineNumber: 81 */
        out.write("\r\n\r\n");

        /* NODE START: lineNumber: 83, offset: 76, length: 2, tagClassName: test.com.skin.ayada.taglib.ScrollPage, tagInstanceName: _jsp_ScrollPage_77 */
        /* <app:scrollpage count="254" pageSize="10" pageNum="2"/> */
        test.com.skin.ayada.taglib.ScrollPage _jsp_ScrollPage_77 = new test.com.skin.ayada.taglib.ScrollPage();
        _jsp_ScrollPage_77.setParent((Tag)null);
        _jsp_ScrollPage_77.setPageContext(pageContext);
        _jsp_ScrollPage_77.setCount(ExpressionUtil.getInteger(expressionContext, "254"));
        _jsp_ScrollPage_77.setPageSize(ExpressionUtil.getInteger(expressionContext, "10"));
        _jsp_ScrollPage_77.setPageNum(ExpressionUtil.getInteger(expressionContext, "2"));
        int _jsp_flag_77 = _jsp_ScrollPage_77.doStartTag();

        if(_jsp_flag_77 == Tag.SKIP_PAGE){
            return;
        }

        do{
            _jsp_flag_77 = _jsp_ScrollPage_77.doAfterBody();
        }
        while(_jsp_flag_77 == IterationTag.EVAL_BODY_AGAIN);
        _jsp_ScrollPage_77.doEndTag();
        _jsp_ScrollPage_77.release();
        /* NODE END: lineNumber: 83, tagClassName: test.com.skin.ayada.taglib.ScrollPage, tagInstanceName: _jsp_ScrollPage_77 */

        /* TEXT: lineNumber: 83 */
        out.write("\r\n</body>\r\n</html>");
        out.flush();
        jspWriter.flush();
    }
}
