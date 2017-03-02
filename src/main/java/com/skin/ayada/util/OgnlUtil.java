/*
 * $RCSfile: OgnlUtil.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-10-21 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.util;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.FutureTask;

import ognl.Ognl;

/**
 * <p>Title: Template</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class OgnlUtil {
    private static ConcurrentHashMap<String, FutureTask<Object>> cache = new ConcurrentHashMap<String, FutureTask<Object>>(2048);

    private OgnlUtil() {
    }

    /**
     * @param expression
     * @param context
     * @return Object
     * @throws Exception
     */
    public static Object getValue(String expression, Map<String, Object> context) throws Exception {
        Object tree = compile(expression);
        return Ognl.getValue(tree, context);
    }

    /**
     * @param expression
     * @return Object
     * @throws Exception 
     */
    public static Object compile(final String expression) throws Exception {
        Object tree = null;

        while(true) {
            FutureTask<Object> f = cache.get(expression);

            if(f == null) {
                Callable<Object> callable = new Callable<Object>() {
                    /**
                     * @throws InterruptedException
                     */
                    @Override
                    public Object call() throws InterruptedException {
                        try {
                            return Ognl.parseExpression(expression);
                        }
                        catch(Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                };

                FutureTask<Object> futureTask = new FutureTask<Object>(callable);
                f = cache.putIfAbsent(expression, futureTask);

                if(f == null) {
                    f = futureTask;
                    futureTask.run();
                }
            }

            tree = f.get();

            if(tree != null) {
                return tree;
            }
        }
    }

    /**
     * print detail
     */
    public static void print() {
        try {
            for(Map.Entry<String, FutureTask<Object>> entry : cache.entrySet()) {
                Object value = entry.getValue().get();
                System.out.println(entry.getKey() + ": [" + value.getClass().getName() + "]: " + value);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
