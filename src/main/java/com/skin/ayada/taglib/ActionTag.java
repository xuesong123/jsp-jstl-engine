/*
 * $RCSfile: ActionTag.java,v $
 * $Revision: 1.1 $
 * $Date: 2011-12-09 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.taglib;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skin.ayada.PageContext;
import com.skin.ayada.tagext.AttributeTagSupport;
import com.skin.ayada.tagext.ParameterTagSupport;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TagSupport;

/**
 * <p>Title: ActionTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class ActionTag extends TagSupport implements ParameterTagSupport, AttributeTagSupport {
    private String className;
    private String method;
    private String page;
    private Parameters parameters = new Parameters();
    private static final String[] EXPORTS = new String[]{
        "request", "response", "session", "servletContext",
        PageContext.LOCALE_KEY, PageContext.TIMEZONE_KEY
    };
    private static final Logger logger = LoggerFactory.getLogger(ActionTag.class);

    /**
     * @return int
     * @throws Exception
     */
    @Override
    public int doStartTag() throws Exception {
        super.doStartTag();
        this.parameters = new Parameters();

        if(this.page == null && this.className == null) {
            return Tag.SKIP_BODY;
        }
        else {
            return Tag.EVAL_BODY_INCLUDE;
        }
    }

    /**
     * @return int
     * @throws Exception
     */
    @Override
    public int doEndTag() throws Exception {
        Map<String, Object> context = this.getContext(this.pageContext, EXPORTS);
        context.putAll(this.parameters.getParameters());

        if(this.className != null) {
            logger.debug("include: {}", this.className);
            Map<String, Object> data = ActionDispatcher.dispatch(this.pageContext, this.parameters, this.className, this.method);

            if(data != null) {
                context.putAll(data);
            }
        }

        if(this.page != null) {
            logger.debug("include: {}", this.page);
            this.pageContext.include(this.getPage(), context);
        }
        return Tag.EVAL_PAGE;
    }

    /**
     * @param pageContext
     * @param names
     * @return Map<String, Object>
     */
    private Map<String, Object> getContext(PageContext pageContext, String[] names) {
        Map<String, Object> context = new HashMap<String, Object>();

        if(names != null) {
            Object value = null;

            for(String name : names) {
                value = pageContext.getAttribute(name);

                if(value != null) {
                    context.put(name, value);
                }
            }
        }
        return context;
    }

    /**
     * @return the className
     */
    public String getClassName() {
        return this.className;
    }

    /**
     * @param className the className to set
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * @return the method
     */
    public String getMethod() {
        return this.method;
    }

    /**
     * @param method the method to set
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * @return the page
     */
    public String getPage() {
        return this.page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(String page) {
        this.page = page;
    }

    /**
     * @param name
     * @param value
     */
    @Override
    public void setAttribute(String name, Object value) {
        this.parameters.setValue(name, value);
    }

    /**
     * @param name
     * @param value
     */
    @Override
    public void setParameter(String name, Object value) {
        this.parameters.setValue(name, value);
    }

    /**
     * @return the parameters
     */
    public Parameters getParameters() {
        return this.parameters;
    }

    /**
     * @param parameters the parameters to set
     */
    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    /**
     * release
     */
    @Override
    public void release() {
        super.release();
        this.parameters.clear();
    }
}