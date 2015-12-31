/*
 * $RCSfile: I18n.java,v $$
 * $Revision: 1.1 $
 * $Date: 2014-04-11 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.util;

import java.util.Locale;

import com.skin.ayada.jstl.fmt.BundleManager;
import com.skin.ayada.jstl.fmt.LocalizationContext;

/**
 * <p>Title: I18n</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class I18n {
    public I18n() {
    }

    /**
     * @param basename
     * @param value
     * @return Object
     */
    public LocalizationContext getBundle(String basename, String value) {
        return this.getBundle(basename, value, null);
    }

    /**
     * @param basename
     * @param value
     * @param variant
     * @return Object
     */
    public LocalizationContext getBundle(String basename, String value, String variant) {
        Locale locale = I18n.getLocale(value, variant);
        return BundleManager.getInstance().getBundle(basename, locale);
    }

    /**
     * @param value
     * @param variant
     * @return Local
     */
    public static Locale getLocale(String value, String variant) {
        int i = 0;
        char ch = '\000';
        int length = value.length();
        StringBuilder buffer = new StringBuilder();

        for(; i < length; i++) {
            ch = value.charAt(i);

            if(ch != '_' && ch != '-') {
                buffer.append(ch);
            }
            else {
                break;
            }
        }

        String language = buffer.toString();
        String country = "";

        if((ch == '_') || (ch == '-')) {
            buffer.setLength(0);

            for(i++; i < length; i++) {
                ch = value.charAt(i);

                if(ch != '_' && ch != '-') {
                    buffer.append(ch);
                }
                else {
                    break;
                }
            }

            country = buffer.toString();
        }

        if(variant != null && variant.length() > 0) {
            return new Locale(language, country, variant);
        }

        return new Locale(language, country);
    }
}
