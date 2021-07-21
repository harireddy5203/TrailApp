CREATE TABLE IF NOT EXISTS dbauth.user
(
    id serial   NOT NULL,
    password character varying(255) NOT NULL,
    username character varying(32) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS dbauth.role
(
    id character varying(36) NOT NULL,
    description character varying(128),
    name character varying(64)  NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS dbauth.user_role
(
    user_id serial  NOT NULL,
    role_id character varying(36) NOT NULL,
    FOREIGN KEY (user_id)
        REFERENCES dbauth.user (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    FOREIGN KEY (role_id)
        REFERENCES dbauth.role (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    PRIMARY KEY(user_id, role_id)
);

insert into dbauth.user (id,username,password)
select 10,'admin','$2a$04$UDQWU8myYBaj.FQunbWdLObd8/wBgRRc0dLng3NndYYiEUPqND0LS'
where not exists (select username from dbauth.user where username='admin');

insert into dbauth.role (id, description, name) select
 '148544a4-4284-4bf3-accd-3f83f18dafbe','SalesHead', 'SalesHead'
 where not exists (select name from dbauth.role where name='SalesHead');


 insert into dbauth.user_role (user_id,role_id) select
 (SELECT id FROM dbauth.user WHERE username='admin'),
(SELECT id FROM dbauth.role WHERE name='SalesHead')
where not exists
(select user_id,role_id from dbauth.user_role where user_id in(select id from dbauth.user WHERE username='admin')
and role_id in(select id from dbauth.role where name ='SalesHead'));

insert into dbauth.role (id, description, name) select
 '2445713e-3a49-40a0-8215-f6f450227dec','SalesPerson', 'SalesPerson'
 where not exists (select name from dbauth.role where name='SalesPerson');


 insert into dbauth.user_role (user_id,role_id) select
 (SELECT id FROM dbauth.user WHERE username='admin'),
(SELECT id FROM dbauth.role WHERE name='SalesPerson')
where not exists
(select user_id,role_id from dbauth.user_role where user_id in(select id from dbauth.user WHERE username='admin')
and role_id in(select id from dbauth.role where name ='SalesPerson'));

insert into dbauth.role (id, description, name) select
 '79ceb888-9e5c-46a6-8e80-c5be93ba1bcc','Admin', 'Admin'
 where not exists (select name from dbauth.role where name='Admin');


 insert into dbauth.user_role (user_id,role_id) select
 (SELECT id FROM dbauth.user WHERE username='admin'),
(SELECT id FROM dbauth.role WHERE name='Admin')
where not exists
(select user_id,role_id from dbauth.user_role where user_id in(select id from dbauth.user WHERE username='admin')
and role_id in(select id from dbauth.role where name ='Admin'));

