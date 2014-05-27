<c:layout page="/tags/layout.jsp">
    <c:attribute name="title" value="base layout test"/>
    <c:attribute name="header">
        <ul>
            <c:forEach items="1,2,3" var="myInt">
                <li>${myInt}</li>
            </c:forEach>
        </ul>
    </c:attribute>
    <c:attribute name="body">
        <c:set var="myString" value="${StringUtil.replace('abc', 'b', '\t')}"/>
        <p>myString: [<c:out value="${myString}"/>]</p>
    </c:attribute>
    <c:attribute name="footer">
        <div>copy right</div>
    </c:attribute>
</c:layout>

<c:choose>
adsfdaff
    ${1 + 2}<c:when test="${1 == 1}">
        <p>1==1</p>
    </c:when>
    asdfdsafdaf${1 + 3}<c:when test="${1 == 1}">
        <p>2==2</p>
    </c:when>
    <c:otherwise></c:otherwise>
</c:choose>
