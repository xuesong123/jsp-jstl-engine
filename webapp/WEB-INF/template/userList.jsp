<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<title>User List</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<link rel="stylesheet" type="text/css" href="${contextPath}/resource/css/style.css"/>
</head>
<body>
<t:include file="/include/common/header.jsp"/>
<div class="wrap">
    <table border="1">
        <tr>
            <td>UserId</td>
            <td>UserName</td>
            <td>Age</td>
        </tr>
        <c:forEach items="${userList}" var="user" varStatus="status">
            <tr>
                <td><a href="/user/UserServlet?userId=${user.userId}">${user.userId}</a></td>
                <td><a href="/user/UserServlet?userId=${user.userId}">${user.userName}</a></td>
                <td>${user.age}</td>
            </tr>
        </c:forEach>
    </table>
</div>
<div style="height: 600px;"></div>
<t:include file="/include/common/footer.jsp"/>
</body>