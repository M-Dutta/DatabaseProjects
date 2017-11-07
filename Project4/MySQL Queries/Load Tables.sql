LOAD DATA LOCAL INFILE 'C:/Users/Mishuk/Documents/SQL Printout/course.txt' /* <-- Place your path in between '  ' here for all */ 
into table course columns terminated by ' '; 

LOAD DATA LOCAL INFILE 'C:/Users/Mishuk/Documents/SQL Printout/Professor.txt' 
into table professor columns terminated by ' ';

LOAD DATA LOCAL INFILE 'C:/Users/Mishuk/Documents/SQL Printout/Student.txt' 
into table student columns terminated by ' ';

LOAD DATA LOCAL INFILE 'C:/Users/Mishuk/Documents/SQL Printout/Teaching.txt' 
into table teaching columns terminated by ' ';

LOAD DATA LOCAL INFILE 'C:/Users/Mishuk/Documents/SQL Printout/Transcript.txt' 
into table transcript columns terminated by ' ';

