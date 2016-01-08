/*
 * $RCSfile: TemplateFactory.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-19 $
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
import com.skin.ayada.config.TemplateConfig;
import com.skin.ayada.jstl.TagLibrary;
import com.skin.ayada.jstl.TagLibraryFactory;
import com.skin.ayada.source.Source;
import com.skin.ayada.source.SourceFactory;
import com.skin.ayada.util.ClassUtil;

/**
 * <p>Title: TemplateFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class TemplateFactory {
    private boolean ignoreJspTag = true;
    private static final Logger logger = LoggerFactory.getLogger(TemplateFactory.class);

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
     * @param file
     * @param encoding
     * @return Template
     * @throws Exception
     */
    public Template create(SourceFactory sourceFactory, String file, String encoding) throws Exception {
        TagLibrary tagLibrary = null;

        if(TemplateConfig.getStandardLibrary()) {
            tagLibrary = TagLibraryFactory.getStandardTagLibrary();
        }
        else {
            tagLibrary = TagLibraryFactory.getTagLibrary();
        }

        long t1 = System.currentTimeMillis();
        TemplateCompiler compiler = new TemplateCompiler(sourceFactory);
        compiler.setIgnoreJspTag(this.getIgnoreJspTag());
        compiler.setTagLibrary(tagLibrary);
        Template template = compiler.compile(file, (encoding != null ? encoding : sourceFactory.getEncoding()));
        long t2 = System.currentTimeMillis();

        if(logger.isDebugEnabled()) {
            logger.debug("compile time: " + (t2 - t1));
        }
        return template;
    }

    /**
     * @param source
     * @return Template
     * @throws Exception
     */
    public Template compile(String source) throws Exception {
        TagLibrary tagLibrary = null;

        if(TemplateConfig.getStandardLibrary()) {
            tagLibrary = TagLibraryFactory.getStandardTagLibrary();
        }
        else {
            tagLibrary = TagLibraryFactory.getTagLibrary();
        }

        long t1 = System.currentTimeMillis();
        TemplateCompiler compiler = new TemplateCompiler(null);
        compiler.setIgnoreJspTag(this.getIgnoreJspTag());
        compiler.setTagLibrary(tagLibrary);
        Template template = compiler.compile(new Source("/", "", source, Source.SCRIPT));
        long t2 = System.currentTimeMillis();

        if(logger.isDebugEnabled()) {
            logger.debug("compile time: " + (t2 - t1));
        }
        return template;
    }

    /**
     * @param ignoreJspTag the ignoreJspTag to set
     */
    public void setIgnoreJspTag(boolean ignoreJspTag) {
        this.ignoreJspTag = ignoreJspTag;
    }

    /**
     * @return the ignoreJspTag
     */
    public boolean getIgnoreJspTag() {
        return this.ignoreJspTag;
    }
}
