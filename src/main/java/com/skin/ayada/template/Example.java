/*
 * $RCSfile: Example.java,v $$
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

import com.skin.ayada.runtime.PageContext;
import com.skin.ayada.util.TemplateUtil;

/**
 * <p>Title: Example</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class Example
{
	public static void main(String[] args)
	{
        String path = null;
        String encoding = "UTF-8";

        if(args == null || args.length < 1)
        {
            System.out.println("Usage:");
            System.out.println("    " + Main.class.getName() + " FILE [ENCODING]");
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

        File parent = file.getParentFile();
        TemplateContext templateContext = TemplateManager.getTemplateContext(parent.getCanonicalPath(), true);

        PageContext pageContext = templateContext.getPageContext(new PrintWriter(System.out));
        Template template = templateContext.getTemplate(file.getName());

        System.out.println("===================== " + template.getClass().getName() + " =====================");
        TemplateUtil.print(template);

        System.out.println("===================== result =====================");
        template.execute(pageContext);
	}
}
