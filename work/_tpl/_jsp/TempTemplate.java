/*
 * $RCSfile: TempTemplate.java,v $$
 * $Revision: 1.1 $
 * $Date: 2016-01-09 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 *
 * home: E:/WorkSpace/ayada/webapp
 * path: /temp.jsp
 * lastModified: 2014-06-17 15:30:38 000
 * options: -fastJstl true
 * JSP generated by JspCompiler-1.0.1.2 (built 2016-01-09 14:41:30 420)
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
 * <p>Title: TempTemplate</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author JspCompiler
 * @version 1.0
 */
@SuppressWarnings("unused")
public class TempTemplate extends JspTemplate {
    public static void main(String[] args){
        java.io.StringWriter writer = new java.io.StringWriter();
        PageContext pageContext = com.skin.ayada.runtime.JspFactory.getPageContext(writer);
        TempTemplate template = new TempTemplate();

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
        // NODE START: lineNumber: 1, offset: 0, length: 2, tagClassName: com.skin.ayada.jstl.core.SetTag, tagInstanceName: _jsp_SetTag_1
        // <c:set var=\"myString\" value=\"${StringUtil.replace(&#39;abc&#39;, &#39;b&#39;, &#39;-&#39;)}\"/>
        pageContext.setAttribute("myString", ExpressionUtil.evaluate(expressionContext, "${StringUtil.replace(\'abc\', \'b\', \'-\')}", null));
        // jsp.jstl.core.SetTag END
        // NODE END: lineNumber: 1, tagClassName: com.skin.ayada.jstl.core.SetTag, tagInstanceName: _jsp_SetTag_1

        // TEXT: lineNumber: 1
        // out.write("\r\n<p>myString: [");
        out.write(_jsp_string_3, 0, _jsp_string_3.length);

        // NODE START: lineNumber: 2, offset: 3, length: 2, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_4
        // <c:out value=\"${myString}\"/>
        com.skin.ayada.jstl.core.OutTag.write(out, pageContext.getAttribute("myString"), false);
        // out.print(ExpressionUtil.getString(expressionContext, "${myString}"));
        // jsp.jstl.core.OutTag END
        // NODE END: lineNumber: 2, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_4

        // TEXT: lineNumber: 2
        // out.write("]</p>\r\n\r\n");
        out.write(_jsp_string_6, 0, _jsp_string_6.length);

        // NODE START: lineNumber: 4, offset: 6, length: 2, tagClassName: com.skin.ayada.jstl.core.ListTag, tagInstanceName: _jsp_ListTag_7
        // <c:list name=\"userList\"/>
        com.skin.ayada.jstl.core.ListTag _jsp_ListTag_7 = new com.skin.ayada.jstl.core.ListTag();
        _jsp_ListTag_7.setPageContext(pageContext);
        _jsp_ListTag_7.setParent((Tag)null);
        _jsp_ListTag_7.setName("userList");
        int _jsp_start_flag_7 = _jsp_ListTag_7.doStartTag();

        if(_jsp_start_flag_7 == Tag.SKIP_PAGE) {
            return;
        }
        if(_jsp_start_flag_7 != Tag.SKIP_BODY) {
            _jsp_ListTag_7.doAfterBody();
        }
        _jsp_ListTag_7.doEndTag();
        _jsp_ListTag_7.release();
        // NODE END: lineNumber: 4, tagClassName: com.skin.ayada.jstl.core.ListTag, tagInstanceName: _jsp_ListTag_7

        // TEXT: lineNumber: 4
        // out.write("\r\n\r\n");
        out.write(_jsp_string_9, 0, _jsp_string_9.length);

        // NODE START: lineNumber: 6, offset: 9, length: 13, tagClassName: com.skin.ayada.jstl.core.ForEachTag, tagInstanceName: _jsp_ForEachTag_10
        // <c:forEach items=\"1, 2, 3, 4, 5\" step=\"2\" var=\"num\">...</c:forEach>
        Object _jsp_old_var_10 = pageContext.getAttribute("num");
        com.skin.ayada.jstl.core.ForEachTag _jsp_ForEachTag_10 = new com.skin.ayada.jstl.core.ForEachTag();
        _jsp_ForEachTag_10.setParent((Tag)null);
        _jsp_ForEachTag_10.setPageContext(pageContext);
        _jsp_ForEachTag_10.setVar("num");
        _jsp_ForEachTag_10.setItems("1, 2, 3, 4, 5");
        _jsp_ForEachTag_10.setStep(2);
        if(_jsp_ForEachTag_10.doStartTag() != Tag.SKIP_BODY) {
            while(true) {
                // TEXT: lineNumber: 6
                // out.write("\r\n    ");
                out.write(_jsp_string_11, 0, _jsp_string_11.length);

                // NODE START: lineNumber: 7, offset: 11, length: 6, tagClassName: com.skin.ayada.jstl.core.MapTag, tagInstanceName: _jsp_MapTag_12
                // <c:map name=\"user\">...</c:map>
                com.skin.ayada.jstl.core.MapTag _jsp_MapTag_12 = new com.skin.ayada.jstl.core.MapTag();
                _jsp_MapTag_12.setPageContext(pageContext);
                _jsp_MapTag_12.setParent((Tag)null);
                _jsp_MapTag_12.setDynamicAttribute("name", "user");
                int _jsp_start_flag_12 = _jsp_MapTag_12.doStartTag();

                if(_jsp_start_flag_12 == Tag.SKIP_PAGE) {
                    return;
                }
                if(_jsp_start_flag_12 != Tag.SKIP_BODY) {
                    int _jsp_flag_12 = 0;

                    do {
                        // NODE START: lineNumber: 8, offset: 12, length: 2, tagClassName: com.skin.ayada.jstl.core.AttributeTag, tagInstanceName: _jsp_AttributeTag_13
                        // <c:entry name=\"userName\" value=\"test_${num}\"/>
                        _jsp_MapTag_12.setAttribute("userName", ExpressionUtil.evaluate(expressionContext, "test_${num}", null));
                        // jsp.jstl.core.AttributeTag END
                        // NODE END: lineNumber: 8, tagClassName: com.skin.ayada.jstl.core.AttributeTag, tagInstanceName: _jsp_AttributeTag_13

                        // NODE START: lineNumber: 9, offset: 14, length: 2, tagClassName: com.skin.ayada.jstl.core.AttributeTag, tagInstanceName: _jsp_AttributeTag_15
                        // <c:entry name=\"nickName\" value=\"test_${num}\"/>
                        _jsp_MapTag_12.setAttribute("nickName", ExpressionUtil.evaluate(expressionContext, "test_${num}", null));
                        // jsp.jstl.core.AttributeTag END
                        // NODE END: lineNumber: 9, tagClassName: com.skin.ayada.jstl.core.AttributeTag, tagInstanceName: _jsp_AttributeTag_15

                        _jsp_flag_12 = _jsp_MapTag_12.doAfterBody();
                    }
                    while(_jsp_flag_12 == IterationTag.EVAL_BODY_AGAIN);
                }
                _jsp_MapTag_12.doEndTag();
                _jsp_MapTag_12.release();
                // NODE END: lineNumber: 7, tagClassName: com.skin.ayada.jstl.core.MapTag, tagInstanceName: _jsp_MapTag_12

                // TEXT: lineNumber: 10
                // out.write("\r\n    ");
                out.write(_jsp_string_18, 0, _jsp_string_18.length);

                // NODE START: lineNumber: 11, offset: 18, length: 2, tagClassName: com.skin.ayada.jstl.core.ExecuteTag, tagInstanceName: _jsp_ExecuteTag_19
                // <c:execute value=\"${userList.add(user)}\"/>
                ExpressionUtil.evaluate(expressionContext, "${userList.add(user)}", null);
                // jsp.jstl.core.ExecuteTag END
                // NODE END: lineNumber: 11, tagClassName: com.skin.ayada.jstl.core.ExecuteTag, tagInstanceName: _jsp_ExecuteTag_19

                // TEXT: lineNumber: 11
                // out.write("\r\n");
                out.write(_jsp_string_21, 0, _jsp_string_21.length);
                if(_jsp_ForEachTag_10.doAfterBody() != IterationTag.EVAL_BODY_AGAIN) {
                    break;
                }
            }
        }
        _jsp_ForEachTag_10.release();
        pageContext.setAttribute("num", _jsp_old_var_10);
        // jsp.jstl.core.ForEachTag END
        // NODE END: lineNumber: 6, tagClassName: com.skin.ayada.jstl.core.ForEachTag, tagInstanceName: _jsp_ForEachTag_10

        // TEXT: lineNumber: 12
        // out.write("\r\nuserList2: ");
        out.write(_jsp_string_23, 0, _jsp_string_23.length);

        // NODE START: lineNumber: 13, offset: 23, length: 2, tagClassName: com.skin.ayada.jstl.core.PrintTag, tagInstanceName: _jsp_PrintTag_24
        // <c:print value=\"${userList}\"/>
        com.skin.ayada.jstl.core.PrintTag.print(pageContext, null, pageContext.getAttribute("userList"));
        // jsp.jstl.core.PrintTag END
        // NODE END: lineNumber: 13, tagClassName: com.skin.ayada.jstl.core.PrintTag, tagInstanceName: _jsp_PrintTag_24


        out.flush();
        jspWriter.flush();
    }

    public static final char[] _jsp_string_3 = "\r\n<p>myString: [".toCharArray();
    public static final char[] _jsp_string_6 = "]</p>\r\n\r\n".toCharArray();
    public static final char[] _jsp_string_9 = "\r\n\r\n".toCharArray();
    public static final char[] _jsp_string_11 = "\r\n    ".toCharArray();
    public static final char[] _jsp_string_18 = "\r\n    ".toCharArray();
    public static final char[] _jsp_string_21 = "\r\n".toCharArray();
    public static final char[] _jsp_string_23 = "\r\nuserList2: ".toCharArray();

}
