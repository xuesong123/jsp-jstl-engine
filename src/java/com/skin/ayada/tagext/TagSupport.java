/*
 * $RCSfile: TagSupport.java,v $$
 * $Revision: 1.1  $
 * $Date: 2013-2-19  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.tagext;

import com.skin.ayada.runtime.PageContext;

/**
 * <p>Title: TagSupport</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class TagSupport implements IterationTag
{
    private String id;
    protected Tag parent;
    protected PageContext pageContext;

    /**
     * @return int
     */
    @Override
    public int doStartTag() throws Exception
    {
        return EVAL_BODY_INCLUDE;
    }

    /**
     * @return int
     */
    @Override
    public int doAfterBody() throws Exception
    {
        return SKIP_BODY;
    }

    /**
     * @return int
     */
    @Override
    public int doEndTag() throws Exception
    {
        return EVAL_PAGE;
    }

    /**
     * @return Tag
     */
    @Override
    public Tag getParent()
    {
        return this.parent;
    }

    @Override
    public void release()
    {
    }

    /**
     * @param pageContext
     */
    @Override
    public void setPageContext(PageContext pageContext)
    {
        this.pageContext = pageContext;
    }

    /**
     * @param pageContext
     */
    public PageContext getPageContext()
    {
        return this.pageContext;
    }

    /**
     * @param tag
     */
    @Override
    public void setParent(Tag tag)
    {
        this.parent = tag;
    }

    /**
     * @return String
     */
    public String getId()
    {
        return this.id;
    }

    /**
     * @param id
     */
    public void setId(String id)
    {
        this.id = id;
    }
}
