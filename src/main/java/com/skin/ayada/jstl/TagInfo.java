/*
 * $RCSfile: TagInfo.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-28 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl;

/**
 * <p>Title: TagInfo</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class TagInfo
{
    private String name;
    private String tagClass;
    private int bodyContent;
    private String description;

    public static final int JSP = 0;
    public static final int TAGDEPENDENT = 1;
    public static final int EMPTY = 2;

    /**
     * @return the name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the tagClass
     */
    public String getTagClass()
    {
        return this.tagClass;
    }

    /**
     * @param tagClass the tagClass to set
     */
    public void setTagClass(String tagClass)
    {
        this.tagClass = tagClass;
    }

    /**
     * @return the bodyContent
     */
    public int getBodyContent()
    {
        return this.bodyContent;
    }

    /**
     * @param bodyContent the bodyContent to set
     */
    public void setBodyContent(int bodyContent)
    {
        this.bodyContent = bodyContent;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return this.description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * @param bodyContent
     * @return int
     */
    public static int getBodyContent(String bodyContent)
    {
        if(bodyContent == null)
        {
            return JSP;
        }

        String type = bodyContent.trim().toLowerCase();

        if(type.equals("tagdependent"))
        {
            return TAGDEPENDENT;
        }
        else if(type.equals("empty"))
        {
            return EMPTY;
        }

        return JSP;
    }

    @Override
    public String toString()
    {
        StringBuilder buffer = new StringBuilder();
        buffer.append("{\"");
        buffer.append(this.getName());
        buffer.append("\": \"");
        buffer.append(this.getTagClass());
        buffer.append("\", ");
        buffer.append("\"bodyContent\": \"");
        buffer.append(this.getBodyContent());
        buffer.append("\", \"description\": \"");
        if(this.getDescription() != null)
        {
            buffer.append(this.getDescription());
        }
        buffer.append("\"}");
        return buffer.toString();
    }
}
