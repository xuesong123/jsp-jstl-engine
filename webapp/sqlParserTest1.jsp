<sql:parse name="myTable">
create table my_table(
    f1      bigint(20) unsigned not null comment 'Ö÷¼ü',
    f2      datetime not null comment '×¢ÊÍ',
    f3      datetime not null comment '×¢ÊÍ',
    f4      bigint(20) unsigned not null comment '×¢ÊÍ',
    f5      bigint(20) unsigned not null comment '×¢ÊÍ',
    f6      bigint(20) unsigned comment '×¢ÊÍ',
    f7      varchar(64) comment '×¢ÊÍ',
    f8      varchar(64) comment '×¢ÊÍ',
    f9      varchar(256) comment '×¢ÊÍ',
    f10     varchar(256) comment '×¢ÊÍ',
    f11     varchar(64) comment '×¢ÊÍ',
    f12     varchar(64) comment '×¢ÊÍ',
    f13     varchar(64) comment '×¢ÊÍ',
    f14     varchar(8192) comment '×¢ÊÍ',
    f15     bigint(20) unsigned comment '×¢ÊÍ',
    f16     bigint(20) unsigned comment '×¢ÊÍ',
    f17     varchar(256) comment '×¢ÊÍ',
    f18     varchar(2048) comment '×¢ÊÍ',
    f19     varchar(2048) comment '×¢ÊÍ',
    f20     varchar(2048) comment '×¢ÊÍ',
    primary key (f1)
);
</sql:parse>
<p>table: ${myTable.tableName}</p>
<c:out value="${myTable.getCreateString('`%s`')}"/>
<c:out value="${myTable.getQueryString()}"/>
<c:out value="${myTable.getInsertString()}"/>
<c:out value="${myTable.getUpdateString()}"/>
