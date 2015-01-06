#Flashcard Application

An electronic flashcard web application. Currently, many flashcard sites charge money for extra features such as: text formatting, pictures, videos, audio, etc. The goal for this project is to offer an open source alternative to those sites and putting the UX/UI first. 

##Create your own development Environment (Get Hacking!):

***1. Go to your command line and input the following:***

>git clone https://github.com/HubertChen/FlashCards

***2. Install PostgreSQL***

>http://www.postgresql.org/download/

***3. Setup the database (Sorry, there should be easier way).***

>1. sudo su postgres
>2. createdb flashcards
>3. createuser test with password '123';
>4. grant all privileges on database "flashcards" to test;
>5. create table users(id serial primary key, username varchar(20) not null, email varchar(256), password varchar(512));
>6. create table folders(id serial, userId integer, name varchar(50), dateCreated timestamp);
>7. create table decks(id serial, folderId integer, name varchar(30), description varchar(256), dateCreated timestamp);
>8. create table cards(id serial, deckId integer, front text, back text, footnote text, dateCreated timestamp, picture text);
>9. grant all privileges on all tables in schema public to test;
>10. grant all on users to test;
