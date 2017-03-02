/*
 * $RCSfile: JavaTest.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-11-08 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.test.engine;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import com.skin.ayada.test.Benchmark;
import com.skin.ayada.test.model.Book;
import com.skin.ayada.test.model.User;

/**
 * <p>Title: JavaTest</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class JavaTest implements Benchmark {
    /**
     * @return the name
     */
    @Override
    public String getName() {
        return "java";
    }

    /**
     * @param work
     * @throws Exception
     */
    @Override
    public void init(String work) throws Exception {
    }

    /**
     * @param name
     * @param context
     * @param stringWriter
     * @param count
     * @throws Exception
     */
    @Override
    public void execute(String name, Map<String, Object> context, StringWriter stringWriter, int count) throws Exception {
        context.put("engineName", this.getName());

        for(int i = 0; i < count; i++) {
            stringWriter.getBuffer().setLength(0);
            this.render(context, stringWriter);
        }
    }
    
    /**
     * @param context
     * @param out
     * @throws IOException
     */
    public void render(Map<String, Object> context, Writer out) throws IOException {
        User user = (User)(context.get("user"));
        Book[] books = (Book[])(context.get("books"));
        out.write("<html>\r\n<head>\r\n");
        out.write("<title>");
        out.write((String)context.get("engineName"));
        out.write("</title>\r\n</head>\r\n<body>\r\n");

        if(user != null) {
            out.write("<h1>");
            out.write(Long.toString(user.getUserId()));
            out.write(" / ");
            out.write(user.getUserName());
            out.write("</h1>\r\n");
        }
        else {
            out.write("<h1> / </h1>");
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if(user != null && "admin".equals(user.getUserName())) {
            int count = 1;
            out.write("<table>\r\n    <tr>\r\n        <th>NO.</th>\r\n        <th>Title</th>\r\n        <th>Author</th>\r\n        <th>Publisher</th>\r\n        <th>PublicationDate</th>\r\n        <th>Price</th>\r\n        <th>DiscountPercent</th>\r\n        <th>DiscountPrice</th>\r\n    </tr>\r\n");

            for(Book book : books) {
                if(book.getPrice() > 0) {
                    out.write("    <tr>\r\n        <td>");
                    out.write(Integer.toString(count++));
                    out.write("</td>\r\n        <td>");
                    out.write(book.getTitle());
                    out.write("</td>\r\n        <td>");
                    out.write(book.getAuthor());
                    out.write("</td>\r\n        <td>");
                    out.write(book.getPublisher());
                    out.write("</td>\r\n        <td>");
                    out.write(dateFormat.format(book.getPublication()));
                    out.write("</td>\r\n        <td>");
                    out.write(Integer.toString(book.getPrice()));
                    out.write("</td>\r\n        <td>");
                    out.write(Integer.toString(book.getDiscount()));
                    out.write("</td>\r\n        <td>");
                    out.write(Integer.toString(book.getPrice() * book.getDiscount() / 100));
                    out.write("%</td>\r\n    </tr>\r\n");
                }
            }
            out.write("</table>\r\n");
        }
        else if(user != null) {
            out.write("<table>\r\n    <tr>\r\n        <td>No privilege.</td>\r\n    </tr>\r\n</table>\r\n");
        }
        else {
            out.write("<table>\r\n    <tr>\r\n        <td>No login.</td>\r\n    </tr>\r\n</table>\r\n");
        }
        out.write("</body>\r\n</html>\r\n");
    }
}
