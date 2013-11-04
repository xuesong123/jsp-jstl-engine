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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.skin.ayada.runtime.ExpressionContext;
import com.skin.ayada.runtime.JspWriter;
import com.skin.ayada.runtime.PageContext;
import com.skin.ayada.runtime.TagFactory;
import com.skin.ayada.statement.Node;
import com.skin.ayada.statement.NodeType;
import com.skin.ayada.statement.Statement;
import com.skin.ayada.tagext.BodyContent;
import com.skin.ayada.tagext.BodyTag;
import com.skin.ayada.tagext.IterationTag;
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
public class DefaultExecutor
{
    /**
     * @param list
     * @param pageContext
     */
    public static void execute(Template template, final PageContext pageContext)
    {
        Node node = null;
        JspWriter out = null;
        Statement statement = null;
        List<Node> list = template.getNodes();
        JspWriter jspWriter = pageContext.getOut();
        List<Statement> statements = getStatements(list);
        ExpressionContext expressionContext = pageContext.getExpressionContext();

        try
        {
            int flag = 0;
            int index = 0;
            int size = statements.size();

            while(index < size)
            {
                out = pageContext.getOut();
                statement = statements.get(index);
                node = statement.getNode();

                if(node.getNodeType() == NodeType.TEXT)
                {
                    out.write(node.toString());
                    index++;
                    continue;
                }

                if(node.getNodeType() == NodeType.EXPRESSION)
                {
                    Object value = expressionContext.evaluate(node.toString());

                    if(value != null)
                    {
                        out.write(value.toString());
                    }
                    index++;
                    continue;
                }

                if(node.getLength() == 0)
                {
                    throw new RuntimeException("Exception at line # " + node.getLineNumber() + " " + NodeUtil.toString(node) + " not match !");
                }

                if(node.getOffset() == index)
                {
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
        }
        catch(Throwable t)
        {
            throw new RuntimeException("Exception at " + template.getFile() + node.getLineNumber() + " " + NodeUtil.toString(node), t);
        }

        pageContext.setOut(jspWriter);

        try
        {
            jspWriter.flush();
        }
        catch(IOException e)
        {
        }
    }

    /**
     * @param statement
     * @return int
     */
    public static int doStartTag(final Statement statement, final PageContext pageContext)
    {
        Tag tag = statement.getTag();
        Node node = statement.getNode();

        if(tag == null)
        {
            tag = TagFactory.create(pageContext, node.getNodeName());
            tag.setPageContext(pageContext);
            statement.setTag(tag);

            Statement parent = statement.getParent();

            if(parent != null)
            {
                tag.setParent(parent.getTag());
            }
        }

        // create - doStartTag
        TagUtil.setAttributes(tag, node.getAttributes(), pageContext.getExpressionContext());
        int flag = tag.doStartTag();
        statement.setStartTagFlag(flag);

        if(flag == Tag.SKIP_PAGE)
        {
            return Tag.SKIP_PAGE;
        }

        if(flag != Tag.SKIP_BODY)
        {
            if(flag != Tag.EVAL_BODY_INCLUDE)
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

        return flag;
    }

    /**
     * @param statement
     * @param pageContext
     * @return int
     */
    private static int doEndTag(final Statement statement, final PageContext pageContext)
    {
        Tag tag = statement.getTag();
        IterationTag iterationTag = null;

        if(tag instanceof IterationTag)
        {
            iterationTag = (IterationTag)tag;
        }

        if(iterationTag != null)
        {
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
                    if(startTagFlag != Tag.EVAL_BODY_INCLUDE)
                    {
                        if(tag instanceof BodyTag)
                        {
                            pageContext.popBody();
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
     * @param list
     * @return List<Statement>
     */
    public static List<Statement> getStatements(List<Node> list)
    {
        List<Statement> statements = new ArrayList<Statement>(list.size());

        for(int i = 0, size = list.size(); i < size; i++)
        {
            Node node = list.get(i);

            if(node.getOffset() == i)
            {
                Node parent = node.getParent();
                Statement statement = new Statement(node);

                if(parent != null)
                {
                    statement.setParent(statements.get(parent.getOffset()));
                }

                statements.add(statement);
            }
            else
            {
                Statement statement = statements.get(node.getOffset());
                statements.add(statement);
            }
        }

        return statements;
    }

    public static void print(List<Node> list)
    {
        for(int index = 0, size = list.size(); index < size; index++)
        {
            Node node = list.get(index);

            if(node.getNodeType() == NodeType.TEXT)
            {
                System.out.println(index + " $" + node.getLineNumber() + " #text: " + node.toString());
                continue;
            }

            if(node.getNodeType() == NodeType.EXPRESSION)
            {
                System.out.println(index + " $" + node.getLineNumber() + " #expr: " + node.toString());
                continue;
            }

            if(node.getOffset() == index)
            {
                System.out.println(index + " $" + node.getLineNumber() + " #node: " + node.toString());
            }
            else
            {
                System.out.println(index + " $" + node.getLineNumber() + " #node: </" + node.getNodeName() + ">");
            }
        }
    }
}
