/*
 * $RCSfile: StringUtil.java,v $$
 * $Revision: 1.1  $
 * $Date: 2013-3-2  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.util;

/**
 * <p>Title: StringUtil</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class StringUtil
{
    private static final String EMPTY = "";

    private StringUtil()
    {
    }

    /**
     * @param source
     * @param length
     * @param padding
     * @return String
     */
    public static String substring(String source, int length, String padding)
    {
        if(source == null)
        {
            return "";
        }

        String s = source.trim();

        char c;
        int size = 0;
        int count = s.length();
        StringBuilder buffer = new StringBuilder();

        for(int i = 0; i < s.length(); i++)
        {
            c = s.charAt(i);

            if(c >= 0x0080)
            {
                size += 2;
                count++;
            }
            else
            {
                size++;
            }

            if(size > length)
            {
                if(c >= 0x4e00)
                {
                    size -= 2;
                }
                else
                {
                    size--;
                }

                break;
            }

            buffer.append(c);
        }

        if(size < count && padding != null)
        {
            buffer.append(padding);
        }

        return buffer.toString();
    }

    /**
     * @param source
     * @param search
     * @param replacement
     * @return String
     */
    public static String replace(String source, String search, String replacement)
    {
        if(source == null)
        {
            return EMPTY;
        }

        if(search == null)
        {
            return source;
        }

        int s = 0;
        int e = 0;
        int d = search.length();
        StringBuilder buffer = new StringBuilder();

        while(true)
        {
            while(true)
            {
                e = source.indexOf(search, s);

                if(e == -1)
                {
                    buffer.append(source.substring(s));
                    break;
                }
                else
                {
                    buffer.append(source.substring(s, e)).append(replacement);
                    s = e + d;
                }
            }

            String result = buffer.toString();
            e = result.indexOf(search, 0);

            if(e > -1)
            {
                s = 0;
                source = result;
                buffer.setLength(0);
            }
            else
            {
                break;
            }
        }

        return buffer.toString();
    }
}
