<c:list name="userList"/>

<c:forEach items="1, 2, 3, 4, 5" step="2" var="num">
    <c:map name="user">
        <c:entry name="userName" value="test_${num}"/>
        <c:entry name="nickName" value="test_${num}"/>
    </c:map>
    <c:execute value="${userList.add(user)}"/>
</c:forEach>
userList2: <c:print value="${userList}"/>