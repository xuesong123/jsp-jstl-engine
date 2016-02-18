/*
 * $RCSfile: ExpressionUtil.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-10 $
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
public class ExpressionUtil {
    /**
     * @param expressionContext
     * @param source
     * @return Object
     */
    public static String getHtml(ExpressionContext expressionContext, String source) {
        return HtmlUtil.encode(getString(expressionContext, source));
    }

    /**
     * @param expressionContext
     * @param source
     * @return Object
     */
    public static String getString(ExpressionContext expressionContext, String source) {
        Object value = ExpressionUtil.evaluate(expressionContext, source);

        if(value == null || value instanceof Empty<?, ?>) {
            return "";
        }
        return value.toString();
    }

    /**
     * @param expressionContext
     * @param source
     * @return Object
     */
    public static boolean getBoolean(ExpressionContext expressionContext, String source) {
        Object value = ExpressionUtil.evaluate(expressionContext, source);

        if(value == null) {
            return false;
        }

        if(value instanceof Boolean) {
            return Boolean.TRUE.equals(value);
        }
        return (value.toString().equals("true"));
    }

    /**
     * @param expressionContext
     * @param source
     * @return Byte
     */
    public static Byte getByte(ExpressionContext expressionContext, String source) {
        Object value = ExpressionUtil.evaluate(expressionContext, source);

        if(value != null && value instanceof Number) {
            return ((Number)value).byteValue();
        }
        return (Byte)(ClassUtil.cast(value, Byte.class));
    }

    /**
     * @param expressionContext
     * @param source
     * @return Short
     */
    public static Short getShort(ExpressionContext expressionContext, String source) {
        Object value = ExpressionUtil.evaluate(expressionContext, source);

        if(value != null && value instanceof Number) {
            return ((Number)value).shortValue();
        }
        return (Short)(ClassUtil.cast(value, Short.class));
    }

    /**
     * @param expressionContext
     * @param source
     * @return Integer
     */
    public static Integer getInteger(ExpressionContext expressionContext, String source) {
        Object value = ExpressionUtil.evaluate(expressionContext, source);

        if(value != null && value instanceof Number) {
            return ((Number)value).intValue();
        }
        return (Integer)(ClassUtil.cast(value, Integer.class));
    }

    /**
     * @param expressionContext
     * @param source
     * @return Float
     */
    public static Float getFloat(ExpressionContext expressionContext, String source) {
        Object value = ExpressionUtil.evaluate(expressionContext, source);

        if(value != null && value instanceof Number) {
            return ((Number)value).floatValue();
        }
        return (Float)(ClassUtil.cast(value, Float.class));
    }

    /**
     * @param expressionContext
     * @param source
     * @return Double
     */
    public static Double getDouble(ExpressionContext expressionContext, String source) {
        Object value = ExpressionUtil.evaluate(expressionContext, source);

        if(value != null && value instanceof Number) {
            return ((Number)value).doubleValue();
        }
        return (Double)(ClassUtil.cast(value, Double.class));
    }

    /**
     * @param expressionContext
     * @param source
     * @return Long
     */
    public static Long getLong(ExpressionContext expressionContext, String source) {
        Object value = ExpressionUtil.evaluate(expressionContext, source);

        if(value != null && value instanceof Number) {
            return ((Number)value).longValue();
        }
        return (Long)(ClassUtil.cast(value, Long.class));
    }

    /**
     * @param expressionContext
     * @param expresion
     * @param resultType
     * @return Object
     */
    public static Object evaluate(ExpressionContext expressionContext, String expresion, Class<?> resultType) {
        Object value = evaluate(expressionContext, expresion);

        if(resultType != String.class && value instanceof String) {
            value = getValue((String)value);
        }

        if(resultType != null) {
            return ClassUtil.cast(value, resultType);
        }
        return value;
    }

    /**
     * @param expressionContext
     * @param expresion
     * @return Object
     */
    private static Object evaluate(ExpressionContext expressionContext, String expresion) {
        if(expresion == null) {
            return null;
        }

        List<Node> list = parse(expresion);

        if(list.size() > 0) {
            if(list.size() == 1) {
                Node node = list.get(0);

                if(node instanceof Expression) {
                    return expressionContext.getValue(node.getTextContent());
                }
                return node.getTextContent();
            }
            Object value = null;
            StringBuilder buffer = new StringBuilder();

            for(Node node : list) {
                if(node instanceof Expression) {
                    value = expressionContext.getValue(node.getTextContent());

                    if(value != null) {
                        buffer.append(value.toString());
                    }
                }
                else {
                    buffer.append(node.getTextContent());
                }
            }
            return buffer.toString();
        }
        return null;
    }

    /**
     * @param source
     * @return List<Node>
     */
    public static List<Node> parse(String source) {
        char c;
        char[] cbuf = source.toCharArray();
        TextNode textNode = null;
        List<Node> list = new ArrayList<Node>();

        for(int i = 0, length = cbuf.length; i < length; i++) {
            c = cbuf[i];

            if(c == '$' && (i + 1) < length && cbuf[i + 1] == '{') {
                Expression expression = new Expression();

                for(i = i + 2; i < length; i++) {
                    if(cbuf[i] == '}') {
                        break;
                    }
                    expression.append(cbuf[i]);
                }

                String content = expression.trim();

                if(content.length() > 0) {
                    if(content.startsWith("?")) {
                        if(textNode == null) {
                            textNode = new TextNode();
                            list.add(textNode);
                        }

                        textNode.append("${");
                        textNode.append(content.substring(1));
                        textNode.append("}");
                    }
                    else {
                        list.add(expression);
                        textNode = null;
                    }
                }
            }
            else {
                if(textNode == null) {
                    textNode = new TextNode();
                    list.add(textNode);
                }

                textNode.append(c);
            }
        }
        return list;
    }

    /**
     * @param source
     * @return Object
     */
    public static Object getValue(String source) {
        String temp = source.trim();
        Object value = source;

        if(temp.length() < 1) {
            return value;
        }

        int type = getDataType(source);

        switch(type) {
            case 0: {
                break;
            }
            case 1: {
                try {
                    value = Boolean.parseBoolean(temp);
                }
                catch(NumberFormatException e) {
                }
                break;
            }
            case 2: {
                try {
                    if(temp.charAt(0) == '+') {
                        value = Integer.parseInt(temp.substring(1));
                    }
                    else {
                        value = Integer.parseInt(temp);
                    }
                }
                catch(NumberFormatException e) {
                }
                break;
            }
            case 3: {
                try {
                    value = Float.parseFloat(temp);
                }
                catch(NumberFormatException e) {
                }
                break;
            }
            case 4: {
                try {
                    value = Double.parseDouble(temp);
                }
                catch(NumberFormatException e) {
                }
                break;
            }
            case 5: {
                try {
                    if(temp.endsWith("l") || temp.endsWith("L")) {
                        value = Long.parseLong(temp.substring(0, temp.length() - 1));
                    }
                    else {
                        value = Long.parseLong(temp);
                    }
                }
                catch(NumberFormatException e) {
                }
                break;
            }
            default: {
                break;
            }
        }
        return value;
    }

    /**
     * 0 - String
     * 1 - Boolean
     * 2 - Integer
     * 3 - Float
     * 4 - Double
     * 5 - Long
     * @param content
     * @return int
     */
    public static int getDataType(String content) {
        String source = content.trim();

        if(source.length() < 1) {
            return 0;
        }

        if(source.equals("true") || source.equals("false")) {
            return 1;
        }

        char c;
        int d = 0;
        int type = 2;
        int length = source.length();

        for(int i = 0; i < length; i++) {
            c = source.charAt(i);

            if(i == 0 && (c == '+' || c == '-')) {
                continue;
            }

            if(c == '.') {
                if(d == 0) {
                    d = 4;
                    continue;
                }
                return 0;
            }

            if(c < 48 || c > 57) {
                if(i == length - 1) {
                    if(c == 'f' || c == 'F') {
                        return 3;
                    }
                    else if(c == 'd' || c == 'D') {
                        return 4;
                    }
                    else if(c == 'l' || c == 'L') {
                        return (d == 0 ? 5 : 0);
                    }
                    else {
                        return 0;
                    }
                }

                if(i == length - 2 && (c == 'e' || c == 'E') && Character.isDigit(source.charAt(length - 1))) {
                    return 4;
                }
                return 0;
            }
        }
        return (d == 0 ? type : d);
    }
}
