/*
 * $RCSfile: ExitException.java,v $$
 * $Revision: 1.1 $
 * $Date: 2017-2-7 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.template;

/**
 * <p>Title: ExitException</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class ExitException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * @param throwable
     */
    public ExitException(Throwable throwable) {
        super(throwable);
    }
}
