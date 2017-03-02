<html>
<head>
<title>test</title>
<meta http-equiv="Content-Type" content="text/html;"/>
<style type="text/css">
body{font-size: 10pt; color: #333333;}
thead{ font-weight: bold; background-color: #c8fbaf;}
td{font-size: 10pt; text-align: center;}
.odd{background-color: #f3defb;}
.even{background-color: #effff8;}
</style>
</head>
<body>
    <h1>test</h1>
    <table>
        <thead>
            <tr>
                <th>test1</th>
                <th>test2</th>
                <th>test3</th>
                <th>test4</th>
                <th>test5</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${userList}" var="user" varStatus="status">
            <tr <c:out value="${(status.index & 1) == 1 ? 'odd' : 'even'}"/>>
                <td>${status.index + 1}</td>
                <td>${model.code}</td>
                <td>${model.name}</td>
                <td>${model.date}</td>
                <td>${model.value}</td>
                <td style="color: red"><c:if test="${model.value > 105.5}">a</c:if></td>
                <td style="color: red"><c:if test="${model.value > 105.5}">b</c:if></td>
            </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
