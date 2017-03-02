/*
 * $RCSfile: DataSetTag.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-04-25 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.csv;

import com.skin.ayada.tagext.BodyContent;
import com.skin.ayada.tagext.BodyTag;
import com.skin.ayada.tagext.BodyTagSupport;

/**
 * <p>Title: DataSetTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class DataSetTag extends BodyTagSupport {
    private Object value;
    private String tableName;

    /**
     * @return int
     * @throws Exception
     */
    @Override
    public int doStartTag() throws Exception {
        DataSet dataSet = (DataSet)(this.value);

        if(this.tableName != null) {
            this.pageContext.getOut().write(dataSet.getInsert(this.tableName, false));
            return SKIP_BODY;
        }
        else {
            return BodyTag.EVAL_BODY_BUFFERED;
        }
    }

    /**
     * @return int
     * @throws Exception
     */
    @Override
    public int doEndTag() throws Exception {
        BodyContent bodyContent = this.getBodyContent();

        if(bodyContent != null) {
            DataSet dataSet = (DataSet)(this.value);
            System.out.println("headers: " + dataSet.getHeaders().keySet());
            this.pageContext.getOut().write(dataSet.replace(bodyContent.getString()));
        }
        return EVAL_PAGE;
    }

    /**
     * @param value
     * @return DataSet
     */
    protected static DataSet getDataSet(Object value) {
        return (DataSet)value;
    }

    /**
     * @return the value
     */
    public Object getValue() {
        return this.value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * @return the tableName
     */
    public String getTableName() {
        return this.tableName;
    }

    /**
     * @param tableName the tableName to set
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
