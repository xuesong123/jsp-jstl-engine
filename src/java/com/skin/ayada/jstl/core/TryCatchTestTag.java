/*
 * $RCSfile: TryCatchTestTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2014-3-25 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TagSupport;
import com.skin.ayada.tagext.TryCatchFinally;

/**
 * <p>Title: TryCatchTestTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class TryCatchTestTag extends TagSupport implements TryCatchFinally
{
    private String exception;

    @Override
    public int doStartTag() throws Exception
    {
        System.out.println("doStartTag invoked !");

        if(this.contains(this.exception, "doStartTag"))
        {
            System.out.println("TryCatchTestTag.doStartTag throws Exception !");
            throw new RuntimeException("TryCatchTestTag.doStartTag throws Exception !");
        }

        return Tag.EVAL_PAGE;
    }

    /**
     * @return int
     */
    @Override
    public int doEndTag() throws Exception
    {
        System.out.println("doEndTag invoked !");

        if(this.contains(this.exception, "doEndTag"))
        {
            System.out.println("TryCatchTestTag.doEndTag throws Exception !");
            throw new RuntimeException("TryCatchTestTag.doEndTag throws Exception !");
        }

        return Tag.EVAL_PAGE;
    }

    @Override
    public void doCatch(Throwable throwable) throws Throwable
    {
        System.out.println("doCatch invoked !");

        if(this.contains(this.exception, "doCatch"))
        {
            System.out.println("TryCatchTestTag.doCatch throws Exception !");
            throw new RuntimeException("TryCatchTestTag.doCatch throws Exception !");
        }
    }

    @Override
    public void doFinally()
    {
        System.out.println("doFinally invoked !");

        if(this.contains(this.exception, "doFinally"))
        {
            System.out.println("TryCatchTestTag.doFinally throws Exception !");
            throw new RuntimeException("TryCatchTestTag.doFinally throws Exception !");
        }
    }

    /**
     * @param content
     * @param value
     * @return boolean
     */
    private boolean contains(String content, String value)
    {
        if(content != null)
        {
            if(content.trim().equals("*"))
            {
                return true;
            }

            String[] array = content.split(",");

            for(int i = 0; i < array.length; i++)
            {
                array[i] = array[i].trim();

                if(array[i].equals(value))
                {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * @return the exception
     */
    public String getException()
    {
        return this.exception;
    }

    /**
     * @param exception the exception to set
     */
    public void setException(String exception)
    {
        this.exception = exception;
    }
}
