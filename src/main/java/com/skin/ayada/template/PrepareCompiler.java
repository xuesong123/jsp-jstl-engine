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
package com.skin.ayada.template;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skin.ayada.io.FileItem;
import com.skin.ayada.source.DefaultSourceFactory;
import com.skin.ayada.source.PathMatcher;
import com.skin.ayada.source.SourceFactory;
import com.skin.ayada.util.Path;

/**
 * <p>Title: PrepareCompiler</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class PrepareCompiler {
    private PrintWriter out;
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
     * non-recursion
     * @param home
     * @param dir
     */
    public void compile(String home, File dir) {
        int index = 0;
        List<FileItem> stack = new ArrayList<FileItem>();
        stack.add(new FileItem(dir));

        try {
            while(!stack.isEmpty()) {
                index = stack.size() - 1;
                FileItem fileItem = stack.get(index);
                String path = Path.getRelativePath(home, fileItem.getAbsolutePath());

                if(!this.match(this.excludes, path)) {
                    if(fileItem.isDirectory()) {
                        if(fileItem.getFlag() == 0) {
                            fileItem.setFlag(1);
                            File[] files = fileItem.getFile().listFiles();

                            for(int j = files.length - 1; j > -1; j--) {
                                stack.add(new FileItem(files[j]));
                            }
                        }
                        else {
                            stack.remove(index);
                        }
                    }
                    else {
                        stack.remove(index);
                        this.info("compile: " + home + path);
                        logger.info("compile - home: {}, file: {}", home, path);
                        this.templateContext.getTemplate(path);
                    }
                }
                else {
                    stack.remove(index);
                    this.info("exclude: " + home + path);
                    logger.info("exclude - home: {}, file: {}", home, path);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * recursion process
     * @param home
     * @param dir
     */
    public void compile2(String home, File dir) {
        File[] files = dir.listFiles();

        try {
            for(File file : files) {
                String path = Path.getRelativePath(home, file.getAbsolutePath());

                if(!this.match(this.excludes, path)) {
                    if(file.isDirectory()) {
                        compile2(home, file);
                    }
                    else {
                        this.info("compile: " + home + path);
                        logger.info("compile - home: {}, file: {}", home, path);
                        this.templateContext.getTemplate(path);
                    }
                }
                else {
                    this.info("exclude: " + home + path);
                    logger.info("exclude - home: {}, file: {}", home, path);
                }
            }
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
