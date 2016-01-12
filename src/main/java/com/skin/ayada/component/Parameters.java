/*
 * $RCSfile: Parameters.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-27 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: Parameters</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class Parameters {
    private Map<String, Object> parameters;

    public Parameters() {
        this.parameters = new HashMap<String, Object>();
    }

    /**
     * @param name
     * @param value
     */
    public void setValue(String name, Object value) {
        this.parameters.put(name, value);
    }

    /**
     * @param name
     * @return Object
     */
    public Object getValue(String name) {
        return this.parameters.get(name);
    }

    /**
     * @param name
     * @param defaultValue
     * @return Object
     */
    public Object getValue(String name, Object defaultValue) {
        Object value = this.getValue(name);
        return (value != null ? value : defaultValue);
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
        Object value = this.getValue(name, defaultValue);

        if(value == null) {
            return defaultValue;
        }

        if(value instanceof Character) {
            return (Character)value;
        }
        return parseCharacter(value.toString(), defaultValue);
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
            return (Boolean)value;
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
            return (Byte)value;
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
            return (Short)value;
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
            return (Integer)value;
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
            return (Float)value;
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
            return (Double)value;
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
            return (Long)value;
        }
        return parseLong(value.toString(), defaultValue);
    }

    /**
     * @param name
     * @param format
     * @return java.util.Date
     */
    public Date getDate(String name, String format) {
        Object value = this.getValue(name, null);

        if(value == null) {
            return null;
        }

        if(value instanceof Date) {
            return (Date)value;
        }
        return parseDate(value.toString(), format);
    }

    /**
     * @param name
     * @param format
     * @return java.sql.Date
     */
    public java.sql.Date getSqlDate(String name, String format) {
        Object value = this.getValue(name, null);

        if(value == null) {
            return null;
        }

        if(value instanceof java.sql.Date) {
            return (java.sql.Date)value;
        }
        else if(value instanceof java.util.Date) {
            return new java.sql.Date(((java.util.Date)(value)).getTime());
        }
        else if(value instanceof java.sql.Timestamp) {
            return new java.sql.Date(((java.sql.Timestamp)(value)).getTime());
        }
        else {
            java.util.Date date =  parseDate(value.toString(), format);
            return new java.sql.Date(date.getTime());
        }
    }

    /**
     * @param name
     * @param format
     * @return java.sql.Timestamp
     */
    public java.sql.Timestamp getTimestamp(String name, String format) {
        Object value = this.getValue(name, null);

        if(value == null) {
            return null;
        }

        if(value instanceof java.sql.Timestamp) {
            return (java.sql.Timestamp)value;
        }
        else if(value instanceof java.util.Date) {
            return new java.sql.Timestamp(((java.util.Date)(value)).getTime());
        }
        else if(value instanceof java.sql.Date) {
            return new java.sql.Timestamp(((java.sql.Date)(value)).getTime());
        }
        else {
            java.util.Date date =  parseDate(value.toString(), format);
            return new java.sql.Timestamp(date.getTime());
        }
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
            catch(NumberFormatException e) {
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
            catch(NumberFormatException e) {
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
            catch(NumberFormatException e) {
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

        if(source != null) {
            try {
                result = Integer.parseInt(source);
            }
            catch(NumberFormatException e) {
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
            catch(NumberFormatException e) {
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
            catch(NumberFormatException e) {
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
            catch(NumberFormatException e) {
            }
        }
        return result;
    }

    /**
     * @param source
     * @param format
     * @return java.util.Date
     */
    public java.util.Date parseDate(String source, String format) {
        java.util.Date date = null;

        if(source != null) {
            try {
                java.text.DateFormat df = new java.text.SimpleDateFormat(format);
                date = df.parse(source);
            }
            catch(java.text.ParseException e) {
            }
        }
        return date;
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

        if(model.equals(char.class) || model.equals(Character.class)) {
            value = getCharacter(name);
        }
        else if(model.equals(boolean.class) || model.equals(Boolean.class)) {
            value = getBoolean(name);
        }
        else if(model.equals(byte.class) || model.equals(Byte.class)) {
            value = getByte(name);
        }
        else if(model.equals(short.class) || model.equals(Short.class)) {
            value = getShort(name);
        }
        else if(model.equals(int.class) || model.equals(Integer.class)) {
            value = getInteger(name);
        }
        else if(model.equals(float.class) || model.equals(Float.class)) {
            value = getFloat(name);
        }
        else if(model.equals(double.class) || model.equals(Double.class)) {
            value = getDouble(name);
        }
        else if(model.equals(long.class) || model.equals(Long.class)) {
            value = getLong(name);
        }
        else if(model.equals(String.class)) {
            value = getString(name);
        }
        else if(model.equals(java.util.Date.class)) {
            value = getDate(name, "yyyy-MM-dd hh:mm:ss");
        }
        else if(model.equals(java.sql.Date.class)) {
            value = getSqlDate(name, "yyyy-MM-dd hh:mm:ss");
        }
        else if(model.equals(java.sql.Timestamp.class)) {
            value = getTimestamp(name, "yyyy-MM-dd hh:mm:ss");
        }
        return (T)value;
    }

    /**
     * @return Map<String, Object>
     */
    public Map<String, Object> getParameters() {
        return this.parameters;
    }

    /**
     * @param map
     * @return Map<String, Object>
     */
    public Map<String, Object> export(Map<String, Object> map) {
        map.putAll(this.parameters);
        return map;
    }

    public void clear() {
        this.parameters.clear();
    }
}
