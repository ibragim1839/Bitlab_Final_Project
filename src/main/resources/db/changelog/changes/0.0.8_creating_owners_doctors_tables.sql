create table if not exists owners(id serial primary key not null,
    address varchar(255),
    phone_number varchar(20),
    user_id int not null,
    constraint fk_user_owner foreign key (user_id) references users(id));

create table if not exists doctors(id serial primary key not null,
                                  address varchar(255),
                                  phone_number varchar(20),
                                  specifications text,
                                  user_id int not null,
                                  constraint fk_user_owner foreign key (user_id) references users(id));