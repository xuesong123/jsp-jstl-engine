/*
 * $RCSfile: JspCompiler.java,v $$
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

import com.skin.ayada.Version;
import com.skin.ayada.config.TemplateConfig;
import com.skin.ayada.io.CharBuffer;
import com.skin.ayada.io.ChunkWriter;
import com.skin.ayada.resource.ClassPathResource;
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
import com.skin.ayada.template.Template;
import com.skin.ayada.util.ClassUtil;
import com.skin.ayada.util.DateUtil;
import com.skin.ayada.util.ExpressionUtil;
import com.skin.ayada.util.HtmlUtil;
import com.skin.ayada.util.NodeUtil;
import com.skin.ayada.util.StringUtil;
import com.skin.ayada.util.TagUtil;

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

    public String compile(Template template, String packageName, String className) {
        logger.info("compile: {}.{}", packageName, className);

        Date date = new Date();
        String jspDirective = this.getJspDirective(template);
        String jspDeclaration = this.getJspDeclaration(template);
        String methodBody = this.getMethodBody(template);
        String subClassBody = this.getSubClassBody(template);
        String staticDeclaration = this.getStaticDeclaration(template);

        Map<String, String> context = new HashMap<String, String>();
        context.put("java.className", className);
        context.put("java.packageName", packageName);
        context.put("build.date", DateUtil.format(date, "yyyy-MM-dd"));
        context.put("build.time", DateUtil.format(date, "yyyy-MM-dd HH:mm:ss SSS"));
        context.put("template.home", StringUtil.replace(template.getHome(), "\\", "/"));
        context.put("template.path", StringUtil.replace(template.getPath(), "\\", "/"));
        context.put("template.lastModified", DateUtil.format(template.getLastModified(), "yyyy-MM-dd HH:mm:ss SSS"));
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
    public String getJspDirective(Template template) {
        Node node = null;
        List<Node> list = template.getNodes();
        ChunkWriter chunkWriter = new ChunkWriter(4096);
        PrintWriter writer = new PrintWriter(chunkWriter);

        for(int index = 0, size = list.size(); index < size; index++) {
            node = list.get(index);

            if(node.getNodeType() == NodeType.JSP_DIRECTIVE_PAGE) {
                if(node.getOffset() == index && node.getAttribute("import") != null) {
                    writer.println("import " + node.getAttribute("import") + "; // jsp:directive.import: lineNumber: " + node.getLineNumber());
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
    public String getJspDeclaration(Template template) {
        Node node = null;
        List<Node> list = template.getNodes();
        ChunkWriter chunkWriter = new ChunkWriter(4096);
        PrintWriter writer = new PrintWriter(chunkWriter);

        for(int index = 0, size = list.size(); index < size; index++) {
            node = list.get(index);

            if(node.getNodeType() == NodeType.JSP_DECLARATION) {
                if(node.getOffset() == index) {
                    writer.println("    // JSP_DECLARATION: lineNumber: " + node.getLineNumber());
                    writer.print(node.getTextContent());
                }
                else {
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
    public String getStaticDeclaration(Template template) {
        Node node = null;
        List<Node> list = template.getNodes();
        ChunkWriter chunkWriter = new ChunkWriter(4096);
        PrintWriter writer = new PrintWriter(chunkWriter);

        for(int index = 0, size = list.size(); index < size; index++) {
            node = list.get(index);

            if(node.getNodeType() == NodeType.TEXT) {
                writer.print("    public static final char[] " + this.getVariableName(node, "_jsp_string_"));
                writer.print(" = \"");
                writer.print(StringUtil.escape(node.getTextContent()));
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
    public String getMethodBody(Template template) {
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
    public String getSubClassBody(Template template) {
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
                writer.println("    // NODE START: lineNumber: " + node.getLineNumber() + ", offset: " + node.getOffset() + ", length: " + node.getLength() + ", tagClassName: " + tagClassName + ", tagInstanceName: " + tagInstanceName);
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
                writer.println("    // NODE END: lineNumber: " + node.getLineNumber() + ", tagClassName: " + tagClassName + ", tagInstanceName: " + tagInstanceName);
            }
        }
    }

    /**
     * @param writer
     * @param list
     * @param offset
     * @param length
     */
    public void writeBody(PrintWriter writer, List<Node> list, int offset, int length) {
        int nodeType = 0;
        Node node = null;
        String indent = null;
        String variable = null;

        for(int index = offset, end = offset + length; index < end; index++) {
            node = list.get(index);
            indent = this.getIndent(node);
            nodeType = node.getNodeType();

            if(nodeType == NodeType.TEXT) {
                variable = this.getVariableName(node, "_jsp_string_");
                writer.println(indent + "// TEXT: lineNumber: " + node.getLineNumber());
                writer.println(indent + "// out.write(\"" + StringUtil.escape(node.getTextContent()) + "\");");
                writer.println(indent + "out.write(" + variable + ", 0, " + variable + ".length);");

                if(this.isTagNode(list, index + 1)) {
                    writer.println();
                }
                continue;
            }

            if(nodeType == NodeType.VARIABLE) {
                String textContent = node.getTextContent();
                writer.println(indent + "// VARIABLE: lineNumber: " + node.getLineNumber());

                if("#".equals(((Variable)node).getFlag())) {
                    writer.println(indent + "out.write(pageContext.getAttribute(\"" + textContent + "\"));");
                }
                else {
                    writer.println(indent + "expressionContext.print(out, pageContext.getAttribute(\"" + textContent + "\"));");
                }

                if(this.isTagNode(list, index + 1)) {
                    writer.println();
                }
                continue;
            }

            if(nodeType == NodeType.EXPRESSION) {
                String textContent = node.getTextContent();
                writer.println(indent + "// EXPRESSION: lineNumber: " + node.getLineNumber());

                if("#".equals(((Expression)node).getFlag())) {
                    writer.println(indent + "out.write(expressionContext.getString(\"" + StringUtil.escape(textContent) + "\"));");
                }
                else {
                    writer.println(indent + "expressionContext.print(out, expressionContext.getString(\"" + StringUtil.escape(textContent) + "\"));");
                }

                if(this.isTagNode(list, index + 1)) {
                    writer.println();
                }
                continue;
            }

            if(node.getLength() == 0) {
                throw new RuntimeException("Exception at line #" + node.getLineNumber() + " " + NodeUtil.getDescription(node) + " not match !");
            }

            int flag = this.write(writer, node, index, indent);

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
     * @param node
     * @param index
     * @param indent
     * @return int
     */
    public int write(PrintWriter writer, Node node, int index, String indent) {
        /** Jsp Support */
        int nodeType = node.getNodeType();

        if(node.getOffset() == index) {
            switch (nodeType) {
                case NodeType.JSP_DECLARATION: {
                    writer.println(indent + "// jsp:declaration: lineNumber: " + node.getLineNumber());
                    writer.println(indent + "// " + NodeUtil.getDescription(node));
                    return Tag.EVAL_PAGE;
                }
                case NodeType.JSP_DIRECTIVE_PAGE: {
                    writer.println(indent + "// jsp:directive.page: lineNumber: " + node.getLineNumber());
                    writer.println(indent + "// " + NodeUtil.getDescription(node));
                    return Tag.EVAL_PAGE;
                }
                case NodeType.JSP_DIRECTIVE_TAGLIB: {
                    writer.println(indent + "// jsp:directive.taglib: lineNumber: " + node.getLineNumber());
                    writer.println(indent + "// " + NodeUtil.getDescription(node));
                    return Tag.EVAL_PAGE;
                }
                case NodeType.JSP_DIRECTIVE_INCLUDE: {
                    writer.println(indent + "// jsp:directive.include: lineNumber: " + node.getLineNumber());
                    writer.println(indent + "// " + NodeUtil.getDescription(node));
                    return Tag.EVAL_PAGE;
                }
                case NodeType.JSP_SCRIPTLET: {
                    writer.println(indent + "// jsp:scriptlet: lineNumber: " + node.getLineNumber());
                    writer.println(node.getTextContent());
                    return Tag.SKIP_BODY;
                }
                case NodeType.JSP_EXPRESSION: {
                    writer.println(indent + "// jsp:expression: lineNumber: " + node.getLineNumber());
                    // writer.println(indent + "out.print(" + node.getTextContent() + ");");
                    writer.println(indent + "expressionContext.print(out, (" + node.getTextContent() + "));");
                    return Tag.SKIP_BODY;
                }
            }
        }
        else {
            switch (nodeType) {
                case NodeType.JSP_DECLARATION: {
                    // writer.println(indent + "// jsp:declaration END");
                    return Tag.EVAL_PAGE;
                }
                case NodeType.JSP_DIRECTIVE_PAGE: {
                    // writer.println(indent + "// jsp:directive.page END");
                    return Tag.EVAL_PAGE;
                }
                case NodeType.JSP_DIRECTIVE_TAGLIB: {
                    // writer.println(indent + "// jsp:directive.taglib END");
                    return Tag.EVAL_PAGE;
                }
                case NodeType.JSP_DIRECTIVE_INCLUDE: {
                    // writer.println(indent + "// jsp:directive.include END");
                    return Tag.EVAL_PAGE;
                }
                case NodeType.JSP_SCRIPTLET: {
                    writer.println(indent + "// jsp:scriptlet END");
                    return Tag.EVAL_PAGE;
                }
                case NodeType.JSP_EXPRESSION: {
                    writer.println(indent + "// jsp:expression END");
                    return Tag.EVAL_PAGE;
                }
            }
        }

        /** Tag Support */
        String tagClassName = ((TagNode)node).getTagClassName();
        String tagInstanceName = this.getTagInstanceName(node);

        if(node.getOffset() == index) {
            writer.println(indent + "// NODE START: lineNumber: " + node.getLineNumber() + ", offset: " + node.getOffset() + ", length: " + node.getLength() + ", tagClassName: " + tagClassName + ", tagInstanceName: " + tagInstanceName);
            writer.println(indent + "// " + StringUtil.escape(NodeUtil.getDescription(node)));
        }

        int flag = Tag.EVAL_PAGE;

        if(this.fastJstl && tagClassName.startsWith("com.skin.ayada.jstl.")) {
            if(tagClassName.endsWith(".IfTag")) {
                flag = this.writeIfTag(writer, node, index, indent);
            }
            else if(tagClassName.endsWith(".SetTag")) {
                flag = this.writeSetTag(writer, node, index, indent);
            }
            else if(tagClassName.endsWith(".OutTag")) {
                flag = this.writeOutTag(writer, node, index, indent);
            }
            else if(tagClassName.endsWith(".ForEachTag")) {
                flag = this.writeForEachTag(writer, node, index, indent);
            }
            else if(tagClassName.endsWith(".ChooseTag")) {
                flag = this.writeChooseTag(writer, node, index, indent);
            }
            else if(tagClassName.endsWith(".WhenTag")) {
                flag = this.writeWhenTag(writer, node, index, indent);
            }
            else if(tagClassName.endsWith(".OtherwiseTag")) {
                flag = this.writeOtherwiseTag(writer, node, index, indent);
            }
            else if(tagClassName.endsWith(".CommentTag")) {
                flag = this.writeCommentTag(writer, node, index, indent);
            }
            else if(tagClassName.endsWith(".PrintTag")) {
                flag = this.writePrintTag(writer, node, index, indent);
            }
            else if(tagClassName.endsWith(".AttributeTag")) {
                flag = this.writeAttributeTag(writer, node, index, indent);
            }
            else if(tagClassName.endsWith(".ElementTag")) {
                flag = this.writeElementTag(writer, node, index, indent);
            }
            else if(tagClassName.endsWith(".ConstructorTag")) {
                flag = this.writeConstructorTag(writer, node, index, indent);
            }
            else if(tagClassName.endsWith(".PropertyTag")) {
                flag = this.writePropertyTag(writer, node, index, indent);
            }
            else if(tagClassName.endsWith(".ParameterTag")) {
                flag = this.writePrameterTag(writer, node, index, indent);
            }
            else if(tagClassName.endsWith(".ExecuteTag")) {
                flag = this.writeExecuteTag(writer, node, index, indent);
            }
            else if(tagClassName.endsWith(".ExitTag")) {
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
            writer.println(indent + "// NODE END: lineNumber: " + node.getLineNumber() + ", tagClassName: " + tagClassName + ", tagInstanceName: " + tagInstanceName);
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
            writer.println(indent + "if(ExpressionUtil.getBoolean(expressionContext, \"" + StringUtil.escape(node.getAttribute("test")) + "\")) {");
        }
        else {
            writer.println(indent + "} // jsp.jstl.core.IfTag END");
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
        String name = node.getAttribute("var");
        String value = this.getValueExpression(node.getAttribute("value"));
        String target = this.getValueExpression(node.getAttribute("target"));
        String property = node.getAttribute("property");

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
            writer.println(indent + "// jsp.jstl.core.SetTag END");
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
        String value = node.getAttribute("value");
        boolean escapeXml = "true".equals(node.getAttribute("escapeXml"));

        if(node.getOffset() == index) {
            if(value != null) {
                writer.println(indent + "com.skin.ayada.jstl.core.OutTag.write(out, " + this.getValueExpression(value) + ", " + escapeXml + ");");
                 writer.println(indent + "// out.print(" + this.getStringExpression(value, escapeXml) + ");");
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
        writer.println(indent + "// jsp.jstl.core.OutTag END");
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
            String items = node.getAttribute("items");
            String variable = node.getAttribute("var");
            String begin = node.getAttribute("begin");
            String step = node.getAttribute("step");
            String end = node.getAttribute("end");
            String varStatus = node.getAttribute("varStatus");
            boolean hasParent = this.hasParent(node);

            if(variable != null && variable.trim().length() > 0) {
                writer.println(indent + "Object " + forEachOldVar + " = pageContext.getAttribute(\"" + variable.trim() + "\");");
            }

            if(varStatus != null && varStatus.trim().length() > 0) {
                writer.println(indent + "Object " + forEachOldVarStatus + " = pageContext.getAttribute(\"" + varStatus.trim() + "\");");
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
                writer.println(indent + tagInstanceName + ".setVar(\"" + StringUtil.escape(variable) + "\");");
            }

            if(items != null) {
                if(items.indexOf("${") > -1) {
                    String varItems = this.getVariable(items);

                    if(varItems != null) {
                        writer.println(indent + tagInstanceName + ".setItems(pageContext.getAttribute(\"" + varItems + "\"));");
                    }
                    else {
                        writer.println(indent + tagInstanceName + ".setItems(ExpressionUtil.evaluate(expressionContext, \"" + StringUtil.escape(items) + "\", null));");
                    }
                }
                else {
                    writer.println(indent + tagInstanceName + ".setItems(\"" + StringUtil.escape(items) + "\");");
                }
            }

            if(begin != null && end != null) {
                if(begin.indexOf("${") < 0) {
                    Object beginValue = ExpressionUtil.getValue(begin);
                    if((beginValue instanceof String) == false) {
                        writer.println(indent + tagInstanceName + ".setBegin(" + begin + ");");
                    }
                }
                else {
                    writer.println(indent + tagInstanceName + ".setBegin(ExpressionUtil.getInteger(expressionContext, \"" + begin + "\"));");
                }

                if(end.indexOf("${") < 0) {
                    Object endValue = ExpressionUtil.getValue(end);
                    if((endValue instanceof String) == false) {
                        writer.println(indent + tagInstanceName + ".setEnd(" + end + ");");
                    }
                }
                else {
                    writer.println(indent + tagInstanceName + ".setEnd(ExpressionUtil.getInteger(expressionContext, \"" + end + "\"));");
                }
            }

            if(step != null) {
                if(step.indexOf("${") < 0) {
                    Object stepValue = ExpressionUtil.getValue(step);

                    if((stepValue instanceof String) == false) {
                        writer.println(indent + tagInstanceName + ".setStep(" + step + ");");
                    }
                }
                else {
                    writer.println(indent + tagInstanceName + ".setStep(ExpressionUtil.getInteger(expressionContext, \"" + step + "\"));");
                }
            }

            if(varStatus != null) {
                writer.println(indent + tagInstanceName + ".setVarStatus(\"" + varStatus + "\");");
            }
            writer.println(indent + "if(" + tagInstanceName + ".doStartTag() != Tag.SKIP_BODY) {");
            writer.println(indent + "    while(true) {");
        }
        else {
            String variable = node.getAttribute("var");
            String varStatus = node.getAttribute("varStatus");
            writer.println(indent + "        if(" + tagInstanceName + ".doAfterBody() != IterationTag.EVAL_BODY_AGAIN) {");
            writer.println(indent + "            break;");
            writer.println(indent + "        }");
            writer.println(indent + "    }");
            writer.println(indent + "}");
            writer.println(indent + tagInstanceName + ".release();");

            if(variable != null && variable.trim().length() > 0) {
                writer.println(indent + "pageContext.setAttribute(\"" + variable.trim() + "\", " + forEachOldVar + ");");
            }

            if(varStatus != null && varStatus.trim().length() > 0) {
                writer.println(indent + "pageContext.setAttribute(\"" + varStatus.trim() + "\", " + forEachOldVarStatus + ");");
            }
            writer.println(indent + "// jsp.jstl.core.ForEachTag END");
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
            writer.println(indent + "boolean " + tagInstanceName + " = true;");
            writer.println();
        }
        else {
            writer.println(indent + "// jsp.jstl.core.ChooseTag END");
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
            String parentTagInstanceName = this.getTagInstanceName(node.getParent());
            writer.println(indent + "if(" + parentTagInstanceName + " && ExpressionUtil.getBoolean(expressionContext, \"" + StringUtil.escape(node.getAttribute("test")) + "\")) {");
            writer.println(indent + "    " + parentTagInstanceName + " = false;");
        }
        else {
            writer.println(indent + "} // jsp.jstl.core.WhenTag END");
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
            String parentTagInstanceName = this.getTagInstanceName(node.getParent());
            writer.println(indent + "if(" + parentTagInstanceName + ") {");
            writer.println(indent + "    " + parentTagInstanceName + " = false;");
        }
        else {
            writer.println(indent + "} // jsp.jstl.core.OtherwiseTag END");
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
            writer.println(indent + "// jsp.jstl.fmt.FormatDateTag END");
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
            writer.println(indent + "if(com.skin.ayada.jstl.core.ContinueTag.getTrue()) {");
        }
        else {
            writer.println(indent + "} // jsp.jstl.core.CommentTag END");
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
            String out = node.getAttribute("out");
            String value = node.getAttribute("value");

            if(out == null || out.trim().length() < 1) {
                out = "null";
            }
            else {
                out = "ExpressionUtil.evaluate(expressionContext, \"" + StringUtil.escape(out) + "\", null)";
            }
            writer.println(indent + "com.skin.ayada.jstl.core.PrintTag.print(pageContext, " + out + ", " + this.getValueExpression(value) + ");");
        }
        else {
            writer.println(indent + "// jsp.jstl.core.PrintTag END");
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
        String name = node.getAttribute("name");
        String value = node.getAttribute("value");
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
        writer.println(indent + "// jsp.jstl.core.AttributeTag END");
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
        String indek = node.getAttribute("index");
        String value = node.getAttribute("value");
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
        writer.println(indent + "// jsp.jstl.core.ElementTag END");
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
        String name = node.getAttribute("type");
        String value = node.getAttribute("value");
        String parentTagInstanceName = this.getTagInstanceName(node.getParent());

        if(node.getOffset() == index) {
            if(value != null) {
                writer.println(indent + "com.skin.ayada.jstl.core.ConstructorTag.setArgument(" + parentTagInstanceName + ", \"" + name + "\", " + this.getValueExpression(value) + ");");
                return Tag.SKIP_BODY;
            }
            return Tag.EVAL_PAGE;
        }
        writer.println(indent + "// jsp.jstl.core.ConstructorTag END");
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
        String name = node.getAttribute("name");
        String value = node.getAttribute("value");
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
        writer.println(indent + "// jsp.jstl.core.PropertyTag END");
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
        String name = node.getAttribute("name");
        String value = node.getAttribute("value");
        String parentTagInstanceName = this.getTagInstanceName(node.getParent());

        if(node.getOffset() == index) {
            if(value != null) {
                writer.println(indent + parentTagInstanceName + ".setParameter(\"" + name + "\", " + this.getStringExpression(value, false) + ");");
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
        writer.println(indent + "// jsp.jstl.core.PrameterTag END");
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
            String name = node.getAttribute("var");
            String value = this.getValueExpression(node.getAttribute("value"));

            if(name != null) {
                writer.println(indent + "pageContext.setAttribute(\"" + name + "\", " + value + ");");
            }
            else {
                writer.println(indent + value + ";");
            }
        }
        else {
            writer.println(indent + "// jsp.jstl.core.ExecuteTag END");
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
            String test = node.getAttribute("test");

            if(test != null) {
                writer.println(indent + "if(ExpressionUtil.getBoolean(expressionContext, \"" + StringUtil.escape(node.getAttribute("test")) + "\")) {");
            }
            else {
                writer.println(indent + "if(com.skin.ayada.jstl.core.ContinueTag.getTrue()) {");
            }
            writer.println(indent + "    return;");
            writer.println(indent + "}");
            return Tag.SKIP_BODY;
        }
        writer.println(indent + "// jsp.jstl.core.ExitTag END");
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

            if(isTryCatchFinallyTag) {
                writer.println(prefix + "try {");
                prefix = prefix + "    ";
            }

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
                writer.println(prefix + tagInstanceName+ ".release();");
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
            if(isTryCatchFinallyTag) {
                prefix = prefix + "    ";
            }

            if(this.isAssignableFrom(tagClassName, SimpleTag.class)) {
            }
            else {
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
                writer.println(prefix + tagInstanceName + ".release();");
            }

            if(isTryCatchFinallyTag) {
                prefix = prefix.substring(4);
                writer.println(prefix + "}");
                writer.println(prefix + "catch(Throwable throwable) {");
                writer.println(prefix + "    try {");
                writer.println(prefix + "        " + tagInstanceName + ".doCatch(throwable);");
                writer.println(prefix + "    } catch (Throwable t) {");
                writer.println(prefix + "        throw new Exception(t);");
                writer.println(prefix + "    }");
                writer.println(prefix + "}");
                writer.println(prefix + "finally {");
                writer.println(prefix + "    " + tagInstanceName + ".doFinally();");
                writer.println(prefix + "}");
            }
        }
        return Tag.EVAL_PAGE;
    }

    /**
     * @param expression
     * @return boolean
     */
    protected String getVariable(String expression) {
        List<Node> nodes = ExpressionUtil.parse(expression);

        if(nodes.size() == 1) {
            Node node = nodes.get(0);

            if(node instanceof Expression && this.isJavaIdentifier(node.getTextContent())) {
                return node.getTextContent();
            }
        }
        return null;
    }

    /**
     * @param expression
     * @return boolean
     */
    protected boolean isVariable(String expression) {
        List<Node> nodes = ExpressionUtil.parse(expression);

        if(nodes.size() == 1) {
            Node node = nodes.get(0);

            if(node instanceof Expression) {
                return this.isJavaIdentifier(node.getTextContent());
            }
        }
        return false;
    }

    /**
     * @param source
     * @return boolean
     */
    protected boolean isJavaIdentifier(String source) {
        if(Character.isJavaIdentifierStart(source.charAt(0)) == false) {
            return false;
        }

        for(int i = 0; i < source.length(); i++) {
            if(Character.isJavaIdentifierPart(source.charAt(i)) == false) {
                return false;
            }
        }
        return true;
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
    protected void setAttributes(String indent, String tagClassName, String tagInstanceName, Map<String, String> attributes, PrintWriter writer) {
        if(attributes == null || attributes.size() < 1) {
            return;
        }

        if(this.isAssignableFrom(tagClassName, DynamicAttributes.class)) {
            for(Map.Entry<String, String> entry : attributes.entrySet()) {
                String name = entry.getKey();
                String value = entry.getValue();

                if(value.indexOf("${") < 0) {
                    String valueExpression = this.getValueExpression(value);
                    writer.println(indent + tagInstanceName + ".setDynamicAttribute(\"" + name + "\", " + valueExpression + ");");
                }
                else {
                    String valueExpression = this.getValueExpression(value);
                    writer.println(indent + tagInstanceName + ".setDynamicAttribute(\"" + name + "\", " + valueExpression + ");");
                }
            }
            return;
        }

        try {
            Class<?> clazz = ClassUtil.getClass(tagClassName);

            for(Map.Entry<String, String> entry : attributes.entrySet()) {
                String name = entry.getKey();
                String value = entry.getValue();
                String methodName = "set" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
                Method method = TagUtil.getSetMethod(clazz, methodName);

                if(method != null) {
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    Class<?> parameterType = parameterTypes[0];
                    Object parameterValue = null;

                    /**
                     * constant
                     */
                    if(value.indexOf("${") < 0) {
                        parameterValue = ExpressionUtil.getValue(value);

                        if(parameterType == char.class || parameterType == Character.class) {
                            writer.println(indent + tagInstanceName + "." + methodName + "('" + StringUtil.escape(value).charAt(0) + "');");
                        }
                        else if(parameterType == boolean.class || parameterType == Boolean.class) {
                            writer.println(indent + tagInstanceName + "." + methodName + "(" + parameterValue.toString() + ");");
                        }
                        else if(parameterType == byte.class || parameterType == Byte.class) {
                            writer.println(indent + tagInstanceName + "." + methodName + "((byte)" + ((Number)parameterValue).intValue() + ");");
                        }
                        else if(parameterType == short.class || parameterType == Short.class) {
                            writer.println(indent + tagInstanceName + "." + methodName + "((short)" + ((Number)parameterValue).intValue() + ");");
                        }
                        else if(parameterType == int.class || parameterType == Integer.class) {
                            writer.println(indent + tagInstanceName + "." + methodName + "(" + ((Number)parameterValue).intValue() + ");");
                        }
                        else if(parameterType == float.class || parameterType == Float.class) {
                            writer.println(indent + tagInstanceName + "." + methodName + "(" + ((Number)parameterValue).floatValue() + "f);");
                        }
                        else if(parameterType == double.class || parameterType == Double.class) {
                            writer.println(indent + tagInstanceName + "." + methodName + "(" + ((Number)parameterValue).doubleValue() + "d);");
                        }
                        else if(parameterType == long.class || parameterType == Long.class) {
                            writer.println(indent + tagInstanceName + "." + methodName + "(" + ((Number)parameterValue).longValue() + "L);");
                        }
                        else if(parameterType == String.class) {
                            writer.println(indent + tagInstanceName + "." + methodName + "(\"" + StringUtil.escape(value) + "\");");
                        }
                        else if(parameterType == Object.class) {
                            if(parameterValue instanceof Number) {
                                writer.println(indent + tagInstanceName + "." + methodName + "(" + parameterValue.toString() + ");");
                            }
                            else {
                                writer.println(indent + tagInstanceName + "." + methodName + "(\"" + StringUtil.escape(value) + "\");");
                            }
                        }
                        else {
                            writer.println(indent + tagInstanceName + "." + methodName + "(\"" + StringUtil.escape(value) + "\");");
                        }
                    }
                    else {
                        /**
                         * expression
                         */
                        String variable = this.getVariable(value);

                        if(parameterType == char.class || parameterType == Character.class) {
                            if(variable != null) {
                                writer.println(indent + tagInstanceName + "." + methodName + "(pageContext.getString(\"" + variable + "\").charAt(0));");
                            }
                            else {
                                writer.println(indent + tagInstanceName + "." + methodName + "(ExpressionUtil.getString(expressionContext, \"" + StringUtil.escape(value) + "\").charAt(0));");
                            }
                        }
                        else if(parameterType == boolean.class || parameterType == Boolean.class) {
                            if(variable != null) {
                                writer.println(indent + tagInstanceName + "." + methodName + "(pageContext.getBoolean(\"" + variable + "\"));");
                            }
                            else {
                                writer.println(indent + tagInstanceName + "." + methodName + "(ExpressionUtil.getBoolean(expressionContext, \"" + StringUtil.escape(value) + "\"));");
                            }
                        }
                        else if(parameterType == byte.class || parameterType == Byte.class) {
                            if(variable != null) {
                                writer.println(indent + tagInstanceName + "." + methodName + "(pageContext.getByte(\"" + variable + "\"));");
                            }
                            else {
                                writer.println(indent + tagInstanceName + "." + methodName + "(ExpressionUtil.getByte(expressionContext, \"" + StringUtil.escape(value) + "\"));");
                            }
                        }
                        else if(parameterType == short.class || parameterType == Short.class) {
                            if(variable != null) {
                                writer.println(indent + tagInstanceName + "." + methodName + "(pageContext.getShort(\"" + variable + "\"));");
                            }
                            else {
                                writer.println(indent + tagInstanceName + "." + methodName + "(ExpressionUtil.getShort(expressionContext, \"" + StringUtil.escape(value) + "\"));");
                            }
                        }
                        else if(parameterType == int.class || parameterType == Integer.class) {
                            if(variable != null) {
                                writer.println(indent + tagInstanceName + "." + methodName + "(pageContext.getInteger(\"" + variable + "\"));");
                            }
                            else {
                                writer.println(indent + tagInstanceName + "." + methodName + "(ExpressionUtil.getInteger(expressionContext, \"" + StringUtil.escape(value) + "\"));");
                            }
                        }
                        else if(parameterType == float.class || parameterType == Float.class) {
                            if(variable != null) {
                                writer.println(indent + tagInstanceName + "." + methodName + "(pageContext.getFloat(\"" + variable + "\"));");
                            }
                            else {
                                writer.println(indent + tagInstanceName + "." + methodName + "(ExpressionUtil.getFloat(expressionContext, \"" + StringUtil.escape(value) + "\"));");
                            }
                        }
                        else if(parameterType == double.class || parameterType == Double.class) {
                            if(variable != null) {
                                writer.println(indent + tagInstanceName + "." + methodName + "(pageContext.getDouble(\"" + variable + "\"));");
                            }
                            else {
                                writer.println(indent + tagInstanceName + "." + methodName + "(ExpressionUtil.getDouble(expressionContext, \"" + StringUtil.escape(value) + "\"));");
                            }
                        }
                        else if(parameterType == long.class || parameterType == Long.class) {
                            if(variable != null) {
                                writer.println(indent + tagInstanceName + "." + methodName + "(pageContext.getLong(\"" + variable + "\"));");
                            }
                            else {
                                writer.println(indent + tagInstanceName + "." + methodName + "(ExpressionUtil.getLong(expressionContext, \"" + StringUtil.escape(value) + "\"));");
                            }
                        }
                        else if(parameterType == String.class) {
                            if(variable != null) {
                                writer.println(indent + tagInstanceName + "." + methodName + "(pageContext.getString(\"" + variable + "\"));");
                            }
                            else {
                                writer.println(indent + tagInstanceName + "." + methodName + "(ExpressionUtil.getString(expressionContext, \"" + StringUtil.escape(value) + "\"));");
                            }
                        }
                        else if(parameterType == Object.class) {
                            if(variable != null) {
                                writer.println(indent + tagInstanceName + "." + methodName + "(pageContext.getAttribute(\"" + variable + "\"));");
                            }
                            else {
                                writer.println(indent + tagInstanceName + "." + methodName + "(ExpressionUtil.evaluate(expressionContext, \"" + StringUtil.escape(value) + "\", " + parameterType.getName() + ".class));");
                            }
                        }
                        else {
                            if(variable != null) {
                                writer.println(indent + tagInstanceName + "." + methodName + "(pageContext.getValue(\"" + variable + "\", " + parameterType.getName() + ".class));");
                            }
                            else {
                                writer.println(indent + tagInstanceName + "." + methodName + "((" + parameterType.getName() + ")(ExpressionUtil.evaluate(expressionContext, \"" + StringUtil.escape(value) + "\", " + parameterType.getName() + ".class)));");
                            }
                        }
                    }
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
        Node parent = node;
        int indent = 2;
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
     * @param expression
     * @return String
     */
    protected String getStringExpression(String expression, boolean escapeXml) {
        if(expression != null) {
            if(expression.indexOf("${") > -1) {
                if(escapeXml) {
                    return "ExpressionUtil.getHtml(expressionContext, \"" + StringUtil.escape(expression) + "\")";
                }
                return "ExpressionUtil.getString(expressionContext, \"" + StringUtil.escape(expression) + "\")";
            }
            Object constant = ExpressionUtil.getValue(expression);

            if(constant instanceof String) {
                if(escapeXml) {
                    return "\"" + StringUtil.escape(HtmlUtil.encode(expression)) + "\"";
                }
                return "\"" + StringUtil.escape(expression) + "\"";
            }
            else if(constant instanceof Float) {
                return constant.toString() + "f";
            }
            else if(constant instanceof Double) {
                return constant.toString() + "d";
            }
            else if(constant instanceof Long) {
                return constant.toString() + "L";
            }
            return constant.toString();
        }
        return null;
    }

    /**
     * @param expression
     * @return String
     */
    protected String getValueExpression(String expression) {
        if(expression != null) {
            List<Node> nodes = ExpressionUtil.parse(expression);

            if(nodes.size() == 1) {
                Node node = nodes.get(0);

                if(node instanceof Expression) {
                    if(this.isJavaIdentifier(node.getTextContent())) {
                        return "pageContext.getAttribute(\"" + node.getTextContent() + "\")";
                    }
                    return "ExpressionUtil.evaluate(expressionContext, \"" + StringUtil.escape(expression) + "\", null)";
                }
                Object constant = ExpressionUtil.getValue(node.getTextContent());

                if(constant instanceof String) {
                    return "\"" + StringUtil.escape(expression) + "\"";
                }
                else if(constant instanceof Float) {
                    return constant.toString() + "f";
                }
                else if(constant instanceof Double) {
                    return constant.toString() + "d";
                }
                else if(constant instanceof Long) {
                    return constant.toString() + "L";
                }
                else {
                    return constant.toString();
                }
            }
            return "ExpressionUtil.evaluate(expressionContext, \"" + StringUtil.escape(expression) + "\", null)";
        }
        else {
            return null;
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
    public String replace(String source, Map<String, String> context) {
        char c;
        StringBuilder name = new StringBuilder();
        CharBuffer result = new CharBuffer(4096);

        for(int i = 0; i < source.length(); i++) {
            c = source.charAt(i);

            if(c == '$' && i < source.length() - 1 && source.charAt(i + 1) == '{') {
                for(int j = i + 2; j < source.length(); j++) {
                    i = j;
                    c = source.charAt(j);

                    if(c == '}') {
                        String value = context.get(name.toString());

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
