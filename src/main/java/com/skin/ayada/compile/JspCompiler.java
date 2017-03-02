/*
 * $RCSfile: JspCompiler.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-11-08 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.compile;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skin.ayada.Source;
import com.skin.ayada.Template;
import com.skin.ayada.Version;
import com.skin.ayada.config.ClassPathResource;
import com.skin.ayada.config.TemplateConfig;
import com.skin.ayada.io.CharBuffer;
import com.skin.ayada.io.ChunkWriter;
import com.skin.ayada.statement.Attribute;
import com.skin.ayada.statement.Expression;
import com.skin.ayada.statement.Node;
import com.skin.ayada.statement.NodeType;
import com.skin.ayada.statement.TagNode;
import com.skin.ayada.statement.Variable;
import com.skin.ayada.tagext.BodyTag;
import com.skin.ayada.tagext.DynamicAttributes;
import com.skin.ayada.tagext.IterationTag;
import com.skin.ayada.tagext.SimpleTag;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TryCatchFinally;
import com.skin.ayada.util.ClassUtil;
import com.skin.ayada.util.DateUtil;
import com.skin.ayada.util.NodeUtil;
import com.skin.ayada.util.Path;
import com.skin.ayada.util.StringUtil;

/**
 * <p>Title: JspCompiler</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class JspCompiler {
    private boolean fastJstl = true;
    private static final String JAVA_TEMPLATE = JspCompiler.getJavaTemplate();
    private static final Logger logger = LoggerFactory.getLogger(JspCompiler.class);

    /**
     * @param template
     * @param packageName
     * @param className
     * @return String
     */
    public String compile(Template template, String packageName, String className) {
        if(logger.isDebugEnabled()) {
            logger.debug("compile: {}.{}", packageName, className);
        }

        Date date = new Date();
        String jspDirective = this.getJspDirective(template);
        String jspDeclaration = this.getJspDeclaration(template);
        String methodBody = this.getMethodBody(template);
        String subClassBody = this.getSubClassBody(template);
        String staticDeclaration = this.getStaticDeclaration(template);
        String dependencies = this.getDependencies(template);

        Map<String, String> context = new HashMap<String, String>();
        context.put("java.className", className);
        context.put("java.packageName", packageName);
        context.put("build.date", DateUtil.format(date, "yyyy-MM-dd"));
        context.put("build.time", DateUtil.format(date, "yyyy-MM-dd HH:mm:ss SSS"));
        context.put("template.home", Path.getStrictPath(template.getHome()));
        context.put("template.path", Path.getStrictPath(template.getPath()));
        context.put("template.lastModified", DateUtil.format(template.getLastModified(), "yyyy-MM-dd HH:mm:ss SSS"));
        context.put("template.dependencies", dependencies);
        context.put("options.fastJstl", String.valueOf(this.fastJstl));
        context.put("compiler.version", Version.getVersion());
        context.put("jsp.directive.import", jspDirective);
        context.put("jsp.declaration", jspDeclaration);
        context.put("jsp.method.body", methodBody);
        context.put("jsp.subclass.body", subClassBody);
        context.put("jsp.static.declaration", staticDeclaration);
        return this.replace(JAVA_TEMPLATE, context);
    }

    /**
     * @param template
     * @return String
     */
    protected String getDependencies(Template template) {
        StringBuilder buffer = new StringBuilder();
        List<Source> dependencies = template.getDependencies();

        if(dependencies != null) {
            for(Source source : dependencies) {
                buffer.append(" * -- ");
                buffer.append(Path.getStrictPath("/" + source.getPath()));
                buffer.append("\r\n");
            }

            if(buffer.length() > 0) {
                buffer.delete(buffer.length() - 2, buffer.length());
            }
        }
        return buffer.toString();
    }

    /**
     * @param template
     * @return String
     */
    protected String getJspDirective(Template template) {
        Node node = null;
        List<Node> list = template.getNodes();
        ChunkWriter chunkWriter = new ChunkWriter(4096);
        PrintWriter writer = new PrintWriter(chunkWriter);

        for(int index = 0, size = list.size(); index < size; index++) {
            node = list.get(index);

            if(node.getNodeType() == NodeType.JSP_DIRECTIVE_PAGE) {
                if(node.getOffset() == index && node.getAttribute("import") != null) {
                    writer.println("import " + node.getAttribute("import") + "; // jsp:directive.import: line: " + node.getLine());
                }
            }
        }
        writer.flush();
        writer.close();
        return chunkWriter.toString();
    }

    /**
     * @param template
     * @return String
     */
    protected String getJspDeclaration(Template template) {
        Node node = null;
        List<Node> list = template.getNodes();
        ChunkWriter chunkWriter = new ChunkWriter(4096);
        PrintWriter writer = new PrintWriter(chunkWriter);

        for(int index = 0, size = list.size(); index < size; index++) {
            node = list.get(index);

            if(node.getNodeType() == NodeType.JSP_DECLARATION) {
                if(node.getOffset() == index) {
                    writer.println("    // JSP_DECLARATION: line: " + node.getLine());
                    writer.print(node.getTextContent());
                    writer.println("    // jsp:declaration END");
                    writer.println();
                }
            }
        }
        writer.flush();
        writer.close();
        return chunkWriter.toString();
    }

    /**
     * @param template
     * @return String
     */
    protected String getStaticDeclaration(Template template) {
        Node node = null;
        List<Node> list = template.getNodes();
        ChunkWriter chunkWriter = new ChunkWriter(4096);
        PrintWriter writer = new PrintWriter(chunkWriter);

        for(int index = 0, size = list.size(); index < size; index++) {
            node = list.get(index);

            if(node.getNodeType() == NodeType.TEXT) {
                String content = StringUtil.escape(node.getTextContent());
                writer.print("    protected static final char[] " + this.getVariableName(node, "_jsp_string_"));
                writer.print(" = \"");
                writer.print(content);
                writer.println("\".toCharArray();");
            }
        }
        writer.flush();
        writer.close();
        return chunkWriter.toString();
    }

    /**
     * @param template
     * @return String
     */
    protected String getMethodBody(Template template) {
        ChunkWriter chunkWriter = new ChunkWriter(8192);
        PrintWriter writer = new PrintWriter(chunkWriter);
        List<Node> list = template.getNodes();
        this.writeBody(writer, list, 0, list.size());
        return chunkWriter.toString();
    }

    /**
     * @param template
     * @return String
     */
    protected String getSubClassBody(Template template) {
        ChunkWriter chunkWriter = new ChunkWriter(8192);
        PrintWriter writer = new PrintWriter(chunkWriter);
        List<Node> list = template.getNodes();
        this.writeClass(writer, list, 0, list.size());
        return chunkWriter.toString();
    }

    /**
     * @param list
     * @param writer
     * @param offset
     * @param length
     */
    public void writeClass(PrintWriter writer, List<Node> list, int offset, int length) {
        int nodeType = 0;
        Node node = null;
        String tagClassName = null;

        for(int index = offset, end = offset + length; index < end; index++) {
            node = list.get(index);
            nodeType = node.getNodeType();

            if(nodeType != NodeType.TAG_NODE) {
                continue;
            }

            tagClassName = ((TagNode)node).getTagClassName();

            if(index == node.getOffset() && this.isAssignableFrom(tagClassName, SimpleTag.class)) {
                String tagInstanceName = this.getTagInstanceName(node);
                String jspFragmentClassName = this.getVariableName(node, "JspFragment_");
                writer.println();
                writer.println("    // NODE START: line: " + node.getLine() + ", offset: " + node.getOffset() + ", length: " + node.getLength() + ", tagClassName: " + tagClassName + ", tagInstanceName: " + tagInstanceName);
                writer.println("    // " + NodeUtil.getDescription(node));
                writer.println("    public class " + jspFragmentClassName + " extends com.skin.ayada.tagext.AbstractJspFragment {");
                writer.println("        @Override");
                writer.println("        public void execute(final JspWriter writer) throws Exception {");
                writer.println("            JspWriter out = writer;");
                writer.println("            PageContext pageContext = this.getPageContext();");
                writer.println("            ExpressionContext expressionContext = pageContext.getExpressionContext();");
                writer.println("            Tag " + tagInstanceName + " = this.getParent();");
                writer.println();
                writer.println("            // offset: " + (index + 1) + ", length: " + (node.getLength() - 2));
                this.writeBody(writer, list, index + 1, node.getLength() - 2);
                writer.println("        }");
                writer.println("    }");
                writer.println("    // NODE END: line: " + node.getLine() + ", tagClassName: " + tagClassName + ", tagInstanceName: " + tagInstanceName);
            }
        }
    }

    /**
     * @param writer
     * @param list
     * @param offset
     * @param length
     */
    protected void writeBody(PrintWriter writer, List<Node> list, int offset, int length) {
        Node node = null;
        String indent = null;

        for(int index = offset, end = offset + length; index < end; index++) {
            node = list.get(index);
            indent = this.getIndent(node);

            /**
             * 
             */
            if(this.writeJspNode(writer, list, index, indent)) {
                continue;
            }

            if(node.getLength() == 0) {
                throw new RuntimeException("Exception at line #" + node.getLine() + " " + NodeUtil.getDescription(node) + " not match !");
            }

            int flag = this.writeTagNode(writer, node, index, indent);

            if(flag == Tag.SKIP_BODY) {
                index = node.getOffset() + node.getLength() - 2;
            }
            else {
                if(node.getOffset() != index) {
                    writer.println();
                }
            }
        }
        writer.flush();
    }
    
    /**
     * @param writer
     * @param list
     * @param index
     * @param indent
     * @return boolean
     */
    protected boolean writeJspNode(PrintWriter writer, List<Node> list, int index, String indent) {
        Node node = list.get(index);
        int nodeType = node.getNodeType();

        switch(nodeType) {
            case NodeType.TEXT: {
                String variable = this.getVariableName(node, "_jsp_string_");
                writer.println(indent + "// [" + node.getLine() + "] out.write(\"" + StringUtil.escape(node.getTextContent()) + "\");");
                writer.println(indent + "out.write(" + variable + ", 0, " + variable + ".length);");
    
                if(this.isTagNode(list, index + 1)) {
                    writer.println();
                }
                return true;
            }
            case NodeType.VARIABLE: {
                String textContent = node.getTextContent();
                writer.println(indent + "// [" + node.getLine() + "] " + textContent);

                if(((Variable)node).getFlag() == '#') {
                    writer.println(indent + "out.write(pageContext.getAttribute(\"" + textContent + "\"));");
                }
                else {
                    writer.println(indent + "expressionContext.print(out, pageContext.getAttribute(\"" + textContent + "\"));");
                }
    
                if(this.isTagNode(list, index + 1)) {
                    writer.println();
                }
                return true;
            }
            case NodeType.EXPRESSION: {
                String textContent = StringUtil.escape(node.getTextContent());
                writer.println(indent + "// [" + node.getLine() + "] " + textContent);
    
                if(((Expression)node).getFlag() == '#') {
                    writer.println(indent + "out.write(expressionContext.getValue(\"" + textContent + "\"));");
                }
                else {
                    writer.println(indent + "expressionContext.print(out, expressionContext.getValue(\"" + textContent + "\"));");
                }
    
                if(this.isTagNode(list, index + 1)) {
                    writer.println();
                }
                return true;
            }
            case NodeType.JSP_DECLARATION: {
                writer.println(indent + "// [" + node.getLine() + "] " + NodeUtil.getDescription(node));
                return true;
            }
            case NodeType.JSP_DIRECTIVE_PAGE: {
                writer.println(indent + "// [" + node.getLine() + "] " + NodeUtil.getDescription(node));
                return true;
            }
            case NodeType.JSP_DIRECTIVE_TAGLIB: {
                writer.println(indent + "// [" + node.getLine() + "] " + NodeUtil.getDescription(node));
                return true;
            }
            case NodeType.JSP_DIRECTIVE_INCLUDE: {
                writer.println(indent + "// [" + node.getLine() + "] " + NodeUtil.getDescription(node));
                return true;
            }
            case NodeType.JSP_SCRIPTLET: {
                writer.println(indent + "// [" + node.getLine() + "] " + NodeUtil.getDescription(node));
                writer.println(node.getTextContent());
                writer.println(indent + "// [" + node.getLine() + "] end.");
                return true;
            }
            case NodeType.JSP_EXPRESSION: {
                writer.println(indent + "// [" + node.getLine() + "] " + NodeUtil.getDescription(node));
                writer.println(indent + "expressionContext.print(out, (" + node.getTextContent() + "));");
                writer.println(indent + "// [" + node.getLine() + "] end.");
                return true;
            }
            default: {
                return false;
            }
        }
    }

    /**
     * @param writer
     * @param node
     * @param index
     * @param indent
     * @return int
     */
    protected int writeTagNode(PrintWriter writer, Node node, int index, String indent) {
        /** Tag Support */
        String tagClassName = ((TagNode)node).getTagClassName();
        String tagInstanceName = this.getTagInstanceName(node);

        if(node.getOffset() == index) {
            writer.println(indent + "// [" + node.getLine() + "] offset: " + node.getOffset() + ", length: " + node.getLength() + ", name: " + tagInstanceName + ", tag: " + tagClassName);
            writer.println(indent + "// " + StringUtil.escape(NodeUtil.getDescription(node)));
        }

        int flag = Tag.EVAL_PAGE;

        if(this.fastJstl && tagClassName.startsWith("com.skin.ayada.jstl.")) {
            if(tagClassName.endsWith(".core.IfTag")) {
                flag = this.writeIfTag(writer, node, index, indent);
            }
            else if(tagClassName.endsWith(".core.SetTag")) {
                flag = this.writeSetTag(writer, node, index, indent);
            }
            else if(tagClassName.endsWith(".core.OutTag")) {
                flag = this.writeOutTag(writer, node, index, indent);
            }
            else if(tagClassName.endsWith(".core.ForEachTag")) {
                flag = this.writeForEachTag(writer, node, index, indent);
            }
            else if(tagClassName.endsWith(".core.ChooseTag")) {
                flag = this.writeChooseTag(writer, node, index, indent);
            }
            else if(tagClassName.endsWith(".core.WhenTag")) {
                flag = this.writeWhenTag(writer, node, index, indent);
            }
            else if(tagClassName.endsWith(".core.OtherwiseTag")) {
                flag = this.writeOtherwiseTag(writer, node, index, indent);
            }
            else if(tagClassName.endsWith(".core.CommentTag")) {
                flag = this.writeCommentTag(writer, node, index, indent);
            }
            else if(tagClassName.endsWith(".core.PrintTag")) {
                flag = this.writePrintTag(writer, node, index, indent);
            }
            else if(tagClassName.endsWith(".core.AttributeTag")) {
                flag = this.writeAttributeTag(writer, node, index, indent);
            }
            else if(tagClassName.endsWith(".core.ElementTag")) {
                flag = this.writeElementTag(writer, node, index, indent);
            }
            else if(tagClassName.endsWith(".core.ConstructorTag")) {
                flag = this.writeConstructorTag(writer, node, index, indent);
            }
            else if(tagClassName.endsWith(".core.PropertyTag")) {
                flag = this.writePropertyTag(writer, node, index, indent);
            }
            else if(tagClassName.endsWith(".core.ParameterTag")) {
                flag = this.writePrameterTag(writer, node, index, indent);
            }
            else if(tagClassName.endsWith(".core.ExecuteTag")) {
                flag = this.writeExecuteTag(writer, node, index, indent);
            }
            else if(tagClassName.endsWith(".core.ExitTag")) {
                flag = this.writeExitTag(writer, node, index, indent);
            }
            else if(tagClassName.endsWith(".fmt.DateFormatTag")) {
                flag = this.writeFormatDateTag(writer, node, index, indent);
            }
            else {
                flag = this.writeTag(writer, node, index, indent);
            }
        }
        else {
            flag = this.writeTag(writer, node, index, indent);
        }

        if(node.getOffset() != index) {
            writer.println(indent + "// [" + node.getLine() + "] name: " + tagInstanceName + ", tag: " + tagClassName);
        }
        return flag;
    }

    /**
     * @param writer
     * @param node
     * @param index
     * @param indent
     * @return int
     */
    private int writeIfTag(PrintWriter writer, Node node, int index, String indent) {
        if(node.getOffset() == index) {
            Attribute attribute = node.getAttribute("test");
            String valueExpression = this.getValueExpression(attribute, boolean.class);
            writer.println(indent + "if(" + valueExpression + ") {");
        }
        else {
            writer.println(indent + "}");
        }
        return Tag.EVAL_PAGE;
    }

    /**
     * @param writer
     * @param node
     * @param index
     * @param indent
     * @return int
     */
    private int writeSetTag(PrintWriter writer, Node node, int index, String indent) {
        String name = node.getAttributeText("var");
        String value = this.getValueExpression(node.getAttribute("value"));
        String target = this.getValueExpression(node.getAttribute("target"));
        String property = node.getAttributeText("property");

        if(node.getOffset() == index) {
            if(value != null) {
                if(name != null) {
                    writer.println(indent + "pageContext.setAttribute(\"" + name + "\", " + value + ");");
                }
                else {
                    if(target != null && property != null) {
                        writer.println(indent + "com.skin.ayada.util.ClassUtil.setProperty(" + target + ", \"" + property + "\", " + value + ");");
                    }
                }
            }
            else {
                if(node.getLength() > 2) {
                    writer.println(indent + "out = pageContext.pushBody();");
                }
            }
        }
        else {
            if(value == null) {
                if(node.getLength() > 2) {
                    if(name != null) {
                        writer.println(indent + "pageContext.setAttribute(\"" + name + "\", ((BodyContent)out).getString().trim());");
                    }
                    else {
                        if(target != null && property != null) {
                            writer.println(indent + "com.skin.ayada.util.ClassUtil.setProperty(" + target + ", \"" + property + "\", ((BodyContent)out).getString().trim());");
                        }
                    }
                    writer.println(indent + "out = pageContext.popBody();");
                }
                else {
                    if(name != null) {
                        writer.println(indent + "pageContext.setAttribute(\"" + name + "\", \"\");");
                    }
                    else {
                        if(target != null && property != null) {
                            writer.println(indent + "com.skin.ayada.util.ClassUtil.setProperty(" + target + ", \"" + property + "\", \"\");");
                        }
                    }
                }
            }
        }
        return Tag.EVAL_PAGE;
    }

    /**
     * @param writer
     * @param node
     * @param index
     * @param indent
     * @return int
     */
    private int writeOutTag(PrintWriter writer, Node node, int index, String indent) {
        Attribute value = node.getAttribute("value");
        boolean escapeXml = "true".equals(node.getAttributeValue("escapeXml"));

        if(node.getOffset() == index) {
            if(value != null) {
                writer.println(indent + "com.skin.ayada.jstl.core.OutTag.write(out, " + this.getValueExpression(value) + ", " + escapeXml + ");");
                return Tag.SKIP_BODY;
            }
            else if(node.getLength() > 2) {
                writer.println(indent + "out = pageContext.pushBody();");
            }
            return Tag.EVAL_PAGE;
        }

        if(value == null && node.getLength() > 2) {
            writer.println(indent + "pageContext.print((BodyContent)out, " + escapeXml + ");");
            writer.println(indent + "out = pageContext.popBody();");
        }
        return Tag.EVAL_PAGE;
    }

    /**
     * @param writer
     * @param node
     * @param index
     * @param indent
     * @return int
     */
    private int writeForEachTag(PrintWriter writer, Node node, int index, String indent) {
        String tagClassName = ((TagNode)node).getTagClassName();
        String tagInstanceName = this.getTagInstanceName(node);
        String parentTagInstanceName = this.getTagInstanceName(node.getParent());
        String forEachOldVar = this.getVariableName(node, "_jsp_old_var_");
        String forEachOldVarStatus = this.getVariableName(node, "_jsp_old_var_status_");

        if(node.getOffset() == index) {
            Attribute items = node.getAttribute("items");
            Attribute variable = node.getAttribute("var");
            Attribute begin = node.getAttribute("begin");
            Attribute step = node.getAttribute("step");
            Attribute end = node.getAttribute("end");
            Attribute varStatus = node.getAttribute("varStatus");
            boolean hasParent = this.hasParent(node);

            if(variable != null) {
                writer.println(indent + "Object " + forEachOldVar + " = pageContext.getAttribute(\"" + variable.getValue() + "\");");
            }

            if(varStatus != null) {
                writer.println(indent + "Object " + forEachOldVarStatus + " = pageContext.getAttribute(\"" + varStatus.getValue() + "\");");
            }

            writer.println(indent + tagClassName + " " + tagInstanceName + " = new " + tagClassName + "();");

            if(hasParent) {
                writer.println(indent + tagInstanceName + ".setParent(" + parentTagInstanceName + ");");
            }
            else {
                writer.println(indent + tagInstanceName + ".setParent((Tag)null);");
            }

            writer.println(indent + tagInstanceName + ".setPageContext(pageContext);");

            if(variable != null) {
                writer.println(indent + tagInstanceName + ".setVar(\"" + StringUtil.escape(variable.getText()) + "\");");
            }

            if(items != null) {
                String itemsValue = this.getValueExpression(items);
                writer.println(indent + tagInstanceName + ".setItems(" + itemsValue + ");");
            }

            if(begin != null && end != null) {
                String beginExpression = this.getValueExpression(begin, int.class);
                String endExpression = this.getValueExpression(end, int.class);
                writer.println(indent + tagInstanceName + ".setBegin(" + beginExpression + ");");
                writer.println(indent + tagInstanceName + ".setEnd(" + endExpression + ");");
            }

            if(step != null) {
                String stepExpression = this.getValueExpression(step, int.class);
                writer.println(indent + tagInstanceName + ".setStep(" + stepExpression + ");");
            }

            if(varStatus != null) {
                writer.println(indent + tagInstanceName + ".setVarStatus(\"" + varStatus + "\");");
            }
            writer.println(indent + "if(" + tagInstanceName + ".doStartTag() != Tag.SKIP_BODY) {");
            writer.println(indent + "    while(true) {");   
        }
        else {
            Attribute variable = node.getAttribute("var");
            Attribute varStatus = node.getAttribute("varStatus");
            writer.println(indent + "        if(" + tagInstanceName + ".doAfterBody() != IterationTag.EVAL_BODY_AGAIN) {");
            writer.println(indent + "            break;");
            writer.println(indent + "        }");
            writer.println(indent + "    }");
            writer.println(indent + "}");
            writer.println(indent + tagInstanceName + ".release();");

            if(variable != null) {
                writer.println(indent + "pageContext.setAttribute(\"" + variable.getValue() + "\", " + forEachOldVar + ");");
            }

            if(varStatus != null) {
                writer.println(indent + "pageContext.setAttribute(\"" + varStatus.getValue() + "\", " + forEachOldVarStatus + ");");
            }
        }
        return Tag.EVAL_PAGE;
    }

    /**
     * @param writer
     * @param node
     * @param index
     * @param indent
     * @return int
     */
    private int writeChooseTag(PrintWriter writer, Node node, int index, String indent) {
        if(node.getOffset() == index) {
            String tagInstanceName = this.getTagInstanceName(node);
            writer.println(indent + "// boolean " + tagInstanceName + " = true;");
            writer.println();
        }
        else {
        }
        return Tag.EVAL_PAGE;
    }

    /**
     * @param writer
     * @param node
     * @param index
     * @param indent
     * @return int
     */
    private int writeWhenTag(PrintWriter writer, Node node, int index, String indent) {
        if(node.getOffset() == index) {
            Attribute attribute = node.getAttribute("test");
            String valueExpression = this.getValueExpression(attribute, boolean.class);

            if(node.getParent().getOffset() + 1 == node.getOffset()) {
                writer.println(indent + "if(" + valueExpression + ") {");
            }
            else {
                writer.println(indent + "else if(" + valueExpression + ") {");
            }
        }
        else {
            writer.println(indent + "}");
        }
        return Tag.EVAL_PAGE;
    }

    /**
     * @param writer
     * @param node
     * @param index
     * @param indent
     * @return int
     */
    private int writeOtherwiseTag(PrintWriter writer, Node node, int index, String indent) {
        if(node.getOffset() == index) {
            writer.println(indent + "else {");
        }
        else {
            writer.println(indent + "}");
        }
        return Tag.EVAL_PAGE;
    }

    /**
     * @param writer
     * @param node
     * @param index
     * @param indent
     * @return int
     */
    private int writeFormatDateTag(PrintWriter writer, Node node, int index, String indent) {
        if(node.getOffset() == index) {
            String tagClassName = ((TagNode)node).getTagClassName();
            String tagInstanceName = this.getTagInstanceName(node);
            String parentTagInstanceName = this.getTagInstanceName(node.getParent());
            boolean hasParent = this.hasParent(node);

            writer.println(indent + tagClassName + " " + tagInstanceName + " = new " + tagClassName + "();");

            if(hasParent) {
                writer.println(indent + tagInstanceName + ".setParent(" + parentTagInstanceName + ");");
            }
            else {
                writer.println(indent + tagInstanceName + ".setParent((Tag)null);");
            }
            writer.println(indent + tagInstanceName + ".setPageContext(pageContext);");
            this.setAttributes(indent, tagClassName, tagInstanceName, node.getAttributes(), writer);
            writer.println(indent + tagInstanceName + ".doStartTag();");
            writer.println(indent + tagInstanceName + ".doEndTag();");
            writer.println(indent + tagInstanceName + ".release();");
        }
        else {
        }
        return Tag.EVAL_PAGE;
    }

    /**
     * @param writer
     * @param node
     * @param index
     * @param indent
     * @return int
     */
    private int writeCommentTag(PrintWriter writer, Node node, int index, String indent) {
        if(node.getOffset() == index) {
            writer.println(indent + "if(com.skin.ayada.tagext.ConditionalTagSupport.getTrue()) {");
        }
        else {
            writer.println(indent + "}");
        }
        return Tag.EVAL_PAGE;
    }

    /**
     * @param writer
     * @param node
     * @param index
     * @param indent
     * @return int
     */
    private int writePrintTag(PrintWriter writer, Node node, int index, String indent) {
        if(node.getOffset() == index) {
            Attribute out = node.getAttribute("out");
            Attribute value = node.getAttribute("value");

            if(out == null) {
                writer.println(indent + "com.skin.ayada.jstl.core.PrintTag.print(pageContext, null, " + this.getValueExpression(value) + ");");
            }
            else {
                writer.println(indent + "com.skin.ayada.jstl.core.PrintTag.print(pageContext, " + this.getValueExpression(out) + ", " + this.getValueExpression(value) + ");");
            }
        }
        else {
        }
        return Tag.EVAL_PAGE;
    }

    /**
     * @param writer
     * @param node
     * @param index
     * @param indent
     * @return int
     */
    private int writeAttributeTag(PrintWriter writer, Node node, int index, String indent) {
        Attribute name = node.getAttribute("name");
        Attribute value = node.getAttribute("value");
        String parentTagInstanceName = this.getTagInstanceName(node.getParent());

        if(node.getOffset() == index) {
            if(value != null) {
                if(name != null) {
                    writer.println(indent + parentTagInstanceName + ".setAttribute(\"" + name + "\", " + this.getValueExpression(value) + ");");
                }
                else {
                    writer.println(indent + parentTagInstanceName + ".setAttribute((String)null, " + this.getValueExpression(value) + ");");
                }
                return Tag.SKIP_BODY;
            }
            else if(node.getLength() > 2) {
                writer.println(indent + "out = pageContext.pushBody();");
            }
            return Tag.EVAL_PAGE;
        }

        if(value == null && node.getLength() > 2) {
            if(name != null) {
                writer.println(indent + parentTagInstanceName + ".setAttribute(\"" + name + "\", ((BodyContent)out).getString());");
            }
            else {
                writer.println(indent + parentTagInstanceName + ".setAttribute((String)null, ((BodyContent)out).getString());");
            }
            writer.println(indent + "out = pageContext.popBody();");
        }
        return Tag.EVAL_PAGE;
    }

    /**
     * @param writer
     * @param node
     * @param index
     * @param indent
     * @return int
     */
    private int writeElementTag(PrintWriter writer, Node node, int index, String indent) {
        Attribute indek = node.getAttribute("index");
        Attribute value = node.getAttribute("value");
        String parentTagInstanceName = this.getTagInstanceName(node.getParent());

        if(node.getOffset() == index) {
            if(value != null) {
                if(indek != null) {
                    writer.println(indent + parentTagInstanceName + ".setElement(" + indek + ", " + this.getValueExpression(value) + ");");
                }
                else {
                    writer.println(indent + parentTagInstanceName + ".addElement(" + this.getValueExpression(value) + ");");
                }
                return Tag.SKIP_BODY;
            }
            else if(node.getLength() > 2) {
                writer.println(indent + "out = pageContext.pushBody();");
            }
            return Tag.EVAL_PAGE;
        }
        if(value == null && node.getLength() > 2) {
            if(indek != null) {
                writer.println(indent + parentTagInstanceName + ".setElement(" + indek + ", ((BodyContent)out).getString());");
            }
            else {
                writer.println(indent + parentTagInstanceName + ".addElement(((BodyContent)out).getString());");
            }
            writer.println(indent + "out = pageContext.popBody();");
        }
        return Tag.EVAL_PAGE;
    }

    /**
     * @param writer
     * @param node
     * @param index
     * @param indent
     * @return int
     */
    private int writeConstructorTag(PrintWriter writer, Node node, int index, String indent) {
        Attribute name = node.getAttribute("type");
        Attribute value = node.getAttribute("value");
        String parentTagInstanceName = this.getTagInstanceName(node.getParent());

        if(node.getOffset() == index) {
            if(value != null) {
                writer.println(indent + "com.skin.ayada.jstl.core.ConstructorTag.setArgument(" + parentTagInstanceName + ", \"" + name + "\", " + this.getValueExpression(value) + ");");
                return Tag.SKIP_BODY;
            }
            return Tag.EVAL_PAGE;
        }
        return Tag.EVAL_PAGE;
    }

    /**
     * @param writer
     * @param node
     * @param index
     * @param indent
     * @return int
     */
    private int writePropertyTag(PrintWriter writer, Node node, int index, String indent) {
        Attribute name = node.getAttribute("name");
        Attribute value = node.getAttribute("value");
        String parentTagInstanceName = this.getTagInstanceName(node.getParent());

        if(node.getOffset() == index) {
            if(value != null) {
                writer.println(indent + parentTagInstanceName + ".setProperty(\"" + name + "\", " + this.getValueExpression(value) + ");");
                return Tag.SKIP_BODY;
            }
            else if(node.getLength() > 2) {
                writer.println(indent + "out = pageContext.pushBody();");
            }
            return Tag.EVAL_PAGE;
        }
        if(value == null && node.getLength() > 2) {
            writer.println(indent + parentTagInstanceName + ".setProperty(\"" + name + "\", ((BodyContent)out).getString());");
            writer.println(indent + "out = pageContext.popBody();");
        }
        return Tag.EVAL_PAGE;
    }

    /**
     * @param writer
     * @param node
     * @param index
     * @param indent
     * @return int
     */
    private int writePrameterTag(PrintWriter writer, Node node, int index, String indent) {
        Attribute name = node.getAttribute("name");
        Attribute value = node.getAttribute("value");
        String parentTagInstanceName = this.getTagInstanceName(node.getParent());

        if(node.getOffset() == index) {
            if(value != null) {
                writer.println(indent + parentTagInstanceName + ".setParameter(\"" + name + "\", " + this.getValueExpression(value) + ");");
                return Tag.SKIP_BODY;
            }
            else if(node.getLength() > 2) {
                writer.println(indent + "out = pageContext.pushBody();");
            }
            return Tag.EVAL_PAGE;
        }
        if(value == null && node.getLength() > 2) {
            writer.println(indent + parentTagInstanceName + ".setParameter(\"" + name + "\", ((BodyContent)out).getString());");
            writer.println(indent + "out = pageContext.popBody();");
        }
        return Tag.EVAL_PAGE;
    }

    /**
     * @param writer
     * @param node
     * @param index
     * @param indent
     * @return int
     */
    private int writeExecuteTag(PrintWriter writer, Node node, int index, String indent) {
        if(node.getOffset() == index) {
            Attribute name = node.getAttribute("var");
            Attribute value = node.getAttribute("value");
            String valueExpression = this.getValueExpression(value);

            if(name != null) {
                writer.println(indent + "pageContext.setAttribute(\"" + name + "\", " + valueExpression + ");");
            }
            else {
                writer.println(indent + valueExpression + ";");
            }
        }
        else {
        }
        return Tag.EVAL_PAGE;
    }

    /**
     * @param writer
     * @param node
     * @param index
     * @param indent
     * @return int
     */
    private int writeExitTag(PrintWriter writer, Node node, int index, String indent) {
        if(node.getOffset() == index) {
            Attribute test = node.getAttribute("test");

            if(test != null) {
                String valueExpression = this.getValueExpression(test, boolean.class);
                writer.println(indent + "if(" + valueExpression + ") {");
            }
            else {
                writer.println(indent + "if(com.skin.ayada.tagext.ConditionalTagSupport.getTrue()) {");
            }
            writer.println(indent + "    return;");
            writer.println(indent + "}");
            return Tag.SKIP_BODY;
        }
        return Tag.EVAL_PAGE;
    }

    /**
     * @param writer
     * @param node
     * @param index
     * @param indent
     * @return int
     */
    private int writeTag(PrintWriter writer, Node node, int index, String indent) {
        String tagClassName = ((TagNode)node).getTagClassName();
        String tagInstanceName = this.getTagInstanceName(node);
        String parentTagInstanceName = this.getTagInstanceName(node.getParent());
        String startFlagName = this.getVariableName(node, "_jsp_start_flag_");
        String flagName = this.getVariableName(node, "_jsp_flag_");
        String bodyContentInstanceName = this.getVariableName(node, "_jsp_body_content_");
        boolean hasParent = this.hasParent(node);
        boolean isTryCatchFinallyTag = this.isAssignableFrom(tagClassName, TryCatchFinally.class);
        String prefix = indent;

        if(node.getOffset() == index) {
            writer.println(prefix + tagClassName + " " + tagInstanceName + " = new " + tagClassName + "();");
            writer.println(prefix + "try {");
            prefix = prefix + "    ";

            writer.println(prefix + tagInstanceName + ".setPageContext(pageContext);");

            if(hasParent) {
                writer.println(prefix + tagInstanceName + ".setParent(" + parentTagInstanceName + ");");
            }
            else {
                writer.println(prefix + tagInstanceName + ".setParent((Tag)null);");
            }

            this.setAttributes(prefix, tagClassName, tagInstanceName, node.getAttributes(), writer);

            if(this.isAssignableFrom(tagClassName, SimpleTag.class)) {
                String jspFragmentClassName = this.getVariableName(node, "JspFragment_");
                String jspFragmentInstanceName = this.getVariableName(node, "_jsp_fragment_");
                writer.println();
                writer.println(prefix + jspFragmentClassName + " " + jspFragmentInstanceName + " = new " + jspFragmentClassName + "();");
                writer.println(prefix + jspFragmentInstanceName + ".setPageContext(pageContext);");
                writer.println(prefix + jspFragmentInstanceName + ".setParent(" + tagInstanceName + ");");
                writer.println();
                writer.println(prefix + tagInstanceName + ".setJspBody(" + jspFragmentInstanceName + ");");
                writer.println(prefix + tagInstanceName + ".doTag();");
                return Tag.SKIP_BODY;
            }
            else {
                writer.println(prefix + "int " + startFlagName + " = " + tagInstanceName + ".doStartTag();");
                writer.println();
                writer.println(prefix + "if(" + startFlagName + " == Tag.SKIP_PAGE) {");
                writer.println(prefix + "    return;");
                writer.println(prefix + "}");
                writer.println(prefix + "if(" + startFlagName + " != Tag.SKIP_BODY) {");

                if(node.getLength() > 2) {
                    writer.println(prefix + "    int " + flagName + " = 0;");

                    if(this.isAssignableFrom(tagClassName, BodyTag.class)) {
                        writer.println(prefix + "    if(" + startFlagName + " == BodyTag.EVAL_BODY_BUFFERED) {");
                        writer.println(prefix + "        BodyContent " + bodyContentInstanceName + " = pageContext.pushBody();");
                        writer.println(prefix + "        " + tagInstanceName + ".setBodyContent(" + bodyContentInstanceName + ");");
                        writer.println(prefix + "        " + tagInstanceName + ".doInitBody();");
                        writer.println(prefix + "        out = " + bodyContentInstanceName + ";");
                        writer.println(prefix + "    }");
                    }

                    writer.println();
                    writer.println(prefix + "    do {");
                }
            }
        }
        else {
            prefix = prefix + "    ";

            if(!this.isAssignableFrom(tagClassName, SimpleTag.class)) {
                if(node.getLength() > 2) {
                    if(this.isAssignableFrom(tagClassName, IterationTag.class)) {
                        writer.println(prefix + "        " + flagName + " = " + tagInstanceName + ".doAfterBody();");
                    }

                    writer.println(prefix + "    }");
                    writer.println(prefix + "    while(" + flagName + " == IterationTag.EVAL_BODY_AGAIN);");

                    if(this.isAssignableFrom(tagClassName, BodyTag.class)) {
                        writer.println(prefix + "    if(" + startFlagName + " == BodyTag.EVAL_BODY_BUFFERED) {");
                        writer.println(prefix + "        out = pageContext.popBody();");
                        writer.println(prefix + "    }");
                    }
                }
                else {
                    if(this.isAssignableFrom(tagClassName, IterationTag.class)) {
                        writer.println(prefix + "    " + tagInstanceName + ".doAfterBody();");
                    }
                }
                writer.println(prefix + "}");
                writer.println(prefix + tagInstanceName+ ".doEndTag();");
            }

            prefix = prefix.substring(4);
            writer.println(prefix + "}");

            if(isTryCatchFinallyTag) {
                writer.println(prefix + "catch(Throwable throwable) {");
                writer.println(prefix + "    this.doCatch(" + tagInstanceName + ", throwable);");
                writer.println(prefix + "}");
                writer.println(prefix + "finally {");
                writer.println(prefix + "    this.doFinally(" + tagInstanceName + ");");
                writer.println(prefix + "}");
            }
            else {
                writer.println(prefix + "finally {");
                writer.println(prefix + "    this.doFinally(" + tagInstanceName + ");");
                writer.println(prefix + "}");
            }
        }
        return Tag.EVAL_PAGE;
    }

    /**
     * @param list
     * @param index
     * @return boolean
     */
    protected boolean isTagNode(List<Node> list, int index) {
        if(index < list.size()) {
            Node node = list.get(index);
            int nodeType = node.getNodeType();

            if(nodeType != NodeType.TEXT && nodeType != NodeType.VARIABLE && nodeType != NodeType.EXPRESSION) {
                if(node.getOffset() == index) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param node
     * @return boolean
     */
    protected boolean hasParent(Node node) {
        Node parent = node.getParent();

        if(this.fastJstl == false) {
            return (parent != null);
        }

        if(parent != null && parent instanceof TagNode) {
            String className = ((TagNode)parent).getTagClassName();
            return (FastJstl.has(className) == false);
        }
        return false;
    }

    /**
     * @param indent
     * @param tagClassName
     * @param tagInstanceName
     * @param attributes
     * @param writer
     */
    protected void setAttributes(String indent, String tagClassName, String tagInstanceName, Map<String, Attribute> attributes, PrintWriter writer) {
        if(attributes == null || attributes.size() < 1) {
            return;
        }

        if(this.isAssignableFrom(tagClassName, DynamicAttributes.class)) {
            for(Map.Entry<String, Attribute> entry : attributes.entrySet()) {
                String name = entry.getKey();
                Attribute value = entry.getValue();
                String valueExpression = this.getValueExpression(value);
                writer.println(indent + tagInstanceName + ".setDynamicAttribute(\"" + name + "\", " + valueExpression + ");");
            }
            return;
        }

        try {
            Class<?> clazz = ClassUtil.getClass(tagClassName);

            for(Map.Entry<String, Attribute> entry : attributes.entrySet()) {
                String name = entry.getKey();
                Attribute value = entry.getValue();
                String methodName = "set" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
                Method method = ClassUtil.getSetMethod(clazz, name);

                if(method != null) {
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    String valueExpression  = this.getValueExpression(value, parameterTypes[0]);
                    writer.println(indent + tagInstanceName + "." + methodName + "(" + valueExpression + ");");
                }
            }
        }
        catch(ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param tagClassName
     * @param parent
     * @return boolean
     */
    protected boolean isAssignableFrom(String tagClassName, Class<?> parent) {
        try {
            Class<?> clazz = ClassUtil.getClass(tagClassName);
            return parent.isAssignableFrom(clazz);
        }
        catch(ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param node
     * @return String
     */
    protected String getIndent(Node node) {
        int indent = 2;
        Node parent = node;
        StringBuilder buffer = new StringBuilder();

        if(this.fastJstl) {
            while(parent != null && (parent = parent.getParent()) != null) {
                String tagClassName = ((TagNode)parent).getTagClassName();

                if(tagClassName.equals("com.skin.ayada.jstl.core.ImportTag")) {
                }
                else if(tagClassName.equals("com.skin.ayada.jstl.core.IfTag")) {
                    indent += 1;
                }
                else if(tagClassName.equals("com.skin.ayada.jstl.core.SetTag")) {
                }
                else if(tagClassName.equals("com.skin.ayada.jstl.core.OutTag")) {
                }
                else if(tagClassName.equals("com.skin.ayada.jstl.core.ForEachTag")) {
                    indent += 2;
                }
                else if(tagClassName.equals("com.skin.ayada.jstl.core.ChooseTag")) {
                }
                else if(tagClassName.equals("com.skin.ayada.jstl.core.WhenTag")) {
                    indent += 1;
                }
                else if(tagClassName.equals("com.skin.ayada.jstl.core.OtherwiseTag")) {
                    indent += 1;
                }
                else if(tagClassName.equals("com.skin.ayada.jstl.core.CommentTag")) {
                    indent += 1;
                }
                else if(tagClassName.equals("com.skin.ayada.jstl.core.AttributeTag")) {
                }
                else if(tagClassName.equals("com.skin.ayada.jstl.core.PropertyTag")) {
                }
                else if(tagClassName.equals("com.skin.ayada.jstl.core.PrintTag")) {
                }
                else if(tagClassName.equals("com.skin.ayada.jstl.fmt.DateFormatTag")) {
                }
                else {
                    indent += 2;

                    if(this.isAssignableFrom(tagClassName, SimpleTag.class)) {
                        indent -= 1;
                        break;
                    }

                    if(this.isAssignableFrom(tagClassName, TryCatchFinally.class)) {
                      indent += 1;
                    }
                }
            }
        }
        else {
            while(parent != null && (parent = parent.getParent()) != null) {
                indent += 2;
                String tagClassName = ((TagNode)parent).getTagClassName();

                if(this.isAssignableFrom(tagClassName, SimpleTag.class)) {
                    indent -= 1;
                    break;
                }

                if(this.isAssignableFrom(tagClassName, TryCatchFinally.class)) {
                    indent += 1;
                }
            }
        }

        for(int i = 0; i < indent; i++) {
            buffer.append("    ");
        }
        return buffer.toString();
    }

    /**
     * @param node
     * @return String
     */
    protected String getTagInstanceName(Node node) {
        if(node != null) {
            String tagClassName = ((TagNode)node).getTagClassName();
            int k = tagClassName.lastIndexOf(".");

            if(k > -1) {
                return this.getVariableName(node, "_jsp_" + tagClassName.substring(k + 1) + "_");
            }
            return this.getVariableName(node, "_jsp_" + tagClassName + "_");
        }
        else {
            return "_jsp_undefined";
        }
    }

    /**
     * @param node
     * @param prefix
     * @return String
     */
    protected String getVariableName(Node node, String prefix) {
        if(node != null) {
            return prefix + (node.getOffset() + 1);
        }
        else {
            return prefix + "_undefined";
        }
    }

    /**
     * @param attribute
     * @return String
     */
    protected String getValueExpression(Attribute attribute) {
        return this.getValueExpression(attribute, (Class<?>)null);
    }

    /**
     * @param attribute
     * @return String
     */
    protected String getValueExpression(Attribute attribute, Class<?> expectType) {
        if(attribute == null) {
            return "null";
        }

        int type = attribute.getType();
        String expression = StringUtil.escape(attribute.getText());

        if(expectType == null) {
            if(type == Attribute.VARIABLE) {
                return "pageContext.getAttribute(\"" + expression + "\")";
            }
            else if(type == Attribute.EXPRESSION) {
                return "expressionContext.getValue(\"" + expression + "\")";
            }
            else if(type == Attribute.BOOLEAN) {
                return attribute.getValue().toString();
            }
            else if(type == Attribute.NUMBER) {
                return attribute.getValue().toString();
            }
            else if(type == Attribute.STRING) {
                return "\"" + expression + "\"";
            }
            else if(type == Attribute.MIX_EXPRESSION) {
                return "ELUtil.replace(expressionContext, \"" + expression + "\")";
            }
            else if(type == Attribute.JSP_EXPRESSION) {
                return "(" + expression + ")";
            }
            else {
                return "\"" + expression + "\"";
            }
        }

        if(type == Attribute.VARIABLE) {
            if(expectType == char.class || expectType == Character.class) {
                return "pageContext.getString(\"" + expression + "\").charAt(0)";
            }
            else if(expectType == boolean.class || expectType == Boolean.class) {
                return "pageContext.getBoolean(\"" + expression + "\")";
            }
            else if(expectType == byte.class || expectType == Byte.class) {
                return "pageContext.getByte(\"" + expression + "\")";
            }
            else if(expectType == short.class || expectType == Short.class) {
                return "pageContext.getShort(\"" + expression + "\")";
            }
            else if(expectType == int.class || expectType == Integer.class) {
                return "pageContext.getInteger(\"" + expression + "\")";
            }
            else if(expectType == float.class || expectType == Float.class) {
                return "pageContext.getFloat(\"" + expression + "\")";
            }
            else if(expectType == double.class || expectType == Double.class) {
                return "pageContext.getDouble(\"" + expression + "\")";
            }
            else if(expectType == long.class || expectType == Long.class) {
                return "pageContext.getLong(\"" + expression + "\")";
            }
            else if(expectType == String.class) {
                return "pageContext.getString(\"" + expression + "\")";
            }
            else if(expectType == Object.class) {
                return "pageContext.getAttribute(\"" + expression + "\")";
            }
            else {
                return "pageContext.getValue(\"" + expression + "\", " + expectType.getName() + ".class)";
            }
        }
        else if(type == Attribute.EXPRESSION) {
            if(expectType == char.class || expectType == Character.class) {
                return "expressionContext.getString(\"" + expression + "\").charAt(0)";
            }
            else if(expectType == boolean.class || expectType == Boolean.class) {
                return "expressionContext.getBoolean(\"" + expression + "\")";
            }
            else if(expectType == byte.class || expectType == Byte.class) {
                return "expressionContext.getByte(\"" + expression + "\")";
            }
            else if(expectType == short.class || expectType == Short.class) {
                return "expressionContext.getShort(\"" + expression + "\")";
            }
            else if(expectType == int.class || expectType == Integer.class) {
                return "expressionContext.getInteger(\"" + expression + "\")";
            }
            else if(expectType == float.class || expectType == Float.class) {
                return "expressionContext.getFloat(\"" + expression + "\")";
            }
            else if(expectType == double.class || expectType == Double.class) {
                return "expressionContext.getDouble(\"" + expression + "\")";
            }
            else if(expectType == long.class || expectType == Long.class) {
                return "expressionContext.getLong(\"" + expression + "\")";
            }
            else if(expectType == String.class) {
                return "expressionContext.getString(\"" + expression + "\")";
            }
            else if(expectType == Object.class) {
                return "expressionContext.getAttribute(\"" + expression + "\")";
            }
            else {
                return "expressionContext.getValue(\"" + expression + "\", " + expectType.getName() + ".class)";
            }
        }
        else if(type == Attribute.MIX_EXPRESSION) {
            if(expectType == char.class || expectType == Character.class) {
                return "ELUtil.getString(expressionContext, \"" + expression + "\").charAt(0)";
            }
            else if(expectType == boolean.class || expectType == Boolean.class) {
                return "ELUtil.getBoolean(expressionContext, \"" + expression + "\")";
            }
            else if(expectType == byte.class || expectType == Byte.class) {
                return "ELUtil.getByte(expressionContext, \"" + expression + "\")";
            }
            else if(expectType == short.class || expectType == Short.class) {
                return "expressionContext.getShort(expressionContext, \"" + expression + "\")";
            }
            else if(expectType == int.class || expectType == Integer.class) {
                return "ELUtil.getInteger(expressionContext, \"" + expression + "\")";
            }
            else if(expectType == float.class || expectType == Float.class) {
                return "ELUtil.getFloat(expressionContext, \"" + expression + "\")";
            }
            else if(expectType == double.class || expectType == Double.class) {
                return "ELUtil.getDouble(expressionContext, \"" + expression + "\")";
            }
            else if(expectType == long.class || expectType == Long.class) {
                return "ELUtil.getLong(expressionContext, \"" + expression + "\")";
            }
            else if(expectType == String.class) {
                return "ELUtil.getString(expressionContext, \"" + expression + "\")";
            }
            else if(expectType == Object.class) {
                return "ELUtil.getValue(expressionContext, \"" + expression + "\")";
            }
            else {
                return "(" + expectType.getName() + ")(ELUtil.getValue(expressionContext, \"" + expression + "\", " + expectType.getName() + ".class))";
            }
        }
        else if(type == Attribute.JSP_EXPRESSION) {
            return "(" + expression + ")";
        }
        else {
            /**
             * 字符串或者常量
             */
            Object value = attribute.getValue();

            if(expectType == char.class || expectType == Character.class) {
                return "'" + StringUtil.escape(value.toString().charAt(0)) + "'";
            }
            else if(expectType == boolean.class || expectType == Boolean.class) {
                return (value.toString().equals("true") ? "true" : "false");
            }
            else if(expectType == byte.class || expectType == Byte.class) {
                return "((byte)" + ((Number)value).intValue() + ")";
            }
            else if(expectType == short.class || expectType == Short.class) {
                return "((short)" + ((Number)value).intValue() + ")";
            }
            else if(expectType == int.class || expectType == Integer.class) {
                return Integer.toString(((Number)value).intValue());
            }
            else if(expectType == float.class || expectType == Float.class) {
                return ((Number)value).floatValue() + "f";
            }
            else if(expectType == double.class || expectType == Double.class) {
                return ((Number)value).doubleValue() + "d";
            }
            else if(expectType == long.class || expectType == Long.class) {
                return ((Number)value).longValue() + "L";
            }
            else if(expectType == String.class) {
                return "\"" + expression + "\"";
            }
            else if(expectType == Object.class) {
                if(value instanceof Number || value instanceof Boolean) {
                    return value.toString();
                }
                else {
                    return "\"" + expression + "\"";
                }
            }
            else {
                return "\"" + expression + "\"";
            }
        }
    }

    /**
     * @return String
     */
    public static String getJavaTemplate() {
        String resource = TemplateConfig.getJavaTemplate();

        if(resource != null) {
            return ClassPathResource.getResourceAsString(resource);
        }
        else {
            return ClassPathResource.getResourceAsString("com/skin/ayada/compile/class.jsp");
        }
    }

    /**
     * @param source
     * @param context
     * @return String
     */
    protected String replace(String source, Map<String, String> context) {
        char c;
        int length = source.length();
        StringBuilder name = new StringBuilder();
        CharBuffer result = new CharBuffer(4096);

        for(int i = 0; i < length; i++) {
            c = source.charAt(i);

            if(c == '$' && i < length - 1 && source.charAt(i + 1) == '{') {
                for(i = i + 2; i < length; i++) {
                    c = source.charAt(i);

                    if(c == '}') {
                        String value = context.get(name.toString().trim());

                        if(value != null) {
                            result.append(value);
                        }
                        break;
                    }
                    else {
                        name.append(c);
                    }
                }
                name.setLength(0);
            }
            else {
                result.append(c);
            }
        }
        return result.toString();
    }

    /**
     * @param fastJstl the fastJstl to set
     */
    public void setFastJstl(boolean fastJstl) {
        this.fastJstl = fastJstl;
    }

    /**
     * @return the fastJstl
     */
    public boolean getFastJstl() {
        return this.fastJstl;
    }
}
