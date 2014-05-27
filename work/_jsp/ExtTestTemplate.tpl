[NODE]: <c:output lineNumber="1" offset="0" length="12" tagClass="com.skin.ayada.jstl.core.OutputTag" tagFactory="com.skin.ayada.jstl.factory.OutputTagFactory" out="true" trim="false" file="webapp\extTest.log">
[TEXT]: \r\n    <p>1 - Hello World!</p>\r\n    
[NODE]: <c:out lineNumber="3" offset="2" length="6" tagClass="com.skin.ayada.jstl.core.OutTag" tagFactory="com.skin.ayada.jstl.factory.OutTagFactory">
[TEXT]: \r\n        <p>2 - Hello World!</p>\r\n        
[NODE]: <c:execute lineNumber="5" offset="4" length="2" tagClass="com.skin.ayada.jstl.core.ExecuteTag" tagFactory="com.skin.ayada.jstl.factory.ExecuteTagFactory" value="${pageContext.clear()}">
[NODE]: </c:execute>
[TEXT]:         <p>3 - Hello World!</p>\r\n    
[NODE]: </c:out>
[TEXT]: \r\n    
[NODE]: <c:set lineNumber="8" offset="9" length="2" tagClass="com.skin.ayada.jstl.core.SetTag" tagFactory="com.skin.ayada.jstl.factory.SetTagFactory" var="a1" value="a1">
[NODE]: </c:set>
[NODE]: </c:output>
[TEXT]: \r\n\r\n
[NODE]: <c:xml lineNumber="11" offset="13" length="111" tagClass="com.skin.ayada.jstl.core.OutputTag" tagFactory="com.skin.ayada.jstl.factory.OutputTagFactory" out="true" trim="false" file="webapp\extTest.log">
[NODE]: <c:out lineNumber="13" offset="14" length="3" tagClass="com.skin.ayada.jstl.core.OutTag" tagFactory="com.skin.ayada.jstl.factory.OutTagFactory">
[TEXT]: <p>3 - Hello World!</p>
[NODE]: </c:out>
[NODE]: <c:set lineNumber="15" offset="17" length="2" tagClass="com.skin.ayada.jstl.core.SetTag" tagFactory="com.skin.ayada.jstl.factory.SetTagFactory" var="a1" value="a1">
[NODE]: </c:set>
[NODE]: <c:set lineNumber="16" offset="19" length="2" tagClass="com.skin.ayada.jstl.core.SetTag" tagFactory="com.skin.ayada.jstl.factory.SetTagFactory" var="a2" value="${a1}">
[NODE]: </c:set>
[NODE]: <c:set lineNumber="17" offset="21" length="2" tagClass="com.skin.ayada.jstl.core.SetTag" tagFactory="com.skin.ayada.jstl.factory.SetTagFactory" var="a3" value="${a2}">
[NODE]: </c:set>
[NODE]: <c:set lineNumber="18" offset="23" length="2" tagClass="com.skin.ayada.jstl.core.SetTag" tagFactory="com.skin.ayada.jstl.factory.SetTagFactory" var="a4" value="${a3}">
[NODE]: </c:set>
[NODE]: <c:bean lineNumber="20" offset="25" length="8" tagClass="com.skin.ayada.jstl.core.BeanTag" tagFactory="com.skin.ayada.jstl.factory.BeanTagFactory" name="testTag1" className="com.skin.ayada.jstl.core.TestTag">
[NODE]: <c:constructor lineNumber="21" offset="26" length="2" tagClass="com.skin.ayada.jstl.core.ConstructorTag" tagFactory="com.skin.ayada.jstl.factory.ConstructorTagFactory" type="char" value="c">
[NODE]: </c:constructor>
[NODE]: <c:constructor lineNumber="22" offset="28" length="2" tagClass="com.skin.ayada.jstl.core.ConstructorTag" tagFactory="com.skin.ayada.jstl.factory.ConstructorTagFactory" type="byte" value="2">
[NODE]: </c:constructor>
[NODE]: <c:property lineNumber="23" offset="30" length="2" tagClass="com.skin.ayada.jstl.core.PropertyTag" tagFactory="com.skin.ayada.jstl.factory.PropertyTagFactory" name="pageContext" value="${pageContext}">
[NODE]: </c:property>
[NODE]: </c:bean>
[NODE]: <c:bean lineNumber="26" offset="33" length="8" tagClass="com.skin.ayada.jstl.core.BeanTag" tagFactory="com.skin.ayada.jstl.factory.BeanTagFactory" name="testTag2" className="com.skin.ayada.jstl.core.TestTag">
[NODE]: <c:constructor lineNumber="27" offset="34" length="2" tagClass="com.skin.ayada.jstl.core.ConstructorTag" tagFactory="com.skin.ayada.jstl.factory.ConstructorTagFactory" type="long" value="1.2e3">
[NODE]: </c:constructor>
[NODE]: <c:constructor lineNumber="28" offset="36" length="2" tagClass="com.skin.ayada.jstl.core.ConstructorTag" tagFactory="com.skin.ayada.jstl.factory.ConstructorTagFactory" type="java.lang.String" value="Hello">
[NODE]: </c:constructor>
[NODE]: <c:property lineNumber="29" offset="38" length="2" tagClass="com.skin.ayada.jstl.core.PropertyTag" tagFactory="com.skin.ayada.jstl.factory.PropertyTagFactory" name="pageContext" value="${pageContext}">
[NODE]: </c:property>
[NODE]: </c:bean>
[NODE]: <c:bean lineNumber="32" offset="41" length="20" tagClass="com.skin.ayada.jstl.core.BeanTag" tagFactory="com.skin.ayada.jstl.factory.BeanTagFactory" name="testTag3" className="com.skin.ayada.jstl.core.TestTag">
[NODE]: <c:property lineNumber="33" offset="42" length="2" tagClass="com.skin.ayada.jstl.core.PropertyTag" tagFactory="com.skin.ayada.jstl.factory.PropertyTagFactory" name="myBoolean" value="true">
[NODE]: </c:property>
[NODE]: <c:property lineNumber="34" offset="44" length="2" tagClass="com.skin.ayada.jstl.core.PropertyTag" tagFactory="com.skin.ayada.jstl.factory.PropertyTagFactory" name="myChar" value="cc">
[NODE]: </c:property>
[NODE]: <c:property lineNumber="35" offset="46" length="2" tagClass="com.skin.ayada.jstl.core.PropertyTag" tagFactory="com.skin.ayada.jstl.factory.PropertyTagFactory" name="myByte" value="1">
[NODE]: </c:property>
[NODE]: <c:property lineNumber="36" offset="48" length="2" tagClass="com.skin.ayada.jstl.core.PropertyTag" tagFactory="com.skin.ayada.jstl.factory.PropertyTagFactory" name="myInt" value="-1.0">
[NODE]: </c:property>
[NODE]: <c:property lineNumber="37" offset="50" length="2" tagClass="com.skin.ayada.jstl.core.PropertyTag" tagFactory="com.skin.ayada.jstl.factory.PropertyTagFactory" name="myFloat" value="1.2f">
[NODE]: </c:property>
[NODE]: <c:property lineNumber="38" offset="52" length="2" tagClass="com.skin.ayada.jstl.core.PropertyTag" tagFactory="com.skin.ayada.jstl.factory.PropertyTagFactory" name="myDouble" value="2.3d">
[NODE]: </c:property>
[NODE]: <c:property lineNumber="39" offset="54" length="2" tagClass="com.skin.ayada.jstl.core.PropertyTag" tagFactory="com.skin.ayada.jstl.factory.PropertyTagFactory" name="myLong" value="5L">
[NODE]: </c:property>
[NODE]: <c:property lineNumber="40" offset="56" length="2" tagClass="com.skin.ayada.jstl.core.PropertyTag" tagFactory="com.skin.ayada.jstl.factory.PropertyTagFactory" name="myString" value="Hello">
[NODE]: </c:property>
[NODE]: <c:property lineNumber="41" offset="58" length="2" tagClass="com.skin.ayada.jstl.core.PropertyTag" tagFactory="com.skin.ayada.jstl.factory.PropertyTagFactory" name="pageContext" value="${pageContext}">
[NODE]: </c:property>
[NODE]: </c:bean>
[NODE]: <c:execute lineNumber="44" offset="61" length="2" tagClass="com.skin.ayada.jstl.core.ExecuteTag" tagFactory="com.skin.ayada.jstl.factory.ExecuteTagFactory" value="${testTag1.print()}">
[NODE]: </c:execute>
[NODE]: <c:execute lineNumber="45" offset="63" length="2" tagClass="com.skin.ayada.jstl.core.ExecuteTag" tagFactory="com.skin.ayada.jstl.factory.ExecuteTagFactory" value="${testTag2.print()}">
[NODE]: </c:execute>
[NODE]: <c:execute lineNumber="46" offset="65" length="2" tagClass="com.skin.ayada.jstl.core.ExecuteTag" tagFactory="com.skin.ayada.jstl.factory.ExecuteTagFactory" value="${testTag3.print()}">
[NODE]: </c:execute>
[NODE]: <c:list lineNumber="48" offset="67" length="10" tagClass="com.skin.ayada.jstl.core.ListTag" tagFactory="com.skin.ayada.jstl.factory.ListTagFactory" name="list">
[NODE]: <c:element lineNumber="49" offset="68" length="2" tagClass="com.skin.ayada.jstl.core.ElementTag" tagFactory="com.skin.ayada.jstl.factory.ElementTagFactory" value="1">
[NODE]: </c:element>
[NODE]: <c:element lineNumber="50" offset="70" length="2" tagClass="com.skin.ayada.jstl.core.ElementTag" tagFactory="com.skin.ayada.jstl.factory.ElementTagFactory" value="2">
[NODE]: </c:element>
[NODE]: <c:element lineNumber="51" offset="72" length="2" tagClass="com.skin.ayada.jstl.core.ElementTag" tagFactory="com.skin.ayada.jstl.factory.ElementTagFactory" value="3">
[NODE]: </c:element>
[NODE]: <c:element lineNumber="52" offset="74" length="2" tagClass="com.skin.ayada.jstl.core.ElementTag" tagFactory="com.skin.ayada.jstl.factory.ElementTagFactory" value="4">
[NODE]: </c:element>
[NODE]: </c:list>
[NODE]: <c:print lineNumber="55" offset="77" length="2" tagClass="com.skin.ayada.jstl.core.PrintTag" tagFactory="com.skin.ayada.jstl.factory.PrintTagFactory" value="${list}">
[NODE]: </c:print>
[NODE]: <c:map lineNumber="57" offset="79" length="8" tagClass="com.skin.ayada.jstl.core.MapTag" tagFactory="com.skin.ayada.jstl.factory.MapTagFactory" name="map">
[NODE]: <c:entry lineNumber="58" offset="80" length="2" tagClass="com.skin.ayada.jstl.core.AttributeTag" tagFactory="com.skin.ayada.jstl.factory.AttributeTagFactory" name="1" value="test1">
[NODE]: </c:entry>
[NODE]: <c:entry lineNumber="59" offset="82" length="2" tagClass="com.skin.ayada.jstl.core.AttributeTag" tagFactory="com.skin.ayada.jstl.factory.AttributeTagFactory" name="2" value="test2">
[NODE]: </c:entry>
[NODE]: <c:entry lineNumber="60" offset="84" length="2" tagClass="com.skin.ayada.jstl.core.AttributeTag" tagFactory="com.skin.ayada.jstl.factory.AttributeTagFactory" name="3" value="test3">
[NODE]: </c:entry>
[NODE]: </c:map>
[NODE]: <c:print lineNumber="63" offset="87" length="2" tagClass="com.skin.ayada.jstl.core.PrintTag" tagFactory="com.skin.ayada.jstl.factory.PrintTagFactory" value="<p>">
[NODE]: </c:print>
[NODE]: <c:print lineNumber="63" offset="89" length="2" tagClass="com.skin.ayada.jstl.core.PrintTag" tagFactory="com.skin.ayada.jstl.factory.PrintTagFactory" value="${map}">
[NODE]: </c:print>
[NODE]: <c:print lineNumber="63" offset="91" length="2" tagClass="com.skin.ayada.jstl.core.PrintTag" tagFactory="com.skin.ayada.jstl.factory.PrintTagFactory" value="</p>">
[NODE]: </c:print>
[NODE]: <c:test lineNumber="64" offset="93" length="2" tagClass="com.skin.ayada.jstl.core.TestTag" tagFactory="com.skin.ayada.jstl.factory.TestTagFactory">
[NODE]: </c:test>
[NODE]: <c:list lineNumber="66" offset="95" length="2" tagClass="com.skin.ayada.jstl.core.ListTag" tagFactory="com.skin.ayada.jstl.factory.ListTagFactory" name="userList">
[NODE]: </c:list>
[NODE]: <c:forEach lineNumber="68" offset="97" length="13" tagClass="com.skin.ayada.jstl.core.ForEachTag" tagFactory="com.skin.ayada.jstl.factory.ForEachTagFactory" items="1, 2, 3, 4, 5" var="num">
[TEXT]: \r\n        
[NODE]: <c:map lineNumber="69" offset="99" length="6" tagClass="com.skin.ayada.jstl.core.MapTag" tagFactory="com.skin.ayada.jstl.factory.MapTagFactory" name="user">
[NODE]: <c:entry lineNumber="70" offset="100" length="2" tagClass="com.skin.ayada.jstl.core.AttributeTag" tagFactory="com.skin.ayada.jstl.factory.AttributeTagFactory" name="userName" value="test_${num}">
[NODE]: </c:entry>
[NODE]: <c:entry lineNumber="71" offset="102" length="2" tagClass="com.skin.ayada.jstl.core.AttributeTag" tagFactory="com.skin.ayada.jstl.factory.AttributeTagFactory" name="nickName" value="test_${num}">
[NODE]: </c:entry>
[NODE]: </c:map>
[TEXT]: \r\n        
[NODE]: <c:execute lineNumber="73" offset="106" length="2" tagClass="com.skin.ayada.jstl.core.ExecuteTag" tagFactory="com.skin.ayada.jstl.factory.ExecuteTagFactory" value="${userList.add(user)}">
[NODE]: </c:execute>
[TEXT]:     
[NODE]: </c:forEach>
[NODE]: <c:print lineNumber="75" offset="110" length="2" tagClass="com.skin.ayada.jstl.core.PrintTag" tagFactory="com.skin.ayada.jstl.factory.PrintTagFactory" value="${userList}">
[NODE]: </c:print>
[NODE]: <c:forEach lineNumber="77" offset="112" length="9" tagClass="com.skin.ayada.jstl.core.ForEachTag" tagFactory="com.skin.ayada.jstl.factory.ForEachTagFactory" items="1, 2, 3, 4, 5" var="num">
[TEXT]: \r\n        
[NODE]: <c:map lineNumber="78" offset="114" length="2" tagClass="com.skin.ayada.jstl.core.MapTag" tagFactory="com.skin.ayada.jstl.factory.MapTagFactory" name="user" userName="${num}_test_${num}">
[NODE]: </c:map>
[TEXT]: \r\n        
[NODE]: <c:execute lineNumber="79" offset="117" length="2" tagClass="com.skin.ayada.jstl.core.ExecuteTag" tagFactory="com.skin.ayada.jstl.factory.ExecuteTagFactory" value="${userList.add(user)}">
[NODE]: </c:execute>
[TEXT]:     
[NODE]: </c:forEach>
[NODE]: <c:print lineNumber="81" offset="121" length="2" tagClass="com.skin.ayada.jstl.core.PrintTag" tagFactory="com.skin.ayada.jstl.factory.PrintTagFactory" value="${userList}">
[NODE]: </c:print>
[NODE]: </c:xml>
[TEXT]: \r\n
[NODE]: <c:exit lineNumber="83" offset="125" length="2" tagClass="com.skin.ayada.jstl.core.ExitTag" tagFactory="com.skin.ayada.jstl.factory.ExitTagFactory">
[NODE]: </c:exit>
