/*
 * $RCSfile: JspParser.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-02-19 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.compile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skin.ayada.Source;
import com.skin.ayada.SourceFactory;
import com.skin.ayada.TagFactory;
import com.skin.ayada.Template;
import com.skin.ayada.config.TemplateConfig;
import com.skin.ayada.factory.TagFactoryManager;
import com.skin.ayada.jstl.TagInfo;
import com.skin.ayada.jstl.TagLibrary;
import com.skin.ayada.jstl.TagLibraryManager;
import com.skin.ayada.statement.AttributeList;
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
import com.skin.ayada.util.DateUtil;
import com.skin.ayada.util.HtmlUtil;
import com.skin.ayada.util.NodeUtil;
import com.skin.ayada.util.Path;
import com.skin.ayada.util.Stack;
import com.skin.ayada.util.StringUtil;

/**
 * <p>Title: JspParser</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class JspParser extends Parser {
    private SourceFactory sourceFactory;
    private TagLibrary tagLibrary = null;
    private boolean ignoreJspTag = true;
    private List<Source> dependencies;

    protected static final String JSP_DIRECTIVE_PAGE    = "jsp:directive.page";
    protected static final String JSP_DIRECTIVE_TAGLIB  = "jsp:directive.taglib";
    protected static final String JSP_DIRECTIVE_INCLUDE = "jsp:directive.include";
    protected static final String JSP_DECLARATION       = "jsp:declaration";
    protected static final String JSP_SCRIPTLET         = "jsp:scriptlet";
    protected static final String JSP_EXPRESSION        = "jsp:expression";

    protected static final String TPL_DIRECTIVE_TAGLIB  = "t:taglib";
    protected static final String TPL_DIRECTIVE_IMPORT  = "t:import";
    protected static final String TPL_DIRECTIVE_RENAME  = "t:rename";
    protected static final String TPL_DIRECTIVE_INCLUDE = "t:include";
    protected static final String TPL_DIRECTIVE_TEXT    = "t:text";
    protected static final String TPL_DIRECTIVE_COMMENT = "t:comment";
    private static final Logger logger = LoggerFactory.getLogger(JspParser.class);

    /**
     * @param sourceFactory
     */
    public JspParser(SourceFactory sourceFactory) {
        this.sourceFactory = sourceFactory;
        this.ignoreJspTag = TemplateConfig.getIgnoreJspTag();
    }

    /**
     * @param path
     * @param charset
     * @return Template
     * @throws Exception
     */
    public Template parse(String path, String charset) throws Exception {
        if(logger.isDebugEnabled()) {
            logger.debug("load source: path: {}", path);
        }

        Source source = this.getSourceFactory().getSource(path);

        if(source == null) {
            throw new NullPointerException("source \"" + path + "\" not exists !");
        }

        this.addDependency(source);

        if(source.getType() == Source.STATIC) {
            String content = this.sourceFactory.getContent(source.getPath(), charset);
            TextNode textNode = new TextNode();
            textNode.setLine(1);
            textNode.setOffset(0);
            textNode.setLength(1);
            textNode.setParent(null);
            textNode.setTextContent(content);
            List<Node> list = new ArrayList<Node>();
            list.add(textNode);
            return this.getTemplate(source, list, this.tagLibrary);
        }
        return this.parse(source, charset);
    }

    /**
     * @param source
     * @param charset
     * @return Template
     * @throws Exception
     */
    public Template parse(Source source, String charset) throws Exception {
        InputStream inputStream = this.sourceFactory.getInputStream(source.getPath());

        try {
            int i;
            this.stream = Stream.getStream(inputStream, charset, 32);
            Stack<Node> stack = new Stack<Node>();
            List<Node> list = new ArrayList<Node>();
            this.stream.skipWhitespace();
    
            while((i = this.stream.peek()) != -1) {
                if(i == '<') {
                    this.stream.read();
                    this.parseStartTag(source, stack, list);
                }
                else if(i == '$' && this.stream.peek(1) == '{') {
                    this.stream.skip(2);
                    this.parseExpression(stack, list);
                }
                else {
                    this.parseText(stack, list);
                }
            }
    
            if(stack.peek() != null) {
                Node node = stack.peek();
                throw new Exception("Exception at line #" + node.getLine() + " " + NodeUtil.getDescription(node) + " not match !");
            }
            return this.getTemplate(source, list, this.tagLibrary);
        }
        finally {
            try {
                inputStream.close();
            }
            catch(IOException e) {
            }
        }
    }

    /**
     * @param stack
     * @param list
     * @throws IOException
     */
    private void parseText(Stack<Node> stack, List<Node> list) throws IOException {
        int line = this.stream.getLine();
        String text = this.readText();

        if(text.length() > 0) {
            this.pushTextNode(stack, list, text, line);
        }
    }

    /**
     * @param source
     * @param stack
     * @param list
     * @throws IOException
     */
    private void parseExpression(Stack<Node> stack, List<Node> list) throws IOException {
        int flag = 0;
        int line = this.stream.getLine();
        String expression = this.stream.readUntil('}');

        if(expression == null) {
            throw new RuntimeException("Exception at #" + line + ", bad expr.");
        }

        expression = expression.trim();

        if(expression.startsWith("?")) {
            this.pushTextNode(stack, list, "${" + expression.substring(1) + "}", line);
            return;
        }

        if(expression.startsWith("#") || expression.startsWith("&")) {
            flag = expression.charAt(0);
            expression = expression.substring(1).trim();
        }

        if(expression.length() > 0) {
            if(StringUtil.isJavaIdentifier(expression)) {
                Variable variable = new Variable();
                variable.setParent(stack.peek());
                variable.setOffset(list.size());
                variable.setLength(1);
                variable.setLine(line);
                variable.setFlag(flag);
                variable.setExpression(expression);
                list.add(variable);
            }
            else {
                Expression expr = new Expression();
                expr.setParent(stack.peek());
                expr.setOffset(list.size());
                expr.setLength(1);
                expr.setLine(line);
                expr.setFlag(flag);
                expr.setExpression(expression);
                list.add(expr);
            }
        }
    }

    /**
     * @param source
     * @param stack
     * @param list
     * @throws Exception
     */
    public void parseStartTag(Source source, Stack<Node> stack, List<Node> list) throws Exception {
        int n = this.stream.peek();
        int line = this.stream.getLine();

        if(n == '/') {
            this.stream.read();
            this.parseEndTag(stack, list);
        }
        else if(n == '%') {
            this.stream.read();
            this.parseJsp(source, stack, list);
        }
        else if(n == '!') {
            this.stream.read();
            this.pushTextNode(stack, list, "<!", line);
        }
        else {
            String nodeName = this.getNodeName();

            if(this.isTplDirective(nodeName)) {
                this.parseTplDirective(source, stack, list, nodeName);
            }
            else if(this.isJspDirective(nodeName)) {
                this.parseJspDirective(source, stack, list, nodeName);
            }
            else {
                this.parseJspTag(source, stack, list, nodeName);
            }
        }
    }

    /**
     * @param source
     * @param stack
     * @param list
     * @throws Exception
     */
    private void parseEndTag(Stack<Node> stack, List<Node> list) throws Exception {
        int line = this.stream.getLine();
        String nodeName = this.getNodeName();

        if(nodeName.length() > 0) {
            if(this.isTplDirective(nodeName)) {
                throw new Exception("at line " + line + ": " + nodeName + " not match !");
            }

            TagInfo tagInfo = this.tagLibrary.getTagInfo(nodeName);

            if(tagInfo != null) {
                this.stream.readUntil('>');
                this.popNode(stack, list, nodeName);
            }
            else {
                this.pushTextNode(stack, list, "</" + nodeName, line);
            }
        }
        else {
            this.pushTextNode(stack, list, "</", line);
        }
    }

    /**
     * @param source
     * @param stack
     * @param list
     * @param nodeName
     * @throws Exception
     */
    private void parseTplDirective(Source source, Stack<Node> stack, List<Node> list, String nodeName) throws Exception {
        if(list.size() > 0) {
            this.clip(list.get(list.size() - 1), 1);
        }

        int line = this.stream.getLine();
        AttributeList attributes = this.getAttributeList();

        if(nodeName.equals(TPL_DIRECTIVE_TAGLIB)) {
            if(this.stream.read() == '/') {
                this.stream.skipUntil('>');
            }
            else {
                throw new Exception("The '" + nodeName + "' direction must be self-closed!");
            }

            String prefix = attributes.getText("prefix");
            String uri = attributes.getText("uri");
            this.loadTagLibrary(prefix, uri);
            this.stream.skipLine();
        }
        else if(nodeName.equals(TPL_DIRECTIVE_IMPORT)) {
            if(this.stream.read() == '/') {
                this.stream.skipUntil('>');
            }
            else {
                throw new Exception("The '" + nodeName + "' direction must be self-closed!");
            }

            String name = attributes.getText("name");
            String className = attributes.getText("className");
            String bodyContent = attributes.getText("bodyContent");
            String ignoreWhitespace = attributes.getText("ignoreWhitespace");
            String description = attributes.getText("description");
            this.setupTagLibrary(name, className, bodyContent, ignoreWhitespace, description);
            this.stream.skipLine();
        }
        else if(nodeName.equals(TPL_DIRECTIVE_RENAME)) {
            if(this.stream.read() == '/') {
                this.stream.skipUntil('>');
            }
            else {
                throw new Exception("The '" + nodeName + "' direction must be self-closed!");
            }

            String from = attributes.getText("from");
            String name = attributes.getText("name");
            this.rename(from, name);
            this.stream.skipLine();
        }
        else if(nodeName.equals(TPL_DIRECTIVE_INCLUDE)) {
            if(this.stream.read() == '/') {
                this.stream.skipUntil('>');
            }
            else {
                throw new Exception("The '" + nodeName + "' direction must be self-closed!");
            }

            String file = attributes.getText("file");
            String type = attributes.getText("type");
            String encoding = attributes.getText("encoding");
            this.include(stack, list, source.getPath(), file, type, encoding);
            this.stream.skipLine();
        }
        else if(nodeName.equals(TPL_DIRECTIVE_TEXT)) {
            this.stream.readUntil('>');
            String escape = attributes.getText("escape");
            String content = this.readNodeContent(nodeName);

            if("xml".equals(escape)) {
                content = HtmlUtil.encode(content);
            }
            this.pushTextNode(stack, list, content, line);
            this.stream.skipLine();
        }
        else if(nodeName.equals(TPL_DIRECTIVE_COMMENT)) {
            this.stream.readUntil('>');
            this.readNodeContent(nodeName);
            this.stream.skipLine();
        }
    }

    /**
     * @param source
     * @param stack
     * @param list
     * @param nodeName
     * @throws Exception
     */
    private void parseJspDirective(Source source, Stack<Node> stack, List<Node> list, String nodeName) throws Exception {
        int line = this.stream.getLine();
        AttributeList attributes = this.getAttributeList();

        if(nodeName.equals(JSP_DIRECTIVE_PAGE) || nodeName.equals(JSP_DIRECTIVE_TAGLIB) || nodeName.equals(JSP_DIRECTIVE_INCLUDE)) {
            if(this.stream.read() == '/') {
                this.stream.skipUntil('>');
            }
            else {
                throw new Exception("The '" + nodeName + "' direction must be self-closed!");
            }

            JspDirective node = JspDirective.getInstance(nodeName);
            node.setLine(line);
            node.setOffset(list.size());
            node.setAttributes(attributes.getAttributes());
            node.setClosed(NodeType.SELF_CLOSED);
            this.pushJspNode(stack, list, node);
        }
        else if(nodeName.equals(JSP_DECLARATION)) {
            this.stream.skipUntil('>');
            JspDeclaration node = new JspDeclaration();
            node.setOffset(list.size());
            node.setAttributes(attributes.getAttributes());
            node.setClosed(NodeType.PAIR_CLOSED);
            node.setLine(line);
            node.setTextContent(this.readNodeContent(nodeName));
            this.pushJspNode(stack, list, node);
        }
        else if(nodeName.equals(JSP_SCRIPTLET)) {
            this.stream.skipUntil('>');
            JspScriptlet node = new JspScriptlet();
            node.setOffset(list.size());
            node.setAttributes(attributes.getAttributes());
            node.setClosed(NodeType.PAIR_CLOSED);
            node.setLine(line);
            node.setTextContent(this.readNodeContent(nodeName));
            this.pushJspNode(stack, list, node);
        }
        else if(nodeName.equals(JSP_EXPRESSION)) {
            this.stream.skipUntil('>');
            JspExpression node = new JspExpression();
            node.setOffset(list.size());
            node.setAttributes(attributes.getAttributes());
            node.setClosed(NodeType.PAIR_CLOSED);
            node.setLine(line);
            node.setTextContent(this.readNodeContent(nodeName));
            this.pushJspNode(stack, list, node);
        }
    }

    /**
     * @param source
     * @param stack
     * @param list
     * @param nodeName
     * @throws Exception
     */
    private void parseJspTag(Source source, Stack<Node> stack, List<Node> list, String nodeName) throws Exception {
        String tagClassName = this.tagLibrary.getTagClassName(nodeName);

        if(tagClassName != null) {
            int line = this.stream.getLine();
            AttributeList attributes = this.getAttributeList();

            TagNode tagNode = new TagNode(nodeName);
            tagNode.setLine(line);
            tagNode.setOffset(list.size());
            tagNode.setAttributes(attributes.getAttributes());
            tagNode.setClosed(NodeType.PAIR_CLOSED);
            tagNode.setTagClassName(tagClassName);
            this.pushNode(stack, list, tagNode);

            if(this.stream.peek() == '/') {
                this.stream.read();
                tagNode.setLength(2);
                tagNode.setClosed(NodeType.SELF_CLOSED);
                this.popNode(stack, list, nodeName);
            }
            this.stream.readUntil('>');
        }
        else {
            int line = this.stream.getLine();
            this.pushTextNode(stack, list, "<" + nodeName, line);
        }
    }

    /**
     * @param source
     * @param stack
     * @param list
     * @throws Exception
     */
    private void parseJsp(Source source, Stack<Node> stack, List<Node> list) throws Exception {
        int i = this.stream.peek();

        if(i == '@') {
            this.stream.read();

            if(list.size() > 0) {
                this.clip(list.get(list.size() - 1), 1);
            }

            String nodeName = null;
            int line = this.stream.getLine();
            AttributeList attributes = this.getAttributeList();

            if(this.stream.match("%>")) {
                this.stream.skip(2);
            }
            else {
                throw new Exception("at line #" + line + " The 'jsp:directive' direction must be ends with '%>'");
            }

            if(attributes.getValue("page") != null) {
                nodeName = NodeType.JSP_DIRECTIVE_PAGE_NAME;
                attributes.remove("page");
            }
            else if(attributes.getValue("taglib") != null) {
                nodeName = NodeType.JSP_DIRECTIVE_TAGLIB_NAME;
            }
            else if(attributes.getValue("include") != null) {
                nodeName = NodeType.JSP_DIRECTIVE_INCLUDE_NAME;
                String path = attributes.getText("file");
                this.include(stack, list, source.getPath(), path, null, null);
            }
            else {
                throw new Exception("Unknown jsp directive at line #" + line + " - <%@ " + NodeUtil.toString(attributes.getAttributes()) + "%>");
            }

            JspDirective node = JspDirective.getInstance(nodeName);
            node.setOffset(list.size());
            node.setLine(line);
            node.setAttributes(attributes.getAttributes());
            node.setClosed(NodeType.SELF_CLOSED);
            this.pushJspNode(stack, list, node);
        }
        else if(i == '!'){
            this.stream.read();
            int line = this.stream.getLine();
            String scriptlet = this.readJspScriptlet();

            if(list.size() > 0) {
                this.clip(list.get(list.size() - 1), 1);
            }

            JspDeclaration node = new JspDeclaration();
            node.setOffset(list.size());
            node.setLine(line);
            node.setClosed(NodeType.PAIR_CLOSED);
            node.setTextContent(scriptlet);
            this.pushJspNode(stack, list, node);
        }
        else if(i == '-'){
            i = this.stream.read();
            i = this.stream.read();
            int line = this.stream.getLine();

            if(i == '-') {
                this.readJspComment();
                this.stream.skipLine();
            }
            else {
                throw new Exception("bad jsp syntax at line #" + line + ": <%-");
            }
        }
        else if(i == '=') {
            this.stream.read();
            int line = this.stream.getLine();
            String expression = this.readJspScriptlet();

            if(!this.isEmpty(expression)) {
                JspExpression node = new JspExpression();
                node.setOffset(list.size());
                node.setLine(line);
                node.setClosed(NodeType.PAIR_CLOSED);
                node.setTextContent(expression);
                this.pushJspNode(stack, list, node);
            }
            else {
                throw new Exception("at line #" + line + " Invalid jsp expression !");
            }
        }
        else {
            int line = this.stream.getLine();
            String scriptlet = this.readJspScriptlet();

            if(list.size() > 0) {
                this.clip(list.get(list.size() - 1), 1);
            }

            JspScriptlet node = new JspScriptlet();
            node.setOffset(list.size());
            node.setLine(line);
            node.setTextContent(scriptlet);
            node.setClosed(NodeType.PAIR_CLOSED);
            this.pushJspNode(stack, list, node);
        }
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

        /**
        if(logger.isDebugEnabled()) {
            logger.debug("[push][node] parent: " + (parent != null ? parent.getNodeName() : "null") + ", nodeName: [" + node.getNodeName() + "]");
        }
        */
    }

    /**
     * @param stack
     * @param list
     * @param node
     */
    private void pushJspNode(Stack<Node> stack, List<Node> list, Node node) {
        Node parent = stack.peek();

        if(parent != null) {
            node.setParent(parent);
        }
        list.add(node);

        /**
        if(logger.isDebugEnabled()) {
            logger.debug("[push][node] parent: " + (parent != null ? parent.getNodeName() : "null") + ", nodeName: [" + node.getNodeName() + "]");
        }
        */
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
                StringBuilder buffer = new StringBuilder();

                if(node.getTextContent() != null) {
                    buffer.append(node.getTextContent());
                }
                buffer.append(text);

                textNode = (TextNode)node;
                textNode.setParent(parent);
                textNode.setTextContent(buffer.toString());
            }
            else {
                textNode = new TextNode();
                textNode.setParent(parent);
                textNode.setLine(lineNumber);
                textNode.setOffset(size);
                textNode.setLength(1);
                textNode.setTextContent(text);
                list.add(textNode);
            }
        }
        else {
            textNode = new TextNode();
            textNode.setParent(parent);
            textNode.setLine(lineNumber);
            textNode.setOffset(size);
            textNode.setLength(1);
            textNode.setTextContent(text);
            list.add(textNode);
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
            int line = this.stream.getLine();
            throw new Exception("Exception at line #" + line + ": </" + nodeName + "> not match !");
        }

        if(node.getNodeName().equalsIgnoreCase(nodeName)) {
            stack.pop();
            node.setLength(list.size() - node.getOffset() + 1);
            list.add(node);

            /**
            if(logger.isDebugEnabled()) {
                Node parent = node.getParent();
                logger.debug("[pop ][node] parent: " + (parent != null ? parent.getNodeName() : "null") + ", nodeName: [/" + node.getNodeName() + "]");
            }
            */
        }
        else {
            throw new Exception("Exception at line #" + node.getLine() + " " + NodeUtil.getDescription(node) + " not match !");
        }
    }

    /**
     * @param stack
     * @param list
     * @param work
     * @param file
     * @param type
     * @param encoding
     * @throws Exception
     */
    public void include(Stack<Node> stack, List<Node> list, String work, String file, String type, String encoding) throws Exception {
        if(logger.isDebugEnabled()) {
            logger.debug("work: {}, file: {}", work, file);
        }

        if(file == null) {
            throw new Exception("t:include error: attribute 'file' not exists !");
        }

        int index = list.size();
        Node parent = stack.peek();
        String path = this.getAbsolutePath(work, file);
        String charset = (encoding != null ? encoding : "utf-8");
        Source source = this.getSourceFactory().getSource(path);
        int sourceType = Source.valueOf(type, source.getType());
        source.setType(sourceType);

        if(sourceType == Source.STATIC) {
            String content = this.getContent(source, charset);
            TextNode textNode = new TextNode();
            textNode.setLine(1);
            textNode.setOffset(list.size());
            textNode.setLength(1);
            textNode.setParent(parent);
            textNode.setTextContent(content);
            list.add(textNode);
            this.addDependency(source);
            return;
        }

        JspParser compiler = new JspParser(this.sourceFactory);
        compiler.setTagLibrary(this.getTagLibrary());
        Template template = compiler.parse(source, charset);
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
        this.addDependency(compiler.getDependencies());
    }

    /**
     * @param work
     * @param file
     * @return String
     */
    private String getAbsolutePath(String work, String file) {
        String path = Path.getStrictPath(file);

        if(path.startsWith("/")) {
            return path;
        }
        else {
            String parent = Path.getParent(work);
            return Path.join(parent, path);
        }
    }

    /**
     * @param prefix
     * @param uri
     * @throws Exception
     */
    protected void loadTagLibrary(String prefix, String uri) throws Exception {
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
     * @param source
     * @param list
     * @param tagLibrary
     * @return Template
     * @throws Exception
     */
    public Template getTemplate(Source source, List<Node> list, TagLibrary tagLibrary) throws Exception {
        List<Node> nodes = this.compact(list, tagLibrary);

        TagFactoryManager tagFactoryManager = TagFactoryManager.getInstance();

        for(int i = 0, size = nodes.size(); i < size; i++) {
            Node node = nodes.get(i);

            if(node.getNodeType() == NodeType.TAG_NODE && i == node.getOffset()) {
                if(node.getLength() == 0) {
                    throw new Exception("Exception at line #" + node.getLine() + " " + NodeUtil.getDescription(node) + " not match !");
                }

                TagNode tagNode = (TagNode)node;
                String tagName = node.getNodeName();
                String className = tagLibrary.getTagClassName(tagName);
                TagFactory tagFactory = tagFactoryManager.getTagFactory(tagName, className);
                tagNode.setTagFactory(tagFactory);
            }
        }

        Map<String, String> pageInfo = new HashMap<String, String>();
        pageInfo.put("home", source.getHome());
        pageInfo.put("path", source.getPath());
        pageInfo.put("lastModified", DateUtil.format(source.getLastModified(), "yyyy-MM-dd HH:mm:ss"));

        Template template = new Template(source.getHome(), source.getPath(), nodes);
        template.setLastModified(source.getLastModified());
        template.setUpdateTime(System.currentTimeMillis());
        template.setDependencies(this.getDependencies());
        template.setPageInfo(pageInfo);
        return template;
    }

    /**
     * @param list
     * @param tagLibrary
     * @return List<Node>
     * @throws Exception
     */
    public List<Node> compact(List<Node> list, TagLibrary tagLibrary) throws Exception {
        this.clip(list, tagLibrary);
        List<Node> nodes = new ArrayList<Node>();

        for(int i = 0, size = list.size(); i < size; i++) {
            Node node = list.get(i);
            int nodeType = node.getNodeType();

            if(nodeType == NodeType.TEXT || nodeType == NodeType.EXPRESSION || nodeType == NodeType.VARIABLE) {
                if(((DataNode)node).getContentLength() > 0) {
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
                        i = i + node.getLength();
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
     */
    protected void clip(List<Node> list, TagLibrary tagLibrary) {
        for(int i = 0, size = list.size(); i < size; i++) {
            Node node = list.get(i);
            int nodeType = node.getNodeType();

            if(nodeType == NodeType.TAG_NODE) {
                String clip = node.getAttributeText("t:clip");
                TagInfo tagInfo = tagLibrary.getTagInfo(node.getNodeName());

                if(clip == null) {
                    clip = String.valueOf(tagInfo.getIgnoreWhitespace());
                }

                /**
                 * trim whitespace
                 */
                if(clip.equals("true")) {
                    if(i > 0) {
                        this.clip(list.get(i - 1), 1);
                    }

                    if(i + 1 < size) {
                        this.clip(list.get(i + 1), 2);
                    }
                }

                if(i != node.getOffset()) {
                    node.removeAttribute("t:clip");
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
    protected void clip(Node node, int type) {
        if(node.getNodeType() != NodeType.TEXT) {
            return;
        }

        char c;
        int j = 0;
        String content = node.getTextContent();

        if(type == 1) {
            /**
             * 删除该文本节点的后缀空格
             * 也就是删除下一个标签节点的前导空格
             * 只删除空格不删除其他不可见字符
             */
            for(j = content.length() - 1; j > -1; j--) {
                c = content.charAt(j);

                if(c == ' ' || c == '\t') {
                    continue;
                }
                else {
                    break;
                }
            }
            content = content.substring(0, j + 1);
        }
        else {
            /**
             * 删除该文本节点的前导回车
             * 也就是删除前一个标签节点的后缀回车
             * 只删除回车不删除其他不可见字符
             */
            int length = content.length();

            for(j = 0; j < length; j++) {
                c = content.charAt(j);

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

            if(j <= length) {
                content = content.substring(j, length);
            }
        }
        ((TextNode)node).setTextContent(content);
    }

    /**
     * @param nodeName
     * @return boolean
     */
    private boolean isTplDirective(String nodeName) {
        if(nodeName.startsWith("t:")) {
            return (nodeName.equals(TPL_DIRECTIVE_TAGLIB)
                    || nodeName.equals(TPL_DIRECTIVE_IMPORT)
                    || nodeName.equals(TPL_DIRECTIVE_RENAME)
                    || nodeName.equals(TPL_DIRECTIVE_INCLUDE)
                    || nodeName.equals(TPL_DIRECTIVE_TEXT)
                    || nodeName.equals(TPL_DIRECTIVE_COMMENT));
        }
        else {
            return false;
        }
    }

    /**
     * @param nodeName
     * @return boolean
     */
    private boolean isJspDirective(String nodeName) {
        return (nodeName.equals(JSP_DIRECTIVE_PAGE)
                || nodeName.equals(JSP_DIRECTIVE_TAGLIB)
                || nodeName.equals(JSP_DIRECTIVE_INCLUDE)
                || nodeName.equals(JSP_DECLARATION)
                || nodeName.equals(JSP_SCRIPTLET)
                || nodeName.equals(JSP_EXPRESSION));
    }

    /**
     * @param source
     * @param charset
     * @return String
     * @throws IOException
     */
    protected String getContent(Source source, String charset) throws IOException {
        return this.sourceFactory.getContent(source.getPath(), charset);
    }

    /**
     * @param inputStream
     * @param charset
     * @return String
     * @throws IOException
     */
    protected String getContent2(InputStream inputStream, String charset) throws IOException {
        int length = 0;
        byte[] buffer = new byte[8192];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            while((length = inputStream.read(buffer, 0, 8192)) >0) {
                bos.write(buffer, 0, length);
            }
            return new String(bos.toByteArray(), charset);
        }
        finally {
            try {
                inputStream.close();
            }
            catch(Exception e) {
            }
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
     * @param dependencies
     */
    public void addDependency(List<Source> dependencies) {
        if(dependencies != null && dependencies.size() > 0) {
            if(this.dependencies == null) {
                this.dependencies = new ArrayList<Source>();
            }
            this.dependencies.addAll(dependencies);
        }
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
