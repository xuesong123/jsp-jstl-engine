<c:action page="/tags/layout.jsp">
    <c:param name="title" value="base layout test"/>
    <c:param name="header">
        <ul>
            <c:forEach items="1,2,3" var="myInt">
                <li>${myInt}</li>
            </c:forEach>
        </ul>
    </c:param>
    <c:param name="body">
        <c:set var="myString" value="${StringUtil.replace('abc', 'b', '\n')}"/>
        <p>myString: [<c:out value="${myString}"/>]</p>
    </c:param>
    <c:param name="body">
        <div>copy right</div>
    </c:param>
</c:action>
