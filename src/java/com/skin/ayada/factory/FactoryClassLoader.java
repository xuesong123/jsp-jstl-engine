/*
 * $RCSfile: TagFactoryClassLoader.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-6 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.factory;

import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * <p>Title: TagFactoryClassLoader</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class FactoryClassLoader extends ClassLoader
{
    /**
     * @param classLoader
     */
    public FactoryClassLoader(ClassLoader classLoader)
    {
        super(classLoader);
    }

    /**
     * @param name
     * @param bytes
     * @return Class<?>
     */
    public Class<?> create(String name, byte[] bytes)
    {
        return defineClass(name, bytes, 0, bytes.length);
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
