/*
 * $RCSfile: VelocityTest.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-11-08 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.test.engine;

import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

import com.skin.ayada.test.Benchmark;
import com.skin.ayada.test.TestWriter;

/**
 * <p>Title: VelocityTest</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class VelocityTest implements Benchmark {
    private VelocityEngine velocityEngine;

    /**
     * default
     */
    public VelocityTest() {
    }

    /**
     * @return the name
     */
    @Override
    public String getName() {
        return "velocity";
    }

    /**
     * @param work
     * @throws Exception
     */
    @Override
    public void init(String work) throws Exception {
        java.util.Properties properties = new java.util.Properties();
        properties.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, work);
        this.velocityEngine = new VelocityEngine();
        this.velocityEngine.init(properties);
    }

    /**
     * @param name
     * @param context
     * @param stringWriter
     * @param count
     * @throws Exception
     */
    @Override
    public void execute(String name, Map<String, Object> context, TestWriter stringWriter, int count) throws Exception {
        context.put("engineName", this.getName());
        Template template = this.velocityEngine.getTemplate(name + ".vm", "utf-8");
        VelocityContext velocityContext = new VelocityContext(context);

        for(int i = 0; i < count; i++) {
            stringWriter.getBuffer().setLength(0);
            template.merge(velocityContext, stringWriter);
        }
    }
}
