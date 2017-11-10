/*List the names of students who have taken course v4 (crsCode).*/
Explain ANALYZE Select s.name
from Project4.student as s, Project4.transcript as t
where t.crsCode='crsCode106349' and s.id = t.studId;