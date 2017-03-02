/*
 * $RCSfile: AccessDialect.java,v $
 * $Revision: 1.1  $
 * $Date: 2009-3-22  $
 *
 * Copyright (C) 2005 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.database.dialect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skin.ayada.database.Column;

/**
 * <p>Title: AccessDialect</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class SQLServerDialect implements Dialect {
    private static final Logger logger = LoggerFactory.getLogger(SQLServerDialect.class);

    /**
     * @param column
     * @return String
     */
    @Override
    public String convert(Column column) {
        String result = "String";
        String typeName = column.getTypeName();

        if(typeName == null) {
            return result;
        }

        typeName = typeName.toUpperCase();

        if("CHAR".equals(typeName)) {

        }
        else if("CHARACTER".equals(typeName)) {

        }
        else if("VARCHAR".equals(typeName)) {

        }
        else if("VARCHAR2".equals(typeName)) {

        }
        else if("LONGCHAR".equals(typeName)) {
            column.setTypeName("MEMO");

            // column.setColumnSize(0);
        }
        else if("INT".equals(typeName)) {
            // Integer.MAX_VALUE: -2147483648
            // Integer.MAX_VALUE: +2147483647
            if(column.getColumnSize() >= 10) {
                result = "Long";
            }
            else {
                result = "Integer";
            }
        }
        else if("SMALLINT".equals(typeName)) {
            result = "Integer";

            // column.setTypeName("INTEGER");
            // column.setColumnSize(0);
        }
        else if("INTEGER".equals(typeName)) {
            // Integer.MAX_VALUE: -2147483648
            // Integer.MAX_VALUE: +2147483647
            if(column.getColumnSize() >= 10) {
                result = "Long";
            }
            else {
                result = "Integer";
            }

            result = "Integer";

            // column.setColumnSize(0);
        }
        else if("COUNTER".equals(typeName)) {
            result = "Integer";

            // column.setTypeName("INTEGER");

            // column.setColumnSize(0);
        }
        else if("FLOAT".equals(typeName)) {
            result = "Float";

            // column.setColumnSize(0);
        }
        else if("CURRENCY".equals(typeName)) {
            result = "Float";

            // column.setColumnSize(0);
        }
        else if("DOUBLE".equals(typeName)) {
            result = "Double";

            // column.setColumnSize(0);
        }
        else if("LONG".equals(typeName)) {
            result = "Long";

            // column.setColumnSize(0);
        }
        else if("NUMBER".equals(typeName)) {
            if(column.getDecimalDigits() > 0) {
                result = "Double";
            }
            else {
                // Integer.MAX_VALUE: -2147483648
                // Integer.MAX_VALUE: +2147483647
                if(column.getColumnSize() >= 10) {
                    result = "Long";
                }
                else {
                    result = "Integer";
                }

                result = "Integer";
            }

            // column.setColumnSize(0);
        }
        else if("TEXT".equals(typeName)) {
            result = "String";
        }
        else if("DATE".equals(typeName) || typeName.startsWith("DATE(")) {
            result = "java.util.Date";

            // column.setColumnSize(0);
        }
        else if("TIME".equals(typeName) || typeName.startsWith("TIME(")) {
            result = "java.sql.Timestamp";

            // column.setColumnSize(0);
        }
        else if("DATETIME".equals(typeName) || typeName.startsWith("TIME(")) {
            result = "java.sql.Timestamp";

            // column.setColumnSize(0);
        }
        else if("TIMESTAMP".equals(typeName) || typeName.startsWith("TIMESTAMP(")) {
            result = "java.sql.Timestamp";

            // column.setColumnSize(0);
        }
        else if("BLOB".equals(typeName)) {
            result = "byte[]";
            result = "java.io.InputStream";
        }
        else if("Clob".equals(typeName)) {
            result = "String";
        }
        else {
            logger.warn("Warnning: Unknown DataType: " + column.getTableName() + "." + column.getColumnName() + ": " + typeName);
        }
        return result;
    }

    /**
     * @param tableName
     */
    @Override
    public String getTableName(String tableName) {
        return "[" + tableName + "]";
    }

    /**
     * @param columnName
     */
    @Override
    public String getColumnName(String columnName) {
        return "[" + columnName + "]";
    }
}
