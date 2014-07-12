[TEXT]: Hello, 
[EXPR]: ${user.userName}
[TEXT]:  !\r\n
[NODE]: <c:if lineNumber="2" offset="3" length="3" tagClass="com.skin.ayada.jstl.core.IfTag" tagFactory="_tpl.com.skin.ayada.jstl.core.factory.IfTagFactory" test="${user.userName == 'xuesong.net'}">
[TEXT]: Are you xuesong.net?
[NODE]: </c:if>
[TEXT]: \r\n<p>userName: <input type=\"text\" value=\"\"/></p>
