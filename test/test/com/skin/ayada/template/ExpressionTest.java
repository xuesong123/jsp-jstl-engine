/*
 * $RCSfile: ExpressionTest.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-15 $
 *
 * Copyright (C) 2008 WanMei, Inc. All rights reserved.
 *
 * This software is the proprietary information of WanMei, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.template;

import com.skin.ayada.util.ExpressionUtil;

/**
 * <p>Title: ExpressionTest</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class ExpressionTest
{
    public static void main(String[] args)
    {
        /*
        float floatValue = 2.0f;
        double doubleValue = 2.0d;
        long longValue = 2L;
        double doubleValue2 = 1E3;
        */
        String[] source = {"true", "false", "ae2", "+1", "-1", "1", "1.0", "1.0f", "1.0F", "1.0d", "1.0D", "1.0L", "1L", "-1e3", "-1.2e3"};

        for(int i = 0; i < source.length; i++)
        {
            int type = ExpressionUtil.getDataType(source[i]);
            Object value = ExpressionUtil.getValue(source[i]);

            switch(type)
            {
                case 0:
                {
                    System.out.println("String: " + source[i] + " - " + value.getClass().getName() + " - " + value);
                    break;
                }
                case 1:
                {
                    System.out.println("Boolean: " + source[i] + " - " + value.getClass().getName() + " - " + value);
                    break;
                }
                case 2:
                {
                    System.out.println("Integer: " + source[i] + " - " + value.getClass().getName() + " - " + value);
                    break;
                }
                case 3:
                {
                    System.out.println("Float: " + source[i] + " - " + value.getClass().getName() + " - " + value);
                    break;
                }
                case 4:
                {
                    System.out.println("Double: " + source[i] + " - " + value.getClass().getName() + " - " + value);
                    break;
                }
                case 5:
                {
                    System.out.println("Long: " + source[i] + " - " + value.getClass().getName() + " - " + value);
                    break;
                }
                default:
                {
                    System.out.println("String: " + source[i] + " - " + value.getClass().getName() + " - " + value);
                    break;
                }
            }
        }
    }
}
