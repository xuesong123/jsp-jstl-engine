/*
 * $RCSfile: XmlCompiler.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-17 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.compile;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.skin.ayada.statement.Node;
import com.skin.ayada.statement.NodeType;
import com.skin.ayada.template.Template;
import com.skin.ayada.util.HtmlUtil;
import com.skin.ayada.util.NodeUtil;
import com.skin.ayada.util.StringUtil;

/**
 * <p>Title: XmlCompiler</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class XmlCompiler
{
    public String compile(Template template)
    {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        writer.write("<jsp:servlet");
        writer.write("\r\n    home=\"");
        writer.write(HtmlUtil.encode(template.getHome()));
        writer.write("\"\r\n    path=\"");
        writer.write(HtmlUtil.encode(template.getPath()));
        writer.write("\"\r\n    astModified=\"");
        writer.write(String.valueOf(template.getLastModified()));
        writer.write("\"\r\n    updateTime=\"");
        writer.write(String.valueOf(template.getUpdateTime()));
        writer.write("\"");

        Node node = null;
        String indent = "    ";
        List<Node> list = template.getNodes();
        Map<String, String> namespaces = this.getNamespaces(list);

        if(namespaces.size() > 0)
        {
            for(Map.Entry<String, String> entry : namespaces.entrySet())
            {
                writer.write(" xmlns:");
                writer.write(entry.getKey());
                writer.write("=\"");
                writer.write(entry.getValue());
                writer.write("\"");
            }
            writer.println(">");
        }
        else
        {
            writer.println(">");
        }

        for(int index = 0, size = list.size(); index < size; index++)
        {
            node = list.get(index);
            indent = this.getIndent(node);

            if(node.getNodeType() == NodeType.TEXT)
            {
                writer.write(indent + "<text lineNumber=\"" + node.getLineNumber() + "\" offset=\"" + node.getOffset() + "\" length=\"" + node.getLength() + "\">");
                writer.write(StringUtil.escape(HtmlUtil.encode(node.getTextContent())));
                writer.println("</text>");
                continue;
            }

            if(node.getNodeType() == NodeType.VARIABLE)
            {
                writer.write(indent + "<variable lineNumber=\"" + node.getLineNumber() + "\" offset=\"" + node.getOffset() + "\" length=\"" + node.getLength() + "\">");
                writer.write(StringUtil.escape(HtmlUtil.encode(node.getTextContent())));
                writer.println("</variable>");
                continue;
            }

            if(node.getNodeType() == NodeType.EXPRESSION)
            {
                writer.write(indent + "<expression lineNumber=\"" + node.getLineNumber() + "\" offset=\"" + node.getOffset() + "\" length=\"" + node.getLength() + "\">");
                writer.write(StringUtil.escape(HtmlUtil.encode(node.getTextContent())));
                writer.println("</expression>");
                continue;
            }

            if(node.getLength() == 0)
            {
                throw new RuntimeException("Exception at line #" + node.getLineNumber() + " " + NodeUtil.getDescription(node) + " not match !");
            }

            if(node.getOffset() == index)
            {
                if(node.getNodeType() == NodeType.JSP_DECLARATION || node.getNodeType() == NodeType.JSP_SCRIPTLET || node.getNodeType() == NodeType.JSP_EXPRESSION)
                {
                    writer.write(indent + node.toString(index));
                    writer.write(StringUtil.escape(HtmlUtil.encode(node.getTextContent())));
                }
                else
                {
                    writer.println(indent + node.toString(index));
                }
            }
            else
            {
                if(node.getNodeType() == NodeType.JSP_DECLARATION || node.getNodeType() == NodeType.JSP_SCRIPTLET || node.getNodeType() == NodeType.JSP_EXPRESSION)
                {
                    writer.println(node.toString(index));
                }
                else
                {
                    if(node.getClosed() == NodeType.PAIR_CLOSED)
                    {
                        writer.println(indent + node.toString(index));
                    }
                }
            }
        }

        writer.println("</jsp:servlet>");
        return stringWriter.toString();
    }

    /**
     * @param list
     * @return
     */
    private Map<String, String> getNamespaces(List<Node> list)
    {
        Node node = null;
        Map<String, String> map = new HashMap<String, String>();

        for(int index = 0, size = list.size(); index < size; index++)
        {
            node = list.get(index);

            if(node.getNodeType() == NodeType.TEXT || node.getNodeType() == NodeType.VARIABLE || node.getNodeType() == NodeType.EXPRESSION)
            {
                continue;
            }

            String nodeName = node.getNodeName();
            int k = nodeName.indexOf(":");

            if(k > -1)
            {
                String prefix = nodeName.substring(0, k);

                if(map.get(prefix) == null)
                {
                    map.put(prefix, "http://www.ayada.org/" + prefix);
                }
            }
        }

        return map;
    }

    /**
     * @param node
     * @return String
     */
    private String getIndent(Node node)
    {
        Node parent = node;
        StringBuilder buffer = new StringBuilder("    ");

        while(parent != null && (parent = parent.getParent()) != null)
        {
            buffer.append("    ");
        }

        return buffer.toString();
    }
}
