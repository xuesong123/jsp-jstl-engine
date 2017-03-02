/*
 * $RCSfile: HttlTest.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-11-08 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.test.engine;

import httl.Engine;

import java.io.File;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

import com.skin.ayada.test.Benchmark;

/**
 * <p>Title: HttlTest</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class HttlTest implements Benchmark {
    private Engine engine;

    /**
     * default
     */
    public HttlTest() {
    }

    /**
     * @return the name
     */
    @Override
    public String getName() {
        return "httl";
    }


    /**
     * @param work
     * @throws Exception
     */
    @Override
    public void init(String work) throws Exception {
        String directory = new File("webapp/benchmark").getCanonicalPath();
        Properties properties = new Properties();
        properties.setProperty("template.directory", directory);
        this.engine = Engine.getEngine("httl.properties", properties);
    }

    /**
     * @param name
     * @param context
     * @param stringWriter
     * @param count
     * @throws Exception
     */
    @Override
    public void execute(String name, Map<String, Object> context, StringWriter stringWriter, int count) throws Exception {
        String fullName = name + ".httl";
        context.put("engineName", this.getName());

        for(int i = 0; i < count; i++) {
            stringWriter.getBuffer().setLength(0);
            this.engine.getTemplate(fullName).render(context, stringWriter);
        }
    }
}
