<t:import name="test:simpleTag" className="test.com.skin.ayada.taglib.MySimpleTag"/>
<c:forEach items="1,1" var="myvar">${myvar}</c:forEach>
<test:simpleTag>123${mytest}
    <test:simpleTag>${mytest}xyz</test:simpleTag>
    <c:if test="${1 == 1}">
        <c:forEach items="1,1" var="myvar">${myvar}</c:forEach>
    </c:if>
    <c:forEach items="1,1">
        <c:forEach items="a,a" var="myvar">${myvar}</c:forEach>
    </c:forEach>
ss
</test:simpleTag>
abc
<t:text>
    <c:if test="${1 == 1}">
        <c:forEach items="1,1" var="myvar">${myvar}</c:forEach>
    </c:if>
</t:text>
1