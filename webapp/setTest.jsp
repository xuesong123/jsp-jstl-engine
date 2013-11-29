<c:bean name="testTag1" className="com.skin.ayada.jstl.core.TestTag">
    <c:constructor type="char" value="c"/>
    <c:constructor type="byte" value="2"/>
    <c:property name="pageContext" value="${pageContext}"/>
</c:bean>

<c:set var="a1" value="1"/><p>a1: ${a1}</p>
<c:set var="a1">0 - Hello World!</c:set><p>a1: ${a1}</p>

<c:set target="${testTag1}" property="myString" value="00 - Hello World !"/>
<c:execute value="${testTag1.print()}"/>
<c:set target="${testTag1}" property="myString">00 - Hello World !</c:set>
<c:execute value="${testTag1.print()}"/>