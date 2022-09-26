create table if not exists breeds(id serial primary key not null,
name varchar(255) not null ,
specifications text,
animal_id int not null,
pic_url varchar,
constraint fk_animal_breed foreign key (animal_id) references animals(id));