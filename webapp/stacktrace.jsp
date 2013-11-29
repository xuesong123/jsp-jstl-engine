<t:import name="c:stackTrace" className="com.skin.ayada.jstl.core.StackTraceTag"/>
<html>
<head>
<title>test</title>
<script type="text/javascript" src="${resource}/js/jquery-1.4.2.min.js"></script>
</head>
<body version="1.0">
${user.userName}
<c:if test="${1 == 1}">
    <c:if test="${1 == 1}">
        <c:if test="${1 == 1}">
            <c:if test="${1 == 1}">
                <c:if test="${1 == 1}">
                    <c:if test="${1 == 1}">
                        <c:if test="${1 == 1}">
                            <c:if test="${1 == 1}">
                                <c:if test="${1 == 1}">
                                    <c:if test="${1 == 1}">
------------------------- StackTrace -------------------------
<c:stackTrace/>
                                    </c:if>
                                </c:if>
                            </c:if>
                        </c:if>
                    </c:if>
                </c:if>
            </c:if>
        </c:if>
    </c:if>
</c:if>

<c:if test="${user.userName == 'xuesong.net'}">userName: ${user.userName}</c:if>
<c:each items="${userList}" var="user">${user.userName} </c:each>
${user.userName}
< test1

</ test2 >

</test3  >

</body>
</html>