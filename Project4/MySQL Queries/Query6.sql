/*List the names of students who have taken all courses offered by department v8 (deptId)*/
select s.name
from student as s USE INDEX (), transcript as t USE INDEX (), course as c USE INDEX ()
where c.deptId="deptId324641" and c.crsCode = t.crsCode and s.id = t.studId;
