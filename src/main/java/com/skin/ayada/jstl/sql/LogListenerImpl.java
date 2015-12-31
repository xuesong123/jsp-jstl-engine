/*
 * $RCSfile: LogListenerImpl.java,v $$
 * $Revision: 1.1 $
 * $Date: 2014-03-24 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.sql;

import java.io.PrintWriter;

import com.skin.ayada.util.LogListener;

/**
 * <p>Title: LogListenerImpl</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class LogListenerImpl implements LogListener {
    private PrintWriter out;

    public LogListenerImpl(PrintWriter out) {
        this.out = out;
    }

    public void log(String content) {
        this.out.println(content);
        this.out.flush();
    }
}
