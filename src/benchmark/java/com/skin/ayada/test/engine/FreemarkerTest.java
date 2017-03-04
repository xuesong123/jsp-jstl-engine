/*
 * $RCSfile: FreemarkerTest.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-11-08 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.test.engine;

import java.io.File;
import java.util.Map;

import com.skin.ayada.test.Benchmark;
import com.skin.ayada.test.TestWriter;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * <p>Title: FreemarkerTest</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class FreemarkerTest implements Benchmark {
    private Configuration configuration;

    /**
     * default
     */
    public FreemarkerTest() {
    }

    /**
     * @return the name
     */
    @Override
    public String getName() {
        return "freemarker";
    }

    /**
     * @param work
     * @throws Exception
     */
    @Override
    public void init(String work) throws Exception {
        TemplateLoader templateLoader = new FileTemplateLoader(new File(work));
        this.configuration = new Configuration();
        this.configuration.setDefaultEncoding("utf-8");
        this.configuration.setTemplateLoader(templateLoader);
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
        Template template = this.configuration.getTemplate(name + ".ftl");

        for(int i = 0; i < count; i++) {
            stringWriter.getBuffer().setLength(0);
            template.process(context, stringWriter);
        }
    }
}
