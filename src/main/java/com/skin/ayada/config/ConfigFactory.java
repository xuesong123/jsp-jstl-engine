/*
 * $RCSfile: ConfigFactory.java,v $$
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
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skin.ayada.resource.ClassPathResource;
import com.skin.ayada.resource.PropertyResource;

/**
 * <p>Title: ConfigFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class ConfigFactory
{
    private static final Logger logger = LoggerFactory.getLogger(ConfigFactory.class);

    /**
     * @param <T>
     * @param resource
     * @param type
     * @return T
     */
    public static <T extends Config> T getConfig(String resource, Class<T> type)
    {
        try
        {
            Map<String, String> map = PropertyResource.load(resource, "UTF-8");
            T config = type.newInstance();
            config.setValues(map);
            return config;
        }
        catch(Exception e)
        {
            logger.warn(e.getMessage(), e);
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
            logger.warn(e.getMessage(), e);
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
                    logger.warn(e.getMessage(), e);
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
            logger.warn(e.getMessage(), e);
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
            logger.warn(e.getMessage(), e);
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
                }
            }
        }
    }
}
