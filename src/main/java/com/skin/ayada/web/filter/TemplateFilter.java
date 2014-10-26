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
import com.skin.ayada.template.JspTemplateFactory;
import com.skin.ayada.template.TemplateContext;
import com.skin.ayada.template.TemplateFactory;
import com.skin.ayada.template.TemplateManager;
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
public class TemplateFilter implements Filter
{
    private String home;
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

        if(logger.isInfoEnabled())
        {
            logger.info("jsp.home: " + this.home);
        }

        TemplateFactory templateFactory = null;

        if(this.templateFactoryClassName != null)
        {
            try
            {
                templateFactory = TemplateFactory.getTemplateFactory(this.templateFactoryClassName);

                if(templateFactory instanceof JspTemplateFactory)
                {
                    String jspWork = this.getJspWork(filterConfig);
                    String ignoreJspTag = filterConfig.getInitParameter("ignore-jsptag");

                    if(ignoreJspTag == null)
                    {
                        ignoreJspTag = TemplateConfig.getInstance().getString("ayada.compile.ignore-jsptag", "true");
                    }

                    if(logger.isInfoEnabled())
                    {
                        logger.info("jsp.work: " + jspWork);
                    }

                    File work = new File(jspWork);
                    JspTemplateFactory jspTemplateFactory = (JspTemplateFactory)templateFactory;
                    jspTemplateFactory.setWork(work.getAbsolutePath());
                    jspTemplateFactory.setClassPath(this.getClassPath(this.servletContext));
                    jspTemplateFactory.setIgnoreJspTag(ignoreJspTag.equals("true"));
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

        if(templateFactory == null)
        {
            throw new ServletException("templateFactory is null!");
        }

        String sourcePattern = filterConfig.getInitParameter("source-pattern");
        this.templateContext = TemplateManager.getTemplateContext(this.servletContext.getRealPath(this.home), true);
        this.templateContext.setTemplateFactory(templateFactory);

        if(sourcePattern != null)
        {
            this.templateContext.getSourceFactory().setSourcePattern(sourcePattern);
        }
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
        TemplateDispatcher.dispatch(this.templateContext, request, response, requestURI);
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

        File clazz = new File(servletContext.getRealPath("/WEB-INF/classes"));

        if(clazz.exists() && clazz.isDirectory())
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
     * @param servletContext
     * @return String
     */
    private String getContextPath(ServletContext servletContext)
    {
        try
        {
            Method method = ServletContext.class.getMethod("getContextPath", new Class[0]);
            return (String)(method.invoke(servletContext, new Object[0]));
        }
        catch(Exception e)
        {
        }

        return null;
    }

    /**
     * @param filterConfig
     * @return String
     */
    private String getJspWork(FilterConfig filterConfig)
    {
        String jspWork = filterConfig.getInitParameter("jsp-work");
        ServletContext servletContext = filterConfig.getServletContext();

        if(jspWork == null)
        {
            jspWork = this.getTempWork(this.getContextPath(servletContext));

            if(jspWork == null)
            {
                jspWork = servletContext.getRealPath("/WEB-INF/ayada");
            }
        }
        else
        {
            if(jspWork.startsWith("context:"))
            {
                jspWork = servletContext.getRealPath(jspWork.substring(8).trim());
            }
        }

        return jspWork;
    }

    /**
     * @return String
     */
    private String getTempWork(String prefix)
    {
        String work = System.getProperty("java.io.tmpdir");

        if(work == null)
        {
            return null;
        }

        long timeMillis = System.currentTimeMillis();
        String pattern = "yyyyMMddHHmmss";
        String name = prefix;

        if(name == null || name.length() < 1 || name.equals("/"))
        {
            name = "";
        }
        else
        {
            name = name.replace('\\', '.');
            name = name.replace('/', '.');
        }

        if(name.length() > 0)
        {
            name = "ayada_" + name + "_";
        }
        else
        {
            name = "ayada_";
        }

        File file = new File(work, name + DateUtil.format(timeMillis, pattern));

        while(file.exists())
        {
            timeMillis += 1000;
            file = new File(work, name + DateUtil.format(timeMillis, pattern));
        }

        return file.getAbsolutePath();
    }

    @Override
    public void destroy()
    {
        this.templateContext.destory();
        this.templateContext = null;
    }
}
