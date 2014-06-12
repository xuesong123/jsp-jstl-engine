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

import com.skin.ayada.Version;
import com.skin.ayada.statement.Expression;
import com.skin.ayada.statement.Node;
import com.skin.ayada.statement.NodeType;
import com.skin.ayada.tagext.BodyTag;
import com.skin.ayada.tagext.DynamicAttributes;
import com.skin.ayada.tagext.IterationTag;
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
 * <p>Title: ClassCompiler</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class JspCompiler
{
    private boolean fastJstl = true;

    public String compile(Template template, String className, String packageName)
    {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        Node node = null;
        List<Node> list = template.getNodes();
        this.writeHeader(template, className, packageName, writer);
        writer.println("/**");
        writer.println(" * <p>Title: " + className + "</p>");
        writer.println(" * <p>Description: </p>");
        writer.println(" * <p>Copyright: Copyright (c) 2006</p>");
        writer.println(" * @author JspCompiler");
        writer.println(" * @version 1.0");
        writer.println(" */");
        writer.println("public class " + className + " extends JspTemplate");
        writer.println("{");

        writer.println("    public static void main(String[] args)");
        writer.println("    {");
        writer.println("        java.io.StringWriter writer = new java.io.StringWriter();");
        writer.println("        PageContext pageContext = com.skin.ayada.runtime.JspFactory.getPageContext(writer);");
        writer.println("        " + className + " template = new " + className + "();");
        writer.println();
        writer.println("        try{");
        writer.println("            template._execute(pageContext);");
        writer.println("            System.out.println(writer.toString());");
        writer.println("        }");
        writer.println("        catch(Throwable throwable)");
        writer.println("        {");
        writer.println("            throwable.printStackTrace();");
        writer.println("        }");
        writer.println("     }");
        writer.println();

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
                    writer.println();
                }
            }
        }

        writer.println("    /**");
        writer.println("     * @param pageContext");
        writer.println("     * @throws Throwable");
        writer.println("     */");
        writer.println("    @Override");
        writer.println("    public void _execute(final PageContext pageContext) throws Throwable");
        writer.println("    {");
        writer.println("        JspWriter out = pageContext.getOut();");
        writer.println("        JspWriter jspWriter = pageContext.getOut();");
        writer.println("        ExpressionContext expressionContext = pageContext.getExpressionContext();");
        writer.println();

        int nodeType = 0;
        String indent = null;

        for(int index = 0, size = list.size(); index < size; index++)
        {
            node = list.get(index);
            indent = this.getIndent(node);
            nodeType = node.getNodeType();

            if(nodeType == NodeType.TEXT)
            {
                writer.println(indent + "/* TEXT: lineNumber: " + node.getLineNumber() + " */");
                writer.println(indent + "out.write(\"" + StringUtil.escape(node.getTextContent()) + "\");");

                if(this.isTagNode(list, index + 1))
                {
                    writer.println();
                }
                continue;
            }

            if(nodeType == NodeType.VARIABLE)
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

            if(nodeType == NodeType.EXPRESSION)
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

            int flag = this.write(index, indent, node.getTagClassName(), node, writer);

            if(flag == Tag.SKIP_BODY)
            {
                index = node.getOffset() + node.getLength() - 2;
            }
            else
            {
                if(node.getOffset() != index)
                {
                    writer.println();
                }
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
    public void writeHeader(Template template, String className, String packageName, PrintWriter writer)
    {
        List<Node> nodes = template.getNodes();
        Date date = new Date(template.getLastModified());

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
        writer.println(" * Home: " + template.getHome());
        writer.println(" * Path: " + template.getPath());
        writer.println(" * LastModified: " + DateUtil.format(date, "yyyy-MM-dd HH:mm:ss SSS"));
        writer.println(" * JSP generated by JspCompiler-" + Version.getVersion() + " (built " + DateUtil.format(new Date(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss SSS") + ")");
        writer.println(" */");
        writer.println("package " + packageName + ";");
        writer.println();

        writer.println("import com.skin.ayada.runtime.ExpressionContext;");
        writer.println("import com.skin.ayada.runtime.JspWriter;");
        writer.println("import com.skin.ayada.runtime.PageContext;");
        writer.println("import com.skin.ayada.tagext.BodyContent;");
        writer.println("import com.skin.ayada.tagext.BodyTag;");
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
                        writer.println("import " + node.getAttribute("import") + "; /* jsp:directive.import: lineNumber: " + node.getLineNumber() + " */");
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
     * @return int
     */
    public int write(int index, String indent, String tagClassName, Node node, PrintWriter writer)
    {
        /** Jsp Support */
        if(node.getOffset() == index)
        {
            if(node.getNodeType() == NodeType.JSP_DECLARATION)
            {
                writer.println(indent + "/* jsp:declaration: lineNumber: " + node.getLineNumber() + " */");
                writer.println(indent + "/* " + NodeUtil.getDescription(node) + " */");
                return Tag.EVAL_PAGE;
            }

            if(node.getNodeType() == NodeType.JSP_DIRECTIVE_PAGE)
            {
                writer.println(indent + "/* jsp:directive.page: lineNumber: " + node.getLineNumber() + " */");
                writer.println(indent + "/* " + NodeUtil.getDescription(node) + " */");
                return Tag.EVAL_PAGE;
            }

            if(node.getNodeType() == NodeType.JSP_DIRECTIVE_TAGLIB)
            {
                writer.println(indent + "/* jsp:directive.taglib: lineNumber: " + node.getLineNumber() + " */");
                writer.println(indent + "/* " + NodeUtil.getDescription(node) + " */");
                return Tag.EVAL_PAGE;
            }

            if(node.getNodeType() == NodeType.JSP_DIRECTIVE_INCLUDE)
            {
                writer.println(indent + "/* jsp:directive.include: lineNumber: " + node.getLineNumber() + " */");
                writer.println(indent + "/* " + NodeUtil.getDescription(node) + " */");
                return Tag.EVAL_PAGE;
            }

            if(node.getNodeType() == NodeType.JSP_SCRIPTLET)
            {
                writer.println(indent + "/* jsp:scriptlet: lineNumber: " + node.getLineNumber() + " */");
                writer.println(node.getTextContent());
                return Tag.SKIP_BODY;
            }

            if(node.getNodeType() == NodeType.JSP_EXPRESSION)
            {
                writer.println(indent + "/* jsp:expression: lineNumber: " + node.getLineNumber() + " */");
                writer.println(indent + "out.print(" + node.getTextContent() + ");");
                return Tag.SKIP_BODY;
            }
        }
        else
        {
            if(node.getNodeType() == NodeType.JSP_DECLARATION)
            {
                writer.println(indent + "/* jsp:declaration END */");
                return Tag.EVAL_PAGE;
            }

            if(node.getNodeType() == NodeType.JSP_DIRECTIVE_PAGE)
            {
                writer.println(indent + "/* jsp:directive.page END */");
                return Tag.EVAL_PAGE;
            }

            if(node.getNodeType() == NodeType.JSP_DIRECTIVE_TAGLIB)
            {
                writer.println(indent + "/* jsp:directive.taglib END */");
                return Tag.EVAL_PAGE;
            }

            if(node.getNodeType() == NodeType.JSP_DIRECTIVE_INCLUDE)
            {
                writer.println(indent + "/* jsp:directive.include END */");
                return Tag.EVAL_PAGE;
            }

            if(node.getNodeType() == NodeType.JSP_SCRIPTLET)
            {
                writer.println(indent + "/* jsp:scriptlet END */");
                return Tag.EVAL_PAGE;
            }

            if(node.getNodeType() == NodeType.JSP_EXPRESSION)
            {
                writer.println(indent + "/* jsp:expression END */");
                return Tag.EVAL_PAGE;
            }
        }

        /** Tag Support */
        String tagInstanceName = this.getTagInstanceName(node);

        if(node.getOffset() == index)
        {
            writer.println(indent + "/* NODE START: lineNumber: " + node.getLineNumber() + ", offset: " + node.getOffset() + ", length: " + node.getLength() + ", tagClassName: " + tagClassName + ", tagInstanceName: " + tagInstanceName + " */");
            writer.println(indent + "/* " + NodeUtil.getDescription(node) + " */");
        }

        int flag = Tag.EVAL_PAGE;

        if(this.fastJstl)
        {
            if(tagClassName.equals("com.skin.ayada.jstl.core.ImportTag"))
            {
                flag = this.writeImportTag(index, indent, tagClassName, node, writer);
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.core.IfTag"))
            {
                flag = this.writeIfTag(index, indent, tagClassName, node, writer);
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.core.SetTag"))
            {
                flag = this.writeSetTag(index, indent, tagClassName, node, writer);
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.core.OutTag"))
            {
                flag = this.writeOutTag(index, indent, tagClassName, node, writer);
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.core.ForEachTag"))
            {
                flag = this.writeForEachTag(index, indent, tagClassName, node, writer);
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.core.ChooseTag"))
            {
                flag = this.writeChooseTag(index, indent, tagClassName, node, writer);
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.core.WhenTag"))
            {
                flag = this.writeWhenTag(index, indent, tagClassName, node, writer);
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.core.OtherwiseTag"))
            {
                flag = this.writeOtherwiseTag(index, indent, tagClassName, node, writer);
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.core.ContinueTag"))
            {
                flag = this.writeContinueTag(index, indent, tagClassName, node, writer);
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.core.BreakTag"))
            {
                flag = this.writeBreakTag(index, indent, tagClassName, node, writer);
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.core.CommentTag"))
            {
                flag = this.writeCommentTag(index, indent, tagClassName, node, writer);
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.core.PrintTag"))
            {
                flag = this.writePrintTag(index, indent, tagClassName, node, writer);
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.core.AttributeTag"))
            {
                flag = this.writeAttributeTag(index, indent, tagClassName, node, writer);
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.core.ElementTag"))
            {
                flag = this.writeElementTag(index, indent, tagClassName, node, writer);
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.core.ConstructorTag"))
            {
                flag = this.writeConstructorTag(index, indent, tagClassName, node, writer);
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.core.PropertyTag"))
            {
                flag = this.writePropertyTag(index, indent, tagClassName, node, writer);
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.core.PrameterTag"))
            {
                flag = this.writePrameterTag(index, indent, tagClassName, node, writer);
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.core.ExecuteTag"))
            {
                flag = this.writeExecuteTag(index, indent, tagClassName, node, writer);
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.core.ExitTag"))
            {
                flag = this.writeExitTag(index, indent, tagClassName, node, writer);
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.fmt.DateFormatTag"))
            {
                flag = this.writeFormatDateTag(index, indent, tagClassName, node, writer);
            }
            else
            {
                flag = this.writeTag(index, indent, tagClassName, node, writer);
            }
        }
        else
        {
            flag = this.writeTag(index, indent, tagClassName, node, writer);
        }

        if(node.getOffset() != index)
        {
            writer.println(indent + "/* NODE END: lineNumber: " + node.getLineNumber() + ", tagClassName: " + tagClassName + ", tagInstanceName: " + tagInstanceName + " */");
        }

        return flag;
    }

    /**
     * @param index
     * @param indent
     * @param tagClassName
     * @param node
     * @param writer
     * @return int
     */
    private int writeImportTag(int index, String indent, String tagClassName, Node node, PrintWriter writer)
    {
        if(node.getOffset() == index)
        {
            writer.println(indent + "/* pageContext.getTagLibrary().setup(\"" + node.getAttribute("name") + "\", \"" + node.getAttribute("className") + "\"); */");
        }
        else
        {
            writer.println(indent + "/* jsp.jstl.core.ImportTag END */");
        }

        return Tag.EVAL_PAGE;
    }

    /**
     * @param index
     * @param indent
     * @param tagClassName
     * @param node
     * @param writer
     * @return int
     */
    private int writeIfTag(int index, String indent, String tagClassName, Node node, PrintWriter writer)
    {
        if(node.getOffset() == index)
        {
            writer.println(indent + "if(ExpressionUtil.getBoolean(expressionContext, \"" + StringUtil.escape(node.getAttribute("test")) + "\")){");
        }
        else
        {
            writer.println(indent + "} /* jsp.jstl.core.IfTag END */");
        }

        return Tag.EVAL_PAGE;
    }

    /**
     * @param index
     * @param indent
     * @param tagClassName
     * @param node
     * @param writer
     * @return int
     */
    private int writeSetTag(int index, String indent, String tagClassName, Node node, PrintWriter writer)
    {
        String name = node.getAttribute("var");
        String value = this.getValueExpression(node.getAttribute("value"));
        String target = this.getValueExpression(node.getAttribute("target"));
        String property = node.getAttribute("property");

        if(node.getOffset() == index)
        {
            if(value != null)
            {
                if(name != null)
                {
                    writer.println(indent + "pageContext.setAttribute(\"" + name + "\", " + value + ");");
                }
                else
                {
                    if(target != null && property != null)
                    {
                        writer.println(indent + "com.skin.ayada.util.ClassUtil.setProperty(" + target + ", \"" + property + "\", " + value + ");");
                    }
                }
            }
            else
            {
                if(node.getLength() > 2)
                {
                    writer.println(indent + "out = pageContext.pushBody();");
                }
            }
        }
        else
        {
            if(value == null)
            {
                if(node.getLength() > 2)
                {
                    if(name != null)
                    {
                        writer.println(indent + "pageContext.setAttribute(\"" + name + "\", ((BodyContent)out).getString().trim());");
                    }
                    else
                    {
                        if(target != null && property != null)
                        {
                            writer.println(indent + "com.skin.ayada.util.ClassUtil.setProperty(" + target + ", \"" + property + "\", ((BodyContent)out).getString().trim());");
                        }
                    }

                    writer.println(indent + "out = pageContext.popBody();");
                }
                else
                {
                    if(name != null)
                    {
                        writer.println(indent + "pageContext.setAttribute(\"" + name + "\", \"\");");
                    }
                    else
                    {
                        if(target != null && property != null)
                        {
                            writer.println(indent + "com.skin.ayada.util.ClassUtil.setProperty(" + target + ", \"" + property + "\", \"\");");
                        }
                    }
                }
            }

            writer.println(indent + "/* jsp.jstl.core.SetTag END */");
        }

        return Tag.EVAL_PAGE;
    }

    /**
     * @param index
     * @param indent
     * @param tagClassName
     * @param node
     * @param writer
     * @return int
     */
    private int writeOutTag(int index, String indent, String tagClassName, Node node, PrintWriter writer)
    {
        String value = node.getAttribute("value");
        boolean escapeXml = "true".equals(node.getAttribute("escapeXml"));

        if(node.getOffset() == index)
        {
            if(value != null)
            {
                writer.println(indent + "/* out.write(\"" + StringUtil.escape(value) + "\"); */");
                writer.println(indent + "out.write(" + this.getStringExpression(value, escapeXml) + ");");
                return Tag.SKIP_BODY;
            }
            else if(node.getLength() > 2)
            {
                writer.println(indent + "out = pageContext.pushBody();");
            }

            return Tag.EVAL_PAGE;
        }

        if(value == null && node.getLength() > 2)
        {
            writer.println(indent + "pageContext.printBodyContent((BodyContent)out, " + escapeXml + ");");
            writer.println(indent + "out = pageContext.popBody();");
        }

        writer.println(indent + "/* jsp.jstl.core.OutTag END */");
        return Tag.EVAL_PAGE;
    }

    /**
     * @param index
     * @param indent
     * @param tagClassName
     * @param node
     * @param writer
     * @return int
     */
    private int writeForEachTag(int index, String indent, String tagClassName, Node node, PrintWriter writer)
    {
        String tagInstanceName = this.getTagInstanceName(node);
        String parentTagInstanceName = this.getTagInstanceName(node.getParent());
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
                    writer.println(indent + tagInstanceName + ".setItems(ExpressionUtil.evaluate(expressionContext, \"" + StringUtil.escape(items) + "\", null));");
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
                    Object stepValue = ExpressionUtil.getValue(step);

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

            writer.println(indent + "if(" + tagInstanceName + ".doStartTag() != Tag.SKIP_BODY){");
            writer.println(indent + "    while(true){");
        }
        else
        {
            String variable = node.getAttribute("var");
            String varStatus = node.getAttribute("varStatus");
            writer.println(indent + "        if(" + tagInstanceName + ".doAfterBody() != IterationTag.EVAL_BODY_AGAIN){");
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

        return Tag.EVAL_PAGE;
    }

    /**
     * @param index
     * @param indent
     * @param tagClassName
     * @param node
     * @param writer
     * @return int
     */
    private int writeChooseTag(int index, String indent, String tagClassName, Node node, PrintWriter writer)
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

        return Tag.EVAL_PAGE;
    }

    /**
     * @param index
     * @param indent
     * @param tagClassName
     * @param node
     * @param writer
     * @return int
     */
    private int writeWhenTag(int index, String indent, String tagClassName, Node node, PrintWriter writer)
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

        return Tag.EVAL_PAGE;
    }

    /**
     * @param index
     * @param indent
     * @param tagClassName
     * @param node
     * @param writer
     * @return int
     */
    private int writeOtherwiseTag(int index, String indent, String tagClassName, Node node, PrintWriter writer)
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

        return Tag.EVAL_PAGE;
    }

    /**
     * @param index
     * @param indent
     * @param tagClassName
     * @param node
     * @param writer
     * @return int
     */
    private int writeContinueTag(int index, String indent, String tagClassName, Node node, PrintWriter writer)
    {
        if(node.getOffset() == index)
        {
            writer.println(indent + "if(com.skin.ayada.jstl.core.ContinueTag.getTrue()){ continue; }");
            writer.println(indent + "/* jsp.jstl.core.ContinueTag END */");
        }

        return Tag.EVAL_PAGE;
    }

    /**
     * @param index
     * @param indent
     * @param tagClassName
     * @param node
     * @param writer
     * @return int
     */
    private int writeBreakTag(int index, String indent, String tagClassName, Node node, PrintWriter writer)
    {
        if(node.getOffset() == index)
        {
            writer.println(indent + "if(com.skin.ayada.jstl.core.BreakTag.getTrue()){ break; }");
            writer.println(indent + "/* jsp.jstl.core.BreakTag END */");
        }

        return Tag.EVAL_PAGE;
    }

    /**
     * @param index
     * @param indent
     * @param tagClassName
     * @param node
     * @param writer
     * @return int
     */
    private int writeFormatDateTag(int index, String indent, String tagClassName, Node node, PrintWriter writer)
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

        return Tag.EVAL_PAGE;
    }

    /**
     * @param index
     * @param indent
     * @param tagClassName
     * @param node
     * @param writer
     * @return int
     */
    private int writeCommentTag(int index, String indent, String tagClassName, Node node, PrintWriter writer)
    {
        if(node.getOffset() == index)
        {
            writer.println(indent + "if(pageContext == null){");
        }
        else
        {
            writer.println(indent + "} /* jsp.jstl.core.CommentTag END */");
        }

        return Tag.EVAL_PAGE;
    }

    /**
     * @param index
     * @param indent
     * @param tagClassName
     * @param node
     * @param writer
     * @return int
     */
    private int writePrintTag(int index, String indent, String tagClassName, Node node, PrintWriter writer)
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
                out = "ExpressionUtil.evaluate(expressionContext, \"" + StringUtil.escape(out) + "\", null)";
            }

            writer.println(indent + "com.skin.ayada.jstl.core.PrintTag.print(pageContext, " + out + ", " + this.getValueExpression(value) + ");");
        }
        else
        {
            writer.println(indent + "/* jsp.jstl.core.PrintTag END */");
        }

        return Tag.EVAL_PAGE;
    }

    /**
     * @param index
     * @param indent
     * @param tagClassName
     * @param node
     * @param writer
     * @return int
     */
    private int writeAttributeTag(int index, String indent, String tagClassName, Node node, PrintWriter writer)
    {
        String name = node.getAttribute("name");
        String value = node.getAttribute("value");
        String parentTagInstanceName = this.getTagInstanceName(node.getParent());

        if(node.getOffset() == index)
        {
            if(value != null)
            {
                if(name != null)
                {
                    writer.println(indent + parentTagInstanceName + ".setAttribute(\"" + name + "\", " + this.getValueExpression(value) + ");");
                }
                else
                {
                    writer.println(indent + parentTagInstanceName + ".setAttribute((String)null, " + this.getValueExpression(value) + ");");
                }
                return Tag.SKIP_BODY;
            }
            else if(node.getLength() > 2)
            {
                writer.println(indent + "out = pageContext.pushBody();");
            }
            return Tag.EVAL_PAGE;
        }

        if(value == null && node.getLength() > 2)
        {
            if(name != null)
            {
                writer.println(indent + parentTagInstanceName + ".setAttribute(\"" + name + "\", ((BodyContent)out).getString());");
            }
            else
            {
                writer.println(indent + parentTagInstanceName + ".setAttribute((String)null, ((BodyContent)out).getString());");
            }
            writer.println(indent + "out = pageContext.popBody();");
        }

        writer.println(indent + "/* jsp.jstl.core.AttributeTag END */");

        return Tag.EVAL_PAGE;
    }

    /**
     * @param index
     * @param indent
     * @param tagClassName
     * @param node
     * @param writer
     * @return int
     */
    private int writeElementTag(int index, String indent, String tagClassName, Node node, PrintWriter writer)
    {
        String indek = node.getAttribute("index");
        String value = node.getAttribute("value");
        String parentTagInstanceName = this.getTagInstanceName(node.getParent());

        if(node.getOffset() == index)
        {
            if(value != null)
            {
                if(indek != null)
                {
                    writer.println(indent + parentTagInstanceName + ".setElement(" + indek + ", " + this.getValueExpression(value) + ");");
                }
                else
                {
                    writer.println(indent + parentTagInstanceName + ".addElement(" + this.getValueExpression(value) + ");");
                }
                return Tag.SKIP_BODY;
            }
            else if(node.getLength() > 2)
            {
                writer.println(indent + "out = pageContext.pushBody();");
            }
            return Tag.EVAL_PAGE;
        }
        if(value == null && node.getLength() > 2)
        {
            if(indek != null)
            {
                writer.println(indent + parentTagInstanceName + ".setElement(" + indek + ", ((BodyContent)out).getString());");
            }
            else
            {
                writer.println(indent + parentTagInstanceName + ".addElement(((BodyContent)out).getString());");
            }
            writer.println(indent + "out = pageContext.popBody();");
        }

        writer.println(indent + "/* jsp.jstl.core.ElementTag END */");
        return Tag.EVAL_PAGE;
    }

    /**
     * @param index
     * @param indent
     * @param tagClassName
     * @param node
     * @param writer
     * @return int
     */
    private int writeConstructorTag(int index, String indent, String tagClassName, Node node, PrintWriter writer)
    {
        String name = node.getAttribute("type");
        String value = node.getAttribute("value");
        String parentTagInstanceName = this.getTagInstanceName(node.getParent());

        if(node.getOffset() == index)
        {
            if(value != null)
            {
                writer.println(indent + "com.skin.ayada.jstl.core.ConstructorTag.setArgument(" + parentTagInstanceName + ", \"" + name + "\", " + this.getValueExpression(value) + ");");
                return Tag.SKIP_BODY;
            }
            return Tag.EVAL_PAGE;
        }
        writer.println(indent + "/* jsp.jstl.core.ConstructorTag END */");
        return Tag.EVAL_PAGE;
    }

    /**
     * @param index
     * @param indent
     * @param tagClassName
     * @param node
     * @param writer
     * @return int
     */
    private int writePropertyTag(int index, String indent, String tagClassName, Node node, PrintWriter writer)
    {
        String name = node.getAttribute("name");
        String value = node.getAttribute("value");
        String parentTagInstanceName = this.getTagInstanceName(node.getParent());

        if(node.getOffset() == index)
        {
            if(value != null)
            {
                writer.println(indent + parentTagInstanceName + ".setProperty(\"" + name + "\", " + this.getValueExpression(value) + ");");
                return Tag.SKIP_BODY;
            }
            else if(node.getLength() > 2)
            {
                writer.println(indent + "out = pageContext.pushBody();");
            }
            return Tag.EVAL_PAGE;
        }
        if(value == null && node.getLength() > 2)
        {
            writer.println(indent + parentTagInstanceName + ".setProperty(\"" + name + "\", ((BodyContent)out).getString());");
            writer.println(indent + "out = pageContext.popBody();");
        }

        writer.println(indent + "/* jsp.jstl.core.PropertyTag END */");
        return Tag.EVAL_PAGE;
    }

    /**
     * @param index
     * @param indent
     * @param tagClassName
     * @param node
     * @param writer
     * @return int
     */
    private int writePrameterTag(int index, String indent, String tagClassName, Node node, PrintWriter writer)
    {
        String name = node.getAttribute("name");
        String value = node.getAttribute("value");
        String parentTagInstanceName = this.getTagInstanceName(node.getParent());

        if(node.getOffset() == index)
        {
            if(value != null)
            {
                writer.println(indent + parentTagInstanceName + ".setParameter(\"" + name + "\", " + this.getStringExpression(value, false) + ");");
                return Tag.SKIP_BODY;
            }
            else if(node.getLength() > 2)
            {
                writer.println(indent + "out = pageContext.pushBody();");
            }
            return Tag.EVAL_PAGE;
        }
        if(value == null && node.getLength() > 2)
        {
            writer.println(indent + parentTagInstanceName + ".setParameter(\"" + name + "\", ((BodyContent)out).getString());");
            writer.println(indent + "out = pageContext.popBody();");
        }

        writer.println(indent + "/* jsp.jstl.core.PrameterTag END */");
        return Tag.EVAL_PAGE;
    }

    /**
     * @param index
     * @param indent
     * @param tagClassName
     * @param node
     * @param writer
     * @return int
     */
    private int writeExecuteTag(int index, String indent, String tagClassName, Node node, PrintWriter writer)
    {
        if(node.getOffset() == index)
        {
            String name = node.getAttribute("var");
            String value = this.getValueExpression(node.getAttribute("value"));

            if(name != null)
            {
                writer.println(indent + "pageContext.setAttribute(\"" + name + "\", " + value + ");");
            }
            else
            {
                writer.println(indent + value + ";");
            }
        }
        else
        {
            writer.println(indent + "/* jsp.jstl.core.ExecuteTag END */");
        }

        return Tag.EVAL_PAGE;
    }

    /**
     * @param index
     * @param indent
     * @param tagClassName
     * @param node
     * @param writer
     * @return int
     */
    private int writeExitTag(int index, String indent, String tagClassName, Node node, PrintWriter writer)
    {
        if(node.getOffset() == index)
        {
            String test = node.getAttribute("test");

            if(test != null)
            {
                writer.println(indent + "if(ExpressionUtil.getBoolean(expressionContext, \"" + StringUtil.escape(node.getAttribute("test")) + "\")){");
            }
            else
            {
                writer.println(indent + "if(com.skin.ayada.jstl.core.ExitTag.getTrue()){");
            }
            writer.println(indent + "    return;");
            writer.println(indent + "}");
            return Tag.SKIP_BODY;
        }
        writer.println(indent + "/* jsp.jstl.core.ExitTag END */");
        return Tag.EVAL_PAGE;
    }

    /**
     * @param index
     * @param indent
     * @param tagClassName
     * @param node
     * @param writer
     * @return int
     */
    private int writeTag(int index, String indent, String tagClassName, Node node, PrintWriter writer)
    {
        String tagInstanceName = this.getTagInstanceName(node);
        String parentTagInstanceName = this.getTagInstanceName(node.getParent());
        String startFlagName = this.getVariableName(node, "_jsp_start_flag_");
        String flagName = this.getVariableName(node, "_jsp_flag_");
        String bodyContentInstanceName = this.getVariableName(node, "_jsp_body_content_");
        boolean hasParent = this.hasParent(node);
        boolean isTryCatchFinallyTag = this.isAssignableFrom(tagClassName, TryCatchFinally.class);
        String prefix = indent;

        if(node.getOffset() == index)
        {
            writer.println(prefix + tagClassName + " " + tagInstanceName + " = new " + tagClassName + "();");

            if(isTryCatchFinallyTag)
            {
                writer.println(prefix + "try{");
                prefix = prefix + "    ";
            }

            if(hasParent)
            {
                writer.println(prefix + tagInstanceName + ".setParent(" + parentTagInstanceName + ");");
            }
            else
            {
                writer.println(prefix + tagInstanceName + ".setParent((Tag)null);");
            }

            writer.println(prefix + tagInstanceName + ".setPageContext(pageContext);");
            this.setAttributes(prefix, tagClassName, tagInstanceName, node.getAttributes(), writer);
            writer.println(prefix + "int " + startFlagName + " = " + tagInstanceName + ".doStartTag();");
            writer.println();
            writer.println(prefix + "if(" + startFlagName + " == Tag.SKIP_PAGE){");
            writer.println(prefix + "    return;");
            writer.println(prefix + "}");
            writer.println(prefix + "if(" + startFlagName + " != Tag.SKIP_BODY){");

            if(node.getLength() > 2)
            {
                writer.println(prefix + "    int " + flagName + " = 0;");

                if(this.isAssignableFrom(tagClassName, BodyTag.class))
                {
                    writer.println(prefix + "    if(" + startFlagName + " == BodyTag.EVAL_BODY_BUFFERED){");
                    writer.println(prefix + "        BodyContent " + bodyContentInstanceName + " = (BodyContent)(pageContext.pushBody());");
                    writer.println(prefix + "        " + tagInstanceName + ".setBodyContent(" + bodyContentInstanceName + ");");
                    writer.println(prefix + "        " + tagInstanceName + ".doInitBody();");
                    writer.println(prefix + "        out = " + bodyContentInstanceName + ";");
                    writer.println(prefix + "    }");
                }

                writer.println();
                writer.println(prefix + "    do{");
            }
        }
        else
        {
            if(isTryCatchFinallyTag)
            {
                prefix = prefix + "    ";
            }

            if(node.getLength() > 2)
            {
                if(this.isAssignableFrom(tagClassName, IterationTag.class))
                {
                    writer.println(prefix + "        " + flagName + " = " + tagInstanceName + ".doAfterBody();");
                }

                writer.println(prefix + "    }");
                writer.println(prefix + "    while(" + flagName + " == IterationTag.EVAL_BODY_AGAIN);");

                if(this.isAssignableFrom(tagClassName, BodyTag.class))
                {
                    writer.println(prefix + "    if(" + startFlagName + " == BodyTag.EVAL_BODY_BUFFERED){");
                    writer.println(prefix + "        out = pageContext.popBody();");
                    writer.println(prefix + "    }");
                }
            }
            else
            {
                if(this.isAssignableFrom(tagClassName, IterationTag.class))
                {
                    writer.println(prefix + "    " + tagInstanceName + ".doAfterBody();");
                }
            }

            writer.println(prefix + "}");
            writer.println(prefix + tagInstanceName+ ".doEndTag();");
            writer.println(prefix + tagInstanceName + ".release();");

            if(isTryCatchFinallyTag)
            {
                prefix = prefix.substring(4);
                writer.println(prefix + "}");
                writer.println(prefix + "catch(Throwable throwable){");
                writer.println(prefix + "    " + tagInstanceName + ".doCatch(throwable);");
                writer.println(prefix + "}");
                writer.println(prefix + "finally{");
                writer.println(prefix + "    " + tagInstanceName + ".doFinally();");
                writer.println(prefix + "}");
            }
        }

        return Tag.EVAL_PAGE;
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

        if(this.fastJstl == false)
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
            return true;
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
    protected void setAttributes(String indent, String tagClassName, String tagInstanceName, Map<String, String> attributes, PrintWriter writer)
    {
        if(attributes == null || attributes.size() < 1)
        {
            return;
        }

        if(this.isAssignableFrom(tagClassName, DynamicAttributes.class))
        {
            for(Map.Entry<String, String> entry : attributes.entrySet())
            {
                String name = entry.getKey();
                String value = entry.getValue();

                if(value.indexOf("${") < 0)
                {
                    String valueExpression = this.getValueExpression(value);
                    writer.println(indent + tagInstanceName + ".setDynamicAttribute(\"" + name + "\", " + valueExpression + ");");
                }
                else
                {
                    String valueExpression = this.getValueExpression(value);
                    writer.println(indent + tagInstanceName + ".setDynamicAttribute(\"" + name + "\", " + valueExpression + ");");
                }
            }

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
                        else if(parameterType == Object.class)
                        {
                            if(parameterValue instanceof Number)
                            {
                                writer.println(indent + tagInstanceName + "." + methodName + "(" + parameterValue.toString() + ");");
                            }
                            else
                            {
                                writer.println(indent + tagInstanceName + "." + methodName + "(\"" + StringUtil.escape(value) + "\");");
                            }
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
                            writer.println(indent + tagInstanceName + "." + methodName + "(ExpressionUtil.evaluate(expressionContext, \"" + StringUtil.escape(value) + "\", null));");
                        }
                        else
                        {
                            writer.println(indent + tagInstanceName + "." + methodName + "((" + parameterType.getName() + ")(ExpressionUtil.evaluate(expressionContext, \"" + StringUtil.escape(value) + "\", " + parameterType.getName() + ".class)));");
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
            else if(tagClassName.equals("com.skin.ayada.jstl.core.AttributeTag"))
            {
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.core.PropertyTag"))
            {
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.core.PrintTag"))
            {
            }
            else if(tagClassName.equals("com.skin.ayada.jstl.fmt.DateFormatTag"))
            {
            }
            else
            {
                buffer.append("        ");

                if(this.isAssignableFrom(tagClassName, TryCatchFinally.class))
                {
                    buffer.append("    ");
                }
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
            return this.getVariableName(node, "_jsp_" + tagClassName + "_");
        }
        return "_jsp_undefined";
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
        return prefix + "undefined";
    }

    /**
     * @param expression
     * @return String
     */
    protected String getStringExpression(String expression, boolean escapeXml)
    {
        if(expression != null)
        {
            if(expression.indexOf("${") > -1)
            {
                if(escapeXml)
                {
                    return "ExpressionUtil.getHtml(expressionContext, \"" + StringUtil.escape(expression) + "\")";
                }
                return "ExpressionUtil.getString(expressionContext, \"" + StringUtil.escape(expression) + "\")";
            }
            Object constant = ExpressionUtil.getValue(expression);

            if(constant instanceof String)
            {
                if(escapeXml)
                {
                    return "\"" + StringUtil.escape(HtmlUtil.encode(expression)) + "\"";
                }
                return "\"" + StringUtil.escape(expression) + "\"";
            }
            else if(constant instanceof Float)
            {
                return constant.toString() + "f";
            }
            else if(constant instanceof Double)
            {
                return constant.toString() + "d";
            }
            else if(constant instanceof Long)
            {
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
    protected String getValueExpression(String expression)
    {
        if(expression != null)
        {
            List<Node> nodes = ExpressionUtil.parse(expression);

            if(nodes.size() == 1)
            {
                Node node = nodes.get(0);

                if(node instanceof Expression)
                {
                    if(this.isJavaIdentifier(node.getTextContent()))
                    {
                        return "pageContext.getAttribute(\"" + node.getTextContent() + "\")";
                    }
                    return "ExpressionUtil.evaluate(expressionContext, \"" + StringUtil.escape(expression) + "\", null)";
                }
                Object constant = ExpressionUtil.getValue(node.getTextContent());

                if(constant instanceof String)
                {
                    return "\"" + StringUtil.escape(expression) + "\"";
                }
                else if(constant instanceof Float)
                {
                    return constant.toString() + "f";
                }
                else if(constant instanceof Double)
                {
                    return constant.toString() + "d";
                }
                else if(constant instanceof Long)
                {
                    return constant.toString() + "L";
                }
                return constant.toString();
            }
            return "ExpressionUtil.evaluate(expressionContext, \"" + StringUtil.escape(expression) + "\", null)";
        }
        return null;
    }

    /**
     * @return the fastJstl
     */
    public boolean getFastJstl()
    {
        return this.fastJstl;
    }

    /**
     * @param fastJstl the fastJstl to set
     */
    public void setFastJstl(boolean fastJstl)
    {
        this.fastJstl = fastJstl;
    }
}
