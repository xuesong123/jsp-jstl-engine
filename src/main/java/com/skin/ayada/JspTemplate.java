/*
 * $RCSfile: Template.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-02-19 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skin.ayada.statement.Node;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TryCatchFinally;

/**
 * <p>Title: Template</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public abstract class JspTemplate extends Template {
    private static final Logger logger = LoggerFactory.getLogger(JspTemplate.class);

    /**
     *
     */
    public JspTemplate() {
    }

    /**
     * @param home
     * @param file
     * @param nodes
     */
    public JspTemplate(String home, String file, List<Node> nodes) {
        super(home, file, nodes);
    }

    /**
     * @param pageContext
     * @throws Exception
     */
    @Override
    public void execute(final PageContext pageContext) throws Exception {
        pageContext.setAttribute("pageInfo", this.getPageInfo());

        try {
            this._execute(pageContext);
        }
        catch(Throwable throwable) {
            if(throwable instanceof Exception) {
                throw ((Exception)throwable);
            }
            throw new Exception(throwable);
        }
    }

    /**
     * @param pageContext
     * @throws Throwable
     */
    public abstract void _execute(final PageContext pageContext) throws Throwable;

    /**
     * @param tryCatchFinally
     * @throws Exception
     */
    protected final void doCatch(TryCatchFinally tryCatchFinally, Throwable throwable) throws Exception {
        try {
            tryCatchFinally.doCatch(throwable);
        }
        catch(Throwable t) {
            if(t instanceof Exception) {
                throw (Exception)t;
            }
            throw new Exception(t);
        }
    }

    /**
     * @param tag
     * @throws Exception
     */
    protected final void doFinally(Tag tag) throws Exception {
        Exception exception = null;

        if(tag instanceof TryCatchFinally) {
            try {
                ((TryCatchFinally)tag).doFinally();
            }
            catch(Exception e) {
                exception = e;
            }
        }

        try {
            tag.release();
        }
        catch(Exception e) {
            if(exception == null) {
                exception = e;
            }
        }

        if(exception != null) {
            throw exception;
        }
    }

    /**
     * @param clazz
     * @param name
     * @return String
     */
    public static String load(Class<?> clazz, String name) {
        return load(clazz, name, "utf-8");
    }
    
    /**
     * @param clazz
     * @param name
     * @param charset
     * @return String
     */
    public static String load(Class<?> clazz, String name, String charset) {
        InputStream inputStream = clazz.getResourceAsStream(name);

        if(inputStream != null) {
            try {
                int length = 0;
                byte[] buffer = new byte[4096];
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                
                while((length = inputStream.read(buffer, 0, 4096)) > 0) {
                    bos.write(buffer, 0, length);
                }
                return new String(bos.toByteArray(), charset);
            }
            catch(IOException e) {
                logger.error(e.getMessage(), e);
            }
            finally {
                try {
                    inputStream.close();
                }
                catch (IOException e) {
                }
            }
        }
        return null;
    }
}
