/*
 * $RCSfile: TemplateManager.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-3-10 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.template;

import java.util.HashMap;
import java.util.Map;

import com.skin.ayada.source.DefaultSourceFactory;
import com.skin.ayada.source.SourceFactory;

/**
 * <p>Title: TemplateManager</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class TemplateManager
{
    private static final Map<String, TemplateContext> map = new HashMap<String, TemplateContext>();
    private TemplateManager(){}

    /**
     * @param home
     * @return TemplateContext
     */
    public static TemplateContext getTemplateContext(String home)
    {
        return getTemplateContext(home, false);
    }

    /**
     * @param home
     * @param create
     * @return TemplateContext
     */
    public static TemplateContext getTemplateContext(String home, boolean create)
    {
        TemplateContext templateContext = map.get(home);

        if(templateContext == null && create == true)
        {
            templateContext = create(home, 600);
            map.put(home, templateContext);
        }

        return templateContext;
    }

    /**
     * @param home
     * @param expire
     * @return TemplateContext
     */
    public static TemplateContext create(String home, int expire)
    {
        SourceFactory sourceFactory = new DefaultSourceFactory(home);
        TemplateFactory templateFactory = new TemplateFactory();
        TemplateContext templateContext = new TemplateContext(home, expire);
        templateContext.setSourceFactory(sourceFactory);
        templateContext.setTemplateFactory(templateFactory);
        return templateContext;
    }

    /**
     * @param templateContext
     */
    public static void add(TemplateContext templateContext)
    {
        if(map.get(templateContext.getHome()) == null)
        {
            map.put(templateContext.getHome(), templateContext);
        }
        else
        {
            throw new RuntimeException("context already exists !");
        }
    }
}
