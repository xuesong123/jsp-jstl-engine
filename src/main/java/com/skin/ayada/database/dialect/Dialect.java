/*
 * $RCSfile: Dialect.java,v $
 * $Revision: 1.1  $
 * $Date: 2009-3-1  $
 *
 * Copyright (C) 2005 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.database.dialect;

import com.skin.ayada.database.Column;

/**
 * <p>Title: Dialect</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public interface Dialect
{
    /**
     * @param column
     * @return String
     */
    public String convert(Column column);

    /**
     * @param tableName
     * @return String
     */
    public String getTableName(String tableName);

    /**
     * @param columnName
     * @return String
     */
    public String getColumnName(String columnName);
}
