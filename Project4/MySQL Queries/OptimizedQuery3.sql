/*
 * List the names of students who have taken course v4 (crsCode).
 */
SET @v4 = 'crsCodeX';

Select s.name
from student as s USE INDEX ()
    JOIN transcript as t USE INDEX ()
        ON t.studid = s.id
WHERE t.crsCode = @v4;
