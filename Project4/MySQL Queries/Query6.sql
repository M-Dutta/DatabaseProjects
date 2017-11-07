/*List the names of students who have taken all courses offered by department v8 (deptId)*/
select s.name
from student as s, transcript as t, course as c
where c.deptId="deptId626720" and c.crsCode = t.crsCode and s.id = t.studId