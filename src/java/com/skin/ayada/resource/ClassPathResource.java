/*
 * $RCSfile: ClassPathResource.java,v $
 * $Revision: 1.1  $
 * $Date: 2009-3-26  $
 *
 * Copyright (C) 2005 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;


/**
 * <p>Title: ClassPathResource</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class ClassPathResource
{
    public static final Class<ClassPathResource> model = ClassPathResource.class;

    /**
     * @param name
     * @return URL
     */
    public static URL getResource(String name)
    {
        return model.getClassLoader().getResource(name);
    }

    /**
     * @param name
     * @return URL
     */
    public static Enumeration<URL> getResources(String name)
    {
        try
        {
            return model.getClassLoader().getResources(name);
        }
        catch(IOException e)
        {
        }

        return null;
    }

    public static InputStream getResourceAsStream(String name)
    {
        return model.getClassLoader().getResourceAsStream(name);
    }
}
