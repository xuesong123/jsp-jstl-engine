/*
 * $RCSfile: MessageTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-2-28 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import com.skin.ayada.tagext.BodyTagSupport;
import com.skin.ayada.tagext.Tag;

/**
 * <p>Title: MessageTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class MessageTag extends BodyTagSupport
{
    private String key;
    private Object bundle;
    private List<Object> parameters;
    private String var;

    public void setKey(String key)
    {
        this.key = key;
    }

    public void setBundle(Object bundle)
    {
        this.bundle = bundle;
    }

    public void setVar(String var)
    {
        this.var = var;
    }

    public void addParam(Object value)
    {
        if(this.parameters == null)
        {
            this.parameters = new ArrayList<Object>();
        }
        this.parameters.add(value);
    }

    @Override
    public int doEndTag() throws Exception
    {
        Object[] args = null;

        if(this.parameters != null)
        {
            args = this.parameters.toArray(new Object[this.parameters.size()]);
            this.parameters = null;
        }

        String message = null;
        LocalizationContext localizationContext = null;

        if(this.bundle != null)
        {
            if(this.bundle instanceof LocalizationContext)
            {
                localizationContext = (LocalizationContext)(this.bundle);
            }
            else if(this.bundle instanceof String)
            {
                localizationContext = this.getBundle((String)(this.bundle));
            }
        }
        else
        {
            localizationContext = (LocalizationContext)this.pageContext.getAttribute("caucho.bundle");
        }

        message = this.getLocalizedMessage(localizationContext, this.key, args);

        if(this.var != null)
        {
            this.pageContext.setAttribute(this.var, message);
        }
        else
        {
            this.pageContext.getOut().print(message);
        }

        return Tag.EVAL_PAGE;
    }

    /**
     * @param name
     * @return LocalizationContext
     */
    private LocalizationContext getBundle(String name)
    {
        return null;
    }

    /**
     * @param localizationContext
     * @param key
     * @param args
     * @return String
     */
    private String getLocalizedMessage(LocalizationContext localizationContext, String key, Object[] args)
    {
        String result = null;
        ResourceBundle bundle = localizationContext.getResourceBundle();
        Locale locale = localizationContext.getLocale();

        if(bundle != null)
        {
            result = bundle.getString(key);
        }

        if((args == null) || (args.length == 0))
        {
            return result;
        }

        if(locale != null)
        {
            return new MessageFormat(result, locale).format(args);
        }

        return new MessageFormat(result).format(args);
    }
}
