/*
 * $RCSfile: TagFactoryCreatorTest.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-6 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.jstl;

import com.skin.ayada.factory.TagFactoryManager;
import com.skin.ayada.runtime.TagFactory;

/**
 * <p>Title: TagFactoryCreatorTest</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class TagFactoryCreatorTest {
    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            TagFactory tagFactory = TagFactoryManager.getInstance().getTagFactory("c:forEach", "com.skin.ayada.jstl.core.ForEachTag");
            System.out.println(tagFactory);
            System.out.println(tagFactory.create());

            tagFactory = TagFactoryManager.getInstance().getTagFactory("c:scrollpage", "com.skin.finder.taglib.ScrollPage");
            System.out.println(tagFactory);
            System.out.println(tagFactory.create());
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
