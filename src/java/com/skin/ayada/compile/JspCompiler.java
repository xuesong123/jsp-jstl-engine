/*
 * $RCSfile: ClassCompiler.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-8 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.compile;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.skin.ayada.statement.Node;
import com.skin.ayada.statement.NodeType;
import com.skin.ayada.tagext.BodyTag;
import com.skin.ayada.tagext.IterationTag;
import com.skin.ayada.template.Template;
import com.skin.ayada.util.ClassUtil;
import com.skin.ayada.util.DateUtil;
import com.skin.ayada.util.ExpressionUtil;
import com.skin.ayada.util.HtmlUtil;
import com.skin.ayada.util.NodeUtil;
import com.skin.ayada.util.StringUtil;
import com.skin.ayada.util.TagUtil;

/**
 * <p>Title: ClassCompiler</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class JspCompiler
{
    private boolean fast = true;

    public String compile(Template template, String className, String packageName)
    {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        Node node = null;
        List<Node> list = template.getNodes();
        this.writeHeader(className, packageName, template.getLastModified(), list, writer);
        writer.println("/**");
        writer.println(" * <p>Title: " + className + "</p>");
        writer.println(" * <p>Description: </p>");
        writer.println(" * <p>Copyright: Copyright (c) 2006</p>");
        writer.println(" * @author JspCompiler");
        writer.println(" * @version 1.0");
        writer.println(" */");
        writer.println("public class " + className + " extends JspTemplate");
        writer.println("{");

        for(int index = 0, size = list.size(); index < size; index++)
        {
            node = list.get(index);

            if(node.getNodeType() == NodeType.JSP_DECLARATION)
            {
                if(node.getOffset() == index)
                {
                    writer.println("    /* JSP_DECLARATION: lineNumber: " + node.getLineNumber() + " */");
                    writer.write(node.getTextContent());
                }
                else
                {
                    writer.println("    /* jsp:declaration END */");
                }
            }
        }

        writer.println("    /**");
        writer.println("     * @param pageContext");
        writer.println("     * @throws Exception");
        writer.println("     */");
        writer.println("    @Override");
        writer.println("    public void _execute(final PageContext pageContext) throws Exception");
        writer.println("    {");
        writer.println("        JspWriter out = pageContext.getOut();");
        writer.println("        JspWriter jspWriter = pageContext.getOut();");
        writer.println("        ExpressionContext expressionContext = pageContext.getExpressionContext();");
        writer.println();
        String indent = this.getIndent(2);

        for(int index = 0, size = list.size(); index < size; index++)
        {
            node = list.get(index);
            indent = getIndent(node);

            if(node.getNodeType() == NodeType.TEXT)
            {
                writer.println(indent + "/* TEXT: lineNumber: " + node.getLineNumber() + " */");
                writer.println(indent + "out.write(\"" + StringUtil.escape(StringUtil.compact(node.getTextContent())) + "\");");

                if(this.isTagNode(list, index + 1))
                {
                    writer.println();
                }
                continue;
            }

            if(node.getNodeType() == NodeType.VARIABLE)
            {
                String textContent = node.getTextContent();
                writer.println(indent + "/* VARIABLE: lineNumber: " + node.getLineNumber() + " */");
                writer.println(indent + "out.print(pageContext.getAttribute(\"" + textContent + "\"), false);");

                if(this.isTagNode(list, index + 1))
                {
                    writer.println();
                }
                continue;
            }

            if(node.getNodeType() == NodeType.EXPRESSION)
            {
                String textContent = node.getTextContent();
                writer.println(indent + "/* EXPRESSION: lineNumber: " + node.getLineNumber() + " */");
                writer.println(indent + "out.write(expressionContext.getString(\"" + StringUtil.escape(textContent) + "\"));");

                if(this.isTagNode(list, index + 1))
                {
                    writer.println();
                }
                continue;
            }

            if(node.getLength() == 0)
            {
                throw new RuntimeException("Exception at line #" + node.getLineNumber() + " " + NodeUtil.getDescription(node) + " not match !");
            }

            this.write(index, indent, node.getTagClassName(), node, writer);

            if(node.getOffset() != index)
            {
                writer.println();
            }
        }

        writer.println("        out.flush();");
        writer.println("        jspWriter.flush();");
        writer.println("    }");
        writer.println("}");
        writer.flush();
        return stringWriter.toString();
    }
    
    /**
     * @param className
     * @param packageName
     * @param writer
     */
    public void writeHeader(String className, String packageName, long lastModified, List<Node> nodes, PrintWriter writer)
    {
        Date date = new Date(lastModified);
        writer.println("/*");
        writer.println(" * $RCSfile: " + className + ".java,v $$");
        writer.println(" * $Revision: 1.1 $");
        writer.println(" * $Date: " + DateUtil.format(date, "yyyy-MM-dd") + " $");
        writer.println(" *");
        writer.println(" * Copyright (C) 2008 Skin, Inc. All rights reserved.");
        writer.println(" *");
        writer.println(" * This software is the proprietary information of Skin, Inc.");
        writer.println(" * Use is subject to license terms.");
        writer.println(" *");
        writer.println(" * LastModified: " + DateUtil.format(date, "yyyy-MM-dd HH:mm:ss SSS"));
        writer.println(" * JSP generated by JspCompiler-0.1.1 (built " + DateUtil.format(new Date(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss SSS") + ")");
        writer.println(" *");
        writer.println(" */");
        writer.println("package " + packageName + ";");
        writer.println();

        writer.println("import com.skin.ayada.runtime.ExpressionContext;");
        writer.println("import com.skin.ayada.runtime.JspWriter;");
        writer.println("import com.skin.ayada.runtime.PageContext;");
        writer.println("import com.skin.ayada.tagext.BodyContent;");
        writer.println("import com.skin.ayada.tagext.IterationTag;");
        writer.println("import com.skin.ayada.tagext.Tag;");
        writer.println("import com.skin.ayada.template.JspTemplate;");
        writer.println("import com.skin.ayada.util.ExpressionUtil;");
        Node node = null;

        for(int index = 0, size = nodes.size(); index < size; index++)
        {
            node = nodes.get(index);

            if(node.getNodeType() == NodeType.JSP_DIRECTIVE_PAGE)
            {
                if(node.getAttribute("import") != null)
                {
                    if(node.getOffset() == index)
                    {
                        writer.println("import " + node.getAttribute("import") + "; /* JSP_DIRECTIVE_PAGE.import: lineNumber: " + node.getLineNumber() + " */");
                    }
                }
            }
        }
        
        writer.println();
    }

    /**
     * @param index
     * @param indent
     * @param tagClassName
     * @param node
     * @param writer
     */
    public void write(int index, String indent, String tagClassName, Node node, PrintWriter writer)
    {
        /** Jsp Support */
        if(node.getOffset() == index)
        {
            if(node.getNodeType() == NodeType.JSP_DECLARATION)
            {
                writer.println(indent + "/* JSP_DECLARATION: lineNumber: " + node.getLineNumber() + " */");
                writer.println(indent + "/* " + NodeUtil.getDescription(node) + " */");
                return;
            }

            if(node.getNodeType() == NodeType.JSP_DIRECTIVE_PAGE)
            {
                writer.println(indent + "/* JSP_DIRECTIVE: lineNumber: " + node.getLineNumber() + " */");
                writer.println(indent + "/* " + NodeUtil.getDescription(node) + " */");
                return;
            }

            if(node.getNodeType() == NodeType.JSP_DIRECTIVE_TAGLIB)
            {
                writer.println(indent + "/* JSP_DIRECTIVE: lineNumber: " + node.getLineNumber() + " */");
                writer.println(indent + "/* " + NodeUtil.getDescription(node) + " */");
                return;
            }

            if(node.getNodeType() == NodeType.JSP_DIRECTIVE_INCLUDE)
            {
                writer.println(indent + "/* JSP_DIRECTIVE: lineNumber: " + node.getLineNumber() + " */");
                writer.println(indent + "/* " + NodeUtil.getDescription(node) + " */");
                return;
            }

            if(node.getNodeType() == NodeType.JSP_SCRIPTLET)
            {
                writer.println(indent + "/* JSP_SCRIPTLET: lineNumber: " + node.getLineNumber() + " */");
                writer.println(node.getTextContent());
                return;
            }

            if(node.getNodeType() == NodeType.JSP_EXPRESSION)
            {
                writer.println(indent + "/* JSP_EXPRESSION: lineNumber: " + node.getLineNumber() + " */");
                writer.println(indent + "out.print(" + node.getTextContent() + ");");
                return;
            }
        }
        else
        {
            if(node.getNodeType() == NodeType.JSP_DECLARATION)
            {
                writer.println(indent + "/* jsp:declaration END */");
                return;
            }

            if(node.getNodeType() == NodeType.JSP_DIRECTIVE_PAGE)
            {
                writer.println(indent + "/* jsp:directive.page END */");
                return;
            }

            if(node.getNodeType() == NodeType.JSP_DIRECTIVE_TAGLIB)
            {
                writer.println(indent + "/* jsp:directive.taglib END */");
                return;
            }

            if(node.getNodeType() == NodeType.JSP_DIRECTIVE_INCLUDE)
            {
                writer.println(indent + "/* jsp:directive.include END */");
                return;
            }

            if(node.getNodeType() == NodeType.JSP_SCRIPTLET)
            {
                writer.println(indent + "/* jsp:scriptlet END */");
                return;
            }

            if(node.getNodeType() == NodeType.JSP_EXPRESSION)
            {
                writer.println(indent + "/* jsp:expression END */");
                return;
            }
        }

        /** Tag Support */
        String tagInstanceName = this.getTagInstanceName(node);

        if(node.getOffset() == index)
        {
            writer.println(indent + "/* NODE START: lineNumber: " + node.getLineNumber() + ", offset: " + node.getOffset() + ", length: " + node.getLength() + ", tagClassName: " + tagClassName + ", tagInstanceName: " + tagInstanceName + " */");
            writer.println(indent + "/* " + NodeUtil.getDescription(node) + " */");
        }

        if(this.fast)
        {
            if(tagClassName.equals("com.skin.ayada.jstl.core.ImportTag"))
            {
                this.writeImportTag(index, indent, tagClassName, node, writer);
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.core.IfTag"))
            {
                this.writeIfTag(index, indent, tagClassName, node, writer);
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.core.SetTag"))
            {
                this.writeSetTag(index, indent, tagClassName, node, writer);
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.core.OutTag"))
            {
                this.writeOutTag(index, indent, tagClassName, node, writer);
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.core.ForEachTag"))
            {
                this.writeForEachTag(index, indent, tagClassName, node, writer);
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.core.ChooseTag"))
            {
                this.writeChooseTag(index, indent, tagClassName, node, writer);
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.core.WhenTag"))
            {
                this.writeWhenTag(index, indent, tagClassName, node, writer);
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.core.OtherwiseTag"))
            {
                this.writeOtherwiseTag(index, indent, tagClassName, node, writer);
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.core.CommentTag"))
            {
                this.writeCommentTag(index, indent, tagClassName, node, writer);
            }
            else if(tagClassName.equals("com.skin.ayada.taglib.PrintTag"))
            {
                this.writePrintTagTag(index, indent, tagClassName, node, writer);
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.fmt.DateFormatTag"))
            {
                this.writeFormatDateTag(index, indent, tagClassName, node, writer);
            }
            else
            {
                this.writeTag(index, indent, tagClassName, node, writer);
            }
        }
        else
        {
            this.writeTag(index, indent, tagClassName, node, writer);
        }

        if(node.getOffset() != index)
        {
            writer.println(indent + "/* NODE END: lineNumber: " + node.getLineNumber() + ", tagClassName: " + tagClassName + ", tagInstanceName: " + tagInstanceName + " */");
        }
    }

    /**
     * @param index
     * @param indent
     * @param tagClassName
     * @param node
     * @param writer
     */
    private void writeImportTag(int index, String indent, String tagClassName, Node node, PrintWriter writer)
    {
        if(node.getOffset() == index)
        {
            writer.println(indent + "/* pageContext.getTagLibrary().setup(\"" + node.getAttribute("name") + "\", \"" + node.getAttribute("className") + "\"); */");
        }
        else
        {
            writer.println(indent + "/* jsp.jstl.core.ImportTag END */");
        }
    }

    /**
     * @param index
     * @param indent
     * @param tagClassName
     * @param node
     * @param writer
     */
    private void writeIfTag(int index, String indent, String tagClassName, Node node, PrintWriter writer)
    {
        if(node.getOffset() == index)
        {
            writer.println(indent + "if(ExpressionUtil.getBoolean(expressionContext, \"" + StringUtil.escape(node.getAttribute("test")) + "\")){");
        }
        else
        {
            writer.println(indent + "} /* jsp.jstl.core.IfTag END */");
        }
    }
    
    /**
     * @param index
     * @param indent
     * @param tagClassName
     * @param node
     * @param writer
     */
    private void writeSetTag(int index, String indent, String tagClassName, Node node, PrintWriter writer)
    {
        if(node.getOffset() == index)
        {
            String name = node.getAttribute("var");
            String value = node.getAttribute("value");

            if(value != null)
            {
                if(value.indexOf("${") > -1)
                {
                    writer.println(indent + "pageContext.setAttribute(\"" + name + "\", ExpressionUtil.evaluate(expressionContext, \"" + StringUtil.escape(node.getAttribute("value")) + "\"));");
                }
                else
                {
                    Object constant = ExpressionUtil.getValue(value);

                    if(constant != null)
                    {
                        if(constant instanceof String)
                        {
                            writer.println(indent + "pageContext.setAttribute(\"" + name + "\", \"" + StringUtil.escape(value) + "\");");
                        }
                        else
                        {
                            writer.println(indent + "pageContext.setAttribute(\"" + name + "\", " + constant.toString() + ");");
                        }
                    }
                    else
                    {
                        writer.println(indent + "pageContext.setAttribute(\"" + name + "\", null);");
                    }
                }
            }
            else
            {
                writer.println(indent + "pageContext.setAttribute(\"" + name + "\", null));");
            }
        }
        else
        {
            writer.println(indent + "/* jsp.jstl.core.SetTag END */");
        }
    }
    
    /**
     * @param index
     * @param indent
     * @param tagClassName
     * @param node
     * @param writer
     */
    private void writeOutTag(int index, String indent, String tagClassName, Node node, PrintWriter writer)
    {
        boolean escapeXml = "true".equals(node.getAttribute("escapeXml"));

        if(node.getOffset() == index)
        {
            String value = node.getAttribute("value");

            if(value != null)
            {
                if(value.indexOf("${") < 0)
                {
                    Object constant = ExpressionUtil.getValue(value);

                    if(constant instanceof String)
                    {
                        if(escapeXml)
                        {
                            writer.println(indent + "/* out.write(\"" + StringUtil.escape(value) + "\"); */");
                            writer.println(indent + "out.write(\"" + StringUtil.escape(HtmlUtil.encode(value)) + "\");");
                        }
                        else
                        {
                            writer.println(indent + "out.write(\"" + StringUtil.escape(value) + "\");");
                        }
                    }
                    else
                    {
                        writer.println(indent + "out.print(" + value + ");");
                    }
                }
                else
                {
                    if(escapeXml)
                    {
                        writer.println(indent + "out.write(ExpressionUtil.getHtml(expressionContext, \"" + StringUtil.escape(value) + "\"));");
                    }
                    else
                    {
                        writer.println(indent + "out.write(ExpressionUtil.getString(expressionContext, \"" + StringUtil.escape(value) + "\"));");
                    }
                }
            }

            if(node.getLength() > 2)
            {
                writer.println(indent + "out = pageContext.pushBody();");
            }
        }
        else
        {
            if(node.getLength() > 2)
            {
                writer.println(indent + "pageContext.printBodyContent((BodyContent)out, " + escapeXml + ");");
                writer.println(indent + "out = pageContext.popBody();");
            }

            writer.println(indent + "/* jsp.jstl.core.OutTag END */");
        }
    }
    
    /**
     * @param index
     * @param indent
     * @param tagClassName
     * @param node
     * @param writer
     */
    private void writeForEachTag(int index, String indent, String tagClassName, Node node, PrintWriter writer)
    {
        String tagInstanceName = this.getTagInstanceName(node);
        String parentTagInstanceName = this.getTagInstanceName(node.getParent());
        String flagName = this.getVariableName(node, "_jsp_flag_");
        String forEachOldVar = this.getVariableName(node, "_jsp_old_var_");
        String forEachOldVarStatus = this.getVariableName(node, "_jsp_old_var_status_");

        if(node.getOffset() == index)
        {
            String items = node.getAttribute("items");
            String variable = node.getAttribute("var");
            String begin = node.getAttribute("begin");
            String step = node.getAttribute("step");
            String end = node.getAttribute("end");
            String varStatus = node.getAttribute("varStatus");
            boolean hasParent = this.hasParent(node);

            if(variable != null && variable.trim().length() > 0)
            {
                writer.println(indent + "Object " + forEachOldVar + " = pageContext.getAttribute(\"" + variable.trim() + "\");");
            }

            if(varStatus != null && varStatus.trim().length() > 0)
            {
                writer.println(indent + "Object " + forEachOldVarStatus + " = pageContext.getAttribute(\"" + varStatus.trim() + "\");");
            }

            writer.println(indent + tagClassName + " " + tagInstanceName + " = new " + tagClassName + "();");

            if(hasParent)
            {
                writer.println(indent + tagInstanceName + ".setParent(" + parentTagInstanceName + ");");
            }
            else
            {
                writer.println(indent + tagInstanceName + ".setParent((Tag)null);");
            }

            writer.println(indent + tagInstanceName + ".setPageContext(pageContext);");

            if(variable != null)
            {
                writer.println(indent + tagInstanceName + ".setVar(\"" + StringUtil.escape(variable) + "\");");
            }

            if(items != null)
            {
                if(items.indexOf("${") > -1)
                {
                    writer.println(indent + tagInstanceName + ".setItems(ExpressionUtil.evaluate(expressionContext, \"" + StringUtil.escape(items) + "\"));");
                }
                else
                {
                    writer.println(indent + tagInstanceName + ".setItems(\"" + StringUtil.escape(items) + "\");");
                }
            }
            else
            {
                writer.println(indent + tagInstanceName + ".setItems(null);");
            }

            if(begin != null && end != null)
            {
                if(begin.indexOf("${") < 0)
                {
                    Object beginValue = ExpressionUtil.getValue(begin);

                    if((beginValue instanceof String) == false)
                    {
                        writer.println(indent + tagInstanceName + ".setBegin(" + begin + ");");
                    }
                }
                else
                {
                    writer.println(indent + tagInstanceName + ".setBegin(ExpressionUtil.getInteger(expressionContext, \"" + begin + "\"));");
                }

                if(end.indexOf("${") < 0)
                {
                    Object endValue = ExpressionUtil.getValue(end);
                    if((endValue instanceof String) == false)
                    {
                        writer.println(indent + tagInstanceName + ".setEnd(" + end + ");");
                    }
                }
                else
                {
                    writer.println(indent + tagInstanceName + ".setEnd(ExpressionUtil.getInteger(expressionContext, \"" + end + "\"));");
                }
            }

            if(step != null)
            {
                if(step.indexOf("${") < 0)
                {
                    Object stepValue = ExpressionUtil.getValue(begin);

                    if((stepValue instanceof String) == false)
                    {
                        writer.println(indent + tagInstanceName + ".setStep(" + step + ");");
                    }
                }
                else
                {
                    writer.println(indent + tagInstanceName + ".setStep(ExpressionUtil.getInteger(expressionContext, \"" + step + "\"));");
                }
            }

            if(varStatus != null)
            {
                writer.println(indent + "pageContext.setAttribute(\"" + varStatus + "\", " + tagInstanceName + ".getLoopStatus());");
            }

            writer.println(indent + "int " + flagName + " = " + tagInstanceName + ".doStartTag();");
            writer.println(indent + "if(" + flagName + " != Tag.SKIP_BODY){");
            writer.println(indent + "    while(true){");
        }
        else
        {
            String variable = node.getAttribute("var");
            String varStatus = node.getAttribute("varStatus");
            writer.println(indent + "        if(" + tagInstanceName + ".hasNext()){");

            if(variable != null && variable.trim().length() > 0)
            {
                writer.println(indent + "            pageContext.setAttribute(\"" + variable + "\", " + tagInstanceName + ".next());");
            }
            else
            {
                writer.println(indent + "            " + tagInstanceName + ".next();");
            }

            writer.println(indent + "        }");
            writer.println(indent + "        else{");
            writer.println(indent + "            break;");
            writer.println(indent + "        }");
            writer.println(indent + "    }");
            writer.println(indent + "}");
            writer.println(indent + tagInstanceName + ".release();");

            if(variable != null && variable.trim().length() > 0)
            {
                writer.println(indent + "pageContext.setAttribute(\"" + variable.trim() + "\", " + forEachOldVar + ");");
            }

            if(varStatus != null && varStatus.trim().length() > 0)
            {
                writer.println(indent + "pageContext.setAttribute(\"" + varStatus.trim() + "\", " + forEachOldVarStatus + ");");
            }

            writer.println(indent + "/* jsp.jstl.core.ForEachTag END */");
        }
    }

    /**
     * @param index
     * @param indent
     * @param tagClassName
     * @param node
     * @param writer
     */
    private void writeChooseTag(int index, String indent, String tagClassName, Node node, PrintWriter writer)
    {
        if(node.getOffset() == index)
        {
            String tagInstanceName = this.getTagInstanceName(node);
            writer.println(indent + "boolean " + tagInstanceName + " = true;");
            writer.println();
        }
        else
        {
            writer.println(indent + "/* jsp.jstl.core.ChooseTag END */");
        }
    }
    
    /**
     * @param index
     * @param indent
     * @param tagClassName
     * @param node
     * @param writer
     */
    private void writeWhenTag(int index, String indent, String tagClassName, Node node, PrintWriter writer)
    {
        if(node.getOffset() == index)
        {
            String parentTagInstanceName = this.getTagInstanceName(node.getParent());
            writer.println(indent + "if(" + parentTagInstanceName + " && ExpressionUtil.getBoolean(expressionContext, \"" + StringUtil.escape(node.getAttribute("test")) + "\")){");
            writer.println(indent + "    " + parentTagInstanceName + " = false;");
        }
        else
        {
            writer.println(indent + "} /* jsp.jstl.core.WhenTag END */");
        }
    }
    
    /**
     * @param index
     * @param indent
     * @param tagClassName
     * @param node
     * @param writer
     */
    private void writeOtherwiseTag(int index, String indent, String tagClassName, Node node, PrintWriter writer)
    {
        if(node.getOffset() == index)
        {
            String parentTagInstanceName = this.getTagInstanceName(node.getParent());
            writer.println(indent + "if(" + parentTagInstanceName + "){");
            writer.println(indent + "    " + parentTagInstanceName + " = false;");
        }
        else
        {
            writer.println(indent + "} /* jsp.jstl.core.OtherwiseTag END */");
        }
    }

    /**
     * @param index
     * @param indent
     * @param tagClassName
     * @param node
     * @param writer
     */
    private void writeFormatDateTag(int index, String indent, String tagClassName, Node node, PrintWriter writer)
    {
        if(node.getOffset() == index)
        {
            String tagInstanceName = this.getTagInstanceName(node);
            String parentTagInstanceName = this.getTagInstanceName(node.getParent());
            boolean hasParent = this.hasParent(node);

            writer.println(indent + tagClassName + " " + tagInstanceName + " = new " + tagClassName + "();");

            if(hasParent)
            {
                writer.println(indent + tagInstanceName + ".setParent(" + parentTagInstanceName + ");");
            }
            else
            {
                writer.println(indent + tagInstanceName + ".setParent((Tag)null);");
            }

            writer.println(indent + tagInstanceName + ".setPageContext(pageContext);");
            this.setAttributes(indent, tagClassName, tagInstanceName, node.getAttributes(), writer);
            writer.println(indent + tagInstanceName + ".doStartTag();");
            writer.println(indent + tagInstanceName + ".doEndTag();");
            writer.println(indent + tagInstanceName + ".release();");
        }
        else
        {
            writer.println(indent + "/* jsp.jstl.fmt.FormatDateTag END */");
        }
    }

    /**
     * @param index
     * @param indent
     * @param tagClassName
     * @param node
     * @param writer
     */
    private void writeCommentTag(int index, String indent, String tagClassName, Node node, PrintWriter writer)
    {
        if(node.getOffset() == index)
        {
            writer.println(indent + "if(pageContext == null){");
        }
        else
        {
            writer.println(indent + "} /* jsp.jstl.core.CommentTag END */");
        }
    }

    /**
     * @param index
     * @param indent
     * @param tagClassName
     * @param node
     * @param writer
     */
    private void writePrintTagTag(int index, String indent, String tagClassName, Node node, PrintWriter writer)
    {
        if(node.getOffset() == index)
        {
            String out = node.getAttribute("out");
            String value = node.getAttribute("value");
            
            if(out == null || out.trim().length() < 1)
            {
                out = "null";
            }
            else
            {
                out = "ExpressionUtil.evaluate(expressionContext, \"" + StringUtil.escape(out) + "\")";
            }
            
            if(value == null || value.trim().length() < 1)
            {
                value = "null";
            }
            else
            {
                if(value.indexOf("${") < 0)
                {
                    value = "\"" + StringUtil.escape(value) + "\"";
                }
                else
                {
                    value = "ExpressionUtil.evaluate(expressionContext, \"" + StringUtil.escape(value) + "\")";
                }
            }

            writer.println(indent + "com.skin.ayada.taglib.PrintTag.print(pageContext, " + out + ", " + value + ");");
        }
        else
        {
            writer.println(indent + "/* jsp.jstl.core.PrintTag END */");
        }
    }
    
    /**
     * @param index
     * @param indent
     * @param tagClassName
     * @param node
     * @param writer
     */
    private void writeTag(int index, String indent, String tagClassName, Node node, PrintWriter writer)
    {
        String tagInstanceName = this.getTagInstanceName(node);
        String parentTagInstanceName = this.getTagInstanceName(node.getParent());
        String flagName = this.getVariableName(node, "_jsp_flag_");
        String bodyContentInstanceName = this.getVariableName(node, "_jsp_body_content_");
        boolean hasParent = this.hasParent(node);

        if(node.getOffset() == index)
        {
            writer.println(indent + tagClassName + " " + tagInstanceName + " = new " + tagClassName + "();");

            if(hasParent)
            {
                writer.println(indent + tagInstanceName + ".setParent(" + parentTagInstanceName + ");");
            }
            else
            {
                writer.println(indent + tagInstanceName + ".setParent((Tag)null);");
            }

            writer.println(indent + tagInstanceName + ".setPageContext(pageContext);");
            this.setAttributes(indent, tagClassName, tagInstanceName, node.getAttributes(), writer);
            writer.println(indent + "int " + flagName + " = " + tagInstanceName + ".doStartTag();");
            writer.println();
            writer.println(indent + "if(" + flagName + " == Tag.SKIP_PAGE){");
            writer.println(indent + "    return;");
            writer.println(indent + "}");

            if(this.isAssignableFrom(tagClassName, BodyTag.class))
            {
                writer.println(indent + "if(" + flagName + " != Tag.SKIP_BODY && " + flagName + " != Tag.EVAL_BODY_INCLUDE){");
                writer.println(indent + "    BodyContent " + bodyContentInstanceName + " = (BodyContent)(pageContext.pushBody());");
                writer.println(indent + "    " + tagInstanceName + ".setBodyContent(" + bodyContentInstanceName + ");");
                writer.println(indent + "    " + tagInstanceName + ".doInitBody();");
                writer.println(indent + "    out = " + bodyContentInstanceName + ";");
                writer.println(indent + "}");
            }

            writer.println();
            writer.println(indent + "do{");
        }
        else
        {
            if(this.isAssignableFrom(tagClassName, IterationTag.class))
            {
                writer.println(indent + "    " + flagName + " = " + tagInstanceName + ".doAfterBody();");
            }

            writer.println(indent + "}");
            writer.println(indent + "while(" + flagName + " == IterationTag.EVAL_BODY_AGAIN);");
            writer.println(indent + tagInstanceName+ ".doEndTag();");
            writer.println(indent + tagInstanceName + ".release();");

            if(this.isAssignableFrom(tagClassName, BodyTag.class))
            {
                writer.println(indent + "if(" + flagName + " != Tag.SKIP_BODY && " + flagName + " != Tag.EVAL_BODY_INCLUDE){");
                writer.println(indent + "    out = pageContext.popBody();");
                writer.println(indent + "}");
            }
        }
    }

    /**
     * @param source
     * @return boolean
     */
    protected boolean isJavaIdentifier(String source)
    {
        if(Character.isJavaIdentifierStart(source.charAt(0)) == false)
        {
            return false;
        }

        for(int i = 0; i < source.length(); i++)
        {
            if(Character.isJavaIdentifierPart(source.charAt(i)) == false)
            {
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
    protected boolean isTagNode(List<Node> list, int index)
    {
        if(index < list.size())
        {
            Node node = list.get(index);
            int nodeType = node.getNodeType();

            if(nodeType != NodeType.TEXT && nodeType != NodeType.VARIABLE && nodeType != NodeType.EXPRESSION)
            {
                if(node.getOffset() == index)
                {
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
    protected boolean hasParent(Node node)
    {
        Node parent = node.getParent();

        if(this.fast == false)
        {
            return (parent != null);
        }

        if(parent != null)
        {
            String parentTagName = parent.getNodeName();

            if(parentTagName.equals("c:if")
                    || parentTagName.equals("c:out")
                    || parentTagName.equals("c:set")
                    || parentTagName.equals("c:each")
                    || parentTagName.equals("c:forEach")
                    || parentTagName.equals("c:choose")
                    || parentTagName.equals("c:when")
                    || parentTagName.equals("c:otherwise")
                    || parentTagName.equals("c:comment")
                    || parentTagName.equals("fmt:formatDate"))
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * @param indent
     * @param tagClassName
     * @param tagInstanceName
     * @param attributes
     * @param writer
     */
    protected void setAttributes(String indent, String tagClassName, String tagInstanceName, Map<String, String> attributes, PrintWriter writer)
    {
        if(attributes == null || attributes.size() < 1)
        {
            return;
        }

        try
        {
            Class<?> clazz = ClassUtil.getClass(tagClassName);

            for(Map.Entry<String, String> entry : attributes.entrySet())
            {
                String name = entry.getKey();
                String value = entry.getValue();
                String methodName = "set" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
                Method method = TagUtil.getSetMethod(clazz, methodName);

                if(method != null)
                {
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    Class<?> parameterType = parameterTypes[0];
                    Object parameterValue = null;

                    if(value.indexOf("${") < 0)
                    {
                        parameterValue = ExpressionUtil.getValue(value);

                        if(parameterType == char.class || parameterType == Character.class)
                        {
                            writer.println(indent + tagInstanceName + "." + methodName + "('" + StringUtil.escape(value).charAt(0) + "');");
                        }
                        else if(parameterType == boolean.class || parameterType == Boolean.class)
                        {
                            writer.println(indent + tagInstanceName + "." + methodName + "(" + parameterValue.toString() + ");");
                        }
                        else if(parameterType == byte.class || parameterType == Byte.class)
                        {
                            writer.println(indent + tagInstanceName + "." + methodName + "((byte)" + ((Number)parameterValue).intValue() + ");");
                        }
                        else if(parameterType == short.class || parameterType == Short.class)
                        {
                            writer.println(indent + tagInstanceName + "." + methodName + "((short)" + ((Number)parameterValue).intValue() + ");");
                        }
                        else if(parameterType == int.class || parameterType == Integer.class)
                        {
                            writer.println(indent + tagInstanceName + "." + methodName + "(" + ((Number)parameterValue).intValue() + ");");
                        }
                        else if(parameterType == float.class || parameterType == Float.class)
                        {
                            writer.println(indent + tagInstanceName + "." + methodName + "(" + ((Number)parameterValue).floatValue() + "f);");
                        }
                        else if(parameterType == double.class || parameterType == Double.class)
                        {
                            writer.println(indent + tagInstanceName + "." + methodName + "(" + ((Number)parameterValue).doubleValue() + "d);");
                        }
                        else if(parameterType == long.class || parameterType == Long.class)
                        {
                            writer.println(indent + tagInstanceName + "." + methodName + "(" + ((Number)parameterValue).longValue() + "L);");
                        }
                        else if(parameterType == String.class)
                        {
                            writer.println(indent + tagInstanceName + "." + methodName + "(\"" + StringUtil.escape(value) + "\");");
                        }
                        else
                        {
                            writer.println(indent + tagInstanceName + "." + methodName + "(\"" + StringUtil.escape(value) + "\");");
                        }
                    }
                    else
                    {
                        if(parameterType == char.class || parameterType == Character.class)
                        {
                            writer.println(indent + tagInstanceName + "." + methodName + "(ExpressionUtil.getString(expressionContext, \"" + StringUtil.escape(value) + "\").charAt(0));");
                        }
                        else if(parameterType == boolean.class || parameterType == Boolean.class)
                        {
                            writer.println(indent + tagInstanceName + "." + methodName + "(ExpressionUtil.getBoolean(expressionContext, \"" + StringUtil.escape(value) + "\"));");
                        }
                        else if(parameterType == byte.class || parameterType == Byte.class)
                        {
                            writer.println(indent + tagInstanceName + "." + methodName + "(ExpressionUtil.getByte(expressionContext, \"" + StringUtil.escape(value) + "\"));");
                        }
                        else if(parameterType == short.class || parameterType == Short.class)
                        {
                            writer.println(indent + tagInstanceName + "." + methodName + "(ExpressionUtil.getShort(expressionContext, \"" + StringUtil.escape(value) + "\"));");
                        }
                        else if(parameterType == int.class || parameterType == Integer.class)
                        {
                            writer.println(indent + tagInstanceName + "." + methodName + "(ExpressionUtil.getInteger(expressionContext, \"" + StringUtil.escape(value) + "\"));");
                        }
                        else if(parameterType == float.class || parameterType == Float.class)
                        {
                            writer.println(indent + tagInstanceName + "." + methodName + "(ExpressionUtil.getFloat(expressionContext, \"" + StringUtil.escape(value) + "\"));");
                        }
                        else if(parameterType == double.class || parameterType == Double.class)
                        {
                            writer.println(indent + tagInstanceName + "." + methodName + "(ExpressionUtil.getDouble(expressionContext, \"" + StringUtil.escape(value) + "\"));");
                        }
                        else if(parameterType == long.class || parameterType == Long.class)
                        {
                            writer.println(indent + tagInstanceName + "." + methodName + "(ExpressionUtil.getLong(expressionContext, \"" + StringUtil.escape(value) + "\"));");
                        }
                        else if(parameterType == String.class)
                        {
                            writer.println(indent + tagInstanceName + "." + methodName + "(ExpressionUtil.getString(expressionContext, \"" + StringUtil.escape(value) + "\"));");
                        }
                        else if(parameterType == java.util.Date.class)
                        {
                            writer.println(indent + tagInstanceName + "." + methodName + "(ExpressionUtil.getDate(expressionContext, \"" + StringUtil.escape(value) + "\"));");
                        }
                        else if(parameterType == Object.class)
                        {
                            writer.println(indent + tagInstanceName + "." + methodName + "(ExpressionUtil.evaluate(expressionContext, \"" + StringUtil.escape(value) + "\"));");
                        }
                    }
                }
            }
        }
        catch(ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param tagClassName
     * @param tag
     * @return boolean
     * @throws ClassNotFoundException 
     */
    protected boolean isAssignableFrom(String tagClassName, Class<?> parent)
    {
        try
        {
            Class<?> clazz = ClassUtil.getClass(tagClassName);
            return parent.isAssignableFrom(clazz);
        }
        catch(ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param indent
     * @return String
     */
    protected String getIndent(int indent)
    {
        StringBuilder buffer = new StringBuilder();

        for(int i = 0; i < indent; i++)
        {
            buffer.append("    ");
        }

        return buffer.toString();
    }

    /**
     * @param indent
     * @return String
     */
    protected String getIndent(Node node)
    {
        Node parent = node;
        StringBuilder buffer = new StringBuilder("        ");

        while(parent != null && (parent = parent.getParent()) != null)
        {
            String tagClassName = parent.getTagClassName();

            if(tagClassName.equals("com.skin.ayada.jstl.core.ImportTag"))
            {
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.core.IfTag"))
            {
                buffer.append("    ");
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.core.SetTag"))
            {
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.core.OutTag"))
            {
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.core.ForEachTag"))
            {
                buffer.append("        ");
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.core.ChooseTag"))
            {
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.core.WhenTag"))
            {
                buffer.append("    ");
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.core.OtherwiseTag"))
            {
                buffer.append("    ");
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.core.CommentTag"))
            {
                buffer.append("    ");
            }
            else if(tagClassName.equals("com.skin.ayada.taglib.PrintTag"))
            {
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.fmt.DateFormatTag"))
            {
            }
            else
            {
                buffer.append("    ");
            }
        }

        return buffer.toString();
    }

    /**
     * @param node
     * @param prefix
     * @return String
     */
    protected String getTagInstanceName(Node node)
    {
        if(node != null)
        {
            String tagClassName = node.getTagClassName();
            int k = tagClassName.lastIndexOf(".");

            if(k > -1)
            {
                return this.getVariableName(node, "_jsp_" + tagClassName.substring(k + 1) + "_");
            }
            else
            {
                return this.getVariableName(node, "_jsp_" + tagClassName + "_");
            }
        }
        else
        {
            return "_jsp_undefined";
        }
    }

    /**
     * @param node
     * @param prefix
     * @return String
     */
    protected String getVariableName(Node node, String prefix)
    {
        if(node != null)
        {
            return prefix + (node.getOffset() + 1);
        }
        else
        {
            return prefix + "undefined";
        }
    }

    /**
     * @return the fast
     */
    public boolean getFast()
    {
        return this.fast;
    }

    /**
     * @param fast the fast to set
     */
    public void setFast(boolean fast)
    {
        this.fast = fast;
    }
}
