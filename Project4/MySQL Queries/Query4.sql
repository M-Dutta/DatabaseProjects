/*List the names of students who have taken a course taught by professor v5 (name)*/
select s.name
from student as s, professor as p, teaching as t, transcript as tr
where p.name = "name283155" and  s.id = tr.studId and tr.crsCode = t.crsCode and t.semester = tr.semester 