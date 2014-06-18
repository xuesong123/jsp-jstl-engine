<sql:parse name="myTable">
create table my_table(
    my_id          int(11) auto_increment not null comment '主键',
    my_name        varchar(64) not null default '' comment '名称',
    create_time    datetime not null default 0     comment '创建时间'
)  dsafdsafdsf ;
</sql:parse>
<c:out value="${myTable.getInsertString()}"/>