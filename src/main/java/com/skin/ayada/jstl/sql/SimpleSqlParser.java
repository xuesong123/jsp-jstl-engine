/*
 * $RCSfile: ConnectTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2014-03-25 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.sql;

import java.io.File;
import java.util.List;

import com.skin.ayada.io.StringStream;
import com.skin.ayada.util.IO;

/**
 * <p>Title: SqlParser</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * @author xuesong.net
 * @version 1.0
 */
public class SimpleSqlParser
{
    public static void main(String[] args)
    {
        SimpleSqlParser parser = new SimpleSqlParser();

        try
        {
            String source = IO.read(new File("webapp/test.sql"), "UTF-8", 1024);
            System.out.println(source);
            Table table = parser.parse(source);
            System.out.println(table.getCreateString());
            System.out.println(table.getQueryString());
            System.out.println(table.getInsertString());
            System.out.println(table.getUpdateString());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * CREATE [TEMPORARY] TABLE [IF NOT EXISTS] tbl_name
     * @param source
     * @return Table
     */
    public Table parse(String source)
    {
        StringStream stream = new StringStream(source);
        String token = this.getToken(stream);

        if(token.equalsIgnoreCase("CREATE") == false)
        {
            throw new RuntimeException("except keyword 'CREATE'!");
        }

        token = this.getToken(stream);

        if(token.equalsIgnoreCase("TEMPORARY"))
        {
            token = this.getToken(stream);
        }

        if(token.equalsIgnoreCase("TABLE") == false)
        {
            throw new RuntimeException("except keyword 'TABLE'!");
        }

        token = this.getToken(stream);
        
        if(token.equalsIgnoreCase("IF"))
        {
            token = this.getToken(stream);

            if(token.equalsIgnoreCase("NOT") == false)
            {
                throw new RuntimeException("except keyword 'NOT'!");
            }

            token = this.getToken(stream);

            if(token.equalsIgnoreCase("EXISTS") == false)
            {
                throw new RuntimeException("except keyword 'EXISTS'!");
            }
        }

        String tableName = token;
        Table table = new Table(tableName);

        this.skipWhitespace(stream);
        int i = stream.read();

        if(i != '(')
        {
            throw new RuntimeException("tableName: " + tableName + ", except '('");
        }

        while(true)
        {
            this.skipWhitespace(stream);
            i = stream.read();

            if(i == -1 || i == ')')
            {
                break;
            }

            char quoto = '\0';

            if(i == '`' || i == '\'' || i == '"' || i == '[')
            {
                quoto = (char)i;
            }
            else
            {
                stream.back();
            }

            token = this.getToken(stream);

            /**
             * @TODO: keyword check
             */
            if(token.equalsIgnoreCase("PRIMARY") || token.equalsIgnoreCase("UNIQUE") || token.equalsIgnoreCase("KEY"))
            {
                while((i = stream.read()) != -1)
                {
                    if(i == '\n')
                    {
                        break;
                    }
                }
                continue;
            }

            Column column = new Column(token);
            this.skipWhitespace(stream);

            if(quoto != '\0')
            {
                i = stream.read();

                if(quoto == '[')
                {
                    if(i != ']')
                    {
                        throw new RuntimeException("except ']'!");
                    }
                }
                else if(quoto != i)
                {
                    throw new RuntimeException("column '" + token + "', except '" + quoto + "': found '" + (char)i);
                }
            }

            this.skipWhitespace(stream);
            String typeName = this.getToken(stream);
            column.setTypeName(typeName);

            this.skipWhitespace(stream);
            i = stream.read();

            if(i == '(')
            {
                Integer precision = this.getInteger(stream);

                if(precision == null)
                {
                    throw new RuntimeException(column.getColumnName() + ", except number!");
                }

                column.setPrecision(precision.intValue());
                this.skipWhitespace(stream);

                if(stream.read() != ')')
                {
                    throw new RuntimeException("except ')'!");
                }
            }
            else
            {
                stream.back();
            }

            while(true)
            {
                this.skipWhitespace(stream);
                i = stream.peek();

                if(i == -1 || i == ',')
                {
                    stream.read();
                    break;
                }

                if(i == ')')
                {
                    break;
                }

                token = this.getToken(stream);

                if(token.equalsIgnoreCase("NOT"))
                {
                    this.skipWhitespace(stream);
                    token = this.getToken(stream);

                    if(token.equalsIgnoreCase("NULL") == false)
                    {
                        throw new RuntimeException("except keyword 'NULL'!");
                    }

                    column.setNullable(0);
                }
                else if(token.equalsIgnoreCase("AUTO_INCREMENT"))
                {
                    column.setAutoIncrement(1);
                }
                else if(token.equalsIgnoreCase("DEFAULT"))
                {
                    this.skipWhitespace(stream);
                    i = stream.peek();

                    if(i == '\'')
                    {
                        this.getString(stream);
                    }
                    else
                    {
                        String defaultValue = this.getToken(stream);
                        
                        if(defaultValue.length() < 1)
                        {
                            throw new RuntimeException(column.getColumnName() + " - default value except default value!");
                        }

                        /*
                        Integer value = this.getInteger(stream);

                        if(value == null)
                        {
                            throw new RuntimeException(column.getColumnName() + " - default value except number!");
                        }
                        */
                    }
                }
                else if(token.equalsIgnoreCase("COMMENT"))
                {
                    String remarks = this.getString(stream);
                    column.setRemarks(remarks);
                }
                else if(token.equalsIgnoreCase("unsigned"))
                {
                }
                else
                {
                    throw new RuntimeException("unknown keyword '" + token + "'!");
                }
            }

            table.addColumn(column);
        }

        while((i = stream.read()) != -1)
        {
            if(i == ';')
            {
                break;
            }
        }

        return table;
    }

    /**
     * @param stream
     * @return Integer
     */
    public Integer getInteger(StringStream stream)
    {
        String token = this.getToken(stream);

        if(token.trim().length() < 1)
        {
            return null;
        }

        try
        {
            return Integer.parseInt(token);
        }
        catch(NumberFormatException e)
        {
        }

        return null;
    }

    /**
     * @param stream
     * @return String
     */
    public String getString(StringStream stream)
    {
        this.skipWhitespace(stream);

        if(stream.read() != '\'')
        {
            throw new RuntimeException("except keyword '\\''!");
        }

        int i = 0;
        StringBuilder buffer = new StringBuilder();

        while((i = stream.read()) != -1)
        {
            if(i == '\\')
            {
                this.unescape(stream, buffer);
            }
            else if(i == '\'')
            {
                break;
            }
            else
            {
                buffer.append((char)i);
            }
        }

        return buffer.toString();
    }

    /**
     * @param stream
     * @return String
     */
    public String getToken(StringStream stream)
    {
        int c = 0;
        StringBuilder buffer = new StringBuilder();
        this.skipWhitespace(stream);

        while((c = stream.read()) != -1)
        {
            if(c <= ' ' || c == '(' || c == ')' || c == ',')
            {
                stream.back();
                break;
            }
            else
            {
                buffer.append((char)c);
            }
        }

        return buffer.toString();
    }

    /**
     * skip whitespace
     */
    public void skipWhitespace(StringStream stream)
    {
        int i = 0;
        while((i = stream.read()) != -1)
        {
            if(i > ' ')
            {
                stream.back();
                break;
            }
        }
    }

    /**
     * @param stream
     * @param buffer
     */
    public void unescape(StringStream stream, StringBuilder buffer)
    {
        int c = stream.read();

        if(c < 0)
        {
            return;
        }

        switch(c)
        {
            case 'n':{
                buffer.append("\n");
                break;
            }
            case 't':
            {
                buffer.append("\t");
                break;
            }
            case 'b':
            {
                buffer.append("\b");
                break;
            }
            case 'r':
            {
                buffer.append("\r");
                break;
            }
            case 'f':
            {
                buffer.append("\f");
                break;
            }
            case '\'':
            {
                buffer.append("\'");
                break;
            }
            case '\"':
            {
                buffer.append("\"");
                break;
            }
            case '\\':
            {
                buffer.append("\\");
                break;
            }
            case 'u':
            {
                char[] cbuf = {'0', '0', '0', '0'};
                int i = stream.read(cbuf);

                if(i == 4)
                {
                    String hex = new String(cbuf);

                    try
                    {
                        int value = Integer.parseInt(hex, 16);
                        buffer.append((char)value);
                    }
                    catch(NumberFormatException e)
                    {
                    }
                }

                break;
            }
            default:
            {
                stream.back();

                char[] cbuf = {'0', '0', '0'};
                int i = stream.read(cbuf);

                if(i == 3)
                {
                    String hex = new String(cbuf);

                    try
                    {
                        int value = Integer.parseInt(hex, 16);
                        buffer.append((char)value);
                    }
                    catch(NumberFormatException e)
                    {
                    }
                }

                break;
            }
        }
    }

    public boolean isSqlIdentifierStart(int i)
    {
        if(i == '_')
        {
            return true;
        }

        return (i >= 97 && i <= 122) || (i >= 65 && i <= 97);
    }

    public boolean isSqlIdentifierPart(int i)
    {
        if(i == '_')
        {
            return true;
        }

        return (i >= 48 && i <= 57) || (i >= 97 && i <= 122) || (i >= 65 && i <= 97);
    }

    /**
     * @param table
     * @return String
     */
    public static String toString(Table table)
    {
        return toString(table, "%s");
    }

    /**
     * @param table
     * @param pattern
     * @return String
     */
    public static String toString(Table table, String pattern)
    {
        StringBuilder buffer = new StringBuilder();
        List<Column> columns = table.listColumns();
        buffer.append("CREATE TABLE ");
        buffer.append(String.format(pattern, table.getTableName()));
        buffer.append("(\r\n");

        for(int i = 0, size = columns.size(); i < size; i++)
        {
            Column column = columns.get(i);
            buffer.append("    ");
            buffer.append(String.format(pattern, column.getColumnName()));

            buffer.append(" ");
            buffer.append(column.getTypeName());

            if(column.getPrecision() > 0)
            {
                buffer.append("(");
                buffer.append(column.getPrecision());
                buffer.append(")");
            }

            if(column.getAutoIncrement() == 1)
            {
                buffer.append(" AUTO_INCREMENT");
            }

            if(column.getNullable() == 1)
            {
                buffer.append(" not null");
            }

            String remarks = column.getRemarks();

            if(remarks != null && remarks.length() > 0)
            {
                buffer.append(" comment '");
                buffer.append(remarks);
                buffer.append("'");
            }

            if(i < size - 1)
            {
                buffer.append(",");
            }

            buffer.append("\r\n");
        }

        buffer.append(");");
        return buffer.toString();
    }
}
