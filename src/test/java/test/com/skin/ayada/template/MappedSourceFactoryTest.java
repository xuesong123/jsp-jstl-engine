/*
 * $RCSfile: MappedSourceFactoryTest.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-3-26 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.template;

import java.io.File;

import com.skin.ayada.source.MappedSourceFactory;

/**
 * <p>Title: MappedSourceFactoryTest</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class MappedSourceFactoryTest {
    /**
     * @param args
     */
    public static void main(String[] args) {
        MappedSourceFactory sourceFactory = new MappedSourceFactory();
        sourceFactory.setHome("E:\\WorkSpace\\fmbak\\webapp\\template");
        sourceFactory.add("/admin", "E:\\WorkSpace\\fmbak\\webapp\\admin");
        sourceFactory.add("/finder", "E:\\WorkSpace\\fmbak\\webapp\\admin\\finder");
        sourceFactory.add("/webcat", "E:\\WorkSpace\\fmbak\\webapp\\admin\\webcat");

        String[] pages = new String[]{
                "/finder/display.jsp",
                "/webcat/query.jsp",
                "/forum/thread.jsp",
                "/admin//user/userEdit.jsp"
        };

        for(String page : pages) {
            File file = sourceFactory.getFile(page);
            System.out.println(file.getAbsolutePath());
        }
    }
}
