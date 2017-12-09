/*
 * List the names of students who have taken all courses offered by department v8 (deptId)
 */
SET @v8 = 'deptId324641';

SELECT s.name
FROM student as s
    JOIN transcript as tr
        ON s.id = tr.studId
            WHERE crsCode IN
                  (SELECT c.crsCode
                   FROM course as c
                   WHERE c.deptId = @v8 AND crsCode IN
                                            (SELECT t.crsCode
                                             FROM teaching as t))
        GROUP BY s.id HAVING COUNT(*) = (SELECT COUNT(*)
                                         FROM course as c USE INDEX ()
                                         WHERE c.deptId = @v8 AND c.crsCode IN
                                                                  (SELECT t.crsCode
                                                                   FROM teaching as t))
