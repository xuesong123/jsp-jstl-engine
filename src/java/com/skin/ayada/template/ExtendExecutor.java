/*
 * $RCSfile: DefaultExecutor.java,v $$
 * $Revision: 1.1  $
 * $Date: 2013-2-19  $
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
import com.skin.ayada.statement.Node;
import com.skin.ayada.statement.NodeType;
import com.skin.ayada.statement.Statement;
import com.skin.ayada.tagext.BodyContent;
import com.skin.ayada.tagext.BodyTag;
import com.skin.ayada.tagext.BreakSupportTag;
import com.skin.ayada.tagext.ContinueSupportTag;
import com.skin.ayada.tagext.IterationTag;
import com.skin.ayada.tagext.SimpleTag;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.util.NodeUtil;
import com.skin.ayada.util.TagUtil;

/**
 * <p>Title: DefaultExecutor</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class ExtendExecutor
{
    /**
     * @param template
     * @param pageContext
     * @throws Exception
     */
    public static void execute(final Template template, final PageContext pageContext) throws Exception
    {
        execute(template, pageContext, 0, template.getNodes().size());
    }

    /**
     * @param template
     * @param pageContext
     * @param offset
     * @param length
     * @throws Exception
     */
    public static void execute(final Template template, final PageContext pageContext, final int offset, final int length) throws Exception
    {
        Statement[] statements = getStatements(template.getNodes());
        execute(template, statements, pageContext, offset, length);
    }

    /**
     * @param template
     * @param pageContext
     * @param offset
     * @param end
     * @throws Exception
     */
    public static void execute(final Template template, final Statement[] statements, final PageContext pageContext, final int offset, final int length) throws Exception
    {
        if(length < 1)
        {
            return;
        }

        Node node = null;
        JspWriter out = null;
        Statement statement = null;
        JspWriter jspWriter = pageContext.getOut();
        ExpressionContext expressionContext = pageContext.getExpressionContext();

        try
        {
            int flag = 0;
            int index = offset;
            int end = offset + length;
            int nodeType = NodeType.UNKNOWN;

            while(index < end)
            {
                out = pageContext.getOut();
                statement = statements[index];
                node = statement.getNode();
                nodeType = node.getNodeType();

                if(nodeType == NodeType.TEXT)
                {
                    out.write(node.getTextContent());
                    index++;
                    continue;
                }

                if(nodeType == NodeType.EXPRESSION)
                {
                    Object value = expressionContext.getValue(node.getTextContent());

                    if(value != null)
                    {
                        out.write(value.toString());
                    }
                    index++;
                    continue;
                }

                if(nodeType == NodeType.VARIABLE)
                {
                    Object value = pageContext.getAttribute(node.getTextContent());

                    if(value != null)
                    {
                        out.write(value.toString());
                    }
                    index++;
                    continue;
                }

                if(nodeType != NodeType.NODE)
                {
                    index++;
                    continue;
                }

                if(node.getOffset() == index)
                {
                    Tag tag = statement.getTag();

                    if(tag == null)
                    {
                        tag = node.getTagFactory().create();
                        tag.setPageContext(pageContext);
                        statement.setTag(tag);
                        Statement parent = statement.getParent();

                        if(parent != null)
                        {
                            tag.setParent(parent.getTag());
                        }
                    }

                    // create - doStartTag
                    TagUtil.setAttributes(tag, node.getAttributes(), expressionContext);

                    if(tag instanceof SimpleTag)
                    {
                        DefaultJspFragment jspFragment = new DefaultJspFragment(template, statements, pageContext);
                        jspFragment.setOffset(node.getOffset() + 1);
                        jspFragment.setLength(node.getLength() - 2);
                        SimpleTag simpleTag = (SimpleTag)tag;
                        simpleTag.setPageBody(jspFragment);
                        simpleTag.doTag();
                        index = node.getOffset() + node.getLength();
                        continue;
                    }

                    flag = doStartTag(statement, pageContext);

                    if(flag == Tag.SKIP_BODY)
                    {
                        index = node.getOffset() + node.getLength();
                        continue;
                    }

                    if(flag == Tag.SKIP_PAGE)
                    {
                        break;
                    }

                    if(flag == Tag.CONTINUE)
                    {
                        Statement parent = getParent(statement, ContinueSupportTag.class);

                        if(parent != null)
                        {
                            index = parent.getNode().getOffset() + 1;
                            continue;
                        }
                        else
                        {
                            throw new Exception("Error: ContinueTag");
                        }
                    }

                    if(flag == Tag.BREAK)
                    {
                        Statement parent = getParent(statement, BreakSupportTag.class);

                        if(parent != null)
                        {
                            index = parent.getNode().getOffset() + parent.getNode().getLength();
                            continue;
                        }
                        else
                        {
                            throw new Exception("Error: BreakTag");
                        }
                    }
                }
                else
                {
                    flag = doEndTag(statement, pageContext);

                    if(flag == BodyTag.EVAL_BODY_AGAIN)
                    {
                        index = node.getOffset() + 1;
                        continue;
                    }

                    if(flag == Tag.SKIP_PAGE)
                    {
                        break;
                    }
                }
                index++;
            }

            jspWriter.flush();
        }
        catch(Throwable t)
        {
            throw new Exception("\"" + template.getPath() + "\" Exception at line #" + node.getLineNumber() + " " + NodeUtil.getDescription(node), t);
        }
        finally
        {
            pageContext.setOut(jspWriter);
        }
    }

    /**
     * @param statement
     * @return int
     */
    public static int doStartTag(final Statement statement, final PageContext pageContext) throws Exception
    {
        Tag tag = statement.getTag();
        int flag = tag.doStartTag();
        statement.setStartTagFlag(flag);

        if(flag == Tag.SKIP_PAGE)
        {
            return Tag.SKIP_PAGE;
        }

        if(flag != Tag.SKIP_BODY)
        {
            if(flag == BodyTag.EVAL_BODY_BUFFERED)
            {
                Node node = statement.getNode();

                if(node.getLength() > 2)
                {
                    if(tag instanceof BodyTag)
                    {
                        BodyTag bodyTag = (BodyTag)tag;
                        BodyContent bodyContent = (BodyContent)(pageContext.pushBody());
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
    private static int doEndTag(final Statement statement, final PageContext pageContext) throws Exception
    {
        Tag tag = statement.getTag();

        if(tag instanceof IterationTag)
        {
            IterationTag iterationTag = (IterationTag)tag;
            int flag = iterationTag.doAfterBody();

            if(flag == BodyTag.EVAL_BODY_AGAIN)
            {
                return flag;
            }
            else
            {
                int startTagFlag = statement.getStartTagFlag();

                if(startTagFlag != Tag.SKIP_BODY)
                {
                    if(startTagFlag == BodyTag.EVAL_BODY_BUFFERED)
                    {
                        Node node = statement.getNode();

                        if(node.getLength() > 2)
                        {
                            if(tag instanceof BodyTag)
                            {
                                pageContext.popBody();
                            }
                        }
                    }
                }
            }
        }

        int flag = tag.doEndTag();
        tag.release();
        return flag;
    }

    /**
     * @param statement
     * @param target
     * @return Tag
     */
    public static Statement getParent(Statement statement, Class<?> target)
    {
        Statement parent = statement;

        while(parent != null && (parent = parent.getParent()) != null)
        {
            if(parent.getTag().getClass() == target)
            {
                return parent;
            }
        }

        return null;
    }

    /**
     * @param list
     * @return List<Statement>
     */
    public static Statement[] getStatements(final List<Node> list)
    {
        Statement[] statements = new Statement[list.size()];

        for(int i = 0, size = list.size(); i < size; i++)
        {
            Node node = list.get(i);

            if(node.getLength() < 1)
            {
                throw new RuntimeException("Exception at line #" + node.getLineNumber() + " " + NodeUtil.getDescription(node) + " not match !");
            }

            if(node.getOffset() == i)
            {
                Node parent = node.getParent();
                Statement statement = new Statement(node);

                if(parent != null)
                {
                    statement.setParent(statements[parent.getOffset()]);
                }

                statements[i] = statement;
            }
            else
            {
                Statement statement = statements[node.getOffset()];
                statements[i] = statement;
            }
        }

        return statements;
    }
}
