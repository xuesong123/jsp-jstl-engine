/*
 * $RCSfile: AttributeList.java,v $
 * $Revision: 1.1 $
 * $Date: 2017-2-12 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.statement;

import java.util.Map;

/**
 * <p>Title: AttributeList</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class AttributeList {
    private Map<String, Attribute> attributes;

    /**
     * @param attributes
     */
    public AttributeList(Map<String, Attribute> attributes) {
        this.attributes = attributes;
    }

    /**
     * @param name
     * @return Attribute
     */
    public Attribute getAttribute(String name) {
        return this.attributes.get(name);
    }

    /**
     * @param name
     * @return String
     */
    public Object getValue(String name) {
        Attribute attribute = this.attributes.get(name);

        if(attribute != null) {
            return attribute.getValue();
        }
        return null;
    }

    /**
     * @param name
     * @return String
     */
    public String getText(String name) {
        Attribute attribute = this.attributes.get(name);

        if(attribute != null) {
            return attribute.getText();
        }
        return null;
    }

    /**
     * @param name
     * @return Attribute
     */
    public Attribute remove(String name) {
        return this.attributes.remove(name);
    }

    /**
     * @return Map<String, Attribute>
     */
    public Map<String, Attribute> getAttributes() {
        return this.attributes;
    }

    /**
     * print detail
     */
    public void print() {
        System.out.println("========================= attrs =========================");

        for(Map.Entry<String, Attribute> entry : this.attributes.entrySet()) {
            Attribute attr = entry.getValue();
            System.out.println(entry.getKey() + ": " + attr.getName() + ": " + entry.getValue());
        }
    }
}
