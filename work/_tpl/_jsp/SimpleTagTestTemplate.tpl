[NODE]: <test:simpleTag lineNumber="3" offset="0" length="20" tagClass="test.com.skin.ayada.taglib.MySimpleTag" tagFactory="test.com.skin.ayada.taglib.proxy.MySimpleTagFactory">
[TEXT]: 123
[VARI]: ${mytest}
[TEXT]: \r\n    
[NODE]: <test:simpleTag lineNumber="4" offset="4" length="4" tagClass="test.com.skin.ayada.taglib.MySimpleTag" tagFactory="test.com.skin.ayada.taglib.proxy.MySimpleTagFactory">
[VARI]: ${mytest}
[TEXT]: xyz
[NODE]: </test:simpleTag>
[TEXT]: \r\n    abc
[VARI]: ${mytest}
[TEXT]: \r\n    
[NODE]: <c:forEach lineNumber="6" offset="11" length="7" tagClass="com.skin.ayada.jstl.core.ForEachTag" tagFactory="com.skin.ayada.jstl.factory.ForEachTagFactory" items="1,2">
[TEXT]: \r\n        
[NODE]: <c:forEach lineNumber="7" offset="13" length="3" tagClass="com.skin.ayada.jstl.core.ForEachTag" tagFactory="com.skin.ayada.jstl.factory.ForEachTagFactory" items="1,2" var="myvar">
[VARI]: ${myvar}
[NODE]: </c:forEach>
[TEXT]: \r\n    
[NODE]: </c:forEach>
[TEXT]: \r\nss
[NODE]: </test:simpleTag>
[TEXT]: 123\r\nabc
