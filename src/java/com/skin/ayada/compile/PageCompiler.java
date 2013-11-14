/*
 * $RCSfile: PageCompiler.java,v $$
 * $Revision: 1.1  $
 * $Date: 2013-3-2  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.compile;

import java.util.HashMap;
import java.util.Map;

import com.skin.ayada.io.StringStream;

/**
 * <p>Title: PageCompiler</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class PageCompiler
{
    protected StringStream stream;

    /**
     */
    public PageCompiler()
    {
    }

    /**
     * read node name, after read '<'
     * @return String
     */
    protected String getNodeName()
    {
        int i;
        char c;
        StringBuilder buffer = new StringBuilder();
        while((i = this.stream.read()) != -1)
        {
            c = (char)i;

            if(Character.isLetter(c) || Character.isDigit(c) || c == ':' || c == '-' || c == '_')
            {
                buffer.append(c);
            }
            else
            {
                this.stream.back();
                break;
            }
        }

        return buffer.toString();
    };

    /**
     * read node name, after read nodeName
     * @return String
     */
    public Map<String, String> getAttributes()
    {
        return this.getAttributes(this.stream);
    }

    /**
     * read node name, after read nodeName
     * @return String
     */
    public Map<String, String> getAttributes(StringStream stream)
    {
        int i;
        char c;
        String name = null;
        String value = null;
        StringBuilder buffer = new StringBuilder();
        Map<String, String> attributes = new HashMap<String, String>();

        while((i = stream.peek()) != -1)
        {
            if(i == ' ' || i == '\t' || i == '\r' || i == '\n')
            {
                stream.read();
            }
            else
            {
                break;
            }
        }

        while((i = stream.peek()) != -1)
        {
            if(i == '/' || i == '>')
            {
                break;
            }

            if(i == '%' && stream.peek(1) == '>')
            {
                stream.read();
                break;
            }

            // skip space
            while((i = stream.read()) != -1)
            {
                c = (char)i;

                if(Character.isLetter(c) || Character.isDigit(c) || c == ':' || c == '-' || c == '_' || c == '/' || c == '>')
                {
                    stream.back();
                    break;
                }
            }

            if(i == '/' || i == '>')
            {
                break;
            }

            // read name
            while((i = stream.read()) != -1)
            {
                c = (char)i;

                if(Character.isLetter(c) || Character.isDigit(c) || c == ':' || c == '-' || c == '_')
                {
                    buffer.append(c);
                }
                else
                {
                    stream.back();
                    break;
                }
            }

            name = buffer.toString();
            buffer.setLength(0);

            if(name.length() < 1)
            {
                continue;
            }

            // skip space
            while((i = stream.read()) != -1)
            {
                c = (char)i;

                if(c != ' ')
                {
                    stream.back();
                    break;
                }
            }

            // next character must be '='
            if(stream.peek() != '=')
            {
                attributes.put(name, "");
                continue;
            }
            else
            {
                stream.read();
            }

            // skip space
            while((i = stream.read()) != -1)
            {
                c = (char)i;

                if(c != ' ')
                {
                    break;
                }
            }

            char quote = ' ';

            if(i == '"')
            {
                quote = '"';
            }
            else if(i == '\'')
            {
                quote = '\'';
            }

            if(quote == ' ')
            {
                while((i = stream.read()) != -1)
                {
                    c = (char)i;

                    if(c == ' ' || c == '>')
                    {
                        break;
                    }
                    else if(c == '/' && stream.peek() == '>')
                    {
                        break;
                    }
                    else
                    {
                        buffer.append(c);
                    }
                }
            }
            else
            {
                while((i = stream.read()) != -1)
                {
                    c = (char)i;

                    if(c != quote)
                    {
                        buffer.append(c);
                    }
                    else
                    {
                        break;
                    }
                }
            }

            value = buffer.toString();
            attributes.put(name, value);
            buffer.setLength(0);
        }

        return attributes;
    }

    /**
     * @return the stream
     */
    public StringStream getStream()
    {
        return this.stream;
    }

    /**
     * @param stream the stream to set
     */
    public void setStream(StringStream stream)
    {
        this.stream = stream;
    };
}
