/*
 * $RCSfile: Template.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-02-19 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.skin.ayada.runtime.DefaultExecutor;
import com.skin.ayada.statement.Node;

/**
 * <p>Title: Template</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class Template {
    private String home;
    private String path;
    private long updateTime;
    private long lastModified;
    private List<Node> nodes;
    private List<Source> dependencies;
    private Map<String, String> pageInfo;

    /**
     *
     */
    public Template() {
    }

    /**
     * @param home
     * @param path
     * @param nodes
     */
    public Template(String home, String path, List<Node> nodes) {
        this.home = home;
        this.path = path;
        this.nodes = nodes;
        this.updateTime = System.currentTimeMillis();
    }

    /**
     * @param pageContext
     * @throws Exception
     */
    public void execute(final PageContext pageContext) throws Exception {
        pageContext.setAttribute("pageInfo", this.getPageInfo());
        DefaultExecutor.execute(this, pageContext);
    }

    /**
     * @param home the home to set
     */
    public void setHome(String home) {
        this.home = home;
    }

    /**
     * @return the home
     */
    public String getHome() {
        return this.home;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return this.path;
    }

    /**
     * @return the lastModified
     */
    public long getLastModified() {
        return this.lastModified;
    }

    /**
     * @param lastModified the lastModified to set
     */
    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    /**
     * @param updateTime the updateTime to set
     */
    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return the updateTime
     */
    public long getUpdateTime() {
        return this.updateTime;
    }

    /**
     * @param nodes the nodes to set
     */
    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    /**
     * @return the nodes
     */
    public List<Node> getNodes() {
        return this.nodes;
    }

    /**
     * @return the dependencies
     */
    public List<Source> getDependencies() {
        return this.dependencies;
    }

    /**
     * @param dependencies the dependencies to set
     */
    public void setDependencies(List<Source> dependencies) {
        this.dependencies = dependencies;
    }

    /**
     * @param source
     */
    public void addDependency(Source source) {
        if(source != null) {
            if(this.dependencies == null) {
                this.dependencies = new ArrayList<Source>();
            }
            this.dependencies.add(source);
        }
    }

    /**
     * @return Map<String, String>
     */
    public Map<String, String> getPageInfo() {
        return this.pageInfo;
    }

    /**
     * @param pageInfo
     */
    public void setPageInfo(Map<String, String> pageInfo) {
        this.pageInfo = pageInfo;
    }

    /**
     *
     */
    public void destroy() {
        this.home = null;
        this.path = null;

        if(this.nodes != null) {
            this.nodes.clear();
            this.nodes = null;
        }

        if(this.dependencies != null) {
            this.dependencies.clear();
            this.dependencies = null;
        }
    }
}
