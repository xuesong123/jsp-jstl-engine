/*
 * $RCSfile: DateFormatTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-26 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.fmt;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TagSupport;

/**
 * <p>Title: DateFormatTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class DateFormatTag extends TagSupport {
    private String var;
    private Date value;
    private TimeZone timeZone;
    private String pattern;
    private String dateStyle;
    private String timeStyle;
    private String type;

    /**
     * @return int
     * @throws Exception
     */
    @Override
    public int doEndTag() throws Exception {
        if(this.value == null) {
            if(this.var != null) {
                this.pageContext.setAttribute(this.var, null);
            }
            return Tag.EVAL_PAGE;
        }

        long time = this.value.getTime();

        DateFormat format = null;
        Locale locale = this.getLocale();

        int dateStyle = 2;
        int timeStyle = 2;

        if(this.dateStyle != null) {
            dateStyle = this.getDateStyle(this.dateStyle);
        }

        if(this.timeStyle != null) {
            timeStyle = getDateStyle(this.timeStyle);
        }

        if(locale != null) {
            if((this.type == null) || (this.type.equals("date"))) {
                format = DateFormat.getDateInstance(dateStyle, locale);
            }
            else if(this.type.equals("both")) {
                format = DateFormat.getDateTimeInstance(dateStyle, timeStyle, locale);
            }
            else if(this.type.equals("time")) {
                format = DateFormat.getTimeInstance(timeStyle, locale);
            }
            else {
                throw new RuntimeException("illegal type " + this.type);
            }
        }
        else if (this.type == null || this.type.equals("date")) {
            format = DateFormat.getDateInstance(dateStyle);
        }
        else if (this.type.equals("both")) {
            format = DateFormat.getDateTimeInstance(dateStyle, timeStyle);
        }
        else if (this.type.equals("time")) {
            format = DateFormat.getTimeInstance(timeStyle);
        }
        else {
            throw new RuntimeException("illegal type " + this.type);
        }

        if((format != null) && (this.pattern != null)) {
            try {
                ((SimpleDateFormat)format).applyPattern(this.pattern);
            }
            catch(ClassCastException e) {
                format = new SimpleDateFormat(this.pattern, locale);
            }
        }

        if(format != null) {
            TimeZone timeZone = this.getTimeZone(this.timeZone);

            if(timeZone == null) {
                timeZone = this.pageContext.getTimeZone();
            }

            if(timeZone != null) {
                format.setTimeZone(timeZone);
            }
        }

        Object value = this.value;

        if(format != null) {
            value = format.format(new Date(time));
        }

        if(this.var == null) {
            try {
                this.pageContext.getOut().print(value);
            }
            catch(IOException e) {
            }
        }
        else {
            this.pageContext.setAttribute(this.var, value);
        }
        return Tag.EVAL_PAGE;
    }

    /**
     * @return Locale
     */
    private Locale getLocale() {
        return Locale.getDefault();
    }

    /**
     * @param value
     * @return TimeZone
     */
    private TimeZone getTimeZone(Object value) {
        if ((value instanceof TimeZone)) {
            return (TimeZone)value;
        }

        if((value instanceof String)) {
            return TimeZone.getTimeZone((String)value);
        }
        return null;
    }

    /**
     * @param style
     * @return int
     */
    private int getDateStyle(String style) {
        if((style == null) || (style.equals("default"))) {
            return 2;
        }
        if(style.equals("short")) {
            return 3;
        }
        if(style.equals("medium")) {
            return 2;
        }
        if(style.equals("long")) {
            return 1;
        }
        if(style.equals("full")) {
            return 0;
        }

        throw new RuntimeException("illegal date style " + style);
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
     * @return the value
     */
    public Date getValue() {
        return this.value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Object value) {
        if((value instanceof Number)) {
          this.value = new Date(((Number)value).longValue());
        }
        else {
            this.value = ((Date)value);
        }
    }

    /**
     * @return the timeZone
     */
    public TimeZone getTimeZone() {
        return this.timeZone;
    }

    /**
     * @param timeZone the timeZone to set
     */
    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
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
     * @return the dateStyle
     */
    public String getDateStyle() {
        return this.dateStyle;
    }

    /**
     * @param dateStyle the dateStyle to set
     */
    public void setDateStyle(String dateStyle) {
        this.dateStyle = dateStyle;
    }

    /**
     * @return the timeStyle
     */
    public String getTimeStyle() {
        return this.timeStyle;
    }

    /**
     * @param timeStyle the timeStyle to set
     */
    public void setTimeStyle(String timeStyle) {
        this.timeStyle = timeStyle;
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
}
