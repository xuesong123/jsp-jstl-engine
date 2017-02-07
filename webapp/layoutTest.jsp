<c:layout page="/layout/default.jsp">
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
        <div>copyright</div>
    </c:attribute>
</c:layout>
dsafasf133
