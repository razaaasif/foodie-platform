create table user (
        id bigint not null auto_increment,
        email varchar(255),
        name varchar(255),
        verified bit not null,
        primary key (id)
    ) engine=InnoDB