create table if not exists comments(id serial primary key not null ,
body text not null ,
post_date timestamp default (current_timestamp),
author int not null ,
pet_id int not null ,
constraint comment_pet_fk foreign key (pet_id) references pets(id) ,
constraint comment_user_fk foreign key (author) references users(id));