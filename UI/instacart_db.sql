-- Create schema
CREATE SCHEMA `instacart` ;

USE `instacart`;

CREATE TABLE `instacart`.`data_object_type` (
  `_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NULL,
  PRIMARY KEY (`_id`));

CREATE TABLE `instacart`.`user_data_object` (
  `_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NULL,
  `user_id` INT NULL,
  `status` INT NULL DEFAULT 0,
  PRIMARY KEY (`_id`));

INSERT INTO `instacart`.`data_object_type` (`_id`, `name`) VALUES ('1', 'Products');
INSERT INTO `instacart`.`data_object_type` (`_id`, `name`) VALUES ('2', 'Orders');
INSERT INTO `instacart`.`data_object_type` (`_id`, `name`) VALUES ('3', 'Order_Products');

CREATE TABLE `instacart`.`instacart_data_object` (
  `_id` INT NOT NULL AUTO_INCREMENT,
  `user_data_id` INT NOT NULL,
  `object_type_id` INT NOT NULL,
  `bucket_name` VARCHAR(1000) NULL,
  `bucket_key` VARCHAR(1000) NULL,
  PRIMARY KEY (`_id`));


ALTER TABLE `instacart`.`user_data_object` 
CHANGE COLUMN `tenant_id` `user_id` INT NULL DEFAULT '1' ;

CREATE TABLE `instacart`.`instacart_features` (
  `_id` INT NOT NULL AUTO_INCREMENT,
  `instacart_object_id` INT NULL,
  `feature_source` VARCHAR(500) NULL,
  `feature_name` VARCHAR(1000) NULL,
  PRIMARY KEY (`_id`));


  CREATE TABLE `instacart`.`segmentation` (
  `_id` INT NOT NULL AUTO_INCREMENT,
  `user_data_object_id` INT NULL,
  `product_id` VARCHAR(500) NULL,
  `destination_bucket` VARCHAR(500) NULL,
  `destination_object` VARCHAR(500) NULL,
  `response_id` VARCHAR(500) NULL,
  PRIMARY KEY (`_id`));

  ALTER TABLE `instacart`.`segmentation` 
  ADD COLUMN `status` INT NULL DEFAULT 1 AFTER `response_id`;

  CREATE TABLE `instacart`.`train_instacart` (
  `_id` INT NOT NULL AUTO_INCREMENT,
  `status` INT NULL,
  `user_data_object_id` INT NULL,
  PRIMARY KEY (`_id`));

  ALTER TABLE `instacart`.`train_instacart` 
  CHANGE COLUMN `status` `status` INT NULL DEFAULT 1 ;