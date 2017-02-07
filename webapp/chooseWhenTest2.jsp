<c:set var="mystring" value="a"/>
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