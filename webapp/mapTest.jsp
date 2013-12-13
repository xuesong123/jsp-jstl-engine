<c:map name="myMap1" test1="test1" test2="test2"/>
<p>myMap1: ${myMap1.test1}</p>

<c:map name="myMap2">
    <c:map name="myMap3" test1="test1" test2="test2"/>
    <c:map name="myMap4" test1="test1" test2="test2"/>
</c:map>
<p>myMap2.myMap3.test1: ${myMap2.myMap3.test1}</p>
<p>myMap2.myMap4.test1: ${myMap2.myMap4.test2}</p>

<c:list name="myList1">
    <c:map test1="test1" test2="test2"/>
    <c:map test1="test1" test2="test2"/>
</c:list>

<p>myList1: ${myList1}</p>
<c:forEach items="${myList1}" var="myObj" varStatus="status">
<p>myObj: ${myObj.test1}</p>
</c:forEach>
