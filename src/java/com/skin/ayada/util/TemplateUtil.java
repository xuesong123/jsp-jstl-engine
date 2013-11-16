/*
 * $RCSfile: TemplateUtil.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-4 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.util;

import java.util.List;

import com.skin.ayada.statement.Node;
import com.skin.ayada.statement.NodeType;
import com.skin.ayada.template.Template;

/**
 * <p>Title: TemplateUtil</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class TemplateUtil
{
    /**
     * @param template
     * @return String
     */
    public static String toString(Template template)
    {
        List<Node> list = template.getNodes();
        StringBuilder buffer = new StringBuilder();

        for(int i = 0, size = list.size(); i < size; i++)
        {
            Node node = list.get(i);

            if(node.getNodeType() == NodeType.TEXT)
            {
                buffer.append(node.getTextContent());
                continue;
            }

            if(node.getNodeType() == NodeType.EXPRESSION)
            {
                buffer.append("${");
                buffer.append(node.getTextContent());
                buffer.append("}");
                continue;
            }

            if(node.getLength() == 0)
            {
                break;
            }

            if(i == node.getOffset())
            {
                buffer.append(node.toString());
            }
            else
            {
                buffer.append(node.toString(i));
            }
        }

        return buffer.toString();
    }
}
