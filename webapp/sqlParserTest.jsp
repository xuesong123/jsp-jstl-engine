<sql:parse name="myTable">
create table game_app(
    id              bigint(20) unsigned not null comment '主键',
    gmt_create      datetime not null comment '创建时间',
    gmt_modified    datetime not null comment '修改时间',
    game_id         bigint(20) unsigned not null comment '游戏id',
    market          bigint(20) unsigned not null comment '市场-android,ios',
    channel         bigint(20) unsigned comment '渠道-主客,来往',
    app_key         varchar(64) comment 'TOP App Key',
    app_secret      varchar(64) comment 'TOP App Secret',
    os_require      varchar(256) comment '系统要求',
    download_url    varchar(256) comment '下载地址',
    size            varchar(64) comment '包大小',
    xiaoer_create   varchar(64) comment '创建小二',
    xiaoer_modified varchar(64) comment '修改小二',
    attributes      varchar(8192) comment '扩展信息',
    target_revenue  bigint(20) unsigned comment '目标收入',
    options         bigint(20) unsigned comment '扩展标识',
    identity        varchar(256) comment '唯一码',
    uri             varchar(2048) comment 'android预留',
    detail_url      varchar(2048) comment '详情页地址',
    logo_url        varchar(2048) comment '小图标地址'
);
<c:comment>
create table my_table(
    f1      bigint(20) unsigned not null comment '主键',
    f2      datetime not null comment '注释',
    f3      datetime not null comment '注释',
    f4      bigint(20) unsigned not null comment '注释',
    f5      bigint(20) unsigned not null comment '注释',
    f6      bigint(20) unsigned comment '注释',
    f7      varchar(64) comment '注释',
    f8      varchar(64) comment '注释',
    f9      varchar(256) comment '注释',
    f10     varchar(256) comment '注释',
    f11     varchar(64) comment '注释',
    f12     varchar(64) comment '注释',
    f13     varchar(64) comment '注释',
    f14     varchar(8192) comment '注释',
    f15     bigint(20) unsigned comment '注释',
    f16     bigint(20) unsigned comment '注释',
    f17     varchar(256) comment '注释',
    f18     varchar(2048) comment '注释',
    f19     varchar(2048) comment '注释',
    f20     varchar(2048) comment '注释',
    primary key (f1)
);
</c:comment>
</sql:parse>
<p>table: ${myTable.tableName}</p>
<c:out value="${myTable.getCreateString('`%s`')}"/>
<c:out value="${myTable.getQueryString()}"/>
<c:out value="${myTable.getInsertString()}"/>
<c:out value="${myTable.getUpdateString()}"/>
