DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
`id`  int NOT NULL AUTO_INCREMENT ,
`first_name`  varchar(255) NULL ,
`last_name`  varchar(255) NULL ,
`id_card`  varchar(255) NULL ,
`date_of_birth`  date NULL
);

DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
`id`  int NOT NULL AUTO_INCREMENT ,
`reader`  varchar(255) NULL ,
`isbn`  varchar(255) NULL ,
`title`  varchar(255) NULL ,
`author`  varchar(255) NULL ,
`description`  varchar(255) NULL
);