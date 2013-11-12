/*
 * $RCSfile: TemplateTest.java,v $$
 * $Revision: 1.1  $
 * $Date: 2013-2-19  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.template;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.com.skin.ayada.handler.UserHandler;
import test.com.skin.ayada.model.User;

import com.skin.ayada.compile.TemplateCompiler;
import com.skin.ayada.jstl.TagLibrary;
import com.skin.ayada.jstl.TagLibraryFactory;
import com.skin.ayada.runtime.JspFactory;
import com.skin.ayada.runtime.JspWriter;
import com.skin.ayada.runtime.PageContext;
import com.skin.ayada.source.ClassPathSourceFactory;
import com.skin.ayada.source.DefaultSourceFactory;
import com.skin.ayada.source.MemorySourceFactory;
import com.skin.ayada.source.Source;
import com.skin.ayada.source.SourceFactory;
import com.skin.ayada.template.DefaultExecutor;
import com.skin.ayada.template.Template;
import com.skin.ayada.template.TemplateContext;
import com.skin.ayada.util.ExpressionUtil;
import com.skin.ayada.util.MemMonitor;
import com.skin.ayada.util.TemplateUtil;

/**
 * <p>Title: TemplateTest</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class TemplateTest
{
    protected static final Logger logger = LoggerFactory.getLogger(TemplateTest.class);

    public static void main(String[] args)
    {
        // System.out.println(Object.class.isAssignableFrom(String.class));
        // compareTest(new Object(), "test");
        // compareTest("test", new Object());

        // classPathTest();

        // test1();
        // test("webapp", "/includeTest.jsp");
        test("webapp", "/whenTest.jsp");
        // test("webapp", "/emptyTest.jsp");
        // test("webapp", "/jspTagTest.jsp");
        // test("webapp", "/eachTest.jsp");
        // test("webapp", "/stacktrace.jsp");
        // test("E:\\WorkSpace\\fmbak\\webapps\\template", "/category.jsp");
        // noFileTest();

        /*
        boolean b = TemplateConfig.getInstance().getBoolean("ayada.compile.ignore-jsptag");
        System.out.println(b);
        System.out.println(TemplateConfig.getInstance().getString("ayada.compile.ignore-jsptag"));
        */
    }

    public static void test(String home, String file)
    {
        TemplateContext templateContext = new TemplateContext(home);
        Template template = templateContext.getTemplate(file);
        StringWriter writer = new StringWriter();
        PageContext pageContext = getPageContext(writer);
        DefaultExecutor.execute(template, pageContext);
        System.out.println("-------------- source result --------------");
        System.out.println(TemplateUtil.toString(template));
        System.out.println("-------------- run result --------------");
        System.out.println(writer.toString());
    }

    public static void test(String home, String file, int count)
    {
        TemplateContext templateContext = new TemplateContext(home);
        Template template = templateContext.getTemplate(file);
        StringWriter writer = new StringWriter();
        PageContext pageContext = getPageContext(writer);
        OutputStream outputStream = null;

        try
        {
            DefaultExecutor.execute(template, pageContext);

            outputStream = new FileOutputStream("D:\\mem.log");
            PrintWriter out = new PrintWriter(outputStream);
            MemMonitor memMonitor = new MemMonitor();

            for(int i = 0; i < count; i++)
            {
                writer = new StringWriter();
                pageContext.setOut(new JspWriter(writer));
                DefaultExecutor.execute(template, pageContext);
                memMonitor.test(out, (i == 0), true);
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(outputStream != null)
            {
                try
                {
                    outputStream.close();
                }
                catch(IOException e)
                {
                }
            }
        }

        // System.out.println("-------------- run result --------------");
        // System.out.println(writer.toString());
    }

    public static PageContext getPageContext(Writer out)
    {
        User user = new User();
        user.setUserId(1L);
        user.setUserName("xuesong.net");
        user.setAge(1);

        List<User> userList = UserHandler.getUserList(16);
        PageContext pageContext = JspFactory.getPageContext(out);
        pageContext.setAttribute("user", user);
        pageContext.setAttribute("userList", userList);
        return pageContext;
    }

    public static void test1()
    {
        SourceFactory sourceFactory = new DefaultSourceFactory("webapp");
        TagLibrary tagLibrary = TagLibraryFactory.getStandardTagLibrary();
        TemplateCompiler compiler = new TemplateCompiler(sourceFactory);
        compiler.setTagLibrary(tagLibrary);

        long t1 = System.currentTimeMillis();
        Template template = compiler.compile("/large.html", "UTF-8");
        long t2 = System.currentTimeMillis();
        System.out.println("compile time: " + (t2 - t1));

        long t3 = System.currentTimeMillis();
        StringWriter writer = new StringWriter();
        PageContext pageContext = getPageContext(writer);
        DefaultExecutor.execute(template, pageContext);

        long t4 = System.currentTimeMillis();
        System.out.println("run time: " + (t4 - t3));
        System.out.println("------------- result -------------");
        // System.out.println(writer.toString());
    }

    public static void test2()
    {
        StringWriter writer = new StringWriter();
        PageContext pageContext = JspFactory.getPageContext(writer);

        User user = new User();
        user.setUserId(1);
        user.setUserName("test1");

        List<User> userList = UserHandler.getUserList(16);
        pageContext.setAttribute("user", user);
        pageContext.setAttribute("userList", userList);

        Object result = ExpressionUtil.evaluate(pageContext.getExpressionContext(), "a${user.userName}#");
        System.out.println(result.getClass().getName() + ": " + result);
    }

    public static void test3()
    {
        TemplateContext templateContext = new TemplateContext("webapp");
        Template template = templateContext.getTemplate("/user/userList.tml");
        StringWriter writer = new StringWriter();
        PageContext pageContext = JspFactory.getPageContext(templateContext, writer);
        List<User> userList = UserHandler.getUserList(16);
        pageContext.setAttribute("userList", userList);
        DefaultExecutor.execute(template, pageContext);  
        System.out.println(writer.toString()); 
    }

    public static void noFileTest()
    {
        Source source = new Source("", "<c:out value=\"123\"/>", 0);
        SourceFactory sourceFactory = new MemorySourceFactory(source);
        TagLibrary tagLibrary = TagLibraryFactory.getStandardTagLibrary();
        TemplateCompiler compiler = new TemplateCompiler(sourceFactory);
        compiler.setTagLibrary(tagLibrary);
        Template template = compiler.compile("", "UTF-8");
        StringWriter writer = new StringWriter();

        TemplateContext templateContext = new TemplateContext("");
        PageContext pageContext = JspFactory.getPageContext(templateContext, writer);
        List<User> userList = UserHandler.getUserList(16);
        pageContext.setAttribute("userList", userList);
        DefaultExecutor.execute(template, pageContext);  
        System.out.println(writer.toString()); 
    }

    public static void classPathTest()
    {
        String home = "com/skin/ayada/demo";
        SourceFactory sourceFactory = new ClassPathSourceFactory(home);
        TemplateContext templateContext = new TemplateContext(home);
        templateContext.setSourceFactory(sourceFactory);

        Template template = templateContext.getTemplate("/hello.jsp");
        StringWriter writer = new StringWriter();
        PageContext pageContext = JspFactory.getPageContext(writer);

        System.out.println("-------------- source result --------------");
        System.out.println(TemplateUtil.toString(template));

        System.out.println("-------------- System.out.print --------------");
        DefaultExecutor.execute(template, pageContext);

        System.out.println("-------------- run result --------------");
        System.out.println(writer.toString());
    }

    public static void compareTest(Object v1, Object v2)
    {
        System.out.println((v1 instanceof Comparable<?>));
        Class<?> t1 = v1.getClass();
        Class<?> t2 = v2.getClass();

        if (t1.isAssignableFrom(t2))
        {
            System.out.println("compareable");
        }
        else
        {
            System.out.println("error");
        }
    }
}
