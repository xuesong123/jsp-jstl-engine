<%@ page contentType="text/html;charset=UTF-8"%>
<file:copy dir="" todir=""/>
<file:copy file="" todir=""/>
<file:delete file=""/>
<file:delete dir=""/>
<file:mkdir dir=""/>

<h1>sql:execute</h1>
<h2>connection</h2>
<p>1. get from attribute, example: connection="[expression]"</p>
<p>2. get from pageContext by name: connection</p>
<p>3. get from parent tag</p>
<p>4. throws exception</p>

<sql:connect var="connection" url="jdbc:mysql://localhost:3306/mytest?user=root&password=1234&characterEncoding=utf8" driverClass="com.mysql.jdbc.Driver">
    <sql:execute sql="delete from forum_user;" out="${pageContext.getOut()}"/>
    <sql:execute sql="insert into forum_user(user_id, user_name, nick_name, password, group_id, user_level, user_gender, user_avatar, user_signature, user_email, create_time, update_time, expire_time, last_login_time, last_login_ip, user_salt, user_status, user_order) values (1,    'webmaster', '站点管理员', '53B987E1F981FA908F247936E9E66CD2', 1, 0, 1, '', '超级管理员', 'webmaster@admin.com', '2013-02-09 08:00:00', '2013-02-09 08:00:00', '5000-01-01 00:00:00', '2013-02-09 08:00:00', '127.0.0.1', 'c234a161', 1, 1);" out="${pageContext.getOut()}"/>
    <sql:execute home="${template.home}/database" file="create.sql" encoding="UTF-8" out="${pageContext.getOut()}"/>

    <sql:execute out="${pageContext.getOut()}">
        <c:forEach items="1, 2, 3, 4, 5" var="id">
            insert into my_test1(my_id, my_code, my_name) values (${id}, '${id}', '${id}');
        </c:forEach>
        <c:forEach items="1, 2, 3, 4, 5" var="id">
            insert into my_test2(my_id, my_code, my_name) values (${id}, '${id}', '${id}');
        </c:forEach>
    </sql:execute>

    <sql:execute out="${pageContext.getOut()}">
    delete from forum_user;
    insert into forum_user(user_id, user_name, nick_name, password, group_id, user_level, user_gender, user_avatar, user_signature, user_email, create_time, update_time, expire_time, last_login_time, last_login_ip, user_salt, user_status, user_order) values (1,    'webmaster', '站点管理员', '53B987E1F981FA908F247936E9E66CD2', 1, 0, 1, '', '超级管理员', 'webmaster@admin.com', '2013-02-09 08:00:00', '2013-02-09 08:00:00', '5000-01-01 00:00:00', '2013-02-09 08:00:00', '127.0.0.1', 'c234a161', 1, 1);
    insert into forum_user(user_id, user_name, nick_name, password, group_id, user_level, user_gender, user_avatar, user_signature, user_email, create_time, update_time, expire_time, last_login_time, last_login_ip, user_salt, user_status, user_order) values (2,    'admin', '超级管理员', '53B987E1F981FA908F247936E9E66CD2', 1, 0, 1, '', '超级管理员', 'admin@admin.com', '2013-02-09 08:00:00', '2013-02-09 08:00:00', '5000-01-01 00:00:00', '2013-02-09 08:00:00', '127.0.0.1', 'c234a161', 1, 1);
    insert into forum_user(user_id, user_name, nick_name, password, group_id, user_level, user_gender, user_avatar, user_signature, user_email, create_time, update_time, expire_time, last_login_time, last_login_ip, user_salt, user_status, user_order) values (3,    'test1', '令狐冲', '53B987E1F981FA908F247936E9E66CD2', 2, 0, 1, '', '令狐冲', 'test1@admin.com', '2013-02-09 08:00:00', '2013-02-09 08:00:00', '5000-01-01 00:00:00', '2013-02-09 08:00:00', '127.0.0.1', 'c234a161', 1, 1);
    insert into forum_user(user_id, user_name, nick_name, password, group_id, user_level, user_gender, user_avatar, user_signature, user_email, create_time, update_time, expire_time, last_login_time, last_login_ip, user_salt, user_status, user_order) values (4,    'test2', '张无忌', '53B987E1F981FA908F247936E9E66CD2', 2, 0, 1, '', '张无忌', 'test2@admin.com', '2013-02-09 08:00:00', '2013-02-09 08:00:00', '5000-01-01 00:00:00', '2013-02-09 08:00:00', '127.0.0.1', 'c234a161', 1, 1);
    insert into forum_user(user_id, user_name, nick_name, password, group_id, user_level, user_gender, user_avatar, user_signature, user_email, create_time, update_time, expire_time, last_login_time, last_login_ip, user_salt, user_status, user_order) values (5,    'test3', '任我行', '53B987E1F981FA908F247936E9E66CD2', 2, 0, 1, '', '任我行', 'test3@admin.com', '2013-02-09 08:00:00', '2013-02-09 08:00:00', '5000-01-01 00:00:00', '2013-02-09 08:00:00', '127.0.0.1', 'c234a161', 1, 1);
    insert into forum_user(user_id, user_name, nick_name, password, group_id, user_level, user_gender, user_avatar, user_signature, user_email, create_time, update_time, expire_time, last_login_time, last_login_ip, user_salt, user_status, user_order) values (6,    'test4', '任盈盈', '53B987E1F981FA908F247936E9E66CD2', 2, 0, 1, '', '任盈盈', 'test4@admin.com', '2013-02-09 08:00:00', '2013-02-09 08:00:00', '5000-01-01 00:00:00', '2013-02-09 08:00:00', '127.0.0.1', 'c234a161', 1, 1);
    insert into forum_user(user_id, user_name, nick_name, password, group_id, user_level, user_gender, user_avatar, user_signature, user_email, create_time, update_time, expire_time, last_login_time, last_login_ip, user_salt, user_status, user_order) values (7,    'test5', '田伯光', '53B987E1F981FA908F247936E9E66CD2', 2, 0, 1, '', '田伯光', 'test5@admin.com', '2013-02-09 08:00:00', '2013-02-09 08:00:00', '5000-01-01 00:00:00', '2013-02-09 08:00:00', '127.0.0.1', 'c234a161', 1, 1);
    insert into forum_user(user_id, user_name, nick_name, password, group_id, user_level, user_gender, user_avatar, user_signature, user_email, create_time, update_time, expire_time, last_login_time, last_login_ip, user_salt, user_status, user_order) values (8,    'test6', '岳不群', '53B987E1F981FA908F247936E9E66CD2', 2, 0, 1, '', '岳不群', 'test6@admin.com', '2013-02-09 08:00:00', '2013-02-09 08:00:00', '5000-01-01 00:00:00', '2013-02-09 08:00:00', '127.0.0.1', 'c234a161', 1, 1);
    insert into forum_user(user_id, user_name, nick_name, password, group_id, user_level, user_gender, user_avatar, user_signature, user_email, create_time, update_time, expire_time, last_login_time, last_login_ip, user_salt, user_status, user_order) values (9,    'test7', '林平之', '53B987E1F981FA908F247936E9E66CD2', 2, 0, 1, '', '林平之', 'test7@admin.com', '2013-02-09 08:00:00', '2013-02-09 08:00:00', '5000-01-01 00:00:00', '2013-02-09 08:00:00', '127.0.0.1', 'c234a161', 1, 1);
    insert into forum_user(user_id, user_name, nick_name, password, group_id, user_level, user_gender, user_avatar, user_signature, user_email, create_time, update_time, expire_time, last_login_time, last_login_ip, user_salt, user_status, user_order) values (10,   'test8', '思过崖', '53B987E1F981FA908F247936E9E66CD2', 2, 0, 1, '', '思过崖', 'test8@admin.com', '2013-02-09 08:00:00', '2013-02-09 08:00:00', '5000-01-01 00:00:00', '2013-02-09 08:00:00', '127.0.0.1', 'c234a161', 1, 1);
    </sql:execute>

    <sql:execute sql="insert into forum_user(user_id, user_name, nick_name, password, group_id, user_level, user_gender, user_avatar, user_signature, user_email, create_time, update_time, expire_time, last_login_time, last_login_ip, user_salt, user_status, user_order) values (1001, 'test9', '无名氏', '53B987E1F981FA908F247936E9E66CD2', 2, 0, 1, '', '无名氏', 'test9@admin.com', '2013-02-09 08:00:00', '2013-02-09 08:00:00', '5000-01-01 00:00:00', '2013-02-09 08:00:00', '127.0.0.1', 'c234a161', 1, 1);" out="${pageContext.getOut()}"/>

    <table>
        <tr>
            <td>id</td>
            <td>userName</td>
            <td>nickName</td>
        </tr>
        <sql:query var="resultSet" sql="select * from forum_user" offset="7">
        <tr>
            <td>${resultSet.getLong("user_id")}</td>
            <td>${resultSet.getString("user_name")}</td>
            <td>${resultSet.getString("nick_name")}</td>
        </tr>
        </sql:query>
    </table>
</sql:connect>
