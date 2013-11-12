/*
 * $RCSfile: AllTagTest.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-8 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package jsp;

import java.io.IOException;

import com.skin.ayada.runtime.ExpressionContext;
import com.skin.ayada.runtime.JspWriter;
import com.skin.ayada.runtime.PageContext;
import com.skin.ayada.tagext.BodyContent;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.IterationTag;
import com.skin.ayada.template.TemplateHandler;
import com.skin.ayada.util.ExpressionUtil;

public class AllTagTest extends TemplateHandler
{
    /**
     * @param pageContext
     * @throws IOException
     */
    public void execute(PageContext pageContext) throws java.io.IOException
    {
        int EVAL_BODY_AGAIN = IterationTag.EVAL_BODY_AGAIN;
        JspWriter out = pageContext.getOut();
        ExpressionContext expressionContext = pageContext.getExpressionContext();


        /* NODE START: lineNumber: 4, offset: 0, length: 2, tagClassName: com.skin.ayada.jstl.core.ImportTag, tagInstanceName: _tag_instance_1 */
        /* <t:import name="app:scrollpage" className="test.com.skin.ayada.taglib.ScrollPage"/> */
        pageContext.getTagLibrary().setup("app:scrollpage", "test.com.skin.ayada.taglib.ScrollPage");
        /* jsp.jstl.core.ImportTag END */
        /* NODE END: lineNumber: 4, tagClassName: com.skin.ayada.jstl.core.ImportTag, tagInstanceName: _tag_instance_1 */

        /* TEXT: lineNumber: 5 */
        out.write("<html>\r\n<head>\r\n<title>test</title>\r\n</head>\r\n<body version=\"1.0\">\r\n\r\n\r\n\r\n");

        /* NODE START: lineNumber: 23, offset: 3, length: 2, tagClassName: com.skin.ayada.jstl.core.SetTag, tagInstanceName: _tag_instance_4 */
        /* <c:set var="myVar" value="Hello, Ayada!"/> */
        pageContext.setAttribute("myVar", ExpressionUtil.evaluate(expressionContext, "Hello, Ayada!"));
        /* jsp.jstl.core.SetTag END */
        /* NODE END: lineNumber: 23, tagClassName: com.skin.ayada.jstl.core.SetTag, tagInstanceName: _tag_instance_4 */

        /* TEXT: lineNumber: 23 */
        out.write("\r\n<h1>");
        /* EXPRESSION: lineNumber: 24 */
        out.write(expressionContext.getString("myVar"));
        /* TEXT: lineNumber: 24 */
        out.write("</h1>\r\n\r\n");

        /* NODE START: lineNumber: 26, offset: 8, length: 2, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _tag_instance_9 */
        /* <c:out value="c:out: Hello, Ayada!"/> */
        out.write(ExpressionUtil.getString(expressionContext, "c:out: Hello, Ayada!"));
        /* jsp.jstl.core.OutTag END */
        /* NODE END: lineNumber: 26, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _tag_instance_9 */

        /* TEXT: lineNumber: 26 */
        out.write("\r\n<div style=\"background-color: #c0c0c0;\"></div>\r\n");

        /* NODE START: lineNumber: 28, offset: 11, length: 2, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _tag_instance_12 */
        /* <c:out escapeXml="true" value="<div>Hello Ayada!</div>"/> */
        out.write(ExpressionUtil.getHtml(expressionContext, "<div>Hello Ayada!</div>"));
        /* jsp.jstl.core.OutTag END */
        /* NODE END: lineNumber: 28, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _tag_instance_12 */

        /* TEXT: lineNumber: 28 */
        out.write("\r\n");

        /* NODE START: lineNumber: 29, offset: 14, length: 3, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _tag_instance_15 */
        /* <c:out escapeXml="true">...</c:out> */
        out = pageContext.pushBody();
        /* TEXT: lineNumber: 29 */
        out.write("<h1>Hello Ayada!</h1>");
        pageContext.printBodyContent((BodyContent)out, true);
        out = pageContext.popBody();
        /* jsp.jstl.core.OutTag END */
        /* NODE END: lineNumber: 29, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _tag_instance_15 */

        /* TEXT: lineNumber: 29 */
        out.write("\r\n\r\n");

        /* NODE START: lineNumber: 31, offset: 18, length: 3, tagClassName: com.skin.ayada.jstl.core.IfTag, tagInstanceName: _tag_instance_19 */
        /* <c:if test="${1 == 1}">...</c:if> */
        if(ExpressionUtil.getBoolean(expressionContext, "${1 == 1}")){
        /* TEXT: lineNumber: 31 */
        out.write("c:if test");
        } /* jsp.jstl.core.IfTag END */
        /* NODE END: lineNumber: 31, tagClassName: com.skin.ayada.jstl.core.IfTag, tagInstanceName: _tag_instance_19 */

        /* TEXT: lineNumber: 31 */
        out.write("\r\n\r\n");

        /* NODE START: lineNumber: 33, offset: 22, length: 5, tagClassName: com.skin.ayada.jstl.core.ForEachTag, tagInstanceName: _tag_instance_23 */
        /* <c:forEach items="${userList}" var="user">...</c:forEach> */
        com.skin.ayada.jstl.core.ForEachTag _tag_instance_23 = new com.skin.ayada.jstl.core.ForEachTag();

        _tag_instance_23.setParent((Tag)null);
        _tag_instance_23.setPageContext(pageContext);
        _tag_instance_23.setItems(ExpressionUtil.evaluate(expressionContext, "${userList}"));
        _tag_instance_23.doStartTag();
        while(_tag_instance_23.hasNext()){
            pageContext.setAttribute("user", _tag_instance_23.next());
        /* TEXT: lineNumber: 33 */
        out.write("\r\nuser: ");
        /* EXPRESSION: lineNumber: 34 */
        out.write(expressionContext.getString("user.userName"));
        /* TEXT: lineNumber: 34 */
        out.write("\r\n");
        }
        _tag_instance_23.release();
        /* jsp.jstl.core.ForEachTag END */
        /* NODE END: lineNumber: 33, tagClassName: com.skin.ayada.jstl.core.ForEachTag, tagInstanceName: _tag_instance_23 */

        /* TEXT: lineNumber: 35 */
        out.write("\r\n\r\n");

        /* NODE START: lineNumber: 37, offset: 28, length: 14, tagClassName: com.skin.ayada.jstl.core.ChooseTag, tagInstanceName: _tag_instance_29 */
        /* <c:choose>...</c:choose> */
        com.skin.ayada.jstl.core.ChooseTag _tag_instance_29 = new com.skin.ayada.jstl.core.ChooseTag();
        _tag_instance_29.setParent((Tag)null);
        _tag_instance_29.setPageContext(pageContext);


        /* NODE START: lineNumber: 38, offset: 29, length: 3, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _tag_instance_30 */
        /* <c:when test="${1 == 1}">...</c:when> */
        if(_tag_instance_29.complete() == false && ExpressionUtil.getBoolean(expressionContext, "${1 == 1}")){
            _tag_instance_29.finish();
        /* TEXT: lineNumber: 38 */
        out.write("1");
        } /* jsp.jstl.core.WhenTag END */
        /* NODE END: lineNumber: 38, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _tag_instance_30 */


        /* NODE START: lineNumber: 39, offset: 32, length: 3, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _tag_instance_33 */
        /* <c:when test="${2 == 2}">...</c:when> */
        if(_tag_instance_29.complete() == false && ExpressionUtil.getBoolean(expressionContext, "${2 == 2}")){
            _tag_instance_29.finish();
        /* TEXT: lineNumber: 39 */
        out.write("2");
        } /* jsp.jstl.core.WhenTag END */
        /* NODE END: lineNumber: 39, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _tag_instance_33 */


        /* NODE START: lineNumber: 40, offset: 35, length: 3, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _tag_instance_36 */
        /* <c:when test="${3 == 3}">...</c:when> */
        if(_tag_instance_29.complete() == false && ExpressionUtil.getBoolean(expressionContext, "${3 == 3}")){
            _tag_instance_29.finish();
        /* TEXT: lineNumber: 40 */
        out.write("3");
        } /* jsp.jstl.core.WhenTag END */
        /* NODE END: lineNumber: 40, tagClassName: com.skin.ayada.jstl.core.WhenTag, tagInstanceName: _tag_instance_36 */


        /* NODE START: lineNumber: 41, offset: 38, length: 3, tagClassName: com.skin.ayada.jstl.core.OtherwiseTag, tagInstanceName: _tag_instance_39 */
        /* <c:otherwise>...</c:otherwise> */
        if(_tag_instance_29.complete() == false){
            _tag_instance_29.finish();
        /* TEXT: lineNumber: 41 */
        out.write("otherwise");
        } /* jsp.jstl.core.OtherwiseTag END */
        /* NODE END: lineNumber: 41, tagClassName: com.skin.ayada.jstl.core.OtherwiseTag, tagInstanceName: _tag_instance_39 */

        _tag_instance_29.release();
        /* jsp.jstl.core.ChooseTag END */
        /* NODE END: lineNumber: 37, tagClassName: com.skin.ayada.jstl.core.ChooseTag, tagInstanceName: _tag_instance_29 */

        /* TEXT: lineNumber: 42 */
        out.write("\r\n\r\n\r\n");

        /* NODE START: lineNumber: 45, offset: 43, length: 3, tagClassName: test.com.skin.ayada.taglib.ScrollPage, tagInstanceName: _tag_instance_44 */
        /* <app:scrollpage count="254" pageSize="10" pageNum="2">...</app:scrollpage> */
        test.com.skin.ayada.taglib.ScrollPage _tag_instance_44 = new test.com.skin.ayada.taglib.ScrollPage();
        _tag_instance_44.setParent((Tag)null);
        _tag_instance_44.setPageContext(pageContext);
_tag_instance_44.setCount(ExpressionUtil.getInteger(expressionContext, "254"));
_tag_instance_44.setPageSize(ExpressionUtil.getInteger(expressionContext, "10"));
_tag_instance_44.setPageNum(ExpressionUtil.getInteger(expressionContext, "2"));
        int _tag_flag_44 = _tag_instance_44.doStartTag();

        if(_tag_flag_44 == Tag.SKIP_PAGE){
            return;
        }
        while(true){
        /* TEXT: lineNumber: 45 */
        out.write("haha");
            _tag_flag_44 = _tag_instance_44.doAfterBody();
            if(_tag_flag_44 != EVAL_BODY_AGAIN){
                break;
            }
        }
        _tag_instance_44.doEndTag();
        _tag_instance_44.release();
        /* NODE END: lineNumber: 45, tagClassName: test.com.skin.ayada.taglib.ScrollPage, tagInstanceName: _tag_instance_44 */

        /* TEXT: lineNumber: 45 */
        out.write("\r\n</body>\r\n</html>");
        out.flush();
    }
}
