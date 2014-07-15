[NODE]: <jsp:directive.page lineNumber="1" offset="0" length="2" contentType="text/html;charset=UTF-8">
[NODE]: </jsp:directive.page>
[NODE]: <jsp:directive.taglib lineNumber="2" offset="2" length="2" taglib="" prefix="c" uri="http://java.sun.com/jsp/jstl/core">
[NODE]: </jsp:directive.taglib>
[NODE]: <jsp:directive.taglib lineNumber="3" offset="4" length="2" taglib="" prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt">
[NODE]: </jsp:directive.taglib>
[NODE]: <jsp:directive.page lineNumber="4" offset="6" length="2" import="java.io.IOException">
[NODE]: </jsp:directive.page>
[TEXT]: <!-- t:import name=\"test:simpleTag\" className=\"test.com.skin.ayada.taglib.MySimpleTag\"/ -->\r\n
[NODE]: <c:forEach lineNumber="7" offset="9" length="3" tagClass="com.skin.ayada.jstl.core.ForEachTag" tagFactory="_tpl.com.skin.ayada.jstl.core.factory.ForEachTagFactory" items="1,1" var="myvar">
[VARI]: ${myvar}
[NODE]: </c:forEach>
[TEXT]: \r\n
[NODE]: <test:simpleTag lineNumber="8" offset="13" length="26" tagClass="test.com.skin.ayada.taglib.MySimpleTag" tagFactory="_tpl.test.com.skin.ayada.taglib.factory.MySimpleTagFactory">
[TEXT]: 123
[VARI]: ${mytest}
[TEXT]: \r\n    
[NODE]: <test:simpleTag lineNumber="9" offset="17" length="4" tagClass="test.com.skin.ayada.taglib.MySimpleTag" tagFactory="_tpl.test.com.skin.ayada.taglib.factory.MySimpleTagFactory">
[VARI]: ${mytest}
[TEXT]: xyz
[NODE]: </test:simpleTag>
[TEXT]: \r\n    
[NODE]: <c:if lineNumber="10" offset="22" length="7" tagClass="com.skin.ayada.jstl.core.IfTag" tagFactory="_tpl.com.skin.ayada.jstl.core.factory.IfTagFactory" test="${1 == 1}">
[TEXT]: \r\n        
[NODE]: <c:forEach lineNumber="11" offset="24" length="3" tagClass="com.skin.ayada.jstl.core.ForEachTag" tagFactory="_tpl.com.skin.ayada.jstl.core.factory.ForEachTagFactory" items="1,1" var="myvar">
[VARI]: ${myvar}
[NODE]: </c:forEach>
[TEXT]: \r\n    
[NODE]: </c:if>
[TEXT]: \r\n    
[NODE]: <c:forEach lineNumber="13" offset="30" length="7" tagClass="com.skin.ayada.jstl.core.ForEachTag" tagFactory="_tpl.com.skin.ayada.jstl.core.factory.ForEachTagFactory" items="1,1">
[TEXT]: \r\n        
[NODE]: <c:forEach lineNumber="14" offset="32" length="3" tagClass="com.skin.ayada.jstl.core.ForEachTag" tagFactory="_tpl.com.skin.ayada.jstl.core.factory.ForEachTagFactory" items="a,a" var="myvar">
[VARI]: ${myvar}
[NODE]: </c:forEach>
[TEXT]: \r\n    
[NODE]: </c:forEach>
[TEXT]: \r\nss\r\n
[NODE]: </test:simpleTag>
[TEXT]: \r\nabc\r\n    <c:if test=\"${1 == 1}\">\r\n        <c:forEach items=\"1,1\" var=\"myvar\">${myvar}</c:forEach>\r\n    </c:if>\r\n1
