/*
 * $RCSfile: Template.java,v $$
 * $Revision: 1.1  $
 * $Date: 2013-2-19  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.template;

import java.util.List;

import com.skin.ayada.statement.Node;

/**
 * <p>Title: Template</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class Template
{
    private String file;
    private long updateTime;
    private List<Node> nodes;

    /**
     * @param document
     */
    public Template(List<Node> nodes)
    {
        this.nodes = nodes;
        this.updateTime = System.currentTimeMillis();
    }

    /**
     * @return the nodes
     */
    public List<Node> getNodes()
    {
        return nodes;
    }

    /**
     * @param nodes the nodes to set
     */
    public void setNodes(List<Node> nodes)
    {
        this.nodes = nodes;
    }

    /**
     * @param updateTime the updateTime to set
     */
    public void setUpdateTime(long updateTime)
    {
        this.updateTime = updateTime;
    }

    /**
     * @return the updateTime
     */
    public long getUpdateTime()
    {
        return this.updateTime;
    }

    /**
     * @param file the file to set
     */
    public void setFile(String file)
    {
        this.file = file;
    }
    
    /**
     * @return the file
     */
    public String getFile()
    {
        return this.file;
    } 
}
