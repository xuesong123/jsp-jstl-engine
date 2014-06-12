[NODE]: <c:comment lineNumber="1" offset="0" length="2" tagClass="com.skin.ayada.jstl.core.CommentTag" tagFactory="com.skin.ayada.jstl.factory.CommentTagFactory">
[NODE]: </c:comment>
[NODE]: <c:forEach lineNumber="18" offset="2" length="15" tagClass="com.skin.ayada.jstl.core.ForEachTag" tagFactory="com.skin.ayada.jstl.factory.ForEachTagFactory" begin="1" end="5" step="2" var="user" varStatus="status">
[TEXT]: \r\n    <div>index: 
[EXPR]: ${status.index}
[TEXT]: , begin: 
[EXPR]: ${status.begin}
[TEXT]: , end: 
[EXPR]: ${status.end}
[TEXT]: , step: 
[EXPR]: ${status.step}
[TEXT]: , count: 
[EXPR]: ${status.count}
[TEXT]: , user.userName: 
[VARI]: ${user}
[TEXT]: </div>
[NODE]: </c:forEach>
