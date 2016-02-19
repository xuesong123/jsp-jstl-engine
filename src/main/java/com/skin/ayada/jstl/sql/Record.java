/*
 * $RCSfile: Record.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-08 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.sql;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>Title: Record</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class Record {
    private String tableName;
    private List<Entry> columns;
    private List<Entry> otherColumns;

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
     * @return the columns
     */
    public List<Entry> getColumns() {
        return this.columns;
    }

    /**
     * @param columns the columns to set
     */
    public void setColumns(List<Entry> columns) {
        this.columns = columns;
    }

    /**
     * @param columnName
     * @param columnValue
     */
    public void addColumn(String columnName, Object columnValue) {
        if(this.columns == null) {
            this.columns = new ArrayList<Entry>();
        }
        this.columns.add(new Entry(columnName, columnValue));
    }

    /**
     * @param index
     * @return Object
     */
    public Object getColumnValue(int index) {
        if(index < 1 || index > this.columns.size()) {
            throw new ArrayIndexOutOfBoundsException("index must be > 0 && <= " + this.columns.size());
        }
        return this.columns.get(index - 1).getValue();
    }

    /**
     * @param columnName
     * @param columnValue
     */
    public void setColumn(String columnName, Object columnValue) {
        for(Entry entry : this.columns) {
            if(entry.getName().equalsIgnoreCase(columnName)) {
                entry.setValue(columnValue);
                return;
            }
        }
        throw new RuntimeException("NoSuchColumnException !");
    }

    /**
     * @param columnName
     * @return Object
     */
    public Object getColumnValue(String columnName) {
        for(Entry entry : this.columns) {
            if(entry.getName().equalsIgnoreCase(columnName)) {
                return entry.getValue();
            }
        }
        throw new RuntimeException("NoSuchColumnException !");
    }

    /**
     * @param columnName
     * @return String
     */
    public String getString(String columnName) {
        Object value = this.getColumnValue(columnName);

        if(value == null) {
            return null;
        }

        if(value instanceof String) {
            return (String)value;
        }
        return value.toString();
    }

    /**
     * @param columnName
     * @return Long
     */
    public int getInt(String columnName) {
        Object value = this.getColumnValue(columnName);

        if(value == null) {
            return 0;
        }

        if(value instanceof Integer) {
            return ((Integer)value).intValue();
        }

        if(value instanceof Number) {
            return ((Number)value).intValue();
        }

        if(value instanceof String) {
            return Integer.parseInt((String)value);
        }
        return 0;
    }

    /**
     * @param columnName
     * @return Long
     */
    public long getLong(String columnName) {
        Object value = this.getColumnValue(columnName);

        if(value == null) {
            return 0L;
        }

        if(value instanceof Long) {
            return ((Long)value).longValue();
        }

        if(value instanceof Number) {
            return ((Number)value).longValue();
        }

        if(value instanceof String) {
            return Long.parseLong((String)value);
        }
        return 0L;
    }

    /**
     * @param columnName
     * @param pattern
     * @return Date
     */
    public Date getDate(String columnName, String pattern) {
        Object value = this.getColumnValue(columnName);

        if(value instanceof Date) {
            return (Date)value;
        }

        if(value instanceof Number) {
            long timeMillis = ((Number)value).longValue();
            return new Date(timeMillis);
        }

        if(value instanceof String) {
            String date = (String)value;
            try {
                return new SimpleDateFormat(pattern).parse(date);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    /**
     * @param columnName
     * @param columnValue
     */
    public void addOtherColumn(String columnName, Object columnValue) {
        if(this.otherColumns == null) {
            this.otherColumns = new ArrayList<Entry>();
        }
        this.otherColumns.add(new Entry(columnName, columnValue));
    }

    /**
     * @param columnName
     * @param columnValue
     */
    public void setOtherColumn(String columnName, Object columnValue) {
        for(Entry entry : this.otherColumns) {
            if(entry.getName().equalsIgnoreCase(columnName)) {
                entry.setValue(columnValue);
                return;
            }
        }
        throw new RuntimeException("NoSuchColumnException !");
    }

    /**
     * @param index
     * @return Object
     */
    public Object getOtherColumnValue(int index) {
        if(index < 1 || index > this.otherColumns.size()) {
            throw new ArrayIndexOutOfBoundsException("index must be > 0 && <= " + this.columns.size());
        }
        return this.otherColumns.get(index - 1).getValue();
    }

    /**
     * @param columnName
     * @return Object
     */
    public Object getOtherColumnValue(String columnName) {
        for(Entry entry : this.otherColumns) {
            if(entry.getName().equalsIgnoreCase(columnName)) {
                return entry.getValue();
            }
        }
        throw new RuntimeException("NoSuchColumnException !");
    }

    /**
     * @return List<Object>
     */
    public List<Object> getColumnValueList() {
        List<Object> list = new ArrayList<Object>(this.columns.size());

        for(Entry entry : this.columns) {
            list.add(entry.getValue());
        }
        return list;
    }

    /**
     * @return List<Object>
     */
    public Object[] getColumnValueArray() {
        int i = 0;
        Object[] array = new Object[this.columns.size()];

        for(Entry entry : this.columns) {
            array[i] = entry.getValue();
            i++;
        }
        return array;
    }

    /**
     * @param source
     * @return String
     */
    public String escape(String source) {
        StringBuilder buffer = new StringBuilder();

        if(source != null) {
            char c;

            for(int i = 0, size = source.length(); i < size; i++) {
                c = source.charAt(i);

                switch (c) {
                    case '\\': {
                        buffer.append("\\\\"); break;
                    }
                    case '\'': {
                        buffer.append("\\\'"); break;
                    }
                    case '"': {
                        buffer.append("\\\""); break;
                    }
                    case '\r': {
                        buffer.append("\\r"); break;
                    }
                    case '\n': {
                        buffer.append("\\n"); break;
                    }
                    case '\t': {
                        buffer.append("\\t"); break;
                    }
                    case '\b': {
                        buffer.append("\\b"); break;
                    }
                    case '\f': {
                        buffer.append("\\f"); break;
                    }
                    default : {
                        buffer.append(c); break;
                    }
                }
            }
        }
        return buffer.toString();
    }

    /**
     * @param pattern
     * @param prepared
     * @return String
     */
    public String getInsertSql(String pattern, boolean prepared) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("insert into ");
        buffer.append(String.format(pattern, this.tableName));
        buffer.append("(");

        for(int i = 0; i < this.columns.size(); i++) {
            String columnName = this.columns.get(i).getName();
            buffer.append(String.format(pattern, columnName));

            if((i + 1) < this.columns.size()) {
                buffer.append(", ");
            }
        }
        buffer.append(") values (");

        if(prepared) {
            for(int i = 0; i < this.columns.size(); i++) {
                if((i + 1) < this.columns.size()) {
                    buffer.append("?, ");
                }
                else {
                    buffer.append("?");
                }
            }
        }
        else {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            for(int i = 0; i < this.columns.size(); i++) {
                Object columnValue = this.columns.get(i).getValue();

                if(columnValue instanceof String) {
                    buffer.append("'");
                    buffer.append(this.escape((String)columnValue));
                    buffer.append("'");
                }
                else if(columnValue instanceof java.util.Date) {
                    buffer.append("'");
                    buffer.append(dateFormat.format((java.util.Date)columnValue));
                    buffer.append("'");
                }
                else {
                    buffer.append(columnValue);
                }

                if((i + 1) < this.columns.size()) {
                    buffer.append(", ");
                }
            }
        }
        buffer.append(");");
        return buffer.toString();
    }

    /**
     * @param pattern
     * @param prepared
     * @return String
     */
    public String getUpdateSql(String pattern, boolean prepared) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuilder buffer = new StringBuilder();
        buffer.append("update ");
        buffer.append(String.format(pattern, this.tableName));
        buffer.append(" set ");

        for(int i = 0; i < this.columns.size(); i++) {
            String columnName = this.columns.get(i).getName();
            buffer.append(String.format(pattern, columnName));

            if(prepared) {
                buffer.append("=?");
            }
            else {
                buffer.append("=");
                Object columnValue = this.columns.get(i).getValue();

                if(columnValue instanceof String) {
                    buffer.append("'");
                    buffer.append(this.escape((String)columnValue));
                    buffer.append("'");
                }
                else if(columnValue instanceof java.util.Date) {
                    buffer.append("'");
                    buffer.append(dateFormat.format((java.util.Date)columnValue));
                    buffer.append("'");
                }
                else {
                    buffer.append(columnValue);
                }
            }

            if((i + 1) < this.columns.size()) {
                buffer.append(", ");
            }
        }
        buffer.append(" where 1=2;");
        return buffer.toString();
    }

    /**
     *
     */
    public void print() {
        System.out.println("===================== record =====================");
        for(Entry entry : this.columns) {
            System.out.println(entry.getName() + ": " + entry.getValue());
        }
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        return this.getInsertSql("%s", false);
    }

    /**
     * @param pattern
     * @param prepared
     * @return String
     */
    public String toString(String pattern, boolean prepared) {
        return this.getInsertSql(pattern, prepared);
    }
}
