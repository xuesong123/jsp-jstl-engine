/*
 * $RCSfile: TemplateFilter.java,v $$
 * $Revision: 1.1  $
 * $Date: 2013-2-27  $
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

import com.skin.ayada.runtime.ExpressionFactory;
import com.skin.ayada.template.JspTemplateFactory;
import com.skin.ayada.template.TemplateContext;
import com.skin.ayada.template.TemplateFactory;
import com.skin.ayada.template.TemplateManager;
import com.skin.ayada.util.StringUtil;
import com.skin.ayada.web.TemplateDispatcher;

/**
 * <p>Title: TemplateFilter</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class TemplateFilter implements Filter
{
    private String home;
    private String expire = "600";
    private String templateFactoryClassName;
    private ServletContext servletContext;
    private TemplateContext templateContext;
    private static final Logger logger = LoggerFactory.getLogger(TemplateFilter.class);

    public TemplateFilter()
    {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        this.home = filterConfig.getInitParameter("home");
        this.templateFactoryClassName = filterConfig.getInitParameter("template-factory");
        this.servletContext = filterConfig.getServletContext();

        if(this.home == null)
        {
            this.home = "/";
        }

        int timeout = -1;
        this.expire = filterConfig.getInitParameter("expire");

        if(this.expire != null)
        {
            try
            {
                timeout = Integer.parseInt(this.expire);
            }
            catch(NumberFormatException e)
            {
                e.printStackTrace();
            }
        }

        if(logger.isDebugEnabled())
        {
            logger.debug("home: " + this.home + ", expire: " + timeout);
        }

        TemplateFactory templateFactory = null;

        if(templateFactoryClassName != null)
        {
            try
            {
                templateFactory = this.getTemplateFactory(templateFactoryClassName);

                if(templateFactory instanceof JspTemplateFactory)
                {
                    JspTemplateFactory jspTemplateFactory = (JspTemplateFactory)templateFactory;
                    File work = new File(servletContext.getRealPath("/WEB-INF/work/ayada"));
                    jspTemplateFactory.setWork(work.getAbsolutePath());
                    jspTemplateFactory.setClassPath(this.getClassPath(servletContext));
                }
            }
            catch(Exception e)
            {
                throw new ServletException(e);
            }
        }
        else
        {
            templateFactory = new TemplateFactory();
        }

        this.templateContext = TemplateManager.getTemplateContext(servletContext.getRealPath(this.home), true);
        this.templateContext.setExpire(timeout);
        this.templateContext.setTemplateFactory(templateFactory);
        ExpressionFactory.setAttribute("servletContext", this.servletContext);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException
    {
        if(!(servletRequest instanceof HttpServletRequest) || !(servletResponse instanceof HttpServletResponse))
        {
            throw new ServletException("TemplateFilter just supports HTTP requests");
        }

        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        String requestURI = request.getRequestURI();
        requestURI = StringUtil.replace(requestURI, "//", "/");

        if(this.home != null && this.home.length() > 1)
        {
            if(requestURI.startsWith(this.home))
            {
                requestURI = requestURI.substring(this.home.length());
            }
        }

        request.setAttribute("TemplateFilter$servletContext", this.servletContext);
        TemplateDispatcher.dispatch(templateContext, request, response, requestURI);
    }

    /**
     * @param servletContext
     * @return String
     */
    public String getClassPath(ServletContext servletContext)
    {
        String seperator = ";";
        StringBuilder buffer = new StringBuilder();
        File lib = new File(servletContext.getRealPath("/WEB-INF/lib"));

        if(System.getProperty("os.name").indexOf("Windows") < 0)
        {
            seperator = ":";
        }

        if(lib.exists())
        {
            File[] files = lib.listFiles();

            if(files != null && files.length > 0)
            {
                for(File file : files)
                {
                    buffer.append(file.getAbsolutePath());
                    buffer.append(seperator);
                }
            }
        }

        File clazz = new File(servletContext.getRealPath("/WEB-INF/class"));

        if(clazz.exists() && clazz.isFile())
        {
            buffer.append(clazz.getAbsolutePath());
            buffer.append(seperator);
        }

        if(buffer.length() > 0)
        {
            buffer.delete(buffer.length() - seperator.length(), buffer.length());
        }

        return buffer.toString();
    }
    
    /**
     * @param className
     * @return TemplateFactory
     * @throws Exception 
     */
    public TemplateFactory getTemplateFactory(String className) throws Exception
    {
        Class<?> clazz = null;

        try
        {
            clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
        }
        catch(Exception e)
        {
        }

        if(clazz == null)
        {
            clazz = TemplateFilter.class.getClassLoader().loadClass(className);
        }

        if(clazz == null)
        {
            clazz = Class.forName(className);
        }

        if(clazz != null)
        {
            return (TemplateFactory)(clazz.newInstance());
        }
        else
        {
            throw new ClassNotFoundException(className + " not found !");
        }
    }

    @Override
    public void destroy()
    {
        this.templateContext.destory();
        this.templateContext = null;
    }
}
