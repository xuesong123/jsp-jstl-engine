<html>
<head>
<title>test</title>
</head>
<body version="1.0">
<p>${user.userName}</p>
<c:if test="${1 != 2}">Hello World!</c:if>

<c:forEach items="${userList}" var="user">
    <c:if test="${1 == 1}">${user.userName}</c:if>
</c:forEach>
<p>${user.userName}</p>
<c:forEach items="${userList}" var="user"><c:if test="${user.userName == 'xuesong.net3'}">${user.userName}</c:if></c:forEach>
<c:out value="123"/>

/* */
<p>${user.userName}</p>
// // //
</body>
</html>