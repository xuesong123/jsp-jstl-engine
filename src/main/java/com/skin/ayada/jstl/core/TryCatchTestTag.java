/*
 * $RCSfile: TryCatchTestTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2014-03-25 $
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
public class TryCatchTestTag extends TagSupport implements TryCatchFinally {
    private String name;
    private String exception;

    /**
     * @return int
     */
    @Override
    public int doStartTag() throws Exception {
        System.out.println(this.name + ": doStartTag invoked !");

        if(this.contains(this.exception, "doStartTag")) {
            System.out.println(this.name + ": TryCatchTestTag.doStartTag throws Exception !");
            throw new RuntimeException(this.name + ": TryCatchTestTag.doStartTag throws Exception !");
        }
        return Tag.EVAL_PAGE;
    }

    /**
     * @return int
     */
    @Override
    public int doEndTag() throws Exception {
        System.out.println(this.name + ": doEndTag invoked !");

        if(this.contains(this.exception, this.name + ": doEndTag")) {
            System.out.println(this.name + ": TryCatchTestTag.doEndTag throws Exception !");
            throw new RuntimeException(this.name + ": TryCatchTestTag.doEndTag throws Exception !");
        }
        return Tag.EVAL_PAGE;
    }

    /**
     * @param throwable
     * @throws Throwable
     */
    @Override
    public void doCatch(Throwable throwable) throws Throwable {
        System.out.println(this.name + ": doCatch invoked !");

        if(this.contains(this.exception, "doCatch")) {
            System.out.println(this.name + ": TryCatchTestTag.doCatch throws Exception !");
            throw new RuntimeException(this.name + ": TryCatchTestTag.doCatch throws Exception !");
        }
    }

    /**
     * doFinally
     */
    @Override
    public void doFinally() {
        System.out.println(this.name + ": doFinally invoked !");

        if(this.contains(this.exception, "doFinally")) {
            System.out.println(this.name + ": TryCatchTestTag.doFinally throws Exception !");
            throw new RuntimeException(this.name + ": TryCatchTestTag.doFinally throws Exception !");
        }
    }

    /**
     * release
     */
    @Override
    public void release() {
        System.out.println(this.name + ": release invoked !");

        if(this.contains(this.exception, "release")) {
            System.out.println(this.name + ": TryCatchTestTag.release throws Exception !");
            throw new RuntimeException(this.name + ": TryCatchTestTag.release throws Exception !");
        }
    }

    /**
     * @param content
     * @param value
     * @return boolean
     */
    private boolean contains(String content, String value) {
        if(content != null) {
            if(content.trim().equals("*")) {
                return true;
            }

            String[] array = content.split(",");

            for(int i = 0; i < array.length; i++) {
                array[i] = array[i].trim();

                if(array[i].equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the exception
     */
    public String getException() {
        return this.exception;
    }

    /**
     * @param exception the exception to set
     */
    public void setException(String exception) {
        this.exception = exception;
    }
}
