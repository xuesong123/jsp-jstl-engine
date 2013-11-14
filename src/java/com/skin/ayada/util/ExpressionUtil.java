/*
 * $RCSfile: ExpressionUtil.java,v $$
 * $Revision: 1.1  $
 * $Date: 2013-11-10  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.util;

import java.util.ArrayList;
import java.util.List;

import com.skin.ayada.ognl.util.Empty;
import com.skin.ayada.runtime.ExpressionContext;
import com.skin.ayada.statement.Expression;
import com.skin.ayada.statement.Node;
import com.skin.ayada.statement.TextNode;

/**
 * <p>Title: ExpressionUtil</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class ExpressionUtil
{
    /**
     * @param expressionContext
     * @param source
     * @return Object
     */
    public static String getHtml(ExpressionContext expressionContext, String source)
    {
        return HtmlUtil.encode(getString(expressionContext, source));
    }

    /**
     * @param expressionContext
     * @param source
     * @return Object
     */
    public static String getString(ExpressionContext expressionContext, String source)
    {
        Object value = ExpressionUtil.evaluate(expressionContext, source);

        if(value == null || value instanceof Empty<?, ?>)
        {
            return "";
        }
        else
        {
            return value.toString();
        }
    }

    /**
     * @param expressionContext
     * @param source
     * @return Object
     */
    public static boolean getBoolean(ExpressionContext expressionContext, String source)
    {
        Object value = ExpressionUtil.evaluate(expressionContext, source);

        if(value == null)
        {
            return false;
        }

        if(value instanceof Boolean)
        {
            return Boolean.TRUE.equals(value);
        }
        else
        {
            return false;
        }
    }

    /**
     * @param expressionContext
     * @param source
     * @return Byte
     */
    public static Byte getByte(ExpressionContext expressionContext, String source)
    {
        Object value = ExpressionUtil.evaluate(expressionContext, source);

        if(value != null && value instanceof Number)
        {
            return ((Number)value).byteValue();
        }
        else
        {
            return null;
        }
    }

    /**
     * @param expressionContext
     * @param source
     * @return Short
     */
    public static Short getShort(ExpressionContext expressionContext, String source)
    {
        Object value = ExpressionUtil.evaluate(expressionContext, source);

        if(value != null && value instanceof Number)
        {
            return ((Number)value).shortValue();
        }
        else
        {
            return null;
        }
    }

    /**
     * @param expressionContext
     * @param source
     * @return Integer
     */
    public static Integer getInteger(ExpressionContext expressionContext, String source)
    {
        Object value = ExpressionUtil.evaluate(expressionContext, source);

        if(value != null && value instanceof Number)
        {
            return ((Number)value).intValue();
        }
        else
        {
            return null;
        }
    }

    /**
     * @param expressionContext
     * @param source
     * @return Float
     */
    public static Float getFloat(ExpressionContext expressionContext, String source)
    {
        Object value = ExpressionUtil.evaluate(expressionContext, source);

        if(value != null && value instanceof Number)
        {
            return ((Number)value).floatValue();
        }
        else
        {
            return null;
        }
    }

    /**
     * @param expressionContext
     * @param source
     * @return Double
     */
    public static Double getDouble(ExpressionContext expressionContext, String source)
    {
        Object value = ExpressionUtil.evaluate(expressionContext, source);

        if(value != null && value instanceof Number)
        {
            return ((Number)value).doubleValue();
        }
        else
        {
            return null;
        }
    }

    /**
     * @param expressionContext
     * @param source
     * @return Long
     */
    public static Long getLong(ExpressionContext expressionContext, String source)
    {
        Object value = ExpressionUtil.evaluate(expressionContext, source);

        if(value != null && value instanceof Number)
        {
            return ((Number)value).longValue();
        }
        else
        {
            return null;
        }
    }

    /**
     * @param source
     * @return String
     */
    public static Object evaluate(ExpressionContext expressionContext, String source)
    {
        if(source == null)
        {
            return null;
        }

        char c;
        char[] cbuf = source.toCharArray();
        List<Node> list = new ArrayList<Node>();

        TextNode textNode = null;

        for(int i = 0, length = cbuf.length; i < length; i++)
        {
            c = cbuf[i];

            if(c == '$' && (i + 1) < length && cbuf[i + 1] == '{')
            {
                Expression expression = new Expression();

                for(i = i + 2; i < length; i++)
                {
                    if(cbuf[i] == '}')
                    {
                        break;
                    }
                    else
                    {
                        expression.append(cbuf[i]);
                    }
                }

                if(expression.toString().length() > 0)
                {
                    list.add(expression);
                    textNode = null;
                }
            }
            else
            {
                if(textNode == null)
                {
                    textNode = new TextNode();
                    list.add(textNode);
                }

                textNode.append(c);
            }
        }

        if(list.size() > 0)
        {
            if(list.size() == 1)
            {
                Node node = list.get(0);

                if(node instanceof Expression)
                {
                    return expressionContext.getValue(node.toString());
                }
                else
                {
                    Object value = getValue(node.toString());
                    return (value != null ? value : node.toString());
                }
            }
            else
            {
                Object value = null;
                StringBuilder buffer = new StringBuilder();

                for(Node node : list)
                {
                    if(node instanceof Expression)
                    {
                        value = expressionContext.getValue(node.toString());

                        if(value != null)
                        {
                            buffer.append(value.toString());
                        }
                    }
                    else
                    {
                        buffer.append(node.toString());
                    }
                }

                return buffer.toString();
            }
        }

        return null;
    }

    /**
     * @param source
     * @return Object
     */
    public static Object getValue(String source)
    {
        Object value = source;
        int type = getNumberType(source);

        switch(type)
        {
            case 0:
            {
                break;
            }
            case 1:
            {
                try
                {
                    String temp = source.trim();

                    if(temp.length() > 0)
                    {
                        if(temp.charAt(0) == '+')
                        {
                            value = Integer.parseInt(temp.substring(1));
                        }
                        else
                        {
                            value = Integer.parseInt(temp);
                        }
                    }
                }
                catch(NumberFormatException e)
                {
                    e.printStackTrace();
                }

                break;
            }
            case 2:
            {
                try
                {
                    value = Float.parseFloat(source);
                }
                catch(NumberFormatException e)
                {
                }
                break;
            }
            case 3:
            {
                try
                {
                    value = Double.parseDouble(source);
                }
                catch(NumberFormatException e)
                {
                }
                break;
            }
            case 4:
            {
                try
                {
                    String temp = source.trim();

                    if(temp.length() > 0)
                    {
                        if(temp.endsWith("l") || temp.endsWith("L"))
                        {
                            value = Long.parseLong(temp.substring(0, temp.length() - 1));
                        }
                        else
                        {
                            value = Long.parseLong(temp);
                        }
                    }
                }
                catch(NumberFormatException e)
                {
                }
                break;
            }
            default:
            {
                break;
            }
        }

        return value;
    }

    /**
     * 0 - String
     * 1 - Integer
     * 2 - Float
     * 3 - Double
     * 4 - Long
     * @param source
     * @return int
     */
    public static int getNumberType(String content)
    {
        String source = content.trim();

        if(source.length() < 1)
        {
            return 0;
        }

        char c;
        int d = 0;
        int type = 1;
        int length = source.length();

        for(int i = 0; i < length; i++)
        {
            c = source.charAt(i);

            if(i == 0 && (c == '+' || c == '-'))
            {
                continue;
            }

            if(c == '.')
            {
                if(d == 0)
                {
                    d = 3;
                    continue;
                }
                else
                {
                    return 0;
                }
            }

            if(c < 48 || c > 57)
            {
                if(i == length - 1)
                {
                    if(c == 'f' || c == 'F')
                    {
                        return 2;
                    }
                    else if(c == 'd' || c == 'D')
                    {
                        return 3;
                    }
                    else if(c == 'l' || c == 'L')
                    {
                        return (d == 0 ? 4 : 0);
                    }
                    else
                    {
                        return 0;
                    }
                }

                if(i == length - 2 && (c == 'e' || c == 'E') && Character.isDigit(source.charAt(length - 1)))
                {
                    return 3;
                }

                return 0;
            }
        }

        return (d == 0 ? type : d);
    }

    public static void main(String[] args)
    {
        /*
        float floatValue = 2.0f;
        double doubleValue = 2.0d;
        long longValue = 2L;
        double doubleValue2 = 1E3;
        */
        String[] source = {"ae2", "+1", "-1", "1", "1.0", "1.0f", "1.0F", "1.0d", "1.0D", "1.0L", "1L", "-1e3", "-1.2e3"};

        for(int i = 0; i < source.length; i++)
        {
            int type = getNumberType(source[i]);
            Object value = getValue(source[i]);

            switch(type)
            {
                case 0:
                {
                    System.out.println("String: " + source[i] + " - " + value.getClass().getName() + " - " + value);
                    break;
                }
                case 1:
                {
                    System.out.println("Integer: " + source[i] + " - " + value.getClass().getName() + " - " + value);
                    break;
                }
                case 2:
                {
                    System.out.println("Float: " + source[i] + " - " + value.getClass().getName() + " - " + value);
                    break;
                }
                case 3:
                {
                    System.out.println("Double: " + source[i] + " - " + value.getClass().getName() + " - " + value);
                    break;
                }
                case 4:
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
