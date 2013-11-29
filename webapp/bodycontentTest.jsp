<%@ page contentType="text/html;charset=UTF-8"%>
<c:map name="user">
    <c:entry name="userName" value="ayada"/>
    <c:entry name="nickName" value="ayada"/>
</c:map>
<html>
<head>
<title>test</title>
</head>
<body version="1.0">
<p><c:out value="${user.userName}"></c:out></p>
<p><c:out><span>${user.userName}</span></c:out></p>
<p><c:out escapeXml="true"><span>${user.userName}</span></c:out></p>
</body>
</html>