/*
 * $RCSfile: TemplateCompiler.java,v $$
 * $Revision: 1.1  $
 * $Date: 2013-2-19  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.compile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skin.ayada.config.TemplateConfig;
import com.skin.ayada.io.StringStream;
import com.skin.ayada.jstl.TagLibrary;
import com.skin.ayada.source.Source;
import com.skin.ayada.source.SourceFactory;
import com.skin.ayada.statement.Expression;
import com.skin.ayada.statement.Node;
import com.skin.ayada.statement.NodeType;
import com.skin.ayada.statement.TextNode;
import com.skin.ayada.template.Template;
import com.skin.ayada.util.NodeUtil;
import com.skin.ayada.util.Stack;

/**
 * <p>Title: TemplateCompiler</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class TemplateCompiler extends PageCompiler
{
    private static final Logger logger = LoggerFactory.getLogger(TemplateCompiler.class);
    private int lineNumber = 1;
    private SourceFactory sourceFactory;
    private TagLibrary tagLibrary = null;
    private static final boolean ignoreJspTag = TemplateConfig.getInstance().getBoolean("ayada.compile.ignore-jsptag");

    /**
     * @param source
     */
    public TemplateCompiler(SourceFactory sourceFactory)
    {
        this.sourceFactory = sourceFactory;
    }

    /**
     * @param path
     * @param encoding
     * @return Template
     */
    public Template compile(String path, String encoding)
    {
        Source source = this.getSourceFactory().getSource(path, encoding);

        if(source.getType() == 1)
        {
            TextNode textNode = new TextNode();
            textNode.setLineNumber(lineNumber);
            textNode.setOffset(0);
            textNode.setLength(1);
            textNode.setParent(null);
            textNode.append(source.getSource());

            List<Node> list = new ArrayList<Node>();
            list.add(textNode);
            return new Template(list);
        }

        int i;
        char c;
        this.stream = new StringStream(source.getSource());

        while((i = this.stream.read()) != -1)
        {
            c = (char)i;

            if(Character.isISOControl(c) || c == ' ')
            {
                continue;
            }

            this.stream.back();
            break;
        }

        Stack<Node> stack = new Stack<Node>();
        StringBuilder buffer = new StringBuilder();
        StringBuilder expression = new StringBuilder();
        List<Node> list = new ArrayList<Node>();

        while((i = this.stream.read()) != -1)
        {
            c = (char)i;

            if(c == '<')
            {
                this.startTag(stack, list);
            }
            else if(c == '$' && this.stream.peek() == '{')
            {
                i = this.stream.read();
                expression.setLength(0);

                while((i = this.stream.read()) != -1)
                {
                    c = (char)i;

                    if(c == '}')
                    {
                        Expression expr = new Expression();
                        expr.setOffset(list.size());
                        expr.setLength(1);
                        expr.setLineNumber(this.lineNumber);
                        expr.append(expression.toString());
                        list.add(expr);
                        break;
                    }
                    else
                    {
                        expression.append(c);
                    }
                }
            }
            else
            {
                int line = this.lineNumber;
                buffer.append(c);

                if(c == '\n')
                {
                    this.lineNumber++;
                }

                while((i = this.stream.read()) != -1)
                {
                    if(i == '<' || i == '$')
                    {
                        this.stream.back();
                        break;
                    }
                    else
                    {
                        c = (char)i;

                        if(c == '\n')
                        {
                            this.lineNumber++;
                        }

                        buffer.append(c);
                    }
                }

                if(buffer.length() > 0)
                {
                    this.pushTextNode(stack, list, buffer.toString(), line);
                    buffer.setLength(0);
                }
            }
        }

        if(stack.peek() != null)
        {
            Node node = stack.peek();
            throw new RuntimeException("Exception at line # " + node.getLineNumber() + " " + NodeUtil.toString(node) + " not match !");
        }

        return new Template(list);
    }

    /**
     * @param stack
     * @param list
     */
    public void startTag(Stack<Node> stack, List<Node> list)
    {
        int i;
        int n = this.stream.peek();

        if(n == '/')
        {
            this.stream.read();
            String nodeName = this.getNodeName();

            if(nodeName.length() > 0)
            {
                String calssName = null;

                if(tagLibrary != null)
                {
                    calssName = tagLibrary.getTagClassName(nodeName);
                }

                if(calssName != null)
                {
                    while((i = this.stream.read()) != -1)
                    {
                        if(i == '>')
                        {
                            break;
                        }
                    }

                    this.popNode(stack, list, nodeName);
                }
                else
                {
                    this.pushTextNode(stack, list, "</" + nodeName, this.lineNumber);
                }
            }
            else
            {
                this.pushTextNode(stack, list, "</", this.lineNumber);
            }
        }
        else if(n != '!' && n != '%')
        {
            String nodeName = this.getNodeName();

            if(nodeName.equals("t:include"))
            {
                Map<String, String> attributes = this.getAttributes();

                while((i = this.stream.read()) != -1)
                {
                    if(i == '>')
                    {
                        break;
                    }
                }

                if(this.stream.peek(-2) != '/')
                {
                    throw new RuntimeException("The 't:include' direction must be self-closed!");
                }

                String type = attributes.get("type");
                String file = attributes.get("file");
                String encoding = attributes.get("encoding");
                this.include(stack, list, type, file, encoding);
                return;
            }

            if(nodeName.equals("t:import"))
            {
                Node node = new Node(nodeName);
                node.setLineNumber(this.getLineNumber());
                Map<String, String> attributes = this.getAttributes();
                node.setAttributes(attributes);
                node.setClosed(NodeType.SELF_CLOSED);
                this.pushNode(stack, list, node);

                while((i = this.stream.read()) != -1)
                {
                    if(i == '>')
                    {
                        break;
                    }
                }

                if(this.stream.peek(-2) != '/')
                {
                    throw new RuntimeException("The 't:import' direction must be self-closed!");
                }

                String name = attributes.get("name");
                String className = attributes.get("className");
                this.setupTagLibrary(name, className);
                return;
            }

            String tagClassName = null;

            if(tagLibrary != null)
            {
                tagClassName = tagLibrary.getTagClassName(nodeName);
            }

            if(tagClassName != null)
            {
                Node node = new Node(nodeName);
                node.setLineNumber(this.getLineNumber());
                Map<String, String> attributes = this.getAttributes();
                node.setOffset(list.size());
                node.setLineNumber(this.lineNumber);
                node.setAttributes(attributes);
                node.setClosed(NodeType.PAIR_CLOSED);
                this.pushNode(stack, list, node);

                i = this.stream.peek();

                if(i == '/')
                {
                    int offset = 0;

                    while((i = this.stream.peek(offset++)) != -1)
                    {
                        if(i == '>')
                        {
                            break;
                        }
                    }

                    this.stream.setPosition(this.stream.getPosition() + offset);
                    node.setLength(2);
                    node.setClosed(NodeType.SELF_CLOSED);
                    this.popNode(stack, list, nodeName);
                }
                else
                {
                    while((i = stream.read()) != -1)
                    {
                        if(i == '>')
                        {
                            break;
                        }
                    }
                }
            }
            else
            {
                this.pushTextNode(stack, list, "<" + nodeName, this.lineNumber);
            }
        }
        else
        {
            if(ignoreJspTag && n == '%')
            {
                this.stream.read();

                while((i = this.stream.read()) != -1)
                {
                    if(i == '%' && this.stream.peek() == '>')
                    {
                        this.stream.read();
                        break;
                    }
                }

                while(this.stream.peek() == '\r')
                {
                    this.stream.read();
                }

                while(this.stream.peek() == '\n')
                {
                    this.stream.read();
                }
            }
            else
            {
                this.pushTextNode(stack, list, "<", this.lineNumber);
            }
        }
    }

    /**
     * @param stack
     * @param list
     * @param node
     */
    private void pushNode(Stack<Node> stack, List<Node> list, Node node)
    {
        Node parent = stack.peek();

        if(parent != null)
        {
            node.setParent(parent);
        }

        stack.push(node);
        list.add(node);

        if(logger.isDebugEnabled())
        {
            // logger.debug("[push][node] parent: " + (parent != null ? parent.getNodeName() : "null") + "[" + node.getNodeName() + "]");
        }
    }

    /**
     * @param stack
     * @param list
     * @param nodeName
     */
    private void popNode(Stack<Node> stack, List<Node> list, String nodeName)
    {
        Node node = stack.peek();

        if(node == null)
        {
            throw new RuntimeException("Exception at line #" + this.lineNumber + ": </" + nodeName + "> not match !");
        }

        if(node.getNodeName().equalsIgnoreCase(nodeName))
        {
            stack.pop();
            node.setLength(list.size() - node.getOffset() + 1);
            list.add(node);

            if(logger.isDebugEnabled())
            {
                // Node parent = node.getParent();
                // logger.debug("[pop ][node] parent: " + (parent != null ? parent.getNodeName() : "null") + ", html:[/" + node.getNodeName() + "]");
            }
        }
        else
        {
            throw new RuntimeException("Exception at line # " + node.getLineNumber() + " " + NodeUtil.toString(node) + " not match !");
        }
    }

    /**
     * @param stack
     * @param list
     * @param text
     * @param lineNumber
     */
    private void pushTextNode(Stack<Node> stack, List<Node> list, String text, int lineNumber)
    {
        Node parent = stack.peek();

        if(parent != null && parent.getNodeName().equals("c:choose"))
        {
            return;
        }

        TextNode textNode = null;
        int size = list.size();

        if(size > 0)
        {
            Node node = list.get(size - 1);

            if(node instanceof TextNode)
            {
                textNode = (TextNode)node;
            }
            else
            {
                textNode = new TextNode();
                textNode.setLineNumber(lineNumber);
                textNode.setOffset(size);
                textNode.setLength(1);
                list.add(textNode);
            }
        }
        else
        {
            textNode = new TextNode();
            textNode.setLineNumber(lineNumber);
            textNode.setOffset(size);
            textNode.setLength(1);
            list.add(textNode);
        }

        textNode.append(text);
    }

    /**
     * @param stack
     * @param list
     * @param path
     * @param encoding
     */
    public void include(Stack<Node> stack, List<Node> list, String type, String path, String encoding)
    {
        if(path == null)
        {
            throw new RuntimeException("t:include error: attribute 'file' not exists !");
        }

        if(path.charAt(0) != '/')
        {
            throw new RuntimeException("t:include error: file must be starts with '/'");
        }

        if(encoding == null)
        {
            encoding = "UTF-8";
        }

        int index = list.size();
        Node parent = stack.peek();
        Source source = this.getSourceFactory().getSource(path, encoding);

        if(type != null && type.equals("static"))
        {
            TextNode textNode = new TextNode();
            textNode.setLineNumber(lineNumber);
            textNode.setOffset(list.size());
            textNode.setLength(1);
            textNode.setParent(parent);
            textNode.append(source.getSource());
            list.add(textNode);
            return;
        }

        TemplateCompiler compiler = new TemplateCompiler(this.sourceFactory);
        compiler.setTagLibrary(this.getTagLibrary());
        Template template = compiler.compile(path, encoding);
        List<Node> nodes = template.getNodes();

        for(Node node : nodes)
        {
            node.setOffset(-1);
        }

        for(Node node : nodes)
        {
            if(node.getOffset() == -1)
            {
                node.setOffset(index);
            }

            if(node.getParent() == null)
            {
                node.setParent(parent);
            }

            list.add(node);
            index++;
        }
    }

    /**
     * @param name
     * @param className
     */
    public void setupTagLibrary(String name, String className)
    {
        if(name == null || className == null)
        {
            return;
        }

        name = name.trim();
        className = className.trim();

        if(name.length() < 1 || className.length() < 1)
        {
            return;
        }

        TagLibrary tagLibrary = this.getTagLibrary();

        if(tagLibrary != null)
        {
            tagLibrary.setup(name, className);
        }
    }

    /**
     * @return int
     */
    public int getLineNumber()
    {
        return this.lineNumber;
    }

    /**
     * @param lineNumber
     */
    public void setLineNumber(int lineNumber)
    {
        this.lineNumber = lineNumber;
    }
    
    /**
     * @return the sourceFactory
     */
    public SourceFactory getSourceFactory()
    {
        return this.sourceFactory;
    }

    /**
     * @param sourceFactory the sourceFactory to set
     */
    public void setSourceFactory(SourceFactory sourceFactory)
    {
        this.sourceFactory = sourceFactory;
    }

    /**
     * @return TagLibrary
     */
    public TagLibrary getTagLibrary()
    {
        return tagLibrary;
    }

    /**
     * @param tagLibrary
     */
    public void setTagLibrary(TagLibrary tagLibrary)
    {
        this.tagLibrary = tagLibrary;
    }
}
