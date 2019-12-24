create table appusers(
  id serial,
  login varchar(255) not null unique ,
  password varchar(255) not null
);
drop table appusers;
drop sequence idSeq;
create table idsandmessages (
  id integer not null ,
  time varchar(255) not null ,
  message varchar(255) not null
);