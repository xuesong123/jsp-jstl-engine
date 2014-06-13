/*
 * $RCSfile: Config.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-5-3 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.config;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * <p>Title: Config</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public abstract class Config
{
    public static final long serialVersionUID = 1L;
    private Map<String, String> parameters = new LinkedHashMap<String, String>();

    public Config()
    {
    }

    /**
     * @param map
     */
    public Config(Map<String, String> map)
    {
        this.parameters.putAll(map);
    }

    /**
     * @param name
     * @param value
     */
    public void setValue(String name, String value)
    {
        this.parameters.put(name, value);
    }

    /**
     * @param name
     * @return String
     */
    public String getValue(String name)
    {
        return this.parameters.get(name);
    }

    /**
     * @param name
     * @return String
     */
    public String getValue(String name, String defaultValue)
    {
        String value = this.parameters.get(name);
        return (value != null ? value : defaultValue);
    }

    /**
     * @param name
     * @return String
     */
    public String getString(String name)
    {
        return this.getValue(name, null);
    }

    /**
     * @param name
     * @param defaultValue
     * @return String
     */
    public String getString(String name, String defaultValue)
    {
        return this.getValue(name, defaultValue);
    }

    /**
     * @param name
     * @return Character
     */
    public Character getCharacter(String name)
    {
        return getCharacter(name, null);
    }

    /**
     * @param name
     * @param defaultValue
     * @return Character
     */
    public Character getCharacter(String name, Character defaultValue)
    {
        String value = this.getValue(name);

        if(value == null)
        {
            return defaultValue;
        }

        return parseCharacter(value, defaultValue);
    }

    /**
     * @param name
     * @return Boolean
     */
    public Boolean getBoolean(String name)
    {
        return this.getBoolean(name, null);
    }

    /**
     * @param name
     * @param defaultValue
     * @return Boolean
     */
    public Boolean getBoolean(String name, Boolean defaultValue)
    {
        String value = this.getValue(name);

        if(value == null)
        {
            return defaultValue;
        }

        return parseBoolean(value, defaultValue);
    }

    /**
     * @param name
     * @return Byte
     */
    public Byte getByte(String name)
    {
        return getByte(name, null);
    }

    /**
     * @param name
     * @param defaultValue
     * @return Byte
     */
    public Byte getByte(String name, Byte defaultValue)
    {
        String value = this.getValue(name);

        if(value == null)
        {
            return defaultValue;
        }

        return parseByte(value, defaultValue);
    }

    /**
     * @param name
     * @return Short
     */
    public Short getShort(String name)
    {
        return getShort(name, null);
    }

    /**
     * @param name
     * @param defaultValue
     * @return Short
     */
    public Short getShort(String name, Short defaultValue)
    {
        String value = this.getValue(name);

        if(value == null)
        {
            return defaultValue;
        }

        return parseShort(value, defaultValue);
    }

    /**
     * @param name
     * @return Integer
     */
    public Integer getInteger(String name)
    {
        return getInteger(name, null);
    }

    /**
     * @param name
     * @param defaultValue
     * @return Integer
     */
    public Integer getInteger(String name, Integer defaultValue)
    {
        String value = this.getValue(name);

        if(value == null)
        {
            return defaultValue;
        }

        return parseInt(value, defaultValue);
    }

    /**
     * @param name
     * @return Float
     */
    public Float getFloat(String name)
    {
        return getFloat(name, null);
    }

    /**
     * @param name
     * @param defaultValue
     * @return Float
     */
    public Float getFloat(String name, Float defaultValue)
    {
        String value = this.getValue(name);

        if(value == null)
        {
            return defaultValue;
        }

        return parseFloat(value, defaultValue);
    }

    /**
     * @param name
     * @return Double
     */
    public Double getDouble(String name)
    {
        return getDouble(name, null);
    }

    /**
     * @param name
     * @param defaultValue
     * @return Double
     */
    public Double getDouble(String name, Double defaultValue)
    {
        String value = this.getValue(name);

        if(value == null)
        {
            return defaultValue;
        }

        return parseDouble(value, defaultValue);
    }

    /**
     * @param name
     * @return Long
     */
    public Long getLong(String name)
    {
        return getLong(name, null);
    }

    /**
     * @param name
     * @param defaultValue
     * @return Long
     */
    public Long getLong(String name, Long defaultValue)
    {
        String value = this.getValue(name);

        if(value == null)
        {
            return defaultValue;
        }

        return parseLong(value, defaultValue);
    }

    /**
     * @param name
     * @param format
     * @return java.util.Date
     */
    public java.util.Date getDate(String name, String format)
    {
        String value = this.getValue(name);

        if(value == null)
        {
            return null;
        }

        return parseDate(value, format);
    }

    /**
     * @param source
     * @return Character
     */
    public Character parseCharacter(String source)
    {
        return parseCharacter(source, null);
    }

    /**
     * @param source
     * @return Boolean
     */
    public Boolean parseBoolean(String source)
    {
        return parseBoolean(source, null);
    }

    /**
     * @param source
     * @return Byte
     */
    public Byte parseByte(String source)
    {
        return parseByte(source, null);
    }

    /**
     * @param source
     * @return Short
     */
    public Short parseShort(String source)
    {
        return parseShort(source, null);
    }

    /**
     * @param source
     * @return Integer
     */
    public Integer parseInt(String source)
    {
        return parseInt(source, null);
    }

    /**
     * @param source
     * @return Float
     */
    public Float parseFloat(String source)
    {
        return parseFloat(source, null);
    }

    /**
     * @param source
     * @return Double
     */
    public Double parseDouble(String source)
    {
        return parseDouble(source, null);
    }

    /**
     * @param source
     * @return Long
     */
    public Long parseLong(String source)
    {
        return parseLong(source, null);
    }

    /**
     * @param source
     * @param defaultValue
     * @return Character
     */
    public Character parseCharacter(String source, Character defaultValue)
    {
        Character result = defaultValue;

        if(source != null && source.trim().length() > 0)
        {
            try
            {
                char c = Character.valueOf(source.trim().charAt(0));
                result = Character.valueOf(c);
            }
            catch(NumberFormatException e)
            {
            }
        }

        return result;
    }

    /**
     * @param source
     * @param defaultValue
     * @return Boolean
     */
    public Boolean parseBoolean(String source, Boolean defaultValue)
    {
        Boolean result = defaultValue;

        if(source != null)
        {
            try
            {
                String b = source.toLowerCase();
                boolean value = ("1".equals(b) || "y".equals(b) || "on".equals(b) || "yes".equals(b) || "true".equals(b));
                result = Boolean.valueOf(value);
            }
            catch(NumberFormatException e)
            {
            }
        }

        return result;
    }

    /**
     * @param source
     * @param defaultValue
     * @return Byte
     */
    public Byte parseByte(String source, Byte defaultValue)
    {
        Byte result = defaultValue;

        if(source != null)
        {
            try
            {
                byte b = Byte.parseByte(source);

                result = Byte.valueOf(b);
            }
            catch(NumberFormatException e)
            {
            }
        }

        return result;
    }

    /**
     * @param source
     * @param defaultValue
     * @return parseShort
     */
    public Short parseShort(String source, Short defaultValue)
    {
        Short result = defaultValue;

        if(source != null)
        {
            try
            {
                short value = Short.parseShort(source);

                result = Short.valueOf(value);
            }
            catch(NumberFormatException e)
            {
            }
        }

        return result;
    }

    /**
     * @param source
     * @param defaultValue
     * @return Integer
     */
    public Integer parseInt(String source, Integer defaultValue)
    {
        Integer result = defaultValue;

        if(source != null)
        {
            try
            {
                int i = Integer.parseInt(source);

                result = Integer.valueOf(i);
            }
            catch(NumberFormatException e)
            {
            }
        }

        return result;
    }

    /**
     * @param source
     * @param defaultValue
     * @return Float
     */
    public Float parseFloat(String source, Float defaultValue)
    {
        Float result = defaultValue;

        if(source != null)
        {
            try
            {
                float value = Float.parseFloat(source);

                result = new Float(value);
            }
            catch(NumberFormatException e)
            {
            }
        }

        return result;
    }

    /**
     * @param source
     * @param defaultValue
     * @return Double
     */
    public Double parseDouble(String source, Double defaultValue)
    {
        Double result = defaultValue;

        if(source != null)
        {
            try
            {
                double value = Double.parseDouble(source);

                result = new Double(value);
            }
            catch(NumberFormatException e)
            {
            }
        }

        return result;
    }

    /**
     * @param source
     * @param defaultValue
     * @return Long
     */
    public Long parseLong(String source, Long defaultValue)
    {
        Long result = defaultValue;

        if(source != null)
        {
            try
            {
                long l = Long.parseLong(source);
                result = Long.valueOf(l);
            }
            catch(NumberFormatException e)
            {
            }
        }

        return result;
    }

    /**
     * @param source
     * @param format
     * @return java.util.Date
     */
    public java.util.Date parseDate(String source, String format)
    {
        java.util.Date date = null;

        if(source != null)
        {
            try
            {
                java.text.DateFormat df = new java.text.SimpleDateFormat(format);
                date = df.parse(source);
            }
            catch(java.text.ParseException e)
            {
            }
        }

        return date;
    }

    /**
     * @param name
     * @param type
     * @return T
     */
    @SuppressWarnings("unchecked")
    public <T> T getObject(String name, Class<T> type)
    {
        Object value = null;
        String className = type.getName();

        if(className.equals("char") || className.equals("java.lang.Character"))
        {
            value = getCharacter(name);
        }
        else if(className.equals("boolean") || className.equals("java.lang.Boolean"))
        {
            value = getBoolean(name);
        }
        else if(className.equals("byte") || className.equals("java.lang.Byte"))
        {
            value = getByte(name);
        }
        else if(className.equals("short") || className.equals("java.lang.Short"))
        {
            value = getShort(name);
        }
        else if(className.equals("int") || className.equals("java.lang.Integer"))
        {
            value = getInteger(name);
        }
        else if(className.equals("float") || className.equals("java.lang.Float"))
        {
            value = getFloat(name);
        }
        else if(className.equals("double") || className.equals("java.lang.Double"))
        {
            value = getDouble(name);
        }
        else if(className.equals("long") || className.equals("java.lang.Long"))
        {
            value = getLong(name);
        }
        else if(className.equals("java.lang.String"))
        {
            value = getString(name);
        }
        else if(className.equals("java.util.Date"))
        {
            value = getDate(name, "yyyy-MM-dd hh:mm:ss");
        }

        return (T)value;
    }

    /**
     * @param name
     * @return boolean
     */
    public boolean has(String name)
    {
        return this.parameters.containsKey(name);
    }

    /**
     * @param name
     * @param value
     * @return boolean
     */
    public boolean contains(String name, String value)
    {
        String content = this.getString(name);

        if(content != null)
        {
            if(content.trim().equals("*"))
            {
                return true;
            }

            String[] array = content.split(",");

            for(int i = 0; i < array.length; i++)
            {
                array[i] = array[i].trim();

                if(array[i].equals(value))
                {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * @return Set<String>
     */
    public Set<String> getNames()
    {
        return this.parameters.keySet();
    }

    /**
     * @param map
     */
    public void setValues(Map<String, String> map)
    {
        this.parameters.putAll(map);
    }

    /**
     * 
     */
    public void clear()
    {
        this.parameters.clear();
    }

    /**
     * @return Map<String, String>
     */
    public Map<String, String> getMap()
    {
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.putAll(this.parameters);
        return map;
    }
}
