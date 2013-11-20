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

import java.io.IOException;
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
        this(null);
    }

    /**
     * @param out
     */
    protected DefaultPageContext(JspWriter out)
    {
        this.out = out;
        this.attributes = new HashMap<String, Object>();
        this.setAttribute("pageContext", this);
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
        if(value != null)
        {
            return this.attributes.put(key, value);
        }
        else
        {
            return this.attributes.remove(key);
        }
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
     * @param bodyContent
     * @param escapeXml
     * @throws IOException 
     */
    public void printBodyContent(BodyContent bodyContent, boolean escapeXml) throws IOException
    {
        String content = bodyContent.getString().trim();

        if(escapeXml)
        {
            content = this.escape(content);
        }

        bodyContent.getEnclosingWriter().write(content);
    }

    /**
     * @param source
     * @return String
     */
    private String escape(String source)
    {
        if(source == null)
        {
            return "";
        }

        char c;
        StringBuilder buffer = new StringBuilder();

        for(int i = 0, size = source.length(); i < size; i++)
        {
            c = source.charAt(i);

            switch (c)
            {
                case '&':
                {
                    buffer.append("&amp;");
                    break;
                }
                case '"':
                {
                    buffer.append("&quot;");
                    break;
                }
                case '<':
                {
                    buffer.append("&lt;");
                    break;
                }
                case '>':
                {
                    buffer.append("&gt;");
                    break;
                }
                case '\'':
                {
                    buffer.append("&#39;");
                }
                default :
                {
                    buffer.append(c);
                    break;
                }
            }
        }

        return buffer.toString();
    }

    /**
     * @param page
     */
    @Override
    public void include(String page) throws Exception
    {
        this.include(page, null);
    }

    /**
     * @param page
     * @throws Exception 
     */
    @Override
    public void include(String page, Map<String, Object> context) throws Exception
    {
        if(this.templateContext == null)
        {
            throw new NullPointerException("NullPointerException: templateContext");
        }

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
