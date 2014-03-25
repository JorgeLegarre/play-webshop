INSERT INTO `Category` (name) VALUES ('Books');
INSERT INTO `Category` (name) VALUES ('Films');
INSERT INTO `Category` (name) VALUES ('Games');

INSERT INTO `Product` (`cost`, `description`, `name`, `rrp`) VALUES (180.5,'JR Tolkien''s book','Lord of the Rings',200);
INSERT INTO `Product` (`cost`, `description`, `name`, `rrp`) VALUES (120.5,'Unknow''s book','Hungry games',180);

INSERT INTO `Product_Category` (`products_id`, `categories_id`) VALUES (1,1);
INSERT INTO `Product_Category` (`products_id`, `categories_id`) VALUES (1,2);
INSERT INTO `Product_Category` (`products_id`, `categories_id`) VALUES (2,1);
INSERT INTO `Product_Category` (`products_id`, `categories_id`) VALUES (2,3);

INSERT INTO `User` (`address1`, `address2`, `dob`, `email`, `firstname`, `isAdmin`, `lastname`, `password`, `postcode`, `telephone`, `town`) VALUES ('Dalbobranten 12','','1981-12-11','enkidugan@gmail.com','Jorge',1,'Legarre','password','12868','0735560243','Sköndal');
INSERT INTO `User` (`address1`, `address2`, `dob`, `email`, `firstname`, `isAdmin`, `lastname`, `password`, `postcode`, `telephone`, `town`) VALUES ('Ingen aning','','1980-06-06','mail2@gmail.com','Björn',0,'Göransson','password2','12331','0735555555','Stockholm');
