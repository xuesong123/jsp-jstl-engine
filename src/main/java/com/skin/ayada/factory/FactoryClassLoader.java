/*
 * $RCSfile: FactoryClassLoader.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-06 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.factory;

import java.security.AccessController;
import java.security.PrivilegedAction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Title: FactoryClassLoader</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class FactoryClassLoader extends ClassLoader
{
    private static final Logger logger = LoggerFactory.getLogger(FactoryClassLoader.class);

    /**
     * @param classLoader
     */
    public FactoryClassLoader(ClassLoader classLoader)
    {
        super(classLoader);
    }

    /**
     * @param className
     * @return Class<?>
     */
    public Class<?> getClass(String className)
    {
        try
        {
            return this.findClass(className);
        }
        catch(ClassNotFoundException e)
        {
            logger.warn(e.getMessage(), e);
        }

        return null;
    }

    /**
     * @param className
     * @param bytes
     * @return Class<?>
     */
    public Class<?> create(String className, byte[] bytes)
    {
        return this.defineClass(className, bytes, 0, bytes.length);
    }

    /**
     * @return FactoryClassLoader
     * @throws ClassNotFoundException
     */
    public static FactoryClassLoader getInstance()
    {
        FactoryClassLoader classLoader = AccessController.doPrivileged(new PrivilegedAction<FactoryClassLoader>()
        {
            public FactoryClassLoader run()
            {
                return new FactoryClassLoader(FactoryClassLoader.class.getClassLoader());
            }
        });
        
        return classLoader;
    }
}
