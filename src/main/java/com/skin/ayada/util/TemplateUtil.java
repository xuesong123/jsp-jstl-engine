/*
 * $RCSfile: TemplateUtil.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-11-04 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.util;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import com.skin.ayada.Template;
import com.skin.ayada.statement.Attribute;
import com.skin.ayada.statement.Node;
import com.skin.ayada.statement.NodeType;
import com.skin.ayada.statement.TagNode;

/**
 * <p>Title: TemplateUtil</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class TemplateUtil {
    /**
     * @param template
     */
    public static void print(Template template) {
        print(template, new PrintWriter(System.out));
    }

    /**
     * @param template
     * @param stream
     */
    public static void print(Template template, PrintStream stream) {
        print(template, new PrintWriter(stream));
    }

    /**
     * @param template
     * @param writer
     */
    public static void print(Template template, PrintWriter writer) {
        print(template.getNodes(), writer);
    }

    /**
     * @param list
     * @param writer
     */
    public static void print(List<Node> list, PrintWriter writer) {
        String format = "%-4s";
        String typeName = "[NODE]";

        for(int i = 0, size = list.size(); i < size; i++) {
            Node node = list.get(i);
            int nodeType = node.getNodeType();

            switch (nodeType) {
                case NodeType.TEXT: {
                    typeName = "TEXT";
                    break;
                }
                case NodeType.COMMENT: {
                    break;
                }
                case NodeType.VARIABLE: {
                    typeName = "VARI";
                    break;
                }
                case NodeType.EXPRESSION: {
                    typeName = "EXPR";
                    break;
                }
                default: {
                    typeName = "NODE";
                    break;
                }
            }
            writer.println(String.format(format, i) + "[" + typeName + "]: " + TemplateUtil.toString(node, i));
        }
        writer.flush();
    }

    /**
     * @param template
     * @return String
     */
    public static String toHtml(Template template) {
        List<Node> list = template.getNodes();
        StringBuilder buffer = new StringBuilder();

        for(int i = 0, size = list.size(); i < size; i++) {
            Node node = list.get(i);

            if(node.getNodeType() == NodeType.TEXT) {
                buffer.append("<div style=\"font-size: 12px; white-space:nowrap; line-height: 20px;\" onmouseover=\"this.style.backgroundColor='#efefef';\" onmouseout=\"this.style.backgroundColor='#ffffff';\">");
                buffer.append(StringUtil.escape(HtmlUtil.encode(node.getTextContent())));
                buffer.append("</div>\r\n");
                continue;
            }

            if(node.getNodeType() == NodeType.VARIABLE || node.getNodeType() == NodeType.EXPRESSION) {
                buffer.append("<div style=\"font-size: 12px; white-space:nowrap; line-height: 20px;\" onmouseover=\"this.style.backgroundColor='#efefef';\" onmouseout=\"this.style.backgroundColor='#ffffff';\">${");
                buffer.append(StringUtil.escape(HtmlUtil.encode(node.getTextContent())));
                buffer.append("}</div>\r\n");
                continue;
            }

            if(node.getLength() == 0) {
                break;
            }

            if(i == node.getOffset()) {
                buffer.append("<div style=\"font-size: 12px; white-space:nowrap; line-height: 20px;\" onmouseover=\"this.style.backgroundColor='#efefef';\" onmouseout=\"this.style.backgroundColor='#ffffff';\">\r\n");
                buffer.append(StringUtil.escape(HtmlUtil.encode(node.toString())));
                buffer.append("\r\n");
            }
            else {
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
    public static String toString(Template template) {
        List<Node> list = template.getNodes();
        StringBuilder buffer = new StringBuilder();

        for(int i = 0, size = list.size(); i < size; i++) {
            Node node = list.get(i);

            if(node.getNodeType() == NodeType.TEXT) {
                buffer.append(StringUtil.escape(node.getTextContent()));
                buffer.append("\r\n");
                continue;
            }

            if(node.getNodeType() == NodeType.VARIABLE || node.getNodeType() == NodeType.EXPRESSION) {
                buffer.append("${");
                buffer.append(StringUtil.escape(node.getTextContent()));
                buffer.append("}\r\n");
                continue;
            }

            if(node.getLength() == 0) {
                break;
            }

            buffer.append(TemplateUtil.toString(node, i));
            buffer.append("\r\n");
        }
        return buffer.toString();
    }

    /**
     * @param node
     * @param index
     * @return String
     */
    public static String toString(Node node, int index) {
        int nodeType = node.getNodeType();

        switch (nodeType) {
            case NodeType.TEXT: {
                return StringUtil.escape(node.getTextContent());
            }
            case NodeType.COMMENT: {
                return StringUtil.escape(node.getTextContent());
            }
            case NodeType.VARIABLE: 
            case NodeType.EXPRESSION: {
                StringBuilder buffer = new StringBuilder();
                buffer.append("${");
                buffer.append(StringUtil.escape(node.getTextContent()));
                buffer.append("}");
                return buffer.toString();
            }
            case NodeType.JSP_DECLARATION: {
                StringBuilder buffer = new StringBuilder();
                buffer.append("<jsp:declaration line=\"").append(node.getLine());
                buffer.append(" offset=\"").append(node.getOffset()).append("\"");
                buffer.append(" length=\"").append(node.getLength()).append("\"");
                buffer.append(">");
                buffer.append(StringUtil.escape(node.getTextContent()));
                buffer.append("</jsp:declaration>");
                return buffer.toString();
            }
            case NodeType.JSP_DIRECTIVE_PAGE: {
                StringBuilder buffer = new StringBuilder();
                buffer.append("<jsp:directive.page line=\"").append(node.getLine()).append("\"");
                buffer.append(" offset=\"").append(node.getOffset()).append("\"");
                buffer.append(" length=\"").append(node.getLength()).append("\"");

                if(node.getAttributeValue("contentType") != null) {
                    buffer.append(" contentType=\"").append(node.getAttributeValue("contentType")).append("\"");
                }

                if(node.getAttributeValue("import") != null) {
                    buffer.append(" import=\"").append(node.getAttributeValue("import")).append("\"");
                }
                buffer.append("/>");
                return buffer.toString();
            }
            case NodeType.JSP_DIRECTIVE_TAGLIB: {
                StringBuilder buffer = new StringBuilder();
                buffer.append("<jsp:directive.taglib line=\"").append(node.getLine()).append("\"");
                buffer.append(" offset=\"").append(node.getOffset()).append("\"");
                buffer.append(" length=\"").append(node.getLength()).append("\"");
                buffer.append(" prefix=\"").append(node.getAttributeValue("prefix")).append("\"");
                buffer.append(" uri=\"").append(node.getAttributeValue("uri")).append("\"");
                buffer.append("/>");
                return buffer.toString();
            }
            case NodeType.JSP_DIRECTIVE_INCLUDE: {
                StringBuilder buffer = new StringBuilder();
                buffer.append("<jsp:directive.include line=\"").append(node.getLine()).append("\"");
                buffer.append(" offset=\"").append(node.getOffset()).append("\"");
                buffer.append(" length=\"").append(node.getLength()).append("\"");
                buffer.append(" file=\"").append(node.getAttributeValue("file")).append("\"");
                buffer.append("/>");
                return buffer.toString();
            }
            case NodeType.JSP_SCRIPTLET: {
                StringBuilder buffer = new StringBuilder();
                buffer.append("<jsp:scriptlet line=\"").append(node.getLine()).append("\"");
                buffer.append(" offset=\"").append(node.getOffset()).append("\"");
                buffer.append(" length=\"").append(node.getLength()).append("\"");
                buffer.append(">");
                buffer.append(StringUtil.escape(node.getTextContent()));
                buffer.append("</jsp:scriptlet>");
                return buffer.toString();
            }
            case NodeType.JSP_EXPRESSION: {
                StringBuilder buffer = new StringBuilder();
                buffer.append("<jsp:expression line=\"").append(node.getLine()).append("\"");
                buffer.append(" offset=\"").append(node.getOffset()).append("\"");
                buffer.append(" length=\"").append(node.getLength()).append("\"");
                buffer.append(">");
                buffer.append(node.getTextContent());
                buffer.append("</jsp:expression>");
                return buffer.toString();
            }
            default: {
            }
        }

        StringBuilder buffer = new StringBuilder();

        if(index == node.getOffset()) {
            buffer.append("<");
            buffer.append(node.getNodeName());
            buffer.append(" line=\"");
            buffer.append(node.getLine());
            buffer.append("\" offset=\"");
            buffer.append(node.getOffset());
            buffer.append("\" length=\"");
            buffer.append(node.getLength());
            buffer.append("\"");

            if(node instanceof TagNode) {
                if(((TagNode)node).getTagClassName() != null) {
                    buffer.append(" tagClass=\"");
                    buffer.append(((TagNode)node).getTagClassName());
                    buffer.append("\"");
                }
                if(((TagNode)node).getTagFactory() != null) {
                    buffer.append(" tagFactory=\"");
                    buffer.append(((TagNode)node).getTagFactory().getClass().getName());
                    buffer.append("\"");
                }
            }

            Map<String, Attribute> attributes = node.getAttributes();

            if(attributes != null && attributes.size() > 0) {
                for(Map.Entry<String, Attribute> entrySet : attributes.entrySet()) {
                    buffer.append(" ");
                    buffer.append(entrySet.getKey());
                    buffer.append("=\"");
                    buffer.append(StringUtil.escape(HtmlUtil.encode(entrySet.getValue().getText())));
                    buffer.append("\"");
                }
            }
            buffer.append(">");
        }
        else {
            buffer.append("</");
            buffer.append(node.getNodeName());
            buffer.append(">");
        }
        return buffer.toString();
    }
}
