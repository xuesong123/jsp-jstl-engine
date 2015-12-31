/*
 * $RCSfile: TagLibrary.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-19 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>Title: TagLibrary</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class TagLibrary {
    private Map<String, TagInfo> library;

    /**
     *
     */
    protected TagLibrary() {
        this.library = new LinkedHashMap<String, TagInfo>();
    }

    /**
     * @param tagName
     * @return TagInfo
     */
    public TagInfo getTagInfo(String tagName) {
        return this.library.get(tagName);
    }

    /**
     * @param tagName
     * @return String
     */
    public String getTagClassName(String tagName) {
        TagInfo tagInfo = this.library.get(tagName);

        if(tagInfo != null) {
            return tagInfo.getTagClass();
        }
        return null;
    }

    /**
     * @param tagName
     * @param className
     */
    public void setup(String tagName, String className) {
        TagInfo tagInfo = this.library.get(tagName);

        if(tagInfo != null) {
            tagInfo.setTagClass(className);
            tagInfo.setBodyContent(0);
            tagInfo.setDescription(null);
        }
        else {
            tagInfo = new TagInfo();
            tagInfo.setName(tagName);
            tagInfo.setTagClass(className);
            tagInfo.setBodyContent(0);
            tagInfo.setDescription(null);
            this.library.put(tagName, tagInfo);
        }
    }

    /**
     * @param tagName
     * @param className
     * @param bodyContent
     * @param description
     */
    public void setup(String tagName, String className, int bodyContent, String description) {
        TagInfo tagInfo = this.library.get(tagName);

        if(tagInfo != null) {
            tagInfo.setTagClass(className);
            tagInfo.setBodyContent(bodyContent);
            tagInfo.setDescription(description);
        }
        else {
            tagInfo = new TagInfo();
            tagInfo.setName(tagName);
            tagInfo.setTagClass(className);
            tagInfo.setBodyContent(bodyContent);
            tagInfo.setDescription(description);
            this.library.put(tagName, tagInfo);
        }
    }

    /**
     * @param tagName
     * @param className
     * @param bodyContent
     * @param description
     */
    public void setup(String tagName, String className, String bodyContent, String description) {
        this.setup(tagName, className, TagInfo.getBodyContent(bodyContent), description);
    }

    /**
     * @param library
     */
    public void setup(Map<String, TagInfo> library) {
        this.library.putAll(library);
    }

    /**
     * @param from
     * @param name
     */
    public void rename(String from, String name) {
        TagInfo tagInfo = this.library.remove(from);

        if(tagInfo != null) {
            tagInfo.setName(name);
            this.library.put(name, tagInfo);
        }
    }

    /**
     * @return Map<String, String>
     */
    public Map<String, TagInfo> getLibrary() {
        return this.library;
    }

    public void println() {
        this.println("taglib");
    }

    public void println(String name) {
        if(name == null) {
            System.out.println("=============== taglib ===============");
        }
        else {
            System.out.println("=============== " + name + " ===============");
        }

        for(Map.Entry<String, TagInfo> entry : this.library.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue().getTagClass());
        }

        System.out.println();
    }

    public void release() {
        this.library.clear();
        this.library = null;
    }
}
