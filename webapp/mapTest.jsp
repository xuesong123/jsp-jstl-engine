<c:map name="myMap1" test1="test1" test2="test2"/>
<p>myMap1: ${myMap1.test1}</p>

<c:map name="myMap2">
    <c:map name="myMap3" test1="test1" test2="test2"/>
    <c:map name="myMap4" test1="test1" test2="test2"/>
</c:map>
<p>myMap2.myMap3.test1: ${myMap2.myMap3.test1}</p>
<p>myMap2.myMap4.test1: ${myMap2.myMap4.test2}</p>

<c:list name="myList1">
    <c:map>
        <c:entry name="test1" value="test1"/>
        <c:entry name="test2" value="test2"/>
    </c:map>
    <c:map test1="test1" test2="test2"/>
</c:list>

<c:map name="myMap1">
    <c:map name="element1">
        <c:entry name="test1" value="test1"/>
        <c:entry name="test2" value="test2"/>
    </c:map>
</c:map>

${myMap1}

<c:list name="myList2">
    <c:list>
        <c:element value="123"/>
        <c:element value="234"/>
        <c:element value="456"/>
    </c:list>
    <c:list>
        <c:element value="456"/>
        <c:element value="234"/>
        <c:element value="123"/>
    </c:list>
</c:list>
myList2: ${myList2}

<p>myList1: ${myList1}</p>
<c:forEach items="${myList1}" var="myObj" varStatus="status">
<p>myObj: ${myObj.test1}</p>
</c:forEach>
