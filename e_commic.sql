create database e_commerce;
use e_commerce;

create table category(
id int auto_increment primary key,
category_name varchar(255),
status bit default 1 
);

delimiter //
create procedure get_category()
begin
 select * from category where status = '1'; 
end //
delimiter ;

delimiter //
create procedure findAll_category( page int,name varchar(255))
begin
DECLARE pageSize INT;
    DECLARE offsetVal INT;
    SET pageSize = 7;
    SET offsetVal = (page - 1) * pageSize;
 select * from category where category_name like concat('%',name,'%') order by id desc
 limit offsetVal, pageSize;
end //
delimiter ;

delimiter //
create procedure create_category(name varchar(255))
begin
 insert into category(category_name) value (name); 
end //
delimiter ;

delimiter //
create procedure find_category(id_find int)
begin
 select * from category where id = id_find; 
end //
delimiter ;

delimiter //
create procedure edit_category(id_edit int,name varchar(255))
begin
 update category set category_name = name where id = id_edit; 
end //
delimiter ;

delimiter //
create procedure delete_category(id_del int)
begin
 delete from category where id= id_del; 
end //
delimiter ;

delimiter //
create procedure change_status_category(id_change int)
begin
 update category set status = not status where id = id_change;
end //
delimiter ;

create table product(
id int auto_increment primary key,
product_name varchar(255),
img_url text,
description text,
price double,
stock int,
category_id int,
status bit default 1,
foreign key (category_id) references category(id) 
);

delimiter //
create procedure related_products(id_cate int , name varchar(255))
begin
 select * from product where categoty_id = id_cate or product_name = name  limit 4; 
end //
delimiter ;

delimiter //
create procedure exitByName(name varchar(255) , cate_id int)
begin
 select * from product where categoty_id = id_cate or product_name = name; 
end //
delimiter ;

delimiter //
create procedure findAll_product(page int, name varchar(255))
begin
DECLARE pageSize INT;
    DECLARE offsetVal INT;
    SET pageSize = 7;
    SET offsetVal = (page - 1) * pageSize;
 select * from product where product_name like concat('%',name,'%') order by id desc
 limit offsetVal, pageSize;
end //

DELIMITER //

CREATE PROCEDURE findAll_product_shop(IN page INT, IN name VARCHAR(255), IN cate_id INT)
BEGIN
    DECLARE pageSize INT;
    DECLARE offsetVal INT;
    SET pageSize = 9;
    SET offsetVal = (page - 1) * pageSize;

    IF cate_id = 0 THEN
        SELECT * FROM product
        WHERE product_name LIKE CONCAT('%', name, '%')
        ORDER BY id DESC
        LIMIT offsetVal, pageSize;
    ELSE
        SELECT * FROM product
        WHERE (product_name LIKE CONCAT('%', name, '%') OR category_id = cate_id)
        ORDER BY id DESC
        LIMIT offsetVal, pageSize;
    END IF;

END //

DELIMITER ;

call findAll_product_shop()


delimiter //
create procedure create_product(name varchar(255),img text,des text,pri double, sto int, cate_id int)
begin
 insert into product(product_name,img_url,description,price,stock,category_id) values 
 (name ,img ,des ,pri , sto , cate_id ); 
end //
delimiter ;

delimiter //
create procedure find_product(id_find int)
begin
 select * from product where id = id_find; 
end //
delimiter ;

delimiter //
create procedure edit_product(id_edit int,name varchar(255),img text,des text,pri double, sto int, cate_id int)
begin
 update product set product_name = name ,img_url=img,description=des,price=pri,stock=sto,category_id=cate_id
 where id = id_edit; 
end //
delimiter ;

delimiter //
create procedure update_stock(id_pro int, quantity int)
begin
 update product set stock =  (stock - quantity) where id = id_pro;
end //
delimiter ;

delimiter //
create procedure refund_stock(id_pro int, quantity int)
begin
 update product set stock =  (stock + quantity) where id = id_pro;
end //
delimiter ;

delimiter //
create procedure delete_product(id_del int)
begin
 delete from product where id= id_del; 
end //
delimiter ;

delimiter //
create procedure change_status_product(id_change int)
begin
 update product set status = not status where id = id_change;
end //
delimiter ;

create table role(
id int primary key auto_increment,
role_name varchar(255)
);

create table user(
id int auto_increment primary key,
username varchar(255) unique,
email varchar(255) unique,
password varchar(255),
role_id int default 2,
status bit default 1,
foreign key (role_id) references role(id)
);

delimiter //
create procedure findAll_user(page int, name varchar(255))
begin
DECLARE pageSize INT;
    DECLARE offsetVal INT;
    SET pageSize = 7;
    SET offsetVal = (page - 1) * pageSize;
 select * from user where role_id != '1' and  username like concat('%',name,'%') order by id desc
 limit offsetVal, pageSize;
end //

delimiter //
create procedure create_user(name varchar(255),mail varchar(255),pass varchar(255))
begin
 insert into user(username,email,password) values 
 (name ,mail ,pass); 
end //
delimiter ;

delimiter //
create procedure find_user(id_find int)
begin
 select * from user where id = id_find; 
end //
delimiter ;

delimiter //
create procedure edit_user(mail varchar(255), pass varchar(255))
begin
 update product set password = pass 
 where email = mail; 
end //
delimiter ;

delimiter //
create procedure change_status_user(id_change int)
begin
 update user set status = not status where id = id_change;
end //
delimiter ;

delimiter //
create procedure check_username(name varchar(255))
begin
 select * from user where username = name;
end //
delimiter ;

delimiter //
create procedure check_email(mail varchar(255))
begin
 select * from user where email = mail;
end //
delimiter ;

delimiter //
create procedure login(name varchar(255), pass varchar(255))
begin
 select * from user where username = name and password = pass;
end //
delimiter ;

create table cart(
id int auto_increment primary key,
user_id int,
product_id int,
quantity int default 1,
foreign key (user_id) references user(id),
foreign key (product_id) references product(id)
);

delimiter //
create procedure total(id_user int)
begin
 select sum(c.quantity * p.price ) total from cart c join product p on c.product_id = p.id where user_id = id_user;
end //
delimiter ;

delimiter //
create procedure clear(id_user int)
begin
 delete from cart where user_id = id_user; 
end //
delimiter ;

delimiter //
create procedure findAll_cart(page int,id_user int)
begin
DECLARE pageSize INT;
    DECLARE offsetVal INT;
    SET pageSize = 5;
    SET offsetVal = (page - 1) * pageSize;
 select * from cart where user_id = id_user order by id desc
 limit offsetVal, pageSize;
end //

delimiter //
create procedure find_cart(id_user int, id_product int)
begin
 select * from cart where user_id = id_user and product_id = id_product;
end //
delimiter ;

delimiter //
create procedure add_to_cart(id_user int, id_product int)
begin
 insert into cart(user_id,product_id) values(id_user,id_product);
end //
delimiter ;

delimiter //
create procedure findCartById(id_cart int)
begin
select * from cart where id=id_cart; 
end //
delimiter ;


delimiter //
create procedure update_cart(id_cart int, quan int)
begin
update cart set quantity = quan where id = id_cart;
end //
delimiter ;



delimiter //
create procedure delete_cart(id_cart int)
begin
delete from cart where id=id_cart; 
end //
delimiter ;

create table orders(
id int auto_increment primary key,
user_id int,
phone varchar(11),
adress varchar(255),
other text,
create_date datetime default now(),
status varchar(100),
foreign key  (user_id) references user(id)
);

delimiter //
create procedure getAll_order(page int, name varchar(255))
begin
DECLARE pageSize INT;
    DECLARE offsetVal INT;
    SET pageSize = 5;
    SET offsetVal = (page - 1) * pageSize;
 select * from  orders o join user u on o.user_id = u.id  where  username like concat('%',name,'%')  order by o.create_date desc
 limit offsetVal, pageSize;
end //

delimiter //
create procedure findAll_order(page int,id_user int)
begin
DECLARE pageSize INT;
    DECLARE offsetVal INT;
    SET pageSize = 5;
    SET offsetVal = (page - 1) * pageSize;
 select * from orders where user_id = id_user order by id desc
 limit offsetVal, pageSize;
end //

call findAll_order(1,5)

delimiter //
create procedure find_orders(id_user int, id_order int)
begin
 select * from orders where user_id = id_user and id = id_order;
end //
delimiter ;

delimiter //
create procedure find_order_user(id_user int)
begin
 select * from orders where user_id = id_user;
end //
delimiter ;

delimiter //

create procedure create_orders(IN id_user int, IN pho varchar(11), IN adre varchar(255), IN oth text, IN sta varchar(255), OUT idNew int)
begin
    insert into orders (user_id, phone, adress, other, status) values (id_user, pho, adre, oth, sta);
    select distinct last_insert_id() id into idNew from orders;
end //

delimiter ;

delimiter //
create procedure find_orderById(id_ord int)
begin
 select * from orders where id = id_ord;
end //
delimiter ;

delimiter //
create procedure change_status_order(id_order int, sta varchar(255))
begin
update orders set status = sta;
end //
delimiter ;

create table order_detail(
id int auto_increment primary key,
order_id int,
product_id int,
price double,
quantity int,
foreign key (product_id) references product(id)
);

delimiter //
create procedure find_order_detail(id_order int)
begin
select * from order_detail where order_id = id_order;
end //
delimiter ;

delimiter //
create procedure create_order_detail(id_ord int ,id_pro int, pri double, quan int)
begin
insert into order_detail(order_id,product_id,price,quantity) values (id_ord  ,id_pro , pri , quan);
end //
delimiter ;

delimiter //
create procedure total_order(id_order int)
begin
select sum(quantity * price) total_order from order_detail where order_id = id_order;
end //
delimiter ;

 use e_commerce;
alter table order_detail add constraint pk_o_od foreign key (order_id) references orders(id);


insert into role(role_name) value("ADMIN"), ("USER");
insert into user(username, password,role_id) value("admin123", "admin123", 1);
