<div>
<h1>${name}</h1>
<table border="${border}">
    <tr>
        <th>&#160;</th>
        <c:forEach var="cell" items="${data}">
        <th>${cell}</th>
        </c:forEach>
    </tr>

    <c:forEach var="row" items="${data}">
    <tr>
        <th>${row}</th>
        <c:forEach var="cell" items="${data}">
            <td>&#x${row}${cell};</td>
        </c:forEach>
    </tr>
    </c:forEach>
</table>
</div>