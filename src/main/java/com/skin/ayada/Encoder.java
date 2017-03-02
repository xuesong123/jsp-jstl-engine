/*
 * $RCSfile: Encoder.java,v $
 * $Revision: 1.1 $
 * $Date: 2014-10-06 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada;

/**
 * <p>Title: Encoder</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public interface Encoder {
    /**
     * @param content
     * @return String
     */
    public String encode(String content);
}
