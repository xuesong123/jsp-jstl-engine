/*
 * $RCSfile: PerformanceTest.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-11-08 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.test;

import java.util.Map;

/**
 * <p>Title: Benchmark</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public interface Benchmark {
    /**
     * @return String
     */
    String getName();

    /**
     * @param work
     * @throws Exception
     */
    void init(String work) throws Exception;

    /**
     * @param name
     * @param context
     * @param stringWriter
     * @param count
     * @throws Exception
     */
    void execute(String name, Map<String, Object> context, TestWriter stringWriter, int count) throws Exception;
}
