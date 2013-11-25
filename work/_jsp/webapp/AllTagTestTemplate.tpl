[NODE]: <jsp:directive.page lineNumber="1" offset="0" length="2" page="" contentType="text/html;charset=UTF-8">
[NODE]: </jsp:directive.page>
[NODE]: <jsp:directive.taglib lineNumber="2" offset="2" length="2" taglib="" prefix="c" uri="http://java.sun.com/jsp/jstl/core">
[NODE]: </jsp:directive.taglib>
[NODE]: <jsp:directive.taglib lineNumber="3" offset="4" length="2" taglib="" prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt">
[NODE]: </jsp:directive.taglib>
[NODE]: <jsp:directive.page lineNumber="4" offset="6" length="2" page="" import="java.io.File">
[NODE]: </jsp:directive.page>
[NODE]: <t:import lineNumber="5" offset="8" length="2" tagClass="com.skin.ayada.jstl.core.ImportTag" tagFactory="com.skin.ayada.jstl.factory.ImportTagFactory" name="app:scrollpage" className="test.com.skin.ayada.taglib.ScrollPage">
[NODE]: </t:import>
[NODE]: <t:import lineNumber="6" offset="10" length="2" tagClass="com.skin.ayada.jstl.core.ImportTag" tagFactory="com.skin.ayada.jstl.factory.ImportTagFactory" name="app:test" className="test.com.skin.ayada.taglib.TestTag">
[NODE]: </t:import>
[NODE]: <t:import lineNumber="7" offset="12" length="2" tagClass="com.skin.ayada.jstl.core.ImportTag" tagFactory="com.skin.ayada.jstl.factory.ImportTagFactory" name="app:bodytest" className="test.com.skin.ayada.taglib.TestBodyTag">
[NODE]: </t:import>
[TEXT]: <html>\r\n<head>\r\n<title>test</title>\r\n</head>\r\n<body jsp=\"
[NODE]: <jsp:expression lineNumber="12" offset="15" length="2">
[NODE]: </jsp:expression>
[TEXT]: \" version=\"1.0\">\r\n\r\n
[NODE]: <jsp:declaration lineNumber="14" offset="18" length="2">
[NODE]: </jsp:declaration>
[TEXT]: \r\n
[NODE]: <jsp:declaration lineNumber="22" offset="21" length="2">
[NODE]: </jsp:declaration>
[TEXT]: \r\n
[NODE]: <jsp:declaration lineNumber="24" offset="24" length="2">
[NODE]: </jsp:declaration>
[TEXT]: \r\n
[NODE]: <jsp:declaration lineNumber="30" offset="27" length="2">
[NODE]: </jsp:declaration>
[TEXT]: \r\n
[NODE]: <jsp:declaration lineNumber="36" offset="30" length="2">
[NODE]: </jsp:declaration>
[TEXT]: \r\n
[NODE]: <jsp:scriptlet lineNumber="42" offset="33" length="2">
[NODE]: </jsp:scriptlet>
[TEXT]: \r\n
[NODE]: <jsp:scriptlet lineNumber="46" offset="36" length="2">
[NODE]: </jsp:scriptlet>
[TEXT]: \r\n
[NODE]: <jsp:scriptlet lineNumber="54" offset="39" length="2">
[NODE]: </jsp:scriptlet>
[TEXT]: \r\n
[NODE]: <jsp:expression lineNumber="56" offset="42" length="2">
[NODE]: </jsp:expression>
[TEXT]: \r\n
[NODE]: <jsp:scriptlet lineNumber="58" offset="45" length="2">
[NODE]: </jsp:scriptlet>
[TEXT]: \r\n
[NODE]: <jsp:expression lineNumber="62" offset="48" length="2">
[NODE]: </jsp:expression>
[TEXT]: \r\n
[NODE]: <c:set lineNumber="64" offset="51" length="2" tagClass="com.skin.ayada.jstl.core.SetTag" tagFactory="com.skin.ayada.jstl.factory.SetTagFactory" var="myString" value="${StringUtil.replace('abc', 'b', '\n')}">
[NODE]: </c:set>
[TEXT]: <p>myString: [
[NODE]: <c:out lineNumber="65" offset="54" length="2" tagClass="com.skin.ayada.jstl.core.OutTag" tagFactory="com.skin.ayada.jstl.factory.OutTagFactory" value="${myString}">
[NODE]: </c:out>
[TEXT]: ]</p>\r\n\r\n
[NODE]: <c:set lineNumber="67" offset="57" length="2" tagClass="com.skin.ayada.jstl.core.SetTag" tagFactory="com.skin.ayada.jstl.factory.SetTagFactory" var="myString" value=""a + 1"">
[NODE]: </c:set>
[TEXT]: <p>myString: [
[NODE]: <c:out lineNumber="68" offset="60" length="2" tagClass="com.skin.ayada.jstl.core.OutTag" tagFactory="com.skin.ayada.jstl.factory.OutTagFactory" value="${myString}">
[NODE]: </c:out>
[TEXT]: ]</p>\r\n\r\n
[NODE]: <c:set lineNumber="70" offset="63" length="2" tagClass="com.skin.ayada.jstl.core.SetTag" tagFactory="com.skin.ayada.jstl.factory.SetTagFactory" var="myVar" value="Hello, World!">
[NODE]: </c:set>
[TEXT]: <h1>
[VARI]: ${myVar}
[TEXT]: </h1>\r\n\r\n
[NODE]: <c:out lineNumber="73" offset="68" length="2" tagClass="com.skin.ayada.jstl.core.OutTag" tagFactory="com.skin.ayada.jstl.factory.OutTagFactory" value="c:out: Hello, World!">
[NODE]: </c:out>
[TEXT]: \r\n<div style=\"background-color: #c0c0c0;\"></div>\r\n
[NODE]: <c:out lineNumber="75" offset="71" length="2" tagClass="com.skin.ayada.jstl.core.OutTag" tagFactory="com.skin.ayada.jstl.factory.OutTagFactory" value=""<div>Hello World!</div>"" escapeXml="false">
[NODE]: </c:out>
[TEXT]: \r\n
[NODE]: <c:out lineNumber="76" offset="74" length="3" tagClass="com.skin.ayada.jstl.core.OutTag" tagFactory="com.skin.ayada.jstl.factory.OutTagFactory" escapeXml="true">
[TEXT]: <h1>Hello World!</h1>
[NODE]: </c:out>
[TEXT]: \r\n
[NODE]: <c:out lineNumber="77" offset="78" length="3" tagClass="com.skin.ayada.jstl.core.OutTag" tagFactory="com.skin.ayada.jstl.factory.OutTagFactory" value="<div>Hello World!</div>" escapeXml="true">
[TEXT]: <h1>Hello World!</h1>
[NODE]: </c:out>
[TEXT]: \r\n\r\n
[NODE]: <c:if lineNumber="79" offset="82" length="3" tagClass="com.skin.ayada.jstl.core.IfTag" tagFactory="com.skin.ayada.jstl.factory.IfTagFactory" test="${1 == 1}">
[TEXT]: c:if test
[NODE]: </c:if>
[TEXT]: \r\n\r\n<h1>c:forEach test1</h1>\r\n
[NODE]: <c:forEach lineNumber="82" offset="86" length="3" tagClass="com.skin.ayada.jstl.core.ForEachTag" tagFactory="com.skin.ayada.jstl.factory.ForEachTagFactory" items="1,2,3,4,5" var="mynum">
[VARI]: ${mynum}
[NODE]: </c:forEach>
[TEXT]: \r\n\r\n<h1>c:forEach test2</h1>\r\n
[NODE]: <c:forEach lineNumber="87" offset="90" length="19" tagClass="com.skin.ayada.jstl.core.ForEachTag" tagFactory="com.skin.ayada.jstl.factory.ForEachTagFactory" items="${userList}" var="user" varStatus="status">
[TEXT]: \r\n    <p>user: 
[EXPR]: ${user.userName}
[TEXT]: </p>\r\n    
[NODE]: <c:choose lineNumber="89" offset="94" length="14" tagClass="com.skin.ayada.jstl.core.ChooseTag" tagFactory="com.skin.ayada.jstl.factory.ChooseTagFactory">
[NODE]: <c:when lineNumber="90" offset="95" length="3" tagClass="com.skin.ayada.jstl.core.WhenTag" tagFactory="com.skin.ayada.jstl.factory.WhenTagFactory" test="${user.userName == 'test1'}">
[TEXT]: <p>test1, good man !</p>
[NODE]: </c:when>
[NODE]: <c:when lineNumber="91" offset="98" length="3" tagClass="com.skin.ayada.jstl.core.WhenTag" tagFactory="com.skin.ayada.jstl.factory.WhenTagFactory" test="${user.userName == 'test2'}">
[TEXT]: <p>test2, good man !</p>
[NODE]: </c:when>
[NODE]: <c:when lineNumber="92" offset="101" length="3" tagClass="com.skin.ayada.jstl.core.WhenTag" tagFactory="com.skin.ayada.jstl.factory.WhenTagFactory" test="${user.userName == 'test3'}">
[TEXT]: <p>test3, good man !</p>
[NODE]: </c:when>
[NODE]: <c:otherwise lineNumber="93" offset="104" length="3" tagClass="com.skin.ayada.jstl.core.OtherwiseTag" tagFactory="com.skin.ayada.jstl.factory.OtherwiseTagFactory">
[TEXT]: <p>unknown user! Do you known \'bad egg\'? You! Are!</p>
[NODE]: </c:otherwise>
[NODE]: </c:choose>
[NODE]: </c:forEach>
[TEXT]: \r\n\r\n<h1>c:choose test1</h1>\r\n
[NODE]: <c:choose lineNumber="98" offset="110" length="14" tagClass="com.skin.ayada.jstl.core.ChooseTag" tagFactory="com.skin.ayada.jstl.factory.ChooseTagFactory">
[NODE]: <c:when lineNumber="99" offset="111" length="3" tagClass="com.skin.ayada.jstl.core.WhenTag" tagFactory="com.skin.ayada.jstl.factory.WhenTagFactory" test="${1 == 1}">
[TEXT]: c:when test=\"{1 == 1}\"
[NODE]: </c:when>
[NODE]: <c:when lineNumber="100" offset="114" length="3" tagClass="com.skin.ayada.jstl.core.WhenTag" tagFactory="com.skin.ayada.jstl.factory.WhenTagFactory" test="${2 == 2}">
[TEXT]: c:when test=\"{2 == 2}\"
[NODE]: </c:when>
[NODE]: <c:when lineNumber="101" offset="117" length="3" tagClass="com.skin.ayada.jstl.core.WhenTag" tagFactory="com.skin.ayada.jstl.factory.WhenTagFactory" test="${3 == 3}">
[TEXT]: c:when test=\"{3 == 3}\"
[NODE]: </c:when>
[NODE]: <c:otherwise lineNumber="102" offset="120" length="3" tagClass="com.skin.ayada.jstl.core.OtherwiseTag" tagFactory="com.skin.ayada.jstl.factory.OtherwiseTagFactory">
[TEXT]: c:otherwise
[NODE]: </c:otherwise>
[NODE]: </c:choose>
[TEXT]: \r\n<h1>app:test test1</h1>\r\n
[NODE]: <app:test lineNumber="106" offset="125" length="2" tagClass="test.com.skin.ayada.taglib.TestTag" tagFactory="test.com.skin.ayada.taglib.proxy.TestTagFactory" myBoolean="true" myChar="c" myByte="1" myInt="-1.0" myFloat="1.0f" myDouble="1.0d" myLong="1L" myString="Hello">
[NODE]: </app:test>
[TEXT]: \r\n
[NODE]: <app:test lineNumber="107" offset="128" length="2" tagClass="test.com.skin.ayada.taglib.TestTag" tagFactory="test.com.skin.ayada.taglib.proxy.TestTagFactory" myBoolean="false" myChar="c" myByte="243" myInt="-1.0" myFloat="1.0F" myDouble="1.0D" myLong="1L" myString="Hello">
[NODE]: </app:test>
[TEXT]: \r\n
[NODE]: <app:test lineNumber="108" offset="131" length="2" tagClass="test.com.skin.ayada.taglib.TestTag" tagFactory="test.com.skin.ayada.taglib.proxy.TestTagFactory" myInt="-1.0" myFloat="1.0" myDouble="1e3" myLong="1e3" myString="Hello">
[NODE]: </app:test>
[TEXT]: \r\n
[NODE]: <app:test lineNumber="109" offset="134" length="2" tagClass="test.com.skin.ayada.taglib.TestTag" tagFactory="test.com.skin.ayada.taglib.proxy.TestTagFactory" myInt="-1.0" myFloat="1.0" myDouble="1.2e3" myLong="1.2e3" myString="Hello">
[NODE]: </app:test>
[TEXT]: \r\n\r\n<h1>app:scrollpage test1</h1>\r\n
[NODE]: <app:scrollpage lineNumber="112" offset="137" length="2" tagClass="test.com.skin.ayada.taglib.ScrollPage" tagFactory="test.com.skin.ayada.taglib.proxy.ScrollPageFactory" count="254" pageNum="2" pageSize="10">
[NODE]: </app:scrollpage>
[TEXT]: \r\n\r\n
[NODE]: <app:bodytest lineNumber="114" offset="140" length="2" tagClass="test.com.skin.ayada.taglib.TestBodyTag" tagFactory="test.com.skin.ayada.taglib.proxy.TestBodyTagFactory">
[NODE]: </app:bodytest>
[TEXT]: \r\n
[NODE]: <app:bodytest lineNumber="115" offset="143" length="3" tagClass="test.com.skin.ayada.taglib.TestBodyTag" tagFactory="test.com.skin.ayada.taglib.proxy.TestBodyTagFactory">
[TEXT]: Hello World !
[NODE]: </app:bodytest>
[TEXT]: \r\n</body>\r\n</html>
