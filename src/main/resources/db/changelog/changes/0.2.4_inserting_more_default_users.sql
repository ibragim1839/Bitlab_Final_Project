insert into users_roles(user_id, role_id) values(3,8);
insert into users_roles(user_id, role_id) values(4,7);

update users set pic_url='user_pic_1' where id=2;
update users set pic_url='user_pic_2' where id=3;
update users set pic_url='user_pic_3' where id=4;
update users set pic_url='user_pic_4' where id=5;

update pets set name='Кирпичик' where id=3;

insert into users (full_name ,email, password, pic_url)
values ('Расул Бишанло','user1@gmail.com','$2a$10$kJiBRcWhgjef9ZDDO1GJw.a0VDSsb4qQ5ZiUSQFXcD45GZnQ8Gvq6','user_pic_5');

insert into users (full_name ,email, password, pic_url)
values ('Аружан Люсанова','user2@gmail.com','$2a$10$kJiBRcWhgjef9ZDDO1GJw.a0VDSsb4qQ5ZiUSQFXcD45GZnQ8Gvq6','user_pic_6');

insert into users (full_name ,email, password, pic_url)
values ('Алихан Азбаев','owner1@gmail.com','$2a$10$kJiBRcWhgjef9ZDDO1GJw.a0VDSsb4qQ5ZiUSQFXcD45GZnQ8Gvq6','user_pic_7');

insert into users (full_name ,email, password, pic_url)
values ('Руслан Азбаев','owner2@gmail.com','$2a$10$kJiBRcWhgjef9ZDDO1GJw.a0VDSsb4qQ5ZiUSQFXcD45GZnQ8Gvq6','user_pic_8');

insert into users (full_name ,email, password, pic_url)
values ('Мэйсон Лалбекова','doctor1@gmail.com','$2a$10$kJiBRcWhgjef9ZDDO1GJw.a0VDSsb4qQ5ZiUSQFXcD45GZnQ8Gvq6','user_pic_9');

insert into users (full_name ,email, password, pic_url)
values ('Назгуль Бакытбекова','doctor2@gmail.com','$2a$10$kJiBRcWhgjef9ZDDO1GJw.a0VDSsb4qQ5ZiUSQFXcD45GZnQ8Gvq6','user_pic_10');

insert into doctors (address, phone_number, specifications, user_id)
values ('409650, Архангельская область, город Пушкино, проезд Сталина, 55',
        '7(7733)173-97-40',
        'Здравствуйте, дорогие друзья. Я - кинолог. Работаю со всеми собаками от крошечных чихуахуа до алабаев. Помогу вам и вашим питомцам преодолеть трудности и наладить отношения. Дрессировка, коррекция поведения, консультации по уходу за собакой.',
        3);

insert into owners(address, phone_number, user_id)
values ('896996, Московская область, город Балашиха, проезд Бухарестская, 78','7(25)717-68-49',4)

