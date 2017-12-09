/*
 * List the names of students who have taken course v4 (crsCode).
 */
SET @v4 = 'crsCode685200';

Select s.name
from student as s 
    JOIN transcript as t 
        ON t.studid = s.id
WHERE t.crsCode = @v4;
