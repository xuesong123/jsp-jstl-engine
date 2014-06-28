[NODE]: <c:list lineNumber="1" offset="0" length="2" tagClass="com.skin.ayada.jstl.core.ListTag" tagFactory="com.skin.ayada.jstl.factory.ListTagFactory" name="userList">
[NODE]: </c:list>
[TEXT]: \r\n\r\n\r\n
[NODE]: <c:forEach lineNumber="4" offset="3" length="12" tagClass="com.skin.ayada.jstl.core.ForEachTag" tagFactory="com.skin.ayada.jstl.factory.ForEachTagFactory" items="1, 2, 3, 4, 5" step="2" var="num">
[TEXT]: \r\n    
[NODE]: <c:map lineNumber="5" offset="5" length="6" tagClass="com.skin.ayada.jstl.core.MapTag" tagFactory="com.skin.ayada.jstl.factory.MapTagFactory" name="user">
[NODE]: <c:entry lineNumber="6" offset="6" length="2" tagClass="com.skin.ayada.jstl.core.AttributeTag" tagFactory="com.skin.ayada.jstl.factory.AttributeTagFactory" name="userName" value="test_${num}">
[NODE]: </c:entry>
[NODE]: <c:entry lineNumber="7" offset="8" length="2" tagClass="com.skin.ayada.jstl.core.AttributeTag" tagFactory="com.skin.ayada.jstl.factory.AttributeTagFactory" name="nickName" value="test_${num}">
[NODE]: </c:entry>
[NODE]: </c:map>
[TEXT]: \r\n    
[NODE]: <c:execute lineNumber="9" offset="12" length="2" tagClass="com.skin.ayada.jstl.core.ExecuteTag" tagFactory="com.skin.ayada.jstl.factory.ExecuteTagFactory" value="${userList.add(user)}">
[NODE]: </c:execute>
[NODE]: </c:forEach>
[TEXT]: \r\nuserList2: 
[NODE]: <c:print lineNumber="11" offset="16" length="2" tagClass="com.skin.ayada.jstl.core.PrintTag" tagFactory="com.skin.ayada.jstl.factory.PrintTagFactory" value="${userList}">
[NODE]: </c:print>
