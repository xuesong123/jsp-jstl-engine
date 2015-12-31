package com.skin.ayada.jstl.sql;

/**
 * @author weixian
 * @version 1.0
 */
public class Entry {
    private String name;
    private Object value;

    /**
     * @param name
     * @param value
     */
    public Entry(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the value
     */
    public Object getValue() {
        return this.value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Object value) {
        this.value = value;
    }
}
