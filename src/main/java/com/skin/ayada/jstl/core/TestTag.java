/*
 * $RCSfile: TestTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-14 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import java.io.PrintStream;
import java.io.PrintWriter;

import com.skin.ayada.tagext.TagSupport;

/**
 * <p>Title: TestTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class TestTag extends TagSupport {
    private boolean myBoolean;
    private char myChar;
    private byte myByte;
    private byte myShort;
    private int myInt;
    private float myFloat;
    private double myDouble;
    private long myLong;
    private String myString;
    private Object myObject;

    /**
     *
     */
    public TestTag() {
    }

    /**
     * @param myChar
     * @param myByte
     */
    public TestTag(char myChar, byte myByte) {
        this.myChar = myChar;
        this.myByte = myByte;
    }

    /**
     * @param myLong
     * @param myString
     */
    public TestTag(long myLong, String myString) {
        this.myLong = myLong;
        this.myString = myString;
    }

    /**
     * @return int
     * @throws Exception
     */
    @Override
    public int doStartTag() throws Exception {
        this.print();
        return SKIP_BODY;
    }

    /**
     *
     */
    public void print() {
        this.print(new PrintWriter(this.pageContext.getOut()));
    }

    /**
     * @param out
     */
    public void print(PrintStream out) {
        this.print(new PrintWriter(out));
    }

    /**
     * @param out
     */
    public void print(PrintWriter out) {
        out.println("<p>---------------- " + this.getClass().getName() + " ----------------</p>");
        out.println("<p>myBoolean: " + this.myBoolean + "</p>");

        if(this.myChar == '\0') {
            out.println("<p>myChar: \\0</p>");
        }
        else {
            out.println("<p>myChar: " + this.myChar + "</p>");
        }

        out.println("<p>myByte: " + this.myByte + "</p>");
        out.println("<p>myShort: " + this.myShort + "</p>");
        out.println("<p>myInt: " + this.myInt + "</p>");
        out.println("<p>myFloat: " + this.myFloat + "</p>");
        out.println("<p>myDouble: " + this.myDouble + "</p>");
        out.println("<p>myLong: " + this.myLong + "</p>");
        out.println("<p>myString: " + this.myString + "</p>");
        out.println("<p>myObject: " + this.myObject + "</p>");
        out.flush();
    }

    /**
     * @return the myBoolean
     */
    public boolean isMyBoolean() {
        return this.myBoolean;
    }

    /**
     * @param myBoolean the myBoolean to set
     */
    public void setMyBoolean(boolean myBoolean) {
        this.myBoolean = myBoolean;
    }

    /**
     * @return the myChar
     */
    public char getMyChar() {
        return this.myChar;
    }

    /**
     * @param myChar the myChar to set
     */
    public void setMyChar(char myChar) {
        this.myChar = myChar;
    }

    /**
     * @return the myByte
     */
    public byte getMyByte() {
        return this.myByte;
    }

    /**
     * @param myByte the myByte to set
     */
    public void setMyByte(byte myByte) {
        this.myByte = myByte;
    }

    /**
     * @return the myShort
     */
    public byte getMyShort() {
        return this.myShort;
    }

    /**
     * @param myShort the myShort to set
     */
    public void setMyShort(byte myShort) {
        this.myShort = myShort;
    }

    /**
     * @return the myInt
     */
    public int getMyInt() {
        return this.myInt;
    }
    /**
     * @param myInt the myInt to set
     */
    public void setMyInt(int myInt) {
        this.myInt = myInt;
    }
    /**
     * @return the myFloat
     */
    public float getMyFloat() {
        return this.myFloat;
    }
    /**
     * @param myFloat the myFloat to set
     */
    public void setMyFloat(float myFloat) {
        this.myFloat = myFloat;
    }
    /**
     * @return the myDouble
     */
    public double getMyDouble() {
        return this.myDouble;
    }
    /**
     * @param myDouble the myDouble to set
     */
    public void setMyDouble(double myDouble) {
        this.myDouble = myDouble;
    }
    /**
     * @return the myLong
     */
    public long getMyLong() {
        return this.myLong;
    }
    /**
     * @param myLong the myLong to set
     */
    public void setMyLong(long myLong) {
        this.myLong = myLong;
    }
    /**
     * @return the myString
     */
    public String getMyString() {
        return this.myString;
    }
    /**
     * @param myString the myString to set
     */
    public void setMyString(String myString) {
        this.myString = myString;
    }

    /**
     * @return the myObject
     */
    public Object getMyObject() {
        return this.myObject;
    }

    /**
     * @param myObject the myObject to set
     */
    public void setMyObject(Object myObject) {
        this.myObject = myObject;
    }
}
