/*List the names of students who have taken all courses offered by department v8 (deptId)*/
explain analyze select s.name
from Project4.student as s, Project4.transcript as t, Project4.course as c
where c.deptId='deptId626720' and c.crsCode = t.crsCode and s.id = t.studId