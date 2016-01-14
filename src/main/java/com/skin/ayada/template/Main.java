/*
 * $RCSfile: Main.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-19 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.template;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skin.ayada.config.TemplateConfig;
import com.skin.ayada.runtime.PageContext;
import com.skin.ayada.util.ClassPath;
import com.skin.ayada.util.TemplateUtil;

/**
 * <p>Title: Main</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        String path = null;
        String encoding = "UTF-8";
        String templateFactoryClassName = null;
        System.out.println("args: " + Main.getArguments(args));
        logger.info("args: {}", Main.getArguments(args));

        /*
        if(args == null || args.length < 1) {
            args = new String[]{"D:\\workspace2\\ayada\\webapp\\clipTest.jsp", "UTF-8"};
        }
        */

        if(args == null || args.length < 1) {
            usage();
        }
        else {
            path = args[0];

            if(args.length > 1 && args[1] != null && args[1].trim().length() > 0) {
                encoding = args[1].trim();
            }

            if(args.length > 2 && args[2] != null && args[2].trim().length() > 0) {
                templateFactoryClassName = args[2].trim();
            }

            try {
                System.out.println("path: " + path);
                System.out.println("encoding: " + encoding);
                System.out.println("templateFactoryClassName: " + templateFactoryClassName);
                execute(path, encoding, templateFactoryClassName);
            }
            catch(Exception e) {
                logger.warn(e.getMessage(), e);
            }
        }
    }

    /**
     * @param path
     * @param encoding
     * @param templateFactoryClassName
     * @throws Exception
     */
    public static void execute(String path, String encoding, String templateFactoryClassName) throws Exception {
        File file = new File(path);

        if(file.exists() == false) {
            throw new IOException(path + " not exists!");
        }

        if(file.isFile() == false) {
            throw new IOException(path + " not exists!");
        }

        TemplateConfig.setValue("ayada.compile.source-pattern", "*");

        File parent = file.getParentFile();
        TemplateContext templateContext = TemplateManager.getTemplateContext(parent.getCanonicalPath(), true);
        TemplateFactory templateFactory = Main.getTemplateFactory(templateFactoryClassName, "work");

        if(templateFactory != null) {
            templateContext.setTemplateFactory(templateFactory);
        }

        templateContext.getTemplateFactory().setIgnoreJspTag(false);
        templateContext.getSourceFactory().setSourcePattern("*");

        /*
        templateContext.getSourceFactory().setSourcePattern("jsp");
        PrepareCompiler prepareCompiler = new PrepareCompiler(templateContext);
        prepareCompiler.exclude(new String[]{"/resource/**", "/include/**", "/WEB-INF/**"});
        prepareCompiler.compile();
        */

        Map<String, Object> context = new HashMap<String, Object>();
        PrintWriter printWriter = new PrintWriter(System.out);
        PageContext pageContext = templateContext.getPageContext(context, printWriter);
        Template template = templateContext.getTemplate(file.getName(), encoding);

        System.out.println("===================== " + template.getClass().getName() + " =====================");
        TemplateUtil.print(template);

        System.out.println("===================== result =====================");
        long t3 = System.currentTimeMillis();
        template.execute(pageContext);
        long t4 = System.currentTimeMillis();
        System.out.println("run time: " + (t4 - t3));
    }

    /**
     * @param templateFactoryClassName
     * @param jspWork
     * @return TemplateFactory
     */
    public static TemplateFactory getTemplateFactory(String templateFactoryClassName, String jspWork) {
        if(templateFactoryClassName == null) {
            return null;
        }

        if(templateFactoryClassName.trim().length() < 1) {
            return null;
        }

        TemplateFactory templateFactory = null;

        try {
            templateFactory = TemplateFactory.getTemplateFactory(templateFactoryClassName.trim());

            if(templateFactory instanceof JspTemplateFactory) {
                File work = null;

                if(jspWork == null) {
                    work = new File("work");
                }
                else {
                    work = new File(jspWork);
                }

                String classPath = ClassPath.getClassPath();
                System.out.println("CLASS_PATH: " + classPath);
                JspTemplateFactory jspTemplateFactory = (JspTemplateFactory)templateFactory;
                jspTemplateFactory.setWork(work.getAbsolutePath());
                jspTemplateFactory.setClassPath(classPath);
                jspTemplateFactory.setIgnoreJspTag(false);
            }
        }
        catch(Exception e) {
            logger.warn(e.getMessage(), e);
        }
        return templateFactory;
    }

    /**
     * @param args
     * @return String
     */
    public static String getArguments(String[] args) {
        StringBuilder buffer = new StringBuilder();

        if(args != null && args.length > 0) {
            for(int i = 0; i < args.length; i++) {
                buffer.append("\"");
                buffer.append(args[i]);
                buffer.append("\" ");
            }

            if(buffer.length() > 0) {
                buffer.deleteCharAt(buffer.length() - 1);
            }
        }

        return buffer.toString();
    }

    public static void usage() {
        System.out.println("Usage:");
        System.out.println("    " + Main.class.getName() + " FILE [ENCODING]");
    }
}
