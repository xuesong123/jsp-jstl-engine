<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.io.IOException"%>
<t:import name="app:scrollpage" className="test.com.skin.ayada.taglib.ScrollPage"/>
<t:import name="app:test" className="test.com.skin.ayada.taglib.TestTag"/>
<t:import name="app:bodytest" className="test.com.skin.ayada.taglib.TestBodyTag"/>
<%!
    public void hello1(JspWriter out) throws IOException{
        out.println("Hello, World !");
    }
%>

<jsp:declaration>
    public void hello2(JspWriter out) throws IOException{
        out.println("Hello, World !");
    }
</jsp:declaration>

<jsp:declaration>
    public void hello3(JspWriter out) throws IOException{
        out.println("Hello, World !");
    }
</jsp:declaration>

<jsp:declaration>
    public void hello4(JspWriter out) throws IOException{
        out.println("Hello, World !");
    }
</jsp:declaration>
<html>
<head>
<title>test</title>
</head>
<body jsp="<%=1 + 2%>" version="1.0">
============================================
<%
    out.println("Hello, World !");
%>

============================================
<jsp:scriptlet>
    out.println("Hello, World !");
</jsp:scriptlet>

<jsp:declaration>
    public int myInt = 0;
</jsp:declaration>

<jsp:scriptlet>
    // out.println("Hello, World1 !");
</jsp:scriptlet>

<jsp:scriptlet>
    // out.println("Hello, World2 ! </test");
    // out.println("Hello, World2 ! </jsp:scriptlet2>");
</jsp:scriptlet>

<%
    int mytest = 0;
%>

<p>
    myInt: <%= this.myInt %>
</p>

<p>
    mytest: <%=mytest%>
</p>
<jsp:scriptlet>
    out.println("Hello, World4 !");
</jsp:scriptlet>

<jsp:expression>("Hello" + " " + "World !")</jsp:expression>
============================================
<c:set var="myString" value="${StringUtil.replace('abc', 'b', '\n')}"/>
<p>myString: [<c:out value="${myString}"/>]</p>

<c:set var="myString" value="&quot;a + 1&quot;"/>
<p>myString: [<c:out value="${myString}"/>]</p>

<c:set var="myVar" value="Hello, World!"/>
<h1>############## ${myVar} ##############</h1>

<c:out value="c:out: Hello, World!"/>
<div style="background-color: #c0c0c0;"></div>
<c:out value="&quot;<div>Hello World!</div>&quot;" escapeXml="false"/>
<c:out escapeXml="true"><h1>Hello World!</h1></c:out>
<c:out value="<div>Hello World!</div>" escapeXml="true"><h1>Hello World!</h1></c:out>

<c:set var="myName" value="xuesong.net"/>
<c:out value="Hello, ${myName}!" escapeXml="true"/>
<c:out value="Hello, ${myName}!" escapeXml="false"/>

<c:if test="${1 == 1}">c:if test</c:if>

<h1>c:forEach test1</h1>
<c:each    
    items="1,2,3,4,5"
    var="mynum">${mynum}</c:each>

<h1>c:forEach test2</h1>
<c:forEach items="${userList}" var="user" varStatus="status">
    <p>user: ${user.userName}</p>
    <c:choose>
        <c:when test="${user.userName == 'test1'}"><p>test1, good man !</p></c:when>
        <c:when test="${user.userName == 'test2'}"><p>test2, good man !</p></c:when>
        <c:when test="${user.userName == 'test3'}"><p>test3, good man !</p></c:when>
        <c:otherwise><p>unknown user! Do you known 'bad egg'? You! Are!</p></c:otherwise>
    </c:choose>
</c:forEach>

<h1>c:choose test1</h1>
<c:choose>
    <c:when test="${1 == 1}">c:when test="{1 == 1}"</c:when>
    <c:when test="${2 == 2}">c:when test="{2 == 2}"</c:when>
    <c:when test="${3 == 3}">c:when test="{3 == 3}"</c:when>
    <c:otherwise>c:otherwise</c:otherwise>
</c:choose>

<h1>app:test test1</h1>
<app:test myBoolean="true" myChar="c" myByte="1" myInt="-1.0" myFloat="1.0f" myDouble="1.0d" myLong="1L" myString="Hello"/>
<app:test myBoolean="false" myChar="c" myByte="243" myInt="-1.0" myFloat="1.0F" myDouble="1.0D" myLong="1L" myString="Hello"/>
<app:test myInt="-1.0" myFloat="1.0" myDouble="1e3" myLong="1e3" myString="Hello"/>
<app:test myInt="-1.0" myFloat="1.0" myDouble="1.2e3" myLong="1.2e3" myString="Hello"/>

<h1>app:scrollpage test1</h1>
<app:scrollpage count="254" pageNum="2" pageSize="10"/>

<app:bodytest/>
<app:bodytest>Hello World !</app:bodytest>
</body>
</html>