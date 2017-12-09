/*
 * List the names of students who have taken a course taught by professor v5 (name)
 */
SET @v5 = 'name735790';

SELECT s.name
FROM student as s
    JOIN transcript as tr
        ON tr.studId = s.id
    JOIN teaching as t 
        ON t.crsCode = tr.crsCode AND tr.semester = t.semester
    JOIN professor as p
        ON p.id = t.profId
WHERE p.name = @v5