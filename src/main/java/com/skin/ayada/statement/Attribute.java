/*
 * $RCSfile: Attribute.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-02-12 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.statement;

/**
 * <p>Title: Attribute</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class Attribute {
    private int type;
    private String name;
    private Object value;

    /**
     * Boolean
     */
    public static final int BOOLEAN        = 1;

    /**
     * Number
     */
    public static final int NUMBER         = 2;

    /**
     * String
     */
    public static final int STRING         = 3;

    /**
     * String
     */
    public static final int VARIABLE       = 4;

    /**
     * String
     */
    public static final int EXPRESSION     = 5;

    /**
     * String
     */
    public static final int MIX_EXPRESSION = 6;

    /**
     * String
     */
    public static final int JSP_EXPRESSION = 7;

    /**
     * default
     */
    public Attribute() {
    }

    /**
     * @param type
     * @param name
     * @param value
     */
    public Attribute(int type, String name, Object value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    /**
     * @return the type
     */
    public int getType() {
        return this.type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
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

    /**
     * @return String
     */
    public String getText() {
        return this.value.toString();
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        if(this.value != null) {
            return this.value.toString();
        }
        else {
            return null;
        }
    }
}
