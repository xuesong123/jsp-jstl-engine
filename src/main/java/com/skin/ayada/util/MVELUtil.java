/*
 * $RCSfile: MVELUtil.java,v $
 * $Revision: 1.1 $
 * $Date: 2017-2-11 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.util;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.FutureTask;

import org.mvel2.MVEL;

/**
 * <p>Title: MVELUtil</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class MVELUtil {
    private static ConcurrentHashMap<String, FutureTask<Serializable>> cache = new ConcurrentHashMap<String, FutureTask<Serializable>>(2048);

    /**
     * @param expression
     * @param context
     * @return Object
     * @throws Exception
     */
    public static Object getValue(String expression, Map<String, Object> context) throws Exception {
        Serializable serializable = compile(expression);
        return MVEL.executeExpression(serializable, context);
    }

    /**
     * @param expression
     * @return Serializable
     * @throws Exception
     */
    public static Serializable compile(final String expression) throws Exception {
        Serializable serializable = null;

        while(true) {
            FutureTask<Serializable> f = cache.get(expression);

            if(f == null) {
                Callable<Serializable> callable = new Callable<Serializable>(){
                    /**
                     * @throws InterruptedException
                     */
                    @Override
                    public Serializable call() throws InterruptedException {
                        try {
                            return MVEL.compileExpression(expression);
                        }
                        catch(Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                };

                FutureTask<Serializable> futureTask = new FutureTask<Serializable>(callable);
                f = cache.putIfAbsent(expression, futureTask);

                if(f == null) {
                    f = futureTask;
                    futureTask.run();
                }
            }

            serializable = f.get();

            if(serializable != null) {
                return serializable;
            }
        }
    }

    /**
     * print detail
     */
    public static void print() {
        try {
            for(Map.Entry<String, FutureTask<Serializable>> entry : cache.entrySet()) {
                Serializable value = entry.getValue().get();
                System.out.println(entry.getKey() + ": [" + value.getClass().getName() + "]: " + value);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
