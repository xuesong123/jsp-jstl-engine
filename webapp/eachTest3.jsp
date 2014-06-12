<c:forEach items="a,b,c" var="mystring" varStatus="status2">
<p>status.index: ${status2.index}: mystring: ${mystring}</p>
</c:forEach>