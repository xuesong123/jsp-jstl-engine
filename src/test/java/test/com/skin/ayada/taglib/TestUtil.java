/*
 * $RCSfile: TestUtil.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-12-10 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.taglib;

/**
 * <p>Title: TestUtil</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class TestUtil {
    /**
     * @param name
     * @return String
     */
    public String camel(String name) {
        if(name == null || name.trim().length() < 1) {
            return "";
        }

        String[] subs = name.split("_");
        StringBuilder buffer = new StringBuilder();

        if(name.startsWith("_")) {
            buffer.append("_");
        }

        if(subs.length == 1) {
            String s = subs[0];

            if("ID".equals(s)) {
                buffer.append("Id");
            }
            else if(s.toUpperCase().equals(s)) {
                buffer.append(Character.toUpperCase(s.charAt(0)));
                buffer.append(s.substring(1).toLowerCase());
            }
            else {
                buffer.append(Character.toUpperCase(s.charAt(0))).append(s.substring(1));
            }
        }
        else {
            for(String s : subs) {
                if(s.length() > 0) {
                    if("ID".equals(s)) {
                        buffer.append(s);
                    }
                    else if(s.toUpperCase().equals(s)) {
                        buffer.append(Character.toUpperCase(s.charAt(0)));
                        buffer.append(s.substring(1).toLowerCase());
                    }
                    else {
                        buffer.append(Character.toUpperCase(s.charAt(0))).append(s.substring(1));
                    }
                }
            }
        }

        if(name.endsWith("_")) {
            buffer.append("_");
        }
        buffer.setCharAt(0, Character.toLowerCase(buffer.charAt(0)));
        return buffer.toString();
    }

    /**
     * @param name
     * @return String
     */
    public String uncamel(String name) {
        if(name.equalsIgnoreCase("id")) {
            return "id";
        }

        char c;
        StringBuilder buffer = new StringBuilder();
        for(int i = 0, length = name.length(); i < length; i++) {
            c = name.charAt(i);

            if(c <= ' ' || c == '\'') {
                break;
            }

            if(i > 0 && Character.isUpperCase(c)) {
                buffer.append("_");
                buffer.append(Character.toLowerCase(c));
            }
            else {
                buffer.append(c);
            }
        }
        return buffer.toString();
    }
}
