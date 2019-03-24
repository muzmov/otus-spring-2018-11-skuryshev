create table IF NOT EXISTS system_message
(
  id      integer not null,
  content varchar(255),
  primary key (id)
);

CREATE TABLE IF NOT EXISTS acl_sid
(
  id        bigint(20)   NOT NULL AUTO_INCREMENT,
  principal tinyint(1)   NOT NULL,
  sid       varchar(100) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY unique_uk_1 (sid, principal)
);

CREATE TABLE IF NOT EXISTS acl_class
(
  id    bigint(20)   NOT NULL AUTO_INCREMENT,
  class varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY unique_uk_2 (class)
);

CREATE TABLE IF NOT EXISTS acl_entry
(
  id                  bigint(20) NOT NULL AUTO_INCREMENT,
  acl_object_identity bigint(20) NOT NULL,
  ace_order           int(11)    NOT NULL,
  sid                 bigint(20) NOT NULL,
  mask                int(11)    NOT NULL,
  granting            tinyint(1) NOT NULL,
  audit_success       tinyint(1) NOT NULL,
  audit_failure       tinyint(1) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY unique_uk_4 (acl_object_identity, ace_order)
);

CREATE TABLE IF NOT EXISTS acl_object_identity
(
  id                 bigint(20) NOT NULL AUTO_INCREMENT,
  object_id_class    bigint(20) NOT NULL,
  object_id_identity bigint(20) NOT NULL,
  parent_object      bigint(20) DEFAULT NULL,
  owner_sid          bigint(20) DEFAULT NULL,
  entries_inheriting tinyint(1) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY unique_uk_3 (object_id_class, object_id_identity)
);

ALTER TABLE acl_entry
  ADD FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity (id);

ALTER TABLE acl_entry
  ADD FOREIGN KEY (sid) REFERENCES acl_sid (id);

--
-- Constraints for table acl_object_identity
--
ALTER TABLE acl_object_identity
  ADD FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id);

ALTER TABLE acl_object_identity
  ADD FOREIGN KEY (object_id_class) REFERENCES acl_class (id);

ALTER TABLE acl_object_identity
  ADD FOREIGN KEY (owner_sid) REFERENCES acl_sid (id);

create table if not exists User
(
  id       bigint(21) primary key auto_increment,
  username varchar(100) not null,
  password varchar(100) not null
);

create table if not exists Author
(
  id          bigint(21) primary key auto_increment,
  first_name  varchar(100),
  last_name   varchar(100),
  middle_name varchar(100)
);

create table if not exists Role
(
  id   bigint(21) primary key auto_increment,
  role varchar(100)
);

create table if not exists user_to_role
(
  user_id bigint(21) not null ,
  role_id bigint(21) not null,
  PRIMARY KEY (user_id, role_id),
  CONSTRAINT ur2u
    FOREIGN KEY (user_id) REFERENCES User (id),
  CONSTRAINT ur2r
    FOREIGN KEY (role_id) REFERENCES Role (id)
);
