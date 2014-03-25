drop table if exists my_test1;

create table my_test1(
    my_id      varchar(64)              not null,
    my_code    varchar(8)               not null,
    my_name    varchar(32)              not null,
    primary key (my_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;