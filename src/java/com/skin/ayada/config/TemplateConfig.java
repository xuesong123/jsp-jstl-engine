/*
 * $RCSfile: TemplateConfig.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-10-30 $
 *
 * Copyright (C) 2008 WanMei, Inc. All rights reserved.
 *
 * This software is the proprietary information of WanMei, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.config;

import java.util.Map;

/**
 * <p>Title: TemplateConfig</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class TemplateConfig extends Config
{
    private static final long serialVersionUID = 1L;
    private static final TemplateConfig instance = TemplateConfig.create();

    public static TemplateConfig getInstance()
    {
        return instance;
    }

    public static TemplateConfig create()
    {
        TemplateConfig c1 = ConfigFactory.getConfig("config-default.properties", TemplateConfig.class);
        TemplateConfig c2 = ConfigFactory.getConfig("config.properties", TemplateConfig.class);

        if(c1 == null)
        {
            throw new NullPointerException("\"config-default.properties\" not found !");
        }

        if(c2 != null)
        {
            for(Map.Entry<String, String> entry : c2.entrySet())
            {
                c1.put(entry.getKey(), entry.getValue());
            }
        }

        return c1;
    }
}
