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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skin.ayada.runtime.PageContext;
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
    private String home;
    private String path;
    private long updateTime;
    private long lastModified;
    private List<Node> nodes;
    private static final Logger logger = LoggerFactory.getLogger(Template.class);

    public Template()
    {
    }

    /**
     * @param file
     * @param nodes
     */
    public Template(String home, String path, List<Node> nodes)
    {
        this.home = home;
        this.path = path;
        this.nodes = nodes;
        this.updateTime = System.currentTimeMillis();
    }

    /**
     * @param pageContext
     */
    public void execute(final PageContext pageContext) throws Exception
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("home", this.home);
        map.put("path", this.path);
        map.put("lastModified", this.lastModified);
        pageContext.setAttribute("template", map);

        if(logger.isDebugEnabled())
        {
            long t1 = System.currentTimeMillis();
            DefaultExecutor.execute(this, pageContext);
            long t2 = System.currentTimeMillis();
            logger.debug(this.getPath() + " - render time: " + (t2 - t1));
        }
        else
        {
            DefaultExecutor.execute(this, pageContext);
        }
    }

    /**
     * @param home the home to set
     */
    public void setHome(String home)
    {
        this.home = home;
    }

    /**
     * @return the home
     */
    public String getHome()
    {
        return this.home;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path)
    {
        this.path = path;
    }

    /**
     * @return the path
     */
    public String getPath()
    {
        return this.path;
    }

    /**
     * @return the lastModified
     */
    public long getLastModified()
    {
        return this.lastModified;
    }

    /**
     * @param lastModified the lastModified to set
     */
    public void setLastModified(long lastModified)
    {
        this.lastModified = lastModified;
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
     * @param nodes the nodes to set
     */
    public void setNodes(List<Node> nodes)
    {
        this.nodes = nodes;
    }

    /**
     * @return the nodes
     */
    public List<Node> getNodes()
    {
        return this.nodes;
    }
}
