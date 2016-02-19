/*
 * $RCSfile: Variable.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-21 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.statement;

/**
 * <p>Title: Variable</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class Variable extends DataNode {
    private String flag;

    /**
     *
     */
    public Variable() {
        super(NodeType.VARI_NAME, NodeType.VARIABLE);
    }

    /**
     * @param nodeName
     */
    public Variable(String nodeName) {
        super(NodeType.VARI_NAME, NodeType.VARIABLE);
    }

    /**
     * @param nodeName
     * @param nodeType
     */
    protected Variable(String nodeName, int nodeType) {
        super(NodeType.VARI_NAME, NodeType.VARIABLE);
    }

    /**
     * @return the flag
     */
    public String getFlag() {
        return this.flag;
    }

    /**
     * @param flag the flag to set
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }

    /**
     * @return TextNode
     */
    @Override
    public Variable clone() {
        Variable node = new Variable();
        node.setNodeName(this.getNodeName());
        node.setNodeType(this.getNodeType());
        node.setOffset(this.getOffset());
        node.setLength(this.getLength());
        node.setLineNumber(this.getLineNumber());
        node.setClosed(this.getClosed());
        node.setParent(this.getParent());
        node.append(this.getTextContent());
        return node;
    }
}
