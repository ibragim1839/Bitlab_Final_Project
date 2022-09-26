create table if not exists users_roles(
    user_id int not null,
    role_id int not null,

    constraint users_roles_error_1
    foreign key (user_id) references users(id),

    constraint users_roles_error_2
    foreign key (role_id) references roles(id)
);