/*
 * $RCSfile: UserListServlet.java,v $$
 * $Revision: 1.1  $
 * $Date: 2013-2-26  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package example.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import test.com.skin.ayada.model.User;

import com.skin.ayada.template.TemplateContext;


/**
 * <p>Title: UserListServlet</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class UserListServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private static final TemplateContext templateContext = new TemplateContext("webapp");

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html; charset=UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();
        List<User> userList = this.getUserList();
        context.put("userList", userList);
        
        try
        {
            templateContext.execute("/userList.html", context, response.getWriter());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private List<User> getUserList()
    {
        List<User> userList = new ArrayList<User>();
        for(int i = 0; i < 10; i++)
        {
            User user = new User();
            user.setUserId(Long.valueOf(i));
            user.setUserName("test" + i);
            userList.add(user);
        }
        return userList;
    }
}
