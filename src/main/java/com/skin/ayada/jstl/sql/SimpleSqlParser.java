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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.skin.ayada.io.StringStream;
import com.skin.ayada.util.IO;
import com.skin.database.dialect.Dialect;
import com.skin.database.sql.Column;
import com.skin.database.sql.Table;

/**
 * <p>Title: SqlParser</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * @author xuesong.net
 * @version 1.0
 */
public class SimpleSqlParser
{
    private Dialect dialect;

    public static void main(String[] args)
    {
        SimpleSqlParser parser = new SimpleSqlParser();

        try
        {
            String source = IO.read(new File("webapp/test.sql"), "UTF-8", 1024);
            System.out.println(source);
            Map<String, Table> map = parser.parse(source);

            for(Map.Entry<String, Table> entry : map.entrySet())
            {
                Table table = entry.getValue();
                System.out.println(table.getCreateString("`%s`"));
                System.out.println(table.getQueryString());
                System.out.println(table.getInsertString());
                System.out.println(table.getUpdateString());
    
                for(Column column : table.listColumns())
                {
                    System.out.println(column.toString());
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public SimpleSqlParser()
    {
    }

    /**
     * @param dialect
     */
    public SimpleSqlParser(Dialect dialect)
    {
        this.dialect = dialect;
    }

    /**
     * @param source
     * @return Map<String, Table>
     */
    public Map<String, Table> parse(String source)
    {
        Table table = null;
        StringStream stream = new StringStream(source);
        Map<String, Table> map = new HashMap<String, Table>();
        
        while((table = this.parse(stream)) != null)
        {
            map.put(table.getTableName(), table);
        }

        return map;
    }

    /**
     * CREATE [TEMPORARY] TABLE [IF NOT EXISTS] tbl_name
     * @param stream
     * @return Table
     */
    public Table parse(StringStream stream)
    {
        String token = this.getToken(stream);

        if(token.length() < 1)
        {
            return null;
        }

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

        String tableName = this.getTableName(stream);

        if(tableName.equalsIgnoreCase("IF"))
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

            tableName = this.getTableName(stream);
        }

        String className = this.toCamel(tableName);
        String variableName = Character.toLowerCase(className.charAt(0)) + className.substring(1);
        Table table = new Table(tableName);
        table.setTableCode(tableName);
        table.setTableName(tableName);
        table.setTableType("TABLE");
        table.setRemarks("");
        table.setQueryName(tableName);
        table.setClassName(className);
        table.setVariableName(variableName);

        this.skipWhitespace(stream);
        int i = stream.read();

        if(i != '(')
        {
            throw new RuntimeException("tableName: " + tableName + ", except '('");
        }

        while(true)
        {
            this.skipWhitespace(stream);
            i = stream.peek();

            if(i == -1 || i == ')')
            {
                break;
            }

            String columnName = this.getColumnName(stream);

            /**
             * @TODO: keyword check
             */
            if(columnName.equalsIgnoreCase("PRIMARY") || columnName.equalsIgnoreCase("UNIQUE") || columnName.equalsIgnoreCase("KEY"))
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

            Column column = new Column(columnName);
            column.setColumnCode(columnName);
            String variable = java.beans.Introspector.decapitalize(this.toCamel(columnName));

            if("ID".equals(variable) == false)
            {
                variable = Character.toLowerCase(variable.charAt(0)) + variable.substring(1);
            }

            this.skipWhitespace(stream);
            String typeName = this.getToken(stream);

            column.setTypeName(typeName);
            column.setJavaTypeName(this.dialect.convert(column));
            column.setVariableName(variable);
            column.setMethodSetter("set" + this.toCamel(columnName));
            column.setMethodGetter("get" + this.toCamel(columnName));

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
     * @return String
     */
    public String getTableName(StringStream stream)
    {
        return this.getWord(stream);
    }

    /**
     * @param stream
     * @return String
     */
    public String getColumnName(StringStream stream)
    {
        return this.getWord(stream);
    }
    
    /**
     * @param stream
     * @return String
     */
    public String getWord(StringStream stream)
    {
        int c = 0;
        char quoto = '\0';
        StringBuilder buffer = new StringBuilder();
        this.skipWhitespace(stream);
        c = stream.read();

        if(c == '`' || c == '\'' || c == '"' || c == '[')
        {
            quoto = (char)c;
            this.skipWhitespace(stream);
        }
        else
        {
            stream.back();
        }

        while((c = stream.read()) != -1)
        {
            if(this.isSqlIdentifierPart(c))
            {
                buffer.append((char)c);
            }
            else
            {
                stream.back();
                break;
            }
        }

        String word = buffer.toString();
        this.skipWhitespace(stream);

        if(quoto != '\0')
        {
            c = stream.read();

            if(quoto == '[' && c != ']')
            {
                throw new RuntimeException(word + ": except ']'!");
            }
            else if(quoto != c)
            {
                throw new RuntimeException("column '" + word + "', except '" + quoto + "': found '" + (char)c);
            }
        }

        return word;
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
        
        String token = buffer.toString();
        
        if(token.equals("--"))
        {
            while((c = stream.read()) != -1)
            {
                if(c == '\n')
                {
                    break;
                }
            }

            return this.getToken(stream);
        }

        return token;
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

        return (i >= 97 && i <= 122) || (i >= 65 && i <= 90);
    }

    public boolean isSqlIdentifierPart(int i)
    {
        if(i == '_')
        {
            return true;
        }

        return (i >= 48 && i <= 57) || (i >= 97 && i <= 122) || (i >= 65 && i <= 90);
    }

    /**
     * @param name
     * @return String
     */
    public String toCamel(String name)
    {
        if(null == name || name.trim().length() < 1)
        {
            return "";
        }

        String[] subs = name.split("_");

        StringBuilder buf = new StringBuilder();

        if(name.startsWith("_"))
        {
            buf.append("_");
        }

        if(1 == subs.length)
        {
            String s = subs[0];

            if("ID".equals(s))
            {
                buf.append("Id");
            }
            else if(s.toUpperCase().equals(s))
            {
                // 如果全部都是大写
                buf.append(Character.toUpperCase(s.charAt(0)));
                buf.append(s.substring(1).toLowerCase());
            }
            else
            {
                buf.append(Character.toUpperCase(s.charAt(0))).append(s.substring(1));
            }
        }
        else
        {
            for(String s : subs)
            {
                if(s.length() > 0)
                {
                    // buf.append(Character.toUpperCase(s.charAt(0))).append(s.substring(1).toLowerCase());

                    if("ID".equals(s))
                    {
                        buf.append(s);
                    }
                    else if(s.toUpperCase().equals(s))
                    {
                        // 如果全部都是大写
                        buf.append(Character.toUpperCase(s.charAt(0)));
                        buf.append(s.substring(1).toLowerCase());
                    }
                    else
                    {
                        buf.append(Character.toUpperCase(s.charAt(0))).append(s.substring(1));
                    }
                }
            }
        }

        if(name.endsWith("_"))
        {
            buf.append("_");
        }

        return buf.toString();
    }

    /**
     * @return the dialect
     */
    public Dialect getDialect()
    {
        return this.dialect;
    }

    /**
     * @param dialect the dialect to set
     */
    public void setDialect(Dialect dialect)
    {
        this.dialect = dialect;
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
