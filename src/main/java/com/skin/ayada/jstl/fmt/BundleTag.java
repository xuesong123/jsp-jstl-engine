/*
 * $RCSfile: BundleTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-26 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.fmt;

import java.util.Locale;

import com.skin.ayada.runtime.PageContext;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TagSupport;
import com.skin.ayada.tagext.TryCatchFinally;

/**
 * <p>Title: BundleTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class BundleTag extends TagSupport implements TryCatchFinally {
    private String basename;
    private String prefix;
    private LocalizationContext oldBundle;
    private Object oldPrefix;
    private static final String BUNDLE_PREFIX_KEY = "com.skin.ayada.bundle.prefix";

    /**
     * @return int
     * @throws Exception
     */
    @Override
    public int doStartTag() throws Exception {
        this.oldBundle = this.pageContext.getBundle();
        this.oldPrefix = this.pageContext.getAttribute(BUNDLE_PREFIX_KEY);

        LocalizationContext bundle = BundleTag.getBundle(this.pageContext, this.basename);
        this.pageContext.setBundle(bundle);

        if(this.prefix != null) {
            this.pageContext.setAttribute(BUNDLE_PREFIX_KEY, this.prefix);
        }
        else if(this.oldPrefix != null) {
            this.pageContext.removeAttribute(BUNDLE_PREFIX_KEY);
        }
        return Tag.EVAL_BODY_INCLUDE;
    }

    /**
     * @param throwable
     * @throws Throwable
     */
    @Override
    public void doCatch(Throwable throwable) throws Throwable {
        throw throwable;
    }

    @Override
    public void doFinally() {
        this.pageContext.setBundle(this.oldBundle);
        this.pageContext.setAttribute(BUNDLE_PREFIX_KEY, this.oldPrefix);
    }

    /**
     * @param basename
     * @return LocalizationContext
     */
    public static LocalizationContext getBundle(PageContext pageContext, String basename) {
        Locale locale = pageContext.getLocale();

        if(locale == null) {
            throw new RuntimeException("locale must be not null ! please set attribute 'com.skin.ayada.locale' !");
        }
        return BundleManager.getInstance().getBundle(basename, locale);
    }

    /**
     * @param basename the basename to set
     */
    public void setBasename(String basename) {
        this.basename = basename;
    }

    /**
     * @return the basename
     */
    public String getBasename() {
        return this.basename;
    }

    /**
     * @return the prefix
     */
    public String getPrefix() {
        return this.prefix;
    }

    /**
     * @param prefix the prefix to set
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
