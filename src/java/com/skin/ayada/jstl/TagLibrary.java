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
    private Map<String, String> library;

    /**
     * 
     */
    protected TagLibrary()
    {
        this.library = new HashMap<String, String>();
    }

    /**
     * @param nodeName
     * @return String
     */
    public String getTagClassName(String nodeName)
    {
        return this.library.get(nodeName);
    }

    /**
     * @param name
     * @param className
     */
    public void setup(String name, String className)
    {
        this.library.put(name, className);
    }

    /**
     * @param library
     */
    public void setup(Map<String, String> library)
    {
        this.library.putAll(library);
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

        for(Map.Entry<String, String> entry : this.library.entrySet())
        {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        System.out.println();
    }

    public void release()
    {
        this.library.clear();
        this.library = null;
    }
}
