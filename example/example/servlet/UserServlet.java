/*
 * $RCSfile: UserServlet.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-15 $
 *
 * Copyright (C) 2008 WanMei, Inc. All rights reserved.
 *
 * This software is the proprietary information of WanMei, Inc.
 * Use is subject to license terms.
 */
package example.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import example.handler.UserHandler;
import example.model.User;

/**
 * <p>Title: UserServlet</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class UserServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        long userId = 1L;
        
        try
        {
            userId = Long.parseLong(request.getParameter("userId"));
        }
        catch(NumberFormatException e)
        {
        }
        
        User user = UserHandler.getUserById(userId);
        request.setAttribute("user", user);

        /**
         * forward to TemplateFilter.doFilter 
         */
        request.getRequestDispatcher("/WEB-INF/template/user.jsp").forward(request, response);
    }
}
