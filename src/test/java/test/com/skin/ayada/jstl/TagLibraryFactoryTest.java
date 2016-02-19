/*
 * $RCSfile: TagLibraryFactoryTest.java,v $$
 * $Revision: 1.1  $
 * $Date: 2014-8-22  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.jstl;

import java.util.Map;

import com.skin.ayada.jstl.TagInfo;
import com.skin.ayada.jstl.TagLibrary;
import com.skin.ayada.jstl.TagLibraryFactory;

/**
 * <p>Title: TagLibraryFactoryTest</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class TagLibraryFactoryTest {
    /**
     * @param args
     */
    public static void main(String[] args) {
        test2();
    }

    /**
     *
     */
    public static void test1() {
        TagLibrary tagLibrary = TagLibraryFactory.getStandardTagLibrary();
        Map<String, TagInfo> library = tagLibrary.getLibrary();

        int i = 0;
        int size = library.size();

        System.out.println("        var list = [");
        for(Map.Entry<String, TagInfo> entry : library.entrySet()) {
            TagInfo tagInfo = entry.getValue();
            String bodyContent = TagInfo.getBodyContent(tagInfo.getBodyContent());
            System.out.print("            " + " new TagInfo(" + padding("\"" + entry.getKey() + "\",", 20, " ") + " \"" + tagInfo.getTagClass() + "\", \"" + bodyContent + "\", \"" + tagInfo.getDescription() + "\")");
            i++;

            if(i < size) {
                System.out.println(",");
            }
            else {
                System.out.println();
            }
        }
        System.out.println("        ];");
    }

    /**
     *
     */
    public static void test2() {
        TagLibrary tagLibrary = TagLibraryFactory.getStandardTagLibrary();
        Map<String, TagInfo> library = tagLibrary.getLibrary();
        System.out.println("<table>");
        System.out.println("    <tr>");
        System.out.println("        <td style=\"width: 200px\">标签</td>");
        System.out.println("        <td style=\"width: 200px\">实现</td>");
        System.out.println("        <td style=\"width: 200px\">类型</td>");
        System.out.println("        <td style=\"width: 200px\">示例</td>");
        System.out.println("    </tr>");

        for(Map.Entry<String, TagInfo> entry : library.entrySet()) {
            System.out.println("    <tr>");
            TagInfo tagInfo = entry.getValue();
            String bodyContent = TagInfo.getBodyContent(tagInfo.getBodyContent());
            System.out.println("        <td>" + entry.getKey() + "</td>");
            System.out.println("        <td>" + tagInfo.getTagClass() + "</td>");
            System.out.println("        <td>" + bodyContent + "</td>");
            System.out.println("        <td>xxx</td>");
            System.out.println("    </tr>");
        }
        System.out.println("</table>");
    }

    /**
     * @param source
     * @param length
     * @param pad
     * @return String
     */
    public static String padding(String source, int length, String pad) {
        StringBuilder buffer = new StringBuilder(source);

        while(buffer.length() < length) {
            buffer.append(pad);
        }

        if(buffer.length() > length) {
            return buffer.substring(0, length);
        }
        return buffer.toString();
    }
}
