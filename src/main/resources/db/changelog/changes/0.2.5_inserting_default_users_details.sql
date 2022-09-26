insert into users_roles(user_id, role_id) values (6,5);
insert into users_roles(user_id, role_id) values (7,5);

insert into users_roles(user_id, role_id) values (8,5);
insert into users_roles(user_id, role_id) values (9,5);
insert into users_roles(user_id, role_id) values (8,7);
insert into users_roles(user_id, role_id) values (9,7);

insert into owners (address, phone_number, user_id)
values ('540164, Челябинская область, город Одинцово, ул. Славы, 22','7(4488)114-98-690',8);
insert into owners (address, phone_number, user_id)
values ('912277, Самарская область, город Чехов, въезд Чехова, 17','7(9723)661-77-68',9);

insert into users_roles(user_id, role_id) values (10,5);
insert into users_roles(user_id, role_id) values (11,5);
insert into users_roles(user_id, role_id) values (10,8);
insert into users_roles(user_id, role_id) values (11,8);

insert into doctors(address, phone_number, specifications, user_id)
VALUES ('663588, Владимирская область, город Шаховская, проезд Гоголя, 32','7(861)588-21-68',
        'Практикующий кинолог Дарья, опыт работы 4 года. Окончила курсы РКФ по специальности кинолог. Считаю, что залог успешного воспитания и дрессировки — индивидуальный подход к каждой собаке.',10);

insert into doctors(address, phone_number, specifications, user_id)
VALUES ('808392, Самарская область, город Домодедово, бульвар Ленина, 86','7(6495)925-25-19',
        'Все собаки живут в домашних условиях. Дом с большой огороженной территорией. Выгул в экологически чистых ближайших лесах и полях. Фото/видео отчеты. Опыт — 9 лет. Я с собаками постоянно дома, это мой основной вид деятельности.',11);
