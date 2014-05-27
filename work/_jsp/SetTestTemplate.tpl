[NODE]: <c:bean lineNumber="1" offset="0" length="8" tagClass="com.skin.ayada.jstl.core.BeanTag" tagFactory="com.skin.ayada.jstl.factory.BeanTagFactory" name="testTag1" className="com.skin.ayada.jstl.core.TestTag">
[NODE]: <c:constructor lineNumber="2" offset="1" length="2" tagClass="com.skin.ayada.jstl.core.ConstructorTag" tagFactory="com.skin.ayada.jstl.factory.ConstructorTagFactory" type="char" value="c">
[NODE]: </c:constructor>
[NODE]: <c:constructor lineNumber="3" offset="3" length="2" tagClass="com.skin.ayada.jstl.core.ConstructorTag" tagFactory="com.skin.ayada.jstl.factory.ConstructorTagFactory" type="byte" value="2">
[NODE]: </c:constructor>
[NODE]: <c:property lineNumber="4" offset="5" length="2" tagClass="com.skin.ayada.jstl.core.PropertyTag" tagFactory="com.skin.ayada.jstl.factory.PropertyTagFactory" name="pageContext" value="${pageContext}">
[NODE]: </c:property>
[NODE]: </c:bean>
[TEXT]: \r\n\r\n
[NODE]: <c:set lineNumber="7" offset="9" length="2" tagClass="com.skin.ayada.jstl.core.SetTag" tagFactory="com.skin.ayada.jstl.factory.SetTagFactory" var="a1" value="1">
[NODE]: </c:set>
[TEXT]: <p>a1: 
[VARI]: ${a1}
[TEXT]: </p>\r\n
[NODE]: <c:set lineNumber="8" offset="14" length="3" tagClass="com.skin.ayada.jstl.core.SetTag" tagFactory="com.skin.ayada.jstl.factory.SetTagFactory" var="a1">
[TEXT]: 0 - Hello World!
[NODE]: </c:set>
[TEXT]: <p>a1: 
[VARI]: ${a1}
[TEXT]: </p>\r\n\r\n
[NODE]: <c:set lineNumber="10" offset="20" length="2" tagClass="com.skin.ayada.jstl.core.SetTag" tagFactory="com.skin.ayada.jstl.factory.SetTagFactory" target="${testTag1}" property="myString" value="00 - Hello World !">
[NODE]: </c:set>
[NODE]: <c:execute lineNumber="11" offset="22" length="2" tagClass="com.skin.ayada.jstl.core.ExecuteTag" tagFactory="com.skin.ayada.jstl.factory.ExecuteTagFactory" value="${testTag1.print()}">
[NODE]: </c:execute>
[NODE]: <c:set lineNumber="12" offset="24" length="3" tagClass="com.skin.ayada.jstl.core.SetTag" tagFactory="com.skin.ayada.jstl.factory.SetTagFactory" target="${testTag1}" property="myString">
[TEXT]: 00 - Hello World !
[NODE]: </c:set>
[TEXT]: \r\n
[NODE]: <c:execute lineNumber="13" offset="28" length="2" tagClass="com.skin.ayada.jstl.core.ExecuteTag" tagFactory="com.skin.ayada.jstl.factory.ExecuteTagFactory" value="${testTag1.print()}">
[NODE]: </c:execute>
