/*
 * $RCSfile: ClassGenerator.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-6 $
 *
 * Copyright (C) 2008 WanMei, Inc. All rights reserved.
 *
 * This software is the proprietary information of WanMei, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.jstl;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import com.skin.ayada.runtime.JspFactory;
import com.skin.ayada.runtime.PageContext;
import com.skin.ayada.template.DefaultExecutor;
import com.skin.ayada.template.DefaultTemplateContext;
import com.skin.ayada.template.Template;
import com.skin.ayada.template.TemplateContext;
import com.skin.ayada.util.IO;

/**
 * <p>Title: ClassGenerator</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class ClassGenerator {
    /**
     * @param args
     */
    public static void main(String[] args) {
        String[] list = {
            "ConstantValue",
            "Code",
            "StackMapTable",
            "Exceptions",
            "InnerClasses",
            "EnclosingMethod",
            "Synthetic",
            "Signature",
            "SourceFile",
            "SourceDebugExtension",
            "LineNumberTable",
            "LocalVariableTable",
            "LocalVariableTypeTable",
            "Deprecated",
            "RuntimeVisibleAnnotations",
            "RuntimeInvisibleAnnotations",
            "RuntimeVisibleParameterAnnotations",
            "RuntimeInvisibleParameterAnnotations",
            "AnnotationDefault",
            "BootstrapMethods"
        };

        for(int i = 0; i < list.length; i++) {
            // test("docs", "code.jsp", list[i] + "Attribute");
            // System.out.println("public static final String " + list[i] + " = \"" + list[i] + "\";");
            String className = list[i] + "Attribute";
            String variableName = Character.toLowerCase(className.charAt(0)) + className.substring(1);

            if(i > 0) {
                System.out.print("else ");
            }

            System.out.println("if(attributeType.equals(AttributeType." + list[i] + "))");
            System.out.println("{");
            System.out.println("    " + className + " " + variableName + " = new " + className + "();");
            System.out.println("    " + variableName + ".setAttributeNameIndex(attributeNameIndex);");
            System.out.println("    " + variableName + ".setAttributeLength(attributeLength);");
            System.out.println("    return " + variableName + ";");
            System.out.println("}");
        }
    }

    public static void test(String home, String file, String className) {
        TemplateContext templateContext = new DefaultTemplateContext();
        StringWriter writer = new StringWriter();
        PageContext pageContext = JspFactory.getPageContext(null, writer);
        pageContext.setAttribute("targetClassName", className);

        try {
            Template template = templateContext.getTemplate(file);
            DefaultExecutor.execute(template, pageContext);
        }
        catch(Exception e1) {
            e1.printStackTrace();
        }

        try {
            IO.write(new File("docs\\" + className + ".java"), writer.toString().getBytes("UTF-8"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
