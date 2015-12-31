/*
 * $RCSfile: DemoTest.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-04 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package example.demo;

import java.io.StringWriter;

import com.skin.ayada.runtime.DefaultExpressionFactory;
import com.skin.ayada.runtime.ExpressionFactory;
import com.skin.ayada.runtime.PageContext;
import com.skin.ayada.source.ClassPathSourceFactory;
import com.skin.ayada.source.SourceFactory;
import com.skin.ayada.template.DefaultTemplateContext;
import com.skin.ayada.template.Template;
import com.skin.ayada.template.TemplateContext;
import com.skin.ayada.template.TemplateFactory;
import com.skin.ayada.template.TemplateManager;
import com.skin.ayada.util.TemplateUtil;

/**
 * <p>Title: DemoTest</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class DemoTest {
    /**
     * @param args
     */
    public static void main(String[] args) {
        classPathTest();
    }

    public static void classPathTest() {
        try {
            String home = "example/demo";
            SourceFactory sourceFactory = new ClassPathSourceFactory(home);
            TemplateFactory templateFactory = new TemplateFactory();
            ExpressionFactory expressionFactory = new DefaultExpressionFactory();

            TemplateContext templateContext = new DefaultTemplateContext(home);
            templateContext.setSourceFactory(sourceFactory);
            templateContext.setTemplateFactory(templateFactory);
            templateContext.setExpressionFactory(expressionFactory);

            TemplateManager.add(templateContext);

            Template template = templateContext.getTemplate("/hello.jsp");
            StringWriter writer = new StringWriter();
            PageContext pageContext = templateContext.getPageContext(writer);

            System.out.println("-------------- source --------------");
            System.out.println(TemplateUtil.toString(template));
            System.out.println("-------------- System.out.println --------------");
            template.execute(pageContext);

            System.out.println("-------------- result --------------");
            System.out.println(writer.toString());
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
