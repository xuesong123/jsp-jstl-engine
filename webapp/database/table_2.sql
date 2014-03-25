drop table if exists my_test2;

create table my_test2(
    my_id      varchar(64)              not null,
    my_code    varchar(8)               not null,
    my_name    varchar(32)              not null,
    primary key (my_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;