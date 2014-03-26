/*
 * $RCSfile: Logger.java,v $$
 * $Revision: 1.1 $
 * $Date: 2014-3-24 $
 *
 * Copyright (C) 2008 WanMei, Inc. All rights reserved.
 *
 * This software is the proprietary information of WanMei, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.sql;

import java.io.PrintWriter;

/**
 * <p>Title: Logger</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class Logger
{
    private PrintWriter out;

    public Logger(PrintWriter out)
    {
        this.out = out;
    }

    public void log(String content)
    {
        this.out.println(content);
        this.out.flush();
    }
}
