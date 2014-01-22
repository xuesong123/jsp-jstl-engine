/*
 * $RCSfile: DataNode.java,v $$
 * $Revision: 1.1  $
 * $Date: 2012-7-6  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.statement;

/**
 * <p>Title: DataNode</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public abstract class DataNode extends Node
{
    private StringBuilder buffer = new StringBuilder();

    protected DataNode()
    {
        super(NodeType.DATA_NAME, NodeType.CDATA);
    }

    /**
     * @param nodeName
     */
    protected DataNode(String nodeName)
    {
        super(nodeName, NodeType.CDATA);
    }

    /**
     * @param nodeName
     * @param nodeType
     */
    protected DataNode(String nodeName, int nodeType)
    {
        super(nodeName, nodeType);
        this.setLength(1);
    }

    /**
     */
    public String getNodeHtml()
    {
        return this.buffer.toString();
    }

    /**
     * @param c
     */
    public void append(char c)
    {
        this.buffer.append(c);
    }

    /**
     * @param b
     */
    public void append(boolean b)
    {
        this.buffer.append(b);
    }

    /**
     * @param b
     */
    public void append(byte b)
    {
        this.buffer.append(b);
    }

    /**
     * @param i
     */
    public void append(int i)
    {
        this.buffer.append(i);
    }

    /**
     * @param f
     */
    public void append(float f)
    {
        this.buffer.append(f);
    }

    /**
     * @param d
     */
    public void append(double d)
    {
        this.buffer.append(d);
    }

    /**
     * @param l
     */
    public void append(long l)
    {
        this.buffer.append(l);
    }

    /**
     * @param text
     */
    public void append(String text)
    {
        this.buffer.append(text);
    }

    /**
     * @param text
     */
    public void append(Object object)
    {
        this.buffer.append((object != null ? object.toString() : "null"));
    }

    /**
     * 
     */
    public void clear()
    {
        this.buffer.setLength(0);
    }

    /**
     * @return String
     */
    @Override
    public String getTextContent()
    {
        return this.buffer.toString();
    }

    /**
     * @return DataNode
     */
    @Override
    public abstract DataNode clone();
}