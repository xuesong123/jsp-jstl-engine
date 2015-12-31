/*
 * $RCSfile: LoopTag.java,v $$
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
 * <p>Title: LoopTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public interface LoopTag extends Tag {
    /**
     * Retrieves the current item in the iteration. Behaves idempotently;
     * calling getCurrent() repeatedly should return the same Object until the iteration is advanced.
     * (Specifically, calling getCurrent() does not advance the iteration.)
     * @return Object
     */
    public Object getCurrent();

    /**
     * Retrieves a 'status' object to provide information about the current round of the iteration.
     * @return LoopTagStatus
     */
    public LoopTagStatus getLoopStatus();
}
