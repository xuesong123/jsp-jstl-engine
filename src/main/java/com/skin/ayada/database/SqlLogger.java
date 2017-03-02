/*
 * $RCSfile: SqlLogger.java,v $
 * $Revision: 1.1 $
 * $Date: 2014-3-24 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.database;

import java.io.PrintWriter;

/**
 * <p>Title: SqlLogger</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public interface SqlLogger {
    /**
     * the standard out
     */
    public static final SqlLogger stdout = new SqlLogger() {
        /**
         * @param content
         */
        @Override
        public void log(String content) {
            System.out.println(content);
        }
    };

    /**
     * @param content
     */
    public void log(String content);

    /**
     * <p>Title: PrintLogger</p>
     * <p>Description: </p>
     * <p>Copyright: Copyright (c) 2006</p>
     * @author xuesong.net
     * @version 1.0
     */
    public static class PrintLogger implements SqlLogger{
        private PrintWriter out;

        /**
         * @param out
         */
        public PrintLogger(PrintWriter out) {
            this.out = out;
        }

        /**
         * @param content
         */
        @Override
        public void log(String content) {
            this.out.println(content);
            this.out.flush();
        }
    }
}
