create table users (
    email         varchar(500) primary key,
    username      varchar(100) not null,
    password      varchar(1000),
    created_date  timestamp not null,
    modified_date timestamp not null
)
;