/*
 * $RCSfile: LoopTagStatus.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-19 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.tagext;

/**
 * <p>Title: LoopTagStatus</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public interface LoopTagStatus {
    /**
     * @return int
     */
    public Integer getBegin();

    /**
     * @return int
     */
    public int getCount();

    /**
     * @return Object
     */
    public Object getCurrent();

    /**
     * @return int
     */
    public Integer getEnd();

    /**
     * @return int
     */
    public int getIndex();

    /**
     * @return int
     */
    public Integer getStep();

    /**
     * @return boolean
     */
    public boolean isFirst();

    /**
     * @return boolean
     */
    public boolean isLast();
}
