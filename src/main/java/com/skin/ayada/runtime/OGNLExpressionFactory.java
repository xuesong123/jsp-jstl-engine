/*
 * $RCSfile: OGNLExpressionFactory.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-03-18 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.runtime;

import com.skin.ayada.ExpressionContext;
import com.skin.ayada.PageContext;

/**
 * <p>Title: OGNLExpressionFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class OGNLExpressionFactory extends DefaultExpressionFactory {
    /**
     * @param pageContext
     * @return ExpressionContext
     */
    @Override
    public ExpressionContext create(PageContext pageContext) {
        return new OGNLExpressionContext(pageContext.getContext());
    }
}
