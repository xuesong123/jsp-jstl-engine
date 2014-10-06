/*
 * $RCSfile: ELEncoder.java,v $$
 * $Revision: 1.1  $
 * $Date: 2014-10-6  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.runtime;

/**
 * <p>Title: ELEncoder</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public interface ELEncoder
{
    /**
     * @param content
     * @return String
     */
    public String encode(String content);
}
