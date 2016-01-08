/*
 * $RCSfile: PrepareCompilerTest.java,v $$
 * $Revision: 1.1 $
 * $Date: 2016-1-8 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.template;

import java.io.File;

import com.skin.ayada.template.JspTemplateFactory;
import com.skin.ayada.template.PrepareCompiler;
import com.skin.ayada.template.TemplateContext;
import com.skin.ayada.template.TemplateManager;
import com.skin.ayada.util.ClassPath;

/**
 * <p>Title: PrepareCompilerTest</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class PrepareCompilerTest {
    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            File home = new File("webapp");
            TemplateContext templateContext = TemplateManager.getTemplateContext(home.getCanonicalPath(), true);
            JspTemplateFactory jspTemplateFactory = new JspTemplateFactory();
            String classPath = ClassPath.getClassPath();
            System.out.println("CLASS_PATH: " + classPath);
            jspTemplateFactory.setWork(new File("work").getAbsolutePath());
            jspTemplateFactory.setClassPath(classPath);
            jspTemplateFactory.setIgnoreJspTag(false);
    
            templateContext.setTemplateFactory(jspTemplateFactory);
            templateContext.getTemplateFactory().setIgnoreJspTag(false);
            templateContext.getSourceFactory().setSourcePattern("*");

            templateContext.getSourceFactory().setSourcePattern("jsp");
            PrepareCompiler prepareCompiler = new PrepareCompiler(templateContext);
            prepareCompiler.exclude(new String[]{"/resource/**", "/include/**", "/WEB-INF/**"});

            long t1 = System.currentTimeMillis();
            prepareCompiler.compile();
            long t2 = System.currentTimeMillis();
            System.out.println("compile complete: " + (t2 - t1));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
