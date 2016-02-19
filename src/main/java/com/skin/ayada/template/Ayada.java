/*
 * $RCSfile: Ayada.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-08 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.template;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skin.ayada.runtime.JspFactory;
import com.skin.ayada.runtime.PageContext;

/**
 * <p>Title: Ayada</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class Ayada {
    private static final TemplateFactory templateFactory = new TemplateFactory();
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
        Template template = templateFactory.compile(source);
        PageContext pageContext = JspFactory.getPageContext(context, writer);
        template.execute(pageContext);
        return writer.toString();
    }

    /**
     * @param source
     * @param context
     * @param writer
     * @throws Exception
     */
    public static void execute(String source, Map<String, Object> context, Writer writer) throws Exception {
        Template template = templateFactory.compile(source);
        PageContext pageContext = JspFactory.getPageContext(context, writer);
        template.execute(pageContext);
    }
}
