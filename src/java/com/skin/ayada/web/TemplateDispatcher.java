/*
 * $RCSfile: TemplateDispatcher.java,v $$
 * $Revision: 1.1  $
 * $Date: 2013-3-10  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.web;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skin.ayada.runtime.ExpressionFactory;
import com.skin.ayada.template.Template;
import com.skin.ayada.template.TemplateContext;

/**
 * <p>Title: TemplateDispatcher</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class TemplateDispatcher
{
    private static final Logger logger = LoggerFactory.getLogger(TemplateDispatcher.class);

    /**
     * @param templateContext
     * @param request
     * @param response
     * @param page
     */
    public static void dispatch(TemplateContext templateContext, HttpServletRequest request, HttpServletResponse response, String page) throws ServletException, IOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("dispatch: " + page);
        }

        Template template = null;

        try
        {
            template = templateContext.getTemplate(page);
        }
        catch(Exception e)
        {
            throw new ServletException(e);
        }

        if(template == null)
        {
            if(logger.isDebugEnabled())
            {
                logger.debug("404: " + page);
            }

            response.sendError(404);
            return;
        }

        HttpSession session = request.getSession(false);
        ServletContext servletContext = getServletContext(session, request);
        Map<String, Object> context = new HashMap<String, Object>();

        // priority: request > session > servletContext 
        if(servletContext != null)
        {
            context.put("servletContext", servletContext);

            if(request.getAttribute("TemplateFilter$servletContext") == null)
            {
                request.setAttribute("TemplateFilter$servletContext", servletContext);
            }

            TemplateDispatcher.setAttributes(servletContext, context);
        }

        if(session != null)
        {
            context.put("session", session);
            TemplateDispatcher.setAttributes(session, context);
        }

        context.put("request", request);
        context.put("response", response);
        TemplateDispatcher.setAttributes(request, context);
        Writer writer = (Writer)(request.getAttribute("template_writer"));

        if(writer == null)
        {
            writer = response.getWriter();
        }

        try
        {
            templateContext.execute(template, context, writer);
        }
        catch(Throwable e)
        {
            throw new ServletException(e);
        }
    }

    /**
     * @param session
     * @param enumeration
     */
    public static void setAttributes(HttpSession session, Map<String, Object> context)
    {
        java.util.Enumeration<?> enumeration = session.getAttributeNames();

        if(enumeration != null)
        {
            while(enumeration.hasMoreElements())
            {
                String key = (String)(enumeration.nextElement());
                context.put(key, session.getAttribute(key));
            }
        }
    }

    /**
     * @param servletContext
     * @param enumeration
     */
    public static void setAttributes(ServletContext servletContext, Map<String, Object> context)
    {
        java.util.Enumeration<?> enumeration = servletContext.getAttributeNames();

        if(enumeration != null)
        {
            while(enumeration.hasMoreElements())
            {
                String key = (String)(enumeration.nextElement());
                context.put(key, servletContext.getAttribute(key));
            }
        }
    }

    /**
     * @param request
     * @param enumeration
     */
    public static void setAttributes(HttpServletRequest request, Map<String, Object> context)
    {
        java.util.Enumeration<?> enumeration = request.getAttributeNames();

        if(enumeration != null)
        {
            while(enumeration.hasMoreElements())
            {
                String key = (String)(enumeration.nextElement());
                context.put(key, request.getAttribute(key));
            }
        }
    }

    /**
     * @param session
     * @param request
     * @return ServletContext
     */
    public static ServletContext getServletContext(HttpSession session, HttpServletRequest request)
    {
        ServletContext servletContext = null;

        if(session != null)
        {
            servletContext = session.getServletContext();
        }

        if(servletContext == null)
        {
            Object value = request.getAttribute("TemplateFilter$servletContext");

            if(value != null && value instanceof ServletContext)
            {
                servletContext = (ServletContext)(value);
            }
        }

        if(servletContext == null)
        {
            Object value = ExpressionFactory.getAttribute("servletContext");

            if(value != null && value instanceof ServletContext)
            {
                servletContext = (ServletContext)(value);
            }
        }

        return servletContext;
    }
}
