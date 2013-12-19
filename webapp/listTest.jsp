<c:list name="myList">
    <c:map test1="test1" test2="test2"/>
    <c:map test1="test1" test2="test2"/>
</c:list>

<c:forEach items="${myList}" var="myObj" varStatus="status">
<p>myObj=${status.index}: ${myObj.test1}</p>
</c:forEach>

<c:forEach items="${myList}" var="myObj" varStatus="status">
<p>myObj=${status.index}: ${myObj.test1}</p>
</c:forEach>
