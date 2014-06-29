/*
 * $RCSfile: TemplateTest.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-19 $
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
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.com.skin.ayada.handler.UserHandler;
import test.com.skin.ayada.model.User;

import com.skin.ayada.compile.TemplateCompiler;
import com.skin.ayada.jstl.TagLibrary;
import com.skin.ayada.jstl.TagLibraryFactory;
import com.skin.ayada.runtime.DefaultExpressionFactory;
import com.skin.ayada.runtime.ExpressionFactory;
import com.skin.ayada.runtime.JspFactory;
import com.skin.ayada.runtime.PageContext;
import com.skin.ayada.source.ClassPathSourceFactory;
import com.skin.ayada.source.DefaultSourceFactory;
import com.skin.ayada.source.MemorySourceFactory;
import com.skin.ayada.source.Source;
import com.skin.ayada.source.SourceFactory;
import com.skin.ayada.template.DefaultTemplateContext;
import com.skin.ayada.template.Template;
import com.skin.ayada.template.TemplateContext;
import com.skin.ayada.template.TemplateFactory;
import com.skin.ayada.template.TemplateManager;
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
        // test("webapp", "/whenTest.jsp");
        // test("webapp", "/emptyTest.jsp");
        // test("webapp", "/jspTagTest.jsp");
        // test("webapp", "/eachTest.jsp");
        // test("webapp", "/stacktrace.jsp");
        // test("E:\\WorkSpace\\fmbak\\webapps\\template", "/category.jsp");
        // noFileTest();
        // test3();
        // test("webapp", "/whenTest.jsp", 100);
        stringTest();

        /*
        boolean b = TemplateConfig.getInstance().getBoolean("ayada.compile.ignore-jsptag");
        System.out.println(b);
        System.out.println(TemplateConfig.getInstance().getString("ayada.compile.ignore-jsptag"));
        */
    }

    public static void test(String home, String file)
    {
        try
        {
            SourceFactory sourceFactory = new DefaultSourceFactory("webapp");
            TemplateFactory templateFactory = new TemplateFactory();
            ExpressionFactory expressionFactory = new DefaultExpressionFactory();

            TemplateContext templateContext = new DefaultTemplateContext(home);
            templateContext.setSourceFactory(sourceFactory);
            templateContext.setTemplateFactory(templateFactory);
            templateContext.setExpressionFactory(expressionFactory);

            Template template = templateContext.getTemplate(file);
            StringWriter writer = new StringWriter();
            PageContext pageContext = templateContext.getPageContext(writer);
            template.execute(pageContext);
            System.out.println("-------------- source result --------------");
            System.out.println(TemplateUtil.toString(template));
            System.out.println("-------------- run result --------------");
            System.out.println(writer.toString());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @param home
     * @param file
     * @param count
     */
    public static void test(String home, String file, int count)
    {
        OutputStream outputStream = null;

        try
        {
            TemplateContext templateContext = TemplateManager.create(home, 0);

            // warmup
            templateContext.execute(file, new HashMap<String, Object>(), new StringWriter());
            System.out.println("test...");

            long t1 = System.currentTimeMillis();
            outputStream = new FileOutputStream("D:\\mem.log");
            PrintWriter out = new PrintWriter(outputStream);
            MemMonitor memMonitor = new MemMonitor();

            for(int i = 0; i < count; i++)
            {
                StringWriter writer = new StringWriter();
                templateContext.execute(file, new HashMap<String, Object>(), writer);
                memMonitor.test(out, (i == 0), true);
            }

            long t2 = System.currentTimeMillis();
            System.out.println("time: " + (t2 - t1));
        }
        catch(Exception e)
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
    }

    public static void setTestData(PageContext pageContext)
    {
        User user = new User();
        user.setUserId(1L);
        user.setUserName("xuesong.net");
        user.setAge(1);

        List<User> userList = UserHandler.getUserList(16);
        pageContext.setAttribute("user", user);
        pageContext.setAttribute("userList", userList);
    }

    public static void test1() throws Exception
    {
        TemplateContext templateContext = TemplateManager.getTemplateContext("webapp");

        long t1 = System.currentTimeMillis();
        Template template = templateContext.getTemplate("/large.html");
        long t2 = System.currentTimeMillis();
        System.out.println("compile time: " + (t2 - t1));

        long t3 = System.currentTimeMillis();
        StringWriter writer = new StringWriter();
        PageContext pageContext = templateContext.getPageContext(writer);

        try
        {
            template.execute(pageContext);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        long t4 = System.currentTimeMillis();
        System.out.println("run time: " + (t4 - t3));
        System.out.println("------------- result -------------");
        // System.out.println(writer.toString());
    }

    public static void test2()
    {
        StringWriter writer = new StringWriter();
        TemplateContext templateContext = TemplateManager.getTemplateContext("webapp");
        PageContext pageContext = templateContext.getPageContext(writer);

        User user = new User();
        user.setUserId(1);
        user.setUserName("test1");

        List<User> userList = UserHandler.getUserList(16);
        pageContext.setAttribute("user", user);
        pageContext.setAttribute("userList", userList);

        Object result = ExpressionUtil.evaluate(pageContext.getExpressionContext(), "a${user.userName}#", Object.class);
        System.out.println(result.getClass().getName() + ": " + result);
    }

    public static void test3()
    {
        try
        {
            StringWriter writer = new StringWriter();
            TemplateContext templateContext = TemplateManager.getTemplateContext("webapp");

            Template template = templateContext.getTemplate("/user/userList.tml");
            PageContext pageContext = templateContext.getPageContext(writer);
            List<User> userList = UserHandler.getUserList(16);
            pageContext.setAttribute("userList", userList);
            template.execute(pageContext);
            System.out.println(writer.toString()); 
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void noFileTest()
    {
        Source source = new Source("webapp", "1.jsp", "<c:out value=\"123\"/>", Source.SCRIPT);
        SourceFactory sourceFactory = new MemorySourceFactory(source);
        TemplateFactory templateFactory = new TemplateFactory();
        ExpressionFactory expressionFactory = new DefaultExpressionFactory();

        TemplateContext templateContext = new DefaultTemplateContext("");
        templateContext.setSourceFactory(sourceFactory);
        templateContext.setTemplateFactory(templateFactory);
        templateContext.setExpressionFactory(expressionFactory);

        TagLibrary tagLibrary = TagLibraryFactory.getStandardTagLibrary();
        TemplateCompiler compiler = new TemplateCompiler(sourceFactory);
        compiler.setTagLibrary(tagLibrary);

        try
        {
            StringWriter writer = new StringWriter();
            Template template = templateContext.getTemplate("1.jsp");
            templateContext.getTemplate("1.jsp");

            PageContext pageContext = templateContext.getPageContext(writer);
            List<User> userList = UserHandler.getUserList(16);
            pageContext.setAttribute("userList", userList);
            template.execute(pageContext);
            System.out.println(writer.toString());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void classPathTest()
    {
        try
        {
            String home = "com/skin/ayada/demo";
            SourceFactory sourceFactory = new ClassPathSourceFactory(home);
            TemplateFactory templateFactory = new TemplateFactory();
            TemplateContext templateContext = new DefaultTemplateContext("");
            templateContext.setSourceFactory(sourceFactory);
            templateContext.setTemplateFactory(templateFactory);

            Template template = templateContext.getTemplate("/hello.jsp");
            StringWriter writer = new StringWriter();
            PageContext pageContext = templateContext.getPageContext(writer);

            System.out.println("-------------- source result --------------");
            System.out.println(TemplateUtil.toString(template));

            System.out.println("-------------- System.out.print --------------");
            template.execute(pageContext);

            System.out.println("-------------- run result --------------");
            System.out.println(writer.toString());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
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

    public static void stringTest()
    {
        try
        {
            TemplateFactory templateFactory = new TemplateFactory();
            Template template = templateFactory.compile("123<c:if test=\"${1 == 1}\">abc</c:if>xyz");

            StringWriter out = new StringWriter();
            PageContext pageContext = JspFactory.getDefaultPageContext(out);
            template.execute(pageContext);

            TemplateUtil.print(template);
            System.out.println(out.toString());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
