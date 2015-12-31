/*
 * $RCSfile: PageCompiler.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-03-02 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.compile;

import java.util.LinkedHashMap;
import java.util.Map;

import com.skin.ayada.io.StringStream;

/**
 * <p>Title: PageCompiler</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class PageCompiler {
    protected int lineNumber = 1;
    protected StringStream stream;

    /**
     */
    public PageCompiler() {
    }

    /**
     * read node name, after read '<'
     * @return String
     */
    protected String getNodeName() {
        int i;
        char c;
        StringBuilder buffer = new StringBuilder();
        while((i = this.stream.read()) != -1) {
            c = (char)i;

            if(Character.isLetter(c) || Character.isDigit(c) || c == ':' || c == '-' || c == '_') {
                buffer.append(c);
            }
            else {
                this.stream.back();
                break;
            }
        }

        return buffer.toString();
    }

    /**
     * read node name, after read nodeName
     * @return String
     */
    public Map<String, String> getAttributes() {
        return this.getAttributes(this.stream);
    }

    /**
     * read node name, after read nodeName
     * @return String
     */
    public Map<String, String> getAttributes(StringStream stream) {
        int i;
        String name = null;
        String value = null;
        StringBuilder buffer = new StringBuilder();
        Map<String, String> attributes = new LinkedHashMap<String, String>();

        while((i = stream.peek()) != -1) {
            // skip invalid character
            while((i = stream.read()) != -1) {
                if(i == '\n') {
                    this.lineNumber++;
                }

                if(Character.isLetter(i) || Character.isDigit(i) || i == ':' || i == '-' || i == '_' || i == '%' || i == '/' || i == '>') {
                    stream.back();
                    break;
                }
            }

            // check end
            if(i == '>') {
                stream.read();
                break;
            }
            else if(i == '%') {
                if(stream.peek(1) == '>') {
                    stream.skip(2);
                    break;
                }
                stream.read();
                continue;
            }
            else if(i == '/') {
                if(stream.peek(1) == '>') {
                    stream.skip(2);
                    break;
                }
                stream.read();
                continue;
            }

            // read name
            while((i = stream.read()) != -1) {
                if(i == '\n') {
                    this.lineNumber++;
                }

                if(Character.isLetter(i) || Character.isDigit(i) || i == ':' || i == '-' || i == '_') {
                    buffer.append((char)i);
                }
                else {
                    stream.back();
                    break;
                }
            }

            name = buffer.toString();
            buffer.setLength(0);

            if(name.length() < 1) {
                continue;
            }

            // skip space
            while((i = stream.read()) != -1) {
                if(i == '\n') {
                    this.lineNumber++;
                }

                if(i != ' ') {
                    stream.back();
                    break;
                }
            }

            // next character must be '='
            if(stream.peek() != '=') {
                attributes.put(name, "");
                continue;
            }

            stream.read();

            // skip space
            while((i = stream.read()) != -1) {
                if(i == '\n') {
                    this.lineNumber++;
                }

                if(i == ' ' || i == '\t' || i == '\r' || i == '\n') {
                    continue;
                }
                break;
            }

            char quote = ' ';

            if(i == '"') {
                quote = '"';
            }
            else if(i == '\'') {
                quote = '\'';
            }

            if(quote == ' ') {
                while((i = stream.read()) != -1) {
                    if(i == '\n') {
                        this.lineNumber++;
                    }

                    if(i == ' ' || i == '\t' || i == '\r' || i == '\n' || i == '>') {
                        break;
                    }
                    else if(i == '/' && stream.peek() == '>') {
                        break;
                    }
                    else {
                        buffer.append((char)i);
                    }
                }
            }
            else {
                while((i = stream.read()) != -1) {
                    if(i != quote) {
                        buffer.append((char)i);
                    }
                    else {
                        break;
                    }
                }
            }

            value = this.decode(buffer.toString());
            attributes.put(name, value);
            buffer.setLength(0);
        }
        return attributes;
    }

    /**
     * @param source
     * @return String
     */
    private String decode(String source) {
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
                                buffer.append(new String(c, i + 2, j - i - 2));
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
     * @return boolean
     */
    protected boolean isJavaIdentifier(String source) {
        if(Character.isJavaIdentifierStart(source.charAt(0)) == false) {
            return false;
        }

        for(int i = 0; i < source.length(); i++) {
            if(Character.isJavaIdentifierPart(source.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return the stream
     */
    public StringStream getStream() {
        return this.stream;
    }

    /**
     * @param stream the stream to set
     */
    public void setStream(StringStream stream) {
        this.stream = stream;
    }

    /**
     * @param lineNumber
     */
    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    /**
     * @return int
     */
    public int getLineNumber() {
        return this.lineNumber;
    }
}
