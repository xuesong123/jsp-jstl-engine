<html>
<head>
<title>test</title>
</head>
<body version="1.0">
${user.userName}
<c:forEach items="${userList}" var="user">
    <c:choose>
        <c:when test="${user.age == 0}">0</c:when>
        <c:when test="${user.age == 1}">1</c:when>
        <c:when test="${user.age == 2}">2</c:when>
        <c:when test="${user.age == 3}">3</c:when>
        <c:otherwise>other: ${user.age}</c:otherwise>
    </c:choose>
</c:forEach>
</body>
</html>