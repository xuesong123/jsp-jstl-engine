/*
 * $RCSfile: TryCatchFinally.java,v $$
 * $Revision: 1.1 $
 * $Date: 2014-03-25 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.tagext;

/**
 * <p>Title: TryCatchFinally</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public interface TryCatchFinally {
    /**
     * Invoked if a Throwable occurs while evaluating the BODY inside a tag or in any of the following methods: Tag.doStartTag(), Tag.doEndTag(), IterationTag.doAfterBody() and BodyTag.doInitBody().
     * This method is not invoked if the Throwable occurs during one of the setter methods.
     * This method may throw an exception (the same or a new one) that will be propagated further up the nest chain. If an exception is thrown, doFinally() will be invoked.
     * This method is intended to be used to respond to an exceptional condition.
     * @param throwable
     * @throws java.lang.Throwable
     */
    public void doCatch(Throwable throwable) throws java.lang.Throwable;

    /**
     * Invoked in all cases after doEndTag() for any class implementing Tag, IterationTag or BodyTag. This method is invoked even if an exception has occurred in the BODY of the tag, or in any of the following methods: Tag.doStartTag(), Tag.doEndTag(), IterationTag.doAfterBody() and BodyTag.doInitBody().
     * This method is not invoked if the Throwable occurs during one of the setter methods.
     * This method should not throw an Exception.
     * This method is intended to maintain per-invocation data integrity and resource management actions.
     */
    public void doFinally();
}
