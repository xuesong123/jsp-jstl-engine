/*
 * $RCSfile: Expression.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-2-19 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.statement;

/**
 * <p>Title: Expression</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class Expression extends DataNode
{
    public Expression()
    {
        super(NodeType.EXPR_NAME, NodeType.EXPRESSION);
    }

    /**
     * @param nodeName
     */
    public Expression(String nodeName)
    {
        super(NodeType.EXPR_NAME, NodeType.EXPRESSION);
    }

    /**
     * @param nodeName
     * @param nodeType
     */
    protected Expression(String nodeName, int nodeType)
    {
        super(NodeType.EXPR_NAME, NodeType.EXPRESSION);
    }

    /**
     * @return TextNode
     */
    @Override
    public Expression clone()
    {
        Expression node = new Expression();
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
