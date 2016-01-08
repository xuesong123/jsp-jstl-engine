/*
 * $RCSfile: PrepareCompiler.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-19 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.template;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skin.ayada.source.DefaultSourceFactory;
import com.skin.ayada.source.PathMatcher;
import com.skin.ayada.source.SourceFactory;

/**
 * <p>Title: PrepareCompiler</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class PrepareCompiler {
    protected List<String> excludes;
    private TemplateContext templateContext;
    private static final Logger logger = LoggerFactory.getLogger(PrepareCompiler.class);

    /**
     * @param templateContext
     */
    public PrepareCompiler(TemplateContext templateContext) {
        this.templateContext = templateContext;
        this.excludes = new ArrayList<String>();
    }

    /**
     * 
     */
    public void compile() {
        SourceFactory sourceFactory = this.templateContext.getSourceFactory();
        String home = sourceFactory.getHome();

        if(sourceFactory instanceof DefaultSourceFactory) {
            this.compile(home, new File(home));
        }
        else {
            throw new UnsupportedOperationException("Unsupported SourceFactory !");
        }
    }

    /**
     * @param dir
     */
    public void compile(String home, File dir) {
        File[] files = dir.listFiles();

        for(File file : files) {
            try {
                String path = file.getCanonicalPath();
                path = path.replace('\\', '/');
                path = path.substring(home.length());

                if(!this.match(this.excludes, path)) {
                    if(file.isDirectory()) {
                        compile(home, file);
                    }
                    else {
                        logger.info("compile - home: {}, file: {}", home, path);
                        this.templateContext.getTemplate(path);
                    }
                }
                else {
                    logger.info("exclude - home: {}, file: {}", home, path);
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                return;
            }
        }
    }

    /**
     * @param patterns
     */
    public void exclude(String[] patterns) {
        if(patterns != null && patterns.length > 0) {
            for(int i = 0, length = patterns.length; i < length; i++) {
                this.excludes.add(patterns[i]);
            }
        }
    }

    /**
     * @param path
     * @param patterns
     * @return boolean
     */
    public boolean match(List<String> patterns, String path) {
        if(path == null) {
            return false;
        }

        if(patterns == null || patterns.size() < 1) {
            return false;
        }

        for(String pattern : patterns) {
            if(PathMatcher.match(pattern, path, true)) {
                return true;
            }
        }
        return false;
    }
}
