CREATE DATABASE IF NOT EXISTS foodie_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
 create table menu_item_seq (
        next_val bigint
    ) engine=InnoDB

    insert into menu_item_seq values ( 1 )

    create table menu_item (
        id bigint not null,
        available bit not null,
        category varchar(255),
        description varchar(255),
        name varchar(255),
        price float(53),
        restaurant_id bigint,
        primary key (id)
    ) engine=InnoDB