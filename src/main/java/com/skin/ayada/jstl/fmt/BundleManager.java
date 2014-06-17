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
     * @param baseName
     * @param locale
     * @return LocalizationContext
     */
    public LocalizationContext getBundle(final String baseName, final Locale locale)
    {
        String language = locale.getLanguage();
        String country = locale.getCountry();
        String variant = locale.getVariant();
        int length = baseName.length() + language.length() + 1;

        StringBuilder buffer = new StringBuilder();
        buffer.append(baseName);
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

        LocalizationContext localizationContext = this.getCachedBundle(buffer.toString(), locale);

        if(localizationContext == null)
        {
            buffer.setLength(length);

            if(country != null && country.length() > 0)
            {
                buffer.append("_");
                buffer.append(country);
            }

            localizationContext = this.getCachedBundle(buffer.toString(), locale);
        }

        if(localizationContext == null)
        {
            buffer.setLength(length);
            localizationContext = this.getCachedBundle(buffer.toString(), locale);
        }

        return localizationContext;
    }

    /**
     * @param fullName
     * @param locale
     * @return LocalizationContext
     */
    public LocalizationContext getCachedBundle(final String fullName, final Locale locale)
    {
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

        LocalizationContext localizationContext = null;

        try
        {
            localizationContext = f.get();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return localizationContext;
    }

    /**
     * @param baseName
     * @param locale
     * @return String
     */
    protected String getFullName(String baseName, Locale locale)
    {
        String language = locale.getLanguage();
        String country = locale.getCountry();
        String variant = locale.getVariant();

        StringBuilder buffer = new StringBuilder();
        buffer.append(baseName);
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
     * @param basename
     * @param locale
     * @param classLoader
     * @return ResourceBundle
     */
    public ResourceBundle getBundle2(String basename, Locale locale, ClassLoader classLoader)
    {
        return ResourceBundle.getBundle(basename, locale, classLoader);
    }

    /**
     * @param name
     * @return ResourceBundle
     */
    public ResourceBundle getBaseBundle(String name)
    {
        InputStream inputStream = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

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
        test("ayada_i18n", new Locale("zh", "cn", "myvar"));
    }

    /**
     * @param baseName
     * @param locale
     */
    public static void test(String baseName, Locale locale)
    {
        BundleManager bundleManager = BundleManager.getInstance();
        LocalizationContext localizationContext = bundleManager.getBundle("ayada_i18n", locale);
        ResourceBundle resourceBundle1 = localizationContext.getResourceBundle();
        System.out.println(resourceBundle1.getString("test.common.test1"));
    }
}
