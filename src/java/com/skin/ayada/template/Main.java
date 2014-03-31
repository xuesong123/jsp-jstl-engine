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
import java.io.StringWriter;

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

            try
            {
                execute(path, encoding);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void execute(String path, String encoding) throws Exception
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
        TemplateContext templateContext = TemplateManager.getTemplateContext(parent.getAbsolutePath(), true);

        StringWriter stringWriter = new StringWriter();
        /* CompactWriter compactWriter = new CompactWriter(stringWriter); */
        PageContext pageContext = JspFactory.getPageContext(templateContext, stringWriter);
        Template template = templateContext.getTemplate(file.getName());

        long t3 = System.currentTimeMillis();
        template.execute(pageContext);
        long t4 = System.currentTimeMillis();

        if(pageContext.getAttribute("template_print") != null)
        {
            System.out.println("===================== template =====================");
            TemplateUtil.print(template);
        }

        System.out.println("run time: " + (t4 - t3));
        System.out.println("===================== result =====================");
        System.out.println(stringWriter.toString());
    }

    public static void usage()
    {
        System.out.println("Usage:");
        System.out.println("    " + Main.class.getName() + " FILE [ENCODING]");
    }
}
