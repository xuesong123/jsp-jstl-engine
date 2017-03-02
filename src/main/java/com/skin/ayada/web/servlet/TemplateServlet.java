/*
 * $RCSfile: TemplateServlet.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-02-27 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.web.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.skin.ayada.web.TemplateDispatcher;

/**
 * <p>Title: TemplateServlet</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class TemplateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateDispatcher templateDispatcher;

    /**
     * default
     */
    public TemplateServlet() {
    }

    /**
     * @param servletConfig
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        this.templateDispatcher = TemplateDispatcher.create(servletConfig);
    }

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.templateDispatcher.dispatch(request, response);
    }

    /**
     * destroy
     */
    @Override
    public void destroy() {
        this.templateDispatcher.destroy();
        this.templateDispatcher = null;
    }
}
