/*List the names of students with id in the range of v2 (id) to v3 (inclusive).*/
SELECT s.name
FROM student as s USE INDEX ()
where s.id >= 10000 And s.id <=90000;


