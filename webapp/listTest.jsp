<h1>\nser.jsp</h1>
<c:out value="<h1>\nse
r.jsp</h1>"></c:out>

<h1>\user.jsp</h1>
<c:out value="<h1>\user
.jsp</h1>"></c:out>

<c:set var="myVar1" value="abc"/>
<c:out value="${myVar1}"/>
<c:out value="xxx${myVar1}xxx"/>
<c:list name="myList">
    <c:element index="0" value="${myVar1}"/>
    <c:element index="0" value="abc${myVar1}xyz"/>
    <c:element index="2" value="456"/>
</c:list>
${myList}