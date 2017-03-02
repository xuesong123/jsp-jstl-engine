/*
 * $RCSfile: OGNLExpressionContext.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-02-20 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.runtime;

import java.util.Map;

import com.skin.ayada.util.OgnlUtil;

/**
 * <p>Title: OGNLExpressionContext</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class OGNLExpressionContext extends DefaultExpressionContext {
    /**
     * @param context
     */
    protected OGNLExpressionContext(Map<String, Object> context) {
        super(context);
    }

    /**
     * @param expression
     * @return Object
     * @throws Exception
     */
    @Override
    public Object evaluate(String expression) throws Exception {
        return OgnlUtil.getValue(expression, this.getContext());
    }
}
