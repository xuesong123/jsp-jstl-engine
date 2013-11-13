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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skin.ayada.compile.TemplateCompiler;
import com.skin.ayada.jstl.TagLibrary;
import com.skin.ayada.jstl.TagLibraryFactory;
import com.skin.ayada.source.SourceFactory;
import com.skin.ayada.statement.Node;

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
     * @param home
     * @param path
     * @param nodes
     * @return Template
     */
    public static Template getInstance(String home, String path, List<Node> nodes)
    {
        return new Template(home, path, nodes);
    }

    /**
     * @param home
     * @param file
     * @param charset
     * @return Template
     */
    public Template create(SourceFactory sourceFactory, String file, String encoding)
    {
        long t1 = System.currentTimeMillis();
        TagLibrary tagLibrary = TagLibraryFactory.getStandardTagLibrary();
        TemplateCompiler compiler = new TemplateCompiler(sourceFactory);
        compiler.setTagLibrary(tagLibrary);
        Template template = compiler.compile(file, encoding);
        long t2 = System.currentTimeMillis();

        if(logger.isDebugEnabled())
        {
            logger.debug("compile time: " + (t2 - t1));
        }

        return template;
    }
}
