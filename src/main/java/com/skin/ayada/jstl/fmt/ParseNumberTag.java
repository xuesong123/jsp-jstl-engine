/*
 * $RCSfile: ParseNumberTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-10-30 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.fmt;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import com.skin.ayada.jstl.core.OutTag;
import com.skin.ayada.jstl.core.SetTag;
import com.skin.ayada.tagext.BodyTagSupport;
import com.skin.ayada.tagext.Tag;

/**
 * <p>Title: ParseNumberTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class ParseNumberTag extends BodyTagSupport {
    private String var;
    private String value;
    private String type;
    private String pattern;
    private boolean integerOnly = false;
    private String scope;

    @Override
    public int doEndTag() throws Exception {
        String string = null;
        NumberFormat format = this.getFormat();

        if(this.value != null) {
            string = this.value;
        }
        else {
            if(this.bodyContent != null) {
                string = this.bodyContent.getString().trim();
            }
        }

        Number value = null;

        if((string != null) && string.length() > 0) {
            value = format.parse(string);
        }

        if(this.var == null) {
            if(this.scope != null) {
                throw new Exception("fmt:parseNumber var must not be null when scope '" + this.scope + "' is set.");
            }

            OutTag.print(this.pageContext, this.value, false);
        }
        else {
            SetTag.setValue(this.pageContext, this.var, this.scope, value);
        }

        return Tag.EVAL_PAGE;
    }

    /**
     * @return NumberFormat
     * @throws JspException
     */
    protected NumberFormat getFormat() throws Exception {
        NumberFormat format = null;
        Locale locale = this.pageContext.getLocale();

        if((this.type == null) || (this.type.equals("")) || (this.type.equals("number"))) {
            if(locale != null) {
                format = NumberFormat.getInstance(locale);
            }
            else {
                format = NumberFormat.getInstance();
            }
        }
        else if(this.type.equals("percent")) {
            if(locale != null) {
                format = NumberFormat.getPercentInstance(locale);
            }
            else {
                format = NumberFormat.getPercentInstance();
            }
        }
        else if(this.type.equals("currency")) {
            if(locale != null) {
                format = NumberFormat.getCurrencyInstance(locale);
            }
            else {
                format = NumberFormat.getCurrencyInstance(locale);
            }
        }
        else {
            throw new Exception("unknown formatNumber type: " + this.type);
        }

        if(this.pattern != null) {
            DecimalFormat decimalFormat = (DecimalFormat)format;
            decimalFormat.applyPattern(this.pattern);
        }

        format.setParseIntegerOnly(this.integerOnly);
        return format;
    }

    /**
    * @return the value
    */
    public String getValue() {
        return this.value;
    }

    /**
    * @param value the value to set
    */
    public void setValue(String value) {
        this.value = value;
    }

    /**
    * @return the type
    */
    public String getType() {
        return this.type;
    }

    /**
    * @param type the type to set
    */
    public void setType(String type) {
        this.type = type;
    }

    /**
    * @return the pattern
    */
    public String getPattern() {
        return this.pattern;
    }

    /**
    * @param pattern the pattern to set
    */
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    /**
    * @return the var
    */
    public String getVar() {
        return this.var;
    }

    /**
    * @param var the var to set
    */
    public void setVar(String var) {
        this.var = var;
    }

    /**
    * @param integerOnly the integerOnly to set
    */
    public void setIntegerOnly(boolean integerOnly) {
        this.integerOnly = integerOnly;
    }

    /**
    * @return the integerOnly
    */
    public boolean getIntegerOnly() {
        return this.integerOnly;
    }

    /**
    * @return the scope
    */
    public String getScope() {
        return this.scope;
    }

    /**
    * @param scope the scope to set
    */
    public void setScope(String scope) {
        this.scope = scope;
    }
}
