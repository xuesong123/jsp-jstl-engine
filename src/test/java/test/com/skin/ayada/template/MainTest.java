/*
 * $RCSfile: MainTest.java,v $$
 * $Revision: 1.1 $
 * $Date: 2014年7月7日 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.template;

import com.skin.ayada.template.Main;

/**
 * <p>Title: MainTest</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class MainTest {
    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            Main.execute("webapp\\test4.jsp", "utf-8", (String)null);
            // Main.execute("D:\\workspace2\\finder\\webapp\\template\\finder\\finder.jsp", "UTF-8", "com.skin.ayada.template.JspTemplateFactory");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
