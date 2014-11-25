<html>
<head>
<title>test</title>
</head>
<body version="1.0">
<t:include file="/include/header.jsp"/>
<div>
    <p>template.home: ${template.home}</p>
    <p>template.path: ${template.path}</p>
    <p>template.lastModified: ${DateUtil.format(template.lastModified, "yyyy-MM-dd HH:mm:ss")}</p>
</div>
<c:if test="${1 == 1}">1 == 1</c:if>
<t:include file="/include/static.jsp" encoding="UTF-8"/>
<t:include file="/include/static.jsp" type="static" escape="xml" encoding="UTF-8"/>
<t:include file="/include/static.jsp" type="script" encoding="UTF-8"/>
<t:include file="/include/script.html" encoding="UTF-8"/>
<t:include file="/include/script.html" type="script" encoding="UTF-8"/>
<t:include file="/include/footer.jsp"/>

<h2>jsp include</h2>
<%@ include file="/include/footer.jsp"%>
</body>
</html>