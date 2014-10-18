/*
 * $RCSfile: XmlTemplateParserTest.java,v $$
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
import java.io.StringReader;

import com.skin.ayada.compile.XmlCompiler;
import com.skin.ayada.compile.XmlTemplateParser;
import com.skin.ayada.template.Template;
import com.skin.ayada.util.IO;

/**
 * <p>Title: XmlTemplateParserTest</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class XmlTemplateParserTest
{
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        test1();
    }

    /**
     * @param args
     */
    public static void test1()
    {
        try
        {
            String source = IO.read(new File("webapp\\allTagTest.xml"), "UTF-8", 4096);
            StringReader stringReader = new StringReader(source);
            XmlTemplateParser xmlTemplateParser = new XmlTemplateParser();
            Template template = xmlTemplateParser.parse(stringReader);
            XmlCompiler xmlCompiler = new XmlCompiler();
            String result = xmlCompiler.compile(template);

            try
            {
                IO.write(new File("webapp\\allTagTest2.xml"), result.getBytes("UTF-8"));
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
