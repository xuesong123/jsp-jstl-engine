/*
 * $RCSfile: Jdbc.java,v $$
 * $Revision: 1.1 $
 * $Date: 2014-03-25 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * <p>Title: Jdbc</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class Jdbc {
    /**
     * @param url
     * @param driverClass
     * @param userName
     * @param password
     * @return Connection
     * @throws SQLException
     */
    public static Connection connect(String url, String driverClass, String userName, String password) throws SQLException {
        if(url == null) {
            throw new SQLException("The url must be not null !");
        }

        if(driverClass == null) {
            throw new SQLException("The driverClass must be not null !");
        }

        try {
            Class.forName(driverClass);
        }
        catch(ClassNotFoundException e) {
            throw new SQLException(e);
        }
        return DriverManager.getConnection(url, userName, password);
    }

    /**
     * @param url
     * @param driverClass
     * @param properties
     * @return Connection
     * @throws SQLException
     */
    public static Connection connect(String url, String driverClass, Properties properties) throws SQLException {
        if(url == null) {
            throw new SQLException("The url must be not null !");
        }

        if(driverClass == null) {
            throw new SQLException("The driverClass must be not null !");
        }

        try {
            Class.forName(driverClass);
        }
        catch(ClassNotFoundException e) {
            throw new SQLException(e.getMessage());
        }
        return DriverManager.getConnection(url, properties);
    }

    /**
     * @param query
     * @return Properties
     */
    public static Properties parse(String query) {
        Properties properties = new Properties();

        if(query == null || query.length() < 1)  {
            return properties;
        }

        char c;
        int offset = 0;
        int length = query.length();
        StringBuilder name = new StringBuilder();
        StringBuilder value = new StringBuilder();

        for(int i = 0; i < length; i++) {
            c = query.charAt(i);

            if(c == '?') {
                if(i + 1 < length) {
                    offset = i + 1;
                }
                else {
                    offset = length;
                }

                break;
            }
        }

        for(int i = offset; i < length; i++) {
            c = query.charAt(i);

            if(c == '?' || c == '&') {
                continue;
            }
            else if(c == '#') {
                for(i++; i < length; i++) {
                    c = query.charAt(i);

                    if(c == '?' || c == '&' || c == '#') {
                        i--;
                        break;
                    }
                }
            }
            else if(c == '=') {
                for(i++; i < length; i++) {
                    c = query.charAt(i);

                    if(c == '?' || c == '&' || c == '#') {
                        if(c == '#') {
                            i--;
                        }

                        break;
                    }
                    value.append(c);
                }

                if(name.length() > 0) {
                    try {
                        properties.setProperty(name.toString(), URLDecoder.decode(value.toString(), "UTF-8"));
                    }
                    catch(UnsupportedEncodingException e) {
                    }
                }
                name.setLength(0);
                value.setLength(0);
            }
            else {
                name.append(c);
            }
        }
        return properties;
    }

    /**
     * @param connection
     */
    public static void close(Connection connection) {
        if(connection != null) {
            boolean closed = false;

            try {
                closed = connection.isClosed();
            }
            catch(SQLException e) {
            }

            if(closed == false) {
                try {
                    connection.close();
                }
                catch(SQLException e) {
                }
            }
        }
    }

    /**
     * @param statement
     */
    public static void close(Statement statement) {
        if(statement != null) {
            try {
                statement.close();
            }
            catch(SQLException e) {
            }
        }
    }

    /**
     * @param resultSet
     */
    public static void close(ResultSet resultSet) {
        if(resultSet != null) {
            try {
                resultSet.close();
            }
            catch(SQLException e) {
            }
        }
    }
}
