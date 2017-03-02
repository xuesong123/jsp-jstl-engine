/*
 * $RCSfile: Json.java,v $
 * $Revision: 1.1 $
 * $Date: 2014-04-16 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada;

/**
 * <p>Title: Json</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public interface Json {
    /**
     * @param source
     * @return Object
     */
    public Object parse(String source);

    /**
     * @param object
     * @return String
     */
    public String stringify(Object object);
}
