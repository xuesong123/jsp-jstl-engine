/*
 * $RCSfile: TagFactoryManager.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-11-07 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.factory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skin.ayada.config.TemplateConfig;
import com.skin.ayada.runtime.ExpressionContext;
import com.skin.ayada.runtime.TagFactory;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.util.ClassUtil;
import com.skin.ayada.util.IO;

/**
 * <p>Title: TagFactoryManager</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class TagFactoryManager {
    private FactoryClassLoader factoryClassLoader;
    private Map<String, TagFactory> context;
    private static TagFactoryManager instance = new TagFactoryManager();
    private static final Logger logger = LoggerFactory.getLogger(TagFactoryManager.class);

    static{
        ClassPool classPool = ClassPool.getDefault();
        classPool.appendClassPath(new ClassClassPath(DefaultTagFactory.class));
    }

    /**
     * @param tagName
     * @param tagClassName
     * @return Tag
     */
    public static Tag getTag(String tagName, String tagClassName) {
        TagFactoryManager tagFactoryManager = TagFactoryManager.getInstance();
        TagFactory tagFactory = tagFactoryManager.getTagFactory(tagName, tagClassName);
        return tagFactory.create();
    }

    private TagFactoryManager() {
        this.context = new HashMap<String, TagFactory>();
        this.factoryClassLoader = FactoryClassLoader.getInstance();
    }

    /**
     * @return TagFactoryManager
     */
    public static TagFactoryManager getInstance() {
        return instance;
    }

    /**
     * @param tagName
     * @param className
     * @return TagFactory
     */
    public synchronized TagFactory getTagFactory(String tagName, String className) {
        /**
         * 此处调用量很小不做线程安全控制
         */
        TagFactory tagFactory = this.context.get(className);

        if(tagFactory == null) {
            tagFactory = this.create(tagName, className);

            if(tagFactory == null) {
                tagFactory = new DefaultTagFactory();
                tagFactory.setTagName(tagName);
                tagFactory.setClassName(className);
            }

            if(logger.isDebugEnabled()) {
                logger.debug("tagName: " + tagName + ", className: " + className + ", tagFactory: " + tagFactory.getClass().getName());
            }
            this.context.put(className, tagFactory);
        }
        return tagFactory;
    }

    /**
     * @param tagName
     * @param className
     * @return TagFactory
     */
    public TagFactory create(String tagName, String className) {
        ClassPool classPool = ClassPool.getDefault();

        try {
            Class<?> tagClass = ClassUtil.getClass(className);
            String returnClassName = Tag.class.getName();
            String surperClassName = DefaultTagFactory.class.getName();
            String factoryClassName = this.getFactoryClassName(className);
            String setMethodCode = getSetMethodCode(tagClass);

            if(logger.isDebugEnabled()) {
                logger.debug("{}.setMethodCode: \r\n{}", className, setMethodCode);
            }

            CtClass ctClass = classPool.makeClass(factoryClassName);
            ctClass.setSuperclass(classPool.get(surperClassName));
            CtMethod createMethod = CtNewMethod.make("public " + returnClassName + " create() {return new " + className + "();}", ctClass);
            CtMethod setMethod = CtNewMethod.make(setMethodCode, ctClass);
            ctClass.addMethod(createMethod);
            ctClass.addMethod(setMethod);

            byte[] bytes = ctClass.toBytecode();
            this.write(factoryClassName, bytes);

            TagFactory tagFactory = (TagFactory)(this.getInstance(factoryClassName, bytes));
            tagFactory.setTagName(tagName);
            tagFactory.setClassName(className);
            return tagFactory;
        }
        catch(Exception e) {
            logger.warn(e.getMessage(), e);
        }
        return null;
    }

    /**
     * @param type
     * @return String
     */
    public static String getSetMethodCode(Class<?> type) {
        /**
         * javaassist不支持泛型
         * 所以此处需要尽可能的符合javaassist的要求
         */
        Method[] methods = type.getMethods();
        StringBuilder buffer = new StringBuilder();
        buffer.append("public void setAttributes(");
        buffer.append(Tag.class.getName());
        buffer.append(" tag, java.util.Map attributes, ");
        buffer.append(ExpressionContext.class.getName());
        buffer.append(" expressionContext) {\r\n");
        buffer.append("    ").append(type.getName()).append(" _tag = (").append(type.getName()).append(")tag;\r\n");

        for(Method method : methods) {
            String name = method.getName();
            Class<?>[] parameterTypes =  method.getParameterTypes();

            if(name.length() <= 3 || parameterTypes.length != 1) {
                continue;
            }

            if(!name.startsWith("set")) {
                continue;
            }

            if(name.equals("setParent") || name.equals("setPageContext")) {
                continue;
            }

            Class<?> parameterType = parameterTypes[0];
            String field = Character.toLowerCase(name.charAt(3)) + name.substring(4);
            String varName = "value" + name.substring(3);
            String parameterTypeName = getParameterType(parameterType);
            String parameterValue = getParameterValue(varName, parameterType);

            buffer.append("    Object ").append(varName).append(" = ");
            buffer.append("this.getValue(expressionContext, attributes.get(\"");
            buffer.append(field).append("\"), ").append(parameterTypeName).append(".class);\r\n\r\n");
            buffer.append("    if(").append(varName).append(" != null) {\r\n");
            buffer.append("        _tag.").append(name).append("(").append(parameterValue).append(");\r\n");
            buffer.append("    }\r\n\r\n");
        }
        buffer.append("}\r\n");
        return buffer.toString();
    }

    /**
     * @param varName
     * @param type
     * @return String
     */
    private static String getParameterType(Class<?> type) {
        if(!type.isPrimitive()) {
            if(type == Object.class) {
                return "Object";
            }
            else if(type == String.class) {
                return "String";
            }
            return type.getName();
        }

        if(type == boolean.class) {
            return "Boolean";
        }
        else if(type == byte.class) {
            return "Byte";
        }
        else if(type == short.class) {
            return "Short";
        }
        else if(type == char.class) {
            return "Character";
        }
        else if(type == int.class) {
            return "Integer";
        }
        else if(type == float.class) {
            return "Float";
        }
        else if(type == double.class) {
            return "Double";
        }
        else if(type == long.class) {
            return "Long";
        }
        return type.getName();
    }

    /**
     * @param varName
     * @param type
     * @return String
     */
    private static String getParameterValue(String varName, Class<?> type) {
        if(!type.isPrimitive()) {
            if(type == Object.class) {
                return varName;
            }
            else if(type == String.class) {
                return "((String)" + varName + ")";
            }
            return "((" + type.getName() + ")" + varName + ")";
        }

        if(type == boolean.class) {
            return "((Boolean)" + varName + ").booleanValue()";
        }
        else if(type == byte.class) {
            return "((Byte)" + varName + ").byteValue()";
        }
        else if(type == short.class) {
            return "((Short)" + varName + ").shortValue()";
        }
        else if(type == char.class) {
            return "((Character)" + varName + ").charValue()";
        }
        else if(type == int.class) {
            return "((Integer)" + varName + ").intValue()";
        }
        else if(type == float.class) {
            return "((Float)" + varName + ").floatValue()";
        }
        else if(type == double.class) {
            return "((Double)" + varName + ").doubleValue()";
        }
        else if(type == long.class) {
            return "((Long)" + varName + ").longValue()";
        }
        return "((" + type.getName() + ")" + varName + ")";
    }

    /**
     * @param buffer
     * @param args
     * @return String
     */
    protected String concat(StringBuilder buffer, Object[] args) {
        buffer.setLength(0);

        for(Object item : args) {
            buffer.append(item);
        }
        return buffer.toString();
    }

    /**
     * @param className
     * @return String
     */
    private String getFactoryClassName(String className) {
        String packageName = "";
        String simpleClassName = className;
        int k = simpleClassName.lastIndexOf(".");

        if(k > -1) {
            packageName = simpleClassName.substring(0, k);
            simpleClassName = simpleClassName.substring(k + 1);
        }
        return "_tpl." + packageName + ".factory." + simpleClassName + "Factory";
    }

    /**
     * @param className
     * @param bytes
     * @return Object
     */
    private Object getInstance(String className, byte[] bytes) {
        Class<?> clazz = this.factoryClassLoader.create(className, bytes);

        try {
            return clazz.newInstance();
        }
        catch(Exception e) {
            logger.warn(e.getMessage(), e);
        }
        return null;
    }

    /**
     * @param className
     * @param bytes
     */
    private void write(String className, byte[] bytes) {
        String work = TemplateConfig.getCompileWork();

        if(work == null) {
            return;
        }

        File dir = new File(work);
        
        if(!dir.exists() || !dir.isDirectory()) {
            return;
        }
        
        File file = new File(dir, className.replace('.', '/') + ".class");
        File parent = file.getParentFile();

        if(!parent.exists()) {
            parent.mkdirs();
        }

        try {
            IO.write(file, bytes);
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
