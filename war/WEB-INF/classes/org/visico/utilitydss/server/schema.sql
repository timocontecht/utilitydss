CREATE DATABASE IF NOT EXISTS  utilityDSS;

USE utilityDSS;

DROP TABLE IF EXISTS user;

CREATE TABLE IF NOT EXISTS user
(
	id INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(250),
	password VARCHAR(250),
	email VARCHAR(250)
);

CREATE TABLE IF NOT EXISTS project
(
	id INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(250),
	location VARCHAR (250),
	description VARCHAR(250)
);

CREATE TABLE IF NOT EXISTS user_project
(
	id INT PRIMARY KEY AUTO_INCREMENT,
	userid INT,
	projectid INT
);

GRANT ALL ON utilityDSS.* to utilityDSS@localhost IDENTIFIED BY 'utilityDSS';

insert into user (name, password) VALUES ('utilityDSS', SHA1('utilityDSS'));
insert into user (name, password) VALUES ('tester', SHA1('test'));

insert into project (name, location, description) VALUES ('Demo Project 1', 'Enschede', 'This is a first demo project');
insert into project (name, location, description) VALUES ('Demo Project 2', 'Enschede', 'This is a first demo project');

insert into user_project (userid, projectid) VALUES (1,1);
insert into user_project (userid, projectid) VALUES (2,2);