/*
 * $RCSfile: Table.java,v $
 * $Revision: 1.1  $
 * $Date: 2009-02-16  $
 *
 * Copyright (C) 2005 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.sql;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>Title: Table</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class Table
{
    private String alias;
    private String tableCode;
    private String tableName;
    private String tableType;
    private String queryName;
    private String className;
    private String variableName;
    private String remarks;

    private List<Column> primaryKeys;
    private List<Column> columns;

    public Table()
    {
        this(null, null);
    }

    public Table(String tableName)
    {
        this(tableName, null);
    }

    public Table(String tableName, String alias)
    {
        this.alias = alias;
        this.tableName = tableName;
        this.primaryKeys = new ArrayList<Column>();
        this.columns = new ArrayList<Column>();
    }

    public Table(Table t)
    {
        this();

        if(t != null)
        {
            this.alias = t.alias;
            this.tableName = t.tableName;
            this.tableType = t.tableType;
            this.remarks = t.remarks;
            this.queryName = t.queryName;

            if(t.primaryKeys != null && !t.primaryKeys.isEmpty())
            {
                for(Iterator<Column> iterator = t.primaryKeys.iterator(); iterator.hasNext();)
                {
                    Column c = new Column(iterator.next());
                    c.setTable(this);
                    this.primaryKeys.add(c);
                }
            }

            if(t.columns != null && t.columns.size() > 0)
            {
                for(Column column : t.columns)
                {
                    Column c = new Column(column);
                    c.setTable(this);
                    this.columns.add(c);
                }
            }
        }
    }

    public String getAlias()
    {
        return this.alias;
    }

    public void setAlias(String alias)
    {
        this.alias = alias;
    }

    public String getTableCode()
    {
        return this.tableCode;
    }

    public void setTableCode(String tableCode)
    {
        this.tableCode = tableCode;
    }

    public String getTableName()
    {
        return this.tableName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public String getTableType()
    {
        return this.tableType;
    }

    public void setTableType(String tableType)
    {
        this.tableType = tableType;
    }

    public String getQueryName()
    {
        return this.queryName;
    }

    public void setQueryName(String queryName)
    {
        this.queryName = queryName;
    }

    public String getClassName()
    {
        return this.className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }

    public String getVariableName()
    {
        return this.variableName;
    }

    public void setVariableName(String variableName)
    {
        this.variableName = variableName;
    }

    public String getRemarks()
    {
        return this.remarks;
    }

    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }

    public List<Column> getPrimaryKeys()
    {
        return this.primaryKeys;
    }

    public void setPrimaryKeys(List<Column> primaryKeys)
    {
        this.primaryKeys = primaryKeys;
    }

    public List<Column> getColumns()
    {
        return this.columns;
    }

    public void setColumns(List<Column> columns)
    {
        this.columns = columns;
    }

    public void addPrimaryKey(Column column)
    {
        this.primaryKeys.add(column);
    }

    public void addColumn(Column column)
    {
        this.columns.add(column);
    }

    public void removeColumn(Column column)
    {
        this.columns.remove(column);
    }

    public void removePrimaryKey(Column column)
    {
        this.primaryKeys.remove(column);
    }

    public Column getColumn(String columnName)
    {
        if(columnName != null)
        {
            List<Column> list = this.getPrimaryKeys();

            if(list != null)
            {
                for(Iterator<Column> iterator = list.iterator(); iterator.hasNext();)
                {
                    Column c = iterator.next();

                    if(columnName.equals(c.getColumnName()))
                    {
                        return c;
                    }
                }
            }

            list = this.getColumns();

            if(list != null)
            {
                for(Iterator<Column> iterator = list.iterator(); iterator.hasNext();)
                {
                    Column c = iterator.next();

                    if(columnName.equals(c.getColumnName()))
                    {
                        return c;
                    }
                }
            }
        }

        return null;
    }

    public Column getPrimaryKey()
    {
        if(this.primaryKeys != null && !this.primaryKeys.isEmpty())
        {
            return this.primaryKeys.get(0);
        }

        return null;
    }

    public boolean contains(Column column)
    {
        if(this.primaryKeys != null && !this.primaryKeys.isEmpty())
        {
            if(this.primaryKeys.contains(column))
            {
                return true;
            }
        }

        if(this.columns != null && !this.columns.isEmpty())
        {
            if(this.columns.contains(column))
            {
                return true;
            }
        }

        return false;
    }

    public int getColumnCount()
    {
        int count = 0;

        if(this.primaryKeys != null && !this.primaryKeys.isEmpty())
        {
            count = count + this.primaryKeys.size();
        }

        if(this.columns != null && !this.columns.isEmpty())
        {
            count = count + this.columns.size();
        }

        return count;
    }

    /**
     * @return
     */
    public List<Column> listColumns()
    {
        List<Column> list = new ArrayList<Column>();

        if(this.primaryKeys != null && !this.primaryKeys.isEmpty())
        {
            list.addAll(this.primaryKeys);
        }

        if(this.columns != null && !this.columns.isEmpty())
        {
            list.addAll(this.columns);
        }

        return list;
    }

    /**
     * @return String
     */
    public String getInsertString()
    {
        List<Column> columns = this.listColumns();
        StringBuilder buffer = new StringBuilder("INSERT INTO ");
        buffer.append(this.getTableName());
        buffer.append("(");
        int size = columns.size() - 1;

        for(int i = 0; i < size; i++)
        {
            Column column = columns.get(i);
            buffer.append(column.getColumnName()).append(", ");
        }

        if(size > 0)
        {
            Column column = columns.get(size);
            buffer.append(column.getColumnName());
        }

        buffer.append(") VALUES (");

        for(int i = 0; i < size; i++)
        {
            Column column = columns.get(i);
            buffer.append(column.getColumnName()).append(", ");
        }

        if(size > 0)
        {
            Column column = columns.get(size);
            buffer.append(column.getColumnName());
        }

        buffer.append(")");
        return buffer.toString();
    }

    /**
     * @return String
     */
    public String getUpdateString()
    {
        List<Column> columns = this.listColumns();
        StringBuilder buffer = new StringBuilder("UPDATE ");
        buffer.append(this.getTableName());
        buffer.append(" SET ");
        int size = columns.size() - 1;

        for(int i = 0; i < size; i++)
        {
            Column column = columns.get(i);
            buffer.append(column.getColumnName());
            buffer.append("=?, ");
        }

        if(size > 0)
        {
            Column column = columns.get(size);
            buffer.append(column.getColumnName());
            buffer.append("=?");
        }

        return buffer.toString();
    }

    /**
     * @return String
     */
    public String getQueryString()
    {
        List<Column> columns = this.listColumns();
        StringBuilder buffer = new StringBuilder("SELECT ");
        int size = columns.size() - 1;

        for(int i = 0; i < size; i++)
        {
            Column column = columns.get(i);
            buffer.append(column.getColumnName()).append(", ");
        }

        if(size > 0)
        {
            Column column = columns.get(size);
            buffer.append(column.getColumnName());
        }

        buffer.append(" FROM ").append(this.getQueryName());
        return buffer.toString();
    }

    /**
     * @return String
     */
    @Override
    public String toString()
    {
        return this.tableName;
    }
}
