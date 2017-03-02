/*
 * $RCSfile: OracleDialect.java,v $
 * $Revision: 1.1  $
 * $Date: 2009-3-1  $
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
 * <p>Title: OracleDialect</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class OracleDialect implements Dialect {
    private static final Logger logger = LoggerFactory.getLogger(OracleDialect.class);

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
        else if("INTEGER".equals(typeName)) {
            // Integer.MAX_VALUE: -2147483648
            // Integer.MAX_VALUE: +2147483647
            if(column.getColumnSize() >= 10) {
                result = "Long";
            }
            else {
                result = "Integer";
            }
        }
        else if("FLOAT".equals(typeName)) {
            result = "Float";
        }
        else if("DOUBLE".equals(typeName)) {
            result = "Double";
        }
        else if("LONG".equals(typeName)) {
            result = "Long";
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
            }
        }
        else if("DATE".equals(typeName) || typeName.startsWith("DATE(")) {
            result = "java.util.Date";
        }
        else if("TIME".equals(typeName) || typeName.startsWith("TIME(")) {
            result = "java.sql.Timestamp";
        }
        else if("TIMESTAMP".equals(typeName) || typeName.startsWith("TIMESTAMP(")) {
            result = "java.sql.Timestamp";
        }
        else if("BLOB".equals(typeName)) {
            result = "byte[]";
            result = "java.io.InputStream";
        }
        else if("Clob".equals(typeName)) {
            // result = "java.io.Reader";
            result = "String";
        }
        else if("RAW".equals(typeName) || typeName.startsWith("RAW(")) {
            /*
            RAW，类似于CHAR，声明方式RAW(L)，L为长度，以字节为单位，作为数据库列最大2000，作为变量最大32767字节。
            LONG RAW，类似于LONG，作为数据库列最大存储2G字节的数据，作为变量最大32760字节
                                建表操作: create table raw_test (id number, raw_date raw(10));
                                插入raw数据操作: insert into raw_test values (1, hextoraw('ff')); insert into raw_test values (utl_raw.cast_to_raw('051'));
                                删除表操作: drop table raw_test;
                                当使用HEXTORAW时，会把字符串中数据当作16进制数。而使用UTL_RAW.CAST_TO_RAW时，直接把字符串中每个字符的ASCII码存放到RAW类型的字段中
            */
            return "java.math.BigDecimal";
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
        return tableName;
    }

    /**
     * @param columnName
     */
    @Override
    public String getColumnName(String columnName) {
        return columnName;
    }
}
