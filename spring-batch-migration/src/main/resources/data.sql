insert into author(id, name) values (1, 'first');
insert into author(id, name) values (2, 'second');

insert into genre(id, name) values (1, 'first');
insert into genre(id, name) values (2, 'second');

insert into book (id, title, description) values (1, 'title', 'description');

insert into book_genre(book_id, genre_id) values (1, 1);
insert into book_author(book_id, author_id) values (1, 1);
insert into book_author(book_id, author_id) values (1, 2);

insert into book (id, title, description) values (2, 'title2', 'description2');

insert into book_genre(book_id, genre_id) values (2, 2);
insert into book_author(book_id, author_id) values (2, 1);
insert into book_author(book_id, author_id) values (2, 2);

commit;