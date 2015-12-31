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

import com.skin.ayada.database.Column;

/**
 * <p>Title: AccessDialect</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class AccessDialect implements Dialect {
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
            // column.setColumnSize(0);
            column.setTypeName("MEMO");
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
            // column.setTypeName("INTEGER");
            // column.setColumnSize(0);
            result = "Integer";
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

            // column.setColumnSize(0);
            result = "Integer";
        }
        else if("COUNTER".equals(typeName)) {
            // column.setTypeName("INTEGER");
            // column.setColumnSize(0);
            result = "Integer";
        }
        else if("FLOAT".equals(typeName)) {
            // column.setColumnSize(0);
            result = "Float";
        }
        else if("CURRENCY".equals(typeName)) {
            // column.setColumnSize(0);
            result = "Float";
        }
        else if("DOUBLE".equals(typeName)) {
            // column.setColumnSize(0);
            result = "Double";
        }
        else if("LONG".equals(typeName)) {
            // column.setColumnSize(0);
            result = "Long";
        }
        else if("NUMBER".equals(typeName)) {
            // column.setColumnSize(0);
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
        }
        else if("TEXT".equals(typeName)) {
            result = "String";
        }
        else if("DATE".equals(typeName) || typeName.startsWith("DATE(")) {
            // column.setColumnSize(0);
            result = "java.util.Date";
        }
        else if("TIME".equals(typeName) || typeName.startsWith("TIME(")) {
            // column.setColumnSize(0);
            result = "java.sql.Timestamp";
        }
        else if("DATETIME".equals(typeName) || typeName.startsWith("TIME(")) {
            // column.setColumnSize(0);
            result = "java.sql.Timestamp";
        }
        else if("TIMESTAMP".equals(typeName) || typeName.startsWith("TIMESTAMP(")) {
            // column.setColumnSize(0);
            result = "java.sql.Timestamp";
        }
        else if("BLOB".equals(typeName)) {
            result = "byte[]";
            result = "java.io.InputStream";
        }
        else if("Clob".equals(typeName)) {
            result = "String";
        }
        else {
            System.out.println("Warnning: Unknown DataType: " + column.getTableName() + "." + column.getColumnName() + ": " + typeName);
        }

        return result;
    }

    @Override
    public String getTableName(String tableName) {
        return "[" + tableName.toUpperCase() + "]";
    }

    @Override
    public String getColumnName(String columnName) {
        return "[" + columnName.toUpperCase() + "]";
    }
}
