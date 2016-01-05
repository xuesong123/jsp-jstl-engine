/*
 * $RCSfile: TemplateFilter.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-27 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.web.filter;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.skin.ayada.util.DateUtil;
import com.skin.ayada.util.StringUtil;
import com.skin.ayada.web.TemplateDispatcher;

/**
 * <p>Title: TemplateFilter</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class TemplateFilter implements Filter {
    private String home;
    private String contentType;
    private ServletContext servletContext;
    private TemplateContext templateContext;
    private static final Logger logger = LoggerFactory.getLogger(TemplateFilter.class);

    public TemplateFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            this.home = filterConfig.getInitParameter("home");
            this.servletContext = filterConfig.getServletContext();
            this.contentType = filterConfig.getInitParameter("contentType");
            String realPath = null;

            if(this.home == null) {
                this.home = "/";
            }

            if(this.home.startsWith("contextPath:")) {
                this.home = this.home.substring(12);
                realPath = this.servletContext.getRealPath(this.home);
            }
            else {
                realPath = this.home;
            }

            if(this.contentType == null) {
                this.contentType = "text/html; charset=UTF-8";
            }

            if(logger.isInfoEnabled()) {
                logger.info("jsp.home: " + realPath);
            }

            String sourcePattern = filterConfig.getInitParameter("source-pattern");
            SourceFactory sourceFactory = this.getSourceFactory(filterConfig);
            TemplateFactory templateFactory = this.getTemplateFactory(filterConfig);
            ExpressionFactory expressionFactory = this.getExpressionFactory(filterConfig);
            sourceFactory.setHome(realPath);
            sourceFactory.setSourcePattern(sourcePattern);

            if(logger.isInfoEnabled()) {
                logger.info("sourceFactory: " + sourceFactory.getClass().getName());
                logger.info("templateFactory: " + templateFactory.getClass().getName());
                logger.info("expressionFactory: " + expressionFactory.getClass().getName());
            }

            this.templateContext = new DefaultTemplateContext(realPath, "UTF-8");
            this.templateContext.setSourceFactory(sourceFactory);
            this.templateContext.setTemplateFactory(templateFactory);
            this.templateContext.setExpressionFactory(expressionFactory);
            TemplateManager.add(this.templateContext);
        }
        catch(Exception e) {
            logger.warn(e.getMessage(), e);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        if(!(servletRequest instanceof HttpServletRequest) || !(servletResponse instanceof HttpServletResponse)) {
            throw new ServletException("TemplateFilter just supports HTTP requests");
        }

        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        String requestURI = request.getRequestURI();
        requestURI = StringUtil.replace(requestURI, "//", "/");

        if(this.home != null && this.home.length() > 1) {
            if(requestURI.startsWith(this.home)) {
                requestURI = requestURI.substring(this.home.length());
            }
        }

        request.setAttribute("TemplateFilter$servletContext", this.servletContext);

        if(response.getContentType() == null) {
            response.setContentType(this.contentType);
        }

        try {
            TemplateDispatcher.dispatch(this.templateContext, request, response, requestURI);
        }
        catch(Exception e) {
            if(response.isCommitted()) {
                logger.error(e.getMessage(), e);
            }
            else {
                if(e instanceof ServletException) {
                    throw (ServletException)e;
                }

                if(e instanceof IOException) {
                    throw (IOException)e;
                }
                throw new ServletException(e);
            }
        }
    }

    /**
     * @param filterConfig
     * @return SourceFactory
     * @throws ServletException
     */
    private SourceFactory getSourceFactory(FilterConfig filterConfig) throws ServletException {
        String sourceFactoryClassName = filterConfig.getInitParameter("source-factory");

        if(sourceFactoryClassName != null) {
            SourceFactory sourceFactory = null;

            try {
                sourceFactory = (SourceFactory)(ClassUtil.getInstance(sourceFactoryClassName));
            }
            catch(Exception e) {
                throw new ServletException(e);
            }

            if(sourceFactory instanceof ZipSourceFactory) {
                String zipFile = filterConfig.getInitParameter("zip-file");

                if(zipFile == null) {
                    throw new ServletException("parameter 'zip-file' must be not null");
                }

                ServletContext servletContext = filterConfig.getServletContext();
                String path = servletContext.getRealPath(zipFile);
                ((ZipSourceFactory)sourceFactory).setFile(path);
            }
            return sourceFactory;
        }
        return new DefaultSourceFactory();
    }

    /**
     * @param filterConfig
     * @return TemplateFactory
     */
    private TemplateFactory getTemplateFactory(FilterConfig filterConfig) throws ServletException {
        String templateFactoryClassName = filterConfig.getInitParameter("template-factory");

        if(templateFactoryClassName == null) {
            return new TemplateFactory();
        }

        try {
            TemplateFactory templateFactory = TemplateFactory.getTemplateFactory(templateFactoryClassName);

            if(templateFactory instanceof JspTemplateFactory) {
                String jspWork = this.getJspWork(filterConfig);
                String ignoreJspTag = System.getProperty("ayada.compile.ignore-jsptag");

                if(ignoreJspTag == null) {
                    ignoreJspTag = filterConfig.getInitParameter("ignore-jsptag");
                }

                if(ignoreJspTag == null) {
                    ignoreJspTag = String.valueOf(TemplateConfig.getIgnoreJspTag());
                }

                if(logger.isInfoEnabled()) {
                    logger.info("jsp.work: " + jspWork);
                }

                File work = new File(jspWork);
                JspTemplateFactory jspTemplateFactory = (JspTemplateFactory)templateFactory;
                jspTemplateFactory.setWork(work.getAbsolutePath());
                jspTemplateFactory.setClassPath(this.getClassPath(this.servletContext));
                jspTemplateFactory.setIgnoreJspTag(ignoreJspTag.equals("true"));
            }
            return templateFactory;
        }
        catch(Exception e) {
            throw new ServletException(e);
        }
    }

    /**
     * @param filterConfig
     * @return TemplateFactory
     */
    private ExpressionFactory getExpressionFactory(FilterConfig filterConfig) throws ServletException {
        String expressionFactoryClassName = filterConfig.getInitParameter("expression-factory");

        if(expressionFactoryClassName != null) {
            try {
                return (ExpressionFactory)(ClassUtil.getInstance(expressionFactoryClassName));
            }
            catch(Exception e) {
                throw new ServletException(e);
            }
        }
        return new DefaultExpressionFactory();
    }

    /**
     * @param servletContext
     * @return String
     */
    public String getClassPath(ServletContext servletContext) {
        String seperator = ";";
        StringBuilder buffer = new StringBuilder();
        File lib = new File(servletContext.getRealPath("/WEB-INF/lib"));

        if(System.getProperty("os.name").indexOf("Windows") < 0) {
            seperator = ":";
        }

        if(lib.exists()) {
            File[] files = lib.listFiles();

            if(files != null && files.length > 0) {
                for(File file : files) {
                    buffer.append(file.getAbsolutePath());
                    buffer.append(seperator);
                }
            }
        }

        File clazz = new File(servletContext.getRealPath("/WEB-INF/classes"));

        if(clazz.exists() && clazz.isDirectory()) {
            buffer.append(clazz.getAbsolutePath());
            buffer.append(seperator);
        }

        if(buffer.length() > 0) {
            buffer.delete(buffer.length() - seperator.length(), buffer.length());
        }
        return buffer.toString();
    }

    /**
     * @param servletContext
     * @return String
     */
    private String getContextPath(ServletContext servletContext) {
        try {
            Method method = ServletContext.class.getMethod("getContextPath", new Class[0]);
            return (String)(method.invoke(servletContext, new Object[0]));
        }
        catch(Exception e) {
        }
        return null;
    }

    /**
     * @param filterConfig
     * @return String
     */
    private String getJspWork(FilterConfig filterConfig) {
        String jspWork = filterConfig.getInitParameter("jsp-work");
        ServletContext servletContext = filterConfig.getServletContext();

        if(jspWork == null) {
            jspWork = this.getTempWork(this.getContextPath(servletContext));

            if(jspWork == null) {
                jspWork = servletContext.getRealPath("/WEB-INF/ayada");
            }
        }
        else {
            if(jspWork.startsWith("context:")) {
                jspWork = servletContext.getRealPath(jspWork.substring(8).trim());
            }
        }
        return jspWork;
    }

    /**
     * @return String
     */
    private String getTempWork(String prefix) {
        String work = System.getProperty("java.io.tmpdir");

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

    @Override
    public void destroy() {
        this.templateContext.destory();
        this.templateContext = null;
    }
}
