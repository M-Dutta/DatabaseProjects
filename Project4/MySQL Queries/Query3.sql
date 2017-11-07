/*List the names of students who have taken course v4 (crsCode).*/
Select s.name
from student as s USE INDEX (), transcript as t USE INDEX ()
where t.crsCode="crsCode106349" and s.id = t.studId;
