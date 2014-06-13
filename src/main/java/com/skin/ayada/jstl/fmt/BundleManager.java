/*
 * $RCSfile: BundleManager.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-2-26 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.fmt;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.FutureTask;

/**
 * <p>Title: BundleManager</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class BundleManager
{
    private static BundleManager instance = new BundleManager();
    private ConcurrentHashMap<String, FutureTask<LocalizationContext>> cache;

    private BundleManager()
    {
        this.cache = new ConcurrentHashMap<String, FutureTask<LocalizationContext>>();
    }

    /**
     * @return BundleManager
     */
    public static BundleManager getInstance()
    {
        return instance;
    }

    /**
     * @param name
     * @param locale
     * @return LocalizationContext
     */
    public LocalizationContext getBundle(final String name, final Locale locale)
    {
        final String fullName = this.getFullName(name, locale);
        FutureTask<LocalizationContext> f = this.cache.get(fullName);
        
        if(f == null)
        {
            Callable<LocalizationContext> callable = new Callable<LocalizationContext>(){
                public LocalizationContext call() throws InterruptedException
                {
                    try
                    {
                        ResourceBundle resourceBundle = getBaseBundle(fullName);

                        if(resourceBundle != null)
                        {
                            return new LocalizationContext(resourceBundle, locale);
                        }
                        
                        return null;
                    }
                    catch(Exception e)
                    {
                        throw new RuntimeException(e);
                    }
                }
            };

            FutureTask<LocalizationContext> futureTask = new FutureTask<LocalizationContext>(callable);
            f = this.cache.putIfAbsent(fullName, futureTask);

            if(f == null)
            {
                f = futureTask;
                f.run();
            }
        }

        LocalizationContext LocalizationContext = null;

        try
        {
            LocalizationContext = f.get();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return LocalizationContext;
    }

    /**
     * @param basename
     * @param locale
     * @return String
     */
    protected String getFullName(String basename, Locale locale)
    {
        String language = locale.getLanguage();
        String country = locale.getCountry();
        String variant = locale.getVariant();

        StringBuilder buffer = new StringBuilder();
        buffer.append(basename);
        buffer.append("_");
        buffer.append(language);
        
        if(country != null && country.length() > 0)
        {
            buffer.append("_");
            buffer.append(country);
        }

        if(variant != null && variant.length() > 0)
        {
            buffer.append("_");
            buffer.append(variant);
        }

        return buffer.toString();
    }

    /**
     * @param name
     * @return ResourceBundle
     */
    private ResourceBundle getBaseBundle(String name)
    {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try
        {
            Class<?> type = Class.forName(name, false, loader);

            if(type != null)
            {
                ResourceBundle resourceBundle = (ResourceBundle)type.newInstance();

                if(resourceBundle != null)
                {
                    return resourceBundle;
                }
            }
        }
        catch(Throwable e)
        {
        }

        InputStream inputStream = null;

        try
        {
            inputStream = loader.getResourceAsStream(name.replace('.', '/') + ".properties");
            return new PropertyResourceBundle(inputStream);
        }
        catch(Exception e)
        {
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

    public static void main(String[] args)
    {
        LocalizationContext localizationContext = BundleManager.getInstance().getBundle("ayada_i18n", new Locale("zh_cn"));
        ResourceBundle resourceBundle = localizationContext.getResourceBundle();
        System.out.println(resourceBundle.getString("test.common.test1"));
    }
}
