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

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skin.ayada.database.Column;
import com.skin.ayada.database.Table;
import com.skin.ayada.jstl.sql.parser.InsertParser;
import com.skin.ayada.tagext.BodyTag;
import com.skin.ayada.tagext.BodyTagSupport;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.util.IO;

/**
 * <p>Title: SqlParseTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * @author xuesong.net
 * @version 1.0
 */
public class InsertParseTag extends BodyTagSupport {
    private String name;
    private String file;
    private String charset;
    private String database;
    private static final Logger logger = LoggerFactory.getLogger(InsertParseTag.class);

    /**
     * @return int
     */
    @Override
    public int doStartTag() throws Exception {
        if(this.file != null) {
            return Tag.SKIP_BODY;
        }
        return BodyTag.EVAL_BODY_BUFFERED;
    }

    /**
     * @return int
     */
    @Override
    public int doEndTag() throws Exception {
        String sql = null;

        if(this.file != null) {
            try {
                if(this.charset == null) {
                    this.charset = "UTF-8";
                }
                sql = IO.read(new File(this.file), this.charset, 2048);
            }
            catch(IOException e) {
            }
        }
        else {
            if(this.bodyContent != null) {
                sql = this.bodyContent.getString().trim();
            }
        }

        logger.debug("sql: {}", sql);

        if(sql != null) {
            InsertParser parser = new InsertParser();
            List<Record> list = parser.parse(sql);

            if(this.name != null) {
                this.pageContext.setAttribute(this.name, list);
            }
        }
        return Tag.EVAL_PAGE;
    }

    /**
     * @param table
     * @return Map<String, Object>
     */
    public Map<String, Object> getMap(Table table) {
        List<Column> columns = table.listColumns();
        Map<String, Object> map = new LinkedHashMap<String, Object>();

        if(columns != null) {
            for(Column column : columns) {
                map.put(column.getColumnName(), column);
            }
        }

        return map;
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the file
     */
    public String getFile() {
        return this.file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(String file) {
        this.file = file;
    }

    /**
     * @return the charset
     */
    public String getCharset() {
        return this.charset;
    }

    /**
     * @param charset the charset to set
     */
    public void setCharset(String charset) {
        this.charset = charset;
    }

    /**
     * @return the database
     */
    public String getDatabase() {
        return this.database;
    }

    /**
     * @param database the database to set
     */
    public void setDatabase(String database) {
        this.database = database;
    }
}
