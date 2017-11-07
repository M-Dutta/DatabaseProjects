/*List the names of students who have taken a course from department v6 (deptId), but not v7.*/
select s.name
from student as s, transcript as t, course as c
where s.id = t.studId and (t.crsCode= c.crsCode and deptId="deptId626720" and deptId!="deptId684634") 