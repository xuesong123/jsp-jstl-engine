/*
 * $RCSfile: IndexInfo.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-03-12 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.database;

/**
 * table_cat string => table catalog (may be null)
 * table_schem string => table schema (may be null)
 * table_name string => table name
 * non_unique boolean => can index values be non-unique. false when type is tableindexstatistic
 * index_qualifier string => index catalog (may be null); null when type is tableindexstatistic
 * index_name string => index name; null when type is tableindexstatistic
 * type short => index type:
 *     tableindexstatistic - this identifies table statistics that are returned in conjuction with a table's index descriptions
 *     tableindexclustered - this is a clustered index
 *     tableindexhashed - this is a hashed index
 *     tableindexother - this is some other style of index
 * ordinal_position short => column sequence number within index; zero when type is tableindexstatistic
 * column_name string => column name; null when type is tableindexstatistic
 * asc_or_desc string => column sort sequence, "a" => ascending, "d" => descending, may be null if sort sequence is not supported; null when type is tableindexstatistic
 * cardinality int => when type is tableindexstatistic, then this is the number of rows in the table; otherwise, it is the number of unique values in the index.
 * pages int => when type is tableindexstatisic then this is the number of pages used for the table, otherwise it is the number of pages used for the current index.
 * filter_condition string => filter condition, if any. (may be null)
 * 
 * <p>Title: IndexInfo</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class IndexInfo {
    private String catalog;
    private String tableSchem;
    private String tableName;
    private int nonUnique;
    private String indexQualifier;
    private String indexName;
    private String indexType;
    private int ordinalPosition;
    private String columnName;
    private String ascOrDesc;
    private int cardinality;
    private int pages;
    private String filterCondition;

    /**
     * @return the catalog
     */
    public String getCatalog() {
        return this.catalog;
    }
    
    /**
     * @param catalog the catalog to set
     */
    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }
    
    /**
     * @return the tableSchem
     */
    public String getTableSchem() {
        return this.tableSchem;
    }
    
    /**
     * @param tableSchem the tableSchem to set
     */
    public void setTableSchem(String tableSchem) {
        this.tableSchem = tableSchem;
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
     * @return the nonUnique
     */
    public int getNonUnique() {
        return this.nonUnique;
    }

    /**
     * @param nonUnique the nonUnique to set
     */
    public void setNonUnique(int nonUnique) {
        this.nonUnique = nonUnique;
    }
    
    /**
     * @return the indexQualifier
     */
    public String getIndexQualifier() {
        return this.indexQualifier;
    }
    
    /**
     * @param indexQualifier the indexQualifier to set
     */
    public void setIndexQualifier(String indexQualifier) {
        this.indexQualifier = indexQualifier;
    }
    
    /**
     * @return the indexName
     */
    public String getIndexName() {
        return this.indexName;
    }
    
    /**
     * @param indexName the indexName to set
     */
    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }
    
    /**
     * @return the indexType
     */
    public String getIndexType() {
        return this.indexType;
    }
    
    /**
     * @param indexType the indexType to set
     */
    public void setIndexType(String indexType) {
        this.indexType = indexType;
    }
    
    /**
     * @return the ordinalPosition
     */
    public int getOrdinalPosition() {
        return this.ordinalPosition;
    }
    
    /**
     * @param ordinalPosition the ordinalPosition to set
     */
    public void setOrdinalPosition(int ordinalPosition) {
        this.ordinalPosition = ordinalPosition;
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
     * @return the ascOrDesc
     */
    public String getAscOrDesc() {
        return this.ascOrDesc;
    }
    
    /**
     * @param ascOrDesc the ascOrDesc to set
     */
    public void setAscOrDesc(String ascOrDesc) {
        this.ascOrDesc = ascOrDesc;
    }
    
    /**
     * @return the cardinality
     */
    public int getCardinality() {
        return this.cardinality;
    }
    
    /**
     * @param cardinality the cardinality to set
     */
    public void setCardinality(int cardinality) {
        this.cardinality = cardinality;
    }
    
    /**
     * @return the pages
     */
    public int getPages() {
        return this.pages;
    }
    
    /**
     * @param pages the pages to set
     */
    public void setPages(int pages) {
        this.pages = pages;
    }
    
    /**
     * @return the filterCondition
     */
    public String getFilterCondition() {
        return this.filterCondition;
    }
    
    /**
     * @param filterCondition the filterCondition to set
     */
    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
    }
}
