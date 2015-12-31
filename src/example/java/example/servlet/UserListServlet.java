/*
 * $RCSfile: UserListServlet.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-26 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package example.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import example.handler.UserHandler;
import example.model.User;


/**
 * <p>Title: UserListServlet</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class UserListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        List<User> userList = UserHandler.getUserList(5);
        request.setAttribute("userList", userList);

        /**
         * forward to TemplateFilter.doFilter
         */
        request.getRequestDispatcher("/WEB-INF/template/userList.jsp").forward(request, response);
    }
}
