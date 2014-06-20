/*
 * $RCSfile: Column.java,v $
 * $Revision: 1.1  $
 * $Date: 2009-02-16  $
 *
 * Copyright (C) 2005 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.sql;

/**
 * <p>Title: Column</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class Column
{
    private String alias;
    private String columnCode;
    private String columnName;
    private int dataType;
    private String typeName;
    private int autoIncrement;
    private int columnSize;
    private int decimalDigits;
    private String columnDef;
    private int nullable;
    private int precision;
    private boolean primaryKey;
    private String remarks;
    private String variableName;
    private String javaTypeName;
    private String methodSetter;
    private String methodGetter;
    private Object value;
    private Table table;

    public Column()
    {
    }

    public Column(String columnName)
    {
        this(columnName, null);
    }

    public Column(String columnName, String alias)
    {
        this.alias = alias;
        this.columnName = columnName;
        this.autoIncrement = 0;
        this.nullable = 1;
    }

    public Column(Column c)
    {
        if(c != null)
        {
            this.alias = c.alias;
            this.columnName = c.columnName;
            this.columnCode = c.columnCode;
            this.dataType = c.dataType;
            this.typeName = c.typeName;
            this.autoIncrement = c.autoIncrement;
            this.columnSize = c.columnSize;
            this.decimalDigits = c.decimalDigits;
            this.columnDef = c.columnDef;
            this.remarks = c.remarks;
            this.nullable = c.nullable;
            this.precision = c.precision;
            this.variableName = c.variableName;
            this.javaTypeName = c.javaTypeName;
            this.methodSetter = c.methodSetter;
            this.methodGetter = c.methodGetter;
            this.table = c.table;
        }
    }

    public void setTable(Table table)
    {
        this.table = table;
    }

    public Table getTable()
    {
        return this.table;
    }

    public String getTableName()
    {
        if(this.table != null)
        {
            return this.table.getTableName();
        }

        return null;
    }

    public String getTableAlias()
    {
        if(this.table != null)
        {
            return this.table.getAlias();
        }

        return null;
    }

    public String getAlias()
    {
        return this.alias;
    }

    public void setAlias(String alias)
    {
        this.alias = alias;
    }

    public void setColumnCode(String columnCode)
    {
        this.columnCode = columnCode;
    }

    public String getColumnCode()
    {
        return this.columnCode;
    }

    public void setColumnName(String columnName)
    {
        this.columnName = columnName;
    }

    public String getColumnName()
    {
        return this.columnName;
    }

    public int getDataType()
    {
        return this.dataType;
    }

    public void setDataType(int dataType)
    {
        this.dataType = dataType;
    }

    public String getTypeName()
    {
        return this.typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }
    
    /**
     * @return the autoIncrement
     */
    public int getAutoIncrement()
    {
        return this.autoIncrement;
    }

    /**
     * @param autoIncrement the autoIncrement to set
     */
    public void setAutoIncrement(int autoIncrement)
    {
        this.autoIncrement = autoIncrement;
    }

    public int getColumnSize()
    {
        return this.columnSize;
    }

    public void setColumnSize(int columnSize)
    {
        this.columnSize = columnSize;
    }

    public int getDecimalDigits()
    {
        return this.decimalDigits;
    }

    public void setDecimalDigits(int decimalDigits)
    {
        this.decimalDigits = decimalDigits;
    }

    public String getColumnDef()
    {
        return this.columnDef;
    }

    public void setColumnDef(String columnDef)
    {
        this.columnDef = columnDef;
    }

    public String getRemarks()
    {
        return this.remarks;
    }

    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }

    public int getNullable()
    {
        return this.nullable;
    }

    public void setNullable(int nullable)
    {
        this.nullable = nullable;
    }

    public int getPrecision()
    {
        return this.precision;
    }

    public void setPrecision(int precision)
    {
        this.precision = precision;
    }

    public void setPrimaryKey(boolean primaryKey)
    {
        this.primaryKey = primaryKey;
    }

    public boolean getPrimaryKey()
    {
        return this.primaryKey;
    }

    public String getVariableName()
    {
        return this.variableName;
    }

    public void setVariableName(String variableName)
    {
        this.variableName = variableName;
    }

    public String getJavaTypeName()
    {
        return this.javaTypeName;
    }

    public void setJavaTypeName(String javaTypeName)
    {
        this.javaTypeName = javaTypeName;
    }

    public String getMethodSetter()
    {
        return this.methodSetter;
    }

    public void setMethodSetter(String methodSetter)
    {
        this.methodSetter = methodSetter;
    }

    public String getMethodGetter()
    {
        return this.methodGetter;
    }

    public void setMethodGetter(String methodGetter)
    {
        this.methodGetter = methodGetter;
    }

    /**
     * @param object
     */
    public void setValue(Object object)
    {
        this.value = object;
    }

    /**
     * @return Object
     */
    public Object getValue()
    {
        return this.value;
    }

    /**
     * @return String
     */
    @Override
    public String toString()
    {
        StringBuilder buffer = new StringBuilder();
        buffer.append("<Column");
        buffer.append(" ColumnName=\"" + this.columnName).append("\"");
        buffer.append(" DataType=\"" + this.dataType).append("\"");
        buffer.append(" TypeName=\"" + this.typeName).append("\"");
        buffer.append(" Length=\"" + this.columnSize).append("\"");
        buffer.append(" DecimalDigits=\"" + this.decimalDigits).append("\"");
        buffer.append(" ColumnDef=\"" + this.columnDef).append("\"");
        buffer.append(" Remarks=\"" + this.remarks).append("\"");
        buffer.append(" Nullable=\"" + this.nullable).append("\"");
        buffer.append("/>");
        return buffer.toString();
    }
}
