/*
 * $RCSfile: TagUtil.java,v $$
 * $Revision: 1.1  $
 * $Date: 2013-2-19  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.skin.ayada.jstl.TagLibrary;
import com.skin.ayada.runtime.ExpressionContext;
import com.skin.ayada.runtime.PageContext;
import com.skin.ayada.statement.Expression;
import com.skin.ayada.statement.Node;
import com.skin.ayada.statement.TextNode;
import com.skin.ayada.tagext.Tag;

/**
 * <p>Title: TemplateExecutor</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class TagUtil
{
    private TagUtil()
    {
    }

    /**
     * @param className
     * @return Tag
     * @throws Exception
     */
    public static Tag create(String className)
    {
        try
        {
            return (Tag)(ClassUtil.getInstance(className, Tag.class));
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param tagName
     * @return Tag
     * @throws Exception
     */
    public static Tag create(PageContext pageContext, String tagName)
    {
        TagLibrary tagLibrary = pageContext.getTagLibrary();
        String className = tagLibrary.getTagClassName(tagName);

        try
        {
            return (Tag)(ClassUtil.getInstance(className, Tag.class));
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param tag
     * @param attributes
     * @param expressionContext
     */
    public static void setAttributes(Tag tag, Map<String, String> attributes, ExpressionContext expressionContext)
    {
        if(attributes == null || attributes.size() < 1)
        {
            return;
        }

        for(Map.Entry<String, String> entry : attributes.entrySet())
        {
            String name = entry.getKey();
            String value = entry.getValue();
            Class<?> type = tag.getClass();

            try
            {
                name = "set" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
                Method method = getSetMethod(type, name);

                if(method != null)
                {
                    Object arg = evaluate(expressionContext, value);
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    Class<?> parameterType = parameterTypes[0];

                    if(arg != null)
                    {
                        arg = ClassUtil.cast(arg, parameterType);
                    }

                    try
                    {
                        method.invoke(tag, new Object[]{arg});
                    }
                    catch(Exception e)
                    {
                        String className = (arg != null ? arg.getClass().getName() : "null");
                        throw new RuntimeException("method: " + method + " - " + parameterType.getName() + " - " + className, e);
                    }
                }
                else
                {
                    throw new RuntimeException("NoSuchMethodException: " + tag.getClass().getName() + "." + name);
                }
            }
            catch(SecurityException e)
            {
                throw new RuntimeException(e);
            }
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
                    return expressionContext.evaluate(node.toString());
                }
                else
                {
                    Object value = null;
                    String result = node.toString();
                    int flag = getNumberType(result);

                    if(flag == 0)
                    {
                        return result;
                    }
                    else
                    {
                        if(flag == 1)
                        {
                            try
                            {
                                value = Integer.valueOf(result);
                            }
                            catch(NumberFormatException e)
                            {
                            }
                        }
                        else if(flag == 2)
                        {
                            try
                            {
                                value = Double.valueOf(result);
                            }
                            catch(NumberFormatException e)
                            {
                            }
                        }
                    }

                    return (value != null ? value : result);
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
                        value = expressionContext.evaluate(node.toString());

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
     * @param type
     * @param methodName
     * @return Method
     */
    public static Method getSetMethod(Class<?> type, String methodName)
    {
        Method[] methods = type.getMethods();

        for(Method method : methods)
        {
            if(method.getModifiers() != Modifier.PUBLIC)
            {
                continue;
            }

            if(method.getName().equals(methodName))
            {
                if(method.getParameterTypes().length == 1)
                {
                    return method;
                }
            }
        }

        return null;
    }

    /**
     * @param source
     * @return int
     */
    public static int getNumberType(String source)
    {
        char c;
        int d = 0;
        int type = 1;

        for(int i = 0, length = source.length(); i < length; i++)
        {
            c = source.charAt(i);

            if(i > 0 && c == '.')
            {
                if(d == 0)
                {
                    d = 2;
                    continue;
                }
                else
                {
                    return 0;
                }
            }

            if(c < 48 || c > 57)
            {
                return 0;
            }
        }

        return (d == 0 ? type : d);
    }
}
