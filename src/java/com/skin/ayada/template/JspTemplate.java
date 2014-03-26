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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skin.ayada.runtime.JspWriter;
import com.skin.ayada.runtime.PageContext;
import com.skin.ayada.statement.Node;

/**
 * <p>Title: Template</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public abstract class JspTemplate extends Template
{
    private static final Logger logger = LoggerFactory.getLogger(JspTemplate.class);

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

    /**
     * @param pageContext
     * @throws Throwable
     */
    @Override
    public void execute(final PageContext pageContext) throws Exception
    {
        JspWriter out = pageContext.getOut();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("home", this.getHome());
        map.put("path", this.getPath());
        map.put("lastModified", this.getLastModified());
        pageContext.setAttribute("template", map);

        try
        {
            if(logger.isDebugEnabled())
            {
                long t1 = System.currentTimeMillis();
                this._execute(pageContext);
                long t2 = System.currentTimeMillis();
                logger.debug(this.getPath() + " - render time: " + (t2 - t1));
            }
            else
            {
                this._execute(pageContext);
            }
        }
        catch(Throwable throwable)
        {
            throw new Exception(throwable);
        }

        out.flush();
    }

    /**
     * @param pageContext
     * @throws Throwable
     */
    public abstract void _execute(final PageContext pageContext) throws Throwable;
}
