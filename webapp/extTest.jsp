<c:xml out="true" trim="false" file="webapp\extTest.log">
    <c:set var="a1" value="a1"/>
    <c:set var="a1" value="a1"/>
    <c:set var="a1" value="a1"/>
    <c:set var="a1" value="a1"/>

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

    <c:execute result="${pageContext.clear()}"/>
    <c:execute result="${testTag1.print()}"/>
    <c:execute result="${testTag2.print()}"/>
    <c:execute result="${testTag3.print()}"/>

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
</c:xml>
<c:if test="${1==1}"><c:exit></c:if>