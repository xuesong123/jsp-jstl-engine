/*
 * $RCSfile: TestTemplate.java,v $$
 * $Revision: 1.1 $
 * $Date: 2016-01-09 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 *
 * home: E:/WorkSpace/ayada/webapp
 * path: /test.sql
 * lastModified: 2014-07-18 09:38:58 000
 * options: -fastJstl true
 * JSP generated by JspCompiler-1.0.1.2 (built 2016-01-09 14:41:30 862)
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
 * <p>Title: TestTemplate</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author JspCompiler
 * @version 1.0
 */
@SuppressWarnings("unused")
public class TestTemplate extends JspTemplate {
    public static void main(String[] args){
        java.io.StringWriter writer = new java.io.StringWriter();
        PageContext pageContext = com.skin.ayada.runtime.JspFactory.getPageContext(writer);
        TestTemplate template = new TestTemplate();

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
        // out.write("-- test\r\ncreate table IF NOT EXISTS `my_table1`(\r\n    `f1`      bigint(20) unsigned not null comment \'主键\',\r\n    `f2`      datetime not null comment \'注释\',\r\n    `f3`      datetime not null comment \'注释\',\r\n    primary key (f1)\r\n);\r\n\r\n-- test\r\ncreate table IF NOT EXISTS `my_table2`(\r\n    `f1`      bigint(20) unsigned not null comment \'主键\',\r\n    `f2`      datetime not null comment \'注释\',\r\n    `f3`      datetime not null comment \'注释\',\r\n    primary key (f1)\r\n);\r\n\r\n-- test\r\ncreate table IF NOT EXISTS `my_table3`(\r\n    `f1`      bigint(20) unsigned not null comment \'主键\',\r\n    `f2`      datetime not null comment \'注释\',\r\n    `f3`      datetime not null comment \'注释\',\r\n    primary key (f1)\r\n);");
        out.write(_jsp_string_1, 0, _jsp_string_1.length);

        out.flush();
        jspWriter.flush();
    }

    public static final char[] _jsp_string_1 = "-- test\r\ncreate table IF NOT EXISTS `my_table1`(\r\n    `f1`      bigint(20) unsigned not null comment \'主键\',\r\n    `f2`      datetime not null comment \'注释\',\r\n    `f3`      datetime not null comment \'注释\',\r\n    primary key (f1)\r\n);\r\n\r\n-- test\r\ncreate table IF NOT EXISTS `my_table2`(\r\n    `f1`      bigint(20) unsigned not null comment \'主键\',\r\n    `f2`      datetime not null comment \'注释\',\r\n    `f3`      datetime not null comment \'注释\',\r\n    primary key (f1)\r\n);\r\n\r\n-- test\r\ncreate table IF NOT EXISTS `my_table3`(\r\n    `f1`      bigint(20) unsigned not null comment \'主键\',\r\n    `f2`      datetime not null comment \'注释\',\r\n    `f3`      datetime not null comment \'注释\',\r\n    primary key (f1)\r\n);".toCharArray();

}
