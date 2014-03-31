<%@ page contentType="text/html; charset=UTF-8"%>
<!--
t:import: 编译指令
1. 可以放在任何位置, 但必须在使用之前引入
2. 如果多次引入同一个tag, tag的bodyContent以最后一次引入的为准, 引擎在编译结束之后才会根据bodyContent的类型做一次紧缩处理
   bodyContent: jsp|tagdependent|empty
   如果是tagdependent, 那么该tag内部的所有文本节点和表达式节点都会被清除, 其子节点的内容不会清除(但是会根据子节点的bodyContent决定是否清除)
   如果是empty, 那么内部的任何节点都会被清除
   如果是jsp, 则保持现有内容不便
   因此所有的引入操作最好放在文件头, 并且对同一个tag只引入一次

3. className不是必选项, 可以为空, 如果为空则使用ayada-taglib-default.xml或者ayada-taglib.xml文件中配置的className, 因此下面的两种写法都是合法的
    <t:import name="c:if" bodyContent="tagdependent" description="重写c:if标签, 不输出内容"/>
    <t:import name="c:if" className="com.skin.ayada.jstl.core.IfTag" bodyContent="tagdependent" description="重写c:if标签, 不输出内容"/>

4. bodyContent和description都不是必选项, 可以为空, bodyContent默认为jsp
-->
<t:import name="c:if" bodyContent="tagdependent" description="重写c:if标签, 不输出内容"/>
<t:import name="c:if" className="com.skin.ayada.jstl.core.IfTag" bodyContent="tagdependent" description="重写c:if标签, 不输出内容"/>

<h1>tag.bodyContent</h1>
<p>1. jsp: any content</p>
<p>2. tagdependent: tag only</p>
<p>3. empty: ignore any content</p>

<!-- 使用重新定义的c:if标签, 该标签内部的所有文本内容在编译期都会被清除, 但是自标签的文本仍然可以输出 -->
<c:if test="${1 == 1}">
    <p>I'm hidden!</p>
    <c:out escapeXml="true">"I'm here !"</c:out>
</c:if>

<!-- c:command的bodyContent被定义为tagdependent，因此内部的文本内容都会被忽略。-->
<c:command>
    <p>I'm hidden!</p>
    <c:if test="${1 == 2}">
        <io:copy   file="E:/WorkSpace/ayada/webapp/test1" todir="E:/WorkSpace/ayada/webapp/test2"/>
        <io:delete file="E:/WorkSpace/ayada/webapp/test2/style.css"/>
        <io:mkdir  file="E:/WorkSpace/ayada/webapp/test2/empty1"/>
        <io:mkdir  file="E:/WorkSpace/ayada/webapp/test2/empty2"/>
        <io:delete file="E:/WorkSpace/ayada/webapp/test2/empty2"/>
        <c:exit test="${1 == 1}"/>
    </c:if>
</c:command>

<h1>sql:execute</h1>
<h2>connection</h2>
<p>1. get from attribute, example: connection="${?expression}"</p>
<p>2. get from pageContext by name: connection</p>
<p>3. get from parent tag</p>
<p>4. throws exception</p>
<p>template.home: ${template.home}</p>

<!-- use external connection -->
<sql:connect var="connection2" connection="${myConnection}"></sql:connect>

<c:if test="${1 == 1}">
    <c:set var="createDatabaseUrl" value="jdbc:mysql://10.3.254.97:3307?zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=utf-8&autoReconnection=true"/>
    <c:set var="url" value="jdbc:mysql://10.3.254.97:3307/mytest2?zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=utf-8&autoReconnection=true"/>
    <c:set var="driverClass" value="com.mysql.jdbc.Driver"/>
    <c:set var="userName" value="bookAdmin"/>
    <c:set var="password" value="bookAdmin"/>
</c:if>

<c:if test="${1 == 2}">
    <c:set var="createDatabaseUrl" value="jdbc:mysql://localhost:3306?user=root&password=1234&characterEncoding=utf8"/>
    <c:set var="url" value="jdbc:mysql://localhost:3306/mytest2?user=root&password=1234&characterEncoding=utf8"/>
    <c:set var="driverClass" value="com.mysql.jdbc.Driver"/>
    <c:set var="userName" value=""/>
    <c:set var="password" value=""/>
</c:if>

<sql:connect var="connection" url="${createDatabaseUrl}" driverClass="com.mysql.jdbc.Driver" userName="${userName}" password="${password}">
<sql:execute out="${pageContext}">
    drop database if exists mytest2;
    create database mytest2 character set utf8;
</sql:execute>
</sql:connect>

<sql:connect var="connection" url="${url}" driverClass="com.mysql.jdbc.Driver" userName="${userName}" password="${password}">
    <sql:execute home="${template.home}/database" file="create.sql" encoding="UTF-8" out="${pageContext}"/>
    <sql:execute sql="delete from my_test1;" out="${pageContext}"/>
    <sql:execute sql="delete from my_test2;" out="${pageContext}"/>
    <sql:execute sql="insert into my_test2(my_id, my_code, my_name) values (1, '1', '1');"/>

    <sql:execute out="${pageContext}">
        <c:forEach items="1, 2, 3, 4, 5" var="id">
            insert into my_test1(my_id, my_code, my_name) values (${id}, '${id}', '${id}');
        </c:forEach>
        <c:forEach items="1, 2, 3, 4, 5" var="id">
            insert into my_test2(my_id, my_code, my_name) values (${id}, '${id}', '${id}');
        </c:forEach>
    </sql:execute>

    <sql:execute out="${pageContext.getOut()}">
        delete from my_test2;
        insert into my_test2(my_id, my_code, my_name) values (1001, '1001', '1001');
        insert into my_test2(my_id, my_code, my_name) values (1002, '1001', '1001');
        insert into my_test2(my_id, my_code, my_name) values (1003, '1001', '1001');
        insert into my_test2(my_id, my_code, my_name) values (1004, '1001', '1001');
        insert into my_test2(my_id, my_code, my_name) values (1005, '1001', '1001');
        insert into my_test2(my_id, my_code, my_name) values (1006, '1001', '1001');
        insert into my_test2(my_id, my_code, my_name) values (1007, '1001', '1001');
        insert into my_test2(my_id, my_code, my_name) values (1008, '1001', '1001');
    </sql:execute>

    <sql:execute connection="${connection}" sql="insert into my_test2(my_id, my_code, my_name) values (1009, '1001', '1001');"/>
    <table>
        <tr>
            <td>id</td>
            <td>userName</td>
            <td>nickName</td>
        </tr>
        <sql:query var="resultSet" sql="select * from my_test2 where my_id > 1001" offset="2" length="3">
        <tr>
            <td>${resultSet.getLong("my_id")}</td>
            <td>${resultSet.getString("my_code")}</td>
            <td>${resultSet.getString("my_name")}</td>
        </tr>
        </sql:query>
    </table>
</sql:connect>