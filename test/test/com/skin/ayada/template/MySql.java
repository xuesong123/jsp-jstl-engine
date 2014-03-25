/*
 * $RCSfile: MySql.java,v $$
 * $Revision: 1.1 $
 * $Date: 2014-3-25 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.template;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * <p>Title: MySql</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class MySql
{
    /**
     * @param host
     * @param port
     * @param databaseName
     * @param userName
     * @param password
     * @return Connection
     * @throws SQLException
     */
    public static Connection connect(String host, String port, String databaseName, String userName, String password) throws SQLException
    {
        StringBuilder url = new StringBuilder();
        url.append("jdbc:mysql://");
        url.append(host);
        
        if(port != null && port.trim().length() > 0)
        {
            url.append(":");
            url.append(port);
        }

        url.append("/");
        url.append(databaseName);
        url.append("?user=");
        url.append(userName);
        url.append("&password=");
        url.append(password);
        url.append("&characterEncoding=utf8");
        return connect(url.toString(), "com.mysql.jdbc.Driver", userName, password);
    }

    /**
     * @param url
     * @param driverClass
     * @param userName
     * @param password
     * @return Connection
     * @throws SQLException
     */
    public static Connection connect(String url, String driverClass, String userName, String password) throws SQLException
    {
        if(url == null)
        {
            throw new SQLException("The Url must be not null !");
        }

        if(driverClass == null)
        {
            throw new SQLException("The DriverClass must be not null !");
        }

        try
        {
            Class.forName(driverClass);
        }
        catch(ClassNotFoundException e)
        {
            throw new SQLException(e.getMessage());
        }

        return DriverManager.getConnection(url, userName, password);
    }

    /**
     * @param statement
     */
    public static void close(Statement statement)
    {
        if(statement != null)
        {
            try
            {
                statement.close();
            }
            catch(SQLException e)
            {}
        }
    }

    /**
     * @param resultSet
     */
    public static void close(ResultSet resultSet)
    {
        if(resultSet != null)
        {
            try
            {
                resultSet.close();
            }
            catch(SQLException e)
            {}
        }
    }

    /**
     * @param statement
     */
    public static void close(Connection connection)
    {
        if(connection != null)
        {
            try
            {
                if(connection.isClosed() == false)
                {
                    connection.close();
                }
            }
            catch(SQLException e)
            {
            }
        }
    }
}
