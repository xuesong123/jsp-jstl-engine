/*
 * $RCSfile: Scriptlet.java,v $$
 * $Revision: 1.1  $
 * $Date: 2014-8-23  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.script;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import com.skin.ayada.runtime.PageContext;

/**
 * <p>Title: Scriptlet</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class Scriptlet {
    /**
     * @param args
     */
    public static void main(String[] args) {
        generate(System.out, PageContext.class);
    }

    /**
     * @param out
     * @param type
     */
    public static void generate(PrintStream out, Class<?> type){
        generate(new PrintWriter(out), type);
    }

    /**
     * @param out
     * @param type
     */
    public static void generate(PrintWriter out, Class<?> type){
        String simpleName = type.getSimpleName();
        Map<String, String> fieldMap = new HashMap<String, String>();
        Field[] fields = type.getDeclaredFields();
        StringBuilder buffer = new StringBuilder();
        out.println("/*");
        out.println(" * $RCSfile: " + simpleName + ".js,v $$");
        out.println(" * $Revision: 1.1 $");
        out.println(" * $Date: 2014-04-11 $");
        out.println(" *");
        out.println(" * Copyright (C) 2008 Skin, Inc. All rights reserved.");
        out.println(" *");
        out.println(" * This software is the proprietary information of Skin, Inc.");
        out.println(" * Use is subject to license terms.");
        out.println(" */");
        out.println("var " + simpleName + " = com.skin.framework.Class.create(null, function(" + getConstructorParameter(type) + "){");

        for(int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if(Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            fieldMap.put(field.getName(), field.getName());
            out.println("    this." + field.getName() + " = " + field.getName() + ";");

            if(buffer.length() > 0) {
                buffer.append(", ");
            }

            buffer.append("\"");
            buffer.append(field.getName());
            buffer.append("\"");
        }

        if(buffer.length() > 0) {
            out.print("}, [");
            out.print(buffer.toString());
            out.println("]);");
        }
        else {
            out.println("});");
        }
        out.println();
        Method[] methods = type.getDeclaredMethods();

        for(int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            String methodName = method.getName();

            if(methodName.startsWith("set") || methodName.startsWith("get")) {
                String fieldName = Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4);

                if(fieldMap.get(fieldName) != null) {
                    continue;
                }
            }

            Class<?> returnType = method.getReturnType();
            out.println(getMethodComment(method));
            out.print("/* ");
            out.print(Modifier.toString(method.getModifiers()));
            out.print(" ");
            out.print(returnType.getSimpleName());
            out.print(" */ ");
            if(Modifier.isStatic(method.getModifiers())) {
                out.println(simpleName + "." + method.getName() + " = function(" + getParameterTypes(method) + ")" + getExceptionTypes(method) + "{");
            }
            else {
                out.println(simpleName + ".prototype." + method.getName() + " = function(" + getParameterTypes(method) + ")" + getExceptionTypes(method) + "{");
            }
            out.println("    // TODO Auto-generated method stub");
            out.println("};");
            out.println();
        }

        out.flush();
    }

    /**
     * @param constructor
     * @return String
     */
    public static String getConstructorParameter(Class<?> type) {
        StringBuilder buffer = new StringBuilder();
        Field[] fields = type.getDeclaredFields();

        for(int i = 0; i < fields.length; i++) {
            Field field = fields[i];

            if(Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            String parameterName = field.getName();
            Class<?> parameterType = field.getType();

            if(buffer.length() > 0) {
                buffer.append(", ");
            }

            buffer.append("/* ");
            buffer.append(parameterType.getSimpleName());
            buffer.append(" */ ");
            buffer.append(parameterName);
        }
        return buffer.toString();
    }

    /**
     * @param method
     * @return String
     */
    public static String getMethodComment(Method method) {
        StringBuilder buffer = new StringBuilder();
        Class<?>[] parameterTypes = method.getParameterTypes();
        Class<?>[] exceptionTypes = method.getExceptionTypes();
        Class<?> returnType = method.getReturnType();
        buffer.append("/**\r\n");

        for(int i = 0; i < parameterTypes.length; i++) {
            Class<?> parameterType = parameterTypes[i];
            String simpleName = parameterType.getSimpleName();
            String parameterName = Character.toLowerCase(simpleName.charAt(0)) + simpleName.substring(1);
            buffer.append(" * @param ");
            buffer.append(parameterName);
            buffer.append("\r\n");
        }

        for(int i = 0; i < exceptionTypes.length; i++) {
            Class<?> exceptionType = exceptionTypes[i];
            buffer.append(" * @throws ");
            buffer.append(exceptionType.getSimpleName());
            buffer.append("\r\n");
        }

        buffer.append(" * @return ");
        buffer.append(returnType.getSimpleName());
        buffer.append("\r\n");
        buffer.append(" */");
        return buffer.toString();
    }

    /**
     * @param method
     * @return String
     */
    public static String getParameterTypes(Method method) {
        StringBuilder buffer = new StringBuilder();
        Class<?>[] parameterTypes = method.getParameterTypes();

        for(int i = 0; i < parameterTypes.length;) {
            Class<?> parameterType = parameterTypes[i];
            String simpleName = parameterType.getSimpleName();
            String parameterName = Character.toLowerCase(simpleName.charAt(0)) + simpleName.substring(1);

            buffer.append("/* ");
            buffer.append(simpleName);
            buffer.append(" */ ");
            buffer.append(parameterName);
            i++;

            if(i < parameterTypes.length) {
                buffer.append(", ");
            }
        }
        return buffer.toString();
    }

    /**
     * @param method
     * @return String
     */
    public static String getExceptionTypes(Method method) {
        StringBuilder buffer = new StringBuilder();
        Class<?>[] exceptionTypes = method.getExceptionTypes();

        if(exceptionTypes.length > 0) {
            buffer.append(" /* throws ");
            for(int i = 0; i < exceptionTypes.length;) {
                Class<?> exceptionType = exceptionTypes[i];
                buffer.append(exceptionType.getSimpleName());
                i++;

                if(i < exceptionTypes.length) {
                    buffer.append(", ");
                }
            }

            buffer.append("*/ ");
        }
        return buffer.toString();
    }
}
