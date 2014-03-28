/*
 * $RCSfile: BodyTag.java,v $$
 * $Revision: 1.1  $
 * $Date: 2013-2-19  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.tagext;


/**
 * <p>Title: BodyTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public interface BodyTag extends IterationTag
{
    /**
     * Request the creation of new buffer, a BodyContent on which to evaluate the body of this tag.
     * Returned from doStartTag when it implements BodyTag.
     * This is an illegal return value for doStartTag when the class does not implement BodyTag.
     */
    public static final int EVAL_BODY_BUFFERED = 2;

    /**
     * Prepare for evaluation of the body. This method is invoked by the JSP page implementation object after setBodyContent and before the first time the body is to be evaluated.
     * This method will not be invoked for empty tags or for non-empty tags whose doStartTag() method returns SKIP_BODY or EVAL_BODY_INCLUDE.
     * The JSP container will resynchronize the values of any AT_BEGIN and NESTED variables (defined by the associated TagExtraInfo or TLD) after the invocation of doInitBody().
     * @throws Exception 
     */
    public void doInitBody() throws Exception;

    /**
     * Set the bodyContent property. This method is invoked by the JSP page implementation object at most once per action invocation.
     * This method will be invoked before doInitBody. This method will not be invoked for empty tags or for non-empty tags whose doStartTag() method returns SKIP_BODY or EVAL_BODY_INCLUDE.
     * When setBodyContent is invoked, the value of the implicit object out has already been changed in the pageContext object.
     * The BodyContent object passed will have not data on it but may have been reused (and cleared) from some previous invocation.
     * The BodyContent object is available and with the appropriate content until after the invocation of the doEndTag method, at which case it may be reused.
     * @param bodyContent the bodyContent to set
     */
    public void setBodyContent(BodyContent bodyContent);
}
