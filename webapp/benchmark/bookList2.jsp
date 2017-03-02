<%@ page import="com.skin.ayada.util.DateUtil"%>
<%@ page import="com.skin.ayada.test.model.User"%>
<%@ page import="com.skin.ayada.test.model.Book"%>
<html>
<head>
<%
    User user = (User)(pageContext.getAttribute("user"));
%>
<title><%out.write(pageContext.getAttribute("engineName"));%></title>
</head>
<body>
<%
    if(user != null) {
%>
<h1><%out.write(Long.toString(user.getUserId()));%> / <%out.write(user.getUserName());%></h1>
<%
    }
    else {
        out.write("<h1> / </h1>");
    }

    if(user != null && "admin".equals(user.getUserName())) {
%>
<table>
    <tr>
        <th>NO.</th>
        <th>Title</th>
        <th>Author</th>
        <th>Publisher</th>
        <th>PublicationDate</th>
        <th>Price</th>
        <th>DiscountPercent</th>
        <th>DiscountPrice</th>
    </tr>
    <%
        int i = 1;
        Book[] books = (Book[])(pageContext.getAttribute("books"));
        for(Book book : books) {
            if(book.getPrice() > 0) {
                int discountPrice = book.getPrice() * book.getDiscount() / 100;
    %>
    <tr>
        <td><%out.write(Integer.toString(i++));%></td>
        <td><%out.write(book.getTitle());%></td>
        <td><%out.write(book.getAuthor());%></td>
        <td><%out.write(book.getPublisher());%></td>
        <td><%out.write(DateUtil.format(book.getPublication(), "yyyy-MM-dd HH:mm:ss"));%></td>
        <td><%out.write(Integer.toString(book.getPrice()));%></td>
        <td><%out.write(Integer.toString(book.getDiscount()));%></td>
        <td><%out.write(Integer.toString(discountPrice));%></td>
    </tr>
    <%
            }
        }
    %>
</table>
<%
    }
    else if(user != null) {
%>
<table>
    <tr>
        <td>No privilege.</td>
    </tr>
</table>
<%
    }
    else {
%>
<table>
    <tr>
        <td>No login.</td>
    </tr>
</table>
<%
    }
%>
</body>
</html>
