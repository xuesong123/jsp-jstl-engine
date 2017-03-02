/*
 * $RCSfile: DataSet.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-04-25 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.csv;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: DataSet</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class DataSet {
    private Map<String, Integer> headers;
    private List<String> data;

    /**
     * @param headers
     * @param data
     */
    public DataSet(List<String> headers, List<String> data) {
        this.headers = getHeaders(headers);
        this.data = data;
    }

    /**
     * @param headers the headers to set
     */
    public void setHeaders(Map<String, Integer> headers) {
        this.headers = headers;
    }

    /**
     * @return the headers
     */
    public Map<String, Integer> getHeaders() {
        return this.headers;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<String> data) {
        this.data = data;
    }

    /**
     * @return the data
     */
    public List<String> getData() {
        return this.data;
    }

    /**
     * @param columns
     * @return Map<String, Integer>
     */
    protected static Map<String, Integer> getHeaders(List<String> columns) {
        Map<String, Integer> map = new LinkedHashMap<String, Integer>();

        if(columns != null) {
            for(int i = 0, size = columns.size(); i < size; i++) {
                String name = columns.get(i);
                map.put(name.toLowerCase(), Integer.valueOf(i));
            }
        }
        return map;
    }

    /**
     * @param name
     * @param value
     */
    public void setValue(String name, String value) {
        Integer index = this.headers.get(name);

        if(index != null) {
            this.data.set(index.intValue(), value);
        }
        else {
            throw new RuntimeException("no such column: " + name);
        }
    }

    /**
     * @param index
     * @return Object
     */
    public Object getValue(int index) {
        if(index < this.data.size()) {
            return this.data.get(index);
        }
        else {
            throw new RuntimeException("no such column: " + index);
        }
    }

    /**
     * @param name
     * @return Object
     */
    public String getValue(String name) {
        Integer index = this.headers.get(name.toLowerCase());

        if(index != null && index.intValue() < this.data.size()) {
            return this.data.get(index.intValue());
        }
        else {
            throw new RuntimeException("no such column: " + name);
        }
    }

    /**
     * @param name
     * @param defaultValue
     * @return Object
     */
    public String getValue(String name, Object defaultValue) {
        String value = this.getValue(name);
        return (value != null ? value : defaultValue.toString());
    }

    /**
     * @param name
     * @return String
     */
    public String getString(String name) {
        Object value = this.getValue(name);
        return (value != null ? value.toString() : null);
    }

    /**
     * @param name
     * @param defaultValue
     * @return String
     */
    public String getString(String name, String defaultValue) {
        Object value = this.getValue(name, defaultValue);
        return (value != null ? value.toString() : null);
    }

    /**
     * @param name
     * @return Character
     */
    public Character getCharacter(String name) {
        return getCharacter(name, null);
    }

    /**
     * @param name
     * @param defaultValue
     * @return Character
     */
    public Character getCharacter(String name, Character defaultValue) {
        String value = this.getValue(name, defaultValue);

        if(value == null) {
            return defaultValue;
        }
        else {
            return parseCharacter(value.toString(), defaultValue);
        }
    }

    /**
     * @param name
     * @return Boolean
     */
    public Boolean getBoolean(String name) {
        return getBoolean(name, null);
    }

    /**
     * @param name
     * @param defaultValue
     * @return Boolean
     */
    public Boolean getBoolean(String name, Boolean defaultValue) {
        Object value = this.getValue(name, defaultValue);

        if(value == null) {
            return defaultValue;
        }

        if(value instanceof Boolean) {
            return (Boolean) value;
        }
        return parseBoolean(value.toString(), defaultValue);
    }

    /**
     * @param name
     * @return Byte
     */
    public Byte getByte(String name) {
        return getByte(name, null);
    }

    /**
     * @param name
     * @param defaultValue
     * @return Byte
     */
    public Byte getByte(String name, Byte defaultValue) {
        Object value = this.getValue(name, defaultValue);

        if(value == null) {
            return defaultValue;
        }

        if(value instanceof Byte) {
            return (Byte) value;
        }
        return parseByte(value.toString(), defaultValue);
    }

    /**
     * @param name
     * @return Short
     */
    public Short getShort(String name) {
        return getShort(name, null);
    }

    /**
     * @param name
     * @param defaultValue
     * @return Short
     */
    public Short getShort(String name, Short defaultValue) {
        Object value = this.getValue(name, defaultValue);

        if(value == null) {
            return defaultValue;
        }

        if(value instanceof Short) {
            return (Short) value;
        }
        return parseShort(value.toString(), defaultValue);
    }

    /**
     * @param name
     * @return Integer
     */
    public Integer getInteger(String name) {
        return getInteger(name, null);
    }

    /**
     * @param name
     * @param defaultValue
     * @return Integer
     */
    public Integer getInteger(String name, Integer defaultValue) {
        Object value = this.getValue(name, defaultValue);

        if(value == null) {
            return defaultValue;
        }

        if(value instanceof Integer) {
            return (Integer) value;
        }
        return parseInt(value.toString(), defaultValue);
    }

    /**
     * @param name
     * @return Float
     */
    public Float getFloat(String name) {
        return getFloat(name, null);
    }

    /**
     * @param name
     * @param defaultValue
     * @return Float
     */
    public Float getFloat(String name, Float defaultValue) {
        Object value = this.getValue(name, defaultValue);

        if(value == null) {
            return defaultValue;
        }

        if(value instanceof Float) {
            return (Float) value;
        }
        return parseFloat(value.toString(), defaultValue);
    }

    /**
     * @param name
     * @return Double
     */
    public Double getDouble(String name) {
        return getDouble(name, null);
    }

    /**
     * @param name
     * @param defaultValue
     * @return Double
     */
    public Double getDouble(String name, Double defaultValue) {
        Object value = this.getValue(name, defaultValue);

        if(value == null) {
            return defaultValue;
        }
        if(value instanceof Double) {
            return (Double) value;
        }
        return parseDouble(value.toString(), defaultValue);
    }

    /**
     * @param name
     * @return Long
     */
    public Long getLong(String name) {
        return getLong(name, null);
    }

    /**
     * @param name
     * @param defaultValue
     * @return Long
     */
    public Long getLong(String name, Long defaultValue) {
        Object value = this.getValue(name, defaultValue);

        if(value == null) {
            return defaultValue;
        }

        if(value instanceof Long) {
            return (Long) value;
        }
        return parseLong(value.toString(), defaultValue);
    }

    /**
     * @param name
     * @return java.util.Date
     */
    public Date getDate(String name) {
        return this.getDate(name, null);
    }

    /**
     * @param name
     * @param defaultValue
     * @return Date
     */
    public Date getDate(String name, Date defaultValue) {
        Object value = this.getValue(name, defaultValue);

        if(value == null) {
            return defaultValue;
        }

        if(value instanceof Date) {
            return (Date) value;
        }

        if(value instanceof Number) {
            return new Date(((Number) value).longValue());
        }
        return defaultValue;
    }

    /**
     * @param name
     * @return java.sql.Date
     */
    public java.sql.Date getSqlDate(String name) {
        return this.getSqlDate(name, null);
    }

    /**
     * @param name
     * @param defaultValue
     * @return java.sql.Date
     */
    public java.sql.Date getSqlDate(String name, Date defaultValue) {
        Date date = this.getDate(name, null);

        if(date == null) {
            date = defaultValue;
        }
        return (date != null ? new java.sql.Date(date.getTime()) : null);
    }

    /**
     * @param name
     * @return Timestamp
     */
    public Timestamp getTimestamp(String name) {
        return this.getTimestamp(name);
    }

    /**
     * @param name
     * @param defaultValue
     * @return Timestamp
     */
    public Timestamp getTimestamp(String name, Timestamp defaultValue) {
        Date date = this.getDate(name, null);
        return (date != null ? new Timestamp(date.getTime()) : defaultValue);
    }

    /**
     * @param source
     * @return Character
     */
    public Character parseCharacter(String source) {
        return parseCharacter(source, null);
    }

    /**
     * @param source
     * @return Boolean
     */
    public Boolean parseBoolean(String source) {
        return parseBoolean(source, null);
    }

    /**
     * @param source
     * @return Byte
     */
    public Byte parseByte(String source) {
        return parseByte(source, null);
    }

    /**
     * @param source
     * @return Short
     */
    public Short parseShort(String source) {
        return parseShort(source, null);
    }

    /**
     * @param source
     * @return Integer
     */
    public Integer parseInt(String source) {
        return parseInt(source, null);
    }

    /**
     * @param source
     * @return Float
     */
    public Float parseFloat(String source) {
        return parseFloat(source, null);
    }

    /**
     * @param source
     * @return Double
     */
    public Double parseDouble(String source) {
        return parseDouble(source, null);
    }

    /**
     * @param source
     * @return Long
     */
    public Long parseLong(String source) {
        return parseLong(source, null);
    }

    /**
     * @param source
     * @param defaultValue
     * @return Character
     */
    public Character parseCharacter(String source, Character defaultValue) {
        Character result = defaultValue;

        if(source != null) {
            String value = source.trim();

            if(value.length() > 0) {
                try {
                    result = value.charAt(0);
                }
                catch(NumberFormatException e) {
                }
            }
        }
        return result;
    }

    /**
     * @param source
     * @param defaultValue
     * @return Boolean
     */
    public Boolean parseBoolean(String source, Boolean defaultValue) {
        Boolean result = defaultValue;

        if(source != null) {
            try {
                String b = source.toLowerCase();
                boolean value = ("1".equals(b) || "y".equals(b) || "on".equals(b) || "yes".equals(b) || "true".equals(b));
                result = Boolean.valueOf(value);
            }
            catch (NumberFormatException e) {
            }
        }
        return result;
    }

    /**
     * @param source
     * @param defaultValue
     * @return Byte
     */
    public Byte parseByte(String source, Byte defaultValue) {
        Byte result = defaultValue;

        if(source != null) {
            try {
                result = Byte.parseByte(source);
            }
            catch (NumberFormatException e) {
            }
        }
        return result;
    }

    /**
     * @param source
     * @param defaultValue
     * @return parseShort
     */
    public Short parseShort(String source, Short defaultValue) {
        Short result = defaultValue;

        if(source != null) {
            try {
                result = Short.parseShort(source);
            }
            catch (NumberFormatException e) {
            }
        }
        return result;
    }

    /**
     * @param source
     * @param defaultValue
     * @return Integer
     */
    public Integer parseInt(String source, Integer defaultValue) {
        Integer result = defaultValue;

        if(source != null){
            try {
                result = Integer.parseInt(source);
            }
            catch (NumberFormatException e) {
            }
        }
        return result;
    }

    /**
     * @param source
     * @param defaultValue
     * @return Float
     */
    public Float parseFloat(String source, Float defaultValue) {
        Float result = defaultValue;

        if(source != null) {
            try {
                result = Float.parseFloat(source);
            }
            catch (NumberFormatException e) {
            }
        }
        return result;
    }

    /**
     * @param source
     * @param defaultValue
     * @return Double
     */
    public Double parseDouble(String source, Double defaultValue) {
        Double result = defaultValue;

        if(source != null) {
            try {
                result = Double.parseDouble(source);
            }
            catch (NumberFormatException e) {
            }
        }
        return result;
    }

    /**
     * @param source
     * @param defaultValue
     * @return Long
     */
    public Long parseLong(String source, Long defaultValue) {
        Long result = defaultValue;

        if(source != null) {
            try {
                result = Long.parseLong(source);
            }
            catch (NumberFormatException e) {
            }
        }
        return result;
    }

    /**
     * @param <T>
     * @param model
     * @param name
     * @return T
     */
    @SuppressWarnings("unchecked")
    public <T> T getValue(Class<T> model, String name) {
        Object value = null;
        String className = model.getName();

        if(className.equals("char") || className.equals("java.lang.Character")) {
            value = getCharacter(name);
        }
        else if(className.equals("boolean")
                || className.equals("java.lang.Boolean")) {
            value = getBoolean(name);
        }
        else if(className.equals("byte") || className.equals("java.lang.Byte")) {
            value = getByte(name);
        }
        else if(className.equals("short") || className.equals("java.lang.Short")) {
            value = getShort(name);
        }
        else if(className.equals("int") || className.equals("java.lang.Integer")) {
            value = getInteger(name);
        }
        else if(className.equals("float") || className.equals("java.lang.Float")) {
            value = getFloat(name);
        }
        else if(className.equals("double") || className.equals("java.lang.Double")) {
            value = getDouble(name);
        }
        else if(className.equals("long") || className.equals("java.lang.Long")) {
            value = getLong(name);
        }
        else if(className.equals("java.lang.String")) {
            value = getString(name);
        }
        else if(className.equals("java.util.Date")) {
            value = getDate(name);
        }
        else if(className.equals("java.sql.Date")) {
            value = getSqlDate(name);
        }
        else if(className.equals("java.sql.Timestamp")) {
            value = getTimestamp(name);
        }
        return (T) value;
    }

    /**
     * clear
     */
    public void clear() {
        if(this.headers != null) {
            this.headers.clear();
        }
        if(this.data != null) {
            this.data.clear();
        }
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
     * @return String
     */
    public String replace(String pattern) {
        char c;
        int length = pattern.length();
        StringBuilder name = new StringBuilder();
        StringBuilder buffer = new StringBuilder();

        for(int i = 0; i < length; i++) {
            c = pattern.charAt(i);

            if(c == '$' && i < length - 1 && pattern.charAt(i + 1) == '{') {
                for(i = i + 2; i < length; i++) {
                    c = pattern.charAt(i);

                    if(c == '}') {
                        String value = this.getValue(name.toString());

                        if(value != null) {
                            buffer.append(this.escape(value));
                        }
                        break;
                    }
                    else {
                        name.append(c);
                    }
                }
                name.setLength(0);
            }
            else {
                buffer.append(c);
            }
        }
        return buffer.toString();
    }

    /**
     * @param tableName
     * @return String
     */
    public String getInsert(String tableName) {
        return getInsert(tableName, false);
    }

    /**
     * @param tableName
     * @param prepare
     * @return String
     */
    public String getInsert(String tableName, boolean prepare) {
        boolean flag = false;
        StringBuilder sql = new StringBuilder();
        Map<String, Integer> headers = this.headers;
        sql.append("insert into ");
        sql.append(tableName);
        sql.append("(");

        for(Map.Entry<String, Integer> entry : headers.entrySet()) {
            String name = entry.getKey();

            if(flag) {
                sql.append(", ");
            }
            else {
                flag = true;
            }
            sql.append(name);
        }

        flag = false;
        sql.append(") values (");

        for(Map.Entry<String, Integer> entry : headers.entrySet()) {
            String name = entry.getKey();
            String value = this.getValue(name);

            if(flag) {
                sql.append(", ");
            }
            else {
                flag = true;
            }

            if(prepare) {
                sql.append("?");
            }
            else {
                sql.append("'");
                sql.append(this.escape(value));
                sql.append("'");
            }
        }
        sql.append(");");
        return sql.toString();
    }
}
