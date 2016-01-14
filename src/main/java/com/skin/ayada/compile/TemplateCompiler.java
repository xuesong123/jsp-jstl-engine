/*
 * $RCSfile: TemplateCompiler.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-19 $
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
import com.skin.ayada.factory.TagFactoryManager;
import com.skin.ayada.io.StringStream;
import com.skin.ayada.jstl.TagInfo;
import com.skin.ayada.jstl.TagLibrary;
import com.skin.ayada.jstl.TagLibraryManager;
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
import com.skin.ayada.statement.TagNode;
import com.skin.ayada.statement.TextNode;
import com.skin.ayada.statement.Variable;
import com.skin.ayada.template.Template;
import com.skin.ayada.util.HtmlUtil;
import com.skin.ayada.util.NodeUtil;
import com.skin.ayada.util.Stack;

/**
 * <p>Title: TemplateCompiler</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class TemplateCompiler extends PageCompiler {
    private SourceFactory sourceFactory;
    private TagLibrary tagLibrary = null;
    private boolean ignoreJspTag = true;
    private List<Source> dependencies;

    public static final String JSP_DIRECTIVE_PAGE    = "jsp:directive.page";
    public static final String JSP_DIRECTIVE_TAGLIB  = "jsp:directive.taglib";
    public static final String JSP_DIRECTIVE_INCLUDE = "jsp:directive.include";
    public static final String JSP_DECLARATION       = "jsp:declaration";
    public static final String JSP_SCRIPTLET         = "jsp:scriptlet";
    public static final String JSP_EXPRESSION        = "jsp:expression";

    public static final String TPL_DIRECTIVE_TAGLIB  = "t:taglib";
    public static final String TPL_DIRECTIVE_IMPORT  = "t:import";
    public static final String TPL_DIRECTIVE_RENAME  = "t:rename";
    public static final String TPL_DIRECTIVE_INCLUDE = "t:include";
    public static final String TPL_DIRECTIVE_TEXT    = "t:text";
    public static final String TPL_DIRECTIVE_COMMENT = "t:comment";
    private static final Logger logger = LoggerFactory.getLogger(TemplateCompiler.class);

    /**
     * @param sourceFactory
     */
    public TemplateCompiler(SourceFactory sourceFactory) {
        this.sourceFactory = sourceFactory;
        this.ignoreJspTag = TemplateConfig.getIgnoreJspTag();
    }

    /**
     * @param path
     * @param encoding
     * @return Template
     */
    public Template compile(String path, String encoding) throws Exception {
        long t1 = System.currentTimeMillis();
        Source source = this.getSourceFactory().getSource(path, encoding);
        long t2 = System.currentTimeMillis();

        if(logger.isDebugEnabled()) {
            logger.debug("load source: " + (t2 - t1));
        }

        if(source == null) {
            throw new NullPointerException("source \"" + path + "\" not exists !");
        }
        return this.compile(source);
    }

    /**
     * @param source
     * @return Template
     * @throws Exception
     */
    public Template compile(Source source) throws Exception {
        this.addDependency(source);

        if(source.getType() == Source.STATIC) {
            TextNode textNode = new TextNode();
            textNode.setLineNumber(this.lineNumber);
            textNode.setOffset(0);
            textNode.setLength(1);
            textNode.setParent(null);
            textNode.append(source.getSource());
            List<Node> list = new ArrayList<Node>();
            list.add(textNode);
            return this.getTemplate(source, list, this.tagLibrary);
        }

        int i;
        long t1 = System.currentTimeMillis();
        Stack<Node> stack = new Stack<Node>();
        List<Node> list = new ArrayList<Node>();
        this.lineNumber = 1;
        this.stream = new StringStream(source.getSource());
        this.skipWhitespace();

        while((i = this.stream.read()) != -1) {
            if(i == '<') {
                this.startTag(stack, list);
            }
            else if(i == '$' && this.stream.peek() == '{') {
                i = this.stream.read();
                String flag = null;
                int ln = this.lineNumber;
                String expression = this.readExpression();
                String temp = this.ltrim(expression);

                if(temp.startsWith("?")) {
                    this.pushTextNode(stack, list, "${" + temp.substring(1) + "}", this.lineNumber);
                    continue;
                }

                if(temp.startsWith("#") || temp.startsWith("&")) {
                    flag = temp.substring(0, 1);
                    temp = temp.substring(1);
                }

                temp = temp.trim();

                if(temp.length() > 0) {
                    if(this.isJavaIdentifier(temp)) {
                        Variable variable = new Variable();
                        variable.setParent(stack.peek());
                        variable.setOffset(list.size());
                        variable.setLength(1);
                        variable.setLineNumber(ln);
                        variable.setFlag(flag);
                        variable.append(temp);
                        list.add(variable);
                    }
                    else {
                        Expression expr = new Expression();
                        expr.setParent(stack.peek());
                        expr.setOffset(list.size());
                        expr.setLength(1);
                        expr.setLineNumber(ln);
                        expr.setFlag(flag);
                        expr.append(temp);
                        list.add(expr);
                    }
                }
            }
            else {
                this.stream.back();
                int ln = this.lineNumber;
                String text = this.readText();

                if(text.length() > 0) {
                    this.pushTextNode(stack, list, text, ln);
                }
            }
        }

        if(stack.peek() != null) {
            Node node = stack.peek();
            throw new Exception("Exception at line #" + node.getLineNumber() + " " + NodeUtil.getDescription(node) + " not match !");
        }

        if(logger.isDebugEnabled()) {
            long t2 = System.currentTimeMillis();
            Template template = this.getTemplate(source, list, this.tagLibrary);
            long t3 = System.currentTimeMillis();

            logger.debug("tpl compile time: " + (t2 - t1));
            logger.debug("create tagFactory: " + (t3 - t2));
            return template;
        }
        return this.getTemplate(source, list, this.tagLibrary);
    }

    /**
     * @param stack
     * @param list
     */
    public void startTag(Stack<Node> stack, List<Node> list) throws Exception {
        int n = this.stream.read();

        if(n == '/') {
            String nodeName = this.getNodeName();

            if(nodeName.length() > 0) {
                if(this.isDirective(nodeName)) {
                    throw new Exception("at line " + this.lineNumber + ": " + nodeName + " not match !");
                }

                TagInfo tagInfo = this.tagLibrary.getTagInfo(nodeName);

                if(tagInfo != null) {
                    int i = 0;
                    while((i = this.stream.read()) != -1) {
                        if(i == '>') {
                            break;
                        }
                        else if(i == '\n') {
                            this.lineNumber++;
                        }
                    }
                    this.popNode(stack, list, nodeName);
                }
                else {
                    this.pushTextNode(stack, list, "</" + nodeName, this.lineNumber);
                }
            }
            else {
                this.pushTextNode(stack, list, "</", this.lineNumber);
            }
        }
        else if(n == '%') {
            this.jspCompile(stack, list);
        }
        else if(n == '!') {
            this.pushTextNode(stack, list, "<!", this.lineNumber);
        }
        else {
            this.stream.back();
            String nodeName = this.getNodeName();

            if(nodeName.equals(TPL_DIRECTIVE_TAGLIB)) {
                if(list.size() > 0) {
                    this.clip(list.get(list.size() - 1), 1);
                }

                Map<String, String> attributes = this.getAttributes();

                if(this.stream.peek(-2) != '/') {
                    throw new Exception("The 't:taglib' direction must be self-closed!");
                }

                String prefix = attributes.get("prefix");
                String uri = attributes.get("uri");
                this.loadTagLibrary(prefix, uri);
                this.skipLine();
                return;
            }
            else if(nodeName.equals(TPL_DIRECTIVE_IMPORT)) {
                if(list.size() > 0) {
                    this.clip(list.get(list.size() - 1), 1);
                }

                Map<String, String> attributes = this.getAttributes();

                if(this.stream.peek(-2) != '/') {
                    throw new Exception("The 't:import' direction must be self-closed!");
                }

                String name = attributes.get("name");
                String className = attributes.get("className");
                String bodyContent = attributes.get("bodyContent");
                String ignoreWhitespace = attributes.get("ignoreWhitespace");
                String description = attributes.get("description");
                this.setupTagLibrary(name, className, bodyContent, ignoreWhitespace, description);
                this.skipLine();
                return;
            }
            else if(nodeName.equals(TPL_DIRECTIVE_RENAME)) {
                if(list.size() > 0) {
                    this.clip(list.get(list.size() - 1), 1);
                }

                Map<String, String> attributes = this.getAttributes();

                if(this.stream.peek(-2) != '/') {
                    throw new Exception("The 't:rename' direction must be self-closed!");
                }

                String from = attributes.get("from");
                String name = attributes.get("name");
                this.rename(from, name);
                this.skipLine();
                return;
            }
            else if(nodeName.equals(TPL_DIRECTIVE_INCLUDE)) {
                if(list.size() > 0) {
                    this.clip(list.get(list.size() - 1), 1);
                }

                Map<String, String> attributes = this.getAttributes();

                if(this.stream.peek(-2) != '/') {
                    throw new Exception("The 't:include' direction must be self-closed!");
                }

                String file = attributes.get("file");
                String type = attributes.get("type");
                String encoding = attributes.get("encoding");
                this.include(stack, list, file, type, encoding);
                this.skipLine();
                return;
            }
            else if(nodeName.equals(TPL_DIRECTIVE_TEXT)) {
                if(list.size() > 0) {
                    this.clip(list.get(list.size() - 1), 1);
                }

                int line = this.lineNumber;
                Map<String, String> attributes = this.getAttributes();
                String escape = attributes.get("escape");
                String content = this.readNodeContent(nodeName);

                if("xml".equals(escape)) {
                    content = HtmlUtil.encode(content);
                }
                this.pushTextNode(stack, list, content, line);
                this.skipLine();
                return;
            }
            else if(nodeName.equals(TPL_DIRECTIVE_COMMENT)) {
                if(list.size() > 0) {
                    this.clip(list.get(list.size() - 1), 1);
                }

                this.getAttributes();
                this.readNodeContent(nodeName);
                this.skipLine();
                return;
            }

            String tagClassName = this.tagLibrary.getTagClassName(nodeName);

            if(nodeName.equals(JSP_DIRECTIVE_PAGE) || nodeName.equals(JSP_DIRECTIVE_TAGLIB) || nodeName.equals(JSP_DIRECTIVE_INCLUDE)) {
                int ln = this.lineNumber;
                Map<String, String> attributes = this.getAttributes();

                if(this.stream.peek(-2) != '/') {
                    throw new Exception("The 'jsp:directive' direction must be self-closed!");
                }

                JspDirective node = JspDirective.getInstance(nodeName);
                node.setLineNumber(ln);
                node.setOffset(list.size());
                node.setAttributes(attributes);
                node.setClosed(NodeType.SELF_CLOSED);
                this.pushNode(stack, list, node);
                this.popNode(stack, list, nodeName);
            }
            else if(nodeName.equals(JSP_DECLARATION)) {
                int ln = this.lineNumber;
                Map<String, String> attributes = this.getAttributes();
                JspDeclaration node = new JspDeclaration();
                node.setOffset(list.size());
                node.setAttributes(attributes);
                node.setClosed(NodeType.PAIR_CLOSED);
                node.setLineNumber(ln);
                node.append(this.readNodeContent(nodeName));
                this.pushNode(stack, list, node);
                this.popNode(stack, list, nodeName);
            }
            else if(nodeName.equals(JSP_SCRIPTLET)) {
                int ln = this.lineNumber;
                Map<String, String> attributes = this.getAttributes();
                JspScriptlet node = new JspScriptlet();
                node.setOffset(list.size());
                node.setAttributes(attributes);
                node.setClosed(NodeType.PAIR_CLOSED);
                node.setLineNumber(ln);
                node.append(this.readNodeContent(nodeName));
                this.pushNode(stack, list, node);
                this.popNode(stack, list, nodeName);
            }
            else if(nodeName.equals(JSP_EXPRESSION)) {
                int ln = this.lineNumber;
                Map<String, String> attributes = this.getAttributes();
                JspExpression node = new JspExpression();
                node.setOffset(list.size());
                node.setAttributes(attributes);
                node.setClosed(NodeType.PAIR_CLOSED);
                node.setLineNumber(ln);
                node.append(this.readNodeContent(nodeName));
                this.pushNode(stack, list, node);
                this.popNode(stack, list, nodeName);
            }
            else if(tagClassName != null) {
                int ln = this.lineNumber;
                Map<String, String> attributes = this.getAttributes();
                TagNode tagNode = new TagNode(nodeName);
                tagNode.setLineNumber(ln);
                tagNode.setOffset(list.size());
                tagNode.setAttributes(attributes);
                tagNode.setClosed(NodeType.PAIR_CLOSED);
                tagNode.setTagClassName(tagClassName);
                this.pushNode(stack, list, tagNode);
                boolean isEnd = (this.stream.peek(-2) == '/');

                if(isEnd) {
                    tagNode.setLength(2);
                    tagNode.setClosed(NodeType.SELF_CLOSED);
                    this.popNode(stack, list, nodeName);
                }
            }
            else {
                this.pushTextNode(stack, list, "<" + nodeName, this.lineNumber);
            }
        }
    }

    /**
     * @param buffer
     */
    private String readText() {
        int i = 0;
        StringBuilder buffer = new StringBuilder();

        while((i = this.stream.read()) != -1) {
            if(i == '<' || i == '$') {
                this.stream.back();
                break;
            }
            else {
                if(i == '\n') {
                    this.lineNumber++;
                }
                buffer.append((char)i);
            }
        }
        return buffer.toString();
    }

    /**
     * @return String
     */
    private String readExpression() {
        int i = 0;
        StringBuilder buffer = new StringBuilder();

        while((i = this.stream.read()) != -1) {
            if(i == '}') {
                break;
            }
            else {
                if(i == '\n') {
                    this.lineNumber++;
                }
                buffer.append((char)i);
            }
        }
        return buffer.toString();
    }

    /**
     * @param stack
     * @param list
     */
    private void jspCompile(Stack<Node> stack, List<Node> list) throws Exception {
        int i = this.stream.read();

        if(i == -1) {
            throw new Exception("at line #" + this.lineNumber + " Invalid jsp tag !");
        }

        if(i == '@') {
            if(list.size() > 0) {
                this.clip(list.get(list.size() - 1), 1);
            }

            String nodeName = null;
            int ln = this.getLineNumber();
            Map<String, String> attributes = this.getAttributes();

            if(this.stream.peek(-2) != '%' && this.stream.peek(-1) != '%') {
                throw new Exception("at line #" + this.lineNumber + " The 'jsp:directive' direction must be ends with '%>'");
            }

            if(attributes.get("page") != null) {
                nodeName = NodeType.JSP_DIRECTIVE_PAGE_NAME;
                attributes.remove("page");
            }
            else if(attributes.get("taglib") != null) {
                nodeName = NodeType.JSP_DIRECTIVE_TAGLIB_NAME;
            }
            else if(attributes.get("include") != null) {
                nodeName = NodeType.JSP_DIRECTIVE_INCLUDE_NAME;
                String path = attributes.get("file");
                this.include(stack, list, path, null, null);
            }
            else {
                throw new Exception("Unknown jsp directive at line #" + this.lineNumber + " - <%@ " + NodeUtil.toString(attributes) + "%>");
            }

            JspDirective node = JspDirective.getInstance(nodeName);
            node.setOffset(list.size());
            node.setLineNumber(ln);
            node.setAttributes(attributes);
            node.setClosed(NodeType.SELF_CLOSED);
            this.pushNode(stack, list, node);
            this.popNode(stack, list, nodeName);
        }
        else if(i == '!'){
            int ln = this.lineNumber;
            String scriptlet = this.readScriptlet();

            if(list.size() > 0) {
                this.clip(list.get(list.size() - 1), 1);
            }

            JspDeclaration node = new JspDeclaration();
            node.append(scriptlet);
            node.setOffset(list.size());
            node.setLineNumber(ln);
            node.setClosed(NodeType.PAIR_CLOSED);
            this.pushNode(stack, list, node);
            this.popNode(stack, list, node.getNodeName());
        }
        else if(i == '=') {
            int ln = this.lineNumber;
            String expression = this.readScriptlet();

            if(!this.isEmpty(expression)) {
                JspExpression node = new JspExpression();
                node.append(expression);
                node.setOffset(list.size());
                node.setLineNumber(ln);
                node.setClosed(NodeType.PAIR_CLOSED);
                this.pushNode(stack, list, node);
                this.popNode(stack, list, node.getNodeName());
            }
            else {
                throw new Exception("at line #" + this.lineNumber + " Invalid jsp expression !");
            }
        }
        else {
            this.stream.back();
            int ln = this.lineNumber;
            String scriptlet = this.readScriptlet();

            if(list.size() > 0) {
                this.clip(list.get(list.size() - 1), 1);
            }

            JspScriptlet node = new JspScriptlet();
            node.append(scriptlet);
            node.setOffset(list.size());
            node.setLineNumber(ln);
            node.setClosed(NodeType.PAIR_CLOSED);
            this.pushNode(stack, list, node);
            this.popNode(stack, list, node.getNodeName());
        }
    }

    /**
     * @param nodeName
     * @return boolean
     */
    private boolean isDirective(String nodeName) {
        if(nodeName.startsWith("t:")) {
            return (nodeName.equals(TPL_DIRECTIVE_TAGLIB)
                    || nodeName.equals(TPL_DIRECTIVE_IMPORT)
                    || nodeName.equals(TPL_DIRECTIVE_INCLUDE)
                    || nodeName.equals(TPL_DIRECTIVE_TEXT)
                    || nodeName.equals(TPL_DIRECTIVE_COMMENT));
        }
        return false;
    }

    /**
     * @param source
     */
    public String ltrim(String source) {
        if(source == null) {
            return "";
        }

        int i = 0;
        int length = source.length();

        while(i < length && source.charAt(i) <= ' ') {
            i++;
        }
        return (i > 0 ? source.substring(i) : source);
    }

    /**
     * @param content
     * @return boolean
     */
    public boolean isEmpty(String content) {
        int i = 0;
        int length = content.length();

        while(i < length && content.charAt(i) <= ' ') {
            i++;
        }
        return (i >= length);
    }

    /**
     * @param content
     * @return boolean
     */
    public boolean isEmpty(StringBuilder content) {
        int i = 0;
        int length = content.length();

        while(i < length && content.charAt(i) <= ' ') {
            i++;
        }
        return (i >= length);
    }

    /**
     * skip line
     */
    public void skipLine() {
        int i = -1;

        while((i = this.stream.read()) != -1) {
            if(i == '\n') {
                this.lineNumber++;
                break;
            }
        }
    }

    /**
     * skip crlf
     */
    public void skipCRLF() {
        int i = -1;

        while((i = this.stream.peek()) != -1) {
            if(i == '\r') {
                this.stream.read();
                continue;
            }

            if(i == '\n') {
                this.lineNumber++;
                this.stream.read();
                continue;
            }
            else {
                break;
            }
        }
    }

    /**
     * skip whitespace
     */
    public void skipWhitespace() {
        int i;
        while((i = this.stream.peek()) != -1) {
            if(i <= ' ') {
                if(i == '\n') {
                    this.lineNumber++;
                }

                this.stream.read();
                continue;
            }
            else {
                break;
            }
        }
    }

    /**
     * @return String
     */
    public String readScriptlet() {
        int i = 0;
        int ln = this.lineNumber;
        StringBuilder buffer = new StringBuilder();

        this.skipCRLF();
        while((i = this.stream.read()) != -1) {
            if(i == '%' && this.stream.peek() == '>') {
                this.stream.read();
                break;
            }
            if(i == '\n') {
                this.lineNumber++;
            }
            buffer.append((char)i);
        }

        if(this.stream.peek(-2) != '%' && this.stream.peek(-1) != '%') {
            throw new RuntimeException("at line #" + ln + " The 'jsp:directive' direction must be ends with '%>'");
        }
        return buffer.toString();
    }

    /**
     * @param nodeName
     * @return String
     */
    public String readNodeContent(String nodeName) {
        int i = 0;
        int offset = this.stream.getPosition();
        int end = this.stream.length();

        while((i = this.stream.read()) != -1) {
            if(i == '<' && this.stream.peek() == '/') {
                this.stream.read();

                if(this.match(nodeName)) {
                    end = this.stream.getPosition() - 2;
                    this.stream.skip(nodeName.length());

                    while((i = this.stream.read()) != -1) {
                        if(i == '\n') {
                            this.lineNumber++;
                        }

                        if(i == '>') {
                            break;
                        }
                    }
                    break;
                }
            }
            else {
                if(i == '\n') {
                    this.lineNumber++;
                }
            }
        }
        return this.stream.getString(offset, end - offset);
    }

    /**
     * @param nodeName
     * @return String
     */
    public String readNodeContent2(String nodeName) {
        int i = 0;
        int depth = 0;
        int offset = this.stream.getPosition();
        int end = this.stream.length();

        while((i = this.stream.read()) != -1) {
            if(i == '<') {
                if(this.stream.peek() == '/') {
                    this.stream.read();

                    if(this.match(nodeName)) {
                        if(depth == 0) {
                            end = this.stream.getPosition() - 2;
                            this.stream.skip(nodeName.length());
                            while((i = this.stream.read()) != -1) {
                                if(i == '\n') {
                                    this.lineNumber++;
                                }

                                if(i == '>') {
                                    break;
                                }
                            }
                            break;
                        }
                        else {
                            this.stream.skip(nodeName.length());
                            while((i = this.stream.read()) != -1) {
                                if(i == '\n') {
                                    this.lineNumber++;
                                }

                                if(i == '>') {
                                    break;
                                }
                            }
                            depth--;
                        }
                    }
                }
                else {
                    if(this.match(nodeName)) {
                        depth++;
                    }
                }
            }
            else {
                if(i == '\n') {
                    this.lineNumber++;
                }
            }
        }
        return this.stream.getString(offset, end - offset);
    }

    /**
     * @param nodeName
     * @return boolean
     */
    public boolean match(String nodeName) {
        int i = 0;
        int length = nodeName.length();

        for(i = 0; i < length; i++) {
            if(this.stream.peek(i) != nodeName.charAt(i)) {
                return false;
            }
        }

        int c = this.stream.peek(i);

        if(c == ' ' || c == '\r' || c == '\n' || c == '\t' || c == '/' || c == '>') {
            return true;
        }
        return false;
    }

    /**
     * @param stack
     * @param list
     * @param node
     */
    private void pushNode(Stack<Node> stack, List<Node> list, Node node) {
        Node parent = stack.peek();

        if(parent != null) {
            node.setParent(parent);
        }

        stack.push(node);
        list.add(node);

        if(logger.isDebugEnabled()) {
            logger.debug("[push][node] parent: " + (parent != null ? parent.getNodeName() : "null") + ", nodeName: [" + node.getNodeName() + "]");
        }
    }

    /**
     * @param stack
     * @param list
     * @param nodeName
     */
    private void popNode(Stack<Node> stack, List<Node> list, String nodeName) throws Exception {
        Node node = stack.peek();

        if(node == null) {
            throw new Exception("Exception at line #" + this.lineNumber + ": </" + nodeName + "> not match !");
        }

        if(node.getNodeName().equalsIgnoreCase(nodeName)) {
            stack.pop();
            node.setLength(list.size() - node.getOffset() + 1);
            list.add(node);

            if(logger.isDebugEnabled()) {
                Node parent = node.getParent();
                logger.debug("[pop ][node] parent: " + (parent != null ? parent.getNodeName() : "null") + ", nodeName: [/" + node.getNodeName() + "]");
            }
        }
        else {
            throw new Exception("Exception at line #" + node.getLineNumber() + " " + NodeUtil.getDescription(node) + " not match !");
        }
    }

    /**
     * @param stack
     * @param list
     * @param text
     * @param lineNumber
     */
    private void pushTextNode(Stack<Node> stack, List<Node> list, String text, int lineNumber) {
        TextNode textNode = null;
        Node parent = stack.peek();
        int size = list.size();

        if(size > 0) {
            Node node = list.get(size - 1);

            if(node instanceof TextNode) {
                textNode = (TextNode)node;
            }
            else {
                textNode = new TextNode();
                textNode.setLineNumber(lineNumber);
                textNode.setOffset(size);
                textNode.setLength(1);
                list.add(textNode);
            }
        }
        else {
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
     * @param type
     * @param path
     * @param charset
     * @throws Exception
     */
    public void include(Stack<Node> stack, List<Node> list, String path, String type, String charset) throws Exception {
        if(path == null) {
            throw new Exception("t:include error: attribute 'file' not exists !");
        }

        if(path.charAt(0) != '/') {
            throw new Exception("t:include error: file must be starts with '/'");
        }

        int index = list.size();
        Node parent = stack.peek();
        String encoding = (charset != null ? charset : "UTF-8");
        Source source = this.getSourceFactory().getSource(path, encoding);
        int sourceType = Source.valueOf(type, source.getType());

        if(sourceType == Source.STATIC) {
            TextNode textNode = new TextNode();
            textNode.setLineNumber(this.lineNumber);
            textNode.setOffset(list.size());
            textNode.setLength(1);
            textNode.setParent(parent);
            textNode.append(source.getSource());
            list.add(textNode);
            this.addDependency(source);
            return;
        }

        source.setType(sourceType);
        TemplateCompiler compiler = new TemplateCompiler(this.sourceFactory);
        compiler.setTagLibrary(this.getTagLibrary());
        Template template = compiler.compile(source);
        List<Node> nodes = template.getNodes();

        for(Node node : nodes) {
            node.setOffset(-1);
        }

        for(Node node : nodes) {
            if(node.getOffset() == -1) {
                node.setOffset(index);
            }

            if(node.getParent() == null) {
                node.setParent(parent);
            }

            list.add(node);
            index++;
        }
        this.getDependencies().addAll(compiler.getDependencies());
    }

    /**
     * @param prefix
     * @param uri
     */
    public void loadTagLibrary(String prefix, String uri) throws Exception {
        if(prefix == null || prefix.trim().length() < 1) {
            throw new NullPointerException("prefix must be not null !");
        }

        if(uri == null || uri.trim().length() < 1) {
            throw new NullPointerException("uri must be not null !");
        }

        TagLibrary tagLibrary = this.getTagLibrary();
        Map<String, TagInfo> library = TagLibraryManager.getTagLibrary(prefix, uri);
        tagLibrary.setup(library);
    }

    /**
     * @param tagName
     * @param className
     */
    protected void setupTagLibrary(String tagName, String className, String bodyContent, String ignoreWhitespace, String description) {
        if(tagName == null || tagName.length() < 1) {
            return;
        }

        String tagClassName = className;
        TagLibrary tagLibrary = this.getTagLibrary();

        if(tagClassName == null || tagClassName.length() < 1) {
            tagClassName = tagLibrary.getTagClassName(tagName);
        }

        if(tagClassName == null) {
            return;
        }

        if(logger.isDebugEnabled()) {
            logger.debug("import: {}, class: {}, bodyContent: {}, ignoreWhitespace: {}, description: {}",
                    tagName, tagClassName, bodyContent, ignoreWhitespace, description);
        }
        tagLibrary.setup(tagName, tagClassName, bodyContent, ignoreWhitespace, description);
    }

    /**
     * @param name
     * @param from
     */
    protected void rename(String from, String name) {
        TagLibrary tagLibrary = this.getTagLibrary();
        tagLibrary.rename(from, name);
    }

    /**
     * @param list
     * @param tagLibrary
     * @return Template
     */
    public Template getTemplate(Source source, List<Node> list, TagLibrary tagLibrary) throws Exception {
        List<Node> nodes = this.compact(list, tagLibrary);
        TagFactoryManager tagFactoryManager = TagFactoryManager.getInstance();

        for(int i = 0, size = nodes.size(); i < size; i++) {
            Node node = nodes.get(i);

            if(node.getNodeType() == NodeType.TAG_NODE && i == node.getOffset()) {
                if(node.getLength() == 0) {
                    throw new Exception("Exception at line #" + node.getLineNumber() + " " + NodeUtil.getDescription(node) + " not match !");
                }

                TagNode tagNode = (TagNode)node;
                String tagName = node.getNodeName();
                String className = tagLibrary.getTagClassName(tagName);
                TagFactory tagFactory = tagFactoryManager.getTagFactory(tagName, className);
                tagNode.setTagFactory(tagFactory);
            }
        }

        Template template = new Template(source.getHome(), source.getPath(), nodes);
        template.setLastModified(source.getLastModified());
        template.setUpdateTime(System.currentTimeMillis());
        template.setDependencies(this.getDependencies());
        return template;
    }

    /**
     * @param list
     * @param tagLibrary
     * @return List<Node>
     */
    public List<Node> compact(List<Node> list, TagLibrary tagLibrary) throws Exception {
        this.clip(list, tagLibrary);
        List<Node> nodes = new ArrayList<Node>();

        for(int i = 0, size = list.size(); i < size; i++) {
            Node node = list.get(i);
            int nodeType = node.getNodeType();

            if(nodeType == NodeType.TEXT || nodeType == NodeType.EXPRESSION || nodeType == NodeType.VARIABLE) {
                if(((DataNode)node).length() > 0) {
                    node.setOffset(nodes.size());
                    node.setLength(1);
                    nodes.add(node);
                }
            }
            else if(nodeType == NodeType.JSP_DIRECTIVE_PAGE
                || nodeType == NodeType.JSP_DIRECTIVE_TAGLIB
                || nodeType == NodeType.JSP_DIRECTIVE_INCLUDE
                || nodeType == NodeType.JSP_DECLARATION
                || nodeType == NodeType.JSP_SCRIPTLET
                || nodeType == NodeType.JSP_EXPRESSION) {

                if(this.ignoreJspTag) {
                    if(i == node.getOffset()) {
                        i = i + node.getLength() - 1;
                    }
                }
                else {
                    if(i == node.getOffset()) {
                        node.setOffset(nodes.size());
                        node.setLength(2);
                    }
                    nodes.add(node);
                }
            }
            else {
                if(i == node.getOffset()) {
                    TagInfo tagInfo = tagLibrary.getTagInfo(node.getNodeName());

                    if(tagInfo != null) {
                        int bodyContent = tagInfo.getBodyContent();

                        if(bodyContent == TagInfo.TAGDEPENDENT) {
                            // clear TextNode
                            for(int j = i + 1, length = i + node.getLength() - 1; j < length; j++) {
                                Node n = list.get(j);

                                if(n instanceof DataNode) {
                                    ((DataNode)n).clear();
                                }
                                else {
                                    j = j + n.getLength() - 1;
                                }
                            }
                        }
                        else if(bodyContent == TagInfo.EMPTY) {
                            // jump to node end
                            i = i + node.getLength() - 2;
                        }
                    }
                    node.setOffset(nodes.size());
                }
                else {
                    node.setLength(nodes.size() - node.getOffset() + 1);

                    if(node.getLength() == 1) {
                        throw new Exception("length == 1");
                    }
                }
                nodes.add(node);
            }
        }
        return nodes;
    }

    /**
     * @param list
     * @param tagLibrary
     * @throws Exception
     */
    public void clip(List<Node> list, TagLibrary tagLibrary) throws Exception {
        for(int i = 0, size = list.size(); i < size; i++) {
            Node node = list.get(i);
            int nodeType = node.getNodeType();

            if(nodeType == NodeType.TAG_NODE) {
                TagInfo tagInfo = tagLibrary.getTagInfo(node.getNodeName());

                if(tagInfo.getIgnoreWhitespace()) {
                    if(i > 0) {
                        this.clip(list.get(i - 1), 1);
                    }

                    if(i + 1 < size) {
                        this.clip(list.get(i + 1), 2);
                    }
                }
            }
            else if(nodeType == NodeType.JSP_DECLARATION
                    || nodeType == NodeType.JSP_SCRIPTLET
                    || nodeType == NodeType.JSP_DIRECTIVE_PAGE
                    || nodeType == NodeType.JSP_DIRECTIVE_TAGLIB
                    || nodeType == NodeType.JSP_DIRECTIVE_INCLUDE) {
                if(i > 0) {
                    this.clip(list.get(i - 1), 1);
                }

                if(i + 1 < size) {
                    this.clip(list.get(i + 1), 2);
                }
            }
            else {
                continue;
            }
        }
    }

    /**
     * type == 1 prefix clip
     * type == 2 suffix clip
     * @param node
     * @param type
     */
    private void clip(Node node, int type) {
        if(node.getNodeType() != NodeType.TEXT) {
            return;
        }

        if(type == 1) {
            char c;
            int j = 0;
            StringBuilder buffer = ((TextNode)(node)).getBuffer();

            for(j = buffer.length() - 1; j > -1; j--) {
                c = buffer.charAt(j);

                if(c == ' ' || c == '\t') {
                    continue;
                }
                else {
                    break;
                }
            }
            buffer.setLength(j + 1);
        }
        else {
            char c;
            int j = 0;
            StringBuilder buffer = ((TextNode)(node)).getBuffer();

            for(int length = buffer.length(); j < length; j++) {
                c = buffer.charAt(j);

                if(c == '\r') {
                    continue;
                }
                else if(c == '\n') {
                    j++;
                    break;
                }
                else {
                    break;
                }
            }
            buffer.delete(0, j);
        }
    }

    /**
     * @return the sourceFactory
     */
    public SourceFactory getSourceFactory() {
        return this.sourceFactory;
    }

    /**
     * @param sourceFactory the sourceFactory to set
     */
    public void setSourceFactory(SourceFactory sourceFactory) {
        this.sourceFactory = sourceFactory;
    }

    /**
     * @return TagLibrary
     */
    public TagLibrary getTagLibrary() {
        return this.tagLibrary;
    }

    /**
     * @param tagLibrary
     */
    public void setTagLibrary(TagLibrary tagLibrary) {
        this.tagLibrary = tagLibrary;
    }

    /**
     * @param ignoreJspTag the ignoreJspTag to set
     */
    public void setIgnoreJspTag(boolean ignoreJspTag) {
        this.ignoreJspTag = ignoreJspTag;
    }

    /**
     * @return the ignoreJspTag
     */
    public boolean getIgnoreJspTag() {
        return this.ignoreJspTag;
    }

    /**
     * @return the dependencies
     */
    public List<Source> getDependencies() {
        return this.dependencies;
    }

    /**
     * @param dependencies the dependencies to set
     */
    public void setDependencies(List<Source> dependencies) {
        this.dependencies = dependencies;
    }

    /**
     * @param dependency
     */
    public void addDependency(Source dependency) {
        if(dependency != null) {
            if(this.dependencies == null) {
                this.dependencies = new ArrayList<Source>();
            }

            this.dependencies.add(dependency);
        }
    }
}
