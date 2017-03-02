概述 Overview
==================

Ayada是一个完全脱离web环境的jsp&jstl引擎，几乎兼容jsp&jstl的全部功能。


* 语法完全同jsp语法，支持java脚本和jstl标签，对于使用jstl的人来说没有任何学习成本。
* 零配置，不需要任何配置文件，也可以指定配置文件，配置文件简单明了。
* 可插拔的模块设计，模板加载方式，el引擎，运行模式均可通过不同的实现替换。
* 多种方式自定义标签，原生的java代码自定义标签或者直接通过页面自定义标签。
* 支持自定义el转码，不需要对el表达式的输出做html转码，防止xss注入。
* 简单快速的标签重写功能，专门针对用模板生成模板。
* 支持java脚本和标签混合模式，这点与jsp一致，如果你追求极致性能那么可以完全使用java脚本编写模版。
* 支持解释模式和编译模式，解释模式适合只需要在内存中运行的模板。编译模式性能更好。

文档 Documents
=================

docs/html/index.html

项目目录结构：
=================

    src - 源码
    release - 最终发布包
    docs/html - 相关文档
    webapp - 示例代码及测试代码
    work   - 编译模式下生成的代码

Change List 1.0.1.4：
=================
1.0.1.6：
1. 重构，与之前版本不兼容。
2. 默认的el引擎替换为mvel, ognl太慢。其他性能优化. 
3. bug修复。
4. 编译模式标签属性支持jsp表达式语法.

1.0.1.4：
1. 功能bug修复, 一个不太容易触发的bug, 当标签实现了TryCatchFinally时, 解释模式下只会执行最近的那个try catch.
2. 编译模式生成代码调整, 确保release方法在任何时候都会被调用.
3. 性能优化, 解释模式大幅提升性能较原来提升40% - 50%. 性能测试用例参见: test.com.skin.ayada.template.PerformanceTest


Tag Example
===================

c:if

    <c:if test="${1 == 1}">1 == 1</c:if>

out:
    1 == 1

c:forEach & c:each

    <c:forEach items="${1, 2, 3}" var="myvar" varStatus="status">
        ${status.index}: ${myvar}
    </c:forEach>

out:
    0: 1
    1: 2
    2: 3

c:forEach & c:each

    <c:forEach begin="1" end="3" step="1" var="myvar" varStatus="status">
        ${status.index}: ${myvar}
    </c:forEach>

out:
    0: 1
    1: 2
    2: 3

c:forEach & c:each
    var varList = [1, 2, 3];

    <c:forEach items="${varList}" var="myvar" varStatus="status">
        ${status.index}: ${myvar}
    </c:forEach>

out:
    0: 1
    1: 2
    2: 3

c:set

    <c:set var="myvar1" value="${myvar}"/>
    <c:set var="myvar1" value="1,2,3"/>

c:out

    <c:out value="${myvar}" defaultValue="1,2,3" escapeXml="true"/>

out:
    1,2,3

c:choose & c:when & c:otherwise

    <c:choose>
        <c:when test="${userList.length > 6}"><div>test1</div></c:when>
        <c:when test="${userList.length > 7}"><div>test2</div></c:when>
        <c:otherwise><div>test3</div></c:otherwise>
    </c:choose>

c:comment

    <c:comment>
        ...
    </c:comment>

fmt:formatDate

    <fmt:formatDate var="mydate1" value="${mydate}" pattern="yyyy-MM-dd"/>

sql:connect

    <sql:connect var="connection" url="jdbc:mysql://localhost:3306?user=root&password=1234&characterEncoding=utf8" driverClass="com.mysql.jdbc.Driver">
        <sql:execute>...</sql:execute>
        <sql:execute sql="update mytest2 set a=1 where b=1">...</sql:execute>
        <sql:execute file="/user/db/create.sql">...</sql:execute>
    </sql:connect>

sql:execute

    <sql:execute>...</sql:execute>
    <sql:execute sql="update mytest2 set a=1 where b=1">...</sql:execute>
    <sql:execute file="/user/db/create.sql">...</sql:execute>

sql:query

    <sql:query var="resultSet" sql="select * from my_test2 where my_id > 1001" offset="2">...</sql:query>

c:include

    <c:include page="/include/mytest.jsp">
        <c:param name="user" value="${user}"/>
    </c:include>

c:macro

    <c:macro name="myTestMacro">
        <c:param name="user" value="${user}"/>
    </c:macro>

import a tag, scope: current page

t:include

<t:include file="/include/mytest.jsp"/>

include a page

配置说明:
ayada-default.properties

    ayada.compile.source-factory=com.skin.ayada.template.DefaultSourceFactory  # 脚本加载factory, 从文件系统加载
    ayada.compile.source-pattern=jsp,jspf,jspx,tpl                             # 脚本扩展名, 其他类型的文件会被认为是静态文件
    ayada.compile.ignore-jsptag=true                                           # 忽略jsp标签
    ayada.compile.fast-jstl=true                                               # 是否使用fast-jstl

ayada-default.properties

    set util=com.skin.ayada.jstl.util.BeanUtil                                 # 定义一个全局工具类

el表达式转义

    在ayada-default.properties文件中添加一行
    set ELEncoder=com.skin.ayada.jstl.util.HtmlEncoder                         # 对所有的el表达式的输出使用html编码之后再输出

在脚本中输出脚本

有时候我们会希望在脚本中输出脚本, 例如页面中输出一段模板, 这段模板在js中再使用js模板引擎进行渲染, 这中情况下可以使用t:text指令

    <t:text escape="xml">
        <c:if test="${1 == 1}"></c:if>
    </t:text>

t:text标签中的任何内容都会作为静态内容原样输出, 如果escape="xml", 那么其中的内容还会被做html编码
t:text是编译指令，因此转码是在编译期进行的, 以后每次执行都直接输出



java: https://github.com/xuesong123/ayada
javascript: https://github.com/xuesong123/javascript-template