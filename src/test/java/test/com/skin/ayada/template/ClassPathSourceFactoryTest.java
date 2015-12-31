/*
 * $RCSfile: ClassPathSourceFactoryTest.java,v $$
 * $Revision: 1.1 $
 * $Date: 2014-12-7 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.template;

import java.io.StringWriter;

import com.skin.ayada.runtime.DefaultExpressionFactory;
import com.skin.ayada.runtime.ExpressionFactory;
import com.skin.ayada.source.ClassPathSourceFactory;
import com.skin.ayada.template.DefaultTemplateContext;
import com.skin.ayada.template.Template;
import com.skin.ayada.template.TemplateContext;
import com.skin.ayada.template.TemplateFactory;
import com.skin.ayada.template.TemplateManager;
import com.skin.ayada.util.TemplateUtil;

/**
 * <p>Title: ClassPathSourceFactoryTest</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class ClassPathSourceFactoryTest {
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        ClassPathSourceFactory sourceFactory = new ClassPathSourceFactory("/");
        System.out.println("-------------------------------------------");

        StringWriter stringWriter = new StringWriter();
        TemplateFactory templateFactory = new TemplateFactory();
        TemplateContext templateContext = new DefaultTemplateContext("classPath:/template", "UTF-8");
        ExpressionFactory expressionFactory = new DefaultExpressionFactory();
        templateContext.setSourceFactory(sourceFactory);
        templateContext.setTemplateFactory(templateFactory);
        templateContext.setExpressionFactory(expressionFactory);
        TemplateManager.add(templateContext);

        templateContext.setSourceFactory(sourceFactory);
        templateContext.execute("/com/skin/ayada/compile/class.jsp", null, stringWriter);
        Template template = templateContext.getTemplate("/com/skin/ayada/compile/class.jsp", "UTF-8");
        TemplateUtil.print(template);
        System.out.println("result: " + stringWriter.toString());
    }
}
