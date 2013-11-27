/*
 * $RCSfile: ConstructorSupportTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-2-28 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.tagext;

/**
 * <p>Title: ConstructorSupportTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public interface ConstructorSupportTag
{
    /**
     * @param index
     * @param type
     * @param value
     */
    public void setArgument(int index, Class<?> type, Object value);
}
