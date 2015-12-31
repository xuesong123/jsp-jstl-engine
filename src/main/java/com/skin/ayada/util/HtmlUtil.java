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

        int s = 0;
        int e = 0;
        int k = 0;
        StringBuilder buffer = new StringBuilder();

        do {
            e = source.indexOf('<', s);

            if(e < 0) {
                buffer.append(HtmlUtil.encode(source.substring(s)));
                break;
            }
            k = source.indexOf('>', e + 1);

            if(k > -1) {
                buffer.append(HtmlUtil.encode(source.substring(s, e)));
                s = k + 1;
            }
            else {
                buffer.append(HtmlUtil.encode(source.substring(s)));
                break;
            }
        }
        while(true);

        return buffer.toString();
    }
}