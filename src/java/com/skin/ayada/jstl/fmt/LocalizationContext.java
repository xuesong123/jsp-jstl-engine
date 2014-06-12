/*
 * $RCSfile: LocalizationContext.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-2-26 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.fmt;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * <p>Title: LocalizationContext</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class LocalizationContext
{
    private Locale locale;
    private ResourceBundle resourceBundle;

    public LocalizationContext()
    {
    }

    /**
     * @param bundle
     */
    public LocalizationContext(ResourceBundle bundle)
    {
        this.resourceBundle = bundle;
    }

    /**
     * @param bundle
     * @param locale
     */
    public LocalizationContext(ResourceBundle resourceBundle, Locale locale)
    {
        this.resourceBundle = resourceBundle;
        this.locale = locale;
    }

    /**
     * @return the bundle
     */
    public ResourceBundle getResourceBundle()
    {
        return this.resourceBundle;
    }

    /**
     * @param bundle the bundle to set
     */
    public void setResourceBundle(ResourceBundle resourceBundle)
    {
        this.resourceBundle = resourceBundle;
    }

    /**
     * @return the locale
     */
    public Locale getLocale()
    {
        return this.locale;
    }

    /**
     * @param locale the locale to set
     */
    public void setLocale(Locale locale)
    {
        this.locale = locale;
    }
}
