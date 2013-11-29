<c:output out="true" trim="false" file="webapp\extTest.log">
    <p>1 - Hello World!</p>
    <c:out>
    <p>2 - Hello World!</p>
    <c:execute value="${pageContext.clear()}"/>
    <p>3 - Hello World!</p>
    </c:out>
    <c:set var="a1" value="a1"/>
</c:output>
<c:xml out="true" trim="false" file="webapp\extTest.log">
    <p>This is a invalid text node !</p>
    <c:out><p>3 - Hello World!</p></c:out>

    <c:set var="a1" value="a1"/>
    <c:set var="a2" value="${a1}"/>
    <c:set var="a3" value="${a2}"/>
    <c:set var="a4" value="${a3}"/>

    <c:bean name="testTag1" className="com.skin.ayada.jstl.core.TestTag">
        <c:constructor type="char" value="c"/>
        <c:constructor type="byte" value="2"/>
        <c:property name="pageContext" value="${pageContext}"/>
    </c:bean>

    <c:bean name="testTag2" className="com.skin.ayada.jstl.core.TestTag">
        <c:constructor type="long" value="1.2e3"/>
        <c:constructor type="java.lang.String" value="Hello"/>
        <c:property name="pageContext" value="${pageContext}"/>
    </c:bean>

    <c:bean name="testTag3" className="com.skin.ayada.jstl.core.TestTag">
        <c:property name="myBoolean" value="true"/>
        <c:property name="myChar" value="cc"/>
        <c:property name="myByte" value="1"/>
        <c:property name="myInt" value="-1.0"/>
        <c:property name="myFloat" value="1.2f"/>
        <c:property name="myDouble" value="2.3d"/>
        <c:property name="myLong" value="5L"/>
        <c:property name="myString" value="Hello"/>
        <c:property name="pageContext" value="${pageContext}"/>
    </c:bean>

    <c:execute value="${testTag1.print()}"/>
    <c:execute value="${testTag2.print()}"/>
    <c:execute value="${testTag3.print()}"/>

    <c:list name="list">
        <c:element value="1"/>
        <c:element value="2"/>
        <c:element value="3"/>
        <c:element value="4"/>
    </c:list>

    <c:print value="${list}"/>

    <c:map name="map">
        <c:entry name="1" value="test1"/>
        <c:entry name="2" value="test2"/>
        <c:entry name="3" value="test3"/>
    </c:map>

    <c:print value="${map}"/>
    <c:test/>

    <c:list name="userList"/>

    <c:forEach items="1, 2, 3, 4, 5" var="num">
        <c:map name="user">
            <c:entry name="userName" value="test_${num}"/>
            <c:entry name="nickName" value="test_${num}"/>
        </c:map>
        <c:execute value="${userList.add(user)}"/>
    </c:forEach>

    <c:print value="${userList}"/>
</c:xml>
<c:exit/>