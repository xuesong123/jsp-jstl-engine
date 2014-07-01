<t:import name="test:simpleTag" className="test.com.skin.ayada.taglib.MySimpleTag"/>

<test:simpleTag>123${mytest}
    <test:simpleTag>${mytest}xyz</test:simpleTag>
    abc${mytest}
    <c:forEach items="1,2">
        <c:forEach items="1,2" var="myvar">${myvar}</c:forEach>
    </c:forEach>
ss</test:simpleTag>123
abc