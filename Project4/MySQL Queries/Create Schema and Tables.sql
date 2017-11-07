create schema Project4;

create Table Project4.Student (
id int not null,
name varchar(255),
address varchar(255),
status varchar(255),
primary key (id)
);

create Table Project4.Professor (
id int not null,
name varchar(255),
deptId varchar(255),
primary key (id)
);

create Table Project4.Course (
crsCode varchar(255),
deptId varchar(255),
crsName varchar(255),
descr varchar(255),
primary key (crsCode)
);

create Table Project4.Teaching (
crsCode varchar(255) ,
semester varchar(255),
profId int,
primary key (crsCode, semester),
foreign key (profId) references Professor(id),
foreign key (crsCode) references Course(crsCode)
);

create Table Project4.Transcript (
studId int not null,
crsCode varchar(255),
semester varchar(255),
grade varchar(255),
primary key (studId,crsCode,semester),
foreign key (studId) references Student(id),
foreign key (crsCode) references Course(crsCode),
foreign key (crsCode, semester) references Teaching(crsCode, semester)
);