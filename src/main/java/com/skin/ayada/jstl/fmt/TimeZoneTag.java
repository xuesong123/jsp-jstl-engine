/*
 * $RCSfile: TimeZoneTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-28 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.fmt;

import java.util.TimeZone;

import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TagSupport;
import com.skin.ayada.tagext.TryCatchFinally;

/**
 * <p>Title: TimeZoneTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class TimeZoneTag extends TagSupport implements TryCatchFinally
{
    private String var;
    private TimeZone oldTimeZone;
    private Object value;

    @Override
    public int doStartTag() throws Exception
    {
        TimeZone timeZone = null;
        this.oldTimeZone = this.pageContext.getTimeZone();

        if((this.value instanceof TimeZone))
        {
            timeZone = (TimeZone)(this.value);
        }
        else if((this.value instanceof String))
        {
            String string = ((String)(this.value)).trim();

            if (string.length() < 1)
            {
                timeZone = TimeZone.getTimeZone("GMT");
            }
            else
            {
                timeZone = TimeZone.getTimeZone(string);
            }
        }
        else
        {
            timeZone = TimeZone.getTimeZone("GMT");
        }

        this.pageContext.setTimeZone(timeZone);
        return Tag.EVAL_BODY_INCLUDE; 
    }

    @Override
    public void doCatch(Throwable throwable) throws java.lang.Throwable
    {
        throw throwable;
    }

    @Override
    public void doFinally()
    {
        if(this.oldTimeZone == null)
        {
            this.pageContext.setTimeZone(null);
        }
        else
        {
            this.pageContext.setTimeZone(this.oldTimeZone);
        }
    }

    /**
     * @param var the var to set
     */
    public void setVar(String var)
    {
        this.var = var;
    }

    /**
     * @return the var
     */
    public String getVar()
    {
        return this.var;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Object value)
    {
        this.value = value;
    }

    /**
     * @return the value
     */
    public Object getValue()
    {
        return this.value;
    }
}
