/*
 * $RCSfile: StringUtil.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-03-02 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.util;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: StringUtil</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class StringUtil {
    private static final String EMPTY = "";

    private StringUtil() {
    }

    /**
     * @param source
     * @param length
     * @param padding
     * @return String
     */
    public static String substring(String source, int length, String padding) {
        if(source == null) {
            return "";
        }

        String s = source.trim();

        char c;
        int size = 0;
        int count = s.length();
        StringBuilder buffer = new StringBuilder();

        for(int i = 0; i < s.length(); i++) {
            c = s.charAt(i);

            if(c >= 0x0080) {
                size += 2;
                count++;
            }
            else {
                size++;
            }

            if(size > length) {
                if(c >= 0x4e00) {
                    size -= 2;
                }
                else {
                    size--;
                }

                break;
            }

            buffer.append(c);
        }

        if(size < count && padding != null) {
            buffer.append(padding);
        }
        return buffer.toString();
    }

    /**
     * @param source
     * @param length
     * @param pad
     * @return String
     */
    public static String padding(String source, int length, String pad) {
        StringBuilder buffer = new StringBuilder(source);

        while(buffer.length() < length) {
            buffer.append(pad);
        }

        if(buffer.length() > length) {
            return buffer.substring(0, length);
        }
        return buffer.toString();
    }

    /**
     * @param source
     * @param search
     * @param replacement
     * @return String
     */
    public static String replace(String source, String search, String replacement) {
        if(source == null) {
            return EMPTY;
        }

        if(search == null) {
            return source;
        }

        int s = 0;
        int e = 0;
        int d = search.length();
        String content = source;
        StringBuilder buffer = new StringBuilder();

        while(true) {
            while(true) {
                e = content.indexOf(search, s);

                if(e == -1) {
                    buffer.append(content.substring(s));
                    break;
                }
                buffer.append(content.substring(s, e)).append(replacement);
                s = e + d;
            }

            content = buffer.toString();
            e = content.indexOf(search, 0);

            if(e > -1) {
                s = 0;
                buffer.setLength(0);
            }
            else {
                break;
            }
        }
        return content;
    }

    /**
     * @param source
     * @param limit
     * @param trim
     * @param ignoreWhitespace
     * @return String[]
     */
    public static String[] split(String source, String limit, boolean trim, boolean ignoreWhitespace) {
        int i = 0;
        int j = 0;
        String s = null;
        List<String> list = new ArrayList<String>();

        while((j = source.indexOf(limit, i)) > -1) {
            if(j > i) {
                s = source.substring(i, j);

                if(trim) {
                    s = s.trim();
                }

                if(!ignoreWhitespace || s.length() > 0) {
                    list.add(s);
                }
            }
            i = j + limit.length();
        }

        if(i < source.length()) {
            s = source.substring(i);

            if(trim) {
                s = s.trim();
            }

            if(!ignoreWhitespace || s.length() > 0) {
                list.add(s);
            }
        }
        String[] result = new String[list.size()];
        return list.toArray(result);
    }

    /**
     * @param source
     * @return String
     */
    public static String escape(String source) {
        if(source == null) {
            return "";
        }

        char c;
        StringBuilder buffer = new StringBuilder();

        for(int i = 0, length = source.length(); i < length; i++) {
            c = source.charAt(i);

            switch (c) {
                case '\'': {
                    buffer.append("\\\'");break;
                }
                case '"': {
                    buffer.append("\\\"");break;
                }
                case '\r': {
                    buffer.append("\\r");break;
                }
                case '\n': {
                    buffer.append("\\n");break;
                }
                case '\t': {
                    buffer.append("\\t");break;
                }
                case '\b': {
                    buffer.append("\\b");break;
                }
                case '\f': {
                    buffer.append("\\f");break;
                }
                case '\\': {
                    buffer.append("\\\\");break;
                }
                default : {
                    buffer.append(c);break;
                }
            }
        }
        return buffer.toString();
    }

    /**
     * @param source
     * @return String
     */
    public static String unescape(String source) {
        if(source == null) {
            return "";
        }

        char c;
        StringBuilder buffer = new StringBuilder();

        for(int i = 0, length = source.length(); i < length; i++) {
            c = source.charAt(i);

            if(c == '\\' && (i + 1 < length)) {
                switch (source.charAt(i + 1)) {
                    case '\'': {
                        buffer.append("\'");break;
                    }
                    case '"': {
                        buffer.append("\"");break;
                    }
                    case 'r': {
                        buffer.append("\r");break;
                    }
                    case 'n': {
                        buffer.append("\n");break;
                    }
                    case 't': {
                        buffer.append("\t");break;
                    }
                    case 'b': {
                        buffer.append("\b");break;
                    }
                    case 'f': {
                        buffer.append("\f");break;
                    }
                    case '\\': {
                        buffer.append("\\");break;
                    }
                    default : {
                        buffer.append('\\');
                        buffer.append(source.charAt(i + 1));
                        break;
                    }
                }
                i++;
            }
            else {
                buffer.append(c);
            }
        }
        return buffer.toString();
    }

    /**
     * @param source
     * @return String
     */
    public static String compact(String source) {
        return compact(source, "\r\n");
    }

    /**
     * @param source
     * @param crlf
     * @return String
     */
    public static String compact(String source, String crlf) {
        char c;
        boolean b = true;
        int length = source.length();
        StringBuilder buffer = new StringBuilder();

        for(int i = 0; i < length; i++) {
            c = source.charAt(i);

            if(c == '\n') {
                if(b) {
                    buffer.append(crlf);
                    b = false;
                }
            }
            else if(c == '\r') {
                continue;
            }
            else {
                buffer.append(c);
                b = true;
            }
        }
        return buffer.toString();
    }

    /**
     * @param content
     * @param value
     * @return boolean
     */
    public static boolean contains(String content, String value) {
        if(content != null) {
            if(content.trim().equals("*")) {
                return true;
            }

            String[] array = content.split(",");

            for(int i = 0; i < array.length; i++) {
                array[i] = array[i].trim();

                if(array[i].equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }
}
