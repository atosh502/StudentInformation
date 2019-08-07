# StudentInformation

### Setup MySQL database:
~~~
create database eadd_db;
create user 'eadd_user'@'localhost' identified by 'ThePassword';
grant all on eadd_db.* to 'eadd_user'@'localhost';
~~~
### Create tables:
create the Users table
~~~
CREATE TABLE Users( 
uid int(20) NOT NULL AUTO_INCREMENT, 
uname VARCHAR(60) NOT NULL, 
password VARCHAR(60) NOT NULL, 
PRIMARY KEY(uid));
~~~
insert the admin into Users
~~~
INSERT INTO Users(uname, password) VALUES('admin', 'admin');
~~~
create the student table
~~~
create table student(
sid int(20) not null auto_increment,
first_name varchar(60) not null,
last_name varchar(60) not null,
email varchar(60) not null,
roll_no varchar(10) not null, 
primary key (sid));
~~~
create the address table
~~~
create table address(
aid int(20) not null auto_increment,
province int not null,
district varchar(60) not null, 
ward_no int not null, 
local_level varchar(120) not null, 
student_id int(20), 
primary key (aid),
foreign key (student_id)
references student(sid)
on delete cascade
on update cascade);
~~~
