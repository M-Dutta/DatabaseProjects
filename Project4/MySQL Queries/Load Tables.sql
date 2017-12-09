LOAD DATA LOCAL INFILE 'C:\Users\Brodie\Desktop\DatabaseProjects\Project4\Course.txt' /* <-- Place your path in between '  ' here for all */ 
into table course columns terminated by ' '; 

LOAD DATA LOCAL INFILE 'C:\Users\Brodie\Desktop\DatabaseProjects\Project4\Professor.txt' 
into table professor columns terminated by ' ';

LOAD DATA LOCAL INFILE 'C:\Users\Brodie\Desktop\DatabaseProjects\Project4\Student.txt' 
into table student columns terminated by ' ';

LOAD DATA LOCAL INFILE 'C:\Users\Brodie\Desktop\DatabaseProjects\Project4\Teaching.txt' 
into table teaching columns terminated by ' ';

LOAD DATA LOCAL INFILE 'C:\Users\Brodie\Desktop\DatabaseProjects\Project4\Transcript.txt' 
into table transcript columns terminated by ' ';

