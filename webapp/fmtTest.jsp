<fmt:bundle basename="ayada_i18n">
    <fmt:message key="test.common.test1"/>
</fmt:bundle>
<fmt:message key="test.common.test2" bundle="ayada_i18n"/>

<fmt:message key="test.common.test4" bundle="ayada_i18n">
    <fmt:param value="000"/>
    <fmt:param value="111"/>
    <fmt:param value="222"/>
    <fmt:param value="333"/>
</fmt:message>

<fmt:message key="test.common.test4" bundle="ayada_i18n">
    <fmt:param>000</fmt:param>
    <fmt:param>111</fmt:param>
    <fmt:param>222</fmt:param>
    <fmt:param>333</fmt:param>
</fmt:message>

<fmt:message key="test.common.test4" bundle="ayada_i18n">
    <fmt:param value="aaa"/>
    <fmt:param value="bbb"/>
    <fmt:param value="ccc"/>
    <fmt:param value="ddd"/>
</fmt:message>

<c:list name="userList"/>
<c:forEach begin="1" end="5" varStatus="status">
    <c:map name="user">
        <c:entry name="userName" value="ayada${status.index}"/>
        <c:entry name="nickName" value="ayada${status.index}"/>
    </c:map>
    <c:execute value="${userList.add(user)}"/>
    <div>status.index: ${status.index}: user.userName: ${user.userName}</div>
</c:forEach>

<c:forEach begin="1" end="5" step="2" var="user" varStatus="status">
    <div>index: ${status.index}, begin: ${status.begin}, end: ${status.end}, step: ${status.step}, count: ${status.count}, user.userName: ${user}</div></c:forEach>