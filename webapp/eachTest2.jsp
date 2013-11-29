<%@ page contentType="text/html;charset=UTF-8"%>
<c:list name="userList"/>

<c:forEach begin="1" end="5" step="1" varStatus="status">
    <c:map name="user">
        <c:entry name="userName" value="ayada${status.index}"/>
        <c:entry name="nickName" value="ayada${status.index}"/>
    </c:map>

    <c:execute value="${userList.add(user)}"/>
</c:forEach>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html message="hello" xmlns="http://www.w3.org/1999/xhtml">
<head><title>test</title></head>
<body version="1.0">
<table>
    <c:forEach items="${userList}" var="user" varStatus="status">
        <c:set var="rowNum" value="${((status.index + 2) / 2).intValue()}"/>
        <c:if test="${(status.index) % 2 == 0}"><tr></c:if>
        <c:if test="${rowNum < rows}"><td></c:if>
        <c:if test="${rowNum >= rows}">
            <td class="nbb">
        </c:if>
        <div>status: ${status}</div>
        <div>rows: ${rows}, rowNum: ${rowNum}, status.index: ${status.index}: user.userName: ${user.userName}</div>
        </td>
        <c:if test="${(status.index + 1) % 2 == 0}"></tr></c:if>
    </c:forEach>
</table>
</body>
</html>