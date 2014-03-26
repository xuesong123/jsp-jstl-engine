/*
 * $RCSfile: QueryTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2014-3-25 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.skin.ayada.tagext.IterationTag;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TagSupport;
import com.skin.ayada.tagext.TryCatchFinally;

/**
 * <p>Title: QueryTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class QueryTag extends TagSupport implements IterationTag, TryCatchFinally
{
    private String var = null;
    private String sql = null;
    private int count  = 0;
    private int offset = 0;
    private int length = 0;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    @Override
    public int doStartTag() throws Exception
    {
        if(this.connection == null)
        {
            this.connection = (Connection)(this.pageContext.getAttribute("connection"));
        }

        if(this.connection == null)
        {
            Tag tag = this.getParent();

            if(tag instanceof ConnectTag)
            {
                this.connection = ((ConnectTag)tag).getConnection();
            }
        }

        if(this.connection == null)
        {
            throw new NullPointerException("connection must be not null");
        }

        if(this.offset > 1)
        {
            this.statement = this.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            this.resultSet = this.statement.executeQuery(sql);
            this.resultSet.absolute(this.offset - 1);
        }
        else
        {
            this.statement = this.connection.createStatement();
            this.resultSet = this.statement.executeQuery(sql);
        }

        if(this.resultSet.next())
        {
            this.count++;
            this.pageContext.setAttribute(this.var, this.resultSet);
            return EVAL_BODY_INCLUDE;
        }

        return SKIP_BODY;
    }

    /**
     * @return int
     */
    @Override
    public int doAfterBody() throws Exception
    {
        if(this.length > 0 && this.count >= this.length)
        {
            return SKIP_BODY;
        }

        if(this.resultSet.next())
        {
            this.count++;
            return EVAL_BODY_AGAIN;
        }

        return SKIP_BODY;
    }

    @Override
    public void doCatch(Throwable throwable) throws Throwable
    {
        if(throwable != null)
        {
            throwable.printStackTrace(System.out);
        }
    }

    @Override
    public void doFinally()
    {
        Jdbc.close(this.resultSet);
        Jdbc.close(this.statement);
    }

    /**
     * @return the var
     */
    public String getVar()
    {
        return this.var;
    }

    /**
     * @param var the var to set
     */
    public void setVar(String var)
    {
        this.var = var;
    }

    /**
     * @return the sql
     */
    public String getSql()
    {
        return this.sql;
    }

    /**
     * @param sql the sql to set
     */
    public void setSql(String sql)
    {
        this.sql = sql;
    }
    
    /**
     * @return the count
     */
    public int getCount()
    {
        return this.count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count)
    {
        this.count = count;
    }

    /**
     * @return the offset
     */
    public int getOffset()
    {
        return this.offset;
    }

    /**
     * @param offset the offset to set
     */
    public void setOffset(int offset)
    {
        this.offset = offset;
    }

    /**
     * @return the length
     */
    public int getLength()
    {
        return this.length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(int length)
    {
        this.length = length;
    }

    /**
     * @return the statement
     */
    public Statement getStatement()
    {
        return this.statement;
    }

    /**
     * @param statement the statement to set
     */
    public void setStatement(Statement statement)
    {
        this.statement = statement;
    }

    /**
     * @return the resultSet
     */
    public ResultSet getResultSet()
    {
        return this.resultSet;
    }

    /**
     * @param resultSet the resultSet to set
     */
    public void setResultSet(ResultSet resultSet)
    {
        this.resultSet = resultSet;
    }

    /**
     * @return the connection
     */
    public Connection getConnection()
    {
        return this.connection;
    }

    /**
     * @param connection the connection to set
     */
    public void setConnection(Connection connection)
    {
        this.connection = connection;
    }
}
