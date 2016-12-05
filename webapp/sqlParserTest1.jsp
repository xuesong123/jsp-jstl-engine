<sql:parse name="tableList">
create table `my_table`(
    `f1`      bigint(20) unsigned not null comment '主键',
    `f2`      datetime not null comment '注释',
    `f3`      datetime not null comment '注释',
    primary key (f1)
);
</sql:parse>

<c:forEach items="${tableList}" var="table">
<p>table: ${table.tableName}</p>
<c:out value="${table.getCreateString('`%s`')}"/>
<c:out value="${table.getQueryString()}"/>
<c:out value="${table.getInsertString()}"/>
<c:out value="${table.getUpdateString()}"/>
</c:forEach>
