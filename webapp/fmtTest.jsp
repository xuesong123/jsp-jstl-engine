<c:set var="i18n" value="${I18n.getBundle('ayada_i18n', 'zh_cn')}"/>
message1: ${i18n.format("test.common.test4", "000", "111", "222", "333")}
message2: ${i18n.message("test.common.test4")}

<fmt:setBundle var="myI18n" basename="ayada_i18n"/>
message3: ${myI18n.format("test.common.test4", "000", "111", "222", "333")}

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

<fmt:setLocale value="fr_fr"/>
<fmt:formatNumber value="123456789.012"/>

<c:list name="userList"/>
<c:forEach begin="1" end="5" var="myInt" varStatus="status">
    <c:map name="user">
        <c:entry name="userName" value="ayada${status.index}"/>
        <c:entry name="nickName" value="ayada${status.index}"/>
    </c:map>
    <c:execute value="${userList.add(user)}"/>
    <div>1 index: ${status.index}, myInt: ${myInt}, user.userName: ${user.userName}</div>
</c:forEach>

<c:comment>
    <c:forEach begin="1" end="5" step="2" var="user" varStatus="status">
        <div>2 index: ${status.index}, begin: ${status.begin}, end: ${status.end}, step: ${status.step}, count: ${status.count}, user.userName: ${user}</div></c:forEach>
</c:comment>