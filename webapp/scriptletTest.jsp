<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="/include/header.jsp"%>

<t:comment>
该页面用来测试jsp编译。Ayada对jsp标记的处理规则：
1. jsp声明和jsp脚本的内容允许为空。
2. jsp表达式不允许为空，如果为空编译期就会报错。例如：<%=%>是不合法的jsp表达式。
3. jsp指令、jsp声明、jsp脚本、jsp表达式必须以%>结束。
4. jsp的结束标记不允许出现在jsp脚本中，否则编译出错。编译器不会做语法检查，只做字符串解析，这点也与jsp/servlet容器的处理一致。
   例如下面的写法是不合法的：
    <%
        String s = "Hello %>";
    %>
    这段代码在tomcat里面也是会编译出错的，可以改成下面的写法：
    <%
        String s = "Hello %" + ">";
    %>
</t:comment>

<%!%>
<%%>
<%!int myVar1=1;%>
<%int myVar2=1;%>

<%int a=1;%>


<%
    int b = 2;
%>

<%=(a + 1)%>

<%

int sum = a + b;

%>

<%=1%>

