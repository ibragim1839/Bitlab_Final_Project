alter table cards alter column post_date type timestamp;
alter table cards alter column post_date set not null;
alter table cards alter column post_date set default current_timestamp;