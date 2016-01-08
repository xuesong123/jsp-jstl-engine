/*
 * $RCSfile: TemplateContextFactory.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-27 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.web;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skin.ayada.config.TemplateConfig;
import com.skin.ayada.runtime.DefaultExpressionFactory;
import com.skin.ayada.runtime.ExpressionFactory;
import com.skin.ayada.source.DefaultSourceFactory;
import com.skin.ayada.source.SourceFactory;
import com.skin.ayada.source.ZipSourceFactory;
import com.skin.ayada.template.DefaultTemplateContext;
import com.skin.ayada.template.JspTemplateFactory;
import com.skin.ayada.template.TemplateContext;
import com.skin.ayada.template.TemplateFactory;
import com.skin.ayada.template.TemplateManager;
import com.skin.ayada.util.ClassUtil;
import com.skin.ayada.util.WebUtil;

/**
 * <p>Title: TemplateContextFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class TemplateContextFactory {
    private String home;
    private String encoding;
    private String sourcePattern;
    private String jspWork;
    private String zipFile;
    private String ignoreJspTag;
    private String classPath;
    private String sourceFactoryClass;
    private String templateFactoryClass;
    private String expressionFactoryClass;
    private static final Logger logger = LoggerFactory.getLogger(TemplateContextFactory.class);

    public TemplateContextFactory() {
    }

    public TemplateContext create() {
        if(this.home.startsWith("contextPath:")) {
            this.home = WebUtil.getRealPath(this.home.substring(12).trim());
        }

        if(this.encoding == null) {
            this.encoding = "utf-8";
        }

        if(this.sourcePattern == null) {
            this.sourcePattern = "jsp,jspx";
        }

        if(this.jspWork != null && this.jspWork.startsWith("contextPath:")) {
            this.jspWork = WebUtil.getRealPath(this.jspWork.substring(12).trim());
        }

        if(this.zipFile != null && this.zipFile.startsWith("contextPath:")) {
            this.zipFile = WebUtil.getRealPath(this.zipFile.substring(12).trim());
        }

        if(this.classPath == null) {
            this.classPath = WebUtil.getClassPath();
        }

        if(this.ignoreJspTag == null) {
            this.ignoreJspTag = System.getProperty("ayada.compile.ignore-jsptag");
        }

        if(this.ignoreJspTag == null) {
            this.ignoreJspTag = String.valueOf(TemplateConfig.getIgnoreJspTag());
        }

        if(this.sourceFactoryClass == null) {
            this.sourceFactoryClass = DefaultSourceFactory.class.getName();
        }

        if(this.templateFactoryClass == null) {
            this.templateFactoryClass = TemplateFactory.class.getName();
        }

        if(this.expressionFactoryClass == null) {
            this.expressionFactoryClass = DefaultExpressionFactory.class.getName();
        }

        if(logger.isInfoEnabled()) {
            logger.info("home: {}", this.home);
            logger.info("encoding: {}", this.encoding);
            logger.info("sourcePattern: {}", this.sourcePattern);
            logger.info("jspWork: {}", this.jspWork);
            logger.info("zipFile: {}", this.zipFile);
            logger.info("ignoreJspTag: {}", this.ignoreJspTag);
            logger.info("classPath: {}", this.classPath);
            logger.info("sourceFactoryClass: {}", this.sourceFactoryClass);
            logger.info("templateFactoryClass: {}", this.templateFactoryClass);
            logger.info("expressionFactoryClass: {}", this.expressionFactoryClass);
        }

        try {
            SourceFactory sourceFactory = this.getSourceFactory();
            TemplateFactory templateFactory = this.getTemplateFactory();
            ExpressionFactory expressionFactory = this.getExpressionFactory();
            sourceFactory.setHome(this.home);
            sourceFactory.setSourcePattern(this.sourcePattern);

            if(logger.isInfoEnabled()) {
                logger.info("sourceFactory: " + sourceFactory.getClass().getName());
                logger.info("templateFactory: " + templateFactory.getClass().getName());
                logger.info("expressionFactory: " + expressionFactory.getClass().getName());
            }
    
            TemplateContext templateContext = new DefaultTemplateContext();
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
     * @param filterConfig
     * @return SourceFactory
     * @throws ServletException
     */
    private SourceFactory getSourceFactory() throws Exception {
        if(this.sourceFactoryClass != null) {
            SourceFactory sourceFactory = (SourceFactory)(ClassUtil.getInstance(this.sourceFactoryClass));

            if(sourceFactory instanceof ZipSourceFactory) {
                if(this.zipFile == null) {
                    throw new RuntimeException("parameter 'zip-file' must be not null");
                }
                ((ZipSourceFactory)sourceFactory).setFile(this.zipFile);
            }
            return sourceFactory;
        }
        return new DefaultSourceFactory();
    }

    /**
     * @param filterConfig
     * @return TemplateFactory
     */
    private TemplateFactory getTemplateFactory() throws Exception {
        if(this.templateFactoryClass == null) {
            return new TemplateFactory();
        }

        TemplateFactory templateFactory = TemplateFactory.getTemplateFactory(this.templateFactoryClass);

        if(templateFactory instanceof JspTemplateFactory) {
            JspTemplateFactory jspTemplateFactory = (JspTemplateFactory)templateFactory;
            jspTemplateFactory.setWork(this.jspWork);
            jspTemplateFactory.setClassPath(this.classPath);
            jspTemplateFactory.setIgnoreJspTag("true".equals(this.ignoreJspTag));
        }
        return templateFactory;
    }

    /**
     * @param filterConfig
     * @return TemplateFactory
     */
    private ExpressionFactory getExpressionFactory() throws Exception {
        if(this.expressionFactoryClass != null) {
            return (ExpressionFactory)(ClassUtil.getInstance(this.expressionFactoryClass));
        }
        return new DefaultExpressionFactory();
    }

    /**
     * @return the home
     */
    public String getHome() {
        return this.home;
    }

    /**
     * @param home the home to set
     */
    public void setHome(String home) {
        this.home = home;
    }

    /**
     * @return the encoding
     */
    public String getEncoding() {
        return this.encoding;
    }

    /**
     * @param encoding the encoding to set
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * @return the sourcePattern
     */
    public String getSourcePattern() {
        return this.sourcePattern;
    }

    /**
     * @param sourcePattern the sourcePattern to set
     */
    public void setSourcePattern(String sourcePattern) {
        this.sourcePattern = sourcePattern;
    }

    /**
     * @return the jspWork
     */
    public String getJspWork() {
        return this.jspWork;
    }

    /**
     * @param jspWork the jspWork to set
     */
    public void setJspWork(String jspWork) {
        this.jspWork = jspWork;
    }

    /**
     * @return the ignoreJspTag
     */
    public String isIgnoreJspTag() {
        return this.ignoreJspTag;
    }

    /**
     * @param ignoreJspTag the ignoreJspTag to set
     */
    public void setIgnoreJspTag(String ignoreJspTag) {
        this.ignoreJspTag = ignoreJspTag;
    }

    /**
     * @return the zipFile
     */
    public String getZipFile() {
        return this.zipFile;
    }

    /**
     * @param zipFile the zipFile to set
     */
    public void setZipFile(String zipFile) {
        this.zipFile = zipFile;
    }

    /**
     * @return the classPath
     */
    public String getClassPath() {
        return this.classPath;
    }

    /**
     * @param classPath the classPath to set
     */
    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    /**
     * @return the sourceFactoryClass
     */
    public String getSourceFactoryClass() {
        return this.sourceFactoryClass;
    }

    /**
     * @param sourceFactoryClass the sourceFactoryClass to set
     */
    public void setSourceFactoryClass(String sourceFactoryClass) {
        this.sourceFactoryClass = sourceFactoryClass;
    }

    /**
     * @return the templateFactoryClass
     */
    public String getTemplateFactoryClass() {
        return this.templateFactoryClass;
    }

    /**
     * @param templateFactoryClass the templateFactoryClass to set
     */
    public void setTemplateFactoryClass(String templateFactoryClass) {
        this.templateFactoryClass = templateFactoryClass;
    }

    /**
     * @return the expressionFactoryClass
     */
    public String getExpressionFactoryClass() {
        return this.expressionFactoryClass;
    }

    /**
     * @param expressionFactoryClass the expressionFactoryClass to set
     */
    public void setExpressionFactoryClass(String expressionFactoryClass) {
        this.expressionFactoryClass = expressionFactoryClass;
    }
}
