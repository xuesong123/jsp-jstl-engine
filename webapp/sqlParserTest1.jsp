<sql:parse name="myTable">
create table `my_table`(
    `f1`      bigint(20) unsigned not null comment 'Ö÷¼ü',
    `f2`      datetime not null comment '×¢ÊÍ',
    `f3`      datetime not null comment '×¢ÊÍ',
    primary key (f1)
);
</sql:parse>
<p>table: ${myTable.tableName}</p>
<c:out value="${myTable.getCreateString('`%s`')}"/>
<c:out value="${myTable.getQueryString()}"/>
<c:out value="${myTable.getInsertString()}"/>
<c:out value="${myTable.getUpdateString()}"/>
