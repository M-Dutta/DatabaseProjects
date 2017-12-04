create schema website;

create table website.company_info(
company_name varchar (35) not null,
WebAddress varchar (35),
Rating varchar(25),
primary key (company_name,WebAddress)
);

create table website.flights(
Company_name varchar(25) ,
Flight_no varchar(25),
Price double not null,
Departure_time time not null,
Arrival_time time not null,
Departure_city varchar(20) not null,
Arrival_city varchar(20) not null,
primary key(Company_name,Flight_no,Departure_time, Departure_city )
);

create table website.users(
Fname varchar(25) not null,
Lname varchar(25) not null,
BirthDate date,
Email varchar(50) primary key,
phone varchar(17)
);
