create table site
(
    id bigint primary key auto_increment,
    name varchar(255),
    description varchar(255)
);

create table component
(
    id bigint primary key auto_increment,
    name varchar(255),
    component_type varchar(255),
    site_id bigint references site(id),
    max_visitors int
);

create table visitor
(
    id bigint primary key auto_increment,
    name varchar(255),
    age int
);

create table ticket
(
    id bigint primary key auto_increment,
    ticket_number varchar(255),
    component_id bigint references component (id),
    visitor_id bigint references visitor (id),
    time_purchase timestamp,
    time_end timestamp
);

create table if not exists acl_sid (
  id bigint(20) not null auto_increment,
  principal tinyint(1) not null,
  sid varchar(100) not null,
  primary key (id),
  unique key unique_uk_1 (sid,principal)
);

create table if not exists acl_class (
  id bigint(20) not null auto_increment,
  class varchar(255) not null,
  primary key (id),
  unique key unique_uk_2 (class)
);

create table if not exists acl_entry (
  id bigint(20) not null auto_increment,
  acl_object_identity bigint(20) not null,
  ace_order int(11) not null,
  sid bigint(20) not null,
  mask int(11) not null,
  granting tinyint(1) not null,
  audit_success tinyint(1) not null,
  audit_failure tinyint(1) not null,
  primary key (id),
  unique key unique_uk_4 (acl_object_identity,ace_order)
);

create table if not exists acl_object_identity (
  id bigint(20) not null auto_increment,
  object_id_class bigint(20) not null,
  object_id_identity bigint(20) not null,
  parent_object bigint(20) default null,
  owner_sid bigint(20) default null,
  entries_inheriting tinyint(1) not null,
  primary key (id),
  unique key unique_uk_3 (object_id_class,object_id_identity)
);

alter table acl_entry
    add foreign key (acl_object_identity) references acl_object_identity(id);

alter table acl_entry
    add foreign key (sid) references acl_sid(id);

--
-- constraints for table acl_object_identity
--
alter table acl_object_identity
    add foreign key (parent_object) references acl_object_identity (id);

alter table acl_object_identity
    add foreign key (object_id_class) references acl_class (id);

alter table acl_object_identity
    add foreign key (owner_sid) references acl_sid (id);


