/*
 * $RCSfile: AssistTest.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-7 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.assist;

import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;

import com.skin.ayada.factory.DefaultTagFactory;
import com.skin.ayada.factory.FactoryClassLoader;
import com.skin.ayada.runtime.TagFactory;

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
        catch(Exception e)
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
            return (TagFactory)(clazz.newInstance());
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
}
