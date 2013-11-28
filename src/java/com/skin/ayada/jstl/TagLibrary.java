/*
 * $RCSfile: TagLibrary.java,v $$
 * $Revision: 1.1  $
 * $Date: 2013-2-19  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: TagLibrary</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class TagLibrary
{
    private Map<String, TagInfo> library;

    /**
     * 
     */
    protected TagLibrary()
    {
        this.library = new HashMap<String, TagInfo>();
    }

    /**
     * @param name
     * @return TagInfo
     */
    public TagInfo getTagInfo(String name)
    {
        return this.library.get(name);
    }

    /**
     * @param nodeName
     * @return String
     */
    public String getTagClassName(String nodeName)
    {
        TagInfo tagInfo = this.library.get(nodeName);

        if(tagInfo != null)
        {
            return tagInfo.getTagClass();
        }
        return null;
    }

    /**
     * @param name
     * @param className
     */
    public void setup(String name, String className)
    {
        TagInfo tagInfo = this.library.get(name);

        if(tagInfo != null)
        {
            tagInfo.setTagClass(className);
            tagInfo.setBodyContent(0);
            tagInfo.setDescription(null);
        }
        else
        {
            tagInfo = new TagInfo();
            tagInfo.setName(name);
            tagInfo.setTagClass(className);
            tagInfo.setBodyContent(0);
            tagInfo.setDescription(null);
            this.library.put(name, tagInfo);
        }
    }

    /**
     * @param name
     * @param className
     */
    public void setup(String name, String className, int bodyContent, String description)
    {
        TagInfo tagInfo = this.library.get(name);

        if(tagInfo != null)
        {
            tagInfo.setTagClass(className);
            tagInfo.setBodyContent(bodyContent);
            tagInfo.setDescription(description);
        }
        else
        {
            tagInfo = new TagInfo();
            tagInfo.setName(name);
            tagInfo.setTagClass(className);
            tagInfo.setBodyContent(bodyContent);
            tagInfo.setDescription(description);
            this.library.put(name, tagInfo);
        }
    }

    /**
     * @param library
     */
    public void setup(Map<String, TagInfo> library)
    {
        this.library.putAll(library);
    }

    /**
     * @return Map<String, String>
     */
    public Map<String, TagInfo> getLibrary()
    {
        return this.library;
    }

    public void println()
    {
        this.println("taglib");
    }

    public void println(String name)
    {
        if(name == null)
        {
            System.out.println("=============== taglib ===============");
        }
        else
        {
            System.out.println("=============== " + name + " ===============");
        }

        for(Map.Entry<String, TagInfo> entry : this.library.entrySet())
        {
            System.out.println(entry.getKey() + " " + entry.getValue().getTagClass());
        }

        System.out.println();
    }

    public void release()
    {
        this.library.clear();
        this.library = null;
    }
}
