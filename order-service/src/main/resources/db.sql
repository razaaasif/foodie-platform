 create table order (
        id bigint not null auto_increment,
        delivery_address varchar(255) not null,
        delivery_time datetime(6),
        order_time datetime(6) not null,
        payment_id varchar(255) not null,
        payment_method tinyint not null check (payment_method between 0 and 3),
        restaurant_id bigint not null,
        rider_id bigint,
        status enum ('CANCELLED','CREATED','DELIVERED','ON_THE_WAY','OUT_FOR_DELIEVERY','PAYMENT_COMPLETED','PAYMENT_FAILED','PAYMENT_PENDING','PICKED_UP','PREPARED','PREPARING','READY_FOR_PICKUP','RESTAURANT_CONFIRMED') not null,
        total_amount decimal(38,2) not null,
        user_id bigint not null,
        primary key (id)
    ) engine=InnoDB;

     create table order (
            id bigint not null auto_increment,
            delivery_address varchar(255) not null,
            delivery_time datetime(6),
            order_time datetime(6) not null,
            payment_id varchar(255) not null,
            payment_method tinyint not null check (payment_method between 0 and 3),
            restaurant_id bigint not null,
            rider_id bigint,
            status enum ('CANCELLED','CREATED','DELIVERED','ON_THE_WAY','OUT_FOR_DELIVERY','PAYMENT_COMPLETED','PAYMENT_FAILED','PAYMENT_PENDING','PICKED_UP','PREPARED','PREPARING','READY_FOR_PICKUP','RESTAURANT_CONFIRMED') not null,
            total_amount decimal(38,2) not null,
            user_id bigint not null,
            primary key (id)
        ) engine=InnoDB;

            create table order_item (
                 id bigint not null auto_increment,
                 menu_item_id bigint not null,
                 price_per_item decimal(38,2) not null,
                 quantity integer not null,
                 special_instructions varchar(255),
                 order_id bigint not null,
                 primary key (id)
             ) engine=InnoDB;


               alter table order_item
                    add constraint FKt6wv8m7eshksp5kp8w4b2d1dm
                    foreign key (order_id)
                    references order (id);


                        alter table order_item
                           add constraint FKt6wv8m7eshksp5kp8w4b2d1dm
                           foreign key (order_id)
                           references order (id);