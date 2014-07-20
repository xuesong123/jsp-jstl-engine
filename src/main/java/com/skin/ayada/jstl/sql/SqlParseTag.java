/*
 * $RCSfile: SqlParseTag.java,v $$
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
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.skin.ayada.tagext.BodyTag;
import com.skin.ayada.tagext.BodyTagSupport;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.util.ClassUtil;
import com.skin.ayada.util.IO;
import com.skin.ayada.util.StringUtil;
import com.skin.database.dialect.Dialect;
import com.skin.database.dialect.MySQLDialect;
import com.skin.database.sql.Column;
import com.skin.database.sql.Table;

/**
 * <p>Title: SqlParseTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * @author xuesong.net
 * @version 1.0
 */
public class SqlParseTag extends BodyTagSupport
{
    private String name;
    private String file;
    private String charset;
    private String database;

    /**
     * @return int
     */
    @Override
    public int doStartTag()
    {
        if(this.file != null)
        {
            return Tag.SKIP_BODY;
        }

        return BodyTag.EVAL_BODY_BUFFERED;
    }

    /**
     * @return int
     */
    @Override
    public int doEndTag()
    {
        String sql = null;

        if(this.file != null)
        {
            try
            {
                if(this.charset == null)
                {
                    this.charset = "UTF-8";
                }

                sql = IO.read(new File(this.file), this.charset, 2048);
            }
            catch(IOException e)
            {
            }
        }
        else
        {
            if(this.bodyContent != null)
            {
                sql = this.bodyContent.getString().trim();
            }
        }

        if(sql != null)
        {
            Dialect dialect = this.getDialect(this.database);
            SimpleSqlParser parser = new SimpleSqlParser(dialect);
            Map<String, Table> map = parser.parse(sql);

            for(Map.Entry<String, Table> entry : map.entrySet())
            {
                String name = entry.getKey();
                Table table = entry.getValue();
                this.pageContext.setAttribute(name, table);
            }

            if(this.name != null)
            {
                this.pageContext.setAttribute(this.name, map);
            }
        }

        return Tag.EVAL_PAGE;
    }

    /**
     * @param table
     * @return Map<String, Object>
     */
    public Map<String, Object> getMap(Table table)
    {
        List<Column> columns = table.listColumns();
        Map<String, Object> map = new LinkedHashMap<String, Object>();

        if(columns != null)
        {
            for(Column column : columns)
            {
                map.put(column.getColumnName(), column);
            }
        }

        return map;
    }

    /**
     * @param productName
     * @return Dialect
     */
    public Dialect getDialect(String productName)
    {
        String className = null;

        if(productName != null && productName.length() > 1)
        {
            String product = StringUtil.replace(productName, " ",  "").toLowerCase();
            String[] names = {"DB2", "HSQL", "MySQL", "Oracle", "Oracle11g", "Oracle10g", "Oracle9", "SQLServer", "Access"};

            for(String name : names)
            {
                if(product.indexOf(name.toLowerCase()) > -1)
                {
                    className = Dialect.class.getName();
                    className = className.substring(0, className.length() - Dialect.class.getSimpleName().length());
                    className = className + name + "Dialect";
                    break;
                }
            }
        }

        if(className == null)
        {
            return new MySQLDialect();
        }

        Dialect dialect = null;

        try
        {
            dialect = (Dialect)(ClassUtil.getInstance(className));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return dialect;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the file
     */
    public String getFile()
    {
        return this.file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(String file)
    {
        this.file = file;
    }

    /**
     * @return the charset
     */
    public String getCharset()
    {
        return this.charset;
    }

    /**
     * @param charset the charset to set
     */
    public void setCharset(String charset)
    {
        this.charset = charset;
    }

    /**
     * @return the database
     */
    public String getDatabase()
    {
        return this.database;
    }

    /**
     * @param database the database to set
     */
    public void setDatabase(String database)
    {
        this.database = database;
    }
}
