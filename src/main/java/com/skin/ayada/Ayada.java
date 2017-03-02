/*
 * $RCSfile: Ayada.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-11-08 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skin.ayada.runtime.DefaultExpressionFactory;
import com.skin.ayada.runtime.DefaultTemplateContext;
import com.skin.ayada.runtime.DefaultTemplateFactory;
import com.skin.ayada.source.StringSourceFactory;

/**
 * <p>Title: Ayada</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class Ayada {
    private static final Logger logger = LoggerFactory.getLogger(Ayada.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            StringWriter out = new StringWriter();
            Ayada.execute("123<c:if test=\"${1 == 1}\">abc</c:if>xyz", null, out);
            System.out.println(out.toString());
        }
        catch(Exception e) {
            logger.warn(e.getMessage(), e);
        }
    }

    /**
     * @param source
     * @param context
     * @return String
     * @throws Exception
     */
    public static String eval(String source, Map<String, Object> context) throws Exception {
        StringWriter writer = new StringWriter();
        execute(source, context, writer);
        return writer.toString();
    }

    /**
     * @param source
     * @param context
     * @param writer
     * @throws Exception
     */
    public static void execute(String source, Map<String, Object> context, Writer writer) throws Exception {
        String path = "/default.jsp";
        TemplateContext templateContext = getStringTemplateContext(path, source);
        templateContext.execute(path, context, writer);
    }

    /**
     * @param path
     * @param source
     * @return TemplateContext
     */
    public static TemplateContext getStringTemplateContext(String path, String source) {
        StringSourceFactory sourceFactory = new StringSourceFactory();
        sourceFactory.setSource(path, source);
        TemplateContext templateContext = new DefaultTemplateContext();
        templateContext.setSourceFactory(sourceFactory);
        templateContext.setExpressionFactory(new DefaultExpressionFactory());
        templateContext.setTemplateFactory(new DefaultTemplateFactory());
        return templateContext;
    }
}
