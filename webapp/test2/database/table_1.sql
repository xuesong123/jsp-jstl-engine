drop table if exists my_test1;

create table my_test1(
    my_id      int(11)                  not null,
    my_code    varchar(8)               not null,
    my_name    varchar(32)              not null,
    primary key (my_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into my_test1(my_id, my_code, my_name) values (1, '1111', '1111');
insert into my_test1(my_id, my_code, my_name) values (2, '1111', '1111');
insert into my_test1(my_id, my_code, my_name) values (3, '1111', '1111');
delete from my_test1;