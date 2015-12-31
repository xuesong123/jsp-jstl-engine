/*
 * $RCSfile: FinallyException.java,v $$
 * $Revision: 1.1 $
 * $Date: 2014-03-25 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.tagext;

/**
 * <p>Title: FinallyException</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class FinallyException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * @param throwable
     */
    public FinallyException(Throwable throwable) {
        super(throwable);
    }
}
