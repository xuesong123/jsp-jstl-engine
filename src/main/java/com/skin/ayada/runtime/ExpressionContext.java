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
public interface ExpressionContext
{
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
     * @return the escapeXml
     */
    public boolean getEscapeXml();

    /**
     * @param escapeXml the escapeXml to set
     */
    public void setEscapeXml(boolean escapeXml);
    
    /**
     * @param pageContext the pageContext to set
     */
    public void setPageContext(PageContext pageContext);

    /**
     * @return the pageContext
     */
    public PageContext getPageContext();

    /**
     * release the current ExpressionContext
     */
    public void release();
}
