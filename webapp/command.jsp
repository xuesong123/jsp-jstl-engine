<%@ page contentType="text/html;charset=UTF-8"%>
<io:copy file="E:/WorkSpace/ayada/webapp/test1" todir="E:/WorkSpace/ayada/webapp/test2"/>
<io:delete file="E:/WorkSpace/ayada/webapp/test2/style.css"/>
<io:mkdir file="E:/WorkSpace/ayada/webapp/test2/empty1"/>
<io:mkdir file="E:/WorkSpace/ayada/webapp/test2/empty2"/>
<io:delete file="E:/WorkSpace/ayada/webapp/test2/empty2"/>

<c:exit test="${1 == 2}"/>

<h1>sql:execute</h1>
<h2>connection</h2>
<p>1. get from attribute, example: connection="[expression]"</p>
<p>2. get from pageContext by name: connection</p>
<p>3. get from parent tag</p>
<p>4. throws exception</p>

<!-- use external connection -->
<sql:connect var="connection2" connection="${myConnection}"></sql:connect>

<sql:connect var="connection" url="jdbc:mysql://localhost:3306?user=root&password=1234&characterEncoding=utf8" driverClass="com.mysql.jdbc.Driver">
    <sql:execute out="${pageContext.getOut()}">
        drop database if exists mytest2;
        create database mytest2 character set utf8;
    </sql:execute>
</sql:connect>

<sql:connect var="connection" url="jdbc:mysql://localhost:3306/mytest2?user=root&password=1234&characterEncoding=utf8" driverClass="com.mysql.jdbc.Driver">
    <sql:execute home="${template.home}/database" file="create.sql" encoding="UTF-8" out="${pageContext.getOut()}"/>

    <sql:execute sql="delete from my_test2;" out="${pageContext.getOut()}"/>
    <sql:execute sql="insert into my_test2(my_id, my_code, my_name) values (1, '1', '1');"/>

    <sql:execute out="${pageContext.getOut()}">
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
        <sql:query var="resultSet" sql="select * from my_test2 where my_id > 1001" offset="2">
        <tr>
            <td>${resultSet.getLong("my_id")}</td>
            <td>${resultSet.getString("my_code")}</td>
            <td>${resultSet.getString("my_name")}</td>
        </tr>
        </sql:query>
    </table>
</sql:connect>
