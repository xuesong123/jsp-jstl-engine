/*
 * $RCSfile: Request.java,v $$
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <p>Title: Request</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class Request {
    public static final String LOCALE = "com.skin.ayada.locale";
    public static final String TIMEZONE = "com.skin.ayada.time-zone";
    private static final String SERVLET_CONTEXT = "com.skin.ayada.web.servletContext";
    private static final String JSP_WRITER = "com.skin.ayada.web.jspWriter";

    /**
     * @param request
     * @param response
     * @return Map<String, Object>
     */
    public static Map<String, Object> getContext(HttpServletRequest request, HttpServletResponse response) {
        return getContext((ServletContext)null, request, response);
    }

    /**
     * @param request
     * @param response
     * @return Map<String, Object>
     */
    public static Map<String, Object> getContext(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) {
        ServletContext application = servletContext;
        HttpSession session = request.getSession(false);
        Map<String, Object> context = new HashMap<String, Object>();

        if(application == null) {
            application = getServletContext(session, request);
        }

        // priority: request > session > servletContext
        if(application != null) {
            String locale = application.getInitParameter("javax.servlet.jsp.jstl.fmt.locale");
            String timeZone = application.getInitParameter("javax.servlet.jsp.jstl.fmt.timeZone");

            if(locale != null) {
                context.put(LOCALE, locale);
            }

            if(timeZone != null) {
                context.put(TIMEZONE, timeZone);
            }
            Request.export(application, context);
        }

        if(session != null) {
            Request.export(session, context);
        }

        context.put("session", session);
        context.put("request", request);
        context.put("response", response);
        context.put("servletContext", application);
        context.put("params", Request.getParameterMap(request));
        context.put("headers", Request.getHttpHeader(request));
        Request.export(request, context);
        return context;
    }

    /**
     * @param request
     * @param servletContext
     */
    public static void setServletContext(HttpServletRequest request, ServletContext servletContext) {
        if(request.getAttribute("servletContext") == null) {
            request.setAttribute("servletContext", servletContext);
        }
        request.setAttribute(SERVLET_CONTEXT, servletContext);
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
            Object value = request.getAttribute("servletContext");

            if(value != null && value instanceof ServletContext) {
                return (ServletContext)(value);
            }

            value = request.getAttribute("javax.servlet.servletContext");

            if(value != null && value instanceof ServletContext) {
                return (ServletContext)(value);
            }

            value = request.getAttribute(SERVLET_CONTEXT);

            if(value != null && value instanceof ServletContext) {
                return (ServletContext)(value);
            }
        }
        return servletContext;
    }

    /**
     * @param request
     * @param response
     * @return Writer
     * @throws IOException
     */
    public static Writer getWriter(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Writer writer = (Writer)(request.getAttribute(JSP_WRITER));

        if(writer != null) {
            return writer;
        }
        else {
            return response.getWriter();
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
        String contextPath = request.getContextPath();
        java.util.Enumeration<?> enumeration = request.getAttributeNames();

        if(enumeration != null) {
            while(enumeration.hasMoreElements()) {
                String key = (String)(enumeration.nextElement());
                context.put(key, request.getAttribute(key));
            }
        }

        if(contextPath == null || contextPath.equals("/")) {
            context.put("contextPath", "");
        }
        else {
            context.put("contextPath", context);
        }
    }

    /**
     * @param request
     * @return HttpHeader
     */
    public static HttpHeader getHttpHeader(HttpServletRequest request) {
        HttpHeader httpHeader = new HttpHeader();
        java.util.Enumeration<?> enumeration = request.getHeaderNames();

        if(enumeration != null) {
            while(enumeration.hasMoreElements()) {
                String name = (String)(enumeration.nextElement());
                httpHeader.addHeader(name, request.getHeaders(name));
            }
        }
        return httpHeader;
    }

    /**
     * @param request
     * @return Map<String, List<Object>>
     */
    public static Map<String, List<Object>> getHttpHeaderMap(HttpServletRequest request) {
        java.util.Enumeration<?> enumeration = request.getHeaderNames();
        Map<String, List<Object>> headers = new HashMap<String, List<Object>>();

        if(enumeration != null) {
            while(enumeration.hasMoreElements()) {
                String name = (String)(enumeration.nextElement());
                List<Object> values = list(request.getHeaders(name));
                headers.put(name, values);
            }
        }
        return headers;
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

    /**
     * @param enumeration
     * @return
     */
    private static List<Object> list(java.util.Enumeration<?> enumeration) {
        List<Object> list = new ArrayList<Object>();

        if(enumeration != null) {
            while(enumeration.hasMoreElements()) {
                list.add(enumeration.nextElement());
            }
        }
        return list;
    }
}
