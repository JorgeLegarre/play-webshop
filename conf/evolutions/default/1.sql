# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table category (
  id                        integer not null,
  name                      varchar(255),
  staff_responsible         integer,
  constraint pk_category primary key (id))
;

create sequence category_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists category;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists category_seq;

