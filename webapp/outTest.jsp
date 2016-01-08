<html>
<head>
<title>test</title>
</head>
<body version="1.0">
<c:map name="user">
    <c:entry name="userName" value="xuesong.net"/>
</c:map>
<h1>Hello World !</h1>
<%
    int myInt = 0;
    float myFloat = 1.1f;
%>
%%${#user.userName}
<%=(myInt + myFloat)%>
##${user.userName}
${#user.userName}

<c:out value="${pageContext}"/>
<c:out value="1 ${user.userName}"></c:out>
<c:out>2 <div>${user.userName}</div></c:out>
<c:if test="${util.notEmpty(userList)}">userList is not empty !</c:if>
<c:out id="1" escapeXml="true"><div><c:out id="2" value="${user.userName}"></c:out></div></c:out>
</body>
</html>