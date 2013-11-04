/*
 * $RCSfile: SourceFactory.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-4 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.source;

/**
 * <p>Title: SourceFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public interface SourceFactory
{
    /**
     * @param home
     * @param path
     * @param encoding
     * @return Source
     */
    public Source getSource(String path, String encoding);
}
