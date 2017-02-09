/*
 * $RCSfile: PerformanceTest.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-11-08 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.template;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.skin.ayada.runtime.PageContext;
import com.skin.ayada.template.JspTemplateFactory;
import com.skin.ayada.template.Template;
import com.skin.ayada.template.TemplateContext;
import com.skin.ayada.template.TemplateManager;
import com.skin.ayada.util.ClassPath;

/**
 * <p>Title: PerformanceTest</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class PerformanceTest {
    /**
     * 性能测试, 分别是ayada的解释模式, 编译模式(纯标签), 编译模式(纯java脚本), velocity, freemarker
     * 不同的机器上跑出来的结果不同, 以下是我的笔记本上某一次跑出来的结果.
     * 我的笔记本性能稍差, 所以测试结果普遍偏低.
     * 从多次测试的结果来看, ayada的解释模式性能略低于freemarker, 大概是velocity的2倍.
     * 编译模式要明显由于freemarker和velocity.
     * 
     * ayada默认对所有的el表达式和java表达式都做了html编码, 即便是纯java脚本也支持这个特性. 如果没有这个特性也许性能会更好一点.
     * 但是这个功能非常好用, 损失一点性能也是值得的.
     * 
     * 该测试依赖velocity和freemarker相关jar包, 请自行下载并添加到项目build path中
     * 
     * Run test4Interpret
     * warmed: 1000, run time: 881
     * count: 10000, run time: 4525
     * =======================================
     * Run test4Compile
     * warmed: 1000, run time: 257
     * count: 10000, run time: 3189
     * =======================================
     * Run test4Compile
     * warmed: 1000, run time: 252
     * count: 10000, run time: 2520
     * =======================================
     * Run test4Velocity
     * warmed: 1000, run time: 997
     * count: 10000, run time: 10002
     * =======================================
     * Run test4Freemarker
     * warmed: 1000, run time: 702
     * count: 10000, run time: 5045
     * =======================================
     * @param args
     */
    public static void main(String[] args) {
        int warmed = 1000;
        int count = 10000;
        File file = new File("webapp\\test5.jsp");
        test4Interpret(file, warmed, count, 100);
        test4Compile(file, warmed, count, 100);
        test4Compile(new File("webapp\\test6.jsp"), warmed, count, 100);
        test4Velocity(new File("webapp\\test5.vm"), warmed, count, 100);
        test4Freemarker(new File("webapp\\test5.ftl"), warmed, count, 100);
    }

    /**
     * @param file
     * @param warmed
     * @param count
     * @param dataSize
     */
    public static void test4Interpret(File file, int warmed, int count, int dataSize) {
        System.out.println("Run test4Interpret");

        try {
            TemplateContext templateContext = getTemplateContext(file);
            StringWriter stringWriter = new StringWriter();
            Map<String, Object> context = new HashMap<String, Object>();
            PageContext pageContext = templateContext.getPageContext(context, stringWriter);

            pageContext.setAttribute("name", "test");
            pageContext.setAttribute("border", "border=\"1\"");
            pageContext.setAttribute("data", getDataList(dataSize));
            Template template = templateContext.getTemplate(file.getName(), "utf-8");

            /**
             * warmed
             */
            long t1 = System.currentTimeMillis();
            for(int i = 0; i < warmed; i++) {
                stringWriter.getBuffer().setLength(0);
                template.execute(pageContext);
            }
            long t2 = System.currentTimeMillis();
            System.out.println("warmed: " + warmed + ", run time: " + (t2 - t1));

            long t3 = System.currentTimeMillis();
            for(int i = 0; i < count; i++) {
                stringWriter.getBuffer().setLength(0);
                template.execute(pageContext);
            }
            long t4 = System.currentTimeMillis();
            System.out.println("count: " + count + ", run time: " + (t4 - t3));
            System.out.println("=======================================");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param file
     * @param warmed
     * @param count
     * @param dataSize
     */
    public static void test4Compile(File file, int warmed, int count, int dataSize) {
        System.out.println("Run test4Compile");

        try {
            TemplateContext templateContext = getJspTemplateContext(file);
            StringWriter stringWriter = new StringWriter();
            Map<String, Object> context = new HashMap<String, Object>();
            PageContext pageContext = templateContext.getPageContext(context, stringWriter);
            pageContext.setAttribute("name", "test");
            pageContext.setAttribute("border", "border=\"1\"");
            pageContext.setAttribute("data", getDataList(dataSize));
            Template template = templateContext.getTemplate(file.getName(), "utf-8");

            /**
             * warmed
             */
            long t1 = System.currentTimeMillis();
            for(int i = 0; i < warmed; i++) {
                stringWriter.getBuffer().setLength(0);
                template.execute(pageContext);
            }
            long t2 = System.currentTimeMillis();
            System.out.println("warmed: " + warmed + ", run time: " + (t2 - t1));

            long t3 = System.currentTimeMillis();
            for(int i = 0; i < count; i++) {
                stringWriter.getBuffer().setLength(0);
                template.execute(pageContext);
            }
            long t4 = System.currentTimeMillis();
            System.out.println("count: " + count + ", run time: " + (t4 - t3));
            System.out.println("=======================================");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param file
     * @param warmed
     * @param count
     * @param dataSize
     */
    public static void test4Velocity(File file, int warmed, int count, int dataSize) {
        System.out.println("Run test4Velocity");
        java.util.Properties properties = new java.util.Properties();
        properties.setProperty(org.apache.velocity.runtime.RuntimeConstants.FILE_RESOURCE_LOADER_PATH, file.getParent());
        org.apache.velocity.app.VelocityEngine velocityEngine = new org.apache.velocity.app.VelocityEngine();
        velocityEngine.init(properties);

        StringWriter stringWriter = new StringWriter();
        org.apache.velocity.VelocityContext context = new org.apache.velocity.VelocityContext();
        context.put("name", "test");
        context.put("border", "border=\"1\"");
        context.put("data", getDataList(dataSize));
        org.apache.velocity.Template template = velocityEngine.getTemplate(file.getName(), "utf-8"); 

        long t1 = System.currentTimeMillis();
        for(int i = 0; i < warmed; i++) {
            stringWriter.getBuffer().setLength(0);
            template.merge(context, stringWriter);
        }
        long t2 = System.currentTimeMillis();
        System.out.println("warmed: " + warmed + ", run time: " + (t2 - t1));

        long t3 = System.currentTimeMillis();
        for(int i = 0; i < count; i++) {
            stringWriter.getBuffer().setLength(0);
            template.merge(context, stringWriter);
        }
        long t4 = System.currentTimeMillis();
        System.out.println("count: " + count + ", run time: " + (t4 - t3));
        System.out.println("=======================================");
    }
    
    /**
     * @param file
     * @param warmed
     * @param count
     * @param dataSize
     */
    public static void test4Freemarker(File file, int warmed, int count, int dataSize) {
        System.out.println("Run test4Freemarker");
        freemarker.cache.TemplateLoader templateLoader = null;

        try {
            templateLoader = new freemarker.cache.FileTemplateLoader(file.getParentFile());
        }
        catch (IOException e1) {
            e1.printStackTrace();
            return;
        }

        freemarker.template.Configuration configuration = new freemarker.template.Configuration();
        configuration.setDefaultEncoding("utf-8");
        configuration.setTemplateLoader(templateLoader);

        StringWriter stringWriter = new StringWriter();
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("name", "test");
        context.put("border", "border=\"1\"");
        context.put("data", getDataList(dataSize));
        freemarker.template.Template template = null;

        try {
            template = configuration.getTemplate(file.getName());
        }
        catch(Exception e) {
            e.printStackTrace();
            return;
        }

        try {
            long t1 = System.currentTimeMillis();
            for(int i = 0; i < warmed; i++) {
                stringWriter.getBuffer().setLength(0);
                template.process(context, stringWriter);
            }
            long t2 = System.currentTimeMillis();
            System.out.println("warmed: " + warmed + ", run time: " + (t2 - t1));
        }
        catch(Exception e) {  
            e.printStackTrace();  
        }

        try {
            long t3 = System.currentTimeMillis();
            for(int i = 0; i < count; i++) {
                stringWriter.getBuffer().setLength(0);
                template.process(context, stringWriter);
            }
            long t4 = System.currentTimeMillis();
            System.out.println("count: " + count + ", run time: " + (t4 - t3));
            System.out.println("=======================================");
        }
        catch(Exception e) {  
            e.printStackTrace();  
        }
    }

    /**
     * @param file
     * @return TemplateContext
     * @throws IOException
     */
    public static TemplateContext getTemplateContext(File file) throws IOException {
        File parent = file.getParentFile();
        TemplateContext templateContext = TemplateManager.create(parent.getCanonicalPath());
        templateContext.getSourceFactory().setSourcePattern("*");
        return templateContext;
    }

    /**
     * @param file
     * @return TemplateContext
     * @throws IOException
     */
    public static TemplateContext getJspTemplateContext(File file) throws IOException {
        File parent = file.getParentFile();
        TemplateContext templateContext = TemplateManager.create(parent.getCanonicalPath());
        JspTemplateFactory jspTemplateFactory = new JspTemplateFactory();
        String classPath = ClassPath.getClassPath();
        // System.out.println("CLASS_PATH: " + classPath);
        jspTemplateFactory.setWork(new File("work").getAbsolutePath());
        jspTemplateFactory.setClassPath(classPath);
        jspTemplateFactory.setIgnoreJspTag(false);

        templateContext.setTemplateFactory(jspTemplateFactory);
        templateContext.getTemplateFactory().setIgnoreJspTag(false);
        templateContext.getSourceFactory().setSourcePattern("*");
        return templateContext;
    }

    /**
     * @return List<Map<String, Object>>
     */
    public static List<Map<String, Object>> getUserList() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for(int i = 0; i < 100; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("code", "123");
            map.put("name", "123");
            map.put("date", "2016-01-01 08:00:00");
            map.put("value", 110.0f);
            list.add(map);
        }
        return list;
    }

    /**
     * @param rows
     * @return List<String>
     */
    public static List<String> getDataList(int rows) {
        List<String> list = new ArrayList<String>();

        for(int i = 0; i < rows; i++) {
            list.add(String.valueOf((char)((i % 26) + 'a')));
        }
        return list;
    }
}
