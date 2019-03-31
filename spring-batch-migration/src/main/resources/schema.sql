create table book (
                    id number(19, 0) primary key,
                    title varchar(100 char) not null,
  description varchar(500 char) not null
);

create table author (
                      id number(19, 0) primary key,
                      name varchar(100 char) not null
);

create table genre (
                     id number(19, 0) primary key,
                     name varchar(100 char) not null
);

create sequence book_seq start with 1 increment by 1;
create sequence author_seq start with 1 increment by 1;
create sequence genre_seq start with 1 increment by 1;

create table book_author (
                           book_id number(19, 0),
                           author_id number(19, 0),
                           constraint ba_2_book foreign key (book_id) references book (id),
                           constraint ba_2_author foreign key (author_id) references author (id)
);

create table book_genre (
                          book_id number(19, 0),
                          genre_id number(19, 0),
                          constraint bg_2_book foreign key (book_id) references book (id),
                          constraint bg_2_genre foreign key (genre_id) references genre (id)
);