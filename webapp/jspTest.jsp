<jsp:directive.page contentType="text/html;charset=UTF-8"/>
<jsp:directive.taglib prefix="c" taglib="" uri="http://java.sun.com/jsp/jstl/core"/>
<jsp:directive.taglib prefix="fmt" taglib="" uri="http://java.sun.com/jsp/jstl/fmt"/>
<%
    String myTest = "myTest";
%>
<c:out value="<%=myTest + "abc\"n"%>"/>
<c:out value="abc\r\n&quot;"/>
<h1>Hello World !</h1>

