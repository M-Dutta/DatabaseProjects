/*
 * List the names of students who have taken a course from department v6 (deptId), but not v7.
 */
SET @v6 = 'deptIdX';
SET @v7 = 'deptIdX';

SELECT s.name
FROM student as s,
    (SELECT s.id
     FROM transcript as t USE INDEX (), course as c USE INDEX ()
     WHERE deptId = @v6 AND t.crsCode = c.crsCode
     AND s.id NOT IN
         (SELECT s.studId
          FROM transcript as t USE INDEX (), course as c USE INDEX ()
          WHERE deptId = @v7 AND t.crsCode = c.crsCode)) as alias
WHERE s.id = alias.id;