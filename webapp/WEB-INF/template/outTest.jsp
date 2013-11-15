<html>
<head>
<title>test</title>
</head>
<body version="1.0">
<c:out value="hello world"></c:out>
<c:if test="${util.notEmpty(userList)}">userList is not empty !</c:if>
<c:out id="1" escapeXml="true"><div><c:out id="2" value="hello, ayada !"></c:out></div></c:out>
</body>
</html>