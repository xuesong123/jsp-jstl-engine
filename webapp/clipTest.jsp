<t:import name="c:forEach" ignoreWhitespace="true"/>
<h1>header</h1>
<p>Clip Test</p>
   <c:set var="myVar1" value="123"/>
   ####
               <c:forEach var="myVar" items="1,2,3">
    <p>${myVar}</p>
             </c:forEach>



###
<c:choose>
    <c:when test="${1 == 1}">
        Hello !
    </c:when>
    <c:when test="${1 == 1}">
        Hi !
    </c:when>
</c:choose>


###

<p>test</p>
    <t:comment>
        Êä³ö»º´æµÄÄÚÈİ
    </t:comment>
<p>test</p>


###

               <%
    for(int i = 0; i < 3; i++) {
    %>
<p><%=i%></p>
<%
     }
%>

###
               <jsp:scriptlet>
    for(int i = 0; i < 3; i++) {
    </jsp:scriptlet>
<p><%=i%></p>
<jsp:scriptlet>
     }
</jsp:scriptlet>