<html>
<head>
<title>${engineName}</title>
</head>
<body>
<h1>${user.userId} / ${user.userName}</h1>

<c:choose>
<c:when test="${user.userName == 'admin'}">
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
    <c:forEach var="book" items="${books}" varStatus="status">
    <c:if test="${book.price > 0}">
    <tr>
        <td>${status.index + 1}</td>
        <td>${book.title}</td>
        <td>${book.author}</td>
        <td>${book.publisher}</td>
        <td>${DateUtil.format(book.publication, "yyyy-MM-dd HH:mm:ss")}</td>
        <td>${book.price}</td>
        <td>${book.discount}%</td>
        <td>${book.price * book.discount / 100}</td>
        <t:comment>
        <td>${status.index + 1}</td>
        <td>xxx</td>
        <td>xxx</td>
        <td>xxx</td>
        <td>xxx</td>
        <td>xxx</td>
        <td>xxx</td>
        <td>xxx</td>
        </t:comment>
    </tr>
    </c:if>
    </c:forEach>
</table>
</c:when>
<c:when test="${user != null}">
<table>
    <tr>
        <td>No privilege.</td>
    </tr>
</table>
</c:when>
<c:otherwise>
<table>
    <tr>
        <td>No login.</td>
    </tr>
</table>
</c:otherwise>
</c:choose>
</body>
</html>
