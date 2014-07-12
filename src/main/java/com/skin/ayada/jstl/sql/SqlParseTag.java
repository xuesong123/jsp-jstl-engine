/*
 * $RCSfile: SqlParseTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2014-03-25 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.sql;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.skin.ayada.tagext.BodyTag;
import com.skin.ayada.tagext.BodyTagSupport;
import com.skin.ayada.tagext.Tag;

/**
 * <p>Title: SqlParseTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * @author xuesong.net
 * @version 1.0
 */
public class SqlParseTag extends BodyTagSupport
{
    private String name;
    private String source;

    /**
     * @return int
     */
    @Override
    public int doStartTag()
    {
        if(this.source != null)
        {
            return Tag.SKIP_BODY;
        }

        return BodyTag.EVAL_BODY_BUFFERED;
    }

    /**
     * @return int
     */
    @Override
    public int doEndTag()
    {
        String sql = this.source;

        if(sql == null)
        {
            if(this.bodyContent != null)
            {
                sql = this.bodyContent.getString().trim();
            }
        }

        if(sql != null)
        {
            SimpleSqlParser parser = new SimpleSqlParser();
            Table table = parser.parse(sql);

            if(this.name == null)
            {
                this.name = table.getTableName();
            }

            this.pageContext.setAttribute(this.name, table);
        }

        return Tag.EVAL_PAGE;
    }

    /**
     * @param table
     * @return Map<String, Object>
     */
    public Map<String, Object> getMap(Table table)
    {
        List<Column> columns = table.listColumns();
        Map<String, Object> map = new LinkedHashMap<String, Object>();

        if(columns != null)
        {
            for(Column column : columns)
            {
                map.put(column.getColumnName(), column);
            }
        }

        return map;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the source
     */
    public String getSource()
    {
        return this.source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source)
    {
        this.source = source;
    }
}
