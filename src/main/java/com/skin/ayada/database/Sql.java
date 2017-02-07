/*
 * $RCSfile: Sql.java,v $
 * $Revision: 1.1 $
 * $Date: 2014-03-25 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.database;

/**
 * <p>Title: Sql</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class Sql {
    /**
     * @param c
     * @return boolean
     */
    public static boolean isSqlIdentifierPart(int c) {
        return Character.isJavaIdentifierPart(c);
    }

    /**
     * @param c
     * @return boolean
     */
    public static boolean isSqlIdentifierPart(char c) {
        return Character.isJavaIdentifierPart(c);
    }

    /**
     * @param c
     * @return boolean
     */
    public static boolean isSqlIdentifierStart(int c) {
        return Character.isJavaIdentifierStart(c);
    }

    /**
     * @param c
     * @return boolean
     */
    public static boolean isSqlIdentifierStart(char c) {
        return Character.isJavaIdentifierStart(c);
    }

    /**
     * @param source
     * @return boolean
     */
    public static boolean isSqlIdentifier(String source) {
        if(source == null || source.length() < 1) {
            return false;
        }

        if(Character.isJavaIdentifierStart(source.charAt(0)) == false) {
            return false;
        }

        for(int i = 0; i < source.length(); i++) {
            if(Character.isJavaIdentifierPart(source.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param identifier
     * @return String
     */
    public static String filter(String identifier) {
        if(identifier == null) {
            return "";
        }

        String result = identifier.trim().replace('\'', ' ');
        int k = result.indexOf(" ");

        if(k > -1) {
            return result.substring(0, k);
        }
        else {
            return result;
        }
    }

    /**
     * @param source
     * @return String
     */
    public static String escape(String source) {
        if(source == null) {
            return "";
        }

        StringBuilder buffer = new StringBuilder();
        escape(buffer, source);
        return buffer.toString();
    }

    /**
     * @param buffer
     * @param source
     */
    public static void escape(StringBuilder buffer, String source) {
        if(source == null) {
            return;
        }

        char c;

        for(int i = 0, length = source.length(); i < length; i++) {
            c = source.charAt(i);

            switch (c) {
                case '\'': {
                    buffer.append("\\'");break;
                }
                case '\r': {
                    buffer.append("\\r");break;
                }
                case '\n': {
                    buffer.append("\\n");break;
                }
                case '\t': {
                    buffer.append("\\t");break;
                }
                case '\b': {
                    buffer.append("\\b");break;
                }
                case '\f': {
                    buffer.append("\\f");break;
                }
                case '\\': {
                    buffer.append("\\\\");break;
                }
                default : {
                    buffer.append(c);break;
                }
            }
        }
    }
}
