/*
 * $RCSfile: Test3.java.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-23 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.html;

/**
 * <p>Title: Test3</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class Test3 {
    /**
     * 0x9e3779b9
     * @param args
     */
    public static void main(String[] args) {
        float f1 = 0.618f;
        double d1 = Math.pow(2d, 32d);
        int result = (int)(f1 * d1);
        System.out.println(Math.pow(2, 32));
        System.out.println(Integer.toHexString(result));
    }
}
