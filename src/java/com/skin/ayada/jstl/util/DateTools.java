/*
 * $RCSfile: DateTools.java,v $$
 * $Revision: 1.1  $
 * $Date: 2013-2-16  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.util;

import java.util.Date;

import com.skin.ayada.util.DateUtil;

/**
 * <p>Title: DateTools</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class DateTools
{
    /**
     * @param date
     * @param pattern
     * @return Date
     */
    public Date parse(String date, String pattern)
    {
        return DateUtil.parse(date, pattern);
    }

    /**
     * @param date
     * @param pattern
     * @return String
     */
    public String format(long timeMillis, String pattern)
    {
        return DateUtil.format(new Date(timeMillis), pattern);
    }

    /**
     * @param date
     * @param pattern
     * @return String
     */
    public String format(Date date, String pattern)
    {
        return DateUtil.format(date, pattern);
    }
    
    /**
     * @param date
     * @return int
     */
    public int year(Date date)
    {
        return DateUtil.year(date);
    }
    
    /**
     * @param date
     * @return int
     */
    public int month(Date date)
    {
        return DateUtil.month(date);
    }
    
    /**
     * @param date
     * @return int
     */
    public int day(Date date)
    {
        return DateUtil.day(date);
    }

    /**
     * @param date
     * @return String
     */
    public String smart(Date date)
    {
        return DateUtil.smart(date);
    }
}
