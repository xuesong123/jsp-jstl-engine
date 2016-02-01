<c:buffer var="csvContent">
id, userName, password, sex, birthday
1,"test1", "1234", 1, "2000-01-01"
2,"test2", "1234", 1, "2000-01-01"
</c:buffer>

# 1
<csv:each content="${csvContent}" var="dataSet">
    ${#dataSet.getInsert("user")}
</csv:each>

# 2
<csv:each content="${csvContent}" var="dataSet">
    <c:out value="${dataSet.getInsert('user')}"/>
</csv:each>

# 3
<csv:each content="${csvContent}" var="dataSet">
    <csv:dataSet value="${dataSet}" tableName="user" t:clip="false"/>
</csv:each>

# 4
<csv:each content="${csvContent}" var="dataSet">
    <csv:dataSet value="${dataSet}">
    insert into user(id, userName, password, sex, birthday) values (${?id}, '${?userName}', '${?password}', ${?sex}, '${?birthday}');
    </csv:dataSet>
</csv:each>

# 5
<csv:each file="webapp/test.csv" var="dataSet">
    <csv:dataSet value="${dataSet}">
    insert into user(id, userName, password, sex, birthday) values (${?id}, '${?userName}', '${?password}', ${?sex}, '${?birthday}');
    </csv:dataSet>
</csv:each>

# 6
<--
可以配合 sql:execute标签直接执行导入到数据库, 关于sql:execute请参考commandTest.jsp
数据导入导出请参考另一个开源项目: exchange, 支持从任意数据库导出任意格式的文件, 或者从已经导出的数据文件导入到任意数据库。
-->
<!-- sql:execute sql="${?dataSet.getInsert("user")}"/ -->