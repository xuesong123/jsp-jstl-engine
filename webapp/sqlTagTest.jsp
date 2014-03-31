<%@ page contentType="text/html;charset=UTF-8"%>
<sql:execute sql="delete from my_test2;" out="${pageContext.getOut()}"/>
<sql:execute sql="insert into my_test2(my_id, my_code, my_name) values (1, '1', '1');"/>
<sql:execute home="${template.home}/database" file="create.sql" encoding="UTF-8" out="${pageContext.getOut()}"/>

<sql:execute>
    <c:forEach items="1, 2, 3, 4, 5" var="id">
        insert into my_test1(my_id, my_code, my_name) values (${id}, '${id}', '${id}');
    </c:forEach>
    <c:forEach items="1, 2, 3, 4, 5" var="id">
        insert into my_test2(my_id, my_code, my_name) values (${id}, '${id}', '${id}');
    </c:forEach>
</sql:execute>

<sql:execute>
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
