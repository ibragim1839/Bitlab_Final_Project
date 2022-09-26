create table if not exists cards(id serial primary key not null ,
header varchar(255) not null ,
post_date TIMESTAMP WITHOUT TIME ZONE,
pet_id int not null ,
constraint card_pet_fk foreign key (pet_id) references pets(id));