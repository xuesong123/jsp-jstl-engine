/*
 * $RCSfile: UserListServlet2.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-15 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package example.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.skin.ayada.template.TemplateContext;
import com.skin.ayada.template.TemplateManager;

import example.handler.UserHandler;
import example.model.User;

/**
 * <p>Title: UserListServlet2</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class UserListServlet2 extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private TemplateContext templateContext = null;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException
    {
        String home = servletConfig.getServletContext().getRealPath("/WEB-INF/template");
        this.templateContext = TemplateManager.getTemplateContext(home, true);
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();
        List<User> userList = UserHandler.getUserList(5);
        context.put("userList", userList);

        try
        {
            this.templateContext.execute("/userList.jsp", context, response.getWriter());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
