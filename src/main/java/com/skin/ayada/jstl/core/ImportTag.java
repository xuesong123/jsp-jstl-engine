/*
 * $RCSfile: ImportTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-26 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TagSupport;

/**
 * <p>Title: ImportTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class ImportTag extends TagSupport {
    private String name;
    private String className;
    private String bodyContent;
    private String description;

    /**
     * @return int
     * @throws Exception
     */
    @Override
    public int doStartTag() throws Exception {
        /* this.pageContext.getTagLibrary().setup(this.name, this.className, this.bodyContent, this.description); */
        return Tag.SKIP_BODY;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param className the className to set
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * @return the className
     */
    public String getClassName() {
        return this.className;
    }

    /**
     * @return the bodyContent
     */
    public String getBodyContent() {
        return this.bodyContent;
    }

    /**
     * @param bodyContent the bodyContent to set
     */
    public void setBodyContent(String bodyContent) {
        this.bodyContent = bodyContent;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
