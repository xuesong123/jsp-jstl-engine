/*
 * $RCSfile: Tag.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-02-19 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.tagext;

import com.skin.ayada.PageContext;

/**
 * <p>Title: Tag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public interface Tag {
    /**
     * skip the tag body
     */
    public static final int SKIP_BODY = 0;

    /**
     * eval the tag body
     */
    public static final int EVAL_BODY_INCLUDE = 1;

    /**
     * exit render
     */
    public static final int SKIP_PAGE = 5;

    /**
     * render
     */
    public static final int EVAL_PAGE = 6;

    /**
     * continue
     */
    public static final int CONTINUE = "continue".hashCode();

    /**
     * break
     */
    public static final int BREAK    = "break".hashCode();

    /**
     * @return int
     * @throws Exception
     */
    public int doStartTag() throws Exception;

    /**
     * @return int
     * @throws Exception
     */
    public int doEndTag() throws Exception;

    /**
     * @param tag
     */
    public void setParent(Tag tag);

    /**
     * @return Tag
     */
    public Tag getParent();

    /**
     * @param pageContext the pageContext to set
     */
    public void setPageContext(PageContext pageContext);

    /**
     * Called on a Tag handler to release state.
     */
    public void release();
}
