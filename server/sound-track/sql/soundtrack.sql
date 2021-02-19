drop database if exists soundtrack;
create database soundtrack;

use soundtrack;

create table location (
	location_id int primary key auto_increment,
    location_name varchar(50) null,
    address varchar(128) not null
);

create table role (
	role_id int primary key auto_increment,
    role_name varchar(50) not null
);

create table system_user (
	user_id int primary key auto_increment,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    email varchar(128) not null,
    phone varchar(16) null,
    access_level varchar(20) not null,
    password_hash varchar(128) not null
);

create table user_role(
	user_role_id int primary key auto_increment,
    role_id int not null,
    user_id int not null,
    constraint fk_user_role_role_id
		foreign key (role_id)
		references role(role_id),
	constraint fk_user_role_user_id
		foreign key (user_id)
        references system_user(user_id)
);

create table event_(
	event_id int primary key auto_increment,
    event_name varchar(50) not null,
    location_id int not null,
    start_date date not null,
    end_date date not null,
    owner_id int not null,
    constraint fk_event_location_id
		foreign key (location_id)
        references location(location_id),
	constraint fk_event_owner_id
		foreign key (owner_id)
        references system_user(user_id)
);

create table item_type (
	item_type_id int primary key auto_increment,
    type_name varchar(50) not null
);

create table item (
	item_id int primary key auto_increment,
    item_name varchar(50) not null,
    description varchar(256) null,
    brand varchar(50) null,
    item_type_id int not null,
    item_category ENUM('VIDEO','AUDIO','LIGHTING','OTHER') not null,
    location_id int not null,
    location_description varchar(128) not null,
    is_broken boolean not null,
    notes varchar(512) null,
    constraint fk_item_item_type_id
		foreign key (item_type_id)
        references item_type(item_type_id),
	constraint fk_item_location_id
		foreign key (location_id)
        references location(location_id)
);

create table event_item (
	event_id int not null,
    item_id int not null,
    constraint fk_event_item_event_id
		foreign key (event_id)
        references event_(event_id),
	constraint fk_event_item_item_id
		foreign key (item_id)
        references item(item_id)
);

create table event_user_role (
	event_id int not null,
    user_role_id int not null,
    constraint fk_event_user_role_event_id
		foreign key (event_id)
        references event_(event_id),
	constraint fk_event_user_role_user_role_id
		foreign key (user_role_id)
        references user_role(user_role_id)
);

create table repair_ticket(
	repair_ticket_id int primary key auto_increment,
    item_id int not null,
    requestor int not null,
    assigned_to int not null,
    status varchar(50) not null,
    description varchar(256) not null,
    constraint fk_repair_ticket_item_id
		foreign key (item_id)
        references item(item_id),
	constraint fk_repair_ticket_requestor
		foreign key (requestor)
        references system_user(user_id),
	constraint fk_repair_ticket_assigned_to
		foreign key (assigned_to)
        references system_user(user_id)
);

insert into location(address, location_name) values
		("123 4th Street", "The Chapel"),
        ("45 West Avenue", "The Barn"),
        ("44 Sunset Blvd.", "Pa's House");

insert into role (role_name) values
		("Sound Board"),
        ("Light Board");

insert into item_type (type_name) values
		("microphone"),
        ("drum"),
        ("projector");

insert into item (item_name, description, brand, item_type_id, item_category, location_id, location_description, is_broken, notes) values
		("Microphone 1", "Bass mic", "Sony", 1, "AUDIO", 1 , "Shelf A", false, "no notes"),
        ("Drum", "Kick", "DrumstickInc", 2, "AUDIO", 1 , "Shelf B", false, "no notes"),
        ("Short Throw", "Projector", "Panasonic", 3, "VIDEO", 1 , "Shelf C", false, "this one is good for short distance projecting");