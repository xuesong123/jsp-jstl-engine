/*
 * $RCSfile: HtmlStreamTest.java.java,v $$
 * $Revision: 1.1  $
 * $Date: 2013-2-23  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.html;

import com.skin.ayada.util.Stack;


/**
 * <p>Title: HtmlStreamTest</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class HtmlStreamTest
{
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        test1();
    }

    public static void test1()
    {
        Stack<String> stack = new Stack<String>();

        for(int i = 0; i < 10; i++)
        {
            stack.push(String.valueOf(i));
        }

        for(int i = 0; i < 5; i++)
        {
            stack.pop();
        }

        for(int i = 'A'; i < 'F'; i++)
        {
            stack.push(String.valueOf((char)i));
        }

        int i = 0;

        while(stack.peek(i) != null)
        {
            System.out.println(stack.peek(i));
            i--;
        }

        stack.println();
    }
}
