/*
 * $RCSfile: Scanner.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-01-13 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.scanner;

/**
 * <p>Title: Scanner</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public interface Scanner {
    /**
     * @param visitor
     * @throws Exception
     */
    public void accept(Visitor visitor) throws Exception;

    /**
     * <p>Title: Visitor</p>
     * <p>Description: </p>
     * <p>Copyright: Copyright (c) 2006</p>
     * @author xuesong.net
     * @version 1.0
     */
    public interface Visitor {
        /**
         * @param home
         * @param path
         * @throws Exception
         */
        public void visit(String home, String path) throws Exception;
    }
}
