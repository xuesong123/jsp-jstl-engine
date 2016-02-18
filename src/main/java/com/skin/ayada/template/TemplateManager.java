/*
 * $RCSfile: TemplateManager.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-03-10 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.template;

import java.util.HashMap;
import java.util.Map;

import com.skin.ayada.runtime.DefaultExpressionFactory;
import com.skin.ayada.runtime.ExpressionFactory;
import com.skin.ayada.source.DefaultSourceFactory;
import com.skin.ayada.source.SourceFactory;

/**
 * <p>Title: TemplateManager</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class TemplateManager {
    private static final Map<String, TemplateContext> cache = new HashMap<String, TemplateContext>();
    private TemplateManager(){}

    /**
     * @param home
     * @return TemplateContext
     */
    public static TemplateContext getTemplateContext(String home) {
        return getTemplateContext(home, true);
    }

    /**
     * @param home
     * @param create
     * @return TemplateContext
     */
    public static TemplateContext getTemplateContext(String home, boolean create) {
        TemplateContext templateContext = cache.get(home);

        if(templateContext == null && create == true) {
            templateContext = create(home);
            cache.put(home, templateContext);
        }
        return templateContext;
    }

    /**
     * @param home
     * @return TemplateContext
     */
    public static TemplateContext create(String home) {
        SourceFactory sourceFactory = new DefaultSourceFactory(home);
        TemplateFactory templateFactory = new TemplateFactory();
        TemplateContext templateContext = new DefaultTemplateContext();
        ExpressionFactory expressionFactory = new DefaultExpressionFactory();
        templateContext.setSourceFactory(sourceFactory);
        templateContext.setTemplateFactory(templateFactory);
        templateContext.setExpressionFactory(expressionFactory);
        return templateContext;
    }

    /**
     * @param templateContext
     */
    public static void add(TemplateContext templateContext) {
        String home = null;
        SourceFactory sourceFactory = templateContext.getSourceFactory();

        if(sourceFactory != null) {
            home = sourceFactory.getHome();
        }

        if(home != null) {
            TemplateContext context = cache.get(home);

            if(context == null) {
                cache.put(home, templateContext);
            }
            else {
                if(context != templateContext) {
                    throw new RuntimeException("context already exists, home: " + home);
                }
            }
        }
    }
}
