create table admin(
admin_id numeric primary key,
email varchar(50) unique not null,
first_name varchar(50) not null,
last_name varchar(50) not null,
account_status boolean
);

create sequence admin_id_seq;