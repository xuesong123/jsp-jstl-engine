<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<title>User Detail</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<link rel="stylesheet" type="text/css" href="${contextPath}/resource/css/style.css"/>
</head>
<body>
<t:include file="/include/common/header.jsp"/>
<div class="wrap">
    <p><a href="/user/UserListServlet">Detail</a></p>
    <p>userId: ${user.userId}</p>
    <p>userName: ${user.userName}</p>
    <p>userAge: ${user.age}</p>
</div>
<div style="height: 600px;"></div>
<t:include file="/include/common/footer.jsp"/>
</body>