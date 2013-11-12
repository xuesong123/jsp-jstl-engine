/*
 * $RCSfile: StaticExecutor.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-8 $
 *
 * Copyright (C) 2008 WanMei, Inc. All rights reserved.
 *
 * This software is the proprietary information of WanMei, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.template;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.skin.ayada.compile.JspCompiler;
import com.skin.ayada.factory.FactoryClassLoader;
import com.skin.ayada.runtime.PageContext;
import com.skin.ayada.util.IO;

/**
 * <p>Title: StaticExecutor</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class StaticExecutor
{
    private static String WORK;

    static
    {
        try
        {
            WORK = new File("work").getCanonicalPath();
        }
        catch(IOException e)
        {
        }
    }

    /**
     * @param template
     * @param pageContext
     */
    public static void execute(Template template, final PageContext pageContext)
    {
        try
        {
            TemplateHandler templateHandler = compile(template, WORK);
            templateHandler.execute(pageContext);
        }
        catch(Throwable e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @param template
     * @return TemplateHandler
     */
    public static TemplateHandler compile(Template template, String work) throws Throwable
    {
        String className = getClassName(template.getFile());
        String simpleName = getSimpleName(className);
        String packageName = getPackageName(className);
        JspCompiler jspCompiler = new JspCompiler();
        String source = jspCompiler.compile(template, simpleName, packageName);

        String path = packageName.replace('.', '/') + "/" + simpleName;
        File srcFile = new File(work, path + ".java");
        File clsFile = new File(work, path + ".class");

        write(source, srcFile);
        // write(source, new File("test", path + ".java"));

        String classPath = System.getProperty("java.class.path");
        String[] args = new String[]{
            "-d", work,
            "-nowarn",
            "-encoding", "UTF-8",
            "-classpath", classPath,
            "-Xlint:unchecked",
            srcFile.getCanonicalPath()
        };

        javax.tools.JavaCompiler javaCompiler = javax.tools.ToolProvider.getSystemJavaCompiler();
        int status = javaCompiler.run(System.in, System.out, System.err, args);

        if(status == 0)
        {
            byte[] bytes = getBytes(clsFile);
            return (TemplateHandler)(getInstance(className, bytes));
        }
        else
        {
            throw new Exception("compile error: " + status);
        }
    }

    /**
     * @param file
     * @return byte[]
     * @throws IOException
     */
    public static byte[] getBytes(File file) throws IOException
    {
        InputStream inputStream = null;

        try
        {
            inputStream = new FileInputStream(file);
            byte[] buffer = new byte[(int)file.length()];
            inputStream.read(buffer, 0, buffer.length);
            return buffer;
        }
        finally
        {
            IO.close(inputStream);
        }
    }

    /**
     * @param className
     * @return String
     */
    private static String getClassName(String path)
    {
        String className = path;
        int k = className.lastIndexOf(".");

        if(k > -1)
        {
            className = className.substring(0, k);
        }

        className = "jsp." + className.replace('/', '.').replace('\\', '.');

        k = className.lastIndexOf(".");

        String simpleName = className.substring(k + 1);
        return className.substring(0, k + 1) + Character.toUpperCase(simpleName.charAt(0)) + simpleName.substring(1);
    }

    /**
     * @param className
     * @return String
     */
    private static String getSimpleName(String className)
    {
        int k = className.lastIndexOf(".");

        if(k > -1)
        {
            return className.substring(k + 1);
        }
        else
        {
            return className;
        }
    }

    /**
     * @param className
     * @return String
     */
    private static String getPackageName(String className)
    {
        int k = className.lastIndexOf(".");

        if(k > -1)
        {
            return className.substring(0, k);
        }
        else
        {
            return "jsp";
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

    /**
     * @param source
     * @param file
     * @throws IOException
     */
    public static void write(String source, File file) throws IOException
    {
        File parent = file.getParentFile();

        if(parent.exists() == false)
        {
            parent.mkdirs();
        }

        IO.write(source.getBytes("UTF-8"), file);
    }
}
