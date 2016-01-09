/*
 * $RCSfile: SqlParserTest1Template.java,v $$
 * $Revision: 1.1 $
 * $Date: 2016-01-09 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 *
 * home: E:/WorkSpace/ayada/webapp
 * path: /sqlParserTest1.jsp
 * lastModified: 2014-07-17 22:12:54 290
 * options: -fastJstl true
 * JSP generated by JspCompiler-1.0.1.2 (built 2016-01-09 14:41:29 660)
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
 * <p>Title: SqlParserTest1Template</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author JspCompiler
 * @version 1.0
 */
@SuppressWarnings("unused")
public class SqlParserTest1Template extends JspTemplate {
    public static void main(String[] args){
        java.io.StringWriter writer = new java.io.StringWriter();
        PageContext pageContext = com.skin.ayada.runtime.JspFactory.getPageContext(writer);
        SqlParserTest1Template template = new SqlParserTest1Template();

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
        // NODE START: lineNumber: 1, offset: 0, length: 3, tagClassName: com.skin.ayada.jstl.sql.CreateParseTag, tagInstanceName: _jsp_CreateParseTag_1
        // <sql:parse name=\"myTable\">...</sql:parse>
        com.skin.ayada.jstl.sql.CreateParseTag _jsp_CreateParseTag_1 = new com.skin.ayada.jstl.sql.CreateParseTag();
        _jsp_CreateParseTag_1.setPageContext(pageContext);
        _jsp_CreateParseTag_1.setParent((Tag)null);
        _jsp_CreateParseTag_1.setName("myTable");
        int _jsp_start_flag_1 = _jsp_CreateParseTag_1.doStartTag();

        if(_jsp_start_flag_1 == Tag.SKIP_PAGE) {
            return;
        }
        if(_jsp_start_flag_1 != Tag.SKIP_BODY) {
            int _jsp_flag_1 = 0;
            if(_jsp_start_flag_1 == BodyTag.EVAL_BODY_BUFFERED) {
                BodyContent _jsp_body_content_1 = pageContext.pushBody();
                _jsp_CreateParseTag_1.setBodyContent(_jsp_body_content_1);
                _jsp_CreateParseTag_1.doInitBody();
                out = _jsp_body_content_1;
            }

            do {
                // TEXT: lineNumber: 1
                // out.write("\r\ncreate table `my_table`(\r\n    `f1`      bigint(20) unsigned not null comment \'����\',\r\n    `f2`      datetime not null comment \'ע��\',\r\n    `f3`      datetime not null comment \'ע��\',\r\n    primary key (f1)\r\n);\r\n");
                out.write(_jsp_string_2, 0, _jsp_string_2.length);
                _jsp_flag_1 = _jsp_CreateParseTag_1.doAfterBody();
            }
            while(_jsp_flag_1 == IterationTag.EVAL_BODY_AGAIN);
            if(_jsp_start_flag_1 == BodyTag.EVAL_BODY_BUFFERED) {
                out = pageContext.popBody();
            }
        }
        _jsp_CreateParseTag_1.doEndTag();
        _jsp_CreateParseTag_1.release();
        // NODE END: lineNumber: 1, tagClassName: com.skin.ayada.jstl.sql.CreateParseTag, tagInstanceName: _jsp_CreateParseTag_1

        // TEXT: lineNumber: 8
        // out.write("\r\n<p>table: ");
        out.write(_jsp_string_4, 0, _jsp_string_4.length);
        // EXPRESSION: lineNumber: 9
        expressionContext.print(out, expressionContext.getString("myTable.tableName"));
        // TEXT: lineNumber: 9
        // out.write("</p>\r\n");
        out.write(_jsp_string_6, 0, _jsp_string_6.length);

        // NODE START: lineNumber: 10, offset: 6, length: 2, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_7
        // <c:out value=\"${myTable.getCreateString(&#39;`%s`&#39;)}\"/>
        com.skin.ayada.jstl.core.OutTag.write(out, ExpressionUtil.evaluate(expressionContext, "${myTable.getCreateString(\'`%s`\')}", null), false);
        // out.print(ExpressionUtil.getString(expressionContext, "${myTable.getCreateString(\'`%s`\')}"));
        // jsp.jstl.core.OutTag END
        // NODE END: lineNumber: 10, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_7

        // TEXT: lineNumber: 10
        // out.write("\r\n");
        out.write(_jsp_string_9, 0, _jsp_string_9.length);

        // NODE START: lineNumber: 11, offset: 9, length: 2, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_10
        // <c:out value=\"${myTable.getQueryString()}\"/>
        com.skin.ayada.jstl.core.OutTag.write(out, ExpressionUtil.evaluate(expressionContext, "${myTable.getQueryString()}", null), false);
        // out.print(ExpressionUtil.getString(expressionContext, "${myTable.getQueryString()}"));
        // jsp.jstl.core.OutTag END
        // NODE END: lineNumber: 11, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_10

        // TEXT: lineNumber: 11
        // out.write("\r\n");
        out.write(_jsp_string_12, 0, _jsp_string_12.length);

        // NODE START: lineNumber: 12, offset: 12, length: 2, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_13
        // <c:out value=\"${myTable.getInsertString()}\"/>
        com.skin.ayada.jstl.core.OutTag.write(out, ExpressionUtil.evaluate(expressionContext, "${myTable.getInsertString()}", null), false);
        // out.print(ExpressionUtil.getString(expressionContext, "${myTable.getInsertString()}"));
        // jsp.jstl.core.OutTag END
        // NODE END: lineNumber: 12, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_13

        // TEXT: lineNumber: 12
        // out.write("\r\n");
        out.write(_jsp_string_15, 0, _jsp_string_15.length);

        // NODE START: lineNumber: 13, offset: 15, length: 2, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_16
        // <c:out value=\"${myTable.getUpdateString()}\"/>
        com.skin.ayada.jstl.core.OutTag.write(out, ExpressionUtil.evaluate(expressionContext, "${myTable.getUpdateString()}", null), false);
        // out.print(ExpressionUtil.getString(expressionContext, "${myTable.getUpdateString()}"));
        // jsp.jstl.core.OutTag END
        // NODE END: lineNumber: 13, tagClassName: com.skin.ayada.jstl.core.OutTag, tagInstanceName: _jsp_OutTag_16

        // TEXT: lineNumber: 13
        // out.write("\r\n");
        out.write(_jsp_string_18, 0, _jsp_string_18.length);

        out.flush();
        jspWriter.flush();
    }

    public static final char[] _jsp_string_2 = "\r\ncreate table `my_table`(\r\n    `f1`      bigint(20) unsigned not null comment \'����\',\r\n    `f2`      datetime not null comment \'ע��\',\r\n    `f3`      datetime not null comment \'ע��\',\r\n    primary key (f1)\r\n);\r\n".toCharArray();
    public static final char[] _jsp_string_4 = "\r\n<p>table: ".toCharArray();
    public static final char[] _jsp_string_6 = "</p>\r\n".toCharArray();
    public static final char[] _jsp_string_9 = "\r\n".toCharArray();
    public static final char[] _jsp_string_12 = "\r\n".toCharArray();
    public static final char[] _jsp_string_15 = "\r\n".toCharArray();
    public static final char[] _jsp_string_18 = "\r\n".toCharArray();

}
