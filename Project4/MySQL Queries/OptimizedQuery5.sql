/*
 * List the names of students who have taken a course from department v6 (deptId), but not v7.
 */
SET @v6 = 'deptId324641';
SET @v7 = 'deptId684634';

SELECT s.name
FROM student as s,
    (SELECT s.id
     FROM transcript as t , course as c 
     WHERE deptId = @v6 AND t.crsCode = c.crsCode
     AND s.id NOT IN
         (SELECT s.studId
          FROM transcript as t , course as c 
          WHERE deptId = @v7 AND t.crsCode = c.crsCode)) as alias
WHERE s.id = alias.id;