/*
 * $RCSfile: ReflectTest.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-05 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.template;

import java.util.HashMap;
import java.util.Map;

import com.skin.ayada.factory.DefaultTagFactory;
import com.skin.ayada.factory.TagFactoryManager;
import com.skin.ayada.jstl.core.ForEachTag;
import com.skin.ayada.runtime.TagFactory;
import com.skin.ayada.statement.TagNode;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.util.TagUtil;

/**
 * <p>Title: ReflectTest</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class ReflectTest {
    /**
     * @param args
     */
    public static void main(String[] args) {
        /**
         * newInstanceTest > tagNodeTest > tagFactoryTest > (reflectTest == factoryMethodTest)
         */
        newInstanceTest();
        // reflectTest();
        // factoryMethodTest();
        tagFactoryTest();
        tagNodeTest();
    }

    public static void reflectTest() {
        int count = 10000000;
        long t1 = System.currentTimeMillis();
        for(int i = 0; i < count; i++) {
            testTag(TagUtil.create("c:if"));
        }
        long t2 = System.currentTimeMillis();
        System.out.println("reflectTest - count: " + count + ", times: " + (t2 - t1));
    }

    public static void newInstanceTest() {
        int count = 10000000;
        long t1 = System.currentTimeMillis();
        for(int i = 0; i < count; i++) {
            testTag(new ForEachTag());
        }
        long t2 = System.currentTimeMillis();
        System.out.println("newInstanceTest - count: " + count + ", times: " + (t2 - t1));
    }

    public static void factoryMethodTest() {
        Map<String, TagFactory> map = new HashMap<String, TagFactory>();
        DefaultTagFactory defaultTagFactory = new DefaultTagFactory();
        defaultTagFactory.setTagName("c:if");
        defaultTagFactory.setClassName("com.skin.ayada.jstl.core.IfTag");
        map.put("c:if", defaultTagFactory);

        int count = 10000000;
        long t1 = System.currentTimeMillis();
        for(int i = 0; i < count; i++) {
            testTag(map.get("c:if").create());
        }
        long t2 = System.currentTimeMillis();
        System.out.println("factoryMethodTest - count: " + count + ", times: " + (t2 - t1));
    }

    public static void tagFactoryTest() {
        /* warm up */
        TagFactoryManager tagFactoryManager = TagFactoryManager.getInstance();
        TagFactory tagFactory = tagFactoryManager.getTagFactory("c:forEach", "com.skin.ayada.jstl.core.ForEachTag");
        testTag(tagFactory.create());

        System.out.println("tagFactory: " + tagFactory);

        int count = 10000000;
        long t1 = System.currentTimeMillis();
        for(int i = 0; i < count; i++) {
            tagFactory = TagFactoryManager.getInstance().getTagFactory("c:forEach", "com.skin.ayada.jstl.core.ForEachTag");
            testTag(tagFactory.create());
        }
        long t2 = System.currentTimeMillis();
        System.out.println("tagFactoryTest - count: " + count + ", times: " + (t2 - t1));
    }

    public static void tagNodeTest() {
        TagNode node = new TagNode("");
        TagFactoryManager tagFactoryManager = TagFactoryManager.getInstance();
        TagFactory tagFactory = tagFactoryManager.getTagFactory("c:forEach", "com.skin.ayada.jstl.core.ForEachTag");
        node.setTagFactory(tagFactory);
        System.out.println("tagFactory: " + node.getTagFactory() + " - " + node.getTagFactory().getClass().getClassLoader() + " - " + node.getTagFactory().create().getClass().getClassLoader());

        int count = 10000000;
        long t1 = System.currentTimeMillis();
        for(int i = 0; i < count; i++) {
            testTag(tagFactory.create());
        }
        long t2 = System.currentTimeMillis();
        System.out.println("tagNodeTest2 - count: " + count + ", times: " + (t2 - t1));
    }

    public static void testTag(Tag tag) {
    }

    private MyTagFactory myTagFactory = new MyTagFactory();

    public MyTagFactory getMyTagFactory() {
        return this.myTagFactory;
    }
}
