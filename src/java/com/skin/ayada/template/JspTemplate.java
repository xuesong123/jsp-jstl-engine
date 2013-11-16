/*
 * $RCSfile: Template.java,v $$
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

import com.skin.ayada.statement.Node;

/**
 * <p>Title: Template</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class JspTemplate extends Template
{
    public JspTemplate()
    {
    }

    /**
     * @param file
     * @param nodes
     */
    public JspTemplate(String home, String file, List<Node> nodes)
    {
        super(home, file, nodes);
    }
}
