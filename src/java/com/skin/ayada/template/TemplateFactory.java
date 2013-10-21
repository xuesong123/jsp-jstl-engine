/*
 * $RCSfile: TemplateFactory.java,v $$
 * $Revision: 1.1  $
 * $Date: 2013-2-19  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skin.ayada.compile.TemplateCompiler;
import com.skin.ayada.jstl.TagLibrary;
import com.skin.ayada.jstl.TagLibraryFactory;

/**
 * <p>Title: TemplateFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class TemplateFactory
{
    private static final Logger logger = LoggerFactory.getLogger(TemplateFactory.class);

    /**
     * @param source
     * @return Template
     */
    public static Template create(String home, String file, String source)
    {
        long t1 = System.currentTimeMillis();
        TagLibrary tagLibrary = TagLibraryFactory.getStandardTagLibrary();
        TemplateCompiler compiler = new TemplateCompiler(source);
        compiler.setHome(home);
        compiler.setFile(file);
        compiler.setTagLibrary(tagLibrary);
        Template template = compiler.compile();
        long t2 = System.currentTimeMillis();

        if(logger.isDebugEnabled())
        {
            logger.debug("compile time: " + (t2 - t1));
        }

        return template;
    }
}
