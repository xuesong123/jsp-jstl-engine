/*
 * $RCSfile: TextNode.java,v $$
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
 * <p>Title: TextNode</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class TextNode extends DataNode {
    /**
     *
     */
    public TextNode() {
        super(NodeType.TEXT_NAME, NodeType.TEXT);
    }

    /**
     * @param nodeName
     */
    public TextNode(String nodeName) {
        super(NodeType.TEXT_NAME, NodeType.TEXT);
    }

    /**
     * @param nodeName
     * @param nodeType
     */
    protected TextNode(String nodeName, int nodeType) {
        super(NodeType.TEXT_NAME, NodeType.TEXT);
    }

    /**
     * @return TextNode
     */
    @Override
    public TextNode clone() {
        TextNode node = new TextNode();
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
