/*
 * $RCSfile: DefaultExecutor.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-02-19 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.template;

import java.util.List;

import com.skin.ayada.runtime.ExpressionContext;
import com.skin.ayada.runtime.JspWriter;
import com.skin.ayada.runtime.PageContext;
import com.skin.ayada.statement.Expression;
import com.skin.ayada.statement.Node;
import com.skin.ayada.statement.NodeType;
import com.skin.ayada.statement.Statement;
import com.skin.ayada.statement.TagNode;
import com.skin.ayada.statement.TextNode;
import com.skin.ayada.statement.Variable;
import com.skin.ayada.tagext.BodyContent;
import com.skin.ayada.tagext.BodyTag;
import com.skin.ayada.tagext.IterationTag;
import com.skin.ayada.tagext.SimpleTag;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TryCatchFinally;
import com.skin.ayada.util.NodeUtil;

/**
 * <p>Title: DefaultExecutor</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class DefaultExecutor {
    /**
     * @param template
     * @param pageContext
     * @throws Exception
     */
    public static void execute(final Template template, final PageContext pageContext) throws Exception {
        execute(template, pageContext, 0, template.getNodes().size());
    }

    /**
     * @param template
     * @param pageContext
     * @param offset
     * @param length
     * @throws Exception
     */
    public static void execute(final Template template, final PageContext pageContext, final int offset, final int length) throws Exception {
        Statement[] statements = getStatements(template.getNodes());
        execute(template, statements, pageContext, offset, length);
    }

    /**
     * @param template
     * @param statements
     * @param pageContext
     * @param offset
     * @param length
     * @throws Exception
     */
    public static void execute(final Template template, final Statement[] statements, final PageContext pageContext, final int offset, final int length) throws Exception {
        Node node = null;
        JspWriter out = null;
        Statement statement = null;
        JspWriter jspWriter = pageContext.getOut();
        ExpressionContext expressionContext = pageContext.getExpressionContext();

        try {
            int flag = 0;
            int index = offset;
            int end = offset + length;

            while(index < end) {
                out = pageContext.getOut();
                statement = statements[index];
                node = statement.getNode();

                try {
                    switch(node.getNodeType()) {
                        case NodeType.TEXT: {
                            /**
                             * out.write(StringBuilder buffer)是JspWriter提供的专有方法
                             * 这样可以减少toString产生的String对象
                             */
                            out.write(((TextNode)node).getBuffer());
                            index++;
                            continue;
                        }
                        case NodeType.EXPRESSION: {
                            Object value = expressionContext.getValue(node.getTextContent());

                            if(value != null) {
                                if(((Expression)node).getFlag() == '#') {
                                    String content = value.toString();
                                    out.write(content, 0, content.length());
                                }
                                else {
                                    expressionContext.print(out, value);
                                }
                            }
                            index++;
                            continue;
                        }
                        case NodeType.VARIABLE: {
                            Object value = pageContext.getAttribute(node.getTextContent());

                            if(value != null) {
                                if(((Variable)node).getFlag() == '#') {
                                    String content = value.toString();
                                    out.write(content, 0, content.length());
                                }
                                else {
                                    expressionContext.print(out, value);
                                }
                            }
                            index++;
                            continue;
                        }
                        case NodeType.JSP_DIRECTIVE_PAGE:
                        case NodeType.JSP_DIRECTIVE_TAGLIB:
                        case NodeType.JSP_DIRECTIVE_INCLUDE:
                        case NodeType.JSP_DECLARATION:
                        case NodeType.JSP_SCRIPTLET:
                        case NodeType.JSP_EXPRESSION: {
                            index += 2;
                            continue;
                        }
                        case NodeType.TAG_NODE: {
                            break;
                        }
                        default: {
                            index = index + node.getLength();
                            continue;
                        }
                    }

                    if(node.getOffset() == index) {
                        Tag tag = statement.getTag();

                        if(tag == null) {
                            tag = create(statement, pageContext);
                        }

                        /**
                         * 经测试(jdk1.6), 编译模式的执行效率是解释模式的2-3倍
                         * 此处原来使用TagUtil.setAttributes设置属性, 现改为使用动态编译
                         * 动态编译在不同的机器上表现不一致, 某些机器上动态编译和反射性能相当, 某些机器上动态编译比反射性能高30%左右
                         */
                        ((TagNode)node).getTagFactory().setAttributes(tag, node.getAttributes(), expressionContext);

                        if(tag instanceof SimpleTag) {
                            DefaultJspFragment jspFragment = new DefaultJspFragment(template, statements, pageContext);
                            jspFragment.setOffset(node.getOffset() + 1);
                            jspFragment.setLength(node.getLength() - 2);
                            SimpleTag simpleTag = (SimpleTag)tag;
                            simpleTag.setJspBody(jspFragment);
                            simpleTag.doTag();
                            simpleTag.release();
                            index = node.getOffset() + node.getLength();
                            continue;
                        }

                        flag = doStartTag(statement, pageContext);

                        if(flag == Tag.SKIP_BODY) {
                            index = node.getOffset() + node.getLength() - 1;
                            continue;
                        }
                        else if(flag == Tag.SKIP_PAGE) {
                            doExit(statement);
                            break;
                        }
                        else {
                            index++;
                            continue;
                        }
                    }
                    else {
                        flag = doEndTag(statement, pageContext);

                        if(flag == IterationTag.EVAL_BODY_AGAIN) {
                            index = node.getOffset() + 1;
                            continue;
                        }
                        else if(flag == Tag.SKIP_PAGE) {
                            doExit(statement);
                            break;
                        }
                        else {
                            doFinally(statement.getTag());
                            index++;
                            continue;
                        }
                    }
                }
                catch(Throwable throwable) {
                    /**
                     * 如果退出时发生了异常, 那么直接抛出异常并退出
                     * doExit会依次向上执行doFinally和release方法, 确保实现了TryCatchFinally接口的tag都会被释放
                     * 但是doFinally和release有可能会抛出异常, 当有异常发生的时候, 后发生的异常会覆盖先发生的异常
                     * 当所有的doFinally和release都被调用完成之后会抛出最后发生的那个异常
                     * 如果没有发生异常则正常退出
                     */
                    if(throwable instanceof ExitException) {
                        throw throwable.getCause();
                    }

                    /**
                     * 当doStart, doEnd发生异常时, 该标签的doFinally不会再被调用, 而是直接调用该标签的doCatch
                     * 当doFinally发生异常时, 异常应该由父标签捕获而不是当前标签的doCatch
                     * 所以此处当发生了异常调用父标签的doCatch
                     * doCatch方法执行完会执行doFinally, 如果实现了TryCatchFinally的话
                     * doCatch执行完之后, 需要跳转到一个新的位置执行, 所以doCatch会返回一个新的指向地址
                     */
                    if(throwable instanceof FinallyException) {
                        index = doCatch(statement.getParent(), throwable.getCause());
                    }
                    else {
                        index = doCatch(statement, throwable);
                    }
                }
            }
        }
        catch(Throwable throwable) {
            throw error(template, node, throwable);
        }
        finally {
            jspWriter.flush();
            pageContext.setOut(jspWriter);
        }
    }

    /**
     * @param statement
     * @param pageContext
     * @return Tag
     */
    private static Tag create(Statement statement, PageContext pageContext) {
        Node node = statement.getNode();
        Tag tag = ((TagNode)node).getTagFactory().create();
        tag.setPageContext(pageContext);
        statement.setTag(tag);
        Statement parent = statement.getParent();

        if(parent != null) {
            tag.setParent(parent.getTag());
        }
        return tag;
    }

    /**
     * @param statement
     * @param pageContext
     * @return int
     * @throws Exception
     */
    private static int doStartTag(final Statement statement, final PageContext pageContext) throws Exception {
        Tag tag = statement.getTag();
        int flag = tag.doStartTag();
        statement.setStartTagFlag(flag);

        if(flag == Tag.SKIP_PAGE) {
            return Tag.SKIP_PAGE;
        }

        if(flag != Tag.SKIP_BODY) {
            if(flag == BodyTag.EVAL_BODY_BUFFERED) {
                Node node = statement.getNode();

                if(node.getLength() > 2) {
                    if(tag instanceof BodyTag) {
                        BodyTag bodyTag = (BodyTag)tag;
                        BodyContent bodyContent = pageContext.pushBody();
                        bodyTag.setBodyContent(bodyContent);
                        bodyTag.doInitBody();
                    }
                }
            }
        }
        return flag;
    }

    /**
     * @param statement
     * @param pageContext
     * @return int
     */
    private static int doEndTag(final Statement statement, final PageContext pageContext) throws Exception {
        Tag tag = statement.getTag();

        if(tag instanceof IterationTag) {
            IterationTag iterationTag = (IterationTag)tag;
            int flag = iterationTag.doAfterBody();

            if(flag == IterationTag.EVAL_BODY_AGAIN) {
                return flag;
            }

            int startTagFlag = statement.getStartTagFlag();

            if(startTagFlag != Tag.SKIP_BODY) {
                if(startTagFlag == BodyTag.EVAL_BODY_BUFFERED) {
                    Node node = statement.getNode();

                    if(node.getLength() > 2) {
                        if(tag instanceof BodyTag) {
                            pageContext.popBody();
                        }
                    }
                }
            }
        }
        return tag.doEndTag();
    }

    /**
     * @param statement
     * @param throwable
     * @return int
     * @throws Throwable
     */
    private static int doCatch(final Statement statement, final Throwable throwable) throws Throwable {
        Statement current = statement;
        Throwable exception = throwable;

        while(current != null) {
            Tag tag = current.getTag();
            Throwable error = null;

            try {
                if(tag instanceof TryCatchFinally) {
                    ((TryCatchFinally)tag).doCatch(exception);
                }
            }
            catch(Throwable t) {
                error = t;
            }
            finally {
                try {
                    doFinally(tag);
                }
                catch(Throwable t) {
                    error = t.getCause();
                }
            }

            if(error != null) {
                exception = error;
                current = current.getParent();
            }
            else {
                exception = null;
                break;
            }
        }

        if(exception != null) {
            throw exception;
        }

        /**
         * 只要exception == null, current一定不为null
         * 需要检查是因为编译器的代码检查
         */
        if(current != null) {
            Node node = current.getNode();
            return (node.getOffset() + node.getLength());
        }
        return -999;
    }

    /**
     * @param statement
     * @throws Throwable
     */
    private static void doExit(final Statement statement) throws Throwable {
        Statement current = statement;
        Throwable exception = null;

        while(current != null) {
            Tag tag = current.getTag();

            try {
                doFinally(tag);
            }
            catch(Throwable t) {
                exception = t.getCause();
            }
            current = current.getParent();
        }

        if(exception != null) {
            throw new ExitException(exception);
        }
    }

    /**
     * @param tag
     * @throws Exception
     */
    private static void doFinally(final Tag tag) throws Exception {
        Exception exception = null;

        if(tag instanceof TryCatchFinally) {
            try {
                ((TryCatchFinally)tag).doFinally();
            }
            catch(Exception e) {
                exception = e;
            }
        }

        try {
            tag.release();
        }
        catch(Exception e) {
            if(exception == null) {
                exception = e;
            }
        }

        if(exception != null) {
            throw new FinallyException(exception);
        }
    }

    /**
     * @param template
     * @param node
     * @param throwable
     * @throws Exception
     */
    private static Exception error(Template template, Node node, Throwable throwable) throws Exception {
        if(node != null) {
            return new Exception("\"" + template.getPath() + "\" Exception at line #" + node.getLineNumber() + " " + NodeUtil.getDescription(node), throwable);
        }

        if(throwable instanceof Exception) {
            return ((Exception)throwable);
        }
        return new Exception(throwable);
    }

    /**
     * @param list
     * @return List<Statement>
     */
    private static Statement[] getStatements(final List<Node> list) {
        Statement[] statements = new Statement[list.size()];

        for(int i = 0, size = list.size(); i < size; i++) {
            Node node = list.get(i);

            if(node.getOffset() == i) {
                Node parent = node.getParent();
                Statement statement = new Statement(node);

                if(parent != null) {
                    statement.setParent(statements[parent.getOffset()]);
                }
                statements[i] = statement;
            }
            else {
                Statement statement = statements[node.getOffset()];
                statements[i] = statement;
            }
        }
        return statements;
    }
}