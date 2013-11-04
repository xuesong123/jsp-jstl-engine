<%@ page contentType="text/html; charset=UTF-8;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<title>Hello</title>
</head>
<body>
<t:include file="/include/header.jsp"/>

<h3>body</h3>
${@java.lang.System@out.println("Hello Ayada !")}
<c:print out="${@java.lang.System@out}" value="Hello Ayada !"/>

<t:include file="/include/static.jsp" type="static"/>

<t:include file="/include/footer.jsp"/>
</body>
</html>