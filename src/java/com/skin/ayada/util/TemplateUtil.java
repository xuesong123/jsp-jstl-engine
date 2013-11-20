/*
 * $RCSfile: TemplateUtil.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-4 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.util;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import com.skin.ayada.statement.Node;
import com.skin.ayada.statement.NodeType;
import com.skin.ayada.template.Template;

/**
 * <p>Title: TemplateUtil</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class TemplateUtil
{
    /**
     * @param template
     * @return String
     */
    public static void print(Template template, PrintWriter writer)
    {
        List<Node> list = template.getNodes();

        for(int i = 0, size = list.size(); i < size; i++)
        {
            Node node = list.get(i);

            if(node.getNodeType() == NodeType.TEXT)
            {
                writer.println("[TEXT]: " + StringUtil.escape(node.getTextContent()));
                continue;
            }

            if(node.getNodeType() == NodeType.EXPRESSION)
            {
                writer.println("[EXPR]: ${" + StringUtil.escape(node.getTextContent()) + "}");
                continue;
            }

            if(node.getLength() == 0)
            {
                break;
            }

            writer.println("[NODE]: " + TemplateUtil.toString(node, i, false));
        }

        writer.flush();
    }

    /**
     * @param template
     * @return String
     */
    public static String toHtml(Template template)
    {
        List<Node> list = template.getNodes();
        StringBuilder buffer = new StringBuilder();

        for(int i = 0, size = list.size(); i < size; i++)
        {
            Node node = list.get(i);

            if(node.getNodeType() == NodeType.TEXT)
            {
                buffer.append("<div style=\"font-size: 12px; white-space:nowrap; line-height: 20px;\" onmouseover=\"this.style.backgroundColor='#efefef';\" onmouseout=\"this.style.backgroundColor='#ffffff';\">");
                buffer.append(StringUtil.escape(HtmlUtil.encode(node.getTextContent())));
                buffer.append("</div>\r\n");
                continue;
            }

            if(node.getNodeType() == NodeType.EXPRESSION)
            {
                buffer.append("<div style=\"font-size: 12px; white-space:nowrap; line-height: 20px;\" onmouseover=\"this.style.backgroundColor='#efefef';\" onmouseout=\"this.style.backgroundColor='#ffffff';\">${");
                buffer.append(StringUtil.escape(HtmlUtil.encode(node.getTextContent())));
                buffer.append("}</div>\r\n");
                continue;
            }

            if(node.getLength() == 0)
            {
                break;
            }

            if(i == node.getOffset())
            {
                buffer.append("<div style=\"font-size: 12px; white-space:nowrap; line-height: 20px;\" onmouseover=\"this.style.backgroundColor='#efefef';\" onmouseout=\"this.style.backgroundColor='#ffffff';\">\r\n");
                buffer.append(StringUtil.escape(HtmlUtil.encode(node.toString())));
                buffer.append("\r\n");
            }
            else
            {
                buffer.append(HtmlUtil.encode("</" + node.getNodeName() + ">"));
                buffer.append("\r\n</div>\r\n");
            }
        }

        return buffer.toString();
    }

    /**
     * @param template
     * @return String
     */
    public static String toString(Template template)
    {
        List<Node> list = template.getNodes();
        StringBuilder buffer = new StringBuilder();

        for(int i = 0, size = list.size(); i < size; i++)
        {
            Node node = list.get(i);

            if(node.getNodeType() == NodeType.TEXT)
            {
                buffer.append(StringUtil.escape(node.getTextContent()));
                buffer.append("\r\n");
                continue;
            }

            if(node.getNodeType() == NodeType.EXPRESSION)
            {
                buffer.append("${");
                buffer.append(StringUtil.escape(node.getTextContent()));
                buffer.append("}\r\n");
                continue;
            }

            if(node.getLength() == 0)
            {
                break;
            }

            buffer.append(TemplateUtil.toString(node, i, false));
            buffer.append("\r\n");
        }

        return buffer.toString();
    }

    /**
     * @param node
     * @return String
     */
    public static String toString(Node node, int index, boolean closed)
    {
        StringBuilder buffer = new StringBuilder();

        if(node.getNodeType() == NodeType.TEXT)
        {
            buffer.append(node.getTextContent());
            return buffer.toString();
        }

        if(node.getNodeType() == NodeType.COMMENT)
        {
            buffer.append(node.getTextContent());
            return buffer.toString();
        }

        if(node.getNodeType() == NodeType.EXPRESSION)
        {
            buffer.append("${");
            buffer.append(node.getTextContent());
            buffer.append("}");
            return buffer.toString();
        }

        if(index == node.getOffset())
        {
            buffer.append("<");
            buffer.append(node.getNodeName());
            buffer.append(" lineNumber=\"");
            buffer.append(node.getLineNumber());
            buffer.append("\" offset=\"");
            buffer.append(node.getOffset());
            buffer.append("\" length=\"");
            buffer.append(node.getLength());
            buffer.append("\"");
    
            if(node.getTagClassName() != null)
            {
                buffer.append(" tagClass=\"");
                buffer.append(node.getTagClassName());
                buffer.append("\"");
            }
    
            if(node.getTagFactory() != null)
            {
                buffer.append(" tagFactory=\"");
                buffer.append(node.getTagFactory().getClass().getName());
                buffer.append("\"");
            }
    
            Map<String, String> attributes = node.getAttributes();
    
            if(attributes != null && attributes.size() > 0)
            {
                for(Map.Entry<String, String> entrySet : attributes.entrySet())
                {
                    buffer.append(" ");
                    buffer.append(entrySet.getKey());
                    buffer.append("=\"");
                    buffer.append(entrySet.getValue());
                    buffer.append("\"");
                }
            }

            if(closed && node.getClosed() == NodeType.SELF_CLOSED)
            {
                buffer.append("/>");
            }
            else
            {
                buffer.append(">");
            }
        }
        else
        {
            buffer.append("</");
            buffer.append(node.getNodeName());
            buffer.append(">");
        }

        return buffer.toString();
    }
}
