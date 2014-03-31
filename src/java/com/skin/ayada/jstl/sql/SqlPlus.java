/*
 * $RCSfile: SqlPlus.java,v $$
 * $Revision: 1.1  $
 * $Date: 2011-4-11  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.sql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * <p>Title: SqlPlus</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author chenyankui
 * @version 1.0
 */
public class SqlPlus
{
    private String home;
    private Connection connection;
    private Logger logger;

    /**
     */
    public SqlPlus()
    {
    }

    /**
     * @param connection
     */
    public SqlPlus(Connection connection)
    {
        this.connection = connection;
    }

    /**
     * @param connection
     */
    public SqlPlus(Connection connection, Logger logger)
    {
        this.connection = connection;
        this.logger = logger;
    }

    /**
     * @param encoding
     * @throws IOException
     */
    public void execute(InputStream inputStream, String encoding) throws IOException
    {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

        try
        {
            this.execute(inputStreamReader, encoding);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @param file
     * @param encoding
     * @throws IOException
     */
    public void execute(File file, String encoding) throws IOException
    {
        Reader reader = null;
        InputStream inputStream = null;

        try
        {
            inputStream = new FileInputStream(file);
            reader = new InputStreamReader(inputStream, encoding);
            this.execute(reader, encoding);
        }
        catch(IOException e)
        {
            throw e;
        }
        finally
        {
            if(reader != null)
            {
                try
                {
                    reader.close();
                }
                catch(IOException e)
                {
                }
            }

            if(inputStream != null)
            {
                try
                {
                    inputStream.close();
                }
                catch(IOException e)
                {
                }
            }
        }
    }

    /**
     * @param reader
     */
    public void execute(Reader reader, String encoding) throws IOException
    {
        Statement statement = null;
        BufferedReader bufferedReader = null;

        try
        {
            int index = 0;
            String sql = null;
            String text = null;
            String line = null;
            String command = null;
            StringBuffer buffer = new StringBuffer();
            bufferedReader = new BufferedReader(reader);

            while((line = bufferedReader.readLine()) != null)
            {
                text = line.trim();

                if(text.endsWith("-- #End;"))
                {
                    break;
                }

                if(text.startsWith("@"))
                {
                    text = text.substring(1);
                }

                index = text.indexOf(32);

                if(index < 0)
                {
                    command = text.toLowerCase();
                }
                else
                {
                    command = text.substring(0, index).toLowerCase();
                }

                if(command.endsWith(";"))
                {
                    command = command.substring(0, command.length() - 1);
                }

                if(command.startsWith("--"))
                {
                    this.log(text);
                    continue;
                }

                if(command.equals("remark"))
                {
                    this.log(text);
                    continue;
                }

                if(command.equals("set"))
                {
                    this.log(text);
                    continue;
                }

                if(command.equals("echo"))
                {
                    this.log(text.substring(5));
                    continue;
                }

                if(command.equals("prompt"))
                {
                    this.log(text.substring(7));
                    continue;
                }

                if(command.equals("commit"))
                {
                    this.log("commit;");
                    this.commit();
                    continue;
                }

                if(command.equals("disconnect"))
                {
                    this.log("disconnect;");
                    break;
                }

                if(command.equals("quit") || command.equals("exit"))
                {
                    this.log("quit;");
                    break;
                }

                if(command.equals("start") || command.equals("source"))
                {
                    text = this.unquote(text.substring(6).trim());
                    this.log("-- start [" + text + "]");
                    this.execute(new File(this.home, text), encoding);
                    continue;
                }

                if(text.endsWith(";"))
                {
                    buffer.append(text.substring(0, text.length() - 1));

                    if(buffer.length() > 0)
                    {
                        sql = buffer.toString().trim();
                        this.log(this.format(sql));

                        int k = sql.indexOf(" ");
                        String prefix = "";

                        if(k > -1)
                        {
                            prefix = sql.substring(0, k).toLowerCase();
                        }

                        try
                        {
                            if(statement == null)
                            {
                                statement = this.connection.createStatement();
                            }

                            if("drop|create|insert|update|delete|alter".indexOf(prefix) > -1)
                            {
                                statement.executeUpdate(sql);
                                this.log("SQLJ0000I  SQL命令成功完成。");
                            }
                            else
                            {
                                ResultSet resultSet = statement.executeQuery(sql);
                                this.print(resultSet, 0);
                            }
                        }
                        catch(SQLException e)
                        {
                            this.log("SQLException: " + e.getMessage());
                        }
                    }

                    buffer.setLength(0);
                }
                else if(text.length() > 0)
                {
                    buffer.append(line);
                    buffer.append("\r\n");
                }
            }
        }
        finally
        {
            if(reader != null)
            {
                try
                {
                    reader.close();
                }
                catch(IOException e)
                {
                }
            }

            Jdbc.close(statement);
        }
    }

    /**
     * @param sql
     * @throws SQLException
     */
    public void update(String sql) throws SQLException
    {
        this.execute(sql);
    }

    /**
     * @param sql
     * @throws SQLException
     */
    public void execute(String sql) throws SQLException
    {
        Statement statement = null;

        try
        {
            this.log(sql.trim());
            statement = this.connection.createStatement();
            statement.executeUpdate(sql);
        }
        finally
        {
            Jdbc.close(statement);
        }
    }

    /**
     * @param resultSet
     * @param limit
     * @throws IOException
     * @throws SQLException
     */
    private void print(ResultSet resultSet, int limit) throws IOException, SQLException
    {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int count = metaData.getColumnCount();
        String[] columnNames = new String[count];

        for(int i = 0; i < count; i++)
        {
            columnNames[i] = metaData.getColumnName(i + 1);
        }

        int rows = 0;

        while(resultSet.next())
        {
            for(int i = 0; i < count; i++)
            {
                this.log(resultSet.getObject(columnNames[i]) + " ");
            }

            if(limit > 0 && rows++ >= limit)
            {
                break;
            }
        }
    }

    /**
     */
    protected void commit()
    {
        try
        {
            if(this.connection != null)
            {
                this.connection.commit();
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     */
    protected void rollback()
    {
        try
        {
            if(this.connection != null)
            {
                this.connection.rollback();
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     */
    protected void disconnect()
    {
        try
        {
            if(this.connection != null)
            {
                this.connection.close();
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @param text
     * @return boolean
     */
    protected boolean command(String text)
    {
        return false;
    }

    /**
     * @param text
     * @return boolean
     */
    protected boolean sql(String text)
    {
        return false;
    }

    /**
     * @param text
     * @return String
     */
    protected String unquote(String text)
    {
        int i = 0;
        int j = text.length() - 1;
        char c = text.charAt(0);

        if(c == '"' || c == '\'')
        {
            i = 1;
        }

        c = text.charAt(j);

        if(c == ';')
        {
            j--;
            c = text.charAt(j);
        }

        if(c == '"' || c == '\'')
        {
            j--;
        }

        if(i == 0 && j == (text.length() - 1))
        {
            return text;
        }
        return text.substring(i, j + 1);
    }

    /**
     * @param text
     * @return String
     */
    protected String format(String text)
    {
        char c;
        char n;
        StringBuilder buffer = new StringBuilder();

        for(int i = 0, length = text.length(); i < length; i++)
        {
            c = text.charAt(i);

            switch(c)
            {
                case '\r':
                {
                    break;
                }
                case '\n':
                case ' ':
                {
                    n = buffer.charAt(buffer.length() - 1);

                    if(n != ' ' && n != '(')
                    {
                        buffer.append(' ');
                        n = ' ';
                    }

                    break;
                }
                default:
                {
                    buffer.append(c);
                }
            }
        }

        return buffer.toString();
    }

    /**
     * @param info
     */
    public void log(String info)
    {
        if(this.logger != null)
        {
            this.logger.log(info);
        }
    }

    /**
     * @return the home
     */
    public String getHome()
    {
        return this.home;
    }

    /**
     * @param home the home to set
     */
    public void setHome(String home)
    {
        this.home = home;
    }

    /**
     * @return the logger
     */
    public Logger getLogger()
    {
        return this.logger;
    }

    /**
     * @param logger the logger to set
     */
    public void setLogger(Logger logger)
    {
        this.logger = logger;
    }

    /**
     * @return Connection
     */
    public Connection getConnection()
    {
        return this.connection;
    }

    /**
     * @param connection
     */
    public void setConnection(Connection connection)
    {
        this.connection = connection;
    }
}
