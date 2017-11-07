/*List the names of students who have taken a course taught by professor v5 (name)*/
select s.name
from student as s USE INDEX (), professor as p USE INDEX (), teaching as t USE INDEX (), transcript as tr USE INDEX ()
where p.name = "name283155" and  s.id = tr.studId and tr.crsCode = t.crsCode and t.semester = tr.semester;
