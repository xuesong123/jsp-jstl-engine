/*
 * $RCSfile: SimpleTemplate.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-19 $
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
 * <p>Title: SimpleTemplate</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class SimpleTemplate
{
    private static final TemplateFactory templateFactory = new TemplateFactory();
    private static final Logger logger = LoggerFactory.getLogger(SimpleTemplate.class);

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        try
        {
            StringWriter out = new StringWriter();
            execute("123<c:if test=\"${1 == 1}\">abc</c:if>xyz", null, out);
            System.out.println(out.toString());
        }
        catch(Exception e)
        {
            logger.warn(e.getMessage(), e);
        }
    }

    /**
     * @param source
     * @param context
     * @param writer
     */
    public static void execute(String source, Map<String, Object> context, Writer writer) throws Exception
    {
        try
        {
            Template template = templateFactory.compile(source);
            PageContext pageContext = JspFactory.getDefaultPageContext(writer);

            if(context != null)
            {
                for(Map.Entry<String, Object> entry : context.entrySet())
                {
                    if(entry.getValue() != null)
                    {
                        pageContext.setAttribute(entry.getKey(), entry.getValue());
                    }
                }
            }

            template.execute(pageContext);
        }
        catch(Exception e)
        {
            logger.warn(e.getMessage(), e);
        }
    }
}
