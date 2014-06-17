/*
 * $RCSfile: LocaleTest.java,v $$
 * $Revision: 1.1 $
 * $Date: 2014年6月16日 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.util;

import java.util.Locale;

/**
 * <p>Title: LocaleTest</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class LocaleTest
{
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        Locale[] list = Locale.getAvailableLocales();

        for(Locale locale : list)
        {
            String language = locale.getLanguage();
            String country = locale.getCountry();
            String variant = locale.getVariant();
            System.out.println("language: " + language + ", country: " + country + ", variant: " + variant);
        }
    }
}
