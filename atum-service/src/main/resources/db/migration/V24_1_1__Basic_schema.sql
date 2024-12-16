create schema if not exists catalogue;

create table if not exists catalogue.t_product (
    id serial primary key,
    c_title varchar(50) not null check(length(trim(c_title)) >= 3),
    c_description varchar(1000)
);