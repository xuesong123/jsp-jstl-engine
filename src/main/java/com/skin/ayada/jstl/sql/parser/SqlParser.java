/*
 * $RCSfile: SqlParser.java,v $
 * $Revision: 1.1 $
 * $Date: 2014-03-25 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.sql.parser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.skin.ayada.database.Sql;
import com.skin.ayada.io.StringStream;

/**
 * <p>Title: SqlParser</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * @author xuesong.net
 * @version 1.0
 */
public class SqlParser {
    /**
     * @param stream
     * @return String
     */
    public static String getToken(StringStream stream) {
        StringBuilder buffer = new StringBuilder();
        skipComment(stream);
        int c = stream.read();

        if(Sql.isSqlIdentifierStart(c)) {
            buffer.append((char)c);
        }
        else {
            stream.back();
            return buffer.toString();
        }

        while((c = stream.read()) != -1) {
            if(Sql.isSqlIdentifierPart(c)) {
                buffer.append((char)c);
            }
            else {
                stream.back();
                break;
            }
        }
        skipComment(stream);
        return buffer.toString();
    }

    /**
     * @param stream
     * @return String
     */
    public static String getWord(StringStream stream) {
        skipComment(stream);

        int q = 32;
        int c = stream.read();
        StringBuilder buffer = new StringBuilder();

        if(c == '`' || c == '\'' || c == '"' || c == '[') {
            q = c;
        }
        else {
            stream.back();
        }

        if(q == '[') {
            q = ']';
        }

        while((c = stream.read()) != -1) {
            if(q == 32 && !Sql.isSqlIdentifierPart(c)) {
                stream.back();
                break;
            }
            else if(c == q) {
                stream.back();
                break;
            }
            else {
                buffer.append((char)c);
            }
        }

        c = stream.peek();
        
        if(q != 32 && c != q) {
            throw new RuntimeException("expect '" + (char)q + "', but found: '" + (char)c + "', ascii: " + c + ", remain: " + stream.getRemain(30));
        }

        if(c == q) {
            stream.read();
        }
        skipComment(stream);
        return buffer.toString().trim();
    }

    /**
     * @param stream
     * @return List<String>
     */
    public static List<String> getArray(StringStream stream) {
        skipComment(stream);
        int i = stream.peek();

        if(i != '(') {
            throw new RuntimeException("expect '(', but found: " + stream.getRemain(30));
        }

        stream.read();
        List<String> list = new ArrayList<String>();

        while(true) {
            String token = getWord(stream);

            if(token.length() < 1) {
                throw new RuntimeException("expect column name, but found: " + stream.getRemain(10));
            }

            list.add(token);

            if(stream.peek() == ')') {
                stream.read();
                break;
            }

            if(stream.peek() == ',') {
                stream.read();
            }
            else {
                throw new RuntimeException("expect ',', but found: " + stream.getRemain(10));
            }
        }
        skipComment(stream);
        return list;
    }

    /**
     * @param text
     * @return String
     */
    public static String unquote(String text) {
        if(text == null) {
            return "";
        }

        String content = text.trim();
        int start = 0;
        int end = content.length();
        char[] quotes = "`\'\"[]".toCharArray();

        for(char quote : quotes) {
            if(start < content.length() && content.charAt(start) == quote) {
                start++;
            }
            if(end > 0 && content.charAt(end - 1) == quote) {
                end--;
            }
        }
        
        if(start > 0 && end < content.length()) {
            return content.substring(start, end);
        }
        else {
            return content;
        }
    }

    /**
     * @param stream
     * @return Integer
     */
    public static Integer getInteger(StringStream stream) {
        String token = getWord(stream);

        if(token.trim().length() < 1) {
            return null;
        }

        try {
            return Integer.parseInt(token);
        }
        catch(NumberFormatException e) {
        }
        return null;
    }

    /**
     * @param stream
     * @return String
     */
    public static String getString(StringStream stream) {
        skipComment(stream);

        if(stream.read() != '\'') {
            throw new RuntimeException("expect keyword '\\''!");
        }

        int i = 0;
        StringBuilder buffer = new StringBuilder();

        while((i = stream.read()) != -1) {
            if(i == '\\') {
                unescape(stream, buffer);
            }
            else if(i == '\'') {
                break;
            }
            else {
                buffer.append((char)i);
            }
        }
        return buffer.toString();
    }

    /**
     * @param stream
     * @return Map<String, String>
     */
    public static Map<String, String> getAttributes(StringStream stream) {
        int i = 0;
        Map<String, String> attributes = new LinkedHashMap<String, String>();
        skipComment(stream);

        while((i = stream.peek()) != StringStream.EOF) {
            if(i == ';') {
                break;
            }

            String name = getToken(stream);

            if(name.length() < 1) {
                break;
            }

            /**
             * syntax:
             * default charset=utf-8
             */
            if(name.equalsIgnoreCase("default")) {
                skipComment(stream);
                name = name + " " + getToken(stream);
            }

            /**
             * syntax:
             * comment '表注释'
             */
            i = stream.peek();

            if(i == '=') {
                stream.read();
            }

            skipComment(stream);
            i = stream.peek();

            if(i == '\'') {
                String value = getString(stream);
                attributes.put(name.toUpperCase(), value);
            }
            else {
                String value = getWord(stream);
                attributes.put(name.toUpperCase(), value);
            }
        }
        return attributes;
    }

    /**
     * @param stream
     * @param buffer
     */
    public static void escape(StringStream stream, StringBuilder buffer) {
        char c = (char)(stream.read());

        if(c != StringStream.EOF) {
            switch(c) {
                case 'n': {
                    buffer.append('\n');
                    break;
                }
                case 't': {
                    buffer.append('\t');
                    break;
                }
                case 'b': {
                    buffer.append('\b');
                    break;
                }
                case 'r': {
                    buffer.append('\r');
                    break;
                }
                case 'f': {
                    buffer.append('\f');
                    break;
                }
                case '\'': {
                    buffer.append('\'');
                    break;
                }
                case '\"': {
                    buffer.append('\"');
                    break;
                }
                case '\\': {
                    buffer.append('\\');
                    break;
                }
                case 'u': {
                    char[] cbuf = new char[4];
                    int i = stream.read(cbuf);

                    if(i == 4) {
                        String hex = new String(cbuf);

                        try {
                            Integer value = Integer.parseInt(hex, 16);
                            buffer.append((char)(value.intValue()));
                        }
                        catch(NumberFormatException e) {
                        }
                    }
                    break;
                }
                default: {
                    stream.back();

                    char[] cbuf = new char[3];
                    int i = stream.read(cbuf);

                    if(i == 3) {
                        String oct = new String(cbuf);

                        try {
                            Integer value = Integer.parseInt(oct, 8);
                            buffer.append((char)(value.intValue()));
                        }
                        catch(NumberFormatException e) {
                        }
                    }
                    break;
                }
            }
        }
    }

    /**
     * @param stream
     * @param buffer
     */
    public static void unescape(StringStream stream, StringBuilder buffer) {
        int c = stream.read();

        if(c < 0) {
            return;
        }

        switch(c) {
            case 'n':{
                buffer.append("\n");
                break;
            }
            case 't': {
                buffer.append("\t");
                break;
            }
            case 'b': {
                buffer.append("\b");
                break;
            }
            case 'r': {
                buffer.append("\r");
                break;
            }
            case 'f': {
                buffer.append("\f");
                break;
            }
            case '\'': {
                buffer.append("\'");
                break;
            }
            case '\"': {
                buffer.append("\"");
                break;
            }
            case '\\': {
                buffer.append("\\");
                break;
            }
            case 'u': {
                char[] cbuf = new char[4];
                int length = stream.read(cbuf);

                if(length == 4) {
                    String hex = new String(cbuf);

                    try {
                        int value = Integer.parseInt(hex, 16);
                        buffer.append((char)value);
                    }
                    catch(NumberFormatException e) {
                    }
                }
                break;
            }
            default: {
                stream.back();
                char[] cbuf = new char[3];
                int length = stream.read(cbuf);

                if(length == 3) {
                    String hex = new String(cbuf);

                    try {
                        int value = Integer.parseInt(hex, 8);
                        buffer.append((char)value);
                    }
                    catch(NumberFormatException e) {
                    }
                }
                break;
            }
        }
    }

    /**
     * @param name
     * @return String
     */
    public static String camel(String name) {
        if(null == name || name.trim().length() < 1) {
            return "";
        }

        String[] subs = name.split("_");
        StringBuilder buffer = new StringBuilder();

        if(name.startsWith("_")) {
            buffer.append("_");
        }

        if(subs.length == 1) {
            String s = subs[0];

            if("ID".equals(s)) {
                buffer.append("Id");
            }
            else if(s.toUpperCase().equals(s)) {
                buffer.append(Character.toUpperCase(s.charAt(0)));
                buffer.append(s.substring(1).toLowerCase());
            }
            else {
                buffer.append(Character.toUpperCase(s.charAt(0))).append(s.substring(1));
            }
        }
        else {
            for(String s : subs) {
                if(s.length() > 0) {
                    if("ID".equals(s)) {
                        buffer.append(s);
                    }
                    else if(s.toUpperCase().equals(s)) {
                        buffer.append(Character.toUpperCase(s.charAt(0)));
                        buffer.append(s.substring(1).toLowerCase());
                    }
                    else {
                        buffer.append(Character.toUpperCase(s.charAt(0))).append(s.substring(1));
                    }
                }
            }
        }

        if(name.endsWith("_")) {
            buffer.append("_");
        }
        return buffer.toString();
    }
    
    /**
     * @param stream
     */
    public static void skipComment(StringStream stream) {
        int i = 0;
        stream.skipWhitespace();

        while((i = stream.read()) != -1) {
            if(i == '/' && stream.peek() == '*') {
                stream.read();
                while((i = stream.read()) != -1) {
                    if(i == '*' && stream.peek() == '/') {
                        stream.read();
                        break;
                    }
                }
            }
            else if(i == '-' && stream.peek() == '-') {
                stream.read();
                while((i = stream.read()) != -1) {
                    if(i == '\n') {
                        break;
                    }
                }
            }
            else if(i > ' ') {
                stream.back();
                break;
            }
        }
        stream.skipWhitespace();
    }
}
