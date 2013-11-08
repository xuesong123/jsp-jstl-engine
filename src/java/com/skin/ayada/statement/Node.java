/*
 * $RCSfile: Node.java,v $$
 * $Revision: 1.1  $
 * $Date: 2012-7-3  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.statement;

import java.util.HashMap;
import java.util.Map;

import com.skin.ayada.runtime.TagFactory;

/**
 * <p>Title: Node</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class Node
{
    private String nodeName;
    private int nodeType;
    private int offset;
    private int length;
    private int lineNumber;
    private int closed;
    private Node parent;
    private Map<String, String> attributes;
    private TagFactory tagFactory;

    /**
     * @param nodeName
     * @param nodeType
     * @param nodeHtml
     */
    public Node(String nodeName)
    {
        this(nodeName, NodeType.NODE);
    }

    /**
     * @param nodeName
     * @param nodeType
     * @param nodeHtml
     */
    public Node(String nodeName, int nodeType)
    {
        this.nodeName = nodeName;
        this.nodeType = nodeType;
        this.closed   = 1;
        this.attributes = new HashMap<String, String>();
    }

    /**
     * @param name
     */
    public void setNodeName(String nodeName)
    {
        this.nodeName = nodeName;
    }

    /**
     * @return String
     */
    public String getNodeName()
    {
        return this.nodeName;
    }

    /**
     * @return the nodeType
     */
    public int getNodeType()
    {
        return nodeType;
    }

    /**
     * @param nodeType the nodeType to set
     */
    public void setNodeType(int nodeType)
    {
        this.nodeType = nodeType;
    }

    /**
     * @param address the address to set
     */
    public void setOffset(int offset)
    {
        this.offset = offset;
    }

    /**
     * @return the address
     */
    public int getOffset()
    {
        return this.offset;
    }

    /**
     * @param length the length to set
     */
    public void setLength(int length)
    {
        this.length = length;
    }

    /**
     * @return the length
     */
    public int getLength()
    {
        return this.length;
    }

    /**
     * @return int
     */
    public int getClosed()
    {
        return this.closed;
    }

    /**
     * @param closed
     */
    public void setClosed(int closed)
    {
        this.closed = closed;
    }

    /**
     * @return int
     */
    public int getLineNumber()
    {
        return lineNumber;
    }

    /**
     * @param lineNumber
     */
    public void setLineNumber(int lineNumber)
    {
        this.lineNumber = lineNumber;
    }

    /**
     * @param name
     * @param value
     */
    public void setAttribute(String name, String value)
    {
        this.attributes.put(name, value);
    }

    /**
     * @param name
     * @return String
     */
    public String getAttribute(String name)
    {
        return this.attributes.get(name.toLowerCase());
    }

    /**
     * @return the attributes
     */
    public Map<String, String> getAttributes()
    {
        return attributes;
    }

    /**
     * @param attributes the attributes to set
     */
    public void setAttributes(Map<String, String> attributes)
    {
        this.attributes = attributes;
    }

    /**
     * @return the parent
     */
    public Node getParent()
    {
        return this.parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(Node parent)
    {
        this.parent = parent;
    }

    /**
     */
    public Node clone()
    {
        Node node = new Node(this.nodeName);
        node.setClosed(this.closed);
        node.setParent(this.parent);

        for(Map.Entry<String, String> entry : this.attributes.entrySet())
        {
            node.setAttribute(entry.getKey(), entry.getValue());
        }

        return node;
    }

    /**
     * @return String
     */
    public String getAttributesHtml()
    {
        StringBuilder buffer = new StringBuilder();

        for(Map.Entry<String, String> entry : this.attributes.entrySet())
        {
            buffer.append(entry.getKey());
            buffer.append("=\"");
            buffer.append(entry.getValue());
            buffer.append("\" ");
        }

        if(buffer.length() > 0)
        {
            buffer.setLength(buffer.length() - 1);
        }

        return buffer.toString();
    }

    /**
     * @return the tagFactory
     */
    public TagFactory getTagFactory()
    {
        return this.tagFactory;
    }

    /**
     * @param tagFactory the tagFactory to set
     */
    public void setTagFactory(TagFactory tagFactory)
    {
        this.tagFactory = tagFactory;
    }

    /**
     * @return String
     */
    public String toString()
    {
        return this.toString(this.getOffset());
    }

    /**
     * @param index
     * @return String
     */
    public String toString(int index)
    {
        StringBuilder buffer = new StringBuilder();

        if(index == this.getOffset())
        {
            buffer.append("<");
            buffer.append(this.getNodeName());
            buffer.append(" offset=\"");
            buffer.append(this.getOffset());
            buffer.append("\" length=\"");
            buffer.append(this.getLength());
            buffer.append("\"");
    
            if(this.attributes != null && this.attributes.size() > 0)
            {
                if(this.tagFactory != null)
                {
                    buffer.append(" tagFactory=\"");
                    buffer.append(this.tagFactory.getClass().getName());
                    buffer.append("\" tagClass=\"");
                    buffer.append(this.tagFactory.getClassName());
                    buffer.append("\"");
                }
                else
                {
                    buffer.append(" tagFactory=\"null\"");
                    buffer.append(" tagClass=\"null\"");
                }

                for(Map.Entry<String, String> entry : this.attributes.entrySet())
                {
                    buffer.append(" ");
                    buffer.append(entry.getKey());
                    buffer.append("=\"");
                    buffer.append(entry.getValue());
                    buffer.append("\"");
                }
            }

            buffer.append("/>");
        }
        else
        {
            buffer.append("</");
            buffer.append(this.getNodeName());
            buffer.append(">");
        }

        return buffer.toString();
    }
}
