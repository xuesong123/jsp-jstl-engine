/*
 * $RCSfile: AppendableNode.java,v $
 * $Revision: 1.1 $
 * $Date: 2012-7-03 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.statement;

/**
 * <p>Title: AppendableNode</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class AppendableNode extends Node {
    private StringBuilder buffer;

    /**
     * default
     */
    public AppendableNode() {
        this(NodeType.TEXT_NAME, NodeType.TEXT);
    }

    /**
     * @param nodeName
     */
    public AppendableNode(String nodeName) {
        super(nodeName);
        this.buffer = new StringBuilder();
    }

    /**
     * @param nodeName
     * @param nodeType
     */
    public AppendableNode(String nodeName, int nodeType) {
        super(nodeName, nodeType);
        this.buffer = new StringBuilder();
    }

    /**
     * @param content
     * @return AppendableNode
     */
    public AppendableNode append(String content) {
        this.buffer.append(content);
        return this;
    }

    /**
     * @param c
     * @return AppendableNode
     */
    public AppendableNode append(char c) {
        this.buffer.append(c);
        return this;
    }

    /**
     * @param content
     */
    public void setTextContent(String content) {
        this.buffer.setLength(0);
        this.buffer.append(content);
    }

    /**
     * @return String
     */
    @Override
    public String getTextContent() {
        return this.buffer.toString();
    }
}
