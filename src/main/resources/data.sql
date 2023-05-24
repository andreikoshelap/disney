insert into site(id, name, description)
values (1, 'South', 'oceanic beach'),
(2, 'West', 'magical forest'),
(3, 'East', 'asia desert'),
(4, 'North', 'north pole')
;

insert into component(name, component_type, site_id, max_visitors)
values ('green', 'DOUBLE_SWINGS', 1, 10),
('red', 'DOUBLE_SWINGS', 1, 15),
('carousel with music', 'CAROUSEL', 1, 12),
('roller coaster', 'CAROUSEL', 1, 10),
('water slide', 'SLIDE', 1, 16),
('Wonder land', 'BALL_PIT', 1, 1),
('blue swings', 'DOUBLE_SWINGS', 2, 5),
('carousel no music', 'CAROUSEL', 2, 10),
('roller coaster 2', 'CAROUSEL', 3, 10),
('water slide 2', 'SLIDE', 4, 10),
('Wonder land 2', 'BALL_PIT', 3, 10);

insert into visitor(name, age)
values ('John Dow', 13),
('Alice Dow', 15),
('Ivan Dubov', 12),
('Sebastien Col', 10);

insert into ticket(ticket_number, component_id, visitor_id, time_purchase, time_end)
values ('a57136cc-f92a-11ed-be56-0242ac120002', 1, 1, '2023-05-05 13.59.59', '2023-05-05 14.59.59'),
('ad22e546-f92a-11ed-be56-0242ac120002', 2, 2, '2023-05-05 13.20.59', '2023-05-05 14.20.59'),
('b4e498a6-f92a-11ed-be56-0242ac120002', 3, 3, '2023-05-05 13.15.00', '2023-05-05 14.15.00'),
('ba1a5978-f92a-11ed-be56-0242ac120002', 1, 4, '2023-05-04 13.15.00', '2023-05-04 14.15.00'),
('c4feb866-f92a-11ed-be56-0242ac120002', 1, 4, '2023-05-05 14.10.00', '2023-05-05 15.10.00');

insert into acl_sid (id, principal, sid)
values    (1, 1, 'admin'),
(2, 1, 'user'),
(3, 0, 'role_editor');

insert into acl_class (id, class)
values    (1, 'com.kn.koshelap.disney.ticket');

insert into acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting)
values    (1, 1, 1, null, 3, 0),
(2, 1, 2, null, 3, 0),
(3, 1, 3, null, 3, 0);

insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure)
values    (1, 1, 1, 1, 1, 1, 1, 1),
(2, 1, 2, 1, 2, 1, 1, 1),
(3, 1, 3, 3, 1, 1, 1, 1),
(4, 2, 1, 2, 1, 1, 1, 1),
(5, 2, 2, 3, 1, 1, 1, 1),
(6, 3, 1, 3, 1, 1, 1, 1),
(7, 3, 2, 3, 2, 1, 1, 1);




