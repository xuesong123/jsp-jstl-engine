/*
 * $RCSfile: TestTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-14 $
 *
 * Copyright (C) 2008 WanMei, Inc. All rights reserved.
 *
 * This software is the proprietary information of WanMei, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.taglib;

import com.skin.ayada.runtime.JspWriter;
import com.skin.ayada.tagext.TagSupport;

/**
 * <p>Title: TestTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class TestTag extends TagSupport
{
    private int myInt;
    private float myFloat;
    private double myDouble;
    private long myLong;
    private String myString;

    @Override
    public int doStartTag()
    {
        try
        {
            JspWriter writer = pageContext.getOut();
            writer.println("<p>myInt: " + this.myInt + "</p>");
            writer.println("<p>myFloat: " + this.myFloat + "</p>");
            writer.println("<p>myDouble: " + this.myDouble + "</p>");
            writer.println("<p>myLong: " + this.myLong + "</p>");
            writer.println("<p>myString: " + this.myString + "</p>");
            writer.println();
            writer.flush();
        }
        catch(java.io.IOException e)
        {
            e.printStackTrace();
        }

        return SKIP_BODY;
    }
    
    /**
     * @return the myInt
     */
    public int getMyInt()
    {
        return this.myInt;
    }
    /**
     * @param myInt the myInt to set
     */
    public void setMyInt(int myInt)
    {
        this.myInt = myInt;
    }
    /**
     * @return the myFloat
     */
    public float getMyFloat()
    {
        return this.myFloat;
    }
    /**
     * @param myFloat the myFloat to set
     */
    public void setMyFloat(float myFloat)
    {
        this.myFloat = myFloat;
    }
    /**
     * @return the myDouble
     */
    public double getMyDouble()
    {
        return this.myDouble;
    }
    /**
     * @param myDouble the myDouble to set
     */
    public void setMyDouble(double myDouble)
    {
        this.myDouble = myDouble;
    }
    /**
     * @return the myLong
     */
    public long getMyLong()
    {
        return this.myLong;
    }
    /**
     * @param myLong the myLong to set
     */
    public void setMyLong(long myLong)
    {
        this.myLong = myLong;
    }
    /**
     * @return the myString
     */
    public String getMyString()
    {
        return this.myString;
    }
    /**
     * @param myString the myString to set
     */
    public void setMyString(String myString)
    {
        this.myString = myString;
    }
}
