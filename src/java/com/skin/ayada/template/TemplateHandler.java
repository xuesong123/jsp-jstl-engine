/*
 * $RCSfile: TemplateHandler.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-8 $
 *
 * Copyright (C) 2008 WanMei, Inc. All rights reserved.
 *
 * This software is the proprietary information of WanMei, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.template;

import java.io.IOException;

import com.skin.ayada.runtime.PageContext;

/**
 * <p>Title: TemplateHandler</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public abstract class TemplateHandler
{
    /**
     * @param pageContext
     */
    public abstract void execute(final PageContext pageContext) throws IOException;
}
