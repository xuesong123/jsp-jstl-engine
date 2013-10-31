/*
 * $RCSfile: DateUtil.java,v $$
 * $Revision: 1.1  $
 * $Date: 2013-2-16  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>Title: DateUtil</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class DateUtil
{
    /**
     * @param date
     * @param pattern
     * @return Date
     */
    public static Date parse(String date, String pattern)
    {
        if(date == null)
        {
            return null;
        }

        DateFormat dateFormat = new SimpleDateFormat(pattern);

        try
        {
            return dateFormat.parse(date);
        }
        catch(ParseException e)
        {
        }

        return null;
    }

    /**
     * @param date
     * @param pattern
     * @return String
     */
    public static String format(Date date, String pattern)
    {
        if(date == null)
        {
            return "";
        }

        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    /**
     * @param date
     * @return int
     */
    public static int year(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * @param date
     * @return int
     */
    public static int month(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    /**
     * @param date
     * @return int
     */
    public static int day(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * @param date
     * @return String
     */
    public static String smart(Date date)
    {
        return DateUtil.smart(new Date(System.currentTimeMillis()), date);
    }

    /**
     * @param offsetDate
     * @param date
     * @return String
     */
    public static String smart(Date offsetDate, Date date)
    {
        if(date == null)
        {
            return "";
        }

        long offsetTimeMillis = offsetDate.getTime();
        long timeMillis = Math.abs(offsetTimeMillis - date.getTime());
        long hour = 60L * 60L * 1000L;
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String result = null;

        if(timeMillis < 60L * 1000L)
        {
            result = "刚刚";
        }
        else if(timeMillis < hour)
        {
            result = (timeMillis / (60L * 1000L)) + " 分钟以前";
        }
        else if(timeMillis < 24 * hour)
        {
            result = (timeMillis / (60L * 60L * 1000L)) + " 小时以前";
        }
        else if(timeMillis < 30 * 24 * hour)
        {
            long days = Math.abs(offsetTimeMillis / 86400000L - date.getTime() / 86400000L);

            if(days < 2)
            {
                result = "昨天  " + dateFormat.format(date);
            }
            else if(days < 3)
            {
                result = "前天  " + dateFormat.format(date);
            }
            else
            {
                result = days + " 天以前";
            }
        }
        else
        {
            Calendar c1 = Calendar.getInstance();
            c1.setTimeInMillis(offsetTimeMillis);
            Calendar c2 = Calendar.getInstance();
            c2.setTime(date);

            int y1 = c1.get(Calendar.YEAR);
            int y2 = c2.get(Calendar.YEAR);
            int m1 = c1.get(Calendar.MONTH);
            int m2 = c2.get(Calendar.MONTH);
            int d1 = (y1 * 12 + m1);
            int d2 = (y2 * 12 + m2);

            if((d1 - d2) < 12)
            {
                result = (d1 - d2) + " 月以前";
            }
            else
            {
                result = (y1 - y2) + " 年以前";
            }
        }

        return result;
    }
}
