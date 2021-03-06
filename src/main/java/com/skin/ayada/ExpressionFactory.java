/*
 * $RCSfile: ExpressionFactory.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-02-27 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada;

import com.skin.ayada.ExpressionContext;
import com.skin.ayada.PageContext;

/**
 * <p>Title: ExpressionFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public interface ExpressionFactory {
    /**
     * @param pageContext
     * @return ExpressionContext
     */
    public ExpressionContext getExpressionContext(PageContext pageContext);
}
