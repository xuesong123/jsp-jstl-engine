package test.com.skin.ayada.template;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
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
 * @author xuesong.net
 * @version 1.0
 */
public class PerformanceTest {
    /**
     * @param args
     */
    public static void main(String[] args) {
        // System.out.println(TagFactoryManager.getSetMethodCode(IfTag.class));
        // test4Ayada(new File("webapp\\test4.jsp"), 10000);
        // test4Java(10000);
        int warmed = 100;
        int count = 10000;
        File file = new File("webapp\\test5.jsp");

        test4Interpret(file, warmed, count);
        System.out.println("==========================");
        test4Compile(file, warmed, count);
    }

    /**
     *
     */
    public static void test3() {
        try {
            File file = new File("webapp\\test3.jsp");
            TemplateContext templateContext = getTemplateContext(file);

            StringWriter stringWriter = new StringWriter();
            Map<String, Object> context = new HashMap<String, Object>();
            PageContext pageContext = templateContext.getPageContext(context, stringWriter);
            pageContext.setAttribute("userList", getUserList());
            Template template = templateContext.getTemplate(file.getName(), "utf-8");

            /**
             *
             */
            template.execute(pageContext);
            System.out.println(stringWriter.toString());
            stringWriter.getBuffer().setLength(0);

            /**
             * warmed
             */
            for(int i = 0; i < 100; i++) {
                template.execute(pageContext);
                stringWriter.getBuffer().setLength(0);
            }

            int count = 50000;
            long t1 = System.currentTimeMillis();
            for(int i = 0; i < count; i++) {
                template.execute(pageContext);
                stringWriter.getBuffer().setLength(0);
            }
            long t2 = System.currentTimeMillis();
            System.out.println("count: " + count + ", run time: " + (t2 - t1));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param file
     * @param warmed
     * @param count
     */
    public static void test4Compile(File file, int warmed, int count) {
        try {
            TemplateContext templateContext = getJspTemplateContext(file);
            StringWriter stringWriter = new StringWriter();
            Map<String, Object> context = new HashMap<String, Object>();
            PageContext pageContext = templateContext.getPageContext(context, stringWriter);
            pageContext.setAttribute("name", "test");
            pageContext.setAttribute("border", "border=\"1\"");
            pageContext.setAttribute("data", getDataList());
            Template template = templateContext.getTemplate(file.getName(), "utf-8");
            System.out.println("template: " + template.getClass().getName());

            /**
             * warmed
             */
            for(int i = 0; i < warmed; i++) {
                template.execute(pageContext);
                stringWriter.getBuffer().setLength(0);
            }

            long t1 = System.currentTimeMillis();
            for(int i = 0; i < count; i++) {
                template.execute(pageContext);
                stringWriter.getBuffer().setLength(0);
            }
            long t2 = System.currentTimeMillis();
            System.out.println("count: " + count + ", run time: " + (t2 - t1));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param file
     * @param warmed
     * @param count
     */
    public static void test4Interpret(File file, int warmed, int count) {
        try {
            TemplateContext templateContext = getTemplateContext(file);
            StringWriter stringWriter = new StringWriter();
            Map<String, Object> context = new HashMap<String, Object>();
            PageContext pageContext = templateContext.getPageContext(context, stringWriter);

            pageContext.setAttribute("name", "test");
            pageContext.setAttribute("border", "border=\"1\"");
            pageContext.setAttribute("data", getDataList());
            Template template = templateContext.getTemplate(file.getName(), "utf-8");
            System.out.println("template: " + template.getClass().getName());

            /**
             * warmed
             */
            for(int i = 0; i < warmed; i++) {
                template.execute(pageContext);
                stringWriter.getBuffer().setLength(0);
            }

            long t1 = System.currentTimeMillis();
            for(int i = 0; i < count; i++) {
                template.execute(pageContext);
                stringWriter.getBuffer().setLength(0);
            }
            long t2 = System.currentTimeMillis();
            System.out.println("count: " + count + ", run time: " + (t2 - t1));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param count
     *
     */
    public static void test4Java(int count) {
        try {
            Map<String, Object> context = new HashMap<String, Object>();
            context.put("name", "test");
            context.put("border", "border=\"1\"");
            context.put("data", getDataList());
            StringWriter stringWriter = new StringWriter();

            /**
             * warmed
             */
            for(int i = 0; i < 100; i++) {
                execute(context, stringWriter);
                stringWriter.getBuffer().setLength(0);
            }

            long t1 = System.currentTimeMillis();
            for(int i = 0; i < count; i++) {
                execute(context, stringWriter);
                stringWriter.getBuffer().setLength(0);
            }
            long t2 = System.currentTimeMillis();
            System.out.println("count: " + count + ", run time: " + (t2 - t1));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param count
     *
     */
    public static void test5Java(int count) {
        try {
            Map<String, Object> context = new HashMap<String, Object>();
            context.put("name", "test");
            context.put("border", "border=\"1\"");
            context.put("data", getDataList());

            StringWriter stringWriter = new StringWriter();

            /**
             * warmed
             */
            for(int i = 0; i < 100; i++) {
                execute5(context, stringWriter);
                stringWriter.getBuffer().setLength(0);
            }

            long t1 = System.currentTimeMillis();
            for(int i = 0; i < count; i++) {
                execute5(context, stringWriter);
                stringWriter.getBuffer().setLength(0);
            }
            long t2 = System.currentTimeMillis();
            System.out.println("count: " + count + ", run time: " + (t2 - t1));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param context
     * @param writer
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static void execute(Map<String, Object> context, Writer writer) throws Exception {
        List<String> data = (List<String>) context.get("data");
        String name = (String) context.get("name");
        String border = (String) context.get("border");
        writer.write("<div>\n<h1>");
        writer.write(name);
        writer.write("</h1>\n<table border=\"");
        writer.write(border);
        writer.write("\">\n\t<tr>\n\t\t<th>&#160;</th>\n");
        for (String cell : data) {
            writer.write("\t\t<th>");
            writer.write(cell);
            writer.write("</th>\n");
        }
        writer.write("\t</tr>\n");
        writer.write("</table>\n</div>\n");
    }

    /**
     * @param context
     * @param writer
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static void execute5(Map<String, Object> context, Writer writer) throws Exception {
        List<String> data = (List<String>) context.get("data");
        String name = (String) context.get("name");
        String border = (String) context.get("border");
        writer.write("<div>\n<h1>");
        writer.write(name);
        writer.write("</h1>\n");

        for(int i = 0; i < 5; i++) {
            writer.write("<table border=\"");
            writer.write(border);
            writer.write("\">\n    <tr>\n        <th>&#160;</th></tr>\n");

            for (String cell : data) {
                writer.write("\r\n        <tr>\r\n            <th>");
                writer.write(cell);
                writer.write("</th>\r\n        </tr>\r\n    ");
            }
            writer.write("</table>\n");
        }
        writer.write("</div>\n");
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
     * @return List<String>
     */
    public static List<String> getDataList() {
        List<String> list = new ArrayList<String>();

        for(int i = 0; i < 10; i++) {
            list.add(String.valueOf((char)i));
        }
        return list;
    }
}
