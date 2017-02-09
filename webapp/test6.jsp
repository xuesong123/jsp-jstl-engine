<%@ page import="java.util.List"%>
<div>
<h1><%=pageContext.getAttribute("name")%></h1>
<%
    java.util.List<String> data = ((List<String>)(pageContext.getAttribute("data")));
%>
<table border="${border}">
    <tr>
        <th>&#160;</th>
    </tr>
    <%
        for(String cell : data) {
    %>
        <tr>
            <th><%=cell%></th>
        </tr>
    <%}%>
</table>

<table border="${border}">
    <tr>
        <th>&#160;</th>
    </tr>
    <%
        for(String cell : data) {
    %>
        <tr>
            <th><%=cell%></th>
        </tr>
    <%}%>
</table>

<table border="${border}">
    <tr>
        <th>&#160;</th>
    </tr>
    <%
        for(String cell : data) {
    %>
        <tr>
            <th><%=cell%></th>
        </tr>
    <%}%>
</table>

<table border="${border}">
    <tr>
        <th>&#160;</th>
    </tr>
    <%
        for(String cell : data) {
    %>
        <tr>
            <th><%=cell%></th>
        </tr>
    <%}%>
</table>
</div>