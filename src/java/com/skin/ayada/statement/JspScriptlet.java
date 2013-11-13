/*
 * $RCSfile: Scriptlet.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-11 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.statement;

/**
 * <p>Title: Scriptlet</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class JspScriptlet extends DataNode
{
    public JspScriptlet()
    {
        super(NodeType.JSP_SCRIPTLET_NAME, NodeType.JSP_SCRIPTLET);
    }

    /**
     * @param nodeName
     */
    public JspScriptlet(String nodeName)
    {
        super(NodeType.JSP_SCRIPTLET_NAME, NodeType.JSP_SCRIPTLET);
    }

    /**
     * @param nodeName
     * @param nodeType
     */
    protected JspScriptlet(String nodeName, int nodeType)
    {
        super(NodeType.JSP_SCRIPTLET_NAME, NodeType.JSP_SCRIPTLET);
    }
    
    @Override
    public DataNode clone()
    {
        JspScriptlet node = new JspScriptlet();
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
