/*List the names of students who have taken course v4 (crsCode).*/
Select s.name
from student as s, transcript as t
where t.crsCode="crsCode106349" and s.id = t.studId;