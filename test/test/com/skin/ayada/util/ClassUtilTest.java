/*
 * $RCSfile: ClassUtilTest.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-26 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.util;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import test.com.skin.ayada.taglib.TestTag;

import com.skin.ayada.jstl.util.BeanUtil;
import com.skin.ayada.util.ClassUtil;

/**
 * <p>Title: ClassUtilTest</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class ClassUtilTest
{
    public static void main(String[] args)
    {
        TestTag tag = new TestTag();
        Map<String, Object> properties = new HashMap<String, Object>();

        properties.put("myBoolean", "true");
        properties.put("myChar", "ccc");
        properties.put("myByte", "1");
        properties.put("myInt", "-1.0");
        properties.put("myFloat", "1.2f");
        properties.put("myDouble", "2.3d");
        properties.put("myLong", "1.00L");
        properties.put("myString", "Hello");

        try
        {
            ClassUtil.setProperties(tag, properties);
            tag.print(new PrintWriter(System.out));

            BeanUtil beanUtil = new BeanUtil();
            System.out.println(beanUtil.toString(tag));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
