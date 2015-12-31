<!--
c:choose 类型是tagdependent, 子节点只允许是标签，不能存在文本
如果有文本将自动忽略
-->
<c:choose>
adsfdaff
    ${1 + 2}<c:when test="${1 == 1}">
        <p>1==1</p>
    </c:when>
    asdfdsafdaf${1 + 3}<c:when test="${1 == 1}">
        <p>2==2</p>
    </c:when>
    <c:otherwise></c:otherwise>
</c:choose>
