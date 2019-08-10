# StudentInformation

### Project Members
* 072BCT502 - Aashutosh Poudel  
* 072BCT529 - Rohan Thapa       
* 072BCT530 - Rupesh Shrestha   

### Built on 
* Eclipse 2019-03 (4.11.0)
* JDK 1.8

### Login Credentials
* username: `admin`, password: `admin`

### Starting Page
* http://localhost:8080/JSF_StudentInformation/faces/login.xhtml

### Setup MySQL database:
~~~
create database eadd_db;
create user 'eadd_user'@'localhost' identified by 'ThePassword';
grant all on eadd_db.* to 'eadd_user'@'localhost';
~~~
### Create tables:
create the Users table
~~~
create table Users( 
uid int(20) not null auto_increment, 
uname varchar(60) not null, 
password varchar(60) not null, 
primary key(uid));
~~~
insert the admin into Users
~~~
insert into Users(uname, password) values('admin', 'admin');
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
