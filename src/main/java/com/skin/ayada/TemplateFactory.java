/*
 * $RCSfile: TemplateFactory.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-02-19 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada;

import com.skin.ayada.SourceFactory;
import com.skin.ayada.Template;

/**
 * <p>Title: TemplateFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public interface TemplateFactory {
    /**
     * @param sourceFactory
     * @param path
     * @param encoding
     * @return Template
     * @throws Exception
     */
    public Template create(SourceFactory sourceFactory, String path, String encoding) throws Exception;

    /**
     * @param ignoreJspTag the ignoreJspTag to set
     */
    public void setIgnoreJspTag(boolean ignoreJspTag);

    /**
     * @return the ignoreJspTag
     */
    public boolean getIgnoreJspTag();
}
