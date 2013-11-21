/*
 * $RCSfile: XmlTemplateParser.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-18 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.compile;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.skin.ayada.factory.TagFactoryManager;
import com.skin.ayada.runtime.TagFactory;
import com.skin.ayada.statement.Expression;
import com.skin.ayada.statement.JspDeclaration;
import com.skin.ayada.statement.JspDirective;
import com.skin.ayada.statement.JspExpression;
import com.skin.ayada.statement.JspScriptlet;
import com.skin.ayada.statement.NodeType;
import com.skin.ayada.statement.TextNode;
import com.skin.ayada.statement.Variable;
import com.skin.ayada.template.Template;
import com.skin.ayada.util.NodeUtil;
import com.skin.ayada.util.StringUtil;

/**
 * <p>Title: XmlTemplateParser</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class XmlTemplateParser
{
    public Template parse(Reader reader) throws Exception
    {
        try
        {
            if(reader == null)
            {
                throw new IllegalArgumentException("");
            }

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setNamespaceAware(true);
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new InputSource(reader));
            Element element = document.getDocumentElement();
            NodeList childNodes = element.getChildNodes();
            List<com.skin.ayada.statement.Node> list = new ArrayList<com.skin.ayada.statement.Node>();

            for(int i = 0, length = childNodes.getLength(); i < length; i++)
            {
                Node node = childNodes.item(i);

                int nodeType = node.getNodeType();

                if(nodeType == Node.ELEMENT_NODE)
                {
                    this.convert(node, null, list);
                }
            }

            String home = element.getAttribute("home");
            String path = element.getAttribute("path");
            long lastModified = this.getLongAttribute(element, "lastModified", 0L);
            long updateTime = this.getLongAttribute(element, "updateTime", 0L);
            Template template = this.getTemplate("", "", list);
            template.setHome(home);
            template.setPath(path);
            template.setLastModified(lastModified);
            template.setUpdateTime(updateTime);
            return template;
        }
        catch(Exception e)
        {
            throw e;
        }
    }

    /**
     * @param node
     * @return com.skin.ayada.statement.Node
     */
    public com.skin.ayada.statement.Node convert(Node node, com.skin.ayada.statement.Node parent, List<com.skin.ayada.statement.Node> list)
    {
        String nodeName = node.getNodeName();
        com.skin.ayada.statement.Node jspNode = null;

        if(nodeName.equals("jsp:directive.page") || nodeName.equals("jsp:directive.taglib") || nodeName.equals("jsp:directive.include"))
        {
            JspDirective jspDirective = JspDirective.getInstance(nodeName);
            jspDirective.setClosed(NodeType.SELF_CLOSED);
            jspNode = jspDirective;
        }
        else if(nodeName.equals("jsp:declaration"))
        {
            JspDeclaration jspDeclaration = new JspDeclaration();
            jspDeclaration.append(StringUtil.unescape(node.getTextContent()));
            jspDeclaration.setClosed(NodeType.PAIR_CLOSED);
            jspNode = jspDeclaration;
        }
        else if(nodeName.equals("jsp:scriptlet"))
        {
            JspScriptlet jspScriptlet = new JspScriptlet();
            jspScriptlet.append(StringUtil.unescape(node.getTextContent()));
            jspScriptlet.setClosed(NodeType.PAIR_CLOSED);
            jspNode = jspScriptlet;
        }
        else if(nodeName.equals("jsp:expression"))
        {
            JspExpression jspExpression = new JspExpression();
            jspExpression.append(StringUtil.unescape(node.getTextContent()));
            jspExpression.setClosed(NodeType.PAIR_CLOSED);
            jspNode = jspExpression;
        }
        else if(nodeName.equals("text"))
        {
            TextNode textNode = new TextNode();
            textNode.append(StringUtil.unescape(node.getTextContent()));
            textNode.setClosed(NodeType.PAIR_CLOSED);
            jspNode = textNode;
        }
        else if(nodeName.equals("expression"))
        {
            Expression expression = new Expression();
            expression.append(StringUtil.unescape(node.getTextContent()));
            expression.setClosed(NodeType.PAIR_CLOSED);
            jspNode = expression;
        }
        else if(nodeName.equals("variable"))
        {
            Variable variable = new Variable();
            variable.append(StringUtil.unescape(node.getTextContent()));
            variable.setClosed(NodeType.PAIR_CLOSED);
            jspNode = variable;
        }
        else
        {
            jspNode = new com.skin.ayada.statement.Node(nodeName);

            if(jspNode.getLength() <= 2)
            {
                jspNode.setClosed(NodeType.SELF_CLOSED);
            }
            else
            {
                jspNode.setClosed(NodeType.PAIR_CLOSED);
            }
        }

        NamedNodeMap map = node.getAttributes();

        for(int i = 0, len = map.getLength(); i < len; i++)
        {
            Node n = map.item(i);
            String name = n.getNodeName();
            String value = n.getNodeValue();

            if(name.equals("lineNumber"))
            {
                jspNode.setLineNumber(Integer.parseInt(value));
            }
            else if(name.equals("offset"))
            {
                jspNode.setOffset(Integer.parseInt(value));
            }
            else if(name.equals("length"))
            {
                jspNode.setLength(Integer.parseInt(value));
            }
            else if(name.equals("tagFactory"))
            {
                continue;
            }
            else if(name.equals("tagClass"))
            {
                jspNode.setTagClassName(value);
            }
            else
            {
                jspNode.setAttribute(name, value);
            }
        }

        if(jspNode.getNodeType() == NodeType.NODE)
        {
            if(jspNode.getLength() <= 2)
            {
                jspNode.setClosed(NodeType.SELF_CLOSED);
            }
            else
            {
                jspNode.setClosed(NodeType.PAIR_CLOSED);
            }
        }

        jspNode.setParent(parent);
        list.add(jspNode);
        NodeList childNodes = node.getChildNodes();

        for(int i = 0, length = childNodes.getLength(); i < length; i++)
        {
            Node n = childNodes.item(i);

            if(n.getNodeType() == Node.ELEMENT_NODE)
            {
                this.convert(n, jspNode, list);
            }
        }

        if(jspNode.getNodeType() != NodeType.TEXT && node.getNodeType() != NodeType.VARIABLE && jspNode.getNodeType() != NodeType.EXPRESSION)
        {
            list.add(jspNode);
        }

        return jspNode;
    }

    /**
     * @param element
     * @param name
     * @param defaultValue
     * @return long
     */
    public long getLongAttribute(Element element, String name, long defaultValue)
    {
        String value = element.getAttribute(name);

        try
        {
            return Long.parseLong(value);
        }
        catch(NumberFormatException e)
        {
        }

        return defaultValue;
    }

    /**
     * @param list
     * @param tagLibrary
     * @return Template
     */
    public Template getTemplate(String home, String path, List<com.skin.ayada.statement.Node> list)
    {
        TagFactoryManager tagFactoryManager = TagFactoryManager.getInstance();

        for(int i = 0, size = list.size(); i < size; i++)
        {
            com.skin.ayada.statement.Node node = list.get(i);

            if(node.getLength() == 0)
            {
                throw new RuntimeException("Exception at line #" + node.getLineNumber() + " " + NodeUtil.getDescription(node) + " not match !");
            }

            if(node.getNodeType() == NodeType.NODE && i == node.getOffset())
            {
                String tagName = node.getNodeName();
                String className = node.getTagClassName();
                TagFactory tagFactory = tagFactoryManager.getTagFactory(tagName, className);
                node.setTagFactory(tagFactory);
            }
        }

        Template template = new Template(home, path, list);
        template.setLastModified(0L);
        template.setUpdateTime(System.currentTimeMillis());
        return template;
    }
}
