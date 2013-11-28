[NODE]: <jsp:directive.page lineNumber="1" offset="0" length="2" contentType="text/html;charset=UTF-8">
[NODE]: </jsp:directive.page>
[NODE]: <jsp:directive.taglib lineNumber="2" offset="2" length="2" taglib="" prefix="c" uri="http://java.sun.com/jsp/jstl/core">
[NODE]: </jsp:directive.taglib>
[NODE]: <jsp:directive.taglib lineNumber="3" offset="4" length="2" taglib="" prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt">
[NODE]: </jsp:directive.taglib>
[NODE]: <jsp:directive.page lineNumber="4" offset="6" length="2" import="java.io.IOException">
[NODE]: </jsp:directive.page>
[NODE]: <t:import lineNumber="5" offset="8" length="2" tagClass="com.skin.ayada.jstl.core.ImportTag" tagFactory="com.skin.ayada.jstl.factory.ImportTagFactory" name="app:scrollpage" className="test.com.skin.ayada.taglib.ScrollPage">
[NODE]: </t:import>
[NODE]: <t:import lineNumber="6" offset="10" length="2" tagClass="com.skin.ayada.jstl.core.ImportTag" tagFactory="com.skin.ayada.jstl.factory.ImportTagFactory" name="app:test" className="com.skin.ayada.jstl.core.TestTag">
[NODE]: </t:import>
[NODE]: <t:import lineNumber="7" offset="12" length="2" tagClass="com.skin.ayada.jstl.core.ImportTag" tagFactory="com.skin.ayada.jstl.factory.ImportTagFactory" name="app:bodytest" className="test.com.skin.ayada.taglib.TestBodyTag">
[NODE]: </t:import>
[NODE]: <jsp:declaration lineNumber="9" offset="14" length="2">
[NODE]: </jsp:declaration>
[NODE]: <jsp:declaration lineNumber="14" offset="16" length="2">
[NODE]: </jsp:declaration>
[NODE]: <jsp:declaration lineNumber="20" offset="18" length="2">
[NODE]: </jsp:declaration>
[NODE]: <jsp:declaration lineNumber="26" offset="20" length="2">
[NODE]: </jsp:declaration>
[TEXT]: <html>\r\n<head>\r\n<title>test</title>\r\n</head>\r\n<body jsp=\"
[NODE]: <jsp:expression lineNumber="35" offset="23" length="2">
[NODE]: </jsp:expression>
[TEXT]: \" version=\"1.0\">\r\n============================================\r\n
[NODE]: <jsp:scriptlet lineNumber="38" offset="26" length="2">
[NODE]: </jsp:scriptlet>
[TEXT]: ============================================\r\n
[NODE]: <jsp:scriptlet lineNumber="42" offset="29" length="2">
[NODE]: </jsp:scriptlet>
[NODE]: <jsp:declaration lineNumber="46" offset="31" length="2">
[NODE]: </jsp:declaration>
[NODE]: <jsp:scriptlet lineNumber="50" offset="33" length="2">
[NODE]: </jsp:scriptlet>
[NODE]: <jsp:scriptlet lineNumber="54" offset="35" length="2">
[NODE]: </jsp:scriptlet>
[NODE]: <jsp:scriptlet lineNumber="60" offset="37" length="2">
[NODE]: </jsp:scriptlet>
[TEXT]: <p>\r\n    myInt: 
[NODE]: <jsp:expression lineNumber="64" offset="40" length="2">
[NODE]: </jsp:expression>
[TEXT]: \r\n</p>\r\n\r\n<p>\r\n    mytest: 
[NODE]: <jsp:expression lineNumber="68" offset="43" length="2">
[NODE]: </jsp:expression>
[TEXT]: \r\n</p>\r\n
[NODE]: <jsp:scriptlet lineNumber="70" offset="46" length="2">
[NODE]: </jsp:scriptlet>
[NODE]: <jsp:expression lineNumber="74" offset="48" length="2">
[NODE]: </jsp:expression>
[TEXT]: \r\n============================================\r\n
[NODE]: <c:set lineNumber="76" offset="51" length="2" tagClass="com.skin.ayada.jstl.core.SetTag" tagFactory="com.skin.ayada.jstl.factory.SetTagFactory" var="myString" value="${StringUtil.replace('abc', 'b', '\n')}">
[NODE]: </c:set>
[TEXT]: \r\n<p>myString: [
[NODE]: <c:out lineNumber="77" offset="54" length="2" tagClass="com.skin.ayada.jstl.core.OutTag" tagFactory="com.skin.ayada.jstl.factory.OutTagFactory" value="${myString}">
[NODE]: </c:out>
[TEXT]: ]</p>\r\n\r\n
[NODE]: <c:set lineNumber="79" offset="57" length="2" tagClass="com.skin.ayada.jstl.core.SetTag" tagFactory="com.skin.ayada.jstl.factory.SetTagFactory" var="myString" value=""a + 1"">
[NODE]: </c:set>
[TEXT]: \r\n<p>myString: [
[NODE]: <c:out lineNumber="80" offset="60" length="2" tagClass="com.skin.ayada.jstl.core.OutTag" tagFactory="com.skin.ayada.jstl.factory.OutTagFactory" value="${myString}">
[NODE]: </c:out>
[TEXT]: ]</p>\r\n\r\n
[NODE]: <c:set lineNumber="82" offset="63" length="2" tagClass="com.skin.ayada.jstl.core.SetTag" tagFactory="com.skin.ayada.jstl.factory.SetTagFactory" var="myVar" value="Hello, World!">
[NODE]: </c:set>
[TEXT]: \r\n<h1>############## 
[VARI]: ${myVar}
[TEXT]:  ##############</h1>\r\n\r\n
[NODE]: <c:out lineNumber="85" offset="68" length="2" tagClass="com.skin.ayada.jstl.core.OutTag" tagFactory="com.skin.ayada.jstl.factory.OutTagFactory" value="c:out: Hello, World!">
[NODE]: </c:out>
[TEXT]: \r\n<div style=\"background-color: #c0c0c0;\"></div>\r\n
[NODE]: <c:out lineNumber="87" offset="71" length="2" tagClass="com.skin.ayada.jstl.core.OutTag" tagFactory="com.skin.ayada.jstl.factory.OutTagFactory" value=""<div>Hello World!</div>"" escapeXml="false">
[NODE]: </c:out>
[TEXT]: \r\n
[NODE]: <c:out lineNumber="88" offset="74" length="3" tagClass="com.skin.ayada.jstl.core.OutTag" tagFactory="com.skin.ayada.jstl.factory.OutTagFactory" escapeXml="true">
[TEXT]: <h1>Hello World!</h1>
[NODE]: </c:out>
[TEXT]: \r\n
[NODE]: <c:out lineNumber="89" offset="78" length="3" tagClass="com.skin.ayada.jstl.core.OutTag" tagFactory="com.skin.ayada.jstl.factory.OutTagFactory" value="<div>Hello World!</div>" escapeXml="true">
[TEXT]: <h1>Hello World!</h1>
[NODE]: </c:out>
[TEXT]: \r\n\r\n
[NODE]: <c:set lineNumber="91" offset="82" length="2" tagClass="com.skin.ayada.jstl.core.SetTag" tagFactory="com.skin.ayada.jstl.factory.SetTagFactory" var="myName" value="xuesong.net">
[NODE]: </c:set>
[TEXT]: \r\n
[NODE]: <c:out lineNumber="92" offset="85" length="2" tagClass="com.skin.ayada.jstl.core.OutTag" tagFactory="com.skin.ayada.jstl.factory.OutTagFactory" value="Hello, ${myName}!" escapeXml="true">
[NODE]: </c:out>
[TEXT]: \r\n
[NODE]: <c:out lineNumber="93" offset="88" length="2" tagClass="com.skin.ayada.jstl.core.OutTag" tagFactory="com.skin.ayada.jstl.factory.OutTagFactory" value="Hello, ${myName}!" escapeXml="false">
[NODE]: </c:out>
[TEXT]: \r\n\r\n
[NODE]: <c:if lineNumber="95" offset="91" length="3" tagClass="com.skin.ayada.jstl.core.IfTag" tagFactory="com.skin.ayada.jstl.factory.IfTagFactory" test="${1 == 1}">
[TEXT]: c:if test
[NODE]: </c:if>
[TEXT]: \r\n\r\n<h1>c:forEach test1</h1>\r\n
[NODE]: <c:each lineNumber="98" offset="95" length="3" tagClass="com.skin.ayada.jstl.core.ForEachTag" tagFactory="com.skin.ayada.jstl.factory.ForEachTagFactory" items="1,2,3,4,5" var="mynum">
[VARI]: ${mynum}
[NODE]: </c:each>
[TEXT]: \r\n\r\n<h1>c:forEach test2</h1>\r\n
[NODE]: <c:forEach lineNumber="103" offset="99" length="20" tagClass="com.skin.ayada.jstl.core.ForEachTag" tagFactory="com.skin.ayada.jstl.factory.ForEachTagFactory" items="${userList}" var="user" varStatus="status">
[TEXT]: \r\n    <p>user: 
[EXPR]: ${user.userName}
[TEXT]: </p>\r\n    
[NODE]: <c:choose lineNumber="105" offset="103" length="14" tagClass="com.skin.ayada.jstl.core.ChooseTag" tagFactory="com.skin.ayada.jstl.factory.ChooseTagFactory">
[NODE]: <c:when lineNumber="106" offset="104" length="3" tagClass="com.skin.ayada.jstl.core.WhenTag" tagFactory="com.skin.ayada.jstl.factory.WhenTagFactory" test="${user.userName == 'test1'}">
[TEXT]: <p>test1, good man !</p>
[NODE]: </c:when>
[NODE]: <c:when lineNumber="107" offset="107" length="3" tagClass="com.skin.ayada.jstl.core.WhenTag" tagFactory="com.skin.ayada.jstl.factory.WhenTagFactory" test="${user.userName == 'test2'}">
[TEXT]: <p>test2, good man !</p>
[NODE]: </c:when>
[NODE]: <c:when lineNumber="108" offset="110" length="3" tagClass="com.skin.ayada.jstl.core.WhenTag" tagFactory="com.skin.ayada.jstl.factory.WhenTagFactory" test="${user.userName == 'test3'}">
[TEXT]: <p>test3, good man !</p>
[NODE]: </c:when>
[NODE]: <c:otherwise lineNumber="109" offset="113" length="3" tagClass="com.skin.ayada.jstl.core.OtherwiseTag" tagFactory="com.skin.ayada.jstl.factory.OtherwiseTagFactory">
[TEXT]: <p>unknown user! Do you known \'bad egg\'? You! Are!</p>
[NODE]: </c:otherwise>
[NODE]: </c:choose>
[TEXT]: \r\n
[NODE]: </c:forEach>
[TEXT]: \r\n\r\n<h1>c:choose test1</h1>\r\n
[NODE]: <c:choose lineNumber="114" offset="120" length="14" tagClass="com.skin.ayada.jstl.core.ChooseTag" tagFactory="com.skin.ayada.jstl.factory.ChooseTagFactory">
[NODE]: <c:when lineNumber="115" offset="121" length="3" tagClass="com.skin.ayada.jstl.core.WhenTag" tagFactory="com.skin.ayada.jstl.factory.WhenTagFactory" test="${1 == 1}">
[TEXT]: c:when test=\"{1 == 1}\"
[NODE]: </c:when>
[NODE]: <c:when lineNumber="116" offset="124" length="3" tagClass="com.skin.ayada.jstl.core.WhenTag" tagFactory="com.skin.ayada.jstl.factory.WhenTagFactory" test="${2 == 2}">
[TEXT]: c:when test=\"{2 == 2}\"
[NODE]: </c:when>
[NODE]: <c:when lineNumber="117" offset="127" length="3" tagClass="com.skin.ayada.jstl.core.WhenTag" tagFactory="com.skin.ayada.jstl.factory.WhenTagFactory" test="${3 == 3}">
[TEXT]: c:when test=\"{3 == 3}\"
[NODE]: </c:when>
[NODE]: <c:otherwise lineNumber="118" offset="130" length="3" tagClass="com.skin.ayada.jstl.core.OtherwiseTag" tagFactory="com.skin.ayada.jstl.factory.OtherwiseTagFactory">
[TEXT]: c:otherwise
[NODE]: </c:otherwise>
[NODE]: </c:choose>
[TEXT]: \r\n\r\n<h1>app:test test1</h1>\r\n
[NODE]: <app:test lineNumber="122" offset="135" length="2" tagClass="com.skin.ayada.jstl.core.TestTag" tagFactory="com.skin.ayada.jstl.factory.TestTagFactory" myBoolean="true" myChar="c" myByte="1" myInt="-1.0" myFloat="1.0f" myDouble="1.0d" myLong="1L" myString="Hello">
[NODE]: </app:test>
[TEXT]: \r\n
[NODE]: <app:test lineNumber="123" offset="138" length="2" tagClass="com.skin.ayada.jstl.core.TestTag" tagFactory="com.skin.ayada.jstl.factory.TestTagFactory" myBoolean="false" myChar="c" myByte="243" myInt="-1.0" myFloat="1.0F" myDouble="1.0D" myLong="1L" myString="Hello">
[NODE]: </app:test>
[TEXT]: \r\n
[NODE]: <app:test lineNumber="124" offset="141" length="2" tagClass="com.skin.ayada.jstl.core.TestTag" tagFactory="com.skin.ayada.jstl.factory.TestTagFactory" myInt="-1.0" myFloat="1.0" myDouble="1e3" myLong="1e3" myString="Hello">
[NODE]: </app:test>
[TEXT]: \r\n
[NODE]: <app:test lineNumber="125" offset="144" length="2" tagClass="com.skin.ayada.jstl.core.TestTag" tagFactory="com.skin.ayada.jstl.factory.TestTagFactory" myInt="-1.0" myFloat="1.0" myDouble="1.2e3" myLong="1.2e3" myString="Hello">
[NODE]: </app:test>
[TEXT]: \r\n\r\n<h1>app:scrollpage test1</h1>\r\n
[NODE]: <app:scrollpage lineNumber="128" offset="147" length="2" tagClass="test.com.skin.ayada.taglib.ScrollPage" tagFactory="test.com.skin.ayada.taglib.proxy.ScrollPageFactory" count="254" pageNum="2" pageSize="10">
[NODE]: </app:scrollpage>
[TEXT]: \r\n\r\n
[NODE]: <app:bodytest lineNumber="130" offset="150" length="2" tagClass="test.com.skin.ayada.taglib.TestBodyTag" tagFactory="test.com.skin.ayada.taglib.proxy.TestBodyTagFactory">
[NODE]: </app:bodytest>
[TEXT]: \r\n
[NODE]: <app:bodytest lineNumber="131" offset="153" length="3" tagClass="test.com.skin.ayada.taglib.TestBodyTag" tagFactory="test.com.skin.ayada.taglib.proxy.TestBodyTagFactory">
[TEXT]: Hello World !
[NODE]: </app:bodytest>
[TEXT]: \r\n
[NODE]: <c:test lineNumber="132" offset="157" length="2" tagClass="com.skin.ayada.jstl.core.TestTag" tagFactory="com.skin.ayada.jstl.factory.TestTagFactory">
[NODE]: </c:test>
[TEXT]: \r\n</body>\r\n</html>
