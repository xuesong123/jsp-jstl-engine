/*
 * $RCSfile: TemplateContextFactory.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-02-27 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.web;

import java.io.File;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skin.ayada.ExpressionFactory;
import com.skin.ayada.TemplateContext;
import com.skin.ayada.TemplateFactory;
import com.skin.ayada.TemplateManager;
import com.skin.ayada.config.TemplateConfig;
import com.skin.ayada.runtime.DefaultExpressionFactory;
import com.skin.ayada.runtime.DefaultTemplateContext;
import com.skin.ayada.runtime.DefaultTemplateFactory;
import com.skin.ayada.runtime.JspTemplateFactory;
import com.skin.ayada.source.DefaultSourceFactory;
import com.skin.ayada.util.ClassUtil;
import com.skin.ayada.util.DateUtil;
import com.skin.ayada.util.WebUtil;

/**
 * <p>Title: TemplateContextFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class TemplateContextFactory {
    private static final Logger logger = LoggerFactory.getLogger(TemplateContextFactory.class);

    /**
     *
     */
    public TemplateContextFactory() {
    }

    /**
     * @param servletContext
     * @param properties
     * @return TemplateContext
     */
    public TemplateContext create(ServletContext servletContext, Properties properties) {
        String name = properties.getProperty("name");
        String home = properties.getProperty("home");
        String sourcePattern = properties.getProperty("sourcePattern");
        String jspWork = properties.getProperty("jspWork");
        String classPath = properties.getProperty("classPath");
        String ignoreJspTag = properties.getProperty("ignoreJspTag");
        String templateFactoryClass = properties.getProperty("templateFactoryClass");
        String expressionFactoryClass = properties.getProperty("expressionFactoryClass");

        if(home == null) {
            throw new NullPointerException("'home' must be not null.");
        }

        if(home.startsWith("contextPath:")) {
            home = servletContext.getRealPath(home.substring(12).trim());
        }

        if(sourcePattern == null) {
            sourcePattern = "jsp,jspx";
        }

        if(jspWork != null && jspWork.startsWith("contextPath:")) {
            jspWork = servletContext.getRealPath(jspWork.substring(12).trim());
        }

        if(classPath == null) {
            classPath = WebUtil.getClassPath();
        }

        if(ignoreJspTag == null) {
            ignoreJspTag = System.getProperty("ayada.compile.ignore-jsptag");
        }

        if(ignoreJspTag == null) {
            ignoreJspTag = String.valueOf(TemplateConfig.getIgnoreJspTag());
        }

        if(templateFactoryClass == null) {
            templateFactoryClass = TemplateFactory.class.getName();
        }

        if(expressionFactoryClass == null) {
            expressionFactoryClass = DefaultExpressionFactory.class.getName();
        }

        if(logger.isInfoEnabled()) {
            logger.info("home: {}", home);
            logger.info("sourcePattern: {}", sourcePattern);
            logger.info("jspWork: {}", jspWork);
            logger.info("ignoreJspTag: {}", ignoreJspTag);
            logger.info("classPath: {}", classPath);
            logger.info("templateFactoryClass: {}", templateFactoryClass);
            logger.info("expressionFactoryClass: {}", expressionFactoryClass);
        }

        try {
            DefaultSourceFactory sourceFactory = new DefaultSourceFactory();
            TemplateFactory templateFactory = getTemplateFactory(templateFactoryClass, jspWork, classPath, ignoreJspTag);
            ExpressionFactory expressionFactory = getExpressionFactory(expressionFactoryClass);
            sourceFactory.setHome(home);
            sourceFactory.setSourcePattern(sourcePattern);

            if(logger.isInfoEnabled()) {
                logger.info("sourceFactory: " + sourceFactory.getClass().getName());
                logger.info("templateFactory: " + templateFactory.getClass().getName());
                logger.info("expressionFactory: " + expressionFactory.getClass().getName());
            }

            DefaultTemplateContext templateContext = new DefaultTemplateContext();
            templateContext.setId(name);
            templateContext.setSourceFactory(sourceFactory);
            templateContext.setTemplateFactory(templateFactory);
            templateContext.setExpressionFactory(expressionFactory);
            TemplateManager.add(templateContext);
            return templateContext;
        }
        catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * @param className
     * @param jspWork
     * @param classPath
     * @param ignoreJspTag
     * @return TemplateFactory
     * @throws Exception
     */
    private TemplateFactory getTemplateFactory(String className, String jspWork, String classPath, String ignoreJspTag) throws Exception {
        TemplateFactory templateFactory = DefaultTemplateFactory.getTemplateFactory(className);

        if(templateFactory instanceof JspTemplateFactory) {
            String work = jspWork;

            if(work == null) {
                work = this.getTempWork("");
            }

            JspTemplateFactory jspTemplateFactory = (JspTemplateFactory)templateFactory;
            jspTemplateFactory.setWork(work);
            jspTemplateFactory.setClassPath(classPath);
            jspTemplateFactory.setIgnoreJspTag("true".equals(ignoreJspTag));
        }
        return templateFactory;
    }

    /**
     * @param className
     * @return TemplateFactory
     */
    private ExpressionFactory getExpressionFactory(String className) throws Exception {
        if(className != null) {
            return (ExpressionFactory)(ClassUtil.getInstance(className));
        }
        return new DefaultExpressionFactory();
    }

    /**
     * @return String
     */
    protected String getTempWork(String prefix) {
        String work = WebUtil.getRealPath("/WEB-INF");

        if(work == null) {
            return null;
        }

        long timeMillis = System.currentTimeMillis();
        String pattern = "yyyyMMddHHmmss";
        String name = prefix;

        if(name == null || name.length() < 1 || name.equals("/")) {
            name = "";
        }
        else {
            name = name.replace('\\', '.');
            name = name.replace('/', '.');
        }

        if(name.length() > 0) {
            name = "ayada_" + name + "_";
        }
        else {
            name = "ayada_";
        }

        File file = new File(work, name + DateUtil.format(timeMillis, pattern));

        while(file.exists()) {
            timeMillis += 1000;
            file = new File(work, name + DateUtil.format(timeMillis, pattern));
        }
        return file.getAbsolutePath();
    }
}
