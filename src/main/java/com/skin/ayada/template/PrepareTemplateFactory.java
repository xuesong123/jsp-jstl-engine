/*
 * $RCSfile: PrepareTemplateFactory.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-13 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skin.ayada.source.SourceFactory;
import com.skin.ayada.util.ClassUtil;

/**
 * <p>Title: PrepareTemplateFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class PrepareTemplateFactory extends JspTemplateFactory {
    private ClassLoader classLoader;
    private static final Logger logger = LoggerFactory.getLogger(PrepareTemplateFactory.class);

    /**
     *
     */
    public PrepareTemplateFactory() {
        super();
    }

    /**
     * @param work
     * @param classPath
     */
    public PrepareTemplateFactory(String work, String classPath) {
        super(work, classPath);
    }

    /**
     * @param sourceFactory
     * @param path
     * @param encoding
     * @return Template
     * @throws Exception
     */
    @Override
    public Template create(SourceFactory sourceFactory, String path, String encoding) throws Exception {
        String className = this.getClassName(path);
        logger.debug("path: {}, class: {}", path, className);

        Class<?> type = this.getClass(className);
        return (JspTemplate)(type.newInstance());
    }

    /**
     * @param className
     * @return Class<?>
     * @throws ClassNotFoundException
     */
    public Class<?> getClass(String className) throws ClassNotFoundException {
        if(this.classLoader != null) {
            return this.classLoader.loadClass(className);
        }

        try {
            return Thread.currentThread().getContextClassLoader().loadClass(className);
        }
        catch(Exception e) {
        }

        try {
            return ClassUtil.class.getClassLoader().loadClass(className);
        }
        catch(Exception e) {
        }
        return Class.forName(className);
    }

    /**
     * @return the classLoader
     */
    public ClassLoader getClassLoader() {
        return this.classLoader;
    }

    /**
     * @param classLoader the classLoader to set
     */
    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
