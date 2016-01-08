/*
 * $RCSfile: JspTemplateFactoryTest.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-08 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.template;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.skin.ayada.compile.TemplateCompiler;
import com.skin.ayada.jstl.TagLibrary;
import com.skin.ayada.jstl.TagLibraryFactory;
import com.skin.ayada.runtime.ExpressionContext;
import com.skin.ayada.runtime.PageContext;
import com.skin.ayada.source.ClassPathSourceFactory;
import com.skin.ayada.source.DefaultSourceFactory;
import com.skin.ayada.source.SourceFactory;
import com.skin.ayada.statement.Node;
import com.skin.ayada.statement.NodeType;
import com.skin.ayada.template.DefaultTemplateContext;
import com.skin.ayada.template.JspTemplateFactory;
import com.skin.ayada.template.Template;
import com.skin.ayada.template.TemplateContext;
import com.skin.ayada.template.TemplateFactory;
import com.skin.ayada.template.TemplateManager;
import com.skin.ayada.util.ExpressionUtil;
import com.skin.ayada.util.IO;
import com.skin.ayada.util.StringUtil;

/**
 * <p>Title: JspTemplateFactoryTest</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class JspTemplateFactoryTest {
    public static void main(String[] args) throws Exception {
        // test1("extTest.jsp");
        test1("command.jsp", true);
    }

    public static Template getTemplate() throws Exception {
        SourceFactory sourceFactory = new DefaultSourceFactory("webapp");
        TemplateCompiler compiler = new TemplateCompiler(sourceFactory);
        TagLibrary tagLibrary = TagLibraryFactory.getStandardTagLibrary();
        compiler.setTagLibrary(tagLibrary);
        return compiler.compile("allTagTest.jsp", "UTF-8");
    }

    public static void test1(String file, boolean execute) throws Exception {
        SourceFactory sourceFactory = new DefaultSourceFactory("webapp");
        TemplateFactory templateFactory = new JspTemplateFactory("work", System.getProperty("java.class.path"));
        templateFactory.setIgnoreJspTag(false);

        TemplateContext templateContext = new DefaultTemplateContext();
        templateContext.setSourceFactory(sourceFactory);
        templateContext.setTemplateFactory(templateFactory);

        long t1 = System.currentTimeMillis();

        Template template = templateFactory.create(sourceFactory, file, "UTF-8");
        long t2 = System.currentTimeMillis();
        System.out.println("compile time: " + (t2 - t1));

        StringWriter writer = new StringWriter();
        PageContext pageContext = templateContext.getPageContext(null, writer);

        if(execute) {
            try {
                long t3 = System.currentTimeMillis();
                template.execute(pageContext);
                long t4 = System.currentTimeMillis();
                System.out.println("run time: " + (t4 - t3));
            }
            catch(Exception e) {
                e.printStackTrace();
            }

            System.out.println("===================== result =====================");
            System.out.println(writer.toString());
        }
    }

    public static void test2() throws Exception {
        String home = "com/skin/ayada/compile";
        SourceFactory sourceFactory = new ClassPathSourceFactory(home);
        TemplateFactory templateFactory = new TemplateFactory();
        TemplateContext templateContext = new DefaultTemplateContext();
        templateContext.setSourceFactory(sourceFactory);
        templateContext.setTemplateFactory(templateFactory);

        long t1 = System.currentTimeMillis();
        Template template = templateContext.getTemplate("class.jsp", "UTF-8");
        long t2 = System.currentTimeMillis();

        System.out.println("compile time: " + (t2 - t1));
        List<Node> nodes = getTemplate().getNodes();

        StringWriter writer = new StringWriter();
        PageContext pageContext = templateContext.getPageContext(null, writer);
        pageContext.setAttribute("className", "Test1");
        pageContext.setAttribute("packageName", "test.com.skin.ayada.template");
        pageContext.setAttribute("date", "2013-11-8");

        pageContext.setAttribute("nodeList", nodes);
        pageContext.setAttribute("NodeType", JspTemplateFactoryTest.getNodeType());
        pageContext.setAttribute("CodeUtil", new JspTemplateFactoryTest());
        pageContext.setAttribute("NodeUtil", new JspTemplateFactoryTest());

        long t3 = System.currentTimeMillis();
        try {
            template.execute(pageContext);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        long t4 = System.currentTimeMillis();
        System.out.println("run time: " + (t4 - t3));

        String result = writer.toString();
        System.out.println(format(result));
    }

    public static void test4(String[] args) {
        try {
            String source = IO.read(new File("webapp/allTagTest.jsp"), "UTF-8", 4096);
            System.out.println(crlf(source));
            System.out.println(StringUtil.escape(source));
            // IO.write(crlf(source).getBytes("UTF-8"), new File("webapp/allTagTest.jsp"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void arrayListTest() {
        ArrayList<String> list = new ArrayList<String>(4);

        for(int i = 0; i < 5; i++) {
            list.add(String.valueOf(i));
        }

        try {
            Field[] fields = list.getClass().getDeclaredFields();

            for(int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                System.out.println("field: " + field.getName());

                if(field.getName().equals("elementData")) {
                    field.setAccessible(true);
                    Object[] elements = (Object[])(field.get(list));
                    System.out.println(elements.length);
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Integer> getNodeType() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("TEXT", NodeType.TEXT);
        map.put("EXPRESSION", NodeType.EXPRESSION);
        return map;
    }

    public static void expressionTest() {
        StringWriter writer = new StringWriter();
        TemplateContext templateContext = TemplateManager.getTemplateContext("webapp");
        PageContext pageContext = templateContext.getPageContext(null, writer);
        ExpressionContext expressionContext = pageContext.getExpressionContext();
        ExpressionUtil.getBoolean(expressionContext, "${1 == 1}");
    }

    /**
     * @param source
     * @return String
     */
    public static String format(String source) {
        StringReader stringReader = new StringReader(source);
        StringWriter stringWriter = new StringWriter();
        BufferedReader buffer = new BufferedReader(stringReader);

        int prev = 0;
        String line = null;

        try {
            while((line = buffer.readLine()) != null) {
                if(line.trim().length() > 0) {
                    prev = 1;
                    stringWriter.write(line);
                    stringWriter.write("\r\n");
                }
                else {
                    if(prev == 1) {
                        stringWriter.write("\r\n");
                        prev = 0;
                    }
                }
            }
        }
        catch(Exception e) {
        }

        return stringWriter.toString();
    }

    /**
     * @param source
     * @return String
     */
    public static String crlf(String source) {
        if(source == null) {
            return "";
        }

        char c;
        StringBuilder buffer = new StringBuilder();

        for(int i = 0, length = source.length(); i < length; i++) {
            c = source.charAt(i);

            switch (c) {
                case '\r': {
                    break;
                }
                case '\n': {
                    buffer.append("\r\n");break;
                }
                default : {
                    buffer.append(c);break;
                }
            }
        }

        return buffer.toString();
    }

}
