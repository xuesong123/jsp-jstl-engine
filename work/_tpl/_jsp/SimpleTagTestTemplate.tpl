[NODE]: <c:forEach lineNumber="2" offset="0" length="3" tagClass="com.skin.ayada.jstl.core.ForEachTag" tagFactory="_tpl.com.skin.ayada.jstl.core.factory.ForEachTagFactory" items="1,1" var="myvar">
[VARI]: ${myvar}
[NODE]: </c:forEach>
[TEXT]: \r\n
[NODE]: <test:simpleTag lineNumber="3" offset="4" length="26" tagClass="test.com.skin.ayada.taglib.MySimpleTag" tagFactory="_tpl.test.com.skin.ayada.taglib.factory.MySimpleTagFactory">
[TEXT]: 123
[VARI]: ${mytest}
[TEXT]: \r\n    
[NODE]: <test:simpleTag lineNumber="4" offset="8" length="4" tagClass="test.com.skin.ayada.taglib.MySimpleTag" tagFactory="_tpl.test.com.skin.ayada.taglib.factory.MySimpleTagFactory">
[VARI]: ${mytest}
[TEXT]: xyz
[NODE]: </test:simpleTag>
[TEXT]: \r\n    
[NODE]: <c:if lineNumber="5" offset="13" length="7" tagClass="com.skin.ayada.jstl.core.IfTag" tagFactory="_tpl.com.skin.ayada.jstl.core.factory.IfTagFactory" test="${1 == 1}">
[TEXT]: \r\n        
[NODE]: <c:forEach lineNumber="6" offset="15" length="3" tagClass="com.skin.ayada.jstl.core.ForEachTag" tagFactory="_tpl.com.skin.ayada.jstl.core.factory.ForEachTagFactory" items="1,1" var="myvar">
[VARI]: ${myvar}
[NODE]: </c:forEach>
[TEXT]: \r\n    
[NODE]: </c:if>
[TEXT]: \r\n    
[NODE]: <c:forEach lineNumber="8" offset="21" length="7" tagClass="com.skin.ayada.jstl.core.ForEachTag" tagFactory="_tpl.com.skin.ayada.jstl.core.factory.ForEachTagFactory" items="1,1">
[TEXT]: \r\n        
[NODE]: <c:forEach lineNumber="9" offset="23" length="3" tagClass="com.skin.ayada.jstl.core.ForEachTag" tagFactory="_tpl.com.skin.ayada.jstl.core.factory.ForEachTagFactory" items="a,a" var="myvar">
[VARI]: ${myvar}
[NODE]: </c:forEach>
[TEXT]: \r\n    
[NODE]: </c:forEach>
[TEXT]: \r\nss\r\n
[NODE]: </test:simpleTag>
[TEXT]: \r\nabc\r\n    <c:if test=\"${1 == 1}\">\r\n        <c:forEach items=\"1,1\" var=\"myvar\">${myvar}</c:forEach>\r\n    </c:if>\r\n1
