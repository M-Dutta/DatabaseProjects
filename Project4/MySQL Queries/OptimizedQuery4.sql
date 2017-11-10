/*
 * List the names of students who have taken a course taught by professor v5 (name)
 */
SET @v5 = 'nameX';

SELECT s.name
FROM student as s USE INDEX ()
    JOIN transcript as tr USE INDEX ()
        ON tr.studId = s.id
    JOIN teaching as t USE INDEX ()
        ON t.crsCode = tr.crsCode AND tr.semester = t.semester
    JOIN professor as p USE INDEX ()
        ON p.id = t.profId
WHERE p.name = @v5