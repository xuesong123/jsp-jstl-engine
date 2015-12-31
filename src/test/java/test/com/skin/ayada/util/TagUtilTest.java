/*
 * $RCSfile: TagUtilTest.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-08-26 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.util;

import com.skin.ayada.util.ExpressionUtil;

/**
 * <p>Title: TagUtilTest</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class TagUtilTest {
    /**
     * @param args
     */
    public static void main(String[] args) {
        int type = ExpressionUtil.getDataType("user");
        System.out.println("type: " + type + ((int)('.')));
    }
}
