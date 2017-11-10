/*List the names of students who have taken a course taught by professor v5 (name)*/
EXPLAIN ANALYZE select s.name
from Project4.student as s, Project4.professor as p, Project4.teaching as t, Project4.transcript as tr
where p.name = 'name283155' and  s.id = tr.studId and tr.crsCode = t.crsCode and t.semester = tr.semester 