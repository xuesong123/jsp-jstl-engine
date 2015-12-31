/*
 * $RCSfile: SqlTag.java,v $$
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
import java.io.PrintWriter;
import java.io.StringReader;
import java.sql.Connection;

import com.skin.ayada.tagext.BodyContent;
import com.skin.ayada.tagext.BodyTag;
import com.skin.ayada.tagext.BodyTagSupport;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TagSupport;
import com.skin.ayada.util.LogListener;
import com.skin.ayada.util.SqlPlus;

/**
 * <p>Title: SqlTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class SqlTag extends BodyTagSupport {
    private String sql = null;
    private String home = null;
    private String file = null;
    private String encoding = null;
    private Object out;
    private Connection connection;

    @Override
    public int doStartTag() throws Exception {
        return BodyTag.EVAL_BODY_BUFFERED;
    }

    /**
     * @return int
     */
    @Override
    public int doEndTag() throws Exception {
        if(this.connection == null) {
            this.connection = SqlTag.getConnection(this);
        }

        if(this.connection == null) {
            throw new NullPointerException("connection must be not null");
        }

        LogListener logListener = SqlTag.getLogListener(this.out);
        SqlPlus sqlPlus = new SqlPlus(this.connection, logListener);
        sqlPlus.setHome(this.home);

        if(this.encoding == null) {
            this.encoding = "UTF-8";
        }

        if(this.sql != null) {
            sqlPlus.execute(this.sql);
        }
        else if(this.file != null) {
            sqlPlus.execute(new File(this.home, this.file), this.encoding);
        }
        else {
            BodyContent bodyContent = this.getBodyContent();

            if(bodyContent != null) {
                String content = bodyContent.getString().trim();
                StringReader stringReader = new StringReader(content);
                sqlPlus.execute(stringReader, this.encoding);
            }
        }
        return EVAL_PAGE;
    }

    /**
     * @param tag
     * @return Connection
     */
    public static Connection getConnection(TagSupport tag) {
        Connection connection = null;
        Tag parent = tag.getParent();

        if(parent instanceof ConnectTag) {
            connection = ((ConnectTag)parent).getConnection();
        }

        if(connection == null) {
            connection = (Connection)(tag.getPageContext().getAttribute("connection"));
        }

        return connection;
    }

    /**
     * @param out
     * @return Logger
     */
    public static LogListener getLogListener(Object out) {
        if(out instanceof LogListener) {
            return (LogListener)(out);
        }

        PrintWriter printWriter = TagSupport.getPrintWriter(out);

        if(printWriter != null) {
            return new LogListenerImpl(printWriter);
        }
        return null;
    }

    /**
     * @return the sql
     */
    public String getSql() {
        return this.sql;
    }

    /**
     * @param sql the sql to set
     */
    public void setSql(String sql) {
        this.sql = sql;
    }

    /**
     * @return the home
     */
    public String getHome() {
        return this.home;
    }

    /**
     * @param home the home to set
     */
    public void setHome(String home) {
        this.home = home;
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
     * @return the encoding
     */
    public String getEncoding() {
        return this.encoding;
    }

    /**
     * @param encoding the encoding to set
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * @return the out
     */
    public Object getOut() {
        return this.out;
    }

    /**
     * @param out the out to set
     */
    public void setOut(Object out) {
        this.out = out;
    }

    /**
     * @return the connection
     */
    public Connection getConnection() {
        return this.connection;
    }

    /**
     * @param connection the connection to set
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
