INSERT INTO `Category` (name) VALUES ('Books');
INSERT INTO `Category` (name) VALUES ('Films');
INSERT INTO `Category` (name) VALUES ('Games');

INSERT INTO `Product` (`cost`, `description`, `name`, `rrp`,`productStock`) VALUES (180.5,'JR Tolkien''s book','Lord of the Rings',200,15);
INSERT INTO `Product` (`cost`, `description`, `name`, `rrp`,`productStock`) VALUES (120.5,'Unknow''s book','Hungry games',180,10);

INSERT INTO `Product_Category` (`product_id`, `category_id`) VALUES (1,1);
INSERT INTO `Product_Category` (`product_id`, `category_id`) VALUES (1,2);
INSERT INTO `Product_Category` (`product_id`, `category_id`) VALUES (2,1);
INSERT INTO `Product_Category` (`product_id`, `category_id`) VALUES (2,3);

INSERT INTO `User` (`address1`, `address2`, `dob`, `email`, `firstname`, `isAdmin`, `lastname`, `password`, `postcode`, `telephone`, `town`) VALUES ('Dalbobranten 12','','1981-12-11','enkidugan@gmail.com','Jorge',1,'Legarre','password','12868','0735560243','Sköndal');
INSERT INTO `User` (`address1`, `address2`, `dob`, `email`, `firstname`, `isAdmin`, `lastname`, `password`, `postcode`, `telephone`, `town`) VALUES ('Ingen aning','','1980-06-06','mail2@gmail.com','Björn',0,'Göransson','password2','12331','0735555555','Stockholm');

insert into OrderStatus (`description`) values ('Placed');
insert into OrderStatus (`description`) values ('Handling');
insert into OrderStatus (`description`) values ('Shipped');
insert into OrderStatus (`description`) values ('Closed');
insert into OrderStatus (`description`) values ('Cancelled');

INSERT INTO `Orders` (`date`, `user_id`,`status_id`) VALUES (NOW(),1,1);
INSERT INTO `Orders` (`date`, `user_id`,`status_id`) VALUES (NOW(),2,4);
INSERT INTO `Orders` (`date`, `user_id`,`status_id`) VALUES (NOW(),1,2);

insert into `OrderDetail` (`order_id`,`productId`,`name`,`quantity`,`cost`,`rrp`) values (1,1,'Lord of the Rings',2,160.5,180);
insert into `OrderDetail` (`order_id`,`productId`,`name`,`quantity`,`cost`,`rrp`) values (1,2,'Hungry games',3,120.5,180);
insert into `OrderDetail` (`order_id`,`productId`,`name`,`quantity`,`cost`,`rrp`) values (2,1,'Lord of the Rings',1,180.5,200);
insert into `OrderDetail` (`order_id`,`productId`,`name`,`quantity`,`cost`,`rrp`) values (3,1,'Lord of the Rings',5,180.5,200);
insert into `OrderDetail` (`order_id`,`productId`,`name`,`quantity`,`cost`,`rrp`) values (3,2,'Hungry games',4,120.5,180);
