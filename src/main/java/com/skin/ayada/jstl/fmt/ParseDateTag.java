/*
 * $RCSfile: DateFormatTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2014-04-11 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.fmt;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import com.skin.ayada.tagext.BodyTagSupport;
import com.skin.ayada.tagext.Tag;

/**
 * <p>Title: DateFormatTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class ParseDateTag extends BodyTagSupport {
    private String var;
    private String value;
    private String type;
    private String pattern;
    private String dateStyle;
    private String timeStyle;
    private String timeZone;
    private String scope;


    @Override
    public int doEndTag() throws Exception {
        String string = null;

        if(this.value != null) {
            string = this.value;
        }
        else if(this.bodyContent != null) {
            string = this.bodyContent.getString();

            if(string != null) {
                string = string.trim();
            }
        }

        Object value;
        DateFormat format = this.getFormat();

        if((string == null) || ("".equals(string))) {
            value = null;
        }
        else {
            value = format.parse(string);
        }

        if(this.var == null) {
            if(value != null) {
                this.pageContext.getOut().print(value);
            }
        }
        else {
            this.pageContext.setAttribute(this.var, value);
        }
        return Tag.EVAL_PAGE;
    }

    /**
     * @return DateFormat
     * @throws Exception
     */
    protected DateFormat getFormat() throws Exception {
        DateFormat format = null;
        Locale locale = this.pageContext.getLocale();

        int dateStyle = DateFormat.MEDIUM;

        if(this.dateStyle != null) {
            dateStyle = getDateStyle(this.dateStyle);
        }

        int timeStyle = DateFormat.MEDIUM;

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
                throw new Exception("illegal type: " + this.type);
            }
        }
        else if((this.type == null) || (this.type.equals("date"))) {
            format = DateFormat.getDateInstance(dateStyle);
        }
        else if (this.type.equals("both")) {
            format = DateFormat.getDateTimeInstance(dateStyle, timeStyle);
        }
        else if (this.type.equals("time")) {
            format = DateFormat.getTimeInstance(timeStyle);
        }
        else {
            throw new Exception("illegal type: " + this.type);
        }

        if(format == null) {
            return null;
        }

        if(this.pattern != null) {
            try {
                ((SimpleDateFormat)format).applyPattern(this.pattern);
            }
            catch(ClassCastException e) {
                format = new SimpleDateFormat(this.pattern, locale);
            }
        }

        TimeZone timeZone = getTimeZone(this.timeZone);

        if(timeZone == null) {
            timeZone = this.pageContext.getTimeZone();
        }

        if(timeZone != null) {
            format.setTimeZone(timeZone);
        }
        return format;
    }

    /**
     * @param value
     * @return TimeZone
     */
    private TimeZone getTimeZone(Object value) {
        if((value instanceof TimeZone)) {
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
     * @throws Exception
     */
    private static int getDateStyle(String style) throws Exception {
        if((style == null) || (style.equals("default"))) {
            return DateFormat.MEDIUM;
        }
        if(style.equals("short")) {
            return DateFormat.SHORT;
        }
        if(style.equals("medium")) {
            return DateFormat.MEDIUM;
        }
        if(style.equals("long")) {
            return DateFormat.LONG;
        }
        if(style.equals("full")) {
            return DateFormat.FULL;
        }
        throw new Exception("illegal date style: " + style);
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
     * @return the timeZone
     */
    public String getTimeZone() {
        return this.timeZone;
    }

    /**
     * @param timeZone the timeZone to set
     */
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
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
