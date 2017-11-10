/*List the names of students who have taken a course from department v6 (deptId), but not v7.*/
explain analyze select s.name
from Project4.student as s, Project4.transcript as t, Project4.course as c
where s.id = t.studId and (t.crsCode= c.crsCode and deptId='deptId626720' and deptId!='deptId684634') 