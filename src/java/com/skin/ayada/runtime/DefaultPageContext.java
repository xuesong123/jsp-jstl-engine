/*
 * $RCSfile: PageContext.java,v $$
 * $Revision: 1.1  $
 * $Date: 2013-2-19  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.runtime;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.skin.ayada.jstl.TagLibrary;
import com.skin.ayada.tagext.BodyContent;
import com.skin.ayada.template.TemplateContext;

/**
 * <p>Title: PageContext</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class DefaultPageContext implements PageContext
{
    private JspWriter out;
    private TagLibrary tagLibrary;
    private Map<String, Object> attributes;
    private TemplateContext templateContext;
    private ExpressionContext expressionContext;

    protected DefaultPageContext()
    {
        this.attributes = new HashMap<String, Object>();
    }

    /**
     * @param out
     */
    protected DefaultPageContext(JspWriter out)
    {
        this.out = out;
        this.attributes = new HashMap<String, Object>();
    }

    /**
     * @param out the out to set
     */
    public void setOut(JspWriter out)
    {
        this.out = out;
    }

    /**
     * @return the out
     */
    public JspWriter getOut()
    {
        return this.out;
    }

    /**
     * @return Iterator<String>
     */
    public Iterator<String> getAttributeNames()
    {
        return this.attributes.keySet().iterator();
    }

    /**
     * @param key
     * @param value
     */
    public Object setAttribute(String key, Object value)
    {
        return this.attributes.put(key, value);
    }

    /**
     * @param key
     * @return Object
     */
    public Object getAttribute(String key)
    {
        return this.attributes.get(key);
    }

    /**
     * @param key
     */
    public Object removeAttribute(String key)
    {
        return this.attributes.remove(key);
    }
    
    /**
     * @return the templateContext
     */
    public TemplateContext getTemplateContext()
    {
        return this.templateContext;
    }

    /**
     * @param templateContext the templateContext to set
     */
    public void setTemplateContext(TemplateContext templateContext)
    {
        this.templateContext = templateContext;
    }

    /**
     * @param expressionContext
     */
    public void setExpressionContext(ExpressionContext expressionContext)
    {
        this.expressionContext = expressionContext;
    }

    /**
     * @return ExpressionContext
     */
    public ExpressionContext getExpressionContext()
    {
        return this.expressionContext;
    }

    /**
     * @return the tagLibrary
     */
    public TagLibrary getTagLibrary()
    {
        return tagLibrary;
    }

    /**
     * @param tagLibrary the tagLibrary to set
     */
    public void setTagLibrary(TagLibrary tagLibrary)
    {
        this.tagLibrary = tagLibrary;
    }

    /**
     * @return JspWriter
     */
    public JspWriter pushBody()
    {
        this.out = new BodyContent(this.out);
        return this.out;
    }

    /**
     * @return JspWriter
     */
    public JspWriter popBody()
    {
        BodyContent bodyContent = (BodyContent)(this.out);
        this.out = (JspWriter)(bodyContent.getOut());
        return this.out;
    }

    /**
     * @param page
     */
    @Override
    public void include(String page)
    {
        this.include(page, null);
    }

    /**
     * @param page
     */
    @Override
    public void include(String page, Map<String, Object> context)
    {
        this.templateContext.execute(page, context, this.getOut());
    }

    /**
     * release
     */
    public void release()
    {
        this.attributes.clear();
        this.expressionContext.release();
        this.tagLibrary.release();
        this.attributes = null;
        this.expressionContext = null;
        this.tagLibrary = null;
        this.out = null;
    }
}
