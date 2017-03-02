/*
 * $RCSfile: SimpleTag.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-11-20 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.tagext;

import com.skin.ayada.PageContext;

/**
 * <p>Title: SimpleTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public abstract class SimpleTag implements Tag {
    private Tag parent;
    protected PageContext pageContext;
    private JspFragment jspBody;

    /**
     * @return int
     */
    @Override
    public int doStartTag() throws Exception {
        throw new UnsupportedOperationException("doStartTag unsupported !");
    }

    /**
     * @return int
     */
    @Override
    public int doEndTag() throws Exception {
        throw new UnsupportedOperationException("doStartTag unsupported !");
    }

    /**
     * @throws Exception
     */
    public abstract void doTag() throws Exception;

    /**
     * @return the parent
     */
    @Override
    public Tag getParent() {
        return this.parent;
    }

    /**
     * @param parent the parent to set
     */
    @Override
    public void setParent(Tag parent) {
        this.parent = parent;
    }

    /**
     * @return the pageContext
     */
    public PageContext getPageContext() {
        return this.pageContext;
    }

    /**
     * @param pageContext the pageContext to set
     */
    @Override
    public void setPageContext(PageContext pageContext) {
        this.pageContext = pageContext;
    }

    /**
     * @return the jspBody
     */
    public JspFragment getJspBody() {
        return this.jspBody;
    }

    /**
     * @param jspBody the jspBody to set
     */
    public void setJspBody(JspFragment jspBody) {
        this.jspBody = jspBody;
    }

    /**
     * release
     */
    @Override
    public void release() {
    }
}
