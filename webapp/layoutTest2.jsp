<c:invoke page="/tags/layout.jsp">
    <c:attribute name="title" value="base layout test"/>
    <c:attribute name="header">
        <ul>
            <c:forEach items="1,2,3" var="myInt">
                <li>${myInt}</li>
            </c:forEach>
        </ul>
    </c:attribute>
    <c:attribute name="body">
        <c:set var="myString" value="${StringUtil.replace('abc', 'b', '\n')}"/>
        <p>myString: [<c:out value="${myString}"/>]</p>
    </c:attribute>
    <c:attribute name="body">
        <div>copy right</div>
    </c:attribute>
</c:invoke>
