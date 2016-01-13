/*
 * $RCSfile: StaticTemplate.java,v $$
 * $Revision: 1.1 $
 * $Date: 2016-01-12 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 *
 * home: E:/WorkSpace/ayada/webapp
 * path: /test1/static.jsp
 * lastModified: 2013-11-04 12:25:44 000
 * options: -fastJstl true
 * JSP generated by JspCompiler-1.0.1.2 (built 2016-01-12 23:30:10 328)
 */
package _tpl._jsp._test1;

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
 * <p>Title: StaticTemplate</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author JspCompiler
 * @version 1.0
 */
@SuppressWarnings("unused")
public class StaticTemplate extends JspTemplate {
    public static void main(String[] args){
        java.io.StringWriter writer = new java.io.StringWriter();
        PageContext pageContext = com.skin.ayada.runtime.JspFactory.getPageContext(writer);
        StaticTemplate template = new StaticTemplate();

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
        // NODE START: lineNumber: 1, offset: 0, length: 3, tagClassName: com.skin.ayada.jstl.core.ForEachTag, tagInstanceName: _jsp_ForEachTag_1
        // <c:forEach items=\"1, 2, 3\" var=\"myi\">...</c:forEach>
        Object _jsp_old_var_1 = pageContext.getAttribute("myi");
        com.skin.ayada.jstl.core.ForEachTag _jsp_ForEachTag_1 = new com.skin.ayada.jstl.core.ForEachTag();
        _jsp_ForEachTag_1.setParent((Tag)null);
        _jsp_ForEachTag_1.setPageContext(pageContext);
        _jsp_ForEachTag_1.setVar("myi");
        _jsp_ForEachTag_1.setItems("1, 2, 3");
        if(_jsp_ForEachTag_1.doStartTag() != Tag.SKIP_BODY) {
            while(true) {
                // VARIABLE: lineNumber: 1
                expressionContext.print(out, pageContext.getAttribute("myi"));
                if(_jsp_ForEachTag_1.doAfterBody() != IterationTag.EVAL_BODY_AGAIN) {
                    break;
                }
            }
        }
        _jsp_ForEachTag_1.release();
        pageContext.setAttribute("myi", _jsp_old_var_1);
        // jsp.jstl.core.ForEachTag END
        // NODE END: lineNumber: 1, tagClassName: com.skin.ayada.jstl.core.ForEachTag, tagInstanceName: _jsp_ForEachTag_1


        out.flush();
        jspWriter.flush();
    }


}