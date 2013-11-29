<%@ page contentType="text/html;charset=UTF-8"%>
<c:list name="userList"/>

<c:forEach begin="1" end="5" step="1" varStatus="status">
    <c:map name="user">
        <c:entry name="userName" value="ayada${status.index}"/>
        <c:entry name="nickName" value="ayada${status.index}"/>
    </c:map>

    <c:execute result="${userList.add(user)}"/>
</c:forEach>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>test</title>
<meta http-equiv="Pragma" content="no-cache"/> 
<meta http-equiv="Cache-Control" content="no-cache"/> 
<meta http-equiv="Expires" content="0"/>
<link rel="stylesheet" type="text/css" href="/style/basic.css"/>
<link rel="stylesheet" type="text/css" href="/style/book.css"/>
<script type="text/javascript" src="/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="/js/jquery-plugin.min.js"></script>
<script type="text/javascript">
<!--
/* */
var a = "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";
var a = "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";
var a = "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";
var a = "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";
var a = "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";
var a = "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";
var a = "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";
var a = "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";
var a = "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";
var a = "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";
var a = "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";
var a = "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";
var a = "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";
var a = "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";
var a = "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";
var a = "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";
var a = "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";
var a = "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";
var a = "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";
var a = "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";
var a = "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";
var a = "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";
var a = "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";
var a = "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";
var a = "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";
var a = "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";
var a = "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";
var a = "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";
var a = "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";
var a = "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";
//-->
</script>
</head>
<body version="1.0">
<c:set var="rows" value="${((userList.size() + 1) / 2).intValue()}"/>

<table>
    <c:forEach items="${userList}" var="user" varStatus="status">
        <c:set var="rowNum" value="${((status.index + 2) / 2).intValue()}"/>
        <c:if test="${(status.index) % 2 == 0}"><tr></c:if>
        <c:if test="${rowNum < rows}"><td></c:if>
        <c:if test="${rowNum >= rows}"><td class="nbb"></c:if>
        <div>status:</div>
        <div>rows: ${rows}, rowNum: ${rowNum}, status.index: ${status.index}: user.userName: ${user.userName}</div>
        </td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <c:if test="${(status.index + 1) % 2 == 0}"></tr></c:if>
    </c:forEach>

    <c:forEach items="${userList}" var="user" varStatus="status">
        <c:set var="rowNum" value="${((status.index + 2) / 2).intValue()}"/>
        <c:if test="${(status.index) % 2 == 0}"><tr></c:if>
        <c:if test="${rowNum < rows}"><td></c:if>
        <c:if test="${rowNum >= rows}"><td class="nbb"></c:if>
        <div>status:</div>
        <div>rows: ${rows}, rowNum: ${rowNum}, status.index: ${status.index}: user.userName: ${user.userName}</div>
        </td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <c:if test="${(status.index + 1) % 2 == 0}"></tr></c:if>
    </c:forEach>

    <c:forEach items="${userList}" var="user" varStatus="status">
        <c:set var="rowNum" value="${((status.index + 2) / 2).intValue()}"/>
        <c:if test="${(status.index) % 2 == 0}"><tr></c:if>
        <c:if test="${rowNum < rows}"><td></c:if>
        <c:if test="${rowNum >= rows}"><td class="nbb"></c:if>
        <div>status:</div>
        <div>rows: ${rows}, rowNum: ${rowNum}, status.index: ${status.index}: user.userName: ${user.userName}</div>
        </td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <c:if test="${(status.index + 1) % 2 == 0}"></tr></c:if>
    </c:forEach>

    <c:forEach items="${userList}" var="user" varStatus="status">
        <c:set var="rowNum" value="${((status.index + 2) / 2).intValue()}"/>
        <c:if test="${(status.index) % 2 == 0}"><tr></c:if>
        <c:if test="${rowNum < rows}"><td></c:if>
        <c:if test="${rowNum >= rows}"><td class="nbb"></c:if>
        <div>status:</div>
        <div>rows: ${rows}, rowNum: ${rowNum}, status.index: ${status.index}: user.userName: ${user.userName}</div>
        </td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <c:if test="${(status.index + 1) % 2 == 0}"></tr></c:if>
    </c:forEach>

    <c:forEach items="${userList}" var="user" varStatus="status">
        <c:set var="rowNum" value="${((status.index + 2) / 2).intValue()}"/>
        <c:if test="${(status.index) % 2 == 0}"><tr></c:if>
        <c:if test="${rowNum < rows}"><td></c:if>
        <c:if test="${rowNum >= rows}"><td class="nbb"></c:if>
        <div>status:</div>
        <div>rows: ${rows}, rowNum: ${rowNum}, status.index: ${status.index}: user.userName: ${user.userName}</div>
        </td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <c:if test="${(status.index + 1) % 2 == 0}"></tr></c:if>
    </c:forEach>
</table>
<table>
    <c:forEach items="${userList}" var="user" varStatus="status">
        <c:set var="rowNum" value="${((status.index + 2) / 2).intValue()}"/>
        <c:if test="${(status.index) % 2 == 0}"><tr></c:if>
        <c:if test="${rowNum < rows}"><td></c:if>
        <c:if test="${rowNum >= rows}"><td class="nbb"></c:if>
        <div>status:</div>
        <div>rows: ${rows}, rowNum: ${rowNum}, status.index: ${status.index}: user.userName: ${user.userName}</div>
        </td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <c:if test="${(status.index + 1) % 2 == 0}"></tr></c:if>
    </c:forEach>

    <c:forEach items="${userList}" var="user" varStatus="status">
        <c:set var="rowNum" value="${((status.index + 2) / 2).intValue()}"/>
        <c:if test="${(status.index) % 2 == 0}"><tr></c:if>
        <c:if test="${rowNum < rows}"><td></c:if>
        <c:if test="${rowNum >= rows}"><td class="nbb"></c:if>
        <div>status:</div>
        <div>rows: ${rows}, rowNum: ${rowNum}, status.index: ${status.index}: user.userName: ${user.userName}</div>
        </td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <c:if test="${(status.index + 1) % 2 == 0}"></tr></c:if>
    </c:forEach>

    <c:forEach items="${userList}" var="user" varStatus="status">
        <c:set var="rowNum" value="${((status.index + 2) / 2).intValue()}"/>
        <c:if test="${(status.index) % 2 == 0}"><tr></c:if>
        <c:if test="${rowNum < rows}"><td></c:if>
        <c:if test="${rowNum >= rows}"><td class="nbb"></c:if>
        <div>status:</div>
        <div>rows: ${rows}, rowNum: ${rowNum}, status.index: ${status.index}: user.userName: ${user.userName}</div>
        </td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <c:if test="${(status.index + 1) % 2 == 0}"></tr></c:if>
    </c:forEach>

    <c:forEach items="${userList}" var="user" varStatus="status">
        <c:set var="rowNum" value="${((status.index + 2) / 2).intValue()}"/>
        <c:if test="${(status.index) % 2 == 0}"><tr></c:if>
        <c:if test="${rowNum < rows}"><td></c:if>
        <c:if test="${rowNum >= rows}"><td class="nbb"></c:if>
        <div>status:</div>
        <div>rows: ${rows}, rowNum: ${rowNum}, status.index: ${status.index}: user.userName: ${user.userName}</div>
        </td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <c:if test="${(status.index + 1) % 2 == 0}"></tr></c:if>
    </c:forEach>

    <c:forEach items="${userList}" var="user" varStatus="status">
        <c:set var="rowNum" value="${((status.index + 2) / 2).intValue()}"/>
        <c:if test="${(status.index) % 2 == 0}"><tr></c:if>
        <c:if test="${rowNum < rows}"><td></c:if>
        <c:if test="${rowNum >= rows}"><td class="nbb"></c:if>
        <div>status:</div>
        <div>rows: ${rows}, rowNum: ${rowNum}, status.index: ${status.index}: user.userName: ${user.userName}</div>
        </td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <td align="center">test</td>
        <c:if test="${(status.index + 1) % 2 == 0}"></tr></c:if>
    </c:forEach>
</table>
</body>
</html>