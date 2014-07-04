/*
 * $RCSfile: TemplateConfig.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-10-30 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.config;

/**
 * <p>Title: TemplateConfig</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class TemplateConfig
{
    private static final Config instance = TemplateConfig.create();

    public static Config getInstance()
    {
        return instance;
    }

    public static Config create()
    {
        Config config = ConfigFactory.getConfig("ayada.properties", Config.class);

        if(config.size() < 1)
        {
            config = ConfigFactory.getConfig("ayada-default.properties", Config.class);
        }

        return config;
    }
}
