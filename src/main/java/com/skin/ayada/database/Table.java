/*
 * $RCSfile: Table.java,v $
 * $Revision: 1.1 $
 * $Date: 2009-02-16 $
 *
 * Copyright (C) 2005 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: Table</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class Table {
    private String alias;
    private String tableCode;
    private String tableName;
    private String tableType;
    private String queryName;
    private String className;
    private String variableName;
    private String remarks;
    private List<Column> columns;
    private List<IndexInfo> indexs;
    private Map<String, String> attributes;

    /**
     * default
     */
    public Table() {
        this(null, null);
    }

    /**
     * @param tableName
     */
    public Table(String tableName) {
        this(tableName, null);
    }

    /**
     * @param tableName
     * @param alias
     */
    public Table(String tableName, String alias) {
        this.alias = alias;
        this.tableName = tableName;
        this.columns = new ArrayList<Column>();
    }

    /**
     * @param table
     */
    public Table(Table table) {
        this();

        if(table != null) {
            this.alias = table.alias;
            this.tableName = table.tableName;
            this.tableType = table.tableType;
            this.remarks = table.remarks;
            this.queryName = table.queryName;

            if(table.columns != null && !table.columns.isEmpty()) {
                Iterator<Column> iterator = table.columns.iterator();
                
                while(iterator.hasNext()) {
                    Column column = new Column(iterator.next());
                    column.setTable(this);
                    this.columns.add(column);
                }
            }
        }
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
     * @return the tableCode
     */
    public String getTableCode() {
        return this.tableCode;
    }

    /**
     * @param tableCode the tableCode to set
     */
    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    /**
     * @return the tableName
     */
    public String getTableName() {
        return this.tableName;
    }

    /**
     * @param tableName the tableName to set
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * @return the tableType
     */
    public String getTableType() {
        return this.tableType;
    }

    /**
     * @param tableType the tableType to set
     */
    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    /**
     * @return the queryName
     */
    public String getQueryName() {
        return this.queryName;
    }

    /**
     * @param queryName the queryName to set
     */
    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    /**
     * @return the className
     */
    public String getClassName() {
        return this.className;
    }

    /**
     * @param className the className to set
     */
    public void setClassName(String className) {
        this.className = className;
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
     * @return the primaryKeys
     */
    public List<Column> getPrimaryKeys() {
        List<Column> primaryKeys = new ArrayList<Column>();

        if(this.columns != null) {
            for(Column column : this.columns) {
                if(column.getPrimaryKey()) {
                    primaryKeys.add(column);
                }
            }
        }
        return primaryKeys;
    }

    /**
     * @return the columns
     */
    public List<Column> getColumns() {
        if(this.columns == null) {
            this.columns = new ArrayList<Column>();
        }
        return this.columns;
    }

    /**
     * @param primaryKey
     * @return the columns
     */
    public List<Column> getColumns(boolean primaryKey) {
        if(primaryKey) {
            return this.getColumns();
        }

        List<Column> list = new ArrayList<Column>();

        if(this.columns != null) {
            for(Column column : this.columns) {
                if(!column.getPrimaryKey()) {
                    list.add(column);
                }
            }
        }
        return list;
    }

    /**
     * @param columns the columns to set
     */
    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    /**
     * @return the indexs
     */
    public List<IndexInfo> getIndexs() {
        return this.indexs;
    }

    /**
     * @param indexs the indexs to set
     */
    public void setIndexs(List<IndexInfo> indexs) {
        this.indexs = indexs;
    }

    /**
     * @return the attributes
     */
    public Map<String, String> getAttributes() {
        return this.attributes;
    }

    /**
     * @param attributes the attributes to set
     */
    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    /**
     * @param indexs the indexs to set
     */
    public void addIndex(List<IndexInfo> indexs) {
        if(this.indexs == null) {
            this.indexs = new ArrayList<IndexInfo>();
        }
        this.indexs.addAll(indexs);
    }

    /**
     * @param indexInfo
     */
    public void addIndex(IndexInfo indexInfo) {
        if(this.indexs == null) {
            this.indexs = new ArrayList<IndexInfo>();
        }
        this.indexs.add(indexInfo);
    }

    /**
     * @param column
     */
    public void add(Column column) {
        this.columns.add(column);
    }

    /**
     * @param column
     */
    public void remove(Column column) {
        this.columns.remove(column);
    }

    /**
     * @param columnName
     * @return Column
     */
    public Column getColumn(String columnName) {
        if(columnName != null) {
            List<Column> list = this.getColumns();

            if(list != null) {
                for(Column column : list) {
                    if(columnName.equals(column.getColumnName())) {
                        return column;
                    }
                }
            }
        }
        return null;
    }

    /**
     * @return Column
     */
    public Column getPrimaryKey() {
        List<Column> list = this.getColumns();

        if(list != null) {
            for(Column column : list) {
                if(column.getPrimaryKey()) {
                    return column;
                }
            }
        }
        return null;
    }

    /**
     * @param column
     * @return boolean
     */
    public boolean contains(Column column) {
        if(this.columns != null && this.columns.size() > 0) {
            if(this.columns.contains(column)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return int
     */
    public int getColumnCount() {
        if(this.columns != null) {
            return this.columns.size();
        }
        else {
            return 0;
        }
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        return this.tableName;
    }
}
