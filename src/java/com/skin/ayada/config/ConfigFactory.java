/*
 * $RCSfile: DomainConfigFactory.java,v $$
 * $Revision: 1.1 $
 * $Date: 2009-7-14 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.config;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.skin.ayada.resource.ClassPathResource;
import com.skin.ayada.resource.PropertyResource;

/**
 * <p>Title: DomainConfigFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class ConfigFactory
{
    /**
     * @param <T>
     * @param resource
     * @param type
     * @return T
     */
    public static <T extends Config> T getConfig(String resource, Class<T> type)
    {
        InputStream inputStream = null;

        try
        {
            inputStream = ClassPathResource.getResourceAsStream(resource);

            if(inputStream == null)
            {
                return null;
            }

            Map<String, String> map = new HashMap<String, String>();
            PropertyResource.load(inputStream, map);
            T config = type.newInstance();
            config.setValues(map);
            return config;
        }
        catch(InstantiationException e)
        {
            e.printStackTrace();
        }
        catch(IllegalAccessException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(inputStream != null)
            {
                try
                {
                    inputStream.close();
                }
                catch(IOException e)
                {
                }
            }
        }

        return null;
    }

    /**
     * @param config
     * @param resource
     */
    public static void save(Config config, String resource)
    {
        URL url = ClassPathResource.getResource(resource);
        OutputStream outputStream = null;

        try
        {
            String file = url.getFile();

            if(file.startsWith("file:"))
            {
                file = file.substring(5);
            }

            outputStream = new FileOutputStream(file);
            ConfigFactory.save(config, outputStream);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(outputStream != null)
            {
                try
                {
                    outputStream.close();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param config
     * @param outputStream
     */
    public static void save(Config config, OutputStream outputStream)
    {
        Writer writer = null;

        try
        {
            writer = new OutputStreamWriter(outputStream, "UTF-8");
            PropertyResource.save(config.getMap(), writer);
            outputStream.flush();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(writer != null)
            {
                try
                {
                    writer.close();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param config
     * @param writer
     */
    public static void save(Config config, Writer writer)
    {
        try
        {
            PropertyResource.save(config.getMap(), writer);
            writer.flush();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(writer != null)
            {
                try
                {
                    writer.close();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
