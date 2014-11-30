Setup DB:
	1. sudo su postgres
	2. createdb flashcards
	3. create user test with password '123';
	4. grant all privileges on database "flashcards" to test;
	5. create table users(id serial primary key, username varchar(20) not null, email varchar(256), password varchar(512));
	6. grant all privileges on all tables in schema public to test;
	7. grant all on users to test;
	8. create table folders(id serial, userId integer, name varchar(50), dateCreated timestamp);
	9. create table decks(id serial, folderId integer, name varchar(30), description varchar(256), dateCreated timestamp);
	10. create table cards(id serial, deckId integer, front text, back text, footnote text, dateCreated timestamp);
