/*
 * $RCSfile: NumberFormatTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-2-28 $
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

import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TagSupport;

/**
 * <p>Title: NumberFormatTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class NumberFormatTag extends TagSupport
{
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

    @Override
    public int doEndTag()
    {
        NumberFormat format = getFormat();

        if((this.value != null) && (Double.isNaN(this.value.doubleValue())))
        {
            this.value = Double.valueOf(0.0D);
        }

        String result = null;

        if(this.value != null)
        {
            if(format != null)
            {
                result = format.format(this.value);
            }
            else
            {
                result = String.valueOf(this.value);
            }
        }

        if(this.var == null)
        {
            if(result != null)
            {
                try
                {
                    this.pageContext.getOut().print(result);
                }
                catch(IOException e)
                {
                }
            }
        }
        else
        {
            this.pageContext.setAttribute(this.var, result);
        }

        return Tag.EVAL_PAGE;
    }

    protected NumberFormat getFormat()
    {
        NumberFormat format = null;
        Locale locale = (Locale)(this.pageContext.getAttribute("Locale"));

        if((this.type == null) || (this.type.equals("")) || (this.type.equals("number")) || ((this.pattern != null) && (!"".equals(this.pattern))))
        {
            if(locale != null)
            {
                format = NumberFormat.getInstance(locale);
            }
            else
            {
                format = NumberFormat.getInstance();
            }

            DecimalFormat decimalFormat = (DecimalFormat)format;
            if(this.pattern != null)
            {
                decimalFormat.applyPattern(this.pattern);
            }
        }
        else if(this.type.equals("percent"))
        {
            if(locale != null)
            {
                format = NumberFormat.getPercentInstance(locale);
            }
            else
            {
                format = NumberFormat.getPercentInstance();
            }
        }
        else if(this.type.equals("currency"))
        {
            if(locale != null)
            {
                format = NumberFormat.getCurrencyInstance(locale);
            }
            else
            {
                format = NumberFormat.getCurrencyInstance();
            }

            if(((this.currencyCode != null) || (this.currencySymbol != null)) && ((format instanceof DecimalFormat)))
            {
                DecimalFormat decimalFormat = (DecimalFormat)format;
                DecimalFormatSymbols decimalFormatSymbols = decimalFormat.getDecimalFormatSymbols();
                if((this.currencyCode != null) && (decimalFormatSymbols != null))
                {
                    decimalFormatSymbols.setInternationalCurrencySymbol(this.currencyCode);
                }
                else if((this.currencySymbol != null) && (decimalFormatSymbols != null))
                {
                    decimalFormatSymbols.setCurrencySymbol(this.currencySymbol);
                }

                decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
            }
        }
        else
        {
            throw new RuntimeException("unknown formatNumber type " + this.type);
        }

        format.setGroupingUsed(this.groupingUsed);

        if(this.minIntegerDigits > 0)
        {
            format.setMinimumIntegerDigits(this.minIntegerDigits);
        }
        if(this.maxIntegerDigits > 0)
        {
            format.setMaximumIntegerDigits(this.maxIntegerDigits);
        }
        if(this.minFractionDigits > 0)
        {
            format.setMinimumFractionDigits(this.minFractionDigits);
        }
        if(this.maxFractionDigits > 0)
        {
            format.setMaximumFractionDigits(this.maxFractionDigits);
        }

        return format;
    }
}
