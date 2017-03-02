
package com.skin.ayada.test.engine;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.skin.ayada.ExpressionContext;
import com.skin.ayada.JspTemplate;
import com.skin.ayada.JspWriter;
import com.skin.ayada.PageContext;
import com.skin.ayada.test.model.Book;
import com.skin.ayada.test.model.User;

/**
 * <p>Title: TestListTemplate</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author JspCompiler
 * @version 1.0
 */
@SuppressWarnings("unused")
public class TestListTemplate extends JspTemplate {
    /**
     * @param args
     */
    public static void main(String[] args){
        java.io.StringWriter writer = new java.io.StringWriter();
        PageContext pageContext = com.skin.ayada.runtime.JspFactory.getPageContext(writer);
        TestListTemplate template = new TestListTemplate();

        try {
            template.execute(pageContext);
            System.out.println(writer.toString());
        }
        catch(Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * @param pageContext
     * @throws Throwable
     */
    @Override
    public void _execute(final PageContext pageContext) throws Throwable {
        JspWriter jspWriter = pageContext.getOut();
        JspWriter out = pageContext.getOut();
        ExpressionContext expressionContext = pageContext.getExpressionContext();
        User user = (User)(pageContext.getAttribute("user"));
        Book[] books = (Book[])(pageContext.getAttribute("books"));
        out.write("<html>\r\n<head>\r\n");
        out.write("<title>");
        out.write((String)pageContext.getAttribute("engineName"));
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

    protected static final char[] _jsp_string_7 = "<html>\r\n<head>\r\n".toCharArray();
    protected static final char[] _jsp_string_10 = "<title>".toCharArray();
    protected static final char[] _jsp_string_13 = "</title>\r\n</head>\r\n<body>\r\n".toCharArray();
    protected static final char[] _jsp_string_16 = "<h1>".toCharArray();
    protected static final char[] _jsp_string_19 = " /".toCharArray();
    protected static final char[] _jsp_string_22 = "</h1>\r\n".toCharArray();
    protected static final char[] _jsp_string_25 = "<table>\r\n    <tr>\r\n        <th>NO.</th>\r\n        <th>Title</th>\r\n        <th>Author</th>\r\n        <th>Publisher</th>\r\n        <th>PublicationDate</th>\r\n        <th>Price</th>\r\n        <th>DiscountPercent</th>\r\n        <th>DiscountPrice</th>\r\n    </tr>\r\n".toCharArray();
    protected static final char[] _jsp_string_28 = "    <tr>\r\n        <td>".toCharArray();
    protected static final char[] _jsp_string_31 = "</td>\r\n        <td>".toCharArray();
    protected static final char[] _jsp_string_34 = "</td>\r\n        <td>".toCharArray();
    protected static final char[] _jsp_string_37 = "</td>\r\n        <td>".toCharArray();
    protected static final char[] _jsp_string_40 = "</td>\r\n        <td>".toCharArray();
    protected static final char[] _jsp_string_43 = "</td>\r\n        <td>".toCharArray();
    protected static final char[] _jsp_string_46 = "</td>\r\n        <td>".toCharArray();
    protected static final char[] _jsp_string_49 = "</td>\r\n        <td>".toCharArray();
    protected static final char[] _jsp_string_52 = "</td>\r\n    </tr>\r\n".toCharArray();
    protected static final char[] _jsp_string_55 = "</table>\r\n".toCharArray();
    protected static final char[] _jsp_string_58 = "<table>\r\n    <tr>\r\n        <td>No privilege.</td>\r\n    </tr>\r\n</table>\r\n".toCharArray();
    protected static final char[] _jsp_string_61 = "<table>\r\n    <tr>\r\n        <td>No login.</td>\r\n    </tr>\r\n</table>\r\n".toCharArray();
    protected static final char[] _jsp_string_64 = "</body>\r\n</html>\r\n".toCharArray();
}
