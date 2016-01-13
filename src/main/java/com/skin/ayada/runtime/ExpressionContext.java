/*
 * $RCSfile: ExpressionContext.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-20 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.runtime;

import java.io.IOException;
import java.io.Writer;

/**
 * <p>Title: ExpressionContext</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public interface ExpressionContext {
    /**
     * @param expression
     * @return Object
     */
    public Object getValue(String expression);

    /**
     * @param expression
     * @return Object
     */
    public boolean getBoolean(String expression);

    /**
     * @param expression
     * @return Byte
     */
    public Byte getByte(String expression);

    /**
     * @param expression
     * @return Short
     */
    public Short getShort(String expression);

    /**
     * @param expression
     * @return Object
     */
    public Integer getInteger(String expression);

    /**
     * @param expression
     * @return Float
     */
    public Float getFloat(String expression);

    /**
     * @param expression
     * @return Double
     */
    public Double getDouble(String expression);

    /**
     * @param expression
     * @return Long
     */
    public Long getLong(String expression);

    /**
     * @param expression
     * @return Character
     */
    public Character getCharacter(String expression);

    /**
     * @param expression
     * @return Object
     */
    public String getString(String expression);

    /**
     * @param expression
     * @return Object
     */
    public String getEscapeString(String expression);

    /**
     * @param out
     * @param b
     * @throws IOException
     */
    public void print(Writer out, boolean b) throws IOException;

    /**
     * @param out
     * @param c
     * @throws IOException
     */
    public void print(Writer out, char c) throws IOException;

    /**
     * @param out
     * @param b
     * @throws IOException
     */
    public void print(Writer out, byte b) throws IOException;

    /**
     * @param out
     * @param s
     * @throws IOException
     */
    public void print(Writer out, short s) throws IOException;

    /**
     * @param out
     * @param i
     * @throws IOException
     */
    public void print(Writer out, int i) throws IOException;

    /**
     * @param out
     * @param f
     * @throws IOException
     */
    public void print(Writer out, float f) throws IOException;

    /**
     * @param out
     * @param d
     * @throws IOException
     */
    public void print(Writer out, double d) throws IOException;

    /**
     * @param out
     * @param l
     * @throws IOException
     */
    public void print(Writer out, long l) throws IOException;

    /**
     * @param out
     * @param content
     * @throws IOException
     */
    public void print(Writer out, String content) throws IOException;

    /**
     * @param out
     * @param object
     * @throws IOException
     */
    public void print(Writer out, Object object) throws IOException;

    /**
     * @param pageContext the pageContext to set
     */
    public void setPageContext(PageContext pageContext);

    /**
     * @return the pageContext
     */
    public PageContext getPageContext();

    /**
     * @return ELEncoder
     */
    public ELEncoder getEncoder();

    /**
     * @param encoder
     */
    public void setEncoder(ELEncoder encoder);

    /**
     * release the current ExpressionContext
     */
    public void release();
}
