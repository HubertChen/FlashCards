Setup DB:
	1. sudo su postgres
	2. createdb flashcards
	3. create user test with password '123';
	4. grant all privileges on database "flashcards" to test;
	5. create table users(id serial primary key, username varchar(20) not null, email varchar(256), password varchar(512));
	6. grant all privileges on all tables in schema public to test;
