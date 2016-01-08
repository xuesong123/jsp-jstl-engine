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

import com.skin.ayada.template.TemplateContext;
import com.skin.ayada.template.TemplateManager;
import com.skin.ayada.util.DateUtil;
import com.skin.ayada.util.StringUtil;
import com.skin.ayada.web.TemplateContextFactory;
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

            TemplateContextFactory contextFactory = new TemplateContextFactory();
            contextFactory.setHome(realPath);
            contextFactory.setEncoding(filterConfig.getInitParameter("encoding"));
            contextFactory.setSourcePattern(filterConfig.getInitParameter("sourcePattern"));
            contextFactory.setJspWork(filterConfig.getInitParameter("jspWork"));
            contextFactory.setZipFile(filterConfig.getInitParameter("zipFile"));
            contextFactory.setIgnoreJspTag(filterConfig.getInitParameter("ignoreJspTag"));
            contextFactory.setClassPath(filterConfig.getInitParameter("classPath"));
            contextFactory.setSourceFactoryClass(filterConfig.getInitParameter("sourceFactoryClass"));
            contextFactory.setTemplateFactoryClass(filterConfig.getInitParameter("templateFactoryClass"));
            contextFactory.setExpressionFactoryClass(filterConfig.getInitParameter("expressionFactoryClass"));
            this.templateContext = contextFactory.create();
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

    @Override
    public void destroy() {
        this.templateContext.destory();
        this.templateContext = null;
    }
}
