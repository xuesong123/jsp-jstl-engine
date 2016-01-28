<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<t:taglib prefix="d" uri="http://localhost/ayada-taglib-standard"/>
### 1
    <p>
            <c:if test="${1 == 1}">Hello world !</c:if>
    </p>

<%----%>
<%--  sfsdfasdsaf --%>
### 2
    <p>
            <c:if test="${1 == 1}" t:clip="false">Hello world !</c:if>
    </p>

### 3
    <p>
            <c:any><c:if test="${1 == 1}">Hello world !</c:if></c:any>
    </p>

<%@ include file="/include/header.jsp"%>

<%int a=1; %>
<%%>

<t:import name="c:forEach" ignoreWhitespace="true"/>
<h1>header</h1>
<p>Clip Test</p>
   <c:set var="myVar1" value="123"/>
   #### 1
               <c:forEach var="myVar" items="1,2,3">
    <p>${myVar}</p>
             </c:forEach>
### 2
<c:choose>
    <c:when test="${1 == 1}">
        Hello !
    </c:when>
    <c:when test="${1 == 1}">
        Hi !
    </c:when>
</c:choose>

### 3
<p>test</p>
    <t:comment>
        输出缓存的内容
    </t:comment>


<p>test</p>
### 4

               <%
    for(int i = 0; i < 3; i++) {
    %>
<p><%=i%></p>
<%
     }
%>

### 5
               <jsp:scriptlet>
    for(int i = 0; i < 3; i++) {
    </jsp:scriptlet>
<p><%=i%></p>
<jsp:scriptlet>
     }
</jsp:scriptlet>

c:remove的bodyContent为empty, 之前的版本有一个bug, 成对闭合的标签如果bodyContent为empty的时候，会忽略掉结束标签后面的所有回车。如果写成自关闭则没有这个问题。
该问题已经修复。
<c:remove var="a"></c:remove>



## 6
这是一个格式处理的测试页面，测试编译程序的clip函数是否正确。
格式处理必须有一个规则，这样编译程序才知道如何处理空格和回车。编译程序对标签的处理规则：
1. jsp指令、jsp生命、jsp脚本、Ayada指令，都会统一删除掉前导空格和后缀回车。不支持配置，统一处理。
2. 标签配置增加ignore-whitespace选项，可选值[true|false]，如果是true，删除掉前导空格和后面的回车。默认是true。t:import指令对应的属性是ignoreWhitespace。
前导空格包括空格和\t符。
后缀回车包括\r和\n。只删除第一个回车。

编译程序在编译出来所有节点列表之后，会对节点列表进行两次扫描：
1. 第一次扫描，根据标签的ignoreWhitespace定义决定是否对标签做格式处理。
   如果ignoreWhitespace为true，则删除标签的前导空格，前导空格是指标签前面的第一个文本节点的尾空格。
   删除标签之后的第一个回车换行符。标签的开始节点和结束节点都按照这个的规则处理。所有的jsp指令、脚本、声明也按照这个规则处理。

2. 第二次扫描，根据标签的bodyContent定义清除标签内的文本节点或者。

总的原则是：标签或者脚本所占的行不输出。即：把标签或者脚本所占的行删除之后就是最终输出的格式。

这个规则会导致一些输出可能不符合编写者的意图，例如：
<div>
    <div>
        <c:if test="${1 == 1}"><p>hello</p></c:if>
    </div>
</div>
我们希望的输出格式应该是如下的样子：
<div>
    <div>
        <p>hello</p>
    </div>
</div>
但由于上面对格式的处理规则：标签必须独占一行，导致输出是下面的样子：
<div>
    <div>
<p>hello</p>    </div>
</div>
一个简单的解决办法是把标签独占一行，改成如下的样子：
<div>
    <div>
        <c:if test="${1 == 1}">
        <p>hello</p>
        </c:if>
    </div>
</div>

第二种方法是使用t:clip属性指令，当一个标签使用了t:clip指令时，标签将不做任何格式处理，该指令只对标签有效；
<div>
    <div>
        <c:if test="${1 == 1}" t:clip="false"><p>hello</p></c:if>
    </div>
</div>

第三种方法是使用c:any标签再包一层，c:any标签事实上什么都不做，但是它的ignoreWhitespace定义为false，这样通过外层标签可以使内层标签起到不做格式处理的作用。
<div>
    <div>
        <c:any><c:if test="${1 == 1}"><p>hello</p></c:if></c:any>
    </div>
</div>
其中第一种方法和第二种方法都不会对性能有任何影响。第三种方法由于多了一层标签，将会多一层处理。