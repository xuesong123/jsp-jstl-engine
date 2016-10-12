/*
 * $RCSfile: DefaultSourceFactoryTest.java,v $$
 * $Revision: 1.1 $
 * $Date: 2016-10-12 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.template;

import com.skin.ayada.source.DefaultSourceFactory;

/**
 * <p>Title: DefaultSourceFactoryTest</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class DefaultSourceFactoryTest {
    /**
     * @param args
     */
    public static void main(String[] args) {
        String path = "/finder-web/template/finder/workspace.jsp";
        String contextPath = "/finder-web";

        if(contextPath.length() > 0 && path.startsWith(contextPath)) {
            path = path.substring(contextPath.length());
        }
        System.out.println(path);

        DefaultSourceFactory sourceFactory = new DefaultSourceFactory("D:/Tomcat-7.0.37/webapps/finder-web/template");
        System.out.println(sourceFactory.getFile("/finder/index.jsp"));
    }
}
