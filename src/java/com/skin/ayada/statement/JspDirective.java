/*
 * $RCSfile: Directive.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-11 $
 *
 * Copyright (C) 2008 WanMei, Inc. All rights reserved.
 *
 * This software is the proprietary information of WanMei, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.statement;

/**
 * <p>Title: Directive</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class JspDirective extends DataNode
{
    public JspDirective()
    {
        super(NodeType.JSP_DIRECTIVE_PAGE_NAME, NodeType.JSP_DIRECTIVE_PAGE);
    }

    /**
     * @param nodeName
     */
    public JspDirective(String nodeName)
    {
        super(NodeType.JSP_DIRECTIVE_PAGE_NAME, NodeType.JSP_DIRECTIVE_PAGE);
    }

    /**
     * @param nodeName
     * @param nodeType
     */
    protected JspDirective(String nodeName, int nodeType)
    {
        super(NodeType.JSP_DIRECTIVE_PAGE_NAME, NodeType.JSP_DIRECTIVE_PAGE);
    }
    
    @Override
    public DataNode clone()
    {
        JspDirective node = new JspDirective();
        node.setNodeName(this.getNodeName());
        node.setNodeType(this.getNodeType());
        node.setOffset(this.getOffset());
        node.setLength(this.getLength());
        node.setLineNumber(this.getLineNumber());
        node.setClosed(this.getClosed());
        node.setParent(this.getParent());
        node.append(this.getText());
        return node;
    }
}
