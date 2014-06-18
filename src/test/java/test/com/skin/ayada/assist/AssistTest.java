/*
 * $RCSfile: AssistTest.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-07 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.assist;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Method;

import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMember;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;
import javassist.compiler.Javac;

import com.skin.ayada.factory.DefaultTagFactory;
import com.skin.ayada.factory.FactoryClassLoader;
import com.skin.ayada.runtime.PageContext;
import com.skin.ayada.runtime.TagFactory;
import com.skin.ayada.template.TemplateContext;
import com.skin.ayada.template.TemplateManager;
import com.skin.ayada.util.IO;

/**
 * <p>Title: AssistTest</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class AssistTest
{
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        ClassPool classPool = ClassPool.getDefault();

        try
        {
            String className = "test.com.skin.ayada.template.Test2";
            CtClass ctClass = classPool.makeClass(className);
            Javac javac = new Javac(ctClass);
            CtMember ctMember = javac.compile(IO.read(new File("test2body.java"), "UTF-8", 4096));

            System.out.println(ctMember.getClass().getName());
            System.out.println(ctMember.getName());

            ctClass = ctMember.getDeclaringClass();
            Object object = getInstance(className, ctClass.toBytecode());

            Method[] methods = object.getClass().getMethods();

            for(Method method : methods)
            {
                System.out.println(method.toGenericString());
            }

            TemplateContext templateContext = TemplateManager.getTemplateContext("webapp");

            StringWriter writer = new StringWriter();
            PageContext pageContext = templateContext.getPageContext(writer);
            invoke(object, "execute", new Class<?>[]{PageContext.class}, new Object[]{pageContext});
            System.out.println(writer.toString());
        }
        catch(Throwable t)
        {
            t.printStackTrace();
        }
    }

    public static void test3()
    {
        ClassPool classPool = ClassPool.getDefault();

        try
        {
            classPool.appendClassPath(new ClassClassPath(com.skin.ayada.factory.DefaultTagFactory.class));
            classPool.get("ognl.OgnlContext");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void test2()
    {
        ClassPool pool = ClassPool.getDefault();

        try
        {
            String factoryClassName = "com.skin.ayada.factory.MyTagFactory";
            CtClass ctClass = pool.makeClass(factoryClassName);
            // CtClass ctClass = pool.get("com.skin.ayada.factory.DefaultTagFactory");
            // System.out.println(pt.getSuperclass().getName());
            // System.out.println(ctClass.getDeclaredMethod("create"));
            // CtNewMethod.make("public com.skin.ayada.tagext.Tag create1(){return new com.skin.ayada.jstl.core.SetTag();}", ctClass);
            // ctClass.addMethod(method);

            ctClass.setSuperclass(pool.get("com.skin.ayada.factory.DefaultTagFactory"));

            System.out.println(ctClass);
            CtMethod method = CtNewMethod.make("public com.skin.ayada.tagext.Tag create(){return new com.skin.ayada.jstl.core.SetTag();}", ctClass);
            ctClass.addMethod(method);

            DefaultTagFactory defaultTagFactory = new DefaultTagFactory();
            defaultTagFactory.setClassName("com.skin.ayada.jstl.core.IfTag");
            System.out.println(defaultTagFactory.create());

            TagFactory tagFactory = (TagFactory)(getInstance(factoryClassName, ctClass.toBytecode()));
            System.out.println(tagFactory);
            System.out.println(tagFactory.create());
        }
        catch(Throwable e)
        {
            e.printStackTrace();
        }
    }


    public static void test1()
    {
        ClassPool pool = ClassPool.getDefault();

        try
        {
            for(int i = 0; i < 2; i++)
            {
                CtClass ctClass = pool.get("com.skin.ayada.factory.DefaultTagFactory");
                // System.out.println(pt.getSuperclass().getName());
                // System.out.println(ctClass.getDeclaredMethod("create"));
                // CtNewMethod.make("public com.skin.ayada.tagext.Tag create1(){return new com.skin.ayada.jstl.core.SetTag();}", ctClass);
                // ctClass.addMethod(method);

                System.out.println(ctClass);
                CtMethod method = ctClass.getDeclaredMethod("create");
                method.setBody("return new com.skin.ayada.jstl.core.SetTag();");

                DefaultTagFactory defaultTagFactory = new DefaultTagFactory();
                System.out.println(defaultTagFactory.create());

                TagFactory tagFactory = (TagFactory)(getInstance("com.skin.ayada.factory.MyTagFactory", ctClass.toBytecode()));
                System.out.println(tagFactory);
                System.out.println(tagFactory.create());
            }
        }
        catch(NotFoundException e)
        {
            e.printStackTrace();
        }
        catch(CannotCompileException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static Object getInstance(String className, byte[] bytes)
    {
        FactoryClassLoader factoryClassLoader = FactoryClassLoader.getInstance();
        Class<?> clazz = factoryClassLoader.create(className, bytes);

        try
        {
            return clazz.newInstance();
        }
        catch(InstantiationException e)
        {
            e.printStackTrace();
        }
        catch(IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static void invoke(Object object, String methodName, Class<?>[] types, Object[] parameters) throws Throwable
    {
        Method method = object.getClass().getMethod(methodName, types);
        method.invoke(object, parameters);
    }
}
