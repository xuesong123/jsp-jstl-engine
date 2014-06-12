/*
 * $RCSfile: BBCode.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-2-17 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Title: BBCode</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class BBCode
{
    private static Logger logger = LoggerFactory.getLogger(BBCode.class);
    private static final boolean DEBUG = logger.isDebugEnabled();
    public static final Map<String, String> map = BBCode.load();
    public static final Map<String, String> sig = new HashMap<String, String>();

    static
    {
        sig.put("img",   "1");
        sig.put("audio", "1");
        sig.put("attach", "1");
        sig.put("attachimg", "1");
        sig.put("flash", "1");
    }

    /**
     * @param source
     * @return String
     */
    public static String decode(String source)
    {
        if(source == null)
        {
            return "";
        }

        int begin = 0;
        int index = 0;
        int length = source.length();
        StringBuilder buffer = new StringBuilder();

        while(begin < length)
        {
            index = source.indexOf('[', begin);

            if(index > -1)
            {
                if(index > begin)
                {
                    buffer.append(HtmlUtil.encode(source.substring(begin, index)));
                }

                int k = source.indexOf(']', index + 1);

                if(k > -1)
                {
                    String name = null;
                    String attributes = null;
                    String content = source.substring(index + 1, k);
                    int m = content.indexOf('=');

                    if(m > -1)
                    {
                        name = content.substring(0, m);
                        attributes = content.substring(m + 1);
                    }
                    else
                    {
                        name = content;
                    }

                    if(sig.get(name) != null)
                    {
                        m = source.indexOf("[/" + name + "]", k + 1);

                        if(m > -1)
                        {
                            String value = source.substring(k + 1, m);
                            String html = getHtml(name, attributes, value);

                            if(DEBUG)
                            {
                                logger.debug("[name: " + name + ", attributes: " + attributes + ", value: " + value + "]: " + html);
                            }

                            buffer.append(html);
                            begin = m + name.length() + 3;
                        }
                        else
                        {
                            if(DEBUG)
                            {
                                logger.debug("[name: " + name + "] --- ERROR ---");
                            }

                            begin = k + 1;
                        }
                    }
                    else
                    {
                        if(DEBUG)
                        {
                            logger.debug("[name: " + name + ", attributes: " + attributes + ", value: #null#]: " + getHtml(name, attributes, null));
                        }

                        buffer.append(getHtml(name, attributes, null));
                        begin = k + 1;
                    }
                }
                else
                {
                    buffer.append(HtmlUtil.encode(source.substring(index)));
                    break;
                }
            }
            else
            {
                buffer.append(HtmlUtil.encode(source.substring(begin, length)));
                break;
            }
        }

        return buffer.toString();
    }

    /**
     * @param name
     * @param value
     * @return String
     */
    public static String getHtml(String name, String attributes, String value)
    {
        String html = map.get(name);

        if(html != null)
        {
            if(value != null)
            {
                String temp = value.trim();

                if(temp.length() >= 10 && temp.toLowerCase().startsWith("javascript"))
                {
                    temp = "";
                }

                html = replace(html, "${value}", HtmlUtil.encode(temp));
            }
            else
            {
                html = replace(html, "${value}", "");
            }

            String[] array = null;

            if(attributes != null)
            {
                array = unquote(attributes.trim()).split(",");
            }
            else
            {
                array = new String[0];
            }

            for(int i = 0; i < array.length; i++)
            {
                String attr = array[i];

                if(attr.length() >= 10 && attr.toLowerCase().startsWith("javascript"))
                {
                    array[i] = "";
                }
                else
                {
                    array[i] = HtmlUtil.encode(attr);
                }
            }

            return evaluate(html, array);
        }

        return "";
    }

    /**
     * @param source
     * @param search
     * @param replacement
     * @return String
     */
    public static String evaluate(String source, String[] replacement)
    {
        char c;
        StringBuilder name = new StringBuilder();
        StringBuilder buffer = new StringBuilder();

        for(int i = 0, length = source.length(); i < length; i++)
        {
            c = source.charAt(i);

            if(c == '$' && i < length - 1 && source.charAt(i + 1) == '{')
            {
                int j = i + 2;

                for(; j < length; j++)
                {
                    c = source.charAt(j);

                    if(c == '}')
                    {
                        String value = replace(name.toString(), replacement);
                        buffer.append(value);
                        name.setLength(0);
                        break;
                    }
                    name.append(c);
                }
                i = j;
            }
            else
            {
                buffer.append(c);
            }
        }

        return buffer.toString();
    }

    /**
     * @param source
     * @param replacement
     * @return String
     */
    public static String replace(String source, String[] replacement)
    {
        int s = 0;
        int e = source.indexOf("#", s);
        int length = source.length();

        if(e > -1)
        {
            for(e = e + 1, s = e; s < length; s++)
            {
                if(Character.isDigit(source.charAt(s)) == false)
                {
                    break;
                }
            }

            if(s > e)
            {
                int index = -1;
                String value = source.substring(e, s);

                try
                {
                    index = Integer.parseInt(value);

                    if(index > 0 && index <= replacement.length)
                    {
                        return source.substring(0, e - 1) + replacement[index - 1] + source.substring(s);
                    }
                }
                catch(NumberFormatException t)
                {
                }
            }
        }

        return "";
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
            return "";
        }

        if(search == null)
        {
            return source;
        }

        int s = 0;
        int e = 0;
        int d = search.length();
        StringBuilder buffer = new StringBuilder();

        do
        {
            e = source.indexOf(search, s);

            if(e == -1)
            {
                buffer.append(source.substring(s));
                break;
            }
            buffer.append(source.substring(s, e)).append(replacement);
            s = e + d;
        }
        while(true);
        return buffer.toString();
    }

    /***
     * @param source
     * @return String
     */
    public static String remove(String source)
    {
        if(source == null)
        {
            return "";
        }

        int begin = 0;
        int index = 0;
        int length = source.length();
        StringBuilder buffer = new StringBuilder();

        while(begin < length)
        {
            index = source.indexOf('[', begin);

            if(index > -1)
            {
                int k = source.indexOf(']', index + 1);
                buffer.append(source.substring(begin, index));

                if(k > -1)
                {
                    begin = k + 1;
                }
                else
                {
                    buffer.append(source.substring(index));
                    break;
                }
            }
            else
            {
                buffer.append(source.substring(begin, length));
                break;
            }
        }

        return HtmlUtil.encode(buffer.toString());
    }

    /**
     * @param source
     * @return String
     */
    public static String unquote(String source)
    {
        if(source == null)
        {
            return "";
        }

        int start = 0;
        int end = source.length();

        char c;
        for(int i = 0; i < end; i++)
        {
            c = source.charAt(i);

            if(c == '\"' || c == '\'')
            {
                start = i + 1;
            }
            else
            {
                break;
            }
        }

        for(int i = end - 1; i > -1; i--)
        {
            c = source.charAt(i);

            if(c == '\"' || c == '\'')
            {
                end = i;
            }
            else
            {
                break;
            }
        }

        if(end < 0)
        {
            end = 0;
        }

        if(start > 0 && end < source.length())
        {
            if(start < end)
            {
                return source.substring(start, end);
            }
            return "";
        }
        return source;
    }

    /**
     * @return Map<String, String>
     */
    private static Map<String, String> load()
    {
        ClassLoader classLoader = BBCode.class.getClassLoader();
        Map<String, String> map1 = load(classLoader, "ayada-bbcode-default.properties");
        Map<String, String> map2 = load(classLoader, "ayada-bbcode.properties");
        map1.putAll(map2);
        return map1;
    }

    /**
     * @param classLoader
     * @param resource
     * @return Map<String, String>
     */
    private static Map<String, String> load(ClassLoader classLoader, String resource)
    {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        Map<String, String> map = new LinkedHashMap<String, String>();

        try
        {
            inputStream = classLoader.getResourceAsStream(resource);

            if(inputStream == null)
            {
                return map;
            }

            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);

            int k = 0;
            String key = null;
            String line = null;
            String value = null;
            while((line = bufferedReader.readLine()) != null)
            {
                line = line.trim();

                if(line.length() < 1)
                {
                    continue;
                }

                if(line.startsWith("#"))
                {
                    continue;
                }

                k = line.indexOf(" ");

                if(k > -1)
                {
                    key = line.substring(0, k).trim();
                    value = line.substring(k + 1).trim();

                    if(key.length() > 0 && value.length() > 0)
                    {
                        map.put(key, value);
                    }
                }
            }
        }
        catch(IOException e)
        {
        }
        finally
        {
            if(inputStream != null)
            {
                try
                {
                    inputStream.close();
                }
                catch(IOException e)
                {
                }
            }
        }

        return map;
    }
}
