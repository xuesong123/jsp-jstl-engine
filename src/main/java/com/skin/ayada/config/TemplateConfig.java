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

import java.util.Set;

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
        Config c1 = ConfigFactory.getConfig("ayada-default.properties", Config.class);
        Config c2 = ConfigFactory.getConfig("ayada.properties", Config.class);

        if(c1 == null)
        {
            throw new NullPointerException("\"config-default.properties\" not found !");
        }

        if(c2 != null)
        {
            Set<String> set = c2.getNames();

            for(String name : set)
            {
                c1.setValue(name, c2.getValue(name));
            }
        }

        return c1;
    }
}
