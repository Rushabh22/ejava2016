/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  ankur.jain
 * Created: 6 Nov, 2016
 */
drop table users;
drop table user_groups;
create table users(id integer not null generated always as identity(start with 1,increment by 1),
username varchar(255),
password varchar(255),
email varchar(255),
primary key (id)
);

create table user_groups(
        id integer not null generated always as identity(start with 1,increment by 1),
	groupname varchar(255),
	username varchar(255),
	primary key (id)
);

insert into users(username,password,email) values ('super','73d1b1b1bc1dabfb97f216d897b7968e44b06457920f00f2dc6c1ed3be25ad4c','admin@my.com');
insert into users(username,password,email) values ('admin','8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918','abc@my.com');
insert into users(username,password,email) values ('ankur','4b81d9c157b819532e78eab4ee305e3947602d8d444874cb4a9199731ec61adc','ankur@my.com');

insert into user_groups (groupname,username) values ('admingroup','super');

insert into user_groups (groupname,username) values ('usergroup','super');
insert into user_groups (groupname,username) values ('admingroup','admin');


insert into user_groups (groupname,username) values ('usergroup','ankur');
select * from users;
select * from user_groups;

create table notes(
	note_id integer not null generated always as identity(start with 1,increment by 1), 	
	username varchar(255),
	title varchar(100),
	category varchar(50),
	content varchar(255),
	created_date date,
	primary key(note_id)
);


