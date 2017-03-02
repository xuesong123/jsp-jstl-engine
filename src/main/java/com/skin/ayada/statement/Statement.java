/*
 * $RCSfile: Statement.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-03-02 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.statement;

import com.skin.ayada.tagext.Tag;

/**
 * <p>Title: Statement</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class Statement {
    private Tag tag;
    private Node node;
    private Statement parent;
    private int startTagFlag;

    /**
     * @param node
     */
    public Statement(Node node) {
        this.node = node;
    }

    /**
     * @return the tag
     */
    public Tag getTag() {
        return this.tag;
    }

    /**
     * @param tag the tag to set
     */
    public void setTag(Tag tag) {
        this.tag = tag;
    }

    /**
     * @return the node
     */
    public Node getNode() {
        return this.node;
    }

    /**
     * @param node the node to set
     */
    public void setNode(Node node) {
        this.node = node;
    }

    /**
     * @return the parent
     */
    public Statement getParent() {
        return this.parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(Statement parent) {
        this.parent = parent;
    }

    /**
     * @return the startTagFlag
     */
    public int getStartTagFlag() {
        return this.startTagFlag;
    }

    /**
     * @param startTagFlag the startTagFlag to set
     */
    public void setStartTagFlag(int startTagFlag) {
        this.startTagFlag = startTagFlag;
    }
}
