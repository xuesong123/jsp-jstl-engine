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

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skin.ayada.runtime.PageContext;
import com.skin.ayada.template.Template;
import com.skin.ayada.template.TemplateContext;

/**
 * <p>Title: TemplateDispatcher</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class TemplateDispatcher {
    private static final Logger logger = LoggerFactory.getLogger(TemplateDispatcher.class);

    /**
     * @param templateContext
     * @param request
     * @param response
     * @param page
     */
    public static void dispatch(TemplateContext templateContext, HttpServletRequest request, HttpServletResponse response, String page) throws ServletException, IOException {
        if(logger.isDebugEnabled()) {
            logger.debug("dispatch: " + page);
        }

        Template template = null;

        try {
            template = templateContext.getTemplate(page);
        }
        catch(Exception e) {
            throw new ServletException(e);
        }

        if(template == null) {
            if(logger.isDebugEnabled()) {
                logger.debug("404: " + page);
            }
            response.sendError(404);
            return;
        }

        HttpSession session = request.getSession(false);
        ServletContext servletContext = getServletContext(session, request);
        Map<String, Object> context = new HashMap<String, Object>();

        // priority: request > session > servletContext
        if(servletContext != null) {
            context.put("servletContext", servletContext);

            if(request.getAttribute("TemplateFilter$servletContext") == null) {
                request.setAttribute("TemplateFilter$servletContext", servletContext);
            }

            String locale = servletContext.getInitParameter("javax.servlet.jsp.jstl.fmt.locale");
            String timeZone = servletContext.getInitParameter("javax.servlet.jsp.jstl.fmt.timeZone");

            if(locale != null) {
                context.put(PageContext.LOCALE_KEY, locale);
            }

            if(timeZone != null) {
                context.put(PageContext.TIMEZONE_KEY, timeZone);
            }

            TemplateDispatcher.export(servletContext, context);
        }

        if(session != null) {
            context.put("session", session);
            TemplateDispatcher.export(session, context);
        }

        context.put("request", request);
        context.put("response", response);
        context.put("param", TemplateDispatcher.getParameterMap(request));
        TemplateDispatcher.export(request, context);
        Writer writer = (Writer)(request.getAttribute("template_writer"));

        if(writer == null) {
            writer = response.getWriter();
        }

        try {
            templateContext.execute(template, context, writer);
        }
        catch(Throwable e) {
            throw new ServletException(e);
        }
    }

    /**
     * @param session
     * @param context
     */
    public static void export(HttpSession session, Map<String, Object> context) {
        java.util.Enumeration<?> enumeration = session.getAttributeNames();

        if(enumeration != null) {
            while(enumeration.hasMoreElements()) {
                String key = (String)(enumeration.nextElement());
                context.put(key, session.getAttribute(key));
            }
        }
    }

    /**
     * @param servletContext
     * @param context
     */
    public static void export(ServletContext servletContext, Map<String, Object> context) {
        java.util.Enumeration<?> enumeration = servletContext.getAttributeNames();

        if(enumeration != null) {
            while(enumeration.hasMoreElements()) {
                String key = (String)(enumeration.nextElement());
                context.put(key, servletContext.getAttribute(key));
            }
        }
    }

    /**
     * @param request
     * @param context
     */
    public static void export(HttpServletRequest request, Map<String, Object> context) {
        java.util.Enumeration<?> enumeration = request.getAttributeNames();

        if(enumeration != null) {
            while(enumeration.hasMoreElements()) {
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
    public static ServletContext getServletContext(HttpSession session, HttpServletRequest request) {
        ServletContext servletContext = null;

        if(session != null) {
            servletContext = session.getServletContext();
        }

        if(servletContext == null) {
            Object value = request.getAttribute("TemplateFilter$servletContext");

            if(value != null && value instanceof ServletContext) {
                servletContext = (ServletContext)(value);
            }
        }
        return servletContext;
    }

    /**
     * @param request
     * @return Map<String, Object>
     */
    public static Map<String, Object> getParameterMap(HttpServletRequest request) {
        Map<?, ?> map = request.getParameterMap();
        Map<String, Object> param = new HashMap<String, Object>();

        if(map != null && map.size() > 0) {
            for(Map.Entry<?, ?> entry : map.entrySet()) {
                String key = (String)(entry.getKey());
                Object value = entry.getValue();

                if(value instanceof String[]) {
                    String[] values = (String[])value;

                    if(values.length > 0) {
                        if(values.length > 1) {
                            param.put(key, values);
                        }
                        else {
                            param.put(key, values[0]);
                        }
                    }
                    continue;
                }

                if(value instanceof List<?>) {
                    List<?> values = (List<?>)value;

                    if(values.size() > 0) {
                        if(values.size() > 1) {
                            param.put(key, values);
                        }
                        else {
                            param.put(key, values.get(0));
                        }
                    }
                    continue;
                }
            }
        }
        return param;
    }
}
