/*
 * $RCSfile: Column.java,v $
 * $Revision: 1.1 $
 * $Date: 2009-02-16 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.database;

/**
 * <p>Title: Column</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class Column {
    private Table table;
    private String alias;
    private String columnCode;
    private String columnName;
    private int dataType;
    private String typeName;
    private boolean autoIncrement;
    private boolean unsigned;
    private int columnSize;
    private int decimalDigits;
    private String columnDef;
    private boolean nullable;
    private int precision;
    private boolean primaryKey;
    private String remarks;
    private String variableName;
    private String javaTypeName;
    private String methodSetter;
    private String methodGetter;
    private Object value;

    /**
     * default
     */
    public Column() {
        this((String)null, (String)null);
    }

    /**
     * @param columnName
     */
    public Column(String columnName) {
        this(columnName, null);
    }

    /**
     * @param columnName
     * @param alias
     */
    public Column(String columnName, String alias) {
        this.alias = alias;
        this.columnName = columnName;
        this.unsigned = false;
        this.nullable = true;
        this.autoIncrement = false;
        this.primaryKey = false;
    }

    /**
     * @param column
     */
    public Column(Column column) {
        if(column != null) {
            this.alias = column.alias;
            this.columnName = column.columnName;
            this.dataType = column.dataType;
            this.typeName = column.typeName;
            this.autoIncrement = column.autoIncrement;
            this.unsigned = column.unsigned;
            this.columnSize = column.columnSize;
            this.decimalDigits = column.decimalDigits;
            this.columnDef = column.columnDef;
            this.remarks = column.remarks;
            this.nullable = column.nullable;
            this.precision = column.precision;
            this.variableName = column.variableName;
            this.javaTypeName = column.javaTypeName;
            this.methodSetter = column.methodSetter;
            this.methodGetter = column.methodGetter;
            this.table = column.table;
        }
    }

    /**
     * @return the table
     */
    public Table getTable() {
        return this.table;
    }

    /**
     * @param table the table to set
     */
    public void setTable(Table table) {
        this.table = table;
    }

    /**
     * @return String
     */
    public String getTableName() {
        if(this.table != null) {
            return this.table.getTableName();
        }
        else {
            return null;
        }
    }

    /**
     * @return String
     */
    public String getTableAlias() {
        if(this.table != null) {
            return this.table.getAlias();
        }
        return null;
    }

    /**
     * @return the alias
     */
    public String getAlias() {
        return this.alias;
    }

    /**
     * @param alias the alias to set
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * @return the columnCode
     */
    public String getColumnCode() {
        return this.columnCode;
    }

    /**
     * @param columnCode the columnCode to set
     */
    public void setColumnCode(String columnCode) {
        this.columnCode = columnCode;
    }

    /**
     * @return the columnName
     */
    public String getColumnName() {
        return this.columnName;
    }

    /**
     * @param columnName the columnName to set
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * @return the dataType
     */
    public int getDataType() {
        return this.dataType;
    }

    /**
     * @param dataType the dataType to set
     */
    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    /**
     * @return the typeName
     */
    public String getTypeName() {
        return this.typeName;
    }

    /**
     * @param typeName the typeName to set
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * @return the autoIncrement
     */
    public boolean getAutoIncrement() {
        return this.autoIncrement;
    }

    /**
     * @param autoIncrement the autoIncrement to set
     */
    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    /**
     * @return the unsigned
     */
    public boolean isUnsigned() {
        return this.unsigned;
    }

    /**
     * @param unsigned the unsigned to set
     */
    public void setUnsigned(boolean unsigned) {
        this.unsigned = unsigned;
    }

    /**
     * @return the columnSize
     */
    public int getColumnSize() {
        return this.columnSize;
    }

    /**
     * @param columnSize the columnSize to set
     */
    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    /**
     * @return the decimalDigits
     */
    public int getDecimalDigits() {
        return this.decimalDigits;
    }

    /**
     * @param decimalDigits the decimalDigits to set
     */
    public void setDecimalDigits(int decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    /**
     * @return the columnDef
     */
    public String getColumnDef() {
        return this.columnDef;
    }

    /**
     * @param columnDef the columnDef to set
     */
    public void setColumnDef(String columnDef) {
        this.columnDef = columnDef;
    }

    /**
     * @return the nullable
     */
    public boolean getNullable() {
        return this.nullable;
    }

    /**
     * @param nullable the nullable to set
     */
    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    /**
     * @return the precision
     */
    public int getPrecision() {
        return this.precision;
    }

    /**
     * @param precision the precision to set
     */
    public void setPrecision(int precision) {
        this.precision = precision;
    }

    /**
     * @return the primaryKey
     */
    public boolean getPrimaryKey() {
        return this.primaryKey;
    }

    /**
     * @param primaryKey the primaryKey to set
     */
    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    /**
     * @return the remarks
     */
    public String getRemarks() {
        return this.remarks;
    }

    /**
     * @param remarks the remarks to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * @return the variableName
     */
    public String getVariableName() {
        return this.variableName;
    }

    /**
     * @param variableName the variableName to set
     */
    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    /**
     * @return the javaTypeName
     */
    public String getJavaTypeName() {
        return this.javaTypeName;
    }

    /**
     * @param javaTypeName the javaTypeName to set
     */
    public void setJavaTypeName(String javaTypeName) {
        this.javaTypeName = javaTypeName;
    }

    /**
     * @return the methodSetter
     */
    public String getMethodSetter() {
        return this.methodSetter;
    }

    /**
     * @param methodSetter the methodSetter to set
     */
    public void setMethodSetter(String methodSetter) {
        this.methodSetter = methodSetter;
    }

    /**
     * @return the methodGetter
     */
    public String getMethodGetter() {
        return this.methodGetter;
    }

    /**
     * @param methodGetter the methodGetter to set
     */
    public void setMethodGetter(String methodGetter) {
        this.methodGetter = methodGetter;
    }

    /**
     * @param object
     */
    public void setValue(Object object) {
        this.value = object;
    }

    /**
     * @return Object
     */
    public Object getValue() {
        return this.value;
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("<column");
        buffer.append(" columnName=\"" + this.columnName).append("\"");
        buffer.append(" dataType=\"" + this.dataType).append("\"");
        buffer.append(" typeName=\"" + this.typeName).append("\"");
        buffer.append(" length=\"" + this.columnSize).append("\"");
        buffer.append(" decimalDigits=\"" + this.decimalDigits).append("\"");
        buffer.append(" columnDef=\"" + this.columnDef).append("\"");
        buffer.append(" remarks=\"" + this.remarks).append("\"");
        buffer.append(" nullable=\"" + this.nullable).append("\"");
        buffer.append("/>");
        return buffer.toString();
    }
}
