/*
 * $RCSfile: DateUtilTest.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-05-15 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.util;

import java.util.Date;

import com.skin.ayada.util.DateUtil;

/**
 * <p>Title: DateUtilTest</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class DateUtilTest
{
    public static void main(String[] args)
    {
        Date offsetDate = DateUtil.parse("2013-06-25 18:24:11", "yyyy-MM-dd HH:mm:ss");
        Date date = DateUtil.parse("2013-06-24 18:04:11", "yyyy-MM-dd HH:mm:ss");
        String result = DateUtil.smart(offsetDate, date);
        System.out.println(result);
        test2();
    }

    public static void test2()
    {
        Date offsetDate = DateUtil.parse("2013-06-26 11:50:11", "yyyy-MM-dd HH:mm:ss");
        Date date = DateUtil.parse("2013-06-24 17:16:16", "yyyy-MM-dd HH:mm:ss");
        String result = DateUtil.smart(offsetDate, date);
        System.out.println(result);
    }
}
