<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.io.File"%>
<t:import name="app:scrollpage" className="test.com.skin.ayada.taglib.ScrollPage"/>
<t:import name="app:test" className="test.com.skin.ayada.taglib.TestTag"/>
<html>
<head>
<title>test</title>
</head>
<body jsp="<%=1 + 2%>" version="1.0">

<jsp:declaration>
    public int myInt = 0;
</jsp:declaration>

<%!
    public void hello1(){
        System.out.println("Hello, Ayada !");
    }
%>

<jsp:declaration>
    public void hello2(){
        System.out.println("Hello, Ayada !");
    }
</jsp:declaration>

<jsp:declaration>
    public void hello3(){
        System.out.println("Hello, Ayada !");
    }
</jsp:declaration>

<jsp:declaration>
    public void hello4(){
        System.out.println(new File("."));
    }
</jsp:declaration>

<jsp:scriptlet>
    System.out.println("Hello, Ayada1 !");
</jsp:scriptlet>

<jsp:scriptlet>
    System.out.println("Hello, Ayada2 ! </test");
    System.out.println("Hello, Ayada2 ! </jsp:scriptlet2>");
</jsp:scriptlet>

<%
    int mytest = 0;
    System.out.println("Hello, Ayada3 !");
%>

<%=mytest%>

<jsp:scriptlet>
    System.out.println("Hello, Ayada4 !");
</jsp:scriptlet >

<jsp:expression>("Hello" + " " + "World !")</jsp:expression>

<c:set var="myVar" value="Hello, Ayada!"/>
<h1>${myVar}</h1>

<c:out value="c:out: Hello, Ayada!"/>
<div style="background-color: #c0c0c0;"></div>
<c:out value="<div>Hello Ayada!</div>" escapeXml="true"/>
<c:out escapeXml="true"><h1>Hello Ayada!</h1></c:out>

<c:if test="${1 == 1}">c:if test</c:if>

<h1>c:forEach test1</h1>
<c:forEach    
    items="1,2,3,4,5"
    var="mynum">${mynum}</c:forEach>

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
</body>
</html>