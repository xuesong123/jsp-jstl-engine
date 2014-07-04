/*
 * $RCSfile: Keyword.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-13 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.compile;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: Keyword</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class Keyword
{
    public static final Map<String, String> map = new HashMap<String, String>();

    static{
        String[] keywords = new String[]{
            "abstract", "assert", "boolean", "break", "byte", "case",
            "catch", "char", "class", "const", "continue", "default",
            "do", "double", "else", "enum", "extends", "final", "finally",
            "float", "for", "goto", "if", "implements", "import", "instanceof",
            "int", "interface", "long", "native", "new", "package", "private",
            "protected", "public", "return", "strictfp", "short", "static",
            "super", "switch", "synchronized", "this", "throw", "throws",
            "transient", "try", "void", "volatile", "while"
        };

        for(int i = 0, length = keywords.length; i < length; i++)
        {
            map.put(keywords[i], keywords[i]);
        }
    }

    /**
     * @param keyword
     * @return String
     */
    public static String get(String keyword)
    {
        return map.get(keyword);
    }
}
