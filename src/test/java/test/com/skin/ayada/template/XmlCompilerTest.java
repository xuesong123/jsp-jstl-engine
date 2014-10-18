/*
 * $RCSfile: XmlCompilerTest.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-18 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.template;

import java.io.File;
import java.io.IOException;

import com.skin.ayada.compile.XmlCompiler;
import com.skin.ayada.source.DefaultSourceFactory;
import com.skin.ayada.source.SourceFactory;
import com.skin.ayada.template.Template;
import com.skin.ayada.template.TemplateFactory;
import com.skin.ayada.util.IO;

/**
 * <p>Title: XmlCompilerTest</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class XmlCompilerTest
{
    public static void main(String[] args)
    {
        try
        {
            test1();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void test1() throws Exception
    {
        SourceFactory sourceFactory = new DefaultSourceFactory("webapp");
        TemplateFactory templateFactory = new TemplateFactory();
        templateFactory.setIgnoreJspTag(false);

        Template template = templateFactory.create(sourceFactory, "allTagTest.jsp", "UTF-8");
        XmlCompiler xmlCompiler = new XmlCompiler();
        String result = xmlCompiler.compile(template);

        try
        {
            IO.write(new File("webapp\\allTagTest.xml"), result.getBytes("UTF-8"));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        System.out.println(result);
    }
}
