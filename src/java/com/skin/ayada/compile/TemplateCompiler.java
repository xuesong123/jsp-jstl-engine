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

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skin.ayada.config.TemplateConfig;
import com.skin.ayada.factory.TagFactoryManager;
import com.skin.ayada.io.StringStream;
import com.skin.ayada.jstl.TagInfo;
import com.skin.ayada.jstl.TagLibrary;
import com.skin.ayada.runtime.TagFactory;
import com.skin.ayada.source.Source;
import com.skin.ayada.source.SourceFactory;
import com.skin.ayada.statement.DataNode;
import com.skin.ayada.statement.Expression;
import com.skin.ayada.statement.JspDeclaration;
import com.skin.ayada.statement.JspDirective;
import com.skin.ayada.statement.JspExpression;
import com.skin.ayada.statement.JspScriptlet;
import com.skin.ayada.statement.Node;
import com.skin.ayada.statement.NodeType;
import com.skin.ayada.statement.TextNode;
import com.skin.ayada.statement.Variable;
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
    private SourceFactory sourceFactory;
    private TagLibrary tagLibrary = null;
    private boolean ignoreJspTag = true;
    private static final Logger logger = LoggerFactory.getLogger(TemplateCompiler.class);

    /**
     * @param source
     */
    public TemplateCompiler(SourceFactory sourceFactory)
    {
        this.sourceFactory = sourceFactory;
        this.ignoreJspTag = TemplateConfig.getInstance().getBoolean("ayada.compile.ignore-jsptag");
    }

    /**
     * @param path
     * @param encoding
     * @return Template
     */
    public Template compile(String path, String encoding) throws Exception
    {
        long t1 = System.currentTimeMillis();
        Source source = this.getSourceFactory().getSource(path, encoding);
        long t2 = System.currentTimeMillis();

        if(source.getType() == Source.STATIC)
        {
            TextNode textNode = new TextNode();
            textNode.setLineNumber(lineNumber);
            textNode.setOffset(0);
            textNode.setLength(1);
            textNode.setParent(null);
            textNode.append(source.getSource());
            List<Node> list = new ArrayList<Node>();
            list.add(textNode);
            return this.getTemplate(source, list, this.tagLibrary);
        }

        int i;
        Stack<Node> stack = new Stack<Node>();
        StringBuilder buffer = new StringBuilder();
        StringBuilder expression = new StringBuilder();
        List<Node> list = new ArrayList<Node>();
        this.stream = new StringStream(source.getSource());

        while((i = this.stream.read()) != -1)
        {
            if(Character.isISOControl(i) || i == ' ')
            {
                continue;
            }

            this.stream.back();
            break;
        }

        while((i = this.stream.read()) != -1)
        {
            if(i == '<')
            {
                this.startTag(stack, list);
            }
            else if(i == '$' && this.stream.peek() == '{')
            {
                i = this.stream.read();
                expression.setLength(0);

                while((i = this.stream.read()) != -1)
                {
                    if(i == '}')
                    {
                        String temp = expression.toString().trim();

                        if(temp.length() > 0)
                        {
                            if(this.isJavaIdentifier(temp))
                            {
                                Variable variable = new Variable();
                                variable.setOffset(list.size());
                                variable.setLength(1);
                                variable.setLineNumber(this.lineNumber);
                                variable.append(temp);

                                if(stack.peek() != null)
                                {
                                    variable.setParent(stack.peek());
                                }

                                list.add(variable);
                            }
                            else
                            {
                                Expression expr = new Expression();
                                expr.setOffset(list.size());
                                expr.setLength(1);
                                expr.setLineNumber(this.lineNumber);
                                expr.append(temp);
    
                                if(stack.peek() != null)
                                {
                                    expr.setParent(stack.peek());
                                }

                                list.add(expr);
                            }
                        }

                        break;
                    }
                    else
                    {
                        expression.append((char)i);
                    }
                }
            }
            else
            {
                int line = this.lineNumber;
                buffer.append((char)i);

                if(i == '\n')
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
                        if(i == '\n')
                        {
                            this.lineNumber++;
                        }

                        buffer.append((char)i);
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
            stack.print(new PrintWriter(System.err));
            throw new Exception("Exception at line #" + node.getLineNumber() + " " + NodeUtil.getDescription(node) + " not match !");
        }

        long t3 = System.currentTimeMillis();
        Template template = this.getTemplate(source, list, this.tagLibrary);
        long t4 = System.currentTimeMillis();

        if(logger.isDebugEnabled())
        {
            logger.debug("getSource: " + (t2 - t1));
            logger.debug("compile time: " + (t3 - t2));
            logger.debug("create tagFactory: " + (t4 - t3));
        }

        return template;
    }

    /**
     * @param stack
     * @param list
     */
    public void startTag(Stack<Node> stack, List<Node> list) throws Exception
    {
        int i;
        int n = this.stream.peek();

        if(n == '/')
        {
            this.stream.read();
            String nodeName = this.getNodeName();

            if(nodeName.length() > 0)
            {
                String tagClassName = null;

                if(tagLibrary != null)
                {
                    tagClassName = tagLibrary.getTagClassName(nodeName);
                }

                if(tagClassName != null)
                {
                    while((i = this.stream.read()) != -1)
                    {
                        if(i == '>')
                        {
                            break;
                        }
                        else if(i == '\n')
                        {
                            this.lineNumber++;
                        }
                    }

                    this.popNode(stack, list, nodeName);
                    TagInfo tagInfo = this.tagLibrary.getTagInfo(nodeName);

                    if(tagInfo != null)
                    {
                        if(tagInfo.getBodyContent() == TagInfo.EMPTY)
                        {
                            this.skipCRLF();
                        }
                    }
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
        else if(n == '%')
        {
            if(this.ignoreJspTag)
            {
                this.stream.read();

                while((i = this.stream.read()) != -1)
                {
                    if(i == '%' && this.stream.peek() == '>')
                    {
                        this.stream.read();
                        break;
                    }

                    if(i == '\n')
                    {
                        this.lineNumber++;
                    }
                }

                this.skipCRLF();
            }
            else
            {
                this.jspCompile(stack, list);
            }
        }
        else if(n != '!')
        {
            String nodeName = this.getNodeName();

            if(nodeName.equals("t:include"))
            {
                Map<String, String> attributes = this.getAttributes();

                if(this.stream.peek(-2) != '/')
                {
                    throw new Exception("The 't:include' direction must be self-closed!");
                }

                this.skipCRLF();
                String type = attributes.get("type");
                String file = attributes.get("file");
                String encoding = attributes.get("encoding");
                this.include(stack, list, type, file, encoding);
                return;
            }

            String tagClassName = null;

            if(tagLibrary != null)
            {
                tagClassName = tagLibrary.getTagClassName(nodeName);
            }

            if(nodeName.equals("t:import"))
            {
                Node node = new Node(nodeName);
                node.setTagClassName(tagClassName);
                node.setLineNumber(this.getLineNumber());
                Map<String, String> attributes = this.getAttributes();
                node.setOffset(list.size());
                node.setAttributes(attributes);
                node.setClosed(NodeType.SELF_CLOSED);
                this.pushNode(stack, list, node);

                if(this.stream.peek(-2) != '/')
                {
                    throw new Exception("The 't:import' direction must be self-closed!");
                }

                this.skipCRLF();
                String name = attributes.get("name");
                String className = attributes.get("className");
                this.setupTagLibrary(name, className);
                this.popNode(stack, list, nodeName);
                return;
            }

            if(nodeName.equals("jsp:directive.page") || nodeName.equals("jsp:directive.taglib") || nodeName.equals("jsp:directive.include"))
            {
                JspDirective node = JspDirective.getInstance(nodeName);
                node.setTagClassName((String)null);
                node.setLineNumber(this.getLineNumber());
                Map<String, String> attributes = this.getAttributes();
                node.setOffset(list.size());
                node.setAttributes(attributes);
                node.setClosed(NodeType.SELF_CLOSED);

                if(this.ignoreJspTag == false)
                {
                    this.pushNode(stack, list, node);
                    this.popNode(stack, list, nodeName);
                }

                if(this.stream.peek(-2) != '/')
                {
                    throw new Exception("The 'jsp:directive.page' direction must be self-closed!");
                }

                this.skipCRLF();
            }
            else if(nodeName.equals("jsp:declaration"))
            {
                JspDeclaration node = new JspDeclaration();
                node.setTagClassName((String)null);
                node.setLineNumber(this.getLineNumber());
                Map<String, String> attributes = this.getAttributes();
                node.setOffset(list.size());
                node.setAttributes(attributes);
                node.setClosed(NodeType.PAIR_CLOSED);
                this.skipCRLF();
                node.append(this.readNodeContent(nodeName));
                this.skipCRLF();

                if(this.ignoreJspTag == false)
                {
                    this.pushNode(stack, list, node);
                    this.popNode(stack, list, nodeName);
                }
            }
            else if(nodeName.equals("jsp:scriptlet"))
            {
                JspScriptlet node = new JspScriptlet();
                node.setTagClassName((String)null);
                node.setLineNumber(this.getLineNumber());
                Map<String, String> attributes = this.getAttributes();
                node.setOffset(list.size());
                node.setAttributes(attributes);
                node.setClosed(NodeType.PAIR_CLOSED);
                this.skipCRLF();
                node.append(this.readNodeContent(nodeName));
                this.skipCRLF();

                if(this.ignoreJspTag == false)
                {
                    this.pushNode(stack, list, node);
                    this.popNode(stack, list, nodeName);
                }
            }
            else if(nodeName.equals("jsp:expression"))
            {
                JspExpression node = new JspExpression();
                node.setTagClassName((String)null);
                node.setLineNumber(this.getLineNumber());
                Map<String, String> attributes = this.getAttributes();
                node.setOffset(list.size());
                node.setAttributes(attributes);
                node.setClosed(NodeType.PAIR_CLOSED);
                this.skipCRLF();
                node.append(this.readNodeContent(nodeName));

                if(this.ignoreJspTag == false)
                {
                    this.pushNode(stack, list, node);
                    this.popNode(stack, list, nodeName);
                };
            }
            else if(tagClassName != null)
            {
                Node node = new Node(nodeName);
                node.setTagClassName(tagClassName);
                node.setLineNumber(this.getLineNumber());
                Map<String, String> attributes = this.getAttributes();
                node.setOffset(list.size());
                node.setAttributes(attributes);
                node.setClosed(NodeType.PAIR_CLOSED);
                this.pushNode(stack, list, node);
                boolean isEnd = (this.stream.peek(-2) == '/');

                if(isEnd)
                {
                    node.setLength(2);
                    node.setClosed(NodeType.SELF_CLOSED);
                    this.popNode(stack, list, nodeName);
                }
            }
            else
            {
                this.pushTextNode(stack, list, "<" + nodeName, this.lineNumber);
            }
        }
        else
        {
            this.pushTextNode(stack, list, "<", this.lineNumber);
        }
    }

    /**
     * skip crlf
     */
    public void skipCRLF()
    {
        int i = -1;

        while((i = this.stream.peek()) != -1)
        {
            if(i == '\r')
            {
                this.stream.read();
                continue;
            }

            if(i == '\n')
            {
                this.lineNumber++;
                this.stream.read();
                continue;
            }

            break;
        }
    }

    /**
     * skip whitespace
     */
    public void skipWhitespace()
    {
        int i;
        while((i = this.stream.peek()) != -1)
        {
            if(i <= ' ')
            {
                if(i == '\n')
                {
                    this.lineNumber++;
                }

                this.stream.read();
                continue;
            }
            else
            {
                break;
            }
        }
    }

    /**
     * @param nodeName
     * @return String
     */
    public String readNodeContent(String nodeName)
    {
        int i = 0;
        StringBuilder buffer = new StringBuilder();

        while((i = this.stream.read()) != -1)
        {
            if(i == '<' && this.stream.peek() == '/')
            {
                this.stream.read();

                if(this.match(nodeName))
                {
                    this.stream.skip(nodeName.length());

                    while((i = this.stream.read()) != -1)
                    {
                        if(i == '\n')
                        {
                            this.lineNumber++;
                        }

                        if(i == '>')
                        {
                            break;
                        }
                    }

                    break;
                }
                else
                {
                    buffer.append("</");
                }
            }
            else
            {
                if(i == '\n')
                {
                    this.lineNumber++;
                }

                buffer.append((char)i);
            }
        }

        return buffer.toString();
    }

    /**
     * @param nodeName
     * @return boolean
     */
    public boolean match(String nodeName)
    {
        int i = 0;
        int length = nodeName.length();

        for(i = 0; i < length; i++)
        {
            if(this.stream.peek(i) != nodeName.charAt(i))
            {
                return false;
            }
        }

        int c = this.stream.peek(i);

        if(c == ' ' || c == '\r' || c == '\n' || c == '\t' || c == '/' || c == '>')
        {
            return true;
        }

        return false;
    }

    /**
     * @param stack
     * @param list
     */
    private void jspCompile(Stack<Node> stack, List<Node> list) throws Exception
    {
        int i = this.stream.read();
        i = this.stream.read();

        if(i == '@')
        {
            i = this.stream.read();
            int lineNumber = this.getLineNumber();
            String nodeName = null;
            Map<String, String> attributes = this.getAttributes();

            if(attributes.get("page") != null)
            {
                nodeName = NodeType.JSP_DIRECTIVE_PAGE_NAME;
                attributes.remove("page");
            }
            else if(attributes.get("taglib") != null)
            {
                nodeName = NodeType.JSP_DIRECTIVE_TAGLIB_NAME;
            }
            else if(attributes.get("include") != null)
            {
                nodeName = NodeType.JSP_DIRECTIVE_INCLUDE_NAME;
            }
            else
            {
                throw new Exception("Unknown jsp directive at line #" + this.lineNumber + " - <%@ " + NodeUtil.toString(attributes));
            }

            JspDirective node = JspDirective.getInstance(nodeName);
            node.setTagClassName((String)null);
            node.setOffset(list.size());
            node.setLineNumber(lineNumber);
            node.setAttributes(attributes);
            node.setClosed(NodeType.SELF_CLOSED);
            this.pushNode(stack, list, node);
            this.popNode(stack, list, nodeName);
            this.skipCRLF();
        }
        else
        {
            int t = i;
            StringBuilder buffer = new StringBuilder();

            if(i != '!' && i != '=' && i != '\r' && i != '\n')
            {
                buffer.append((char)i);
            }

            if(i == '\n')
            {
                this.lineNumber++;
            }

            this.skipCRLF();
            int ln = this.lineNumber;

            while((i = this.stream.read()) != -1)
            {
                if(i == '%' && this.stream.peek() == '>')
                {
                    this.stream.read();
                    break;
                }
                else
                {
                    if(i == '\n')
                    {
                        this.lineNumber++;
                    }

                    buffer.append((char)i);
                }
            }

            if(t == '!')
            {
                JspDeclaration node = new JspDeclaration();
                node.append(buffer.toString());
                node.setOffset(list.size());
                node.setLineNumber(ln);
                node.setClosed(NodeType.PAIR_CLOSED);
                this.pushNode(stack, list, node);
                this.popNode(stack, list, node.getNodeName());
                this.skipCRLF();
            }
            else if(t == '=')
            {
                JspExpression node = new JspExpression();
                node.append(buffer.toString());
                node.setOffset(list.size());
                node.setLineNumber(ln);
                node.setClosed(NodeType.PAIR_CLOSED);
                this.pushNode(stack, list, node);
                this.popNode(stack, list, node.getNodeName());
            }
            else
            {
                JspScriptlet node = new JspScriptlet();
                node.append(buffer.toString());
                node.setOffset(list.size());
                node.setLineNumber(ln);
                node.setClosed(NodeType.PAIR_CLOSED);
                this.pushNode(stack, list, node);
                this.popNode(stack, list, node.getNodeName());
                this.skipCRLF();
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
            logger.debug("[push][node] parent: " + (parent != null ? parent.getNodeName() : "null") + ", nodeName: [" + node.getNodeName() + "]");
        }
    }

    /**
     * @param stack
     * @param list
     * @param nodeName
     */
    private void popNode(Stack<Node> stack, List<Node> list, String nodeName) throws Exception
    {
        Node node = stack.peek();

        if(node == null)
        {
            throw new Exception("Exception at line #" + this.lineNumber + ": </" + nodeName + "> not match !");
        }

        if(node.getNodeName().equalsIgnoreCase(nodeName))
        {
            stack.pop();
            node.setLength(list.size() - node.getOffset() + 1);
            list.add(node);

            if(logger.isDebugEnabled())
            {
                Node parent = node.getParent();
                logger.debug("[pop ][node] parent: " + (parent != null ? parent.getNodeName() : "null") + ", nodeName: [/" + node.getNodeName() + "]");
            }
        }
        else
        {
            stack.print();
            throw new Exception("Exception at line #" + node.getLineNumber() + " " + NodeUtil.getDescription(node) + " not match !");
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

        textNode.setParent(parent);
        textNode.append(text);
    }

    /**
     * @param stack
     * @param list
     * @param path
     * @param encoding
     */
    public void include(Stack<Node> stack, List<Node> list, String type, String path, String encoding) throws Exception
    {
        if(path == null)
        {
            throw new Exception("t:include error: attribute 'file' not exists !");
        }

        if(path.charAt(0) != '/')
        {
            throw new Exception("t:include error: file must be starts with '/'");
        }

        if(encoding == null)
        {
            encoding = "UTF-8";
        }

        int index = list.size();
        Node parent = stack.peek();
        Source source = this.getSourceFactory().getSource(path, encoding);

        if(source.getType() == Source.STATIC || (type != null && type.equals("static")))
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
        compiler.setIgnoreJspTag(this.getIgnoreJspTag());

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
     * @param list
     * @param tagLibrary
     * @return Template
     */
    public Template getTemplate(Source source, List<Node> list, TagLibrary tagLibrary) throws Exception
    {
        List<Node> nodes = this.compact(list, tagLibrary);
        TagFactoryManager tagFactoryManager = TagFactoryManager.getInstance();

        for(int i = 0, size = nodes.size(); i < size; i++)
        {
            Node node = nodes.get(i);

            if(node.getNodeType() == NodeType.NODE && i == node.getOffset())
            {
                if(node.getLength() == 0)
                {
                    throw new Exception("Exception at line #" + node.getLineNumber() + " " + NodeUtil.getDescription(node) + " not match !");
                }

                String tagName = node.getNodeName();
                String className = tagLibrary.getTagClassName(tagName);
                TagFactory tagFactory = tagFactoryManager.getTagFactory(tagName, className);
                node.setTagFactory(tagFactory);
            }
        }

        Template template = new Template(source.getHome(), source.getPath(), nodes);
        template.setLastModified(source.getLastModified());
        template.setUpdateTime(System.currentTimeMillis());
        return template;
    }

    /**
     * @param list
     * @param tagLibrary
     * @return List<Node>
     */
    public List<Node> compact(List<Node> list, TagLibrary tagLibrary) throws Exception
    {
        List<Node> nodes = new ArrayList<Node>();

        for(int i = 0, size = list.size(); i < size; i++)
        {
            Node node = list.get(i);
            int nodeType = node.getNodeType();

            if(nodeType == NodeType.TEXT || nodeType == NodeType.EXPRESSION || nodeType == NodeType.VARIABLE)
            {
                if(node.getTextContent().length() > 0)
                {
                    node.setOffset(nodes.size());
                    node.setLength(1);
                    nodes.add(node);
                }
            }
            else
            {
                if(i == node.getOffset())
                {
                    TagInfo tagInfo = tagLibrary.getTagInfo(node.getNodeName());

                    if(tagInfo != null)
                    {
                        int bodyContent = tagInfo.getBodyContent();
    
                        if(bodyContent == TagInfo.TAGDEPENDENT)
                        {
                            // clear TextNode
                            for(int j = i + 1, length = i + node.getLength() - 1; j < length; j++)
                            {
                                Node n = list.get(j);
    
                                if(n instanceof DataNode)
                                {
                                    ((DataNode)n).clear();
                                }
                                else
                                {
                                    j = j + n.getLength() - 1;
                                }
                            }
                        }
                        else if(bodyContent == TagInfo.EMPTY)
                        {
                            // jump to node end
                            i = i + node.getLength() - 2;
                        }
                    }

                    node.setOffset(nodes.size());
                }
                else
                {
                    node.setLength(nodes.size() - node.getOffset() + 1);
                    
                    if(node.getLength() == 1)
                    {
                        throw new Exception("length == 1");
                    }
                }

                nodes.add(node);
            }
        }

        return nodes;
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

    /**
     * @param ignoreJspTag the ignoreJspTag to set
     */
    public void setIgnoreJspTag(boolean ignoreJspTag)
    {
        this.ignoreJspTag = ignoreJspTag;
    }

    /**
     * @return the ignoreJspTag
     */
    public boolean getIgnoreJspTag()
    {
        return this.ignoreJspTag;
    }
}
