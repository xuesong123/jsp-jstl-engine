/*
 * $RCSfile: TemplateContextTest.java,v $$
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
import java.util.HashMap;

import com.skin.ayada.runtime.DefaultExpressionFactory;
import com.skin.ayada.runtime.ExpressionFactory;
import com.skin.ayada.source.DefaultSourceFactory;
import com.skin.ayada.source.SourceFactory;
import com.skin.ayada.template.DefaultTemplateContext;
import com.skin.ayada.template.Template;
import com.skin.ayada.template.TemplateContext;
import com.skin.ayada.template.TemplateFactory;

/**
 * <p>Title: TemplateContextTest</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class TemplateContextTest {
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String sourcePattern = "jsp,jspx";
        String home = "E:\\WorkSpace\\test\\webapp\\template";
        SourceFactory sourceFactory = new DefaultSourceFactory();
        TemplateFactory templateFactory = new TemplateFactory();
        ExpressionFactory expressionFactory = new DefaultExpressionFactory();
        sourceFactory.setHome(home);
        sourceFactory.setSourcePattern(sourcePattern);

        TemplateContext templateContext = new DefaultTemplateContext(home, "UTF-8");
        templateContext.setSourceFactory(sourceFactory);
        templateContext.setTemplateFactory(templateFactory);
        templateContext.setExpressionFactory(expressionFactory);

        StringWriter writer = new StringWriter();
        Template template = templateContext.getTemplate("/test.jsp");
        templateContext.execute(template, new HashMap<String, Object>(), writer);
        System.out.println(writer.toString());
    }
}
