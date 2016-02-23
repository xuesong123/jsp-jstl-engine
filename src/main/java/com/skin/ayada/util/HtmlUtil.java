/*
 * $RCSfile: HtmlUtil.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-17 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.util;

/**
 * <p>Title: HtmlUtil</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class HtmlUtil {
    /**
     * @param source
     * @return String
     */
    public static String encode(String source) {
        if(source == null) {
            return "";
        }

        StringBuilder buffer = new StringBuilder();

        for(int i = 0, length = source.length(); i < length; i++) {
            char c = source.charAt(i);

            switch(c) {
                case '"':
                    buffer.append("&quot;");
                    break;
                case '<':
                    buffer.append("&lt;");
                    break;
                case '>':
                    buffer.append("&gt;");
                    break;
                case '&':
                    buffer.append("&amp;");
                    break;
                case '\'':
                    buffer.append("&#39;");
                    break;
                default:
                    buffer.append(c);
                    break;
            }
        }
        return buffer.toString();
    }

    /**
     * @param source
     * @return String
     */
    public static String decode(String source) {
        if(source == null) {
            return "";
        }

        int length = source.length();
        char[] c = source.toCharArray();
        StringBuilder buffer = new StringBuilder(length);

        for(int i = 0; i < length; i++) {
            if(c[i] == '&') {
                if(((i + 3) < length) && (c[i + 1] == 'l') && (c[i + 2] == 't') && (c[i + 3] == ';')) {
                    // &lt;
                    buffer.append('<');
                    i += 3;
                }
                else if(((i + 3) < length) && (c[i + 1] == 'g') && (c[i + 2] == 't') && (c[i + 3] == ';')) {
                    // &gt;
                    buffer.append('>');
                    i += 3;
                }
                else if (((i + 4) < length) && (c[i + 1] == 'a') && (c[i + 2] == 'm') && (c[i + 3] == 'p') && (c[i + 4] == ';')) {
                    // &amp;
                    buffer.append('&');
                    i += 4;
                }
                else if(((i + 5) < length) && (c[i + 1] == 'q') && (c[i + 2] == 'u') && (c[i + 3] == 'o') && (c[i + 4] == 't') && (c[i + 5] == ';') ) {
                    // &quot;
                    buffer.append('"');
                    i += 5;
                }
                else if(((i + 3) < length && (c[i + 1] == '#') && Character.isDigit(c[i + 2]))) {
                    // &#10;
                    for(int j = i + 2; j < length; j++) {
                        if(Character.isDigit(c[j])) {
                            continue;
                        }
                        if(c[j] != ';') {
                            buffer.append('&');
                        }
                        else {
                            try {
                                int charCode = Integer.parseInt(new String(c, i + 2, j - i - 2));
                                buffer.append((char)charCode);
                            }
                            catch(NumberFormatException e) {
                            }

                            i = j;
                        }

                        break;
                    }
                }
                else {
                    buffer.append('&');
                }
            }
            else {
                buffer.append(c[i]);
            }
        }
        return buffer.toString();
    }

    /**
     * @param source
     * @return String
     */
    public static String remove(String source) {
        if(source == null) {
            return "";
        }

        char c;
        String nodeName = null;
        StringBuilder temp = new StringBuilder();
        StringBuilder buffer = new StringBuilder();

        for(int i = 0, length = source.length(); i < length; i++) {
            c = source.charAt(i);

            if(c == '<') {
                int j = i + 1;

                for(; j < length; j++) {
                    c = source.charAt(j);

                    if(Character.isWhitespace(c) || Character.isISOControl(c) || c == '/' || c == '>') {
                        break;
                    }
                    else {
                        temp.append(Character.toLowerCase(c));
                    }
                }

                j = source.indexOf('>', j);

                if(j < 0) {
                    break;
                }

                nodeName = temp.toString();
                temp.setLength(0);

                if(nodeName.equals("script") || nodeName.equals("style")) {
                    for(j++; j < length; j++) {
                        c = source.charAt(j);

                        if(c == '<' && j + 1 < length && source.charAt(j + 1) == '/') {
                            for(j += 2; j < length; j++) {
                                c = source.charAt(j);

                                if(Character.isWhitespace(c) || Character.isISOControl(c) || c == '/' || c == '>') {
                                    break;
                                }
                                else {
                                    temp.append(Character.toLowerCase(c));
                                }
                            }

                            nodeName = temp.toString();
                            temp.setLength(0);

                            if(nodeName.equals("script") || nodeName.equals("style")) {
                                j = source.indexOf('>', j);

                                if(j > -1) {
                                    j++;
                                }

                                break;
                            }
                            else {
                                j += 2;
                            }
                        }
                    }

                    if(j < 0) {
                        break;
                    }
                }
                i = j;
            }
            else {
                buffer.append(c);
            }
        }
        return buffer.toString();
    }
}