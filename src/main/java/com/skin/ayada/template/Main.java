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

import com.skin.ayada.config.TemplateConfig;
import com.skin.ayada.runtime.JspFactory;
import com.skin.ayada.runtime.PageContext;
import com.skin.ayada.util.TemplateUtil;

/**
 * <p>Title: Main</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class Main
{
    public static void main(String[] args)
    {
        String path = null;
        String encoding = "UTF-8";
        String templateFactoryClassName = null;
        System.out.println("args: " + Main.getArguments(args));

        if(args == null || args.length < 1)
        {
            usage();
        }
        else
        {
            path = args[0];

            if(args.length > 1)
            {
                encoding = args[1];
            }

            if(args.length > 2)
            {
                templateFactoryClassName = args[2];
            }

            try
            {
                execute(path, encoding, templateFactoryClassName);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void execute(String path, String encoding, String templateFactoryClassName) throws Exception
    {
        File file = new File(path);

        if(file.exists() == false)
        {
            throw new IOException(path + " not exists!");
        }

        if(file.isFile() == false)
        {
            throw new IOException(path + " not exists!");
        }

        TemplateConfig config = TemplateConfig.getInstance();
        config.setValue("ayada.compile.source-pattern", "*");

        File parent = file.getParentFile();
        TemplateContext templateContext = TemplateManager.getTemplateContext(parent.getCanonicalPath(), true);
        TemplateFactory templateFactory = Main.getTemplateFactory(templateFactoryClassName, "work");

        if(templateFactory != null)
        {
            templateContext.setTemplateFactory(templateFactory);
        }

        templateContext.getTemplateFactory().setIgnoreJspTag(false);
        templateContext.getSourceFactory().setSourcePattern("*");

        PrintWriter printWriter = new PrintWriter(System.out);
        PageContext pageContext = JspFactory.getPageContext(templateContext, printWriter);
        Template template = templateContext.getTemplate(file.getName());

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
    public static TemplateFactory getTemplateFactory(String templateFactoryClassName, String jspWork)
    {
        if(templateFactoryClassName == null)
        {
            return null;
        }

        if(templateFactoryClassName.trim().length() < 1)
        {
            return null;
        }

        TemplateFactory templateFactory = null;

        try
        {
            templateFactory = TemplateFactory.getTemplateFactory(templateFactoryClassName.trim());

            if(templateFactory instanceof JspTemplateFactory)
            {
                if(jspWork == null)
                {
                    jspWork = "work";
                }

                String classPath = Main.getClassPath();
                System.out.println("CLASS_PATH: " + classPath);
                JspTemplateFactory jspTemplateFactory = (JspTemplateFactory)templateFactory;
                File work = new File(jspWork);
                jspTemplateFactory.setWork(work.getAbsolutePath());
                jspTemplateFactory.setClassPath(classPath);
                jspTemplateFactory.setIgnoreJspTag(false);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return templateFactory;
    }

    /**
     * @return String
     */
    public static String getClassPath()
    {
        String seperator = ";";
        StringBuilder buffer = new StringBuilder();
        File lib = new File("lib");

        if(System.getProperty("os.name").indexOf("Windows") < 0)
        {
            seperator = ":";
        }

        if(lib.exists())
        {
            File[] files = lib.listFiles();

            if(files != null && files.length > 0)
            {
                for(File file : files)
                {
                    buffer.append(file.getAbsolutePath());
                    buffer.append(seperator);
                }
            }
        }

        File clazz = new File("build/classes");

        if(clazz.exists() && clazz.isDirectory())
        {
            buffer.append(clazz.getAbsolutePath());
            buffer.append(seperator);
        }

        if(buffer.length() > 0)
        {
            buffer.delete(buffer.length() - seperator.length(), buffer.length());
        }

        return buffer.toString();
    }

    /**
     * @param args
     * @return String
     */
    public static String getArguments(String[] args)
    {
        StringBuilder buffer = new StringBuilder();

        if(args != null && args.length > 0)
        {
            for(int i = 0; i < args.length; i++)
            {
                buffer.append("\"");
                buffer.append(args[i]);
                buffer.append("\" ");
            }

            if(buffer.length() > 0)
            {
                buffer.deleteCharAt(buffer.length() - 1);
            }
        }

        return buffer.toString();
    }

    public static void usage()
    {
        System.out.println("Usage:");
        System.out.println("    " + Main.class.getName() + " FILE [ENCODING]");
    }
}
