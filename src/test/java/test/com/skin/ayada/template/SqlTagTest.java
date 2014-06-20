/*
 * $RCSfile: SqlTagTest.java,v $$
 * $Revision: 1.1 $
 * $Date: 2014-03-25 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.template;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;

import com.skin.ayada.runtime.PageContext;
import com.skin.ayada.template.Template;
import com.skin.ayada.template.TemplateContext;
import com.skin.ayada.template.TemplateFactory;
import com.skin.ayada.template.TemplateManager;
import com.skin.ayada.util.TemplateUtil;

/**
 * <p>Title: SqlTagTest</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class SqlTagTest
{
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        String home = "E:\\WorkSpace\\ayada\\webapp";
        TemplateFactory templateFactory = new TemplateFactory();
        TemplateContext templateContext = TemplateManager.getTemplateContext(home, true);
        templateContext.setExpire(300);
        templateContext.setTemplateFactory(templateFactory);
        Template template = null;
        Connection connection = null;

        try
        {
            template = templateContext.getTemplate("sqlTagTest.jsp");
            StringWriter writer = new StringWriter();
            PageContext pageContext = templateContext.getPageContext(writer);
            connection = getConnection();
            pageContext.setAttribute("connection", connection);

            template.execute(pageContext);
            System.out.println("-------------- source result --------------");
            System.out.println(TemplateUtil.toString(template));
            System.out.println("-------------- run result --------------");
            System.out.println(writer.toString());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            MySql.close(connection);
        }
    }

    public static Connection getConnection() throws SQLException
    {
        return MySql.connect("localhost", "3306", "mytest", "root", "1234");
    }
}
