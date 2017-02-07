/*
 * $RCSfile: CreateParser.java,v $
 * $Revision: 1.1 $
 * $Date: 2014-03-25 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.sql.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.skin.ayada.database.Column;
import com.skin.ayada.database.IndexInfo;
import com.skin.ayada.database.Table;
import com.skin.ayada.database.dialect.Dialect;
import com.skin.ayada.io.StringStream;

/**
 * <p>Title: CreateParser</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * @author xuesong.net
 * @version 1.0
 */
public class CreateParser {
    private Dialect dialect;

    /**
     * default
     */
    public CreateParser() {
    }

    /**
     * @param dialect
     */
    public CreateParser(Dialect dialect) {
        this.dialect = dialect;
    }

    /**
     * @param source
     * @return List<Table>
     */
    public List<Table> parse(String source) {
        Table table = null;
        StringStream stream = new StringStream(source);
        List<Table> list = new ArrayList<Table>();

        while((table = this.parse(stream)) != null) {
            list.add(table);
        }
        return list;
    }

    /**
     * CREATE [TEMPORARY] TABLE [IF NOT EXISTS] tbl_name
     * @param stream
     * @return Table
     */
    public Table parse(StringStream stream) {
        String line = null;
        String token = null;
        String tableName = null;

        while(true) {
            token = SqlParser.getToken(stream);

            if(token.length() < 1) {
                return null;
            }

            if(token.equalsIgnoreCase("create")) {
                break;
            }

            while((line = stream.readLine()) != null) {
                line = line.trim();

                if(line.endsWith(";")) {
                    break;
                }
            }
        }

        token = SqlParser.getToken(stream);

        if(token.equalsIgnoreCase("TEMPORARY")) {
            token = SqlParser.getToken(stream);
        }

        if(token.equalsIgnoreCase("TABLE") == false) {
            throw new RuntimeException("expect keyword 'TABLE'!");
        }

        token = SqlParser.getWord(stream);

        if(token.equalsIgnoreCase("IF")) {
            token = SqlParser.getToken(stream);

            if(token.equalsIgnoreCase("NOT") == false) {
                throw new RuntimeException("expect keyword 'NOT'!");
            }

            token = SqlParser.getToken(stream);

            if(token.equalsIgnoreCase("EXISTS") == false) {
                throw new RuntimeException("expect keyword 'EXISTS'!");
            }

            stream.tryread("`", true);
            tableName = SqlParser.getWord(stream);
            stream.tryread("`", true);
        }
        else {
            tableName = token;
        }

        String className = SqlParser.camel(tableName);
        String variableName = Character.toLowerCase(className.charAt(0)) + className.substring(1);
        Table table = new Table(tableName);
        table.setTableCode(tableName);
        table.setTableName(tableName);
        table.setTableType("TABLE");
        table.setQueryName(tableName);
        table.setClassName(className);
        table.setVariableName(variableName);

        SqlParser.skipComment(stream);
        int i = stream.read();

        if(i != '(') {
            throw new RuntimeException("tableName: " + tableName + ", expect '('");
        }

        List<String> primaryKeyList = null;

        while(true) {
            SqlParser.skipComment(stream);
            String columnName = SqlParser.getWord(stream);

            if(columnName.length() < 1) {
                throw new RuntimeException("expect column name: " + stream.getRemain(10) + "...");
            }

            if(columnName.equalsIgnoreCase("PRIMARY")) {
                token = SqlParser.getToken(stream);

                if(token.equalsIgnoreCase("KEY") == false) {
                    throw new RuntimeException("expect keyword 'KEY'!");
                }
                primaryKeyList = SqlParser.getArray(stream);
            }
            else if(columnName.equalsIgnoreCase("UNIQUE")) {
                token = SqlParser.getToken(stream);

                if(token.equalsIgnoreCase("KEY") == false) {
                    throw new RuntimeException("expect keyword 'KEY'!");
                }

                String indexName = SqlParser.getWord(stream);
                List<String> indexList = SqlParser.getArray(stream);

                for(String name : indexList) {
                    IndexInfo indexInfo = new IndexInfo();
                    indexInfo.setTableName(tableName);
                    indexInfo.setIndexName(indexName);
                    indexInfo.setColumnName(name);
                    indexInfo.setNonUnique(0);
                    table.addIndex(indexInfo);
                }
            }
            else if(columnName.equalsIgnoreCase("KEY")) {
                String indexName = SqlParser.getWord(stream);
                List<String> indexList = SqlParser.getArray(stream);
                String indexType = null;
                i = stream.peek();

                if(i != ',' && i != ')') {
                    token = SqlParser.getToken(stream);

                    if(!token.equalsIgnoreCase("USING")) {
                        throw new RuntimeException("expect keyword 'USING', but found: " + token);
                    }

                    token = SqlParser.getToken(stream);

                    if(!token.equalsIgnoreCase("BTREE") && !token.equalsIgnoreCase("HASH")) {

                        throw new RuntimeException("expect keyword 'BTREE' or 'HASH', but found: " + token);
                    }
                    indexType = token;
                }

                for(String name : indexList) {
                    IndexInfo indexInfo = new IndexInfo();
                    indexInfo.setTableName(tableName);
                    indexInfo.setIndexName(indexName);
                    indexInfo.setColumnName(name);
                    indexInfo.setNonUnique(1);
                    indexInfo.setIndexType(indexType);
                    table.addIndex(indexInfo);
                }
            }
            else {
                Column column = this.getColumn(columnName, stream);

                if(column == null) {
                    break;
                }
                table.add(column);
            }

            i = stream.peek();

            if(i == ',') {
                stream.read();
            }
            else if(i == ')') {
                stream.read();
                break;
            }
        }
        SqlParser.skipComment(stream);

        if(stream.peek() != ';') {
            Map<String, String> attributes = SqlParser.getAttributes(stream);
            table.setAttributes(attributes);
            table.setRemarks(attributes.get("COMMENT"));
            attributes.remove("COMMENT");
        }

        /**
         * 读取表定义结束符
         */
        while(!stream.eof()) {
            i = stream.read();

            if(i == ';' || i == '\n') {
                break;
            }
        }

        if(primaryKeyList != null) {
            for(String primaryKey : primaryKeyList) {
                Column column = table.getColumn(primaryKey);

                if(column != null) {
                    column.setPrimaryKey(true);
                }
                else {
                    throw new RuntimeException("primaryKey \"" + primaryKey + "\" not found !");
                }
            }
        }

        List<IndexInfo> indexInfoList = table.getIndexs();
        table.setIndexs(this.merge(indexInfoList));
        return table;
    }

    /**
     * @param columnName
     * @param stream
     * @return Column
     */
    public Column getColumn(String columnName, StringStream stream) {
        Column column = new Column();
        String typeName = SqlParser.getToken(stream);
        String variable = java.beans.Introspector.decapitalize(SqlParser.camel(columnName));

        if(typeName.length() < 1) {
            throw new RuntimeException(columnName + ", expect data type: " + stream.getRemain(10) + "...");
        }

        column.setColumnName(columnName);
        column.setColumnCode(columnName);
        column.setTypeName(typeName);
        column.setUnsigned(false);
        column.setNullable(true);
        column.setAutoIncrement(false);
        column.setPrimaryKey(false);
        column.setJavaTypeName(this.dialect.convert(column));
        column.setVariableName(variable);
        column.setMethodSetter("set" + SqlParser.camel(columnName));
        column.setMethodGetter("get" + SqlParser.camel(columnName));

        if(stream.peek() == '(') {
            stream.read();
            Integer precision = SqlParser.getInteger(stream);

            if(precision == null) {
                throw new RuntimeException(column.getColumnName() + ", expect number!");
            }

            column.setColumnSize(precision.intValue());
            column.setDecimalDigits(0);

            if(stream.peek() == ',') {
                stream.read();
                precision = SqlParser.getInteger(stream);

                if(precision == null) {
                    throw new RuntimeException(column.getColumnName() + ", expect number!");
                }

                column.setDecimalDigits(precision);
                SqlParser.skipComment(stream);
            } 

            if(stream.read() != ')') {
                throw new RuntimeException("expect ')' at: " + stream.getRemain());
            }
        }

        while(true) {
            String token = SqlParser.getToken(stream);

            if(token.length() < 1) {
                break;
            }

            if(token.equalsIgnoreCase("NOT")) {
                token = SqlParser.getToken(stream);
    
                if(token.equalsIgnoreCase("NULL") == false) {
                    throw new RuntimeException("expect keyword 'NULL'!");
                }
                column.setNullable(false);
            }
            else if(token.equalsIgnoreCase("AUTO_INCREMENT")) {
                column.setAutoIncrement(true);
            }
            else if(token.equalsIgnoreCase("DEFAULT")) {
                String defaultValue = SqlParser.getWord(stream);
                column.setColumnDef(defaultValue);
            }
            else if(token.equalsIgnoreCase("COMMENT")) {
                String remarks = SqlParser.getString(stream);
                column.setRemarks(remarks);
            }
            else if(token.equalsIgnoreCase("UNSIGNED")) {
                column.setTypeName(typeName);
                column.setUnsigned(true);
            }
            else if(token.equalsIgnoreCase("null")) {
            }
            else {
                throw new RuntimeException("unknown keyword '" + token + "'!" + stream.getRemain(10));
            }
        }
        return column;
    }
    

    
    /**
     * @param indexInfoList
     * @return List<IndexInfo>
     */
    public List<IndexInfo> merge(List<IndexInfo> indexInfoList) {
        List<IndexInfo> result = new ArrayList<IndexInfo>();

        if(indexInfoList != null && indexInfoList.size() > 0) {
            Map<String, IndexInfo> group = new HashMap<String, IndexInfo>();

            for(IndexInfo indexInfo : indexInfoList) {
                String indexName = indexInfo.getIndexName();
                IndexInfo model = group.get(indexName);

                if(model == null) {
                    result.add(indexInfo);
                    group.put(indexName, indexInfo);
                }
                else {
                    String columnName = model.getColumnName();
                    model.setColumnName(columnName + ", " + indexInfo.getColumnName());
                }
            }
        }
        return result;
    }

    /**
     * @return the dialect
     */
    public Dialect getDialect() {
        return this.dialect;
    }

    /**
     * @param dialect the dialect to set
     */
    public void setDialect(Dialect dialect) {
        this.dialect = dialect;
    }
}
