/*
 * $RCSfile: MySQLDialect.java,v $
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
 * <p>Title: MySQLDialect</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class MySQLDialect implements Dialect
{
    @Override
    public String convert(Column column)
    {
        String result = "String";
        String typeName = column.getTypeName().toUpperCase();

        if("CHAR".equals(typeName) || typeName.startsWith("CHAR("))
        {
            /**
             * String
             */
        }
        else if("CHARACTER".equals(typeName) || typeName.startsWith("CHARACTER("))
        {
            /**
             * String
             */
        }
        else if("VARCHAR".equals(typeName) || typeName.startsWith("VARCHAR("))
        {
            /**
             * String
             */
        }
        else if("VARCHAR2".equals(typeName) || typeName.startsWith("VARCHAR2("))
        {
            /**
             * String
             */
        }
        else if("TEXT".equals(typeName))
        {
            /**
             * String
             */
        }
        else if("LONGTEXT".equals(typeName))
        {
            /**
             * String
             */
        }
        else if("ENUM".equals(typeName))
        {
            /**
             * String
             */
        }
        else if("BOOL".equals(typeName)
                || "BOOLEAN".equals(typeName)
                || typeName.startsWith("BOOL(")
                || typeName.startsWith("BOOLEAN("))
        {
            /**
             * These types are synonyms for TINYINT(1). A value of zero is considered false. Nonzero values are considered true: 
             */
            result = "boolean";
        }
        else if("BIT".equals(typeName) || typeName.startsWith("BIT("))
        {
            /**
             * A bit-field type. M indicates the number of bits per value, from 1 to 64. The default is 1 if M is omitted.
             * This data type was added in MySQL 5.0.3 for MyISAM, and extended in 5.0.5 to MEMORY, InnoDB, BDB, and NDBCLUSTER. Before 5.0.3, BIT is a
             * synonym for TINYINT(1).
             */
            result = "int";
        }
        else if("TINYINT".equals(typeName) || typeName.startsWith("TINYINT(") || "TINYINT UNSIGNED".equals(typeName) || typeName.startsWith("TINYINT UNSIGNED("))
        {
            /**
             * A very small integer. The signed range is -128 to 127.
             * The unsigned range is 0 to 255. 
             */
            result = "int";
        }
        else if("SMALLINT".equals(typeName) || typeName.startsWith("SMALLINT(") || "SMALLINT UNSIGNED".equals(typeName) || typeName.startsWith("SMALLINT UNSIGNED("))
        {
            /**
             * A small integer. The signed range is -32768 to 32767.
             * The unsigned range is 0 to 65535.
             */
            result = "int";
        }
        else if("MEDIUMINT".equals(typeName) || typeName.startsWith("MEDIUMINT(") || "MEDIUMINT UNSIGNED".equals(typeName) || typeName.startsWith("MEDIUMINT UNSIGNED("))
        {
            /**
             * A medium-sized integer. The signed range is -8388608 to 8388607.
             * The unsigned range is 0 to 16777215.
             */
            result = "int";
        }
        else if("INT".equals(typeName) || typeName.startsWith("INT(") || typeName.startsWith("INT "))
        {
            /**
             * A normal-size integer. The signed range is -2147483648 to 2147483647.
             * The unsigned range is 0 to 4294967295.
             * Integer.MAX_VALUE: -2147483648
             * Integer.MAX_VALUE: +2147483647
             */
            // Integer.MAX_VALUE: -2147483648
            // Integer.MAX_VALUE: +2147483647
            if(column.getColumnSize() >= 10)
            {
                result = "long";
            }
            else
            {
                result = "int";
            }
        }
        else if("BIGINT".equals(typeName) || typeName.startsWith("BIGINT("))
        {
            /**
             * A large integer. The signed range is -9223372036854775808 to 9223372036854775807.
             * The unsigned range is 0 to 18446744073709551615.
             */
            result = "long";
        }
        else if("INTEGER".equals(typeName) || typeName.startsWith("INTEGER("))
        {
            /**
             * This type is a synonym for INT. 
             * Integer.MAX_VALUE: -2147483648
             * Integer.MAX_VALUE: +2147483647
             */
            if(column.getColumnSize() >= 10)
            {
                result = "long";
            }
            else
            {
                result = "int";
            }
        }
        else if("FLOAT".equals(typeName) || typeName.startsWith("FLOAT("))
        {
            result = "float";
        }
        else if("DOUBLE".equals(typeName) || typeName.startsWith("DOUBLE("))
        {
            result = "double";
        }
        else if("LONG".equals(typeName) || typeName.startsWith("LONG("))
        {
            result = "long";
        }
        else if("NUMBER".equals(typeName) || typeName.startsWith("NUMBER("))
        {
            if(column.getDecimalDigits() > 0)
            {
                result = "double";
            }
            else
            {
                // Integer.MAX_VALUE: -2147483648
                // Integer.MAX_VALUE: +2147483647
                if(column.getColumnSize() >= 10)
                {
                    result = "long";
                }
                else
                {
                    result = "int";
                }
            }
        }
        else if("DATE".equals(typeName) || typeName.startsWith("DATE("))
        {
            result = "java.util.Date";
        }
        else if("TIME".equals(typeName) || typeName.startsWith("TIME("))
        {
            result = "java.util.Date";
        }
        else if("DATETIME".equals(typeName) || typeName.startsWith("DATETIME("))
        {
            result = "java.util.Date";
        }
        else if("TIMESTAMP".equals(typeName) || typeName.startsWith("TIMESTAMP("))
        {
            result = "java.util.Date";
        }
        else if("YEAR".equals(typeName) || typeName.startsWith("YEAR("))
        {
            result = "int";
        }
        else if("BLOB".equals(typeName))
        {
            result = "byte[]";
            result = "java.io.InputStream";
        }
        else if("CLOB".equals(typeName))
        {
            result = "String";
        }
        else if("RAW".equals(typeName) || typeName.startsWith("RAW("))
        {
            return "java.math.BigDecimal";
        }
        else
        {
            System.out.println("Warnning: Unknown DataType: " + column.getTableName() + "." + column.getColumnName() + ": " + typeName);
        }

        return result;
    }

    @Override
    public String getTableName(String tableName)
    {
        return tableName;
    }

    @Override
    public String getColumnName(String columnName)
    {
        return columnName;
    }
}
