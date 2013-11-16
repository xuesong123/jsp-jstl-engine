/*
 * $RCSfile: StreamTest.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-14 $
 *
 * Copyright (C) 2008 WanMei, Inc. All rights reserved.
 *
 * This software is the proprietary information of WanMei, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.template;

import java.util.LinkedHashMap;
import java.util.Map;

import com.skin.ayada.io.StringStream;
import com.skin.ayada.util.NodeUtil;
import com.skin.ayada.util.StringUtil;

/**
 * <p>Title: StreamTest</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class StreamTest
{
    protected int lineNumber = 1;

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        StreamTest st = new StreamTest();
        String source = " % \\ / a=\"1\" % b=\"2\" \\ / c=\"3\" d=\"4\" %%///\\\\%>abc</jsp:scriptlet   >abc";
        StringStream stream = new StringStream(source);

        System.out.println("---------10        20        30        40        50        60");
        System.out.println("0123456789012345678901234567890123456789012345678901234567890");
        System.out.println(source);

        Map<String, String> attributes = st.getAttributes(stream);
        System.out.println("########################################");

        System.out.println("attributes.size: [" + attributes.size() + "]");
        System.out.println("attributes.html: [" + NodeUtil.toString(attributes) + "]");
        System.out.println("pos: " + stream.getPosition() + " - " + (char)(stream.peek()) + " - " + (char)(stream.peek(-1)) + " - " + (char)(stream.peek(-2)));

        System.out.println("pos: " + stream.getPosition() + " - " + (char)(stream.peek()));
        st.skipCRLF(stream);
        System.out.println("pos: " + stream.getPosition() + " - " + (char)(stream.peek()));
        String content = readNodeContent(stream, "jsp:scriptlet");
        System.out.println("content: [" + content + "]");
        System.out.println("compact: " + StringUtil.escape(compact("\r\n\r\n\r\n\r\n123\r\nabc\r\n\r\nedf")) + "]");
    }
    
    /**
     * read node name, after read nodeName
     * @return String
     */
    public Map<String, String> getAttributes(StringStream stream)
    {
        int i;
        String name = null;
        String value = null;
        StringBuilder buffer = new StringBuilder();
        Map<String, String> attributes = new LinkedHashMap<String, String>();

        while((i = stream.peek()) != -1)
        {
            // skip invalid character
            while((i = stream.read()) != -1)
            {
                if(i == '\n')
                {
                    this.lineNumber++;
                }

                if(Character.isLetter(i) || Character.isDigit(i) || i == ':' || i == '-' || i == '_' || i == '%' || i == '/' || i == '>')
                {
                    stream.back();
                    break;
                }
            }

            // check end
            if(i == '>')
            {
                stream.read();
                break;
            }
            else if(i == '%')
            {
                if(stream.peek(1) == '>')
                {
                    stream.skip(2);
                    break;
                }
                else
                {
                    stream.read();
                    continue;
                }
            }
            else if(i == '/')
            {
                if(stream.peek(1) == '>')
                {
                    stream.skip(2);
                    break;
                }
                else
                {
                    stream.read();
                    continue;
                }
            }

            // read name
            while((i = stream.read()) != -1)
            {
                if(i == '\n')
                {
                    this.lineNumber++;
                }

                if(Character.isLetter(i) || Character.isDigit(i) || i == ':' || i == '-' || i == '_')
                {
                    buffer.append((char)i);
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
                if(i == '\n')
                {
                    this.lineNumber++;
                }

                if(i != ' ')
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
                if(i == '\n')
                {
                    this.lineNumber++;
                }

                if(i == ' ' || i == '\t' || i == '\r' || i == '\n')
                {
                    continue;
                }
                else
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
                    if(i == '\n')
                    {
                        this.lineNumber++;
                    }

                    if(i == ' ' || i == '\t' || i == '\r' || i == '\n' || i == '>')
                    {
                        break;
                    }
                    else if(i == '/' && stream.peek() == '>')
                    {
                        break;
                    }
                    else
                    {
                        buffer.append((char)i);
                    }
                }
            }
            else
            {
                while((i = stream.read()) != -1)
                {
                    if(i != quote)
                    {
                        buffer.append((char)i);
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
     * skip crlf
     */
    public void skipCRLF(StringStream stream)
    {
        while(stream.peek() == '\r')
        {
            stream.read();
        }

        while(stream.peek() == '\n')
        {
            this.lineNumber++;
            stream.read();
        }
    }

    /**
     * @param nodeName
     * @return String
     */
    public static String readNodeContent(StringStream stream, String nodeName)
    {
        int i = 0;
        StringBuilder buffer = new StringBuilder();

        while((i = stream.read()) != -1)
        {
            if(i == '<' && stream.peek() == '/')
            {
                stream.read();

                if(match(stream, nodeName))
                {
                    stream.skip(nodeName.length());

                    while((i = stream.read()) != -1)
                    {
                        if(i == '>')
                        {
                            break;
                        }
                    }

                    break;
                }
                else
                {
                    buffer.append('/');
                    buffer.append((char)i);
                }
            }
            else
            {
                buffer.append((char)i);
            }
        }

        return buffer.toString();
    }

    /**
     * @param nodeName
     * @return boolean
     */
    public static boolean match(StringStream stream, String nodeName)
    {
        int i = 0;
        int length = nodeName.length();

        for(i = 0; i < length; i++)
        {
            if(stream.peek(i) != nodeName.charAt(i))
            {
                return false;
            }
        }

        int c = stream.peek(i);

        if(c == ' ' || c == '\r' || c == '\n' || c == '\t' || c == '/' || c == '>')
        {
            return true;
        }

        return false;
    }
    
    /**
     * @param source
     * @return String
     */
    public static String compact(String source)
    {
        char c;
        boolean b = true;
        int length = source.length();
        StringBuilder buffer = new StringBuilder();
        
        for(int i = 0; i < length; i++)
        {
            c = source.charAt(i);

            if(c == '\n')
            {
                if(b)
                {
                    buffer.append("\r\n");
                    b = false;
                }
            }
            else if(c == '\r')
            {
                continue;
            }
            else
            {
                buffer.append(c);
                b = true;
            }
        }

        return buffer.toString();
    }
}
