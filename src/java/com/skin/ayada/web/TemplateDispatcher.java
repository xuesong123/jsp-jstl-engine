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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        Template template = templateContext.getTemplate(page);

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
        Map<String, Object> context = new HashMap<String, Object>();

        context.put("request", request);
        context.put("response", response);

        if(session != null)
        {
            context.put("session", session);

            java.util.Enumeration<?> enu = session.getAttributeNames();

            if(enu != null)
            {
                while(enu.hasMoreElements())
                {
                    String key = (String)(enu.nextElement());
                    context.put(key, session.getAttribute(key));
                }
            }
        }

        java.util.Enumeration<?> enu = request.getAttributeNames();

        if(enu != null)
        {
            while(enu.hasMoreElements())
            {
                String key = (String)(enu.nextElement());
                context.put(key, request.getAttribute(key));
            }
        }

        Writer writer = (Writer)(request.getAttribute("template_writer"));

        if(writer == null)
        {
            writer = response.getWriter();
        }

        templateContext.execute(template, context, writer);
    }
}
