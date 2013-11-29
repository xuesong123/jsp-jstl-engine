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
[TEXT]: \r\n
[NODE]: <c:execute lineNumber="11" offset="23" length="2" tagClass="com.skin.ayada.jstl.core.ExecuteTag" tagFactory="com.skin.ayada.jstl.factory.ExecuteTagFactory" value="${testTag1.print()}">
[NODE]: </c:execute>
[TEXT]: \r\n
[NODE]: <c:set lineNumber="12" offset="26" length="3" tagClass="com.skin.ayada.jstl.core.SetTag" tagFactory="com.skin.ayada.jstl.factory.SetTagFactory" target="${testTag1}" property="myString">
[TEXT]: 00 - Hello World !
[NODE]: </c:set>
[TEXT]: \r\n
[NODE]: <c:execute lineNumber="13" offset="30" length="2" tagClass="com.skin.ayada.jstl.core.ExecuteTag" tagFactory="com.skin.ayada.jstl.factory.ExecuteTagFactory" value="${testTag1.print()}">
[NODE]: </c:execute>
[TEXT]: \r\n\r\n
[NODE]: <c:output lineNumber="15" offset="33" length="13" tagClass="com.skin.ayada.jstl.core.OutputTag" tagFactory="com.skin.ayada.jstl.factory.OutputTagFactory" out="true" trim="false" file="webapp\extTest.log">
[TEXT]: \r\n    <p>1 - Hello World!</p>\r\n    
[NODE]: <c:out lineNumber="17" offset="35" length="6" tagClass="com.skin.ayada.jstl.core.OutTag" tagFactory="com.skin.ayada.jstl.factory.OutTagFactory">
[TEXT]: \r\n    <p>2 - Hello World!</p>\r\n    
[NODE]: <c:execute lineNumber="19" offset="37" length="2" tagClass="com.skin.ayada.jstl.core.ExecuteTag" tagFactory="com.skin.ayada.jstl.factory.ExecuteTagFactory" value="${pageContext.clear()}">
[NODE]: </c:execute>
[TEXT]: \r\n    <p>3 - Hello World!</p>\r\n    
[NODE]: </c:out>
[TEXT]: \r\n    
[NODE]: <c:set lineNumber="22" offset="42" length="2" tagClass="com.skin.ayada.jstl.core.SetTag" tagFactory="com.skin.ayada.jstl.factory.SetTagFactory" var="a1" value="a1">
[NODE]: </c:set>
[TEXT]: \r\n
[NODE]: </c:output>
[TEXT]: \r\n\r\n
[NODE]: <c:xml lineNumber="25" offset="47" length="96" tagClass="com.skin.ayada.jstl.core.OutputTag" tagFactory="com.skin.ayada.jstl.factory.OutputTagFactory" out="true" trim="false" file="webapp\extTest.log">
[NODE]: <c:out lineNumber="27" offset="48" length="3" tagClass="com.skin.ayada.jstl.core.OutTag" tagFactory="com.skin.ayada.jstl.factory.OutTagFactory">
[TEXT]: <p>3 - Hello World!</p>
[NODE]: </c:out>
[NODE]: <c:set lineNumber="29" offset="51" length="2" tagClass="com.skin.ayada.jstl.core.SetTag" tagFactory="com.skin.ayada.jstl.factory.SetTagFactory" var="a1" value="a1">
[NODE]: </c:set>
[NODE]: <c:set lineNumber="30" offset="53" length="2" tagClass="com.skin.ayada.jstl.core.SetTag" tagFactory="com.skin.ayada.jstl.factory.SetTagFactory" var="a2" value="${a1}">
[NODE]: </c:set>
[NODE]: <c:set lineNumber="31" offset="55" length="2" tagClass="com.skin.ayada.jstl.core.SetTag" tagFactory="com.skin.ayada.jstl.factory.SetTagFactory" var="a3" value="${a2}">
[NODE]: </c:set>
[NODE]: <c:set lineNumber="32" offset="57" length="2" tagClass="com.skin.ayada.jstl.core.SetTag" tagFactory="com.skin.ayada.jstl.factory.SetTagFactory" var="a4" value="${a3}">
[NODE]: </c:set>
[NODE]: <c:bean lineNumber="34" offset="59" length="8" tagClass="com.skin.ayada.jstl.core.BeanTag" tagFactory="com.skin.ayada.jstl.factory.BeanTagFactory" name="testTag1" className="com.skin.ayada.jstl.core.TestTag">
[NODE]: <c:constructor lineNumber="35" offset="60" length="2" tagClass="com.skin.ayada.jstl.core.ConstructorTag" tagFactory="com.skin.ayada.jstl.factory.ConstructorTagFactory" type="char" value="c">
[NODE]: </c:constructor>
[NODE]: <c:constructor lineNumber="36" offset="62" length="2" tagClass="com.skin.ayada.jstl.core.ConstructorTag" tagFactory="com.skin.ayada.jstl.factory.ConstructorTagFactory" type="byte" value="2">
[NODE]: </c:constructor>
[NODE]: <c:property lineNumber="37" offset="64" length="2" tagClass="com.skin.ayada.jstl.core.PropertyTag" tagFactory="com.skin.ayada.jstl.factory.PropertyTagFactory" name="pageContext" value="${pageContext}">
[NODE]: </c:property>
[NODE]: </c:bean>
[NODE]: <c:bean lineNumber="40" offset="67" length="8" tagClass="com.skin.ayada.jstl.core.BeanTag" tagFactory="com.skin.ayada.jstl.factory.BeanTagFactory" name="testTag2" className="com.skin.ayada.jstl.core.TestTag">
[NODE]: <c:constructor lineNumber="41" offset="68" length="2" tagClass="com.skin.ayada.jstl.core.ConstructorTag" tagFactory="com.skin.ayada.jstl.factory.ConstructorTagFactory" type="long" value="1.2e3">
[NODE]: </c:constructor>
[NODE]: <c:constructor lineNumber="42" offset="70" length="2" tagClass="com.skin.ayada.jstl.core.ConstructorTag" tagFactory="com.skin.ayada.jstl.factory.ConstructorTagFactory" type="java.lang.String" value="Hello">
[NODE]: </c:constructor>
[NODE]: <c:property lineNumber="43" offset="72" length="2" tagClass="com.skin.ayada.jstl.core.PropertyTag" tagFactory="com.skin.ayada.jstl.factory.PropertyTagFactory" name="pageContext" value="${pageContext}">
[NODE]: </c:property>
[NODE]: </c:bean>
[NODE]: <c:bean lineNumber="46" offset="75" length="20" tagClass="com.skin.ayada.jstl.core.BeanTag" tagFactory="com.skin.ayada.jstl.factory.BeanTagFactory" name="testTag3" className="com.skin.ayada.jstl.core.TestTag">
[NODE]: <c:property lineNumber="47" offset="76" length="2" tagClass="com.skin.ayada.jstl.core.PropertyTag" tagFactory="com.skin.ayada.jstl.factory.PropertyTagFactory" name="myBoolean" value="true">
[NODE]: </c:property>
[NODE]: <c:property lineNumber="48" offset="78" length="2" tagClass="com.skin.ayada.jstl.core.PropertyTag" tagFactory="com.skin.ayada.jstl.factory.PropertyTagFactory" name="myChar" value="cc">
[NODE]: </c:property>
[NODE]: <c:property lineNumber="49" offset="80" length="2" tagClass="com.skin.ayada.jstl.core.PropertyTag" tagFactory="com.skin.ayada.jstl.factory.PropertyTagFactory" name="myByte" value="1">
[NODE]: </c:property>
[NODE]: <c:property lineNumber="50" offset="82" length="2" tagClass="com.skin.ayada.jstl.core.PropertyTag" tagFactory="com.skin.ayada.jstl.factory.PropertyTagFactory" name="myInt" value="-1.0">
[NODE]: </c:property>
[NODE]: <c:property lineNumber="51" offset="84" length="2" tagClass="com.skin.ayada.jstl.core.PropertyTag" tagFactory="com.skin.ayada.jstl.factory.PropertyTagFactory" name="myFloat" value="1.2f">
[NODE]: </c:property>
[NODE]: <c:property lineNumber="52" offset="86" length="2" tagClass="com.skin.ayada.jstl.core.PropertyTag" tagFactory="com.skin.ayada.jstl.factory.PropertyTagFactory" name="myDouble" value="2.3d">
[NODE]: </c:property>
[NODE]: <c:property lineNumber="53" offset="88" length="2" tagClass="com.skin.ayada.jstl.core.PropertyTag" tagFactory="com.skin.ayada.jstl.factory.PropertyTagFactory" name="myLong" value="5L">
[NODE]: </c:property>
[NODE]: <c:property lineNumber="54" offset="90" length="2" tagClass="com.skin.ayada.jstl.core.PropertyTag" tagFactory="com.skin.ayada.jstl.factory.PropertyTagFactory" name="myString" value="Hello">
[NODE]: </c:property>
[NODE]: <c:property lineNumber="55" offset="92" length="2" tagClass="com.skin.ayada.jstl.core.PropertyTag" tagFactory="com.skin.ayada.jstl.factory.PropertyTagFactory" name="pageContext" value="${pageContext}">
[NODE]: </c:property>
[NODE]: </c:bean>
[NODE]: <c:execute lineNumber="58" offset="95" length="2" tagClass="com.skin.ayada.jstl.core.ExecuteTag" tagFactory="com.skin.ayada.jstl.factory.ExecuteTagFactory" value="${testTag1.print()}">
[NODE]: </c:execute>
[NODE]: <c:execute lineNumber="59" offset="97" length="2" tagClass="com.skin.ayada.jstl.core.ExecuteTag" tagFactory="com.skin.ayada.jstl.factory.ExecuteTagFactory" value="${testTag2.print()}">
[NODE]: </c:execute>
[NODE]: <c:execute lineNumber="60" offset="99" length="2" tagClass="com.skin.ayada.jstl.core.ExecuteTag" tagFactory="com.skin.ayada.jstl.factory.ExecuteTagFactory" value="${testTag3.print()}">
[NODE]: </c:execute>
[NODE]: <c:list lineNumber="62" offset="101" length="10" tagClass="com.skin.ayada.jstl.core.ListTag" tagFactory="com.skin.ayada.jstl.factory.ListTagFactory" name="list">
[NODE]: <c:element lineNumber="63" offset="102" length="2" tagClass="com.skin.ayada.jstl.core.AttributeTag" tagFactory="com.skin.ayada.jstl.factory.AttributeTagFactory" value="1">
[NODE]: </c:element>
[NODE]: <c:element lineNumber="64" offset="104" length="2" tagClass="com.skin.ayada.jstl.core.AttributeTag" tagFactory="com.skin.ayada.jstl.factory.AttributeTagFactory" value="2">
[NODE]: </c:element>
[NODE]: <c:element lineNumber="65" offset="106" length="2" tagClass="com.skin.ayada.jstl.core.AttributeTag" tagFactory="com.skin.ayada.jstl.factory.AttributeTagFactory" value="3">
[NODE]: </c:element>
[NODE]: <c:element lineNumber="66" offset="108" length="2" tagClass="com.skin.ayada.jstl.core.AttributeTag" tagFactory="com.skin.ayada.jstl.factory.AttributeTagFactory" value="4">
[NODE]: </c:element>
[NODE]: </c:list>
[NODE]: <c:print lineNumber="69" offset="111" length="2" tagClass="com.skin.ayada.jstl.core.PrintTag" tagFactory="com.skin.ayada.jstl.factory.PrintTagFactory" value="${list}">
[NODE]: </c:print>
[NODE]: <c:map lineNumber="71" offset="113" length="8" tagClass="com.skin.ayada.jstl.core.MapTag" tagFactory="com.skin.ayada.jstl.factory.MapTagFactory" name="map">
[NODE]: <c:entry lineNumber="72" offset="114" length="2" tagClass="com.skin.ayada.jstl.core.AttributeTag" tagFactory="com.skin.ayada.jstl.factory.AttributeTagFactory" name="1" value="test1">
[NODE]: </c:entry>
[NODE]: <c:entry lineNumber="73" offset="116" length="2" tagClass="com.skin.ayada.jstl.core.AttributeTag" tagFactory="com.skin.ayada.jstl.factory.AttributeTagFactory" name="2" value="test2">
[NODE]: </c:entry>
[NODE]: <c:entry lineNumber="74" offset="118" length="2" tagClass="com.skin.ayada.jstl.core.AttributeTag" tagFactory="com.skin.ayada.jstl.factory.AttributeTagFactory" name="3" value="test3">
[NODE]: </c:entry>
[NODE]: </c:map>
[NODE]: <c:print lineNumber="77" offset="121" length="2" tagClass="com.skin.ayada.jstl.core.PrintTag" tagFactory="com.skin.ayada.jstl.factory.PrintTagFactory" value="${map}">
[NODE]: </c:print>
[NODE]: <c:test lineNumber="78" offset="123" length="2" tagClass="com.skin.ayada.jstl.core.TestTag" tagFactory="com.skin.ayada.jstl.factory.TestTagFactory">
[NODE]: </c:test>
[NODE]: <c:list lineNumber="80" offset="125" length="2" tagClass="com.skin.ayada.jstl.core.ListTag" tagFactory="com.skin.ayada.jstl.factory.ListTagFactory" name="userList">
[NODE]: </c:list>
[NODE]: <c:forEach lineNumber="82" offset="127" length="13" tagClass="com.skin.ayada.jstl.core.ForEachTag" tagFactory="com.skin.ayada.jstl.factory.ForEachTagFactory" items="1, 2, 3, 4, 5" var="num">
[TEXT]: \r\n        
[NODE]: <c:map lineNumber="83" offset="129" length="6" tagClass="com.skin.ayada.jstl.core.MapTag" tagFactory="com.skin.ayada.jstl.factory.MapTagFactory" name="user">
[NODE]: <c:entry lineNumber="84" offset="130" length="2" tagClass="com.skin.ayada.jstl.core.AttributeTag" tagFactory="com.skin.ayada.jstl.factory.AttributeTagFactory" name="userName" value="test_${num}">
[NODE]: </c:entry>
[NODE]: <c:entry lineNumber="85" offset="132" length="2" tagClass="com.skin.ayada.jstl.core.AttributeTag" tagFactory="com.skin.ayada.jstl.factory.AttributeTagFactory" name="nickName" value="test_${num}">
[NODE]: </c:entry>
[NODE]: </c:map>
[TEXT]: \r\n        
[NODE]: <c:execute lineNumber="87" offset="136" length="2" tagClass="com.skin.ayada.jstl.core.ExecuteTag" tagFactory="com.skin.ayada.jstl.factory.ExecuteTagFactory" value="${userList.add(user)}">
[NODE]: </c:execute>
[TEXT]: \r\n    
[NODE]: </c:forEach>
[NODE]: <c:print lineNumber="90" offset="140" length="2" tagClass="com.skin.ayada.jstl.core.PrintTag" tagFactory="com.skin.ayada.jstl.factory.PrintTagFactory" value="${userList}">
[NODE]: </c:print>
[NODE]: </c:xml>
[TEXT]: \r\n
[NODE]: <c:exit lineNumber="92" offset="144" length="2" tagClass="com.skin.ayada.jstl.core.ExitTag" tagFactory="com.skin.ayada.jstl.factory.ExitTagFactory">
[NODE]: </c:exit>
