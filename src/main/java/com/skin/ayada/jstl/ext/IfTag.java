/*
 * $RCSfile: IfTag.java,v $
 * $Revision: 1.1 $
 * $Date: 2011-12-09 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.ext;

import com.skin.ayada.PageContext;
import com.skin.ayada.tagext.ConditionalTagSupport;
import com.skin.ayada.util.Stack;

/**
 * <p>Title: IfTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class IfTag extends ConditionalTagSupport {
    private boolean flag = false;

    /**
     * @return int
     * @throws Exception
     */
    @Override
    public int doStartTag() throws Exception {
        Stack<Object> stack = getStack(this.pageContext, true);
        stack.push(this);

        if(this.condition() == true) {
            this.finish();
            return EVAL_BODY_INCLUDE;
        }
        else {
            return SKIP_BODY;
        }
    }

    /**
     * @param pageContext
     * @return IfTag
     */
    public static IfTag getIfTag(PageContext pageContext) {
        Stack<Object> stack = getStack(pageContext, false);

        if(stack == null) {
            return null;
        }
        else {
            return (IfTag)(stack.peek());
        }
    }

    /**
     * @param pageContext
     */
    public static void remove(PageContext pageContext) {
        Stack<Object> stack = getStack(pageContext, false);

        if(stack != null && stack.size() > 0) {
            stack.pop();
        }
    }

    /**
     * @param pageContext
     * @param create
     * @return Stack<Object>
     */
    @SuppressWarnings("unchecked")
    public static Stack<Object> getStack(PageContext pageContext, boolean create) {
        Stack<Object> stack = (Stack<Object>)(pageContext.getAttribute("ifStack"));

        if(stack == null && create) {
            stack = new Stack<Object>();
            pageContext.setAttribute("ifStack", stack);
        }
        return stack;
    }

    /**
     * @return boolean
     */
    public boolean complete() {
        return this.flag;
    }

    /**
     *
     */
    public void finish() {
        this.flag = true;
    }
}
