/*
 * $RCSfile: Directive.java,v $$
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
        super(nodeName, NodeType.JSP_DIRECTIVE_PAGE);
    }

    /**
     * @param nodeName
     * @param nodeType
     */
    protected JspDirective(String nodeName, int nodeType)
    {
        super(nodeName, nodeType);
    }

    /**
     * @return
     */
    public static JspDirective getInstance(String nodeName)
    {
        if(nodeName.equals("jsp:directive.page"))
        {
            return getPageDirective();
        }
        else if(nodeName.equals("jsp:directive.taglib"))
        {
            return getTaglibDirective();
        }
        else if(nodeName.equals("jsp:directive.include"))
        {
            return getIncludeDirective();
        }
        else
        {
            return getPageDirective();
        }
    }

    /**
     * @return JspDirective
     */
    public static JspDirective getPageDirective()
    {
        return new JspDirective(NodeType.JSP_DIRECTIVE_PAGE_NAME, NodeType.JSP_DIRECTIVE_PAGE);
    }

    /**
     * @return JspDirective
     */
    public static JspDirective getTaglibDirective()
    {
        return new JspDirective(NodeType.JSP_DIRECTIVE_TAGLIB_NAME, NodeType.JSP_DIRECTIVE_TAGLIB);
    }

    /**
     * @return JspDirective
     */
    public static JspDirective getIncludeDirective()
    {
        return new JspDirective(NodeType.JSP_DIRECTIVE_INCLUDE_NAME, NodeType.JSP_DIRECTIVE_INCLUDE);
    }

    @Override
    public JspDirective clone()
    {
        JspDirective node = new JspDirective();
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
