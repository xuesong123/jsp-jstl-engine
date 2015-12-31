/*
 * $RCSfile: NumberFormatTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-28 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.fmt;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import com.skin.ayada.jstl.core.SetTag;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TagSupport;

/**
 * <p>Title: NumberFormatTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class NumberFormatTag extends TagSupport {
    private Double value;
    private String type;
    private String pattern;
    private String currencyCode;
    private String currencySymbol;
    private boolean groupingUsed = true;
    private int maxIntegerDigits = -1;
    private int minIntegerDigits = -1;
    private int maxFractionDigits = -1;
    private int minFractionDigits = -1;
    private String var;
    private String scope;

    @Override
    public int doEndTag() {
        NumberFormat format = getFormat();

        if((this.value != null) && (Double.isNaN(this.value.doubleValue()))) {
            this.value = Double.valueOf(0.0D);
        }

        String result = null;

        if(this.value != null) {
            if(format != null) {
                result = format.format(this.value);
            }
            else {
                result = String.valueOf(this.value);
            }
        }

        if(this.var == null) {
            if(result != null) {
                try {
                    this.pageContext.getOut().print(result);
                }
                catch(IOException e) {
                }
            }
        }
        else {
            SetTag.setValue(this.pageContext, this.var, this.scope, result);
        }

        return Tag.EVAL_PAGE;
    }

    protected NumberFormat getFormat() {
        NumberFormat format = null;
        Locale locale = (Locale)(this.pageContext.getAttribute("Locale"));

        if((this.type == null) || (this.type.equals("")) || (this.type.equals("number")) || ((this.pattern != null) && (!"".equals(this.pattern)))) {
            if(locale != null) {
                format = NumberFormat.getInstance(locale);
            }
            else {
                format = NumberFormat.getInstance();
            }

            DecimalFormat decimalFormat = (DecimalFormat)format;
            if(this.pattern != null) {
                decimalFormat.applyPattern(this.pattern);
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
                format = NumberFormat.getCurrencyInstance();
            }

            if(((this.currencyCode != null) || (this.currencySymbol != null)) && ((format instanceof DecimalFormat))) {
                DecimalFormat decimalFormat = (DecimalFormat)format;
                DecimalFormatSymbols decimalFormatSymbols = decimalFormat.getDecimalFormatSymbols();
                if((this.currencyCode != null) && (decimalFormatSymbols != null)) {
                    decimalFormatSymbols.setInternationalCurrencySymbol(this.currencyCode);
                }
                else if((this.currencySymbol != null) && (decimalFormatSymbols != null)) {
                    decimalFormatSymbols.setCurrencySymbol(this.currencySymbol);
                }

                decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
            }
        }
        else {
            throw new RuntimeException("unknown formatNumber type " + this.type);
        }

        format.setGroupingUsed(this.groupingUsed);

        if(this.minIntegerDigits > 0) {
            format.setMinimumIntegerDigits(this.minIntegerDigits);
        }
        if(this.maxIntegerDigits > 0) {
            format.setMaximumIntegerDigits(this.maxIntegerDigits);
        }
        if(this.minFractionDigits > 0) {
            format.setMinimumFractionDigits(this.minFractionDigits);
        }
        if(this.maxFractionDigits > 0) {
            format.setMaximumFractionDigits(this.maxFractionDigits);
        }

        return format;
    }

    /**
     * @return the value
     */
    public Double getValue() {
        return this.value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Double value) {
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
     * @return the currencyCode
     */
    public String getCurrencyCode() {
        return this.currencyCode;
    }

    /**
     * @param currencyCode the currencyCode to set
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    /**
     * @return the currencySymbol
     */
    public String getCurrencySymbol() {
        return this.currencySymbol;
    }

    /**
     * @param currencySymbol the currencySymbol to set
     */
    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    /**
     * @return the groupingUsed
     */
    public boolean isGroupingUsed() {
        return this.groupingUsed;
    }

    /**
     * @param groupingUsed the groupingUsed to set
     */
    public void setGroupingUsed(boolean groupingUsed) {
        this.groupingUsed = groupingUsed;
    }

    /**
     * @return the maxIntegerDigits
     */
    public int getMaxIntegerDigits() {
        return this.maxIntegerDigits;
    }

    /**
     * @param maxIntegerDigits the maxIntegerDigits to set
     */
    public void setMaxIntegerDigits(int maxIntegerDigits) {
        this.maxIntegerDigits = maxIntegerDigits;
    }

    /**
     * @return the minIntegerDigits
     */
    public int getMinIntegerDigits() {
        return this.minIntegerDigits;
    }

    /**
     * @param minIntegerDigits the minIntegerDigits to set
     */
    public void setMinIntegerDigits(int minIntegerDigits) {
        this.minIntegerDigits = minIntegerDigits;
    }

    /**
     * @return the maxFractionDigits
     */
    public int getMaxFractionDigits() {
        return this.maxFractionDigits;
    }

    /**
     * @param maxFractionDigits the maxFractionDigits to set
     */
    public void setMaxFractionDigits(int maxFractionDigits) {
        this.maxFractionDigits = maxFractionDigits;
    }

    /**
     * @return the minFractionDigits
     */
    public int getMinFractionDigits() {
        return this.minFractionDigits;
    }

    /**
     * @param minFractionDigits the minFractionDigits to set
     */
    public void setMinFractionDigits(int minFractionDigits) {
        this.minFractionDigits = minFractionDigits;
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
