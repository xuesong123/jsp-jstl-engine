/*
 * $RCSfile: LocalizationContext.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-26 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.fmt;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * <p>Title: LocalizationContext</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class LocalizationContext {
    private Locale locale;
    private ResourceBundle resourceBundle;

    /**
     *
     */
    public LocalizationContext() {
    }

    /**
     * @param bundle
     */
    public LocalizationContext(ResourceBundle bundle) {
        this.resourceBundle = bundle;
    }

    /**
     * @param resourceBundle
     * @param locale
     */
    public LocalizationContext(ResourceBundle resourceBundle, Locale locale) {
        this.resourceBundle = resourceBundle;
        this.locale = locale;
    }

    /**
     * @param key
     * @return String
     */
    public String message(String key) {
        return this.getLocalizedMessage(key);
    }

    /**
     * @param key
     * @param args
     * @return String
     */
    public String format(String key, Object ... args) {
        return this.getLocalizedMessage(key, args);
    }

    /**
     * @param key
     * @param args
     * @return String
     */
    public String getLocalizedMessage(String key, Object ... args) {
        String result = null;

        if(this.resourceBundle != null) {
            try {
                result = this.resourceBundle.getString(key);
            }
            catch(Exception e) {
            }
        }

        if(result == null) {
            return "???" + key + "???";
        }

        if((args == null) || (args.length == 0)) {
            return result;
        }

        if(this.locale != null) {
            return new MessageFormat(result, this.locale).format(args);
        }
        return new MessageFormat(result).format(args);
    }

    /**
     * @return the bundle
     */
    public ResourceBundle getResourceBundle() {
        return this.resourceBundle;
    }

    /**
     * @param resourceBundle the resourceBundle to set
     */
    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    /**
     * @return the locale
     */
    public Locale getLocale() {
        return this.locale;
    }

    /**
     * @param locale the locale to set
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
