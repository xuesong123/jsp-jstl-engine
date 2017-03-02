/*
 * $RCSfile: TagNode.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-11-08 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.statement;

import java.util.Map;

import com.skin.ayada.TagFactory;

/**
 * <p>Title: TagNode</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class TagNode extends Node {
    /**
     * @param nodeName
     */
    public TagNode(String nodeName) {
        super(nodeName, NodeType.TAG_NODE);
    }

    /**
     * @param nodeName
     * @param nodeType
     */
    public TagNode(String nodeName, int nodeType) {
        super(nodeName, nodeType);
    }

    private String tagClassName;
    private TagFactory tagFactory;

    /**
     * @return the tagClassName
     */
    public String getTagClassName() {
        return this.tagClassName;
    }

    /**
     * @param tagClassName the tagClassName to set
     */
    public void setTagClassName(String tagClassName) {
        this.tagClassName = tagClassName;
    }

    /**
     * @return the tagFactory
     */
    public TagFactory getTagFactory() {
        return this.tagFactory;
    }

    /**
     * @param tagFactory the tagFactory to set
     */
    public void setTagFactory(TagFactory tagFactory) {
        this.tagFactory = tagFactory;
    }

    /**
     * clone
     */
    @Override
    public Node clone() {
        TagNode node = new TagNode(this.getNodeName());
        node.setClosed(this.getClosed());
        node.setParent(this.getParent());
        node.setTagClassName(this.tagClassName);
        node.setTagFactory(this.tagFactory);

        Map<String, Attribute> attributes = this.getAttributes();

        if(attributes != null && attributes.size() > 0) {
            for(Map.Entry<String, Attribute> entry : attributes.entrySet()) {
                node.setAttribute(entry.getKey(), entry.getValue());
            }
        }
        return node;
    }

    /**
     * @param index
     * @return String
     */
    @Override
    public String toString(int index, boolean sort) {
        StringBuilder buffer = new StringBuilder();

        if(index == this.getOffset()) {
            buffer.append("<");
            buffer.append(this.getNodeName());
            buffer.append(" lineNumber=\"");
            buffer.append(this.getLine());
            buffer.append("\" offset=\"");
            buffer.append(this.getOffset());
            buffer.append("\" length=\"");
            buffer.append(this.getLength());
            buffer.append("\"");

            if(this.tagClassName != null) {
                buffer.append(" tagClass=\"");
                buffer.append(this.tagClassName);
                buffer.append("\"");
            }

            if(this.tagFactory != null) {
                buffer.append(" tagFactory=\"");
                buffer.append(this.tagFactory.getClass().getName());
                buffer.append("\"");
            }

            Map<String, Attribute> attributes = this.getAttributes();

            if(attributes != null && attributes.size() > 0) {
                if(sort == true) {
                    java.util.TreeMap<String, Attribute> treeMap = new java.util.TreeMap<String, Attribute>();
                    treeMap.putAll(attributes);
                    attributes = treeMap;
                }

                for(Map.Entry<String, Attribute> entry : attributes.entrySet()) {
                    buffer.append(" ");
                    buffer.append(entry.getKey());
                    buffer.append("=\"");
                    buffer.append(this.encode(entry.getValue().getText()));
                    buffer.append("\"");
                }
            }

            if(this.getClosed() == NodeType.SELF_CLOSED) {
                buffer.append("/>");
            }
            else {
                buffer.append(">");
            }
        }
        else {
            if(this.getClosed() == NodeType.PAIR_CLOSED) {
                buffer.append("</");
                buffer.append(this.getNodeName());
                buffer.append(">");
            }
        }
        return buffer.toString();
    }
}
