create table if not exists users(ID serial primary key not null ,
                                 FULL_NAME varchar not null default 'Новый пользователь' , EMAIL varchar not null unique ,
                                 PASSWORD varchar not null, pic_url text);

create table if not exists roles(ID serial primary key not null ,
                                 NAME varchar not null);
