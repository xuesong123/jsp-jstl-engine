/*
 * $RCSfile: ClassPathClassLoader.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-06 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.factory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.regex.Pattern;

/**
 * <p>Title: ClassFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class ClassFactory
{
    /**
     * @param className
     * @param classPaths
     * @return Object
     * @throws Exception
     */
    public static Object getInstance(String className, File[] classPaths) throws Exception
    {
        URL[] repositories = ClassFactory.getRepositories(classPaths);
        ClassLoader classLoader = ClassFactory.getClassLoader(ClassFactory.class.getClassLoader(), repositories);
        return ClassFactory.loadClass(className, true, classLoader).newInstance();
    }

    /**
     * @param className
     * @param init
     * @param classLoader
     * @return Object
     * @throws Exception
     */
    public static Class<?> loadClass(String className, boolean init, ClassLoader classLoader) throws Exception
    {
        ClassLoader loader = classLoader;

        if(loader == null)
        {
            loader = Thread.currentThread().getContextClassLoader();
        }

        if((loader == null) || (loader.equals(ClassFactory.class.getClassLoader())))
        {
            return Class.forName(className);
        }

        return Class.forName(className, init, loader);
    }

    /**
     * @param parent
     * @param repositories
     * @return ClassLoader
     */
    public static ClassLoader getClassLoader(final ClassLoader parent, final URL[] repositories)
    {
        return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>(){
            public ClassLoader run(){
                if(parent == null)
                {
                    return new URLClassLoader(repositories);
                }
                else
                {
                    return new URLClassLoader(repositories, parent);
                }
            }
        });
    }

    /**
     * @param files
     * @return URL[]
     * @throws IOException
     */
    public static URL[] getRepositories(File[] files) throws IOException
    {
        if(files != null)
        {
            URL[] repositories = new URL[files.length];

            for(int i = 0, length = files.length; i < length; i++)
            {
                repositories[i] = files[i].toURI().toURL();
            }

            return repositories;
        }
        else
        {
            return new URL[0];
        }
    }

    /**
     * @return String
     */
    public static String getClassPath()
    {
        String boot = System.getProperty("sun.boot.class.path");
        String classPath = System.getProperty("java.class.path");

        if(boot != null && boot.length() > 0)
        {
            classPath = classPath + File.pathSeparatorChar + boot;
        }

        Pattern pattern = Pattern.compile(String.valueOf(File.pathSeparatorChar));
        String[] path = pattern.split(classPath);
        StringBuilder buffer = new StringBuilder();

        for(int i = 0; i < path.length; i++)
        {
            File file = new File(path[i]);

            if(file.exists() || file.isDirectory())
            {
                if(buffer.length() > 0)
                {
                    buffer.append(File.pathSeparatorChar);
                }
                buffer.append(path[i]);
            }
        }

        return buffer.toString();
    }

    public static void main(String[] args)
    {
        System.out.println(getClassPath());
    }
}
