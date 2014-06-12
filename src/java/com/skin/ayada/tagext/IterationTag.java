/*
 * $RCSfile: IterationTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-2-19 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.tagext;

/**
 * <p>Title: IterationTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public interface IterationTag extends Tag
{
    public static final int EVAL_BODY_AGAIN = 2;

    /**
     * Process body (re)evaluation. This method is invoked by the JSP Page implementation object after every evaluation of the body into the BodyEvaluation object. The method is not invoked if there is no body evaluation.
     * If doAfterBody returns EVAL_BODY_AGAIN, a new evaluation of the body will happen (followed by another invocation of doAfterBody). If doAfterBody returns SKIP_BODY, no more body evaluations will occur, and the doEndTag method will be invoked.
     * If this tag handler implements BodyTag and doAfterBody returns SKIP_BODY, the value of out will be restored using the popBody method in pageContext prior to invoking doEndTag.
     * The method re-invocations may be lead to different actions because there might have been some changes to shared state, or because of external computation.
     * The JSP container will resynchronize the values of any AT_BEGIN and NESTED variables (defined by the associated TagExtraInfo or TLD) after the invocation of doAfterBody().
     * @return whether additional evaluations of the body are desired
     */
    public int doAfterBody() throws Exception;
}
