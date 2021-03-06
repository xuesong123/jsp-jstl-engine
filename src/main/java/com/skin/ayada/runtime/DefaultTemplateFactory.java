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
package com.skin.ayada.runtime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skin.ayada.SourceFactory;
import com.skin.ayada.Template;
import com.skin.ayada.TemplateFactory;
import com.skin.ayada.compile.JspParser;
import com.skin.ayada.config.TemplateConfig;
import com.skin.ayada.jstl.TagLibrary;
import com.skin.ayada.jstl.TagLibraryFactory;
import com.skin.ayada.util.ClassUtil;

/**
 * <p>Title: TemplateFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class DefaultTemplateFactory implements TemplateFactory {
    private boolean ignoreJspTag = true;
    private static final Logger logger = LoggerFactory.getLogger(DefaultTemplateFactory.class);

    /**
     * @param className
     * @return TemplateFactory
     * @throws Exception
     */
    public static TemplateFactory getTemplateFactory(String className) throws Exception {
        return (TemplateFactory)(ClassUtil.getInstance(className));
    }

    /**
     * @param sourceFactory
     * @param path
     * @param encoding
     * @return Template
     * @throws Exception
     */
    @Override
    public Template create(SourceFactory sourceFactory, String path, String encoding) throws Exception {
        TagLibrary tagLibrary = null;

        if(TemplateConfig.getStandardLibrary()) {
            tagLibrary = TagLibraryFactory.getStandardTagLibrary();
        }
        else {
            tagLibrary = TagLibraryFactory.getTagLibrary();
        }

        long t1 = System.currentTimeMillis();
        JspParser parser = new JspParser(sourceFactory);
        parser.setIgnoreJspTag(this.getIgnoreJspTag());
        parser.setTagLibrary(tagLibrary);
        Template template = parser.parse(path, (encoding != null ? encoding : "utf-8"));
        long t2 = System.currentTimeMillis();

        if(logger.isDebugEnabled()) {
            logger.debug("compile time: " + (t2 - t1));
        }
        return template;
    }

    /**
     * @param ignoreJspTag the ignoreJspTag to set
     */
    @Override
    public void setIgnoreJspTag(boolean ignoreJspTag) {
        this.ignoreJspTag = ignoreJspTag;
    }

    /**
     * @return the ignoreJspTag
     */
    @Override
    public boolean getIgnoreJspTag() {
        return this.ignoreJspTag;
    }
}
