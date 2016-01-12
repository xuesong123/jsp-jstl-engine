/*
 * $RCSfile: TemplateDispatcher.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-03-10 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.web;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.servlet.FilterConfig;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skin.ayada.template.Template;
import com.skin.ayada.template.TemplateContext;
import com.skin.ayada.template.TemplateManager;
import com.skin.ayada.util.DateUtil;
import com.skin.ayada.util.StringUtil;

/**
 * <p>Title: TemplateDispatcher</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class TemplateDispatcher {
    private String home;
    private String encoding;
    private String contentType;
    private ServletContext servletContext;
    private TemplateContext templateContext;
    private static final Logger logger = LoggerFactory.getLogger(TemplateDispatcher.class);

    /**
     * @param filterConfig
     * @return TemplateDispatcher
     * @throws ServletException
     */
    public static TemplateDispatcher create(FilterConfig filterConfig) throws ServletException {
        String home = filterConfig.getInitParameter("home");
        String encoding = filterConfig.getInitParameter("encoding");
        String contentType = filterConfig.getInitParameter("contentType");
        ServletContext servletContext = filterConfig.getServletContext();

        if(home == null) {
            home = "/";
        }

        if(home.startsWith("contextPath:")) {
            home = home.substring(12);
            home = servletContext.getRealPath(home);
        }

        if(encoding == null) {
            encoding = "UTF-8";
        }

        if(contentType == null) {
            contentType = "text/html; charset=UTF-8";
        }

        if(logger.isInfoEnabled()) {
            logger.info("jsp.home: " + home);
        }

        TemplateContextFactory contextFactory = new TemplateContextFactory();
        contextFactory.setHome(home);
        contextFactory.setEncoding(filterConfig.getInitParameter("encoding"));
        contextFactory.setSourcePattern(filterConfig.getInitParameter("sourcePattern"));
        contextFactory.setJspWork(filterConfig.getInitParameter("jspWork"));
        contextFactory.setZipFile(filterConfig.getInitParameter("zipFile"));
        contextFactory.setIgnoreJspTag(filterConfig.getInitParameter("ignoreJspTag"));
        contextFactory.setClassPath(filterConfig.getInitParameter("classPath"));
        contextFactory.setSourceFactoryClass(filterConfig.getInitParameter("sourceFactoryClass"));
        contextFactory.setTemplateFactoryClass(filterConfig.getInitParameter("templateFactoryClass"));
        contextFactory.setExpressionFactoryClass(filterConfig.getInitParameter("expressionFactoryClass"));
        TemplateContext templateContext = contextFactory.create();
        TemplateManager.add(templateContext);

        TemplateDispatcher templateDispatcher = new TemplateDispatcher();
        templateDispatcher.setHome(home);
        templateDispatcher.setEncoding(encoding);
        templateDispatcher.setContentType(contentType);
        templateDispatcher.setServletContext(servletContext);
        templateDispatcher.setTemplateContext(templateContext);
        return templateDispatcher;
    }

    /**
     * @param servletConfig
     * @return TemplateDispatcher
     * @throws ServletException
     */
    public static TemplateDispatcher create(ServletConfig servletConfig) throws ServletException {
        String home = servletConfig.getInitParameter("home");
        String encoding = servletConfig.getInitParameter("encoding");
        String contentType = servletConfig.getInitParameter("contentType");
        ServletContext servletContext = servletConfig.getServletContext();

        if(home == null) {
            home = "/";
        }

        if(home.startsWith("contextPath:")) {
            home = home.substring(12);
            home = servletContext.getRealPath(home);
        }

        if(encoding == null) {
            encoding = "UTF-8";
        }

        if(contentType == null) {
            contentType = "text/html; charset=UTF-8";
        }

        if(logger.isInfoEnabled()) {
            logger.info("jsp.home: " + home);
        }

        TemplateContextFactory contextFactory = new TemplateContextFactory();
        contextFactory.setHome(home);
        contextFactory.setEncoding(servletConfig.getInitParameter("encoding"));
        contextFactory.setSourcePattern(servletConfig.getInitParameter("sourcePattern"));
        contextFactory.setJspWork(servletConfig.getInitParameter("jspWork"));
        contextFactory.setZipFile(servletConfig.getInitParameter("zipFile"));
        contextFactory.setIgnoreJspTag(servletConfig.getInitParameter("ignoreJspTag"));
        contextFactory.setClassPath(servletConfig.getInitParameter("classPath"));
        contextFactory.setSourceFactoryClass(servletConfig.getInitParameter("sourceFactoryClass"));
        contextFactory.setTemplateFactoryClass(servletConfig.getInitParameter("templateFactoryClass"));
        contextFactory.setExpressionFactoryClass(servletConfig.getInitParameter("expressionFactoryClass"));
        TemplateContext templateContext = contextFactory.create();
        TemplateManager.add(templateContext);

        TemplateDispatcher templateDispatcher = new TemplateDispatcher();
        templateDispatcher.setHome(home);
        templateDispatcher.setEncoding(encoding);
        templateDispatcher.setContentType(contentType);
        templateDispatcher.setServletContext(servletContext);
        templateDispatcher.setTemplateContext(templateContext);
        return templateDispatcher;
    }

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getRequestURI();
        path = StringUtil.replace(path, "//", "/");

        if(this.home != null && this.home.length() > 1) {
            if(path.startsWith(this.home)) {
                path = path.substring(this.home.length());
            }
        }

        if(response.getContentType() == null) {
            response.setContentType(this.contentType);
        }
        
        Template template = null;

        try {
            template = this.templateContext.getTemplate(path, this.encoding);
        }
        catch(Exception e) {
            throw new ServletException(e);
        }

        if(template == null) {
            if(logger.isDebugEnabled()) {
                logger.debug("404: " + path);
            }
            response.sendError(404);
            return;
        }

        Request.setServletContext(request, this.servletContext);
        Map<String, Object> context = Request.getContext(request, response);
        Writer writer = Request.getWriter(request, response);

        try {
            this.templateContext.execute(template, context, writer);
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
     * @return String
     */
    protected String getTempWork(String prefix) {
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

    public void destroy() {
        this.templateContext.destory();
        this.templateContext = null;
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
     * @return the contentType
     */
    public String getContentType() {
        return this.contentType;
    }

    /**
     * @param contentType the contentType to set
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * @return the servletContext
     */
    public ServletContext getServletContext() {
        return this.servletContext;
    }

    /**
     * @param servletContext the servletContext to set
     */
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    /**
     * @return the templateContext
     */
    public TemplateContext getTemplateContext() {
        return this.templateContext;
    }

    /**
     * @param templateContext the templateContext to set
     */
    public void setTemplateContext(TemplateContext templateContext) {
        this.templateContext = templateContext;
    }
}
