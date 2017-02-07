<c:forEach items="a,b,c" var="mystring" varStatus="status2">
<p>status.index: ${status2.index}: mystring: ${mystring}</p>
    <c:choose>
        <c:when test="${mystring == 'a'}">
            a
        </c:when>
        <c:when test="${mystring == 'b'}">
            b
        </c:when>
        <c:when test="${mystring == 'c'}">
            c
        </c:when>
        <c:otherwise>
            x
        </c:otherwise>
    </c:choose>
</c:forEach>