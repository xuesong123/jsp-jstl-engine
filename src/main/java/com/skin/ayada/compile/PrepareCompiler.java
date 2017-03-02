/*
 * $RCSfile: PrepareCompiler.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-02-19 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.compile;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skin.ayada.SourceFactory;
import com.skin.ayada.TemplateContext;
import com.skin.ayada.scanner.Scanner;
import com.skin.ayada.util.PathMatcher;

/**
 * <p>Title: PrepareCompiler</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class PrepareCompiler {
    private PrintWriter out;
    protected List<String> includes;
    protected List<String> excludes;
    private TemplateContext templateContext;
    private static final Logger logger = LoggerFactory.getLogger(PrepareCompiler.class);

    /**
     * @param templateContext
     */
    public PrepareCompiler(TemplateContext templateContext) {
        this.templateContext = templateContext;
        this.includes = new ArrayList<String>();
        this.excludes = new ArrayList<String>();
    }

    /**
     * compile
     */
    public void compile() {
        final PrepareCompiler prepareCompiler = this;
        final TemplateContext context = this.templateContext;
        final SourceFactory sourceFactory = context.getSourceFactory();

        try {
            sourceFactory.accept(new Scanner.Visitor() {
                @Override
                public void visit(String home, String path) throws Exception {
                    boolean b = prepareCompiler.match(path);

                    if(b) {
                        String message = "compile: [" + home + "]" + path;
                        prepareCompiler.info(message);
                        System.out.println(message);
                        context.getTemplate(path);
                    }
                    else {
                        String message = "exclude: [" + home + "]" + path;
                        prepareCompiler.info(message);
                        System.out.println(message);
                    }
                }
            });
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * @param message
     */
    public void info(String message) {
        if(this.out != null) {
            this.out.println(message);
        }
    }

    /**
     * 必须在includes里面并且不在excludes里面
     * @param path
     * @return boolean
     */
    public boolean match(String path) {
        if(this.includes != null && this.includes.size() > 0) {
            if(!this.match(this.includes, path)) {
                return false;
            }
        }

        if(this.excludes != null && this.excludes.size() > 0) {
            if(this.match(this.excludes, path)) {
                return false;
            }
        }
        return true;
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

        for(String pattern : patterns) {
            if(PathMatcher.match(pattern, path, true)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param patterns
     */
    public void include(String[] patterns) {
        if(patterns != null && patterns.length > 0) {
            for(int i = 0, length = patterns.length; i < length; i++) {
                this.includes.add(patterns[i]);
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
     * @return the out
     */
    public PrintWriter getOut() {
        return this.out;
    }

    /**
     * @param out the out to set
     */
    public void setOut(PrintWriter out) {
        this.out = out;
    }
}
