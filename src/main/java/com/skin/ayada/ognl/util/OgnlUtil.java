/*
 * $RCSfile: OgnlUtil.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-10-21 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.ognl.util;

import java.util.HashMap;

import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;

/**
 * <p>Title: OgnlUtil</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class OgnlUtil
{
    private static HashMap<String, Object> expressions = new HashMap<String, Object>();

    private OgnlUtil()
    {
    }

    /**
     * @param expression
     * @param context
     * @param root
     * @return Object
     */
    public static Object getValue(String expression, OgnlContext context, Object root)
    {
        try
        {
            return Ognl.getValue(compile(expression), context, root);
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param expression
     * @return Object
     * @throws OgnlException
     */
    public static Object compile(String expression) throws OgnlException
    {
        synchronized(expressions)
        {
            Object tree = expressions.get(expression);

            if(tree == null)
            {
                tree = Ognl.parseExpression(expression);
                expressions.put(expression, tree);
            }

            return tree;
        }
    }
}
